package sj.springMVC.web.frontcontroller.v1.controller;

import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;

import java.util.Map;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
