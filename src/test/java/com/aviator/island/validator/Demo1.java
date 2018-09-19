package com.aviator.island.validator;

import com.aviator.island.validator.entity.Student;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by 18057046 on 2018/8/18.
 */
public class Demo1 {

    private static Validator validator;

    @BeforeClass
    public static void before(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void nameIsBlank(){
        Student student = new Student(null, 0);
        Set<ConstraintViolation<Student>> constraintViolationSet = validator.validate(student);
        System.out.println(constraintViolationSet.size());
        constraintViolationSet.stream().forEach(constraintViolation -> System.out.println(constraintViolation.getPropertyPath() + constraintViolation.getMessage()));
    }

}
