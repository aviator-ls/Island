package com.aviator.island.controller.back;

import com.aviator.island.entity.dto.output.BaseOutputDTO;
import com.aviator.island.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aviator_ls on 2018/8/29.
 */
public abstract class AbstractBackBaseController {

    protected ModelAndView responsePage(String viewName, Map<String, Object> params) {
        ModelAndView modelAndView = new ModelAndView();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> modelAndView.addObject(k, v));
        }
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected ModelAndView responsePage(String viewName) {
        return responsePage(viewName, null, null);
    }

    protected ModelAndView responsePage(String viewName, String msg) {
        return responsePage(viewName, null, msg);
    }

    protected <T> ModelAndView responsePage(String viewName, T data) {
        return responsePage(viewName, data, null);
    }

    protected <T> ModelAndView responsePage(String viewName, T data, String msg) {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isNotBlank(msg)) {
            modelAndView.addObject("msg", msg);
        }
        if (data != null) {
            modelAndView.addObject("data", data);
        }
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected <S, T> List<T> convertListToOutputDTOList(List<S> list, BaseOutputDTO<S, T> outputDTO) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(po -> outputDTO.convertFor(po)).collect(Collectors.toList());
    }

    protected <S, T> Page<T> convertPageToOutputDTOPage(Page<S> page, BaseOutputDTO<S, T> outputDTO) {
        if (page == null) {
            return null;
        }
        List<S> list = page.getData();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<T> result = this.convertListToOutputDTOList(list, outputDTO);
        return new Page(page.getPageNum(), page.getPageSize(), result, page.getTotalCount());
    }

}
