package java63.web03.servlets;

import java.io.IOException;
import java63.web03.dao.ProductDao;
import java63.web03.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/* POST 요청 처리
 *  => 한글이 깨지는 문제 해결
 *  => getParameter()를 호출하기 전에
 *     request.setCharacterEncoding("UTF-8") 호출하라!
 *     => 클라이언트가 보내는 데이터의 문자 집합을 알려줘라(지정하라!!!)
 */

//@WebServlet("/product/update.do")
public class ProductUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 다음 코드는 필터로 대체함.
		//request.setCharacterEncoding("UTF-8");

		Product product = new Product();
		product.setNo(Integer.parseInt(request.getParameter("no")));
		product.setName(request.getParameter("name"));
		product.setQuantity(Integer.parseInt(request.getParameter("qty")));
		product.setMakerNo(Integer.parseInt(request.getParameter("mkno")));


		//AppInitServlet.productDao.update(product);
		//ContextLoaderListener.productDao.update(product);

		// ProductDao를 ServletContext 보관소에서 꺼내는 방식을 사용
		// => 단점: 위의 방식보다 코드가 늘었다.
		// => 장점: 특정 클래스에 종속되지 않는다. 유지보수에서 더 중요!
		//ProductDao productDao = (ProductDao)this.getServletContext()
		//		.getAttribute("productDao");
		/*ProductDao productDao = (ProductDao)ContextLoaderListener.appCtx
				.getBean("productDao");*/
		
		ApplicationContext appCtx =
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

		ProductDao productDao = (ProductDao)appCtx.getBean("productDao");
		
		productDao.update(product);

		response.sendRedirect("list.do");
	}

}
