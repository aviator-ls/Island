package com.aviator.island.controller.desk.api;

import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.po.PagePic;
import com.aviator.island.service.PagePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/index")
public class IndexApiController extends AbstractBaseController {

    @Autowired
    private PagePicService<PagePic> pagePicService;

    @GetMapping("/pic/carousel/list")
    public ResponseContent list() {
        List<PagePic> result = pagePicService.findIndexCarouselPicList();
        return this.successResponseBody(result);
    }
}
