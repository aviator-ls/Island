package com.aviator.island.entity;

import lombok.Data;

/**
 * Created by aviator_ls on 2018/7/20.
 */
@Data
public class ResponseContent extends BaseEntity {
    private String responseCode;

    private String responseMsg;

    private Object data;

    public ResponseContent() {
    }

    public ResponseContent(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public ResponseContent(String responseCode, String responseMsg, Object data) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.data = data;
    }
}
