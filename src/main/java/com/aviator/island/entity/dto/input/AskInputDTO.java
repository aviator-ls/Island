package com.aviator.island.entity.dto.input;

import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.exception.InputDTOConvertException;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.BoardService;
import com.aviator.island.service.TagService;
import com.aviator.island.utils.ComponentManager;
import com.google.common.base.Converter;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/24.
 */
@Data
@Accessors(chain = true)
public class AskInputDTO extends BaseInputDTO<AskInputDTO, Ask> {

    @NotBlank(message = "问题标题不可为空")
    private String title;

    private String content;

    @Min(value = 1, message = "问题类型错误")
    private int type;

    private String boardId;

    private Set<String> tagIdSet;

    @Override
    public Ask converterTo() {
        return new AskInputConverter().convert(this);
    }

    protected class AskInputConverter extends Converter<AskInputDTO, Ask> {

        @Override
        protected Ask doForward(AskInputDTO askInputDTO) {
            Ask ask = new Ask();
            try {
                askInputDTO.setTitle(askInputDTO.getTitle().trim());
                BeanUtils.copyProperties(askInputDTO, ask);
                if (StringUtils.isNotBlank(boardId)) {
                    Board board = (Board) ComponentManager.getComponent(BoardService.class).get(boardId);
                    if (board == null || !CollectionUtils.isEmpty(board.getChildBoardSet())) {
                        throw new ParamsErrorException("board is null or not leaf");
                    }
                    ask.setBoard(board);
                }
                if (!CollectionUtils.isEmpty(tagIdSet)) {
                    List<Tag> tagList = ComponentManager.getComponent(TagService.class).findListByIds(tagIdSet);
                    if (CollectionUtils.isEmpty(tagList) || tagList.size() != tagIdSet.size()) {
                        throw new ParamsErrorException("tag not exist");
                    }
                    Set<Tag> tagSet = Sets.newHashSet(tagList);
                    if (!CollectionUtils.isEmpty(tagSet)) {
                        ask.setTagSet(tagSet);
                    }
                }
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return ask;
        }

        @Override
        protected AskInputDTO doBackward(Ask ask) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
