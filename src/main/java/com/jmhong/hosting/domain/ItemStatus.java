package com.jmhong.hosting.domain;

public enum ItemStatus {

    SALE("판매중"),
    SUSPEND("판매중단");

    private final String statusName;

    ItemStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
