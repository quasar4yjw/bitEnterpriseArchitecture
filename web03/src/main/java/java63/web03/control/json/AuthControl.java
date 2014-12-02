package java63.web03.control.json;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller("json.authControl")
@RequestMapping("/json/auth")
/* @SessionAttributes
 * => Model에 저장되는 값 중에서 세션에 저장될 객체를 지정한다.
 * => 사용법
 *    @SessionAttributes({"key", "key", ...})
 */
// 만약 Model에 loginUser라는 이름으로 값을 저장한다면
// 그 값은 request에 보관하지 말고 session에 보관하라!
// 그 값은 세션에 있는 값이다.
@SessionAttributes({"loginUser", "requestUrl"})
public class AuthControl {

	@Autowired MemberDao memberDao;

	
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
	@RequestMapping(value="/loginUser", method=RequestMethod.GET)
	public Object loginUser(HttpSession session)	throws Exception {
		HashMap<String,Object> resultMap = new HashMap<>();
		if (session.getAttribute("loginUser") != null) {
			resultMap.put("status", "success");
			resultMap.put("loginUser", session.getAttribute("loginUser"));
		} else {
			resultMap.put("status", "fail");
		}
		return resultMap;//"json/auth/LoginUser"; // "/WEB-INF/view/auth/LoginForm.jsp";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String form(@CookieValue(/*required=false*/defaultValue="") String uid, Model model)	throws Exception {
		model.addAttribute("uid", uid);
		return "auth/LoginForm"; // "/WEB-INF/view/auth/LoginForm.jsp";
	}
		 @RequestMapping(value="/login", method=RequestMethod.POST)
			public String login(
					String uid, 
					String pwd, 
					String save,
					String requestUrl,
					HttpServletResponse response,
					Model model,
					SessionStatus status)	throws Exception {

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
			      	model.addAttribute("loginUser", member);
			      	if (requestUrl != null) {
				      return "redirect:" + requestUrl;
				} else {
					return "redirect:../product/list.do";
				}
			} else {
				      //@SessionAttributes로 지정된 값을 무효화시킨다.
				      // => 주의!!!! 세션 전체를 무효화시키지 않는다.
				      status.setComplete();
				return "redirect:login.do";// 로그인 폼으로 보낸다.
			}

		}
		 
			@RequestMapping("/logout")
			public String execute(
					SessionStatus status)
							throws Exception {
			    status.setComplete();
				return "redirect:login.do";
			}



	}

	


