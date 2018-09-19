package com.aviator.island.utils;

/**
 * Created by 18057046 on 2018/8/7.
 */
public enum SearchConditional {
    EQ("eq"),// 等于
    GT("gt"),// 大于 greater than
    GE("ge"),// 大于等于 greater than or equal
    LT("lt"),// 小于 less than
    LE("le"),// 小于 等于 less than or equal
    IS_NULL("isNull"),// 等于空值
    IS_NOT_NULL("isNotNull"),// 非空值
    IS_EMPTY("isEmpty"),// 集合空
    IS_NOT_EMPTY("isNotEmpty"),// 集合非空
    LIKE("like"),
    IN("in"),
    BETWEEN("between");

    private String conditional;

    public String getConditional() {
        return conditional;
    }

    public void setConditional(String conditional) {
        this.conditional = conditional;
    }

    SearchConditional(String conditional) {
        this.conditional = conditional;
    }

    public boolean conditionalEqual(SearchConditional conditional) {
        return this.getConditional().equals(conditional.getConditional());
    }
}
