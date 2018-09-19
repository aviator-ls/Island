package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Tag;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by aviator_ls on 2018/8/16.
 */
@Data
@Accessors(chain = true)
public class TagOutputDTO extends BaseOutputDTO<Tag, TagOutputDTO> {

    private String id;

    private String name;

    private String desc;

    private String imgPath;

    private String path;

    private Date createTime;

    private Date updateTime;

    @Override
    public TagOutputDTO convertFor(Tag tag) {
        return new TagOutputConvert().convert(tag);
    }

    protected class TagOutputConvert extends Converter<Tag, TagOutputDTO> {

        @Override
        protected TagOutputDTO doForward(Tag tag) {
            TagOutputDTO tagOutputDTO = new TagOutputDTO();
            try {
                BeanUtils.copyProperties(tag, tagOutputDTO);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return tagOutputDTO;
        }

        @Override
        protected Tag doBackward(TagOutputDTO tagOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
