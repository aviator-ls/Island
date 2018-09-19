package com.aviator.island.entity.dto.input;

import com.aviator.island.entity.BaseEntity;

/**
 * Created by 18057046 on 2018/8/13.
 */
public abstract class BaseInputDTO<S, T> extends BaseEntity {

    public abstract T converterTo();
}
