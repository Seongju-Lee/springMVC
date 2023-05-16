package sj.springMVC.web.frontcontroller.v3;

import sj.springMVC.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    /**
     * request에 맞는 핸들러를 처리할 수 있는 HandlerAdapter인지 확인한다.
     * @param handler: parameter로 들어오는 인자가 MyHandlerAdapter의 구현체와 일치한 HandlerAdapter인지 확인하기 위함.
     * @return: 만약, 동일한 handlerAdapter라면 true 아니라면 false를 리턴.
     */
    boolean supports(Object handler);

    /**
     * 실제 Handler를 호출하여, 비즈니스 로직을 처리하기 위한 메서드.
     * MyHandlerAdapter를 구현한 실제 Controller가 동작한 뒤, ModelAndView를 리턴.
     * 여러 종류의 Handler(Controller)를 해당 인터페이스를 활용하여 적용할 수 있다.
     * e.g) ControllerV1는 Controller의 비즈니스 로직 처리 결과가 ModelView이고,
     * ControllerV2는 Controller의 비즈니스 로직 처리 결과가 ViewName이다.
     * 이런 서로 다른 Controller의 성질에 맞게 MyHandlerAdapter를 구현하고 적용시킨다.
     *
     * @param request: HTTP request
     * @param response: HTTP response
     * @param handler: MyHandlerAdapter를 구현한 클래스 중, 각 Adapter와 연결하고자 하는 Handler(Controller)를 호출하기 위해 사용.
     *               e.g) ControllerV3가 들어올 지, ControllerV4가 들어올 지 모르기 때문에 Object 타입으로 받는다.
     *               어차피, handle()메서드를 타기 전에 supports() 메서드를 통해 handler의 타입을 확인하기 때문에 명시적 형변환이 가능.
     * @return

     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
