package sj.springMVC.web.frontcontroller.v2;

import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.MyView;
import sj.springMVC.web.frontcontroller.v1.ControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberFormControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberListControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import sj.springMVC.web.frontcontroller.v2.controller.MemberFormControllerV2;
import sj.springMVC.web.frontcontroller.v2.controller.MemberListControllerV2;
import sj.springMVC.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {


    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // Constructor로 Controller Mapping
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //== 1. Controller 조회 ==//
        ControllerV2 controller = controllerMap.get(request.getRequestURI());
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        //== 2~3. controller 호출 및 viewName 반환 ==//
        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>(); // model 객체를 따로 선언
        String viewName = controller.process(paramMap, model);

        //== 4~5. viewResolver ==//
        MyView view = viewResolver(viewName);

        //== 6. model 객체 들고, view로 이동 ==//
        view.render(model, request, response);

    }


    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" +viewName + ".jsp");
    }


    private static Map<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
