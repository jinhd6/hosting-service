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
        if (StringUtils.hasText(usageSearchDto.getMemberUsername())) {
            query.setParameter("memberUsername", ("%" + usageSearchDto.getMemberUsername() + "%"));
        }

        if (StringUtils.hasText(usageSearchDto.getOrderItemName())) {
            query.setParameter("orderItemName", ("%" + usageSearchDto.getOrderItemName() + "%"));
        }

        if (usageSearchDto.getStartTime() != null
                && (!usageSearchDto.getStartTime().isAfter(usageSearchDto.getEndTime()))) {
            query.setParameter("startTime", usageSearchDto.getStartTime());
        }

        if (usageSearchDto.getEndTime() != null
                && (!usageSearchDto.getEndTime().isBefore(usageSearchDto.getStartTime()))) {
            query.setParameter("endTime", usageSearchDto.getEndTime());
        }
    }

    private static String buildJpql(UsageSearchDto usageSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder(
                "select u from Usage u"
                + " join u.orderItem oi"
                + " join oi.order o"
                + " join o.member m"
        );

        if (StringUtils.hasText(usageSearchDto.getMemberUsername())) {
            simpleJpqlBuilder.andWhere("m.username like :memberUsername");
        }

        if (StringUtils.hasText(usageSearchDto.getOrderItemName())) {
            simpleJpqlBuilder.andWhere("oi.name like :orderItemName");
        }

        if (usageSearchDto.getStartTime() != null
                && (!usageSearchDto.getStartTime().isAfter(usageSearchDto.getEndTime()))) {
            simpleJpqlBuilder.andWhere("u.connectDate <= :endTime");
        }

        if (usageSearchDto.getEndTime() != null
                && (!usageSearchDto.getEndTime().isBefore(usageSearchDto.getStartTime()))) {
            simpleJpqlBuilder.andWhere("u.disconnectDate >= :startTime");
        }

        return simpleJpqlBuilder.build();
    }
}
