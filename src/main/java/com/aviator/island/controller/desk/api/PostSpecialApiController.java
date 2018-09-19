package com.aviator.island.controller.desk.api;

import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.PostSpecialInputDTO;
import com.aviator.island.entity.dto.output.PostSpecialOutputDTO;
import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.PostSpecialService;
import com.aviator.island.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 文章专辑
 * Created by 18057046 on 2018/9/12.
 */
@RestController
@RequestMapping("/api/postSpecial")
public class PostSpecialApiController extends AbstractBaseController {
    @Autowired
    private UserService<User> userService;

    @Autowired
    private PostSpecialService<PostSpecial> postSpecialService;

    @GetMapping("/list/user")
    public ResponseContent findListByUserId() {
        // 得到当前登录用户,作为创建用户
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        User user = userService.getUserByUserName(this.getPrincipal());
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        }
        List<PostSpecial> postSpecialList = postSpecialService.findListByUser(user);
        List<PostSpecialOutputDTO> result = this.convertListToOutputDTOList(postSpecialList, new PostSpecialOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("")
    public ResponseContent add(@Valid @RequestBody PostSpecialInputDTO postSpecialInputDTO, BindingResult validResult){
        PostSpecial postSpecial = postSpecialInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        User user = userService.getUserByUserName(this.getPrincipal());
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        }
        postSpecial.setUser(user);
        Serializable result = postSpecialService.save(postSpecial);
        if (result == null) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }
}
