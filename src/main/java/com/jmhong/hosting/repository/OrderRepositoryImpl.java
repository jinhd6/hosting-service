package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Order;
import com.jmhong.hosting.dto.OrderSearchDto;
import com.jmhong.hosting.util.SimpleJpqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Order> search(OrderSearchDto orderSearchDto) {
        String jpql = buildJpql(orderSearchDto);
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);
        setQueryParams(orderSearchDto, query);
        return query.getResultList();
    }

    private static void setQueryParams(OrderSearchDto orderSearchDto, TypedQuery<Order> query) {
        if (orderSearchDto.getStartTime() != null &&
                (!orderSearchDto.getStartTime().isAfter(orderSearchDto.getEndTime()))) {
            query.setParameter("startTime", orderSearchDto.getStartTime());
        }

        if (orderSearchDto.getEndTime() != null &&
                (!orderSearchDto.getEndTime().isBefore(orderSearchDto.getStartTime()))) {
            query.setParameter("endTime", orderSearchDto.getEndTime());
        }

        if (StringUtils.hasText(orderSearchDto.getMemberUsername())) {
            query.setParameter("username", ("%" + orderSearchDto.getMemberUsername() + "%"));
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerName())) {
            query.setParameter("customerName", ("%" + orderSearchDto.getCustomerName() + "%"));
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerPhoneNumber())) {
            query.setParameter("customerPhoneNumber", ("%" + orderSearchDto.getCustomerPhoneNumber() + "%"));
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerAddress())) {
            query.setParameter("customerAddress", ("%" + orderSearchDto.getCustomerAddress() + "%"));
        }

        if (orderSearchDto.getType() != null) {
            query.setParameter("type", orderSearchDto.getType());
        }

        if (orderSearchDto.getStatus() != null) {
            query.setParameter("status", orderSearchDto.getStatus());
        }
    }

    private static String buildJpql(OrderSearchDto orderSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder("select o from Order o join o.member m");

        if (orderSearchDto.getStartTime() != null &&
                (!orderSearchDto.getStartTime().isAfter(orderSearchDto.getEndTime()))) {
            simpleJpqlBuilder.andWhere("o.orderDate >= :startTime");
        }

        if (orderSearchDto.getEndTime() != null &&
                (!orderSearchDto.getEndTime().isBefore(orderSearchDto.getStartTime()))) {
            simpleJpqlBuilder.andWhere("o.orderDate <= :endTime");
        }

        if (StringUtils.hasText(orderSearchDto.getMemberUsername())) {
            simpleJpqlBuilder.andWhere("m.username like :username");
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerName())) {
            simpleJpqlBuilder.andWhere("o.customerName like :customerName");
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerPhoneNumber())) {
            simpleJpqlBuilder.andWhere("o.customerPhoneNumber like :customerPhoneNumber");
        }

        if (StringUtils.hasText(orderSearchDto.getCustomerAddress())) {
            simpleJpqlBuilder.andWhere("o.customerAddress like :customerAddress");
        }

        if (orderSearchDto.getType() != null) {
            simpleJpqlBuilder.andWhere("o.type = :type");
        }

        if (orderSearchDto.getStatus() != null) {
            simpleJpqlBuilder.andWhere("o.status = :status");
        }

        return simpleJpqlBuilder.build();
    }
}
