package com.aviator.island.service;

import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.utils.Page;

import java.util.List;

/**
 * Created by 18057046 on 2018/7/18.
 */
public interface BoardService<T> extends BaseService<T> {

    /**
     * 根据层级数查询版块
     *
     * @param level
     * @return
     */
    List<T> findListByLevel(int level);

    /**
     * 根据父版块id查询版块列表
     *
     * @param parentBoardId
     * @return
     */
    List<T> findListByParentBoardId(String parentBoardId);

    /**
     * 查询叶子版块(无子版块)
     *
     * @return
     */
    List<Board> findLeafList();

    /**
     * 查询所有可作为父版块的版块列表
     *
     * @return
     */
    List<Board> findParentBoardList();

    /**
     * 根据关键词(版块名)分页查询版块列表
     *
     * @param keyword
     * @param searchPage
     * @return
     */
    Page<Board> findPageListByKeyword(String keyword, SearchPage searchPage);

}
