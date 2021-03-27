package study.querydsl;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    EntityManager em;
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @Transactional
    @Commit
    public void testEntity() throws Exception {

        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15 ,teamA);
        Member member2 = new Member("member2", 25 ,teamA);
        Member member3 = new Member("member3", 35 ,teamB);
        Member member4 = new Member("member4", 55 ,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        member3.setTeam(teamA);


        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m ",Member.class).getResultList();
        System.out.println(members.size());
        for(Member m : members){
            System.out.println("member : " + m);
            System.out.println("-> member.team : " + m.getTeam());
        }

    }
}
