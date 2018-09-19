package com.aviator.island.entity.dto.input;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.BoardService;
import com.aviator.island.service.PostService;
import com.aviator.island.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by 18057046 on 2018/8/13.
 */
public class PostInputDTOTest extends BaseJunit4Test {

    @Autowired
    private PostService<Post> postService;

    @Autowired
    private UserService<User> userService;

    @Autowired
    private BoardService<Board> boardService;

    @Test
    public void convert() {
        String userId = (String) userService.save(new User().setUserName("20180814").setPassword("20180814"));
        User user = userService.get(userId);
        PostInputDTO postInputDTO = new PostInputDTO().setTitle("title").setContent("content");
        Post post = postInputDTO.converterTo();
        post.setUser(user);
        postService.save(post);
        List<Post> postList = postService.findAll();
        Assert.notEmpty(postList, "result list empty");
    }
}
