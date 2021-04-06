package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
public interface MemberSearchRepository {
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition , Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition , Pageable pageable);
}

