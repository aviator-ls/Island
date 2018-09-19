package com.aviator.island.utils;

import com.aviator.island.entity.dto.output.BaseOutputDTO;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 18057046 on 2018/8/16.
 */
public class ConvertUtil {

    public static <S, T> List<T> convertCollectionToOutputDTOList(Collection<S> collection, BaseOutputDTO<S, T> outputDTO) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(po -> outputDTO.convertFor(po)).collect(Collectors.toList());
    }

    public static <S, T> Set<T> convertCollectionToOutputDTOSet(Collection<S> collection, BaseOutputDTO<S, T> outputDTO) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(po -> outputDTO.convertFor(po)).collect(Collectors.toSet());
    }

    public static <S, T> Page<T> convertPageToOutputDTOPage(Page<S> page, BaseOutputDTO<S, T> outputDTO) {
        if (page == null) {
            return null;
        }
        List<S> list = page.getData();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<T> result = convertCollectionToOutputDTOList(list, outputDTO);
        return new Page(page.getPageNum(), page.getPageSize(), result, page.getTotalCount());
    }
}
