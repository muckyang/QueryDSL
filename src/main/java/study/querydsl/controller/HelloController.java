package study.querydsl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberRepository;

@RestController
public class HelloController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping(name = "/hello")
    public String hello(Model model){
        return "hello";
    }

    @GetMapping("/api/v1")
    public Page<MemberTeamDto> pageSearchV1(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.searchPageSimple(condition,pageable);

    }   @GetMapping("/api/v2")
    public Page<MemberTeamDto> pageSearchV2(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.searchPageComplex(condition,pageable);

    }
}
