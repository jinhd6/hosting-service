package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.dto.MemberCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jmhong.hosting.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> search(MemberCond memberCond) {
        return queryFactory
                .select(member)
                .from(member)
                .where(
                        containsUsername(memberCond.getUsername()),
                        containsEmail(memberCond.getEmail()),
                        containsRealName(memberCond.getRealName()),
                        containsPhoneNumber(memberCond.getPhoneNumber()),
                        containsAddress(memberCond.getAddress()),
                        eqMemberType(memberCond.getType())
                )
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression containsUsername(String cond) {
        return hasText(cond) ? member.username.contains(cond) : null;
    }

    private static BooleanExpression containsEmail(String cond) {
        return hasText(cond) ? member.email.contains(cond) : null;
    }

    private static BooleanExpression containsRealName(String cond) {
        return hasText(cond) ? member.realName.contains(cond) : null;
    }

    private static BooleanExpression containsPhoneNumber(String cond) {
        return hasText(cond) ? member.phoneNumber.contains(cond) : null;
    }

    private static BooleanExpression containsAddress(String cond) {
        return hasText(cond) ? member.address.contains(cond) : null;
    }

    private static BooleanExpression eqMemberType(MemberType cond) {
        return cond != null ? member.type.eq(cond) : null;
    }

}
