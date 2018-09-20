package com.aviator.island.controller.desk.api;

import com.aviator.island.constants.ResponseCode;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.input.PageInputDTO;
import com.aviator.island.entity.dto.input.TagInputDTO;
import com.aviator.island.entity.dto.output.TagOutputDTO;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.TagService;
import com.aviator.island.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 标签管理
 * Created by aviator_ls on 2018/8/15.
 */
@RestController
@RequestMapping("/api/tag")
public class TagApiController extends AbstractBaseController {

    @Autowired
    private TagService<Tag> tagService;

    @PostMapping("")
    public ResponseContent addTag(@Valid @RequestBody TagInputDTO tagInputDTO, BindingResult bindingResult) {
        Tag tag = tagInputDTO.converterTo();
        // 得到当前登录用户,作为创建用户
        User user = this.getSessionUser();
        if (user == null) {
            return this.failResponseBody(ResponseCode.USER_NOT_LOGIN);
        }
        tag.setCreateUser(user);
        tag.setUpdateUser(user);
        Serializable result;
        try {
            result = tagService.save(tag);
        } catch (DataIntegrityViolationException e) {
            return this.failResponseBody(ResponseCode.ADD_DATA_IS_EXIST);
        }
        return this.successResponseBody(result);
    }

    @DeleteMapping("/{id}")
    public ResponseContent deleteTag(@PathVariable("id") String id) {
        tagService.remove(id);
        return this.successResponseBody();
    }

    @PutMapping("/{id}")
    public ResponseContent updateTag(@PathVariable("id") String id, @Valid @RequestBody TagInputDTO tagInputDTO, BindingResult bindingResult) {
        Tag tag = tagInputDTO.converterTo();
        tag.setId(id);
        tagService.update(tag);
        return this.successResponseBody();
    }

    @GetMapping("/{id}")
    public ResponseContent getTag(@PathVariable String id) {
        Tag tag = tagService.get(id);
        return this.successResponseBody(tag);
    }

    @GetMapping("/list")
    public ResponseContent list() {
        List<Tag> tagList = tagService.findAll();
        List<TagOutputDTO> result = this.convertListToOutputDTOList(tagList, new TagOutputDTO());
        return this.successResponseBody(result);
    }

    @PostMapping("/tagPageList")
    public ResponseContent findPageListByCondition(@Valid @RequestBody PageInputDTO pageInputDTO, BindingResult bindingResult) {
        SearchPage searchPage = pageInputDTO.converterTo();
        Page<Tag> tagPage = tagService.findPageListByCondition(searchPage);
        Page<TagOutputDTO> result = this.convertPageToOutputDTOPage(tagPage, new TagOutputDTO());
        return this.successResponseBody(result);
    }

}
