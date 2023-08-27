package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.UsageCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.jmhong.hosting.domain.QMember.member;
import static com.jmhong.hosting.domain.QOrder.order;
import static com.jmhong.hosting.domain.QOrderItem.orderItem;
import static com.jmhong.hosting.domain.QUsage.usage;
import static org.springframework.util.StringUtils.hasText;

public class UsageRepositoryImpl implements UsageRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UsageRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Usage> search(UsageCond usageCond) {
        return queryFactory
                .select(usage)
                .from(usage)
                .join(usage.orderItem, orderItem)
                .join(orderItem.order, order)
                .join(order.member, member)
                .where(
                        usernameContains(usageCond.getMemberUsername()),
                        orderItemNameContains(usageCond.getOrderItemName()),
                        connectDateLoe(usageCond.getStartTime(), usageCond.getEndTime()),
                        disconnectDateGoe(usageCond.getStartTime(), usageCond.getEndTime())
                )
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression usernameContains(String cond) {
        return hasText(cond) ? member.username.contains(cond) : null;
    }

    private static BooleanExpression orderItemNameContains(String cond) {
        return hasText(cond) ? orderItem.name.contains(cond) : null;
    }

    private static BooleanExpression connectDateLoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((endTime != null) && (!endTime.isBefore(startTime))) {
            return usage.connectDate.loe(endTime);
        } else {
            return null;
        }
    }

    private static BooleanExpression disconnectDateGoe(LocalDateTime startTime, LocalDateTime endTime) {
        if ((startTime != null) && (!startTime.isAfter(endTime))) {
            return usage.disconnectDate.goe(startTime);
        } else {
            return null;
        }
    }

}
