package com.aviator.island.entity.dto.input;

import com.aviator.island.entity.sys.User;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.ComponentManager;
import com.google.common.base.Converter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * Created by 18057046 on 2018/9/20.
 */
@Data
@Accessors(chain = true)
public class FollowUserInputDTO extends BaseInputDTO<FollowUserInputDTO, List<User>> {
    @NotEmpty
    Set<String> userIdSet;

    @Override
    public List<User> converterTo() {
        return new FollowUserInputConverter().convert(this);
    }

    protected class FollowUserInputConverter extends Converter<FollowUserInputDTO, List<User>> {

        @Override
        protected List<User> doForward(FollowUserInputDTO followUserInputDTO) {
            Set<String> userIdSet = followUserInputDTO.getUserIdSet();
            for (String userId : userIdSet) {
                if (StringUtils.isBlank(userId)) {
                    throw new ParamsErrorException("userId is blank");
                }
            }
            UserService<User> userService = ComponentManager.getComponent(UserService.class);
            List<User> result = userService.findListByIds(userIdSet);
            if (CollectionUtils.isEmpty(result) || result.size() != userIdSet.size()) {
                throw new ParamsErrorException("userList by idSet is not exist");
            }
            return result;
        }

        @Override
        protected FollowUserInputDTO doBackward(List<User> list) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
