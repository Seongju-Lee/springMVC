package sj.springMVC.web.frontcontroller.v2.controller;

import sj.springMVC.domain.member.Member;
import sj.springMVC.domain.member.MemberRepository;
import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;
import sj.springMVC.web.frontcontroller.v2.ControllerV2;

import java.util.Map;

public class MemberSaveControllerV2 implements ControllerV2 {


    MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);

        return "save-result";
    }
}
