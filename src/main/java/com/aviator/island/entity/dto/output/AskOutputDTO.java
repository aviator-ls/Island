package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.sys.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by 18057046 on 2018/9/13.
 */
@Data
@Accessors(chain = true)
public class AskOutputDTO extends BaseOutputDTO<Ask, AskOutputDTO> {

    private String id;// 问题id

    private String title;

    private String content;

    private int type = 1;// 问题状态 1:未解决 2:已解决 3:已过期

    private Date createTime = new Date();// 发布时间

    private Date updateTime = new Date();// 更新时间

    private UserOutputDTO askUser;// 提问者

    private BoardOutputDTO board;

    @Override
    public AskOutputDTO convertFor(Ask ask) {
        return new AskOutputDTOConvert().convert(ask);
    }

    protected class AskOutputDTOConvert extends Converter<Ask, AskOutputDTO> {

        @Override
        protected AskOutputDTO doForward(Ask ask) {
            AskOutputDTO askOutputDTO = new AskOutputDTO();
            try {
                BeanUtils.copyProperties(ask, askOutputDTO);
                User user = ask.getAskUser();
                if (user != null) {
                    askOutputDTO.setAskUser(new UserOutputDTO().convertFor(user));
                }
                Board board = ask.getBoard();
                if (board != null) {
                    askOutputDTO.setBoard(new BoardOutputDTO().convertFor(board));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return askOutputDTO;
        }

        @Override
        protected Ask doBackward(AskOutputDTO askOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
