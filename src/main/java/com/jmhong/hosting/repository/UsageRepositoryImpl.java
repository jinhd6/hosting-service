package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageSearchDto;
import com.jmhong.hosting.util.SimpleJpqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsageRepositoryImpl implements UsageRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public UsageRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Usage> search(UsageSearchDto usageSearchDto) {
        String jpql = buildJpql(usageSearchDto);
        TypedQuery<Usage> query = em.createQuery(jpql, Usage.class).setMaxResults(1000);
        setQueryParams(usageSearchDto, query);
        return query.getResultList();
    }

    private static void setQueryParams(UsageSearchDto usageSearchDto, TypedQuery<Usage> query) {
        if (StringUtils.hasText(usageSearchDto.getMemberUserName())) {
            query.setParameter("memberUserName", ("%" + usageSearchDto.getMemberUserName() + "%"));
        }

        if (StringUtils.hasText(usageSearchDto.getOrderItemName())) {
            query.setParameter("orderItemName", ("%" + usageSearchDto.getOrderItemName() + "%"));
        }
    }

    private static String buildJpql(UsageSearchDto usageSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder("select u from Usage u join u.member m join u.orderItem oi");

        if (StringUtils.hasText(usageSearchDto.getMemberUserName())) {
            simpleJpqlBuilder.andWhere("m.username like :memberUserName");
        }

        if (StringUtils.hasText(usageSearchDto.getOrderItemName())) {
            simpleJpqlBuilder.andWhere("oi.name like :orderItemName");
        }

        return simpleJpqlBuilder.build();
    }
}
