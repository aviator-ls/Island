package com.aviator.island.entity.dto.input;

import com.google.common.base.Converter;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.exception.InputDTOConvertException;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Min;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/3.
 */
@Data
public class PageInputDTO extends BaseInputDTO<PageInputDTO, SearchPage> {

    private Map<String, Object> searchOrParams;

    private Map<String, Object> searchAndParams;

    @Min(value = 1, message = "当前页码不可小于1")
    private int pageNum;

    @Min(value = 1, message = "每页数据数不可小于1")
    private int pageSize;

    private String orderBy;

    private boolean isAsc;

    public <T> T getForSearchOrParams(String searchKey) {
        if (!CollectionUtils.isEmpty(searchOrParams)) {
            return (T) searchOrParams.get(searchKey);
        }
        return null;
    }

    public <T> T getForSearchAndParams(String searchKey) {
        if (!CollectionUtils.isEmpty(searchAndParams)) {
            return (T) searchAndParams.get(searchKey);
        }
        return null;
    }

    @Override
    public SearchPage converterTo() {
        return new PageInputConverter().convert(this);
    }

    protected class PageInputConverter extends Converter<PageInputDTO, SearchPage> {

        @Override
        protected SearchPage doForward(PageInputDTO pageInputDTO) {
            SearchPage searchPage = new SearchPage();
            try {
                BeanUtils.copyProperties(pageInputDTO, searchPage);
            } catch (Exception e) {
                throw new InputDTOConvertException(e);
            }
            return searchPage;
        }

        @Override
        protected PageInputDTO doBackward(SearchPage searchPage) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
