package study.querydsl.repository;

import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    public List<MemberTeamDto> searchPlain(MemberSearchCondition condition);
    public List<MemberTeamDto> searchBuilder(MemberSearchCondition condition);

    public List<MemberTeamDto> findAllDto();
}
