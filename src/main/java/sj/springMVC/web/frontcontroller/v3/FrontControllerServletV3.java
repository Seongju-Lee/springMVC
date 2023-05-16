package sj.springMVC.web.frontcontroller.v3;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import sj.springMVC.web.frontcontroller.ModelView;
import sj.springMVC.web.frontcontroller.MyView;
import sj.springMVC.web.frontcontroller.v1.controller.MemberFormControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberListControllerV1;
import sj.springMVC.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import sj.springMVC.web.frontcontroller.v2.controller.MemberFormControllerV2;
import sj.springMVC.web.frontcontroller.v2.controller.MemberListControllerV2;
import sj.springMVC.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import sj.springMVC.web.frontcontroller.v3.adapter.ControllerV1HandlerAdapter;
import sj.springMVC.web.frontcontroller.v3.adapter.ControllerV2HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV3 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV3() {

        initHandlerMappingMap();
        initHandlerAdapters();

    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV1HandlerAdapter());
        handlerAdapters.add(new ControllerV2HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v1/members/new-form", new MemberFormControllerV1());
        handlerMappingMap.put("/front-controller/v5/v1/members/save", new MemberSaveControllerV1());
        handlerMappingMap.put("/front-controller/v5/v1/members", new MemberListControllerV1());

        handlerMappingMap.put("/front-controller/v5/v2/members/new-form", new MemberFormControllerV2());
        handlerMappingMap.put("/front-controller/v5/v2/members/save", new MemberSaveControllerV2());
        handlerMappingMap.put("/front-controller/v5/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //== 1. 핸들러 조회 ==//
        Object handler = getHandler(request);
        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //== 2. 핸들러 어댑터 목록에서, 핸들러를 처리하기 위한 어댑터 조회 ==//
        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);


        //== 3~5. HandlerAdapter 통해서 handler 호출 후(비즈니스 로직 수행 후) ModelView 반환 ==//
        ModelView mv = handlerAdapter.handle(request, response, handler);

        //== 6~7. viewResolver() ==//
        MyView view = viewResolver(mv);

        //== 8. view로 이동-> render ==//
        view.render(mv.getModel(), request, response);

    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다= " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappingMap.get(request.getRequestURI());
    }

    private static MyView viewResolver(ModelView mv) {
        return new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
    }
}
