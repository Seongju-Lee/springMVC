package sj.springMVC.web.frontcontroller.v3.adapter;

import org.springframework.validation.ObjectError;
import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;
import sj.springMVC.web.frontcontroller.v2.ControllerV2;
import sj.springMVC.web.frontcontroller.v3.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ControllerV2의 Adapter
 */
public class ControllerV2HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {

        return (handler instanceof ControllerV2);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV2 controller = (ControllerV2) handler;
        Map<String, String> paramMap = createParamMap(request);

        HashMap<String, Object> model = new HashMap<>();
        // ControllerV2는 view의 논리 이름만 반환
        String viewName = controller.process(paramMap, model);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}

