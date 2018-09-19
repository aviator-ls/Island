package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.entity.sys.User;
import com.aviator.island.utils.ConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/15.
 */
@Data
@Accessors(chain = true)
public class PostOutputDTO extends BaseOutputDTO<Post, PostOutputDTO> {
    private String id;

    private String title;// 帖子标题

    private String content;// 帖子内容

    private int sourceType;// 来源,1:原创,2:转载,3:翻译

    private String source;

    private String reference;

    private UserOutputDTO user;// 发帖人

//    private Set<ReplyPostOutputDTO> replyPostSet;// 回复帖

    private Set<TagOutputDTO> tagSet;// 标签

    private Date createTime = new Date();// 发布时间

    private Date updateTime = new Date();// 更新时间

    @Override
    public PostOutputDTO convertFor(Post post) {
        return new PostOutputConvert().convert(post);
    }

    protected class PostOutputConvert extends Converter<Post, PostOutputDTO> {

        @Override
        protected PostOutputDTO doForward(Post post) {
            PostOutputDTO postOutputDTO = new PostOutputDTO();
            try {
                BeanUtils.copyProperties(post, postOutputDTO);
//                Set<ReplyPost> replyPostSet = post.getReplyPostSet();
//                if (!CollectionUtils.isEmpty(replyPostSet)) {
//                    Set<ReplyPostOutputDTO> replyPostOutputDTOSet = ConvertUtil.convertCollectionToOutputDTOSet(replyPostSet, new ReplyPostOutputDTO());
//                    postOutputDTO.setReplyPostSet(replyPostOutputDTOSet);
//                }
                Set<Tag> tagSet = post.getTagSet();
                if (!CollectionUtils.isEmpty(tagSet)) {
                    Set<TagOutputDTO> tagOutputDTOSet = ConvertUtil.convertCollectionToOutputDTOSet(tagSet, new TagOutputDTO());
                    postOutputDTO.setTagSet(tagOutputDTOSet);
                }
                User user = post.getUser();
                if (user != null) {
                    UserOutputDTO userOutputDTO = new UserOutputDTO().convertFor(user);
                    postOutputDTO.setUser(userOutputDTO);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return postOutputDTO;
        }

        @Override
        protected Post doBackward(PostOutputDTO PostOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
