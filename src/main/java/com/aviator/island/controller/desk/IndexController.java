package com.aviator.island.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/7/20.
 */
@Controller
@RequestMapping("/")
public class IndexController extends AbstractBaseController {

    @RequestMapping("")
    public ModelAndView toIndex() {
        return this.responsePage("index");
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return this.responsePage("index");
    }
}
