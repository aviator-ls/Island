package com.aviator.island.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by aviator_ls on 2018/8/2.
 */
public class SerializableUtil {

    public static String simpleSerialize(Object obj){
        return JSON.toJSONString(obj);
    }

    public static <T> T simpleDeserialize(String str, Class<T> requiredType){
        return JSON.parseObject(str, requiredType);
    }
}
