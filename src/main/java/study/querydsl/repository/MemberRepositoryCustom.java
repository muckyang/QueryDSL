package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> searchPlain(MemberSearchCondition condition);
    List<MemberTeamDto> searchBuilder(MemberSearchCondition condition);

    List<MemberTeamDto> findAllDto();
//
//    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition , Pageable pageable);
//    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition , Pageable pageable);
}
