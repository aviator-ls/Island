package com.aviator.island.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 18057046 on 2018/8/29.
 */
@Controller
@RequestMapping("/back")
public class IndexBackController extends AbstractBackBaseController{
    @RequestMapping("")
    public ModelAndView home() {
        return this.responsePage("index");
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return this.responsePage("index");
    }
}
