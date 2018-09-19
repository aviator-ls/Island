package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Post;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.PostService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * Created by 18057046 on 2018/9/19.
 */
@Data
@Accessors(chain = true)
public class CollectPostInputDTO extends BaseInputDTO<CollectPostInputDTO, List<Post>> {
    @NotEmpty
    Set<String> postIdSet;

    @Override
    public List<Post> converterTo() {
        return new CollectPostInputConverter().convert(this);
    }

    protected class CollectPostInputConverter extends Converter<CollectPostInputDTO, List<Post>> {

        @Override
        protected List<Post> doForward(CollectPostInputDTO collectPostInputDTO) {
            for (String postId : postIdSet) {
                if (StringUtils.isBlank(postId)) {
                    throw new ParamsErrorException("postId is blank");
                }
            }
            PostService<Post> postService = ComponentManager.getComponent(PostService.class);
            List<Post> result = postService.findListByIds(postIdSet);
            if (CollectionUtils.isEmpty(result) || result.size() != postIdSet.size()) {
                throw new ParamsErrorException("postList by idSet is not exist");
            }
            return result;
        }

        @Override
        protected CollectPostInputDTO doBackward(List<Post> list) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
