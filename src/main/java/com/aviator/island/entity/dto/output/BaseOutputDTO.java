package com.aviator.island.entity.dto.output;

import com.aviator.island.entity.BaseEntity;

/**
 * Created by aviator_ls on 2018/8/14.
 */
public abstract class BaseOutputDTO<S, T> extends BaseEntity{

    public abstract T convertFor(S s);
}
