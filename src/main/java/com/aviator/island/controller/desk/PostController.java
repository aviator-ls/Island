package com.aviator.island.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/9/6.
 */
@Controller
@RequestMapping("/post")
public class PostController extends AbstractBaseController {

    @RequestMapping("/toPost/{id}")
    public ModelAndView toPost() {
        return this.responsePage("post");
    }
}
