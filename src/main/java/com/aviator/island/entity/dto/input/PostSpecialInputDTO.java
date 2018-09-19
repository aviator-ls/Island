package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.exception.InputDTOConvertException;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * Created by 18057046 on 2018/9/12.
 */
@Data
@Accessors(chain = true)
public class PostSpecialInputDTO extends BaseInputDTO<PostSpecialInputDTO, PostSpecial> {

    @NotBlank(message = "专辑名称不可为空")
    private String name;// 专辑名

    private String desc;// 专辑描述

    @Override
    public PostSpecial converterTo() {
        return new PostInputConverter().convert(this);
    }

    protected class PostInputConverter extends Converter<PostSpecialInputDTO, PostSpecial> {

        @Override
        protected PostSpecial doForward(PostSpecialInputDTO postSpecialInputDTO) {
            PostSpecial postSpecial = new PostSpecial();
            try {
                BeanUtils.copyProperties(postSpecialInputDTO, postSpecial);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return postSpecial;
        }

        @Override
        protected PostSpecialInputDTO doBackward(PostSpecial postSpecial) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
