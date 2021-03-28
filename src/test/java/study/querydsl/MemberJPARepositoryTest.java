package study.querydsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;
import study.querydsl.repository.JPAMemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberJPARepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAMemberRepository memberJPARepository;


    @Test
    @Transactional
    @Rollback(false)
    public void save() throws Exception {
        Member member1 = new Member("member", 10);
        memberJPARepository.save(member1);

        Member findMember = memberJPARepository.findById(member1.getId()).get();
        assertThat(findMember).isEqualTo(member1);

        List<Member> result1 = memberJPARepository.findAll();
        assertThat(result1).containsExactly(member1);

        List<Member> result2 = memberJPARepository.findByUsername("member");
        assertThat(result2).containsExactly(member1);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void querydslTest() throws Exception {
        Member member1 = new Member("member", 10);
        memberJPARepository.save(member1);

        Member findMember = memberJPARepository.findById(member1.getId()).get();
        assertThat(findMember).isEqualTo(member1);

        List<Member> result1 = memberJPARepository.findAll_Querydsl();
        assertThat(result1).containsExactly(member1);

        List<Member> result2 = memberJPARepository.findByUsername_Querydsl("member");
        assertThat(result2).containsExactly(member1);
    }

    @Test
    @Transactional
//    @Rollback(false)
    public void SearchTest() throws Exception {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("TeamB");
        List<MemberTeamDto> result = memberJPARepository.search(condition);
//        List<MemberTeamDto> result = memberJPARepository.searchBuilder(condition);

        assertThat(result).extracting("username").containsExactly("member4");
    }
}
