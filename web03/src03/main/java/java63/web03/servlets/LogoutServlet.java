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

//@WebServlet("/auth/logout.do")
public class LogoutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("login.do");
	}

	

}
