package com.aviator.island.controller.desk.api;

import com.aviator.island.annotation.UserBehavior;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.PostInputDTO;
import com.aviator.island.entity.dto.input.SimplePageInputDTO;
import com.aviator.island.entity.dto.output.PostOutputDTO;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.PostService;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 帖子（文章）
 * Created by aviator_ls on 2018/8/3.
 */
@RestController
@RequestMapping("/api/post")
public class PostApiController extends AbstractBaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private PostService<Post> postService;

    @PostMapping("")
    @UserBehavior(credit = 5)
    public ResponseContent addPost(@Valid @RequestBody PostInputDTO postInputDTO, BindingResult validResult) {
        Post post = postInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        User user = this.getSessionUser();
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        post.setUser(user);
        Serializable result;
        try {
            result = postService.save(post);
        } catch (DataIntegrityViolationException e) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }

    @DeleteMapping("/{id}")
    public ResponseContent deletePost(@PathVariable("id") String id) {
        postService.remove(id);
        return this.successResponseBody();
    }

    @PutMapping("/{id}")
    public ResponseContent updatePost(@PathVariable("id") String id, @Valid @RequestBody PostInputDTO postInputDTO, BindingResult validResult) {
        Post post = postInputDTO.converterTo();
        post.setId(id);
        postService.update(post);
        return this.successResponseBody();
    }

    @GetMapping("/{id}")
    public ResponseContent getPost(@PathVariable String id) {
        Post post = postService.view(id);
        PostOutputDTO postOutputDTO = new PostOutputDTO().convertFor(post);
        return this.successResponseBody(postOutputDTO);
    }

    @PostMapping("/pageList/new")
    public ResponseContent findPageListNew(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Post> postPage = postService.findPageListNew(searchPage);
        Page<PostOutputDTO> result = this.convertPageToOutputDTOPage(postPage, new PostOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/hot")
    public ResponseContent findPageListHot(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Post> postPage = postService.findPageListHot(searchPage);
        Page<PostOutputDTO> result = this.convertPageToOutputDTOPage(postPage, new PostOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/boutique")
    public ResponseContent findPageListBoutique(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Post> postPage = postService.findPageListBoutique(searchPage);
        Page<PostOutputDTO> result = this.convertPageToOutputDTOPage(postPage, new PostOutputDTO());
        return this.successResponseBody(result);
    }

}
