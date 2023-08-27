package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.jmhong.hosting.domain.QMember.member;
import static com.jmhong.hosting.domain.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> search(OrderCond orderCond) {
        return queryFactory
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(
                        orderDateGoe(orderCond.getStartTime(), orderCond.getEndTime()),
                        orderDateLoe(orderCond.getStartTime(), orderCond.getEndTime()),
                        usernameContains(orderCond.getMemberUsername()),
                        customerNameContains(orderCond.getCustomerName()),
                        customerPhoneNumberContains(orderCond.getCustomerPhoneNumber()),
                        customerAddressContains(orderCond.getCustomerAddress()),
                        typeEq(orderCond.getType()),
                        statusEq(orderCond.getStatus())
                )
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression orderDateGoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((startTime != null) && (!startTime.isAfter(endTime))) {
            return order.orderDate.goe(startTime);
        } else {
            return null;
        }
    }

    private static BooleanExpression orderDateLoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((endTime != null) && (!endTime.isBefore(startTime))) {
            return order.orderDate.loe(endTime);
        } else {
            return null;
        }
    }

    private static BooleanExpression usernameContains(String cond) {
        return hasText(cond) ? member.username.contains(cond) : null;
    }

    private static BooleanExpression customerNameContains(String cond) {
        return hasText(cond) ? order.customerName.contains(cond) : null;
    }

    private static BooleanExpression customerPhoneNumberContains(String cond) {
        return hasText(cond) ? order.customerPhoneNumber.contains(cond) : null;
    }

    private static BooleanExpression customerAddressContains(String cond) {
        return hasText(cond) ? order.customerAddress.contains(cond) : null;
    }

    private static BooleanExpression typeEq(OrderType cond) {
        return (cond != null) ? order.type.eq(cond) : null;
    }

    private static BooleanExpression statusEq(OrderStatus cond) {
        return (cond != null) ? order.status.eq(cond) : null;
    }

}
