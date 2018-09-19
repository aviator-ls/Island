package com.aviator.island.entity;

import lombok.Data;

/**
 * Created by 18057046 on 2018/9/10.
 */
@Data
public class ResponseEntity<T> extends BaseEntity{

    private T data;

    private String msg;

    public ResponseEntity(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }
}
