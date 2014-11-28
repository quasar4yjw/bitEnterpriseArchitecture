package java63.web03.control;

import java.io.IOException;
import java.util.HashMap;
import java63.web03.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Component("/product/list.do")
@Controller
public class ProductListControl{
	static final int PAGE_DEFAULT_SIZE = 3;
	
	
	@Autowired
	ProductDao productDao;
	
	/* @RequestMapping
	 * => 요청을 처리할 메서드를 지정하는 애노테이션
	 * => 즉 이 메서드를 호출해라!!
	 */
	@RequestMapping("/product/list.do")
	public String execute(
			HttpServletRequest request)
			throws Exception, IOException {


		
			
		int pageNo = 0;
		int pageSize = 0;

		
			if (request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
				pageSize = PAGE_DEFAULT_SIZE;
			}

			if (request.getParameter("pageSize") != null) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
			/*	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();*/


			
			HashMap<String,Object> paramMap = new HashMap<>();
	    paramMap.put("startIndex", ((pageNo - 1) * pageSize));
	    paramMap.put("pageSize", pageSize);


			//List<Product> products = productDao.selectList(pageNo, pageSize);
			request.setAttribute("products", productDao.selectList(paramMap));

			// include를 수행할 때는 여기에서 콘텐츠 타입을 설정해야 한다.

			// 결과를 출력하기 위해 JSP에게 위임한다.

			// 다른 서블릿을 실행 => 실행 후 되돌아 제어권이 되돌아 온다.
/*			RequestDispatcher rd = 
					request.getRequestDispatcher("/product/ProductList.jsp");
			rd.include(request, response);*/
			
			return "/product/ProductList.jsp";


	}

}
