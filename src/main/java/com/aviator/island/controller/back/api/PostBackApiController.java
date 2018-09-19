package com.aviator.island.controller.back.api;

import com.aviator.island.controller.back.AbstractBackBaseController;
import com.aviator.island.entity.dto.output.BackPageOutputDTO;
import com.aviator.island.entity.dto.output.PostOutputDTO;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.service.PostService;
import com.aviator.island.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * Created by 18057046 on 2018/9/11.
 */
@RestController
@RequestMapping("/back/api/post")
public class PostBackApiController extends AbstractBackBaseController {

    @Autowired
    private PostService<Post> postService;

    @GetMapping("/pageList")
    public BackPageOutputDTO list(@RequestParam("keyword") String keyword, @Min(value = 1, message = "当前页码不可小于1") @RequestParam("page") Integer pageNum, @Min(value = 1, message = "每页数据数不可小于1") @RequestParam("limit") Integer pageSize) {
        SearchPage searchPage = new SearchPage().setPageNum(pageNum).setPageSize(pageSize);
        Page<Post> page = postService.findPageListByKeyword(keyword, searchPage);
        BackPageOutputDTO result = new BackPageOutputDTO(new PostOutputDTO()).convertFor(page);
        return result;
    }
}
