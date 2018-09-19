package com.aviator.island.controller.desk.api;

import com.google.common.collect.Sets;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.CollectPostInputDTO;
import com.aviator.island.entity.dto.input.UserInputDTO;
import com.aviator.island.entity.dto.output.UserOutputDTO;
import com.aviator.island.entity.po.LoginLog;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.LoginLogService;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.RecordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 用户管理
 * Created by 18057046 on 2018/7/23.
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController extends AbstractBaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private LoginLogService<LoginLog> loginLogService;

    @PostMapping("/register")
    public ResponseContent register(@Valid @RequestBody UserInputDTO userInputDTO, BindingResult validResult) {
        User user = userInputDTO.converterTo();
        Serializable result = userService.register(user);
        if (result == null) {
            return this.failResponseBody(ResponseCode.USER_IS_EXIST);
        }
        return this.successResponseBody();
    }

    @PostMapping("/login")
    public ResponseContent login(@Valid @RequestBody UserInputDTO userInputDTO, BindingResult validResult) {
        User user = userInputDTO.converterTo();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            return this.failResponseBody(ResponseCode.USER_USERNAME_OR_PASSWORD_ERROR);
        } catch (LockedAccountException e) {
            return this.failResponseBody(ResponseCode.USER_IS_LOCK);
        } catch (ExcessiveAttemptsException e) {
            return this.failResponseBody(ResponseCode.LOGIN_MUCH_ERROR);
        } catch (AuthenticationException e) {
            return this.failResponseBody(ResponseCode.UNKNOWN_ERROR);
        }
        User dbUser = userService.getUserByUserName(user.getUserName());
        // 记录用户登录
        loginLogService.recordUserLogin(RecordUtil.recordUser(this.getSession().getHost(), dbUser));
        return this.successResponseBody();
    }

    @RequestMapping("/outLogin")
    public ResponseContent outLogin() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return this.successResponseBody();
    }

    @GetMapping("")
    public ResponseContent subject() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            String principal = (String) subject.getPrincipal();
            if (StringUtils.isNotBlank(principal)) {
                User user = userService.getUserByUserName(principal);
                if (user != null) {
                    UserOutputDTO userOutputDTO = new UserOutputDTO().convertFor(user);
                    return this.successResponseBody(userOutputDTO);
                }
            }
        }
        return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
    }

    @PostMapping("/collect/post")
    public ResponseContent collectPost(CollectPostInputDTO collectPostInputDTO) {
        List<Post> postList = collectPostInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        User user = userService.getUserByUserName(this.getPrincipal());
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        }
        user.setCollectPostSet(Sets.newHashSet(postList));
        userService.update(user);
        return this.successResponseBody();
    }

//    @PostMapping("/collect/ask")
//    public ResponseContent collectAsk() {
//
//    }

}
