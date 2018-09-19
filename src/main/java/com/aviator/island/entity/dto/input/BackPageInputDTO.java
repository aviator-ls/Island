package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.SearchPage;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by 18057046 on 2018/8/30.
 */
@Data
public class BackPageInputDTO extends BaseInputDTO<BackPageInputDTO, SearchPage> {

    @Min(value = 1, message = "当前页码不可小于1")
    private int page;

    @Min(value = 1, message = "每页数据数不可小于1")
    private int limit;

    @Override
    public SearchPage converterTo() {
        return new BackPageInputConverter().convert(this);
    }

    protected class BackPageInputConverter extends Converter<BackPageInputDTO, SearchPage> {

        @Override
        protected SearchPage doForward(BackPageInputDTO backPageInputDTO) {
            SearchPage searchPage = new SearchPage();
            searchPage.setPageNum(page);
            searchPage.setPageSize(limit);
            return searchPage;
        }

        @Override
        protected BackPageInputDTO doBackward(SearchPage searchPage) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
