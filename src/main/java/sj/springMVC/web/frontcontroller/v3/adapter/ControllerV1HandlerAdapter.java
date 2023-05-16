package sj.springMVC.web.frontcontroller.v3.adapter;

import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;
import sj.springMVC.web.frontcontroller.v3.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ControllerV1의 Adapter
 */
public class ControllerV1HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {

        return (handler instanceof ControllerV1);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV1 controller = (ControllerV1) handler;
        Map<String, String> paramMap = createParamMap(request);

        return controller.process(paramMap);

    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}

