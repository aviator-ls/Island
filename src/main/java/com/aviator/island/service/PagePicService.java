package com.aviator.island.service;

import com.aviator.island.entity.po.PagePic;

import java.util.List;

public interface PagePicService<T> extends BaseService<T> {
    List<PagePic> findIndexCarouselPicList();
}
