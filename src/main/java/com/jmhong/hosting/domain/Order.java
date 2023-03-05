package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Long quantity;

    protected Order() {
    }

    public Order(Member member, LocalDateTime orderDate,
                 String customerName, String customerPhoneNumber, String customerAddress,
                 OrderType type, OrderStatus status, Long quantity) {
        this.member = member;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.type = type;
        this.status = status;
        this.quantity = quantity;
        member.addOrder(this);
    }

    public static Order createOrder(Member member, Item item, Long quantity) {
        if (item.getStatus() == ItemStatus.SUSPEND) {
            throw new IllegalStateException("판매 중단된 상품을 주문할 수 없습니다.");
        }
        return new Order(member, LocalDateTime.now(),
                member.getRealName(), member.getPhoneNumber(), member.getAddress(),
                OrderType.NEW, OrderStatus.ORDER, quantity);
    }

    public void cancelOrder() {
        if (status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("완료된 주문을 취소할 수 없습니다.");
        }
        status = OrderStatus.CANCEL;
        for (OrderItem orderItem : this.getOrderItems()) {
            orderItem.cancelOrderItem();
        }
    }

    public void completeOrder() {
        if (status == OrderStatus.CANCEL) {
            throw new IllegalStateException("취소된 주문을 완료할 수 없습니다.");
        }
        status = OrderStatus.COMPLETE;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
