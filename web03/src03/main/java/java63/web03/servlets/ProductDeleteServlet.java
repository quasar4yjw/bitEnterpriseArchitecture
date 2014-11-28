package java63.web03.servlets;

import java.io.IOException;
import java63.web03.dao.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/* Redirect
 * => 클라이언트에게 콘텐츠를 보내지 않는다.
 * => 응답 헤더에 다시 요청할 주소를 보낸다.
 * 
 */

//@WebServlet("/product/delete.do")
public class ProductDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("list.do");
		int no = Integer.parseInt(request.getParameter("no"));
		//AppInitServlet.productDao.delete(no);
		//ContextLoaderListener.productDao.delete(no);

		// ProductDao를 ServletContext 보관소에서 꺼내는 방식을 사용
		// => 단점: 위의 방식보다 코드가 늘었다.
		// => 장점: 특정 클래스에 종속되지 않는다. 유지보수에서 더 중요!
		//ProductDao productDao = (ProductDao)this.getServletContext()
		//		.getAttribute("productDao");
		/*ProductDao productDao = (ProductDao)ContextLoaderListener.appCtx
				.getBean("productDao");*/
		
	//스프링의 ContextLoaderListener가 준비한
			//ApplicationContext 객체 꺼내기
		ApplicationContext appCtx =
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

		ProductDao productDao = (ProductDao)appCtx.getBean("productDao");
		productDao.delete(no);
		

		/* Redirect 는 클라이언트에 재요청 URL만 보낸다.
		 * 따라서 이전에 출력한 콘텐츠는 취소한다.
		 * => 버퍼에 출력된 내용은 클라이언트로 보내지 않고 버린다.
		 * => 위의 출력문 작성할 필요 없다
		 */

		/*HttpServletResponse originResponse = (HttpServletResponse)response;
		originResponse.sendRedirect("list");*/

		/* Redirect의 응답 내용 => Location 헤더에 재요청 URL이 있다.
		 * => 클라이언트에 어떤 내용을 출력하지 않고,
		 *    페이지를 바로 전환하는 효과를 내고 싶을 때
		 * 기트허브 참조
		 */
	}

}
