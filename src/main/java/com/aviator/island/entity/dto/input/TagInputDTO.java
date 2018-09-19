package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.entity.sys.User;
import com.aviator.island.exception.InputDTOConvertException;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
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

    @NotBlank(message = "创建者id不可为空")
    private String createUserId;

    @NotBlank(message = "更新者id不可为空")
    private String updateUserId;

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
                UserService<User> userService = ComponentManager.getComponent(UserService.class);
                if (StringUtils.isNotBlank(createUserId)) {
                    User createUser = userService.get(createUserId);
                    if (createUser == null) {
                        throw new ParamsErrorException("createUser not exist");
                    }
                    tag.setCreateUser(createUser);
                }
                if (StringUtils.isNotBlank(updateUserId)) {
                    User updateUser = userService.get(updateUserId);
                    if (updateUser == null) {
                        throw new ParamsErrorException("updateUser not exist");
                    }
                    tag.setUpdateUser(updateUser);
                }
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
