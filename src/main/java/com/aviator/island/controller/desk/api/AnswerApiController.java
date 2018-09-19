package com.aviator.island.controller.desk.api;

import com.google.common.collect.Maps;
import com.aviator.island.annotation.UserBehavior;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.AnswerInputDTO;
import com.aviator.island.entity.dto.input.SimplePageInputDTO;
import com.aviator.island.entity.dto.output.AnswerOutputDTO;
import com.aviator.island.entity.po.Answer;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.AnswerService;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

/**
 * 问答回复
 * Created by 18057046 on 2018/9/19.
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerApiController extends AbstractBaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private AnswerService<Answer> answerService;

    @PostMapping("")
    @UserBehavior(credit = 1)
    public ResponseContent addAnswer(@Valid @RequestBody AnswerInputDTO answerInputDTO, BindingResult validResult) {
        Answer answer = answerInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        User user = userService.getUserByUserName(this.getPrincipal());
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        }
        answer.setAnswerUser(user);
        Serializable result = answerService.save(answer);
        if (result == null) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }

    @PostMapping("/askId/{askId}")
    public ResponseContent findPageListByAskId(@PathVariable("askId") String askId, @Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Map<String, Object> params = Maps.newHashMap();
        params.put("ask", new Ask().setId(askId));
        searchPage.setSearchAndParams(params);
        Page<Answer> answerPage = answerService.findPageListByCondition(searchPage);
        Page<AnswerOutputDTO> result = this.convertPageToOutputDTOPage(answerPage, new AnswerOutputDTO());
        return this.successResponseBody(result);
    }
}
