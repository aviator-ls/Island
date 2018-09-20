package com.aviator.island.entity.dto.input;

import com.aviator.island.entity.po.Tag;
import com.aviator.island.exception.InputDTOConvertException;
import com.google.common.base.Converter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * Created by aviator_ls on 2018/8/15.
 */
@Data
@Accessors(chain = true)
public class TagInputDTO extends BaseInputDTO<TagInputDTO, Tag> {

    @NotBlank(message = "标签名称不可为空")
    private String name;

    private String desc;

    private String imgPath;

    @Override
    public Tag converterTo() {
        return new TagInputConverter().convert(this);
    }

    protected class TagInputConverter extends Converter<TagInputDTO, Tag> {

        @Override
        protected Tag doForward(TagInputDTO tagInputDTO) {
            Tag tag = new Tag();
            try {
                BeanUtils.copyProperties(tagInputDTO, tag);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return tag;
        }

        @Override
        protected TagInputDTO doBackward(Tag tag) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
