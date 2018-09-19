package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.entity.sys.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by aviator_ls on 2018/9/12.
 */
@Data
@Accessors(chain = true)
public class PostSpecialOutputDTO extends BaseOutputDTO<PostSpecial, PostSpecialOutputDTO> {

    private String id;

    private String name;

    private String desc;

    private String userId;

    @Override
    public PostSpecialOutputDTO convertFor(PostSpecial postSpecial) {
        return new PostSpecialOutputConvert().convert(postSpecial);
    }

    protected class PostSpecialOutputConvert extends Converter<PostSpecial, PostSpecialOutputDTO> {

        @Override
        protected PostSpecialOutputDTO doForward(PostSpecial postSpecial) {
            PostSpecialOutputDTO postSpecialOutputDTO = new PostSpecialOutputDTO();
            try {
                BeanUtils.copyProperties(postSpecial, postSpecialOutputDTO);
                User user = postSpecial.getUser();
                if (user != null) {
                    postSpecialOutputDTO.setUserId(user.getId());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return postSpecialOutputDTO;
        }

        @Override
        protected PostSpecial doBackward(PostSpecialOutputDTO postSpecialOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
