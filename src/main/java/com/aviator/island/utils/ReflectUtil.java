package com.aviator.island.utils;

import com.aviator.island.entity.po.LoginLog;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by 18057046 on 2018/8/7.
 */
public class ReflectUtil extends PropertyUtils {

    public static boolean hasProperty(Object bean, String property) {
        PropertyDescriptor[] propertyDescriptors = ReflectUtil.getPropertyDescriptors(bean);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals(property)) {
                return true;
            }
        }
        return false;
    }

    public static void setField(final Object obj, final String fieldName, final Object value) throws IllegalAccessException {
        if (StringUtils.isBlank(fieldName)) {
            return;
        }
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            return;
        }
        field.set(obj, value);
    }

    private static Field getAccessibleField(final Object obj, final String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return null;
    }

    private static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void main(String[] args) {
        LoginLog loginLog = new LoginLog();
        Assert.isTrue(!ReflectUtil.hasProperty(loginLog, "createTime"), "");
        Assert.isTrue(ReflectUtil.hasProperty(loginLog, "ip"), "");
    }
}
