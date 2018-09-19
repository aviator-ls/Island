package com.aviator.island.controller.desk.api;

import com.aviator.island.annotation.UserBehavior;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.AskInputDTO;
import com.aviator.island.entity.dto.input.SimplePageInputDTO;
import com.aviator.island.entity.dto.output.AskOutputDTO;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.AskService;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 问答
 * Created by aviator_ls on 2018/9/13.
 */
@RestController
@RequestMapping("/api/ask")
public class AskApiController extends AbstractBaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private AskService<Ask> askService;

    @GetMapping("/{id}")
    public ResponseContent getAsk(@PathVariable String id) {
        Ask ask = askService.get(id);
        AskOutputDTO askOutputDTO = new AskOutputDTO().convertFor(ask);
        return this.successResponseBody(askOutputDTO);
    }

    @PostMapping("")
    @UserBehavior(credit = 1)
    public ResponseContent addAsk(@Valid @RequestBody AskInputDTO askInputDTO, BindingResult validResult) {
        Ask ask = askInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        User user = userService.getUserByUserName(this.getPrincipal());
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_EXIST);
        }
        ask.setAskUser(user);
        Serializable result = askService.save(ask);
        if (result == null) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/recommend")
    public ResponseContent findPageListRecommend(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        // TODO: 2018/9/13 待实现问答推荐列表查询
        Page<Ask> askPage = askService.findPageListByCondition(searchPage);
        Page<AskOutputDTO> result = this.convertPageToOutputDTOPage(askPage, new AskOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/new")
    public ResponseContent findPageListNew(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Ask> askPage = askService.findPageListNew(searchPage);
        Page<AskOutputDTO> result = this.convertPageToOutputDTOPage(askPage, new AskOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/hot")
    public ResponseContent findPageListHot(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Ask> askPage = askService.findPageListHot(searchPage);
        Page<AskOutputDTO> result = this.convertPageToOutputDTOPage(askPage, new AskOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/pageList/resolved")
    public ResponseContent findPageListResolved(@Valid @RequestBody SimplePageInputDTO pageInputDTO, BindingResult validResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Ask> askPage = askService.findPageListResolved(searchPage);
        Page<AskOutputDTO> result = this.convertPageToOutputDTOPage(askPage, new AskOutputDTO());
        return this.successResponseBody(result);
    }
}
