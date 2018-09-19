package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Board;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by aviator_ls on 2018/8/14.
 */
@Data
@Accessors(chain = true)
public class BoardOutputDTO extends BaseOutputDTO<Board, BoardOutputDTO> {
    private String id;

    private String name;// 版块名

    private String desc;// 版块描述

    private int level = 1;

    private String parentBoardId;

    private String parentBoardName;

    @Override
    public BoardOutputDTO convertFor(Board board) {
        return new BoardOutputConvert().convert(board);
    }

    protected class BoardOutputConvert extends Converter<Board, BoardOutputDTO> {

        @Override
        protected BoardOutputDTO doForward(Board board) {
            BoardOutputDTO boardOutputDTO = new BoardOutputDTO();
            try {
                BeanUtils.copyProperties(board, boardOutputDTO);
                Board parentBoard = board.getParentBoard();
                if (parentBoard != null) {
                    boardOutputDTO.setParentBoardId(parentBoard.getId());
                    boardOutputDTO.setParentBoardName(parentBoard.getName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return boardOutputDTO;
        }

        @Override
        protected Board doBackward(BoardOutputDTO boardOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
