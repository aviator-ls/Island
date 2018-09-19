package com.aviator.island.entity.dto.output;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.po.Board;
import com.aviator.island.service.BoardService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 18057046 on 2018/8/14.
 */
public class BoardOutputDTOTest extends BaseJunit4Test{

    @Autowired
    private BoardService<Board> boardService;

    @Test
    public void convert(){
        Board board = new Board().setName("test").setDesc("test");
        String id = (String) boardService.save(board);
        board = boardService.get(id);
        BoardOutputDTO boardOutputDTO = new BoardOutputDTO().convertFor(board);
        System.out.println(boardOutputDTO);
    }
}
