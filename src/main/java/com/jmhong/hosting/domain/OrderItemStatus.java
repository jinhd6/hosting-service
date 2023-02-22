package com.jmhong.hosting.domain;

public enum OrderItemStatus {
    ACTIVE("활성"),
    EXPIRE("만료"),
    CANCEL("취소");

    public final String statusName;

    OrderItemStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
