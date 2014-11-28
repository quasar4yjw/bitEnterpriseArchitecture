package java63.web03.servlets;

import java.io.IOException;
import java.util.HashMap;
import java63.web03.dao.MemberDao;
import java63.web03.domain.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/* POST 요청 처리
 *  => 한글이 깨지는 문제 해결
 *  => getParameter()를 호출하기 전에
 *     request.setCharacterEncoding("UTF-8") 호출하라!
 *     => 클라이언트가 보내는 데이터의 문자 집합을 알려줘라(지정하라!!!)
 */

//@WebServlet("/auth/login.do")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie :cookies) {
				if (cookie.getName().equals("uid")) {
					request.setAttribute("uid", cookie.getValue());
				}
			}

		}



		/*	ApplicationContext appCtx =
				WebApplicationContextUtils.getWebApplicationContext(
						this.getServletContext());
		MakerDao makerDao = (MakerDao)appCtx.getBean("makerDao");
		request.setAttribute("makers", makerDao.selectNameList());*/

		RequestDispatcher rd = 
				request.getRequestDispatcher("/auth/LoginForm.jsp");
		rd.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String uid = request.getParameter("uid");
			String pwd = request.getParameter("pwd");
			String save = request.getParameter("save");

			System.out.println(uid);
			System.out.println(pwd);
			System.out.println(save);

			if (save != null) { // 쿠키로 아이디 저장
				Cookie cookie = new Cookie("uid", uid);
				cookie.setMaxAge(60 * 60 * 24 * 15);
				response.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("uid", ""); // 빈문자열 안먹음
				cookie.setMaxAge(0); // 무효화시킴
				response.addCookie(cookie);
			}


			ApplicationContext appCtx =
					WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

			MemberDao memberDao = (MemberDao)appCtx.getBean("memberDao");
			HashMap<String,String> params = new HashMap<>();
			params.put("userId", uid);
			params.put("password", pwd);
			Member member = memberDao.existUser(params);
			HttpSession session = request.getSession();
			if (member != null) {
				session.setAttribute("loginUser", member);

				if (session.getAttribute("requestUrl") != null) {
					response.sendRedirect(
							(String)session.getAttribute("requestUrl"));
				} else {
					response.sendRedirect("../product/list.do");
				}
				return;
			} else {
				session.invalidate();//세션을 제거하고 새로 만든다.
				response.sendRedirect("login.do");// 로그인 폼으로 보낸다.
			}


			/*Map<String, String> paramMap = FileUploadHelper.parse(request); 


/*
			// 다음 코드는 필터로 대체함.
			//request.setCharacterEncoding("UTF-8");

			Product product = new Product();
			product.setName(paramMap.get("name"));
			product.setQuantity(Integer.parseInt(paramMap.get("qty")));
			product.setMakerNo(Integer.parseInt(paramMap.get("mkno")));
			product.setPhoto(paramMap.get("photo"));


			//ProductDao productDao = (ProductDao)ContextLoaderListener.appCtx
			//		.getBean("productDao");

			//스프링의 ContextLoaderListener가 준비한
			//ApplicationContext 객체 꺼내기
			ApplicationContext appCtx =
					WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

			ProductDao productDao = (ProductDao)appCtx.getBean("productDao");
			productDao.insert(product);

			//Map<String,Object> params = new HashMap<>();
			//params.put("productNo", );
			//params.put("photo", paramMap.get("photo"));
			productDao.insertPhoto(product);


			response.sendRedirect("list.do");*/

		} catch (Exception e) {
			/* Forward로 다른 서블릿에게 제어권 위임하기
			 * => 제어권이 넘어가면 돌아오지 않는다.
			 */
			// 다른 서블릿을 실행 => 실행 후 되돌아 제어권이 되돌아 온다.
			RequestDispatcher rd = 
					request.getRequestDispatcher("/common/Error.jsp");
			request.setAttribute("error", e);
			rd.forward(request, response);
		}

		//response.sendRedirect("list.do");

		/*HttpServletResponse originResponse = (HttpServletResponse)response;
		originResponse.sendRedirect("list.do");*/
	}

}
