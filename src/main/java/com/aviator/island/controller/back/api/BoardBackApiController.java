package com.aviator.island.controller.back.api;

import com.aviator.island.controller.back.AbstractBackBaseController;
import com.aviator.island.entity.dto.output.BackPageOutputDTO;
import com.aviator.island.entity.dto.output.BoardOutputDTO;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.service.BoardService;
import com.aviator.island.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * Created by 18057046 on 2018/8/30.
 */
@RestController
@RequestMapping("/back/api/board")
public class BoardBackApiController extends AbstractBackBaseController {

    @Autowired
    private BoardService<Board> boardService;

    @GetMapping("/pageList")
    public BackPageOutputDTO list(@RequestParam("keyword") String keyword, @Min(value = 1, message = "当前页码不可小于1") @RequestParam("page") Integer pageNum, @Min(value = 1, message = "每页数据数不可小于1") @RequestParam("limit") Integer pageSize) {
        SearchPage searchPage = new SearchPage().setPageNum(pageNum).setPageSize(pageSize);
        Page<Board> page = boardService.findPageListByKeyword(keyword, searchPage);
        BackPageOutputDTO result = new BackPageOutputDTO(new BoardOutputDTO()).convertFor(page);
        return result;
    }
}
