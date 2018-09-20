package com.aviator.island.controller.desk.api;

import com.aviator.island.annotation.UserBehavior;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.ReplyPostInputDTO;
import com.aviator.island.entity.dto.input.SimplePageInputDTO;
import com.aviator.island.entity.dto.output.ReplyPostOutputDTO;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.ReplyPost;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.ReplyPostService;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.Page;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

/**
 * 帖子（文章）回复
 * Created by aviator_ls on 2018/8/16.
 */
@RestController
@RequestMapping("/api/replyPost")
public class ReplyPostApiController extends AbstractBaseController {

    @Autowired
    private ReplyPostService<ReplyPost> replyPostService;

    @Autowired
    private UserService<User> userService;

    @PostMapping("")
    @UserBehavior(credit = 1)
    public ResponseContent addReplyPost(@Valid @RequestBody ReplyPostInputDTO replyPostInputDTO, BindingResult validResult) {
        ReplyPost replyPost = replyPostInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        User user = this.getSessionUser();
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        replyPost.setUser(user);
        Serializable result;
        try {
            result = replyPostService.save(replyPost);
        } catch (DataIntegrityViolationException e) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }

    @DeleteMapping("/{id}")
    public ResponseContent deleteReplyPost(@PathVariable("id") String id) {
        replyPostService.remove(id);
        return this.successResponseBody();
    }

    @PutMapping("/{id}")
    public ResponseContent updatePost(@PathVariable("id") String id, @RequestBody ReplyPostInputDTO replyPostInputDTO, BindingResult validResult) {
        ReplyPost replyPost = replyPostInputDTO.converterTo();
        replyPost.setId(id);
        replyPostService.update(replyPost);
        return this.successResponseBody();
    }

    @PostMapping("/postId/{id}")
    public ResponseContent findPageListByPostId(@PathVariable("id") String id, @Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Map<String, Object> params = Maps.newHashMap();
        params.put("mainPost", new Post().setId(id));
        searchPage.setSearchAndParams(params);
        Page<ReplyPost> replyPostPage = replyPostService.findPageListByCondition(searchPage);
        Page<ReplyPostOutputDTO> result = this.convertPageToOutputDTOPage(replyPostPage, new ReplyPostOutputDTO());
        return this.successResponseBody(result);
    }

}
