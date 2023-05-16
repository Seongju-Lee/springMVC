package sj.springMVC.web.frontcontroller.v2;

import sj.springMVC.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV2 {

    String process(Map<String, String> paramMap, Map<String, Object> model);
}
