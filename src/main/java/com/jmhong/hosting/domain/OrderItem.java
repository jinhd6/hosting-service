package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String name;
    private LocalDateTime activateDate;
    private LocalDateTime expireDate;

    private Long orderPrice;
    private Long orderPeriod;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    protected OrderItem() {
    }

    public OrderItem(Order order, Item item,
                     String name, LocalDateTime activateDate, LocalDateTime expireDate,
                     Long orderPrice, Long orderPeriod, OrderItemStatus status) {
        this.order = order;
        this.item = item;
        this.name = name;
        this.activateDate = activateDate;
        this.expireDate = expireDate;
        this.orderPrice = orderPrice;
        this.orderPeriod = orderPeriod;
        this.status = status;
        order.addOrderItem(this);
    }

    public static OrderItem createOrderItem(Order order, Item item, String name) {
        LocalDateTime activateDate = LocalDateTime.now();
        LocalDateTime expireDate = activateDate.plusDays(item.getPeriod());
        return new OrderItem(order, item, name,
                activateDate, expireDate, item.getPrice(), item.getPeriod(), OrderItemStatus.ACTIVE);
    }

    public void cancelOrderItem() {
        if (status == OrderItemStatus.EXPIRE) {
            throw new IllegalStateException("대여가 끝난 주문상품을 취소할 수 없습니다.");
        }
        status = OrderItemStatus.CANCEL;
    }
}
