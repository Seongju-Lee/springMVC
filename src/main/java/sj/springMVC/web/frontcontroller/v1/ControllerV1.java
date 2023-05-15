package sj.springMVC.web.frontcontroller.v1;


import sj.springMVC.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV1 {


    ModelView process(Map<String, String> paramMap);
}
