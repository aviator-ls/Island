package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.Answer;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.exception.InputDTOConvertException;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.AskService;
import com.aviator.island.utils.ComponentManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * Created by aviator_ls on 2018/8/26.
 */
@Data
@Accessors(chain = true)
public class AnswerInputDTO extends BaseInputDTO<AnswerInputDTO, Answer> {

    @NotBlank(message = "回答内容不可为空")
    private String content;// 回答内容

    @NotBlank(message = "问题id不可为空")
    private String askId;// 问题id

    @Override
    public Answer converterTo() {
        return new AnswerInputConverter().convert(this);
    }

    protected class AnswerInputConverter extends Converter<AnswerInputDTO, Answer> {

        @Override
        protected Answer doForward(AnswerInputDTO answerInputDTO) {
            Answer answer = new Answer();
            try {
                BeanUtils.copyProperties(answerInputDTO, answer);
                AskService<Ask> askService = ComponentManager.getComponent(AskService.class);
                Ask ask = askService.get(askId);
                if (ask == null) {
                    throw new ParamsErrorException("ask not exist");
                }
                answer.setAsk(ask);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return answer;
        }

        @Override
        protected AnswerInputDTO doBackward(Answer answer) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
