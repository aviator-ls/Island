package com.aviator.island.service;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.po.Post;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by 18057046 on 2018/8/13.
 */
public class PostServiceTest extends BaseJunit4Test {
    @Autowired
    private PostService<Post> postService;

    @Test
    public void save(){
        Serializable id = postService.save(new Post().setTitle("aa"));
        System.out.println(id);
    }
}
