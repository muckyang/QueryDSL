package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;

@SpringBootTest
@Transactional
//@Commit
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    //동시성 문제 없음
    JPAQueryFactory queryFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //시작 전에 생성
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @Transactional
    //@RollBack(false)
    public void testEntity() throws Exception {

        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 25, teamA);
        Member member3 = new Member("member3", 35, teamB);
        Member member4 = new Member("member4", 55, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);


        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m ", Member.class).getResultList();
        System.out.println(members.size());
        for (Member m : members) {
            System.out.println("member : " + m);
            System.out.println("-> member.team : " + m.getTeam());
        }
    }

    @Test
    public void JPQLQuery() throws Exception {
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    @Transactional
    public void dslTest() throws Exception {
//        QMember qMember = QMember.member;
//          static import
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    @Transactional
    public void select() throws Exception {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.between(10,20)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    @Transactional
    public void selectComma() throws Exception {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"),
                        member.age.between(10,20))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}
