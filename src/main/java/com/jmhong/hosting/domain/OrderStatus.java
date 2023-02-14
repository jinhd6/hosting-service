package com.jmhong.hosting.domain;

public enum OrderStatus {

    ORDER("주문중"),
    COMPLETE("주문완료"),
    CANCEL("주문취소");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
