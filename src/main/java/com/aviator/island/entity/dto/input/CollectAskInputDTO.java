package com.aviator.island.entity.dto.input;

import com.aviator.island.entity.po.Ask;
import com.aviator.island.exception.ParamsErrorException;
import com.aviator.island.service.AskService;
import com.aviator.island.utils.ComponentManager;
import com.google.common.base.Converter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * Created by 18057046 on 2018/9/20.
 */
@Data
@Accessors(chain = true)
public class CollectAskInputDTO extends BaseInputDTO<CollectAskInputDTO, List<Ask>> {
    @NotEmpty
    Set<String> askIdSet;

    @Override
    public List<Ask> converterTo() {
        return new CollectAskInputConverter().convert(this);
    }

    protected class CollectAskInputConverter extends Converter<CollectAskInputDTO, List<Ask>> {

        @Override
        protected List<Ask> doForward(CollectAskInputDTO collectAskInputDTO) {
            for (String askId : askIdSet) {
                if (StringUtils.isBlank(askId)) {
                    throw new ParamsErrorException("askId is blank");
                }
            }
            AskService<Ask> askService = ComponentManager.getComponent(AskService.class);
            List<Ask> result = askService.findListByIds(askIdSet);
            if (CollectionUtils.isEmpty(result) || result.size() != askIdSet.size()) {
                throw new ParamsErrorException("askList by idSet is not exist");
            }
            return result;
        }

        @Override
        protected CollectAskInputDTO doBackward(List<Ask> list) {
            throw new AssertionError("don't support doBackward converter");
        }
    }

}
