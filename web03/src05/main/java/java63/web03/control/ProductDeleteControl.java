package java63.web03.control;

import java63.web03.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/* Redirect
 * => 클라이언트에게 콘텐츠를 보내지 않는다.
 * => 응답 헤더에 다시 요청할 주소를 보낸다.
 * 
 */

@Component
public class ProductDeleteControl {
	
	@Autowired
	ProductDao productDao;
	
	@RequestMapping("/product/delete.do")
	public String execute(@RequestParam int no)
			throws Exception {
		productDao.deletePhoto(no);
		productDao.delete(no);
		
		//해당 제품의 사진 경로를 알아내서
		//파일 시스템에서 지운다.
		
		return "redirect:list.do";
	}

}
