package study.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class JPQLTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void createQ() {
        queryFactory = new JPAQueryFactory(em);
    }


    @Test
    @Transactional
    public void JPQL() {
        Long userId = 1L;
        Member findMember = em.createQuery("select m from Member m where m.id = :id",Member.class)
                .setParameter("id",userId).getSingleResult();
     }

    @Test
    @Transactional
    public void DSL() throws Exception {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m"); //식별자

        Member findMember = jpaQueryFactory
                .select(m)
                .from(m)
                .where(m.id.eq(1L))
                .fetchOne();
    }


}
