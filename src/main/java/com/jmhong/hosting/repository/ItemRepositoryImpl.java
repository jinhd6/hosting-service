package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jmhong.hosting.domain.QItem.item;
import static org.springframework.util.StringUtils.hasText;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ItemRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> search(ItemCond itemCond) {
        return queryFactory
                .select(item)
                .from(item)
                .where(
                        nameContains(itemCond.getName()),
                        statusEq(itemCond.getStatus()),
                        priceGoe(itemCond.getMinPrice()),
                        priceLoe(itemCond.getMaxPrice()),
                        periodGoe(itemCond.getMinPeriod()),
                        periodLoe(itemCond.getMaxPeriod())
                )
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression nameContains(String name) {
        return hasText(name) ? item.name.contains(name) : null;
    }

    private static BooleanExpression statusEq(ItemStatus status) {
        return (status != null) ? item.status.eq(status) : null;
    }

    private static BooleanExpression priceGoe(String price) {
        return hasText(price) ? item.price.goe(Long.valueOf(price)) : null;
    }

    private static BooleanExpression priceLoe(String price) {
        return hasText(price) ? item.price.loe(Long.valueOf(price)) : null;
    }

    private static BooleanExpression periodGoe(String period) {
        return hasText(period) ? item.period.goe(Long.valueOf(period)) : null;
    }

    private static BooleanExpression periodLoe(String period) {
        return hasText(period) ? item.period.loe(Long.valueOf(period)) : null;
    }

}
