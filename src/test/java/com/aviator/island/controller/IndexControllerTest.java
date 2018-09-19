package com.aviator.island.controller;

import com.aviator.island.BaseJunit4Test;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 18057046 on 2018/7/20.
 */
public class IndexControllerTest extends BaseJunit4Test {

    @Test
    public void index(){
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/index");
        try {
            ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
            ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
            logger.info("index viewName:" + modelAndView.getViewName());
        } catch (Exception e) {
            logger.error("请求index失败", e);
        }
    }
}
