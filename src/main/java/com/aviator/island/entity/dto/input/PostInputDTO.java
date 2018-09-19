package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.google.common.collect.Sets;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.exception.InputDTOConvertException;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.BoardService;
import com.aviator.island.service.PostSpecialService;
import com.aviator.island.service.TagService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/13.
 */
@Data
@Accessors(chain = true)
public class PostInputDTO extends BaseInputDTO<PostInputDTO, Post> {

    @NotBlank(message = "标题不可为空")
    private String title;// 帖子标题

    @NotBlank(message = "文章内容不可为空")
    private String content;// 帖子内容

    private String boardId;

    private String specialId;

    @Min(value = 1, message = "文章类型错误")
    private int sourceType;

    private String source;

    private String reference;

    private Set<String> tagIdSet;

    @Override
    public Post converterTo() {
        return new PostInputConverter().convert(this);
    }

    protected class PostInputConverter extends Converter<PostInputDTO, Post> {

        @Override
        protected Post doForward(PostInputDTO postInputDTO) {
            // 转载或翻译文章，应标明来源
            if (sourceType > 1 && StringUtils.isBlank(source)) {
                throw new ParamsErrorException("sourceType gt 1 and source is blank");
            }
            Post post = new Post();
            try {
                BeanUtils.copyProperties(postInputDTO, post);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            if (StringUtils.isNotBlank(boardId)) {
                Board board = (Board) ComponentManager.getComponent(BoardService.class).get(boardId);
                if (board != null && !CollectionUtils.isEmpty(board.getChildBoardSet())) {
                    throw new ParamsErrorException("board is not leaf");
                }
                post.setBoard(board);
            }
            if (StringUtils.isNotBlank(specialId)) {
                PostSpecial postSpecial = (PostSpecial) ComponentManager.getComponent(PostSpecialService.class).get(specialId);
                if (postSpecial == null) {
                    throw new ParamsErrorException("postSpecial is not exist");
                }
                post.setSpecial(postSpecial);
            }
            if (!CollectionUtils.isEmpty(tagIdSet)) {
                List<Tag> tagList = ComponentManager.getComponent(TagService.class).findListByIds(tagIdSet);
                if (CollectionUtils.isEmpty(tagList) || tagList.size() < tagIdSet.size()) {
                    throw new ParamsErrorException("tag not exist");
                }
                Set<Tag> tagSet = Sets.newHashSet(tagList);
                if (!CollectionUtils.isEmpty(tagSet)) {
                    post.setTagSet(tagSet);
                }
            }
            return post;
        }

        @Override
        protected PostInputDTO doBackward(Post post) {
            throw new AssertionError("don't support doBackward converter");
        }
    }

}
