package com.aviator.island.utils;

import java.util.UUID;

/**
 * Created by aviator_ls on 2018/8/2.
 */
public class UUIDGenerator {

    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(createUUID());
    }
}
