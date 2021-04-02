package study.querydsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.repository.MemberRepository;

import javax.persistence.EntityManager;
import com.querydsl.core.Tuple;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

@SpringBootTest
@Transactional
public class FetchAndSubTest {
    @Autowired
    EntityManager em;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberRepository memberRepository;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        logger.error("started TEST");
    }

    @Test
    @Rollback
    public void FetchJoin() {
        em.flush();
        em.clear();
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .fetch();
        for (Member m : result) {
            System.out.println(m.toString());
        }
    }

    @Test
    public void SubQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        for (Member m : result)
            System.out.println(m.toString());

    }

    @Test
    public void SelectSubQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(member,select(memberSub.age.avg()).from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : result)
            System.out.println(tuple);

    }


}
