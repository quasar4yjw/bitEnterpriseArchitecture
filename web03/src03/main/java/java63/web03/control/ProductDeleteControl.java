package java63.web03.control;

import java63.web03.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* Redirect
 * => 클라이언트에게 콘텐츠를 보내지 않는다.
 * => 응답 헤더에 다시 요청할 주소를 보낸다.
 * 
 */

@Component("/product/delete.do")
public class ProductDeleteControl {
	
	@Autowired
	ProductDao productDao;
	
	public String execute(HttpServletRequest request)
			throws Exception {
		
		int no = Integer.parseInt(request.getParameter("no"));
		productDao.deletePhoto(no);
		productDao.delete(no);
		
		//해당 제품의 사진 경로를 알아내서
		//파일 시스템에서 지운다.
		
		return "redirect:list.do";
	}

}
