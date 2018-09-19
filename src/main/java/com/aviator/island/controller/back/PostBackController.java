package com.aviator.island.controller.back;

import com.aviator.island.entity.po.Board;
import com.aviator.island.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 18057046 on 2018/9/11.
 */
@Controller
@RequestMapping("/back/post")
public class PostBackController extends AbstractBackBaseController {
    @Autowired
    private BoardService<Board> boardService;

    @RequestMapping("/toList")
    public ModelAndView toList() {
        return this.responsePage("/post_list");
    }
}
