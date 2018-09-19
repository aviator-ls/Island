package com.aviator.island.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/15.
 */
@Data
@Accessors(chain = true)
public class SearchPage {
    private Map<String, Object> searchOrParams;

    private Map<String, Object> searchAndParams;

    private int pageNum;

    private int pageSize;

    private String orderBy;

    private boolean isAsc;
}
