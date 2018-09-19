package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Answer;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.sys.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by 18057046 on 2018/9/19.
 */
@Data
@Accessors(chain = true)
public class AnswerOutputDTO extends BaseOutputDTO<Answer, AnswerOutputDTO> {

    private String id;// 回答id

    private String content;// 回答内容

    private Date createTime = new Date();// 发布时间

    private Date updateTime = new Date();// 更新时间

    private AskOutputDTO ask;// 问题

    private UserOutputDTO answerUser;// 解答者

    @Override
    public AnswerOutputDTO convertFor(Answer answer) {
        return new AnswerOutputDTOConvert().convert(answer);
    }

    protected class AnswerOutputDTOConvert extends Converter<Answer, AnswerOutputDTO> {

        @Override
        protected AnswerOutputDTO doForward(Answer answer) {
            AnswerOutputDTO answerOutputDTO = new AnswerOutputDTO();
            try {
                BeanUtils.copyProperties(answer, answerOutputDTO);
                User user = answer.getAnswerUser();
                if (user != null) {
                    answerOutputDTO.setAnswerUser(new UserOutputDTO().convertFor(user));
                }
                Ask ask = answer.getAsk();
                if (ask != null) {
                    answerOutputDTO.setAsk(new AskOutputDTO().convertFor(ask));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return answerOutputDTO;
        }

        @Override
        protected Answer doBackward(AnswerOutputDTO answerOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
