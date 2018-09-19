package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.exception.InputDTOConvertException;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;

/**
 * Created by 18057046 on 2018/9/7.
 */
@Data
public class SimplePageInputDTO extends BaseInputDTO<SimplePageInputDTO, SearchPage> {
    @Min(value = 1, message = "当前页码不可小于1")
    private int pageNum;

    @Min(value = 1, message = "每页数据数不可小于1")
    private int pageSize;

    private String orderBy;

    private boolean isAsc;

    @Override
    public SearchPage converterTo() {
        return new SimplePageInputDTOConverter().convert(this);
    }

    protected class SimplePageInputDTOConverter extends Converter<SimplePageInputDTO, SearchPage> {

        @Override
        protected SearchPage doForward(SimplePageInputDTO simplePageInputDTO) {
            SearchPage searchPage = new SearchPage();
            try {
                BeanUtils.copyProperties(simplePageInputDTO, searchPage);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return searchPage;
        }

        @Override
        protected SimplePageInputDTO doBackward(SearchPage searchPage) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
