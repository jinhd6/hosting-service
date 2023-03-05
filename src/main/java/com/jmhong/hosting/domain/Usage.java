package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usages")
@Getter
public class Usage {

    @Id @GeneratedValue
    @Column(name = "usage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private LocalDateTime connectDate;
    private LocalDateTime disconnectDate;

    protected Usage() {
    }

    public Usage(OrderItem orderItem, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        this.orderItem = orderItem;
        this.connectDate = connectDate;
        this.disconnectDate = disconnectDate;
    }

    public static Usage createUsage(OrderItem orderItem, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        checkOrderItem(orderItem);
        checkUsageTime(orderItem, connectDate, disconnectDate);
        Usage usage = new Usage(orderItem, connectDate, disconnectDate);
        if (orderItem.getOrder().getStatus() == OrderStatus.ORDER) {
            orderItem.getOrder().completeOrder();
        }
        return usage;
    }

    private static void checkUsageTime(OrderItem orderItem, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        if (connectDate.isAfter(disconnectDate)) {
            throw new IllegalArgumentException("사용시작시간은 사용종료시간보다 이후일 수 없습니다.");
        }
        if (connectDate.isBefore(orderItem.getActivateDate())) {
            throw new IllegalArgumentException("사용시작시간은 대여시작시간보다 이전일 수 없습니다.");
        }
        if (connectDate.isAfter(orderItem.getExpireDate())) {
            throw new IllegalArgumentException("사용시작시간은 대여만료시간보다 이후일 수 없습니다.");
        }
    }

    private static void checkOrderItem(OrderItem orderItem) {
        if (orderItem.getStatus() == OrderItemStatus.CANCEL) {
            throw new IllegalStateException("취소된 주문상품을 사용할 수 없습니다.");
        }
        if (orderItem.getStatus() == OrderItemStatus.EXPIRE) {
            throw new IllegalStateException("만료된 주문상품을 사용할 수 없습니다.");
        }
    }
}
