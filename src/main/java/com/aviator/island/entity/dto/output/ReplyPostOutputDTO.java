package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.ReplyPost;
import com.aviator.island.entity.sys.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by 18057046 on 2018/8/16.
 */
@Data
@Accessors(chain = true)
public class ReplyPostOutputDTO extends BaseOutputDTO<ReplyPost, ReplyPostOutputDTO> {

    private String id;// 回复贴id

    private String content;// 回复内容

    private String mainPostId;

    private String mainReplyPostId;// 本回复回复的ReplyPostId

    private UserOutputDTO user;// 回复用户

    private Date createTime = new Date();// 发布时间

    private Date updateTime = new Date();// 更新时间

    @Override
    public ReplyPostOutputDTO convertFor(ReplyPost replyPost) {
        return new ReplyPostOutputConvert().convert(replyPost);
    }

    protected class ReplyPostOutputConvert extends Converter<ReplyPost, ReplyPostOutputDTO> {

        @Override
        protected ReplyPostOutputDTO doForward(ReplyPost replyPost) {
            ReplyPostOutputDTO replyPostOutputDTO = new ReplyPostOutputDTO();
            try {
                BeanUtils.copyProperties(replyPost, replyPostOutputDTO);
                if (replyPost.getMainPost() != null) {
                    replyPostOutputDTO.setMainPostId(replyPost.getMainPost().getId());
                }
                if (replyPost.getMainReplyPost() != null) {
                    replyPostOutputDTO.setMainReplyPostId(replyPost.getMainReplyPost().getId());
                }
                User user = replyPost.getUser();
                if (user != null) {
                    UserOutputDTO userOutputDTO = new UserOutputDTO().convertFor(user);
                    replyPostOutputDTO.setUser(userOutputDTO);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return replyPostOutputDTO;
        }

        @Override
        protected ReplyPost doBackward(ReplyPostOutputDTO replyPostOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
