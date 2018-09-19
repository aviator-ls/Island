package com.aviator.island.validator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Created by aviator_ls on 2018/8/18.
 */
@Data
@AllArgsConstructor
public class Student {
    @NotBlank
    private String name;
    @Min(7)
    private int age;
}
