package com.aviator.island.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/9/13.
 */
@Controller
@RequestMapping("/ask")
public class AskController extends AbstractBaseController {

    @RequestMapping("/toAsk/{id}")
    public ModelAndView toAsk() {
        return this.responsePage("ask");
    }
}
