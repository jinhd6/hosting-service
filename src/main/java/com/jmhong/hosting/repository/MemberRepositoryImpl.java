package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.dto.MemberSearchDto;
import com.jmhong.hosting.util.SimpleJpqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> search(MemberSearchDto memberSearchDto) {
        String jpql = buildJpql(memberSearchDto);
        TypedQuery<Member> query = em.createQuery(jpql, Member.class).setMaxResults(1000);
        setQueryParams(memberSearchDto, query);
        return query.getResultList();
    }

    private static String buildJpql(MemberSearchDto memberSearchDto) {
        SimpleJpqlBuilder simpleJpqlBuilder = new SimpleJpqlBuilder("select m from Member m");

        if (StringUtils.hasText(memberSearchDto.getUsername())) {
            simpleJpqlBuilder.andWhere("m.username like :username");
        }

        if (StringUtils.hasText(memberSearchDto.getEmail())) {
            simpleJpqlBuilder.andWhere("m.email like :email");
        }

        if (StringUtils.hasText(memberSearchDto.getRealName())) {
            simpleJpqlBuilder.andWhere("m.realName like :realName");
        }

        if (StringUtils.hasText(memberSearchDto.getPhoneNumber())) {
            simpleJpqlBuilder.andWhere("m.phoneNumber like :phoneNumber");
        }

        if (StringUtils.hasText(memberSearchDto.getAddress())) {
            simpleJpqlBuilder.andWhere("m.address like :address");
        }

        if (memberSearchDto.getType() != null) {
            simpleJpqlBuilder.andWhere("m.type = :type");
        }

        return simpleJpqlBuilder.build();
    }

    private static void setQueryParams(MemberSearchDto memberSearchDto, TypedQuery<Member> query) {
        if (StringUtils.hasText(memberSearchDto.getUsername())) {
            query.setParameter("username", ("%" + memberSearchDto.getUsername() + "%"));
        }

        if (StringUtils.hasText(memberSearchDto.getEmail())) {
            query.setParameter("email", ("%" + memberSearchDto.getEmail() + "%"));
        }

        if (StringUtils.hasText(memberSearchDto.getRealName())) {
            query.setParameter("realName", ("%" + memberSearchDto.getRealName() + "%"));
        }

        if (StringUtils.hasText(memberSearchDto.getPhoneNumber())) {
            query.setParameter("phoneNumber", ("%" + memberSearchDto.getPhoneNumber() + "%"));
        }

        if (StringUtils.hasText(memberSearchDto.getAddress())) {
            query.setParameter("address", ("%" + memberSearchDto.getAddress() + "%"));
        }

        if (memberSearchDto.getType() != null) {
            query.setParameter("type", memberSearchDto.getType());
        }
    }
}
