package java63.web03.control;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// @Component("/auth/logout.do")
@Controller
public class LogoutControl {

	@RequestMapping("/auth/logout.do")
	public String execute(
			HttpSession session)
					throws Exception {
		//request.getSession().invalidate();
	  session.invalidate();
		return "redirect:login.do";
	}


}
