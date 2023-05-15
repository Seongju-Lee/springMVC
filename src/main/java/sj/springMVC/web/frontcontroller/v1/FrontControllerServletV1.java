package sj.springMVC.web.frontcontroller.v1;

import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.MyView;
import sj.springMVC.web.frontcontroller.v1.controller.MemberFormControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberListControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // Constructor로 Controller Mapping
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //== 1. Controller 조회 ==//
        ControllerV1 controller = getController(request);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        //== 2. Controller 호출(비즈니스 로직 처리), 3 ModelView 반환 ==//
        Map<String, String> paramMap = createParamMap(request); // request의 parameter data를 새로운 Map에 담아서, 전달
        ModelView mv = controller.process(paramMap);

        // 4~5. ViewResolver를 통해 논리이름 -> 물리위치로 변경
        MyView view = viewResolver(mv);

        // 6. render(model) 호출
        view.render(mv.getModel(), request, response);

    }

    /**
     * 논리이름 -> 물리위치로 변경
     * @param mv : 모델의 이름을 가져오기 위함
     * @return : view 반환
     */
    private static MyView viewResolver(ModelView mv) {
        return new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
    }


    /**
     * Controller의 비즈니스 로직 처리를 위한 req 정보 parameter 생성
     * @param request : HTTP Request로부터 온 내용을 기반으로, servlet container에서 생성한 request 객체
     * @return : Controller Parameter
     */
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

    /**
     * Controller 조회
     * @param request : request에 들어온 Controller 정보
     * @return : req에 매핑되어 있는 Controller
     */
    private ControllerV1 getController(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 요청 URI
        return controllerMap.get(requestURI);
    }
}

