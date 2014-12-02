package java63.web03.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain nextFilter) throws IOException, ServletException {
		System.out.println(((HttpServletRequest)request).getRequestURL());
	
		
		if (!((HttpServletRequest)request).getServletPath().startsWith("/auth") &&
				((HttpServletRequest)request).getSession().getAttribute("loginUser") == null) {
			
			((HttpServletRequest)request).getSession().setAttribute("requestUrl", 
					((HttpServletRequest)request).getRequestURL() 
					+ "?" + ((HttpServletRequest)request).getQueryString());
			
			((HttpServletResponse)response).sendRedirect(
					request.getServletContext().getContextPath() // => /web03
					+ "/auth/login.do");
			return; 
		} else {
			nextFilter.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {	}

}
