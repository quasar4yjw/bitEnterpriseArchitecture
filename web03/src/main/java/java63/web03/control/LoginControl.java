package java63.web03.control;

import java.util.ArrayList;
import java.util.HashMap;

import java63.web03.dao.MemberDao;
import java63.web03.domain.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

/* POST 요청 처리
 *  => 한글이 깨지는 문제 해결
 *  => getParameter()를 호출하기 전에
 *     request.setCharacterEncoding("UTF-8") 호출하라!
 *     => 클라이언트가 보내는 데이터의 문자 집합을 알려줘라(지정하라!!!)
 */

@Component("/auth/login.do")
public class LoginControl {

	@Autowired MemberDao memberDao;

	@RequestMapping
	public String execute(
			HttpServletRequest request)	throws Exception {

		if (request.getMethod().equals("GET")){
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie :cookies) {
					if (cookie.getName().equals("uid")) {
						request.setAttribute("uid", cookie.getValue());
					}
				}

			}

			return "/auth/LoginForm.jsp";
		} else {


			String uid = request.getParameter("uid");
			String pwd = request.getParameter("pwd");
			String save = request.getParameter("save");

			System.out.println(uid);
			System.out.println(pwd);
			System.out.println(save);
			
			ArrayList<Cookie> cookieList = new ArrayList<>();
			
			if (save != null) { // 쿠키로 아이디 저장
				Cookie cookie = new Cookie("uid", uid);
				cookie.setMaxAge(60 * 60 * 24 * 15);
				//response.addCookie(cookie);
				cookieList.add(cookie);
			} else {
				Cookie cookie = new Cookie("uid", ""); // 빈문자열 안먹음
				cookie.setMaxAge(0); // 무효화시킴
				//response.addCookie(cookie);
				cookieList.add(cookie);
			}

			request.setAttribute("cookieList", cookieList);
			
			HashMap<String,String> params = new HashMap<>();
			params.put("userId", uid);
			params.put("password", pwd);
			Member member = memberDao.existUser(params);
			
			HttpSession session = request.getSession();
			if (member != null) {
				session.setAttribute("loginUser", member);

				if (session.getAttribute("requestUrl") != null) {
					return "redirect:" +
							(String)session.getAttribute("requestUrl");
				} else {
					return "redirect:../product/list.do";
				}
			} else {
				session.invalidate();//세션을 제거하고 새로 만든다.
				return "redirect:login.do";// 로그인 폼으로 보낸다.
			}

		}



	}

	

}
