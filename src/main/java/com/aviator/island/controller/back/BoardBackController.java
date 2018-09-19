package com.aviator.island.controller.back;

import com.google.common.collect.Maps;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.entity.dto.input.BoardInputDTO;
import com.aviator.island.entity.dto.output.BoardOutputDTO;
import com.aviator.island.entity.po.Board;
import com.aviator.island.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/30.
 */
@Controller
@RequestMapping("/back/board")
public class BoardBackController extends AbstractBackBaseController {
    @Autowired
    private BoardService<Board> boardService;

    @RequestMapping("/toAdd")
    public ModelAndView toAddBoard() {
        List<Board> parentBoardList = boardService.findParentBoardList();
        List<BoardOutputDTO> parentBoardOutputList = this.convertListToOutputDTOList(parentBoardList, new BoardOutputDTO());
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentBoardList", parentBoardOutputList);
        return this.responsePage("/add_board", map);
    }

    @PostMapping("")
    public ModelAndView add(@Valid BoardInputDTO boardInputDTO, BindingResult validResult) {
        List<Board> boardList = boardService.findParentBoardList();
        List<BoardOutputDTO> result = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        if (validResult.hasErrors()) {
            ObjectError error = validResult.getAllErrors().get(0);
            Map<String, Object> map = Maps.newHashMap();
            map.put("boardInput", boardInputDTO);
            map.put("parentBoardList", result);
            map.put("msg", error.getDefaultMessage());
            return this.responsePage("/add_board", map);
        }
        Board board = boardInputDTO.converterTo();
        Serializable id = boardService.save(board);
        // 若新增失败，返回新增页面
        if (id == null) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("boardInput", boardInputDTO);
            map.put("parentBoardList", result);
            map.put("msg", ResponseCode.ADD_DATA_IS_EXIST.getMsg());
            return this.responsePage("/add_board", map);
        }
        return this.responsePage("/board_list");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBoard(@PathVariable("id") String id) {
        boardService.remove(id);
    }

    @GetMapping("/toUpdate/{id}")
    public ModelAndView toUpdate(@PathVariable("id") String id) {
        Board board = boardService.get(id);
        BoardOutputDTO boardOutputDTO = new BoardOutputDTO().convertFor(board);
        List<Board> boardList = boardService.findParentBoardList();
        List<BoardOutputDTO> parentBoardList = this.convertListToOutputDTOList(boardList, new BoardOutputDTO());
        Map<String, Object> map = Maps.newHashMap();
        map.put("board", boardOutputDTO);
        map.put("parentBoardList", parentBoardList);
        return this.responsePage("/update_board", map);
    }

    @PutMapping("/{id}")
    public ModelAndView update(@PathVariable("id") String id, @Valid BoardInputDTO boardInputDTO, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return this.responsePage("redirect:/back/board_list", validResult.getAllErrors().get(0).getDefaultMessage());
        }
        Board board = boardInputDTO.converterTo();
        board.setId(id);
        boardService.update(board);
        return this.responsePage("redirect:/back/board_list");
    }

    @RequestMapping("/toList")
    public ModelAndView toList() {
        return this.responsePage("/board_list");
    }
}
