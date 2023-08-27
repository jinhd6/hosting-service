package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.OrderItemStatus;
import com.jmhong.hosting.dto.OrderItemCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.jmhong.hosting.domain.QOrder.order;
import static com.jmhong.hosting.domain.QOrderItem.orderItem;
import static org.springframework.util.StringUtils.hasText;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public OrderItemRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderItem> search(OrderItemCond orderItemCond) {
        return queryFactory
                .select(orderItem)
                .from(orderItem)
                .join(orderItem.order, order)
                .where(
                        nameContains(orderItemCond.getOrderItemName()),
                        customerNameContains(orderItemCond.getOrderCustomerName()),
                        activateDateLoe(orderItemCond.getStartTime(), orderItemCond.getEndTime()),
                        expireDateGoe(orderItemCond.getStartTime(), orderItemCond.getEndTime()),
                        statusEq(orderItemCond.getStatus())
                )
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression nameContains(String cond) {
        return hasText(cond) ? orderItem.name.contains(cond) : null;
    }

    private static BooleanExpression customerNameContains(String cond) {
        return hasText(cond) ? order.customerName.contains(cond) : null;
    }

    private static BooleanExpression activateDateLoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((endTime != null) && (!endTime.isBefore(startTime))) {
            return orderItem.activateDate.loe(endTime);
        } else {
            return null;
        }
    }

    private static BooleanExpression expireDateGoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((startTime != null) && (!startTime.isAfter(endTime))) {
            return orderItem.expireDate.goe(startTime);
        } else {
            return null;
        }
    }

    private static BooleanExpression statusEq(OrderItemStatus cond) {
        return (cond != null) ? orderItem.status.eq(cond) : null;
    }

}
