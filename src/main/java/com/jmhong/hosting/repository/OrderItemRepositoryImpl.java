package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.dto.OrderItemSearchDto;
import com.jmhong.hosting.util.SimpleJpqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public OrderItemRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<OrderItem> search(OrderItemSearchDto orderItemSearchDto) {
        String jpql = buildJpql(orderItemSearchDto);
        TypedQuery<OrderItem> query = em.createQuery(jpql, OrderItem.class).setMaxResults(1000);
        setQueryParams(orderItemSearchDto, query);
        return query.getResultList();
    }

    private static void setQueryParams(OrderItemSearchDto orderItemSearchDto, TypedQuery<OrderItem> query) {
        if (StringUtils.hasText(orderItemSearchDto.getOrderItemName())) {
            query.setParameter("orderItemName", ("%" + orderItemSearchDto.getOrderItemName() + "%"));
        }

        if (StringUtils.hasText(orderItemSearchDto.getOrderCustomerName())) {
            query.setParameter("orderCustomerName", ("%" + orderItemSearchDto.getOrderCustomerName() + "%"));
        }

        if (orderItemSearchDto.getStartTime() != null &&
                (!orderItemSearchDto.getStartTime().isAfter(orderItemSearchDto.getEndTime()))) {
            query.setParameter("startTime", orderItemSearchDto.getStartTime());
        }

        if (orderItemSearchDto.getEndTime() != null &&
                (!orderItemSearchDto.getEndTime().isBefore(orderItemSearchDto.getStartTime()))) {
            query.setParameter("endTime", orderItemSearchDto.getEndTime());
        }

        if (orderItemSearchDto.getStatus() != null) {
            query.setParameter("status", orderItemSearchDto.getStatus());
        }
    }

    private static String buildJpql(OrderItemSearchDto orderItemSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder("select oi from OrderItem oi join oi.order o");

        if (StringUtils.hasText(orderItemSearchDto.getOrderItemName())) {
            simpleJpqlBuilder.andWhere("oi.name like :orderItemName");
        }

        if (StringUtils.hasText(orderItemSearchDto.getOrderCustomerName())) {
            simpleJpqlBuilder.andWhere("o.customerName like :orderCustomerName");
        }

        if (orderItemSearchDto.getStartTime() != null &&
                (!orderItemSearchDto.getStartTime().isAfter(orderItemSearchDto.getEndTime()))) {
            simpleJpqlBuilder.andWhere("oi.expireDate >= :startTime");
        }

        if (orderItemSearchDto.getEndTime() != null &&
                (!orderItemSearchDto.getEndTime().isBefore(orderItemSearchDto.getStartTime()))) {
            simpleJpqlBuilder.andWhere("oi.activateDate <= :endTime");
        }

        if (orderItemSearchDto.getStatus() != null) {
            simpleJpqlBuilder.andWhere("oi.status = :status");
        }

        return simpleJpqlBuilder.build();
    }
}
