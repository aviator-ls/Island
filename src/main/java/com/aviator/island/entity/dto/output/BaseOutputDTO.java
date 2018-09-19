package com.aviator.island.entity.dto.output;

import com.aviator.island.entity.BaseEntity;

/**
 * Created by 18057046 on 2018/8/14.
 */
public abstract class BaseOutputDTO<S, T> extends BaseEntity{

    public abstract T convertFor(S s);
}
