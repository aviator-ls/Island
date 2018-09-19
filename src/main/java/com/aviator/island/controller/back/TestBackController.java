package com.aviator.island.controller.back;

import com.aviator.island.entity.sys.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/8/31.
 */
@Controller
@RequestMapping("/back/test")
public class TestBackController extends AbstractBackBaseController {

    @RequestMapping("/{name}/{nickName}")
    public ModelAndView test(@PathVariable String name, @PathVariable String nickName) {
        User user = new User();
        user.setNickName(nickName);
        user.setUserName(name);
        return this.responsePage("test", user);
    }
}
