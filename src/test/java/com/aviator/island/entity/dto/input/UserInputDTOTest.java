package com.aviator.island.entity.dto.input;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


/**
 * Created by aviator_ls on 2018/8/13.
 */
public class UserInputDTOTest extends BaseJunit4Test{

    @Autowired
    private UserService<User> userService;

    @Test
    public void convert(){
        UserInputDTO userInputDTO = new UserInputDTO().setUserName("name").setPassword("ps");
        User user = userInputDTO.converterTo();
        String id = (String) userService.save(user);
        Assert.notNull(id, "id is null");
    }
}
