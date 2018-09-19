package com.aviator.island.controller.desk;

import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.sys.User;
import com.aviator.island.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/8/8.
 */
@Controller
@RequestMapping("/test")
public class TestController extends AbstractBaseController{

    @RequestMapping("")
    public ModelAndView toTest() {
        return this.responsePage("test");
    }

    @GetMapping("/ajax/get")
    @ResponseBody
    public ResponseContent testAjaxGet() {
        return this.successResponseBody();
    }

    @PostMapping("/ajax/post")
    @ResponseBody
    public ResponseContent testAjaxPost(@RequestBody User user) {
        return this.successResponseBody(user);
    }

    @PutMapping("/ajax/put")
    @ResponseBody
    public ResponseContent testAjaxPut(@RequestBody User user) {
        return this.successResponseBody(user);
    }

    @PostMapping("/ajax/path")
    @ResponseBody
    public ResponseContent testId(@RequestBody Board boardInputDTO){
        System.out.println(boardInputDTO);
        return this.successResponseBody();
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseContent testUpload(@RequestParam("file") MultipartFile file){
        UploadUtil.upload(file);
        return this.successResponseBody();
    }
}
