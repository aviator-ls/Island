package com.aviator.island.utils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

/**
 * Created by 18057046 on 2018/8/7.
 */
public class OrderUtil {
    public static Order order(String orderBy, boolean isAsc) {
        if (StringUtils.isBlank(orderBy)) {
            return null;
        }
        if (isAsc) {
            return Order.asc(orderBy);
        }
        return Order.desc(orderBy);
    }
}
