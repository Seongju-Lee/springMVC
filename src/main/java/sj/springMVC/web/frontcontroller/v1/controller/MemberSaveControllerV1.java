package sj.springMVC.web.frontcontroller.v1.controller;

import sj.springMVC.domain.member.Member;
import sj.springMVC.domain.member.MemberRepository;
import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;

import java.util.Map;

public class MemberSaveControllerV1 implements ControllerV1 {


    MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;
    }
}
