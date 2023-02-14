package com.jmhong.hosting.domain;

public enum MemberType {

    MEMBER("일반회원"),
    ADMIN("관리자");

    private final String typeName;

    MemberType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
