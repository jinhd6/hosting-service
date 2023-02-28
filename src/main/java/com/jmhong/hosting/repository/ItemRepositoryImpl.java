package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.dto.ItemSearchDto;
import com.jmhong.hosting.util.SimpleJpqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public ItemRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Item> search(ItemSearchDto itemSearchDto) {
        String jpql = buildJpql(itemSearchDto);
        TypedQuery<Item> query = em.createQuery(jpql, Item.class).setMaxResults(1000);
        setQueryParams(itemSearchDto, query);
        return query.getResultList();
    }

    private static void setQueryParams(ItemSearchDto itemSearchDto, TypedQuery<Item> query) {
        if (StringUtils.hasText(itemSearchDto.getName())) {
            query.setParameter("name", ("%" + itemSearchDto.getName() + "%"));
        }

        if (itemSearchDto.getStatus() != null) {
            query.setParameter("status", itemSearchDto.getStatus());
        }

        if (StringUtils.hasText(itemSearchDto.getMinPrice())) {
            query.setParameter("minPrice", Long.valueOf(itemSearchDto.getMinPrice()));
        }

        if (StringUtils.hasText(itemSearchDto.getMaxPrice())) {
            query.setParameter("maxPrice", Long.valueOf(itemSearchDto.getMaxPrice()));
        }

        if (StringUtils.hasText(itemSearchDto.getMinPeriod())) {
            query.setParameter("minPeriod", Long.valueOf(itemSearchDto.getMinPeriod()));
        }

        if (StringUtils.hasText(itemSearchDto.getMaxPeriod())) {
            query.setParameter("maxPeriod", Long.valueOf(itemSearchDto.getMaxPeriod()));
        }
    }

    private static String buildJpql(ItemSearchDto itemSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder("select i from Item i");

        if (StringUtils.hasText(itemSearchDto.getName())) {
            simpleJpqlBuilder.andWhere("i.name like :name");
        }

        if (itemSearchDto.getStatus() != null) {
            simpleJpqlBuilder.andWhere("i.status = :status");
        }

        if (StringUtils.hasText(itemSearchDto.getMinPrice())) {
            simpleJpqlBuilder.andWhere("i.price >= :minPrice");
        }

        if (StringUtils.hasText(itemSearchDto.getMaxPrice())) {
            simpleJpqlBuilder.andWhere("i.price <= :maxPrice");
        }

        if (StringUtils.hasText(itemSearchDto.getMinPeriod())) {
            simpleJpqlBuilder.andWhere("i.period >= :minPeriod");
        }

        if (StringUtils.hasText(itemSearchDto.getMaxPeriod())) {
            simpleJpqlBuilder.andWhere("i.period <= :maxPeriod");
        }

        return simpleJpqlBuilder.build();
    }
}
