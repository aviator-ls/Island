package com.aviator.island.entity.dto.output;

import com.google.common.base.Converter;
import com.aviator.island.utils.Page;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aviator_ls on 2018/8/30.
 */
@Data
public class BackPageOutputDTO extends BaseOutputDTO<Page, BackPageOutputDTO> {

    private int count;

    private String msg;

    private List<? extends BaseOutputDTO> data;

    private transient BaseOutputDTO convertTo;

    private int code = 0;

    public BackPageOutputDTO(BaseOutputDTO convertTo) {
        this.convertTo = convertTo;
    }

    @Override
    public BackPageOutputDTO convertFor(Page page) {
        return new BackPageOutputConvert().convert(page);
    }

    protected class BackPageOutputConvert extends Converter<Page, BackPageOutputDTO> {

        @Override
        protected BackPageOutputDTO doForward(Page page) {
            BackPageOutputDTO backPageOutputDTO = new BackPageOutputDTO(convertTo);
            try {
                backPageOutputDTO.setCount(Integer.parseInt(String.valueOf(page.getTotalCount())));
                List list = page.getData();
                List<? extends BaseOutputDTO> convertedList = null;
                if (!CollectionUtils.isEmpty(list)) {
                    convertedList = (List<? extends BaseOutputDTO>) list.stream().map(po -> convertTo.convertFor(po)).collect(Collectors.toList());
                }
                backPageOutputDTO.setData(convertedList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return backPageOutputDTO;
        }

        @Override
        protected Page doBackward(BackPageOutputDTO backPageOutputDTO) {
            throw new AssertionError("don't support doBackward converter");
        }
    }
}
