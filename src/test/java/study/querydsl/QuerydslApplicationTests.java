package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.First;
import study.querydsl.entity.QFirst;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void contextLoads() {
        First first = new First();
        em.persist(first);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QFirst qFirst = new QFirst("h");
        First result = query
				.selectFrom(qFirst)
				.fetchOne();

		assertThat(result).isEqualTo(first);
    }

}
