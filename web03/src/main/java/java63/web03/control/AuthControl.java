package java63.web03.control;

import java.util.HashMap;

import java63.web03.dao.MemberDao;
import java63.web03.domain.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* POST 요청 처리
 *  => 한글이 깨지는 문제 해결
 *  => getParameter()를 호출하기 전에
 *     request.setCharacterEncoding("UTF-8") 호출하라!
 *     => 클라이언트가 보내는 데이터의 문자 집합을 알려줘라(지정하라!!!)
 */
//@Component("/auth/logout.do")
@Controller
@RequestMapping("/auth")
public class AuthControl {

	@Autowired MemberDao memberDao;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	
	/*@CookieValue
	 * => 요청 헤더에서 쿠키 값을 꺼낸다.
	 * => 기본은 필수 항목이다.
	 * => 쿠키가 없으면 다음 오류가 뜬다.
	 * 
	 * required => 필수 여부 지정(기본은 true)
	 * defaultValue => 기본 값 지정(값이 없을 때 지정될 값)
	 */
	// HTTP Status 400 -
	// The request sent by the client was syntactically incorrect.
	// 파라미터 타입(int => Date)을 변경할 수 없거나 필수 파라미터가 넘어오지 않을 때
	
	public String form(@CookieValue(/*required=false*/defaultValue="") String uid, Model model)	throws Exception {
		model.addAttribute("uid", uid);
		return "/auth/LoginForm.jsp";
	}
		 @RequestMapping(value="/login", method=RequestMethod.POST)
			public String login(
					String uid, 
					String pwd, 
					String save,
					HttpServletResponse response,
					HttpSession session)	throws Exception {

			/*System.out.println(uid);
			System.out.println(pwd);
			System.out.println(save);*/
			
			/*ArrayList<Cookie> cookieList = new ArrayList<>();*/
			
			if (save != null) { // 쿠키로 아이디 저장
				Cookie cookie = new Cookie("uid", uid);
				cookie.setMaxAge(60 * 60 * 24 * 15);
				response.addCookie(cookie);
				/*cookieList.add(cookie);*/
			} else {
				Cookie cookie = new Cookie("uid", ""); // 빈문자열 안먹음
				cookie.setMaxAge(0); // 무효화시킴
				response.addCookie(cookie);
				/*cookieList.add(cookie);*/
			}

			/*request.setAttribute("cookieList", cookieList);*/
			
			HashMap<String,String> params = new HashMap<>();
			params.put("userId", uid);
			params.put("password", pwd);
			Member member = memberDao.existUser(params);
			
			/*HttpSession session = request.getSession();*/
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
		 
			@RequestMapping("/logout")
			public String execute(
					HttpSession session)
							throws Exception {
				//request.getSession().invalidate();
			  session.invalidate();
				return "redirect:login.do";
			}



	}

	


