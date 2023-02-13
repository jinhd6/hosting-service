package com.jmhong.hosting.util;


import org.springframework.util.StringUtils;

/**
 * JPQL 동적 쿼리를 생성합니다. 주어진 JPQL 쿼리 끝부분에 Where 절의 조건을 추가하는 기능만 있습니다.
 */
public class SimpleJpqlBuilder {
    private String jpql;
    private boolean isFirstCondition = true;

    private SimpleJpqlBuilder() {
    }

    public SimpleJpqlBuilder(String jpql) {
        this.jpql = jpql;
    }

    /**
     * Where 절의 조건을 추가합니다.
     * @param searchCondition Where 절의 조건 구문입니다. 양 끝의 공백은 필요하지 않습니다.
     */
    public void andWhere(String searchCondition) {
        if (isFirstCondition) {
            jpql += " where";
            isFirstCondition = false;
        } else {
            jpql += " and";
        }
        jpql += " " + StringUtils.trimWhitespace(searchCondition);
    }

    /**
     * 생성된 JPQL 쿼리를 반환합니다.
     */
    public String build() {
        return jpql;
    }
}
