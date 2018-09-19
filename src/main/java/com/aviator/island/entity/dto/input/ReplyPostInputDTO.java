package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.ReplyPost;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.PostService;
import com.aviator.island.service.ReplyPostService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * Created by 18057046 on 2018/8/16.
 */
@Data
@Accessors(chain = true)
public class ReplyPostInputDTO extends BaseInputDTO<ReplyPostInputDTO, ReplyPost> {

    @NotBlank(message = "回复内容不可为空")
    private String content;// 回复内容

    @NotBlank(message = "回复id不可为空")
    private String mainPostId;

    private String mainReplyPostId;

    @Override
    public ReplyPost converterTo() {
        return new ReplyPostInputConverter().convert(this);
    }

    protected class ReplyPostInputConverter extends Converter<ReplyPostInputDTO, ReplyPost> {

        @Override
        protected ReplyPost doForward(ReplyPostInputDTO replyPostInputDTO) {
            ReplyPost replyPost = new ReplyPost();
            BeanUtils.copyProperties(replyPostInputDTO, replyPost);
            PostService<Post> postService = ComponentManager.getComponent(PostService.class);
            Post mainPost = postService.get(mainPostId);
            if (mainPost == null) {
                throw new ParamsErrorException("mainPost not exist");
            }
            replyPost.setMainPost(mainPost);
            if (StringUtils.isNotBlank(mainReplyPostId)) {
                ReplyPostService<ReplyPost> replyPostService = ComponentManager.getComponent(ReplyPostService.class);
                ReplyPost mainReplyPost = replyPostService.get(mainReplyPostId);
                if(mainReplyPost == null){
                    throw new ParamsErrorException("mainReplyPost not exist");
                }
                replyPost.setMainReplyPost(mainReplyPost);
            }
            return replyPost;
        }

        @Override
        protected ReplyPostInputDTO doBackward(ReplyPost replyPost) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
