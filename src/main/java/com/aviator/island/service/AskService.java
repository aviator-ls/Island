package com.aviator.island.service;

import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.utils.Page;

/**
 * Created by 18057046 on 2018/8/26.
 */
public interface AskService<T> extends BaseService<T> {

    /**
     * 最新问答
     *
     * @param searchPage
     * @return
     */
    Page<Ask> findPageListNew(SearchPage searchPage);

    /**
     * 热门问答
     *
     * @param searchPage
     * @return
     */
    Page<Ask> findPageListHot(SearchPage searchPage);

    /**
     * 已解决问答
     *
     * @param searchPage
     * @return
     */
    Page<Ask> findPageListResolved(SearchPage searchPage);
}
