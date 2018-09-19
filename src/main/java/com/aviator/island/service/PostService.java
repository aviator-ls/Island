package com.aviator.island.service;

import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.utils.Page;

/**
 * Created by aviator_ls on 2018/7/18.
 */
public interface PostService<T> extends BaseService<T> {
    /**
     * 查看文章，浏览数+1
     *
     * @param id
     * @return
     */
    Post view(String id);

    /**
     * 最新文章
     *
     * @param searchPage
     * @return
     */
    Page<T> findPageListNew(SearchPage searchPage);

    /**
     * 热门文章
     *
     * @param searchPage
     * @return
     */
    Page<T> findPageListHot(SearchPage searchPage);

    /**
     * 精品文章
     *
     * @param searchPage
     * @return
     */
    Page<Post> findPageListBoutique(SearchPage searchPage);

    /**
     * 根据关键词(文章标题)分页查询文章列表
     *
     * @param keyword
     * @param searchPage
     * @return
     */
    Page<Post> findPageListByKeyword(String keyword, SearchPage searchPage);
}
