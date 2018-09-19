package com.aviator.island.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 18057046 on 2018/9/4.
 */
@Controller
@RequestMapping("/")
public class HeaderController extends AbstractBaseController {
    @RequestMapping("/toLogin")
    public ModelAndView toLogin() {
        return this.responsePage("login");
    }

    @RequestMapping("/toRegister")
    public ModelAndView toRegister() {
        return this.responsePage("register");
    }

    @RequestMapping("/toAddPost")
    public ModelAndView toPost() {
        return this.responsePage("add_post");
    }

    @RequestMapping("/toAddAsk")
    public ModelAndView toAsk() {
        return this.responsePage("add_ask");
    }

    @RequestMapping("/toPostList")
    public ModelAndView toPostList() {
        return this.responsePage("post_list");
    }

    @RequestMapping("/toAskList")
    public ModelAndView toAskList() {
        return this.responsePage("ask_list");
    }
}
