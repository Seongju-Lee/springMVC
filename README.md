# MVC & Spring MVC

## MVC 패턴
비즈니스 로직과 화면을 구분하는데 중점을 둔다.  
**관심사 분리**를 통해 더 나은 유지보수가 가능해진다.

- 뷰와 비즈니스 로직을 변경하는 시점은 다르고, 각 변경은 서로에게 영향을 주지 않는다.  
즉, 변경의 라이프 사이클이 다른 부분을 정확하게 분리하여 효과적인 유지보수를 기대한다.

### Model, View, Controller
동적인 처리를 위해 하나의 템플릿 엔진으로 처리하던 것을 비즈니스 로직(Controller)와 뷰(View)로 분리한다.  
그리고, Controller에서 만든 데이터(Model)를 View에게 넘겨줌으로써 관심사를 분리한다.
- Controller: HTTP 요청을 받아서 파라미터를 검증한다. 비즈니스 로직을 수행 후, 결과 데이터를 Model에 담는다.
- Model: View에 출력할 데이터들을 담아놓은 객체. View단에선 Model에서만 데이터를 찾고, 비즈니스 로직은 전혀 관여하지 않는다.
- View: Model을 이용해서 화면단을 처리한다.


## Spring MVC
본 Repository는 Spring MVC 구조를 분석하고 구현해본다. (김영한_스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술)  

### FrontController 패턴
- 프론트 컨트롤러를 배치함으로써 request를 처리하고, 그에 맞는 Controller를 호출.
- 스프링 웹 MVC의 DispatcherServlet이 이러한 Front-Controller 패턴으로 구현.

## 단계별 Spring MVC 구조
[[MVC패턴 적용_v1](./src/main/java/sj/springMVC/web/frontcontroller/v1)]  
- Front-Controller를 제외한 모든 Controller가 서블릿에 종속적이지 않도록 설계.  
  - JSP에서 model로써 사용하는 request 객체 대신, 별도의 Model 객체를 생성하여 반환.
  - 요청 파라미터를 HttpServletRequest를 사용하는 대신, Front-Controller에서 요청 정보를 새로운 ParamMap변수에 매핑.

- 각각의 Controller에서 서로 다른 View의 이름만 사용함으로써, 경로의 중복을 없앤다.
  - viewResolver 활용.


[[MVC패턴 적용_v2](./src/main/java/sj/springMVC/web/frontcontroller/v2)]
- Controller의 return type을 View가 아닌, view의 이름만 반환하도록 하여 개발자의 편의성을 올림.
  - ModelAndView를 반환 했었으나, Model 객체를 따로 넘겨줌으로써 Controller에선 비즈니스 로직에만 집중할 수 있다.

[[MVC패턴 적용_v3](./src/main/java/sj/springMVC/web/frontcontroller/v3)]
- Front-Controller에서 여러가지 Controller 인터페이스를 사용할 수 있도록 변경
  - Adapter 패턴을 적용시켜, Controller의 종류를 쉽게 확장시킬 수 있다.
  - _e.g) Spring MVC에서의 HandlerAdapter 인터페이스_
    - Annotation 기반의 컨트롤러를 만들고, Annotation을 지원하는 Adapter를 추가한다.
