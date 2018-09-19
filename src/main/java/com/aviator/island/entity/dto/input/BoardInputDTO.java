package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Board;
import com.aviator.island.exception.InputDTOConvertException;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.BoardService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;

/**
 * Created by 18057046 on 2018/8/14.
 */
@Data
@Accessors(chain = true)
public class BoardInputDTO extends BaseInputDTO<BoardInputDTO, Board> {

    @NotBlank(message = "版块名不可为空")
    private String name;// 版块名

    private String desc;// 版块描述

    @Range(min = 1, max = 2, message = "版块最多只能有两级且不可小于一级")
    private int level = 1;

    private String parentBoardId;// 父版块id

    @Override
    public Board converterTo() {
        return new BoardInputConverter().convert(this);
    }

    protected class BoardInputConverter extends Converter<BoardInputDTO, Board> {

        @Override
        protected Board doForward(BoardInputDTO boardInputDTO) {
            Board board = new Board();
            try {
                BeanUtils.copyProperties(boardInputDTO, board);
                if (level > 1 && StringUtils.isBlank(parentBoardId)) {
                    throw new ParamsErrorException("level gt 1 and parent board id null");
                }
                if (StringUtils.isNotBlank(parentBoardId)) {
                    Board parentBoard = (Board) ComponentManager.getComponent(BoardService.class).get(parentBoardId);
                    if (parentBoard == null) {
                        throw new ParamsErrorException("parent board not exist");
                    }
                    if (!CollectionUtils.isEmpty(parentBoard.getChildBoardSet())) {
                        throw new ParamsErrorException("parent board not leaf");
                    }
                    board.setParentBoard(parentBoard);
                }
            } catch (ParamsErrorException e) {
                throw new ParamsErrorException(e);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return board;
        }

        @Override
        protected BoardInputDTO doBackward(Board board) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
