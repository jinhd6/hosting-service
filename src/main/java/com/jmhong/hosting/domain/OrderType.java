package com.jmhong.hosting.domain;

public enum OrderType {

    NEW("신규주문"),
    EXTEND("연장주문");

    private final String typeName;

    OrderType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
