package com.aviator.island.controller.desk.api;

import com.aviator.island.controller.desk.AbstractBaseController;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.output.BoardOutputDTO;
import com.aviator.island.entity.po.Board;
import com.aviator.island.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 版块管理
 * Created by aviator_ls on 2018/8/14.
 */
@RestController
@RequestMapping("/api/board")
public class BoardApiController extends AbstractBaseController {

    @Autowired
    private BoardService<Board> boardService;

    @GetMapping("/list/level/{level}")
    public ResponseContent listByLevel(@PathVariable int level) {
        List<Board> boardList = boardService.findListByLevel(level);
        List<BoardOutputDTO> result = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        return this.successResponseBody(result);
    }

    @GetMapping("/list/parentBoard/{parentBoardId}")
    public ResponseContent listByParentBoardId(@PathVariable String parentBoardId) {
        List<Board> boardList = boardService.findListByParentBoardId(parentBoardId);
        List<BoardOutputDTO> result = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        return this.successResponseBody(result);
    }

    @GetMapping("/list")
    public ResponseContent list() {
        List<Board> boardList = boardService.findAll();
        List<BoardOutputDTO> result = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        return this.successResponseBody(result);
    }

    @GetMapping("/leafList")
    public ResponseContent leafList() {
        List<Board> boardList = boardService.findLeafList();
        List<BoardOutputDTO> result = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        return this.successResponseBody(result);
    }

}
