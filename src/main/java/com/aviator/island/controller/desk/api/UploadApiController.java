package com.aviator.island.controller.desk.api;

import com.aviator.island.constants.Constants;
import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.utils.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by aviator_ls on 2018/9/5.
 */
@RestController
@RequestMapping("/api/upload")
public class UploadApiController extends AbstractBaseController {

    @PostMapping("/{classify}")
    public ResponseContent upload(@RequestParam("file") MultipartFile file, @PathVariable String classify) {
        String storePath = UploadUtil.upload(file, classify);
        return this.successResponseBody(storePath);
    }

    @PostMapping("/postContent")
    public ResponseContent upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String serverPath = this.getRealPath(request, "upload");
        String storePath = UploadUtil.upload(file, serverPath, Constants.UPLOAD_CLASSIFY.IMG.POST_CONTENT_IMG);
        return this.successResponseBody(storePath);
    }
}
