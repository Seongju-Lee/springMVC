package sj.springMVC.web.frontcontroller.v1.controller;


import sj.springMVC.domain.member.Member;
import sj.springMVC.domain.member.MemberRepository;
import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;

import java.util.List;
import java.util.Map;

public class MemberListControllerV1 implements ControllerV1 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();

        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);
        return mv;
    }
}
