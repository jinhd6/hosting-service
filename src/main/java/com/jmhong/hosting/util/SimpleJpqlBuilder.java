package com.jmhong.hosting.util;


import org.springframework.util.StringUtils;

public class SimpleJpqlBuilder {
    private String jpql;
    private boolean isFirstCondition = true;

    protected SimpleJpqlBuilder() {
    }

    public SimpleJpqlBuilder(String jpql) {
        this.jpql = jpql;
    }

    public void andWhere(String searchCondition) {
        if (isFirstCondition) {
            jpql += " where";
            isFirstCondition = false;
        } else {
            jpql += " and";
        }
        jpql += " " + StringUtils.trimWhitespace(searchCondition);
    }

    public String build() {
        return jpql;
    }
}
