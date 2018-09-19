package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.sys.User;
import com.aviator.island.exception.InputDTOConvertException;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by 18057046 on 2018/8/13.
 */
@Data
@Accessors(chain = true)
public class UserInputDTO extends BaseInputDTO<UserInputDTO, User> {

    @NotBlank(message = "用户名不可为空")
    @Size(min = 5, max = 20, message = "用户名长度应在5到20之间")
    private String userName;

    @NotBlank(message = "密码不可为空")
    @Size(min = 6, max = 20, message = "密码长度应在6到20之间")
    private String password;

    private String nickName;

    private String profilePicture;// 头像

    @Override
    public User converterTo() {
        return new UserInputConverter().convert(this);
    }

    protected class UserInputConverter extends Converter<UserInputDTO, User> {

        @Override
        protected User doForward(UserInputDTO userInputDTO) {
            User user = new User();
            if (StringUtils.isBlank(nickName)) {
                nickName = userName;
            }
            try {
                BeanUtils.copyProperties(userInputDTO, user);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return user;
        }

        @Override
        protected UserInputDTO doBackward(User user) {
            throw new AssertionError("don't support doBackward converter");
        }
    }

}
