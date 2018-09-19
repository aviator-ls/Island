package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.sys.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by 18057046 on 2018/9/6.
 */
@Data
@Accessors(chain = true)
public class UserOutputDTO extends BaseOutputDTO<User, UserOutputDTO> {

    private String userName;// 用户名

    private String nickName;// 昵称

    private int type = 1;// 1:普通用户,2:管理员

    private int locked;// 0:未锁定,1:锁定

    private int credit;// 积分

    private String profilePicture;// 头像

    @Override
    public UserOutputDTO convertFor(User user) {
        return new UserOutputDTOConvert().convert(user);
    }

    protected class UserOutputDTOConvert extends Converter<User, UserOutputDTO> {

        @Override
        protected UserOutputDTO doForward(User user) {
            UserOutputDTO userOutputDTO = new UserOutputDTO();
            try {
                BeanUtils.copyProperties(user, userOutputDTO);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return userOutputDTO;
        }

        @Override
        protected User doBackward(UserOutputDTO userOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }

}
