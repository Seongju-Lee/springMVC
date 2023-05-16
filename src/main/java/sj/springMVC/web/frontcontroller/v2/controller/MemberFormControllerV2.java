package sj.springMVC.web.frontcontroller.v2.controller;

import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;
import sj.springMVC.web.frontcontroller.v2.ControllerV2;

import java.util.Map;

public class MemberFormControllerV2 implements ControllerV2 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
