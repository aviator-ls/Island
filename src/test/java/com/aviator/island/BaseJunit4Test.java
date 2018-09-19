package com.aviator.island;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by 18057046 on 2018/7/17.
 */
@RunWith(SpringRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath:conf/spring/spring.xml", "classpath:conf/spring/spring-mvc.xml"})
@Transactional
@Rollback
@WebAppConfiguration
public class BaseJunit4Test implements InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(BaseJunit4Test.class);

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Override
    public void afterPropertiesSet() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
}
