package java63.web03.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java63.web03.dao.MakerDao;
import java63.web03.dao.ProductDao;
import java63.web03.domain.Product;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/* @RequestMapping
 * => 메서드에 URL을 연결한다.
 * => 이 애노테이션의 기능을 완전히 활용하려면,
 * 		이 애노테이션 처리기를 등록해야 한다.
 */

//방법 1) @Component("/product/add.do")
//방법 2) @Component 
//방법 3) @Component 
//방법 3) @RequestMapping("/product/add.do")
//방법 4) @Component 
//방법 4) @RequestMapping("/product")
//@Component // Spring IoC 컨테이너의 기본 객체를 지정할 때 주로 사용.
@Controller // Spring MVC의 컴포넌트(페이지 컨트롤러)임을 지정할 때 사용.
@RequestMapping("/product")
public class ProductControl {
	static Logger log = Logger.getLogger(ProductControl.class);
	static final int PAGE_DEFAULT_SIZE = 3;
	@Autowired MakerDao makerDao;
	@Autowired ProductDao productDao;
	@Autowired ServletContext servletContext;

	//방법 1) @RequestMapping
	//방법 2) @RequestMapping("/product/add.do")
	//방법 3) @RequestMapping
	//방법 4) @RequestMapping("/add.do")
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView form() throws Exception { // 5~6년전
		ModelAndView mv = new ModelAndView();
		mv.addObject("makers", makerDao.selectNameList());
		mv.setViewName("product/ProductForm");
		return mv;
	}

	// Model은 request에 담을 값을 보관할 임시 저장소
	/*
	public String form(Model model) throws Exception { //최근
		model.addAttribute("makers", makerDao.selectNameList());
		return "/product/ProductForm.jsp";
	}*/

	/* Map은 request에 담을 값을 보관할 임시 저장소
	public String form(Map<String,Object> model) throws Exception { // 때로는 어떤 개발자
		model.put("makers", makerDao.selectNameList());
		return "/product/ProductForm.jsp";
	}*/


	/* @RequestParam("파라미터명")
	 *  => 파라미터 값을 담을 변수임을 선언하는 것.
	 *  => 선언하지 않아도 파라미터와 이름이 같다면 (기본으로 파라미터) 값을 넣어 준다
	 *  => 단, MultipartFile인 경우는 선언해야 한다!
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST) //DispatcherServlet이 호출
	public String add(Product product)
					throws Exception {

		String fileuploadRealPath = servletContext.getRealPath("/fileupload");
		String filename = System.currentTimeMillis() + "_";
		File file = new File(fileuploadRealPath + "/" + filename);
		product.getPhotofile().transferTo(file);
    product.setPhoto(filename);


		/*Product product = new Product();
		product.setName(name);
		product.setQuantity(qty);
		product.setMakerNo(mkno);
		product.setPhoto(filename);*/
		//product.setPhoto(paramMap.get("photo"));

		productDao.insert(product);
		productDao.insertPhoto(product);
		return "forward:list.do";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int no)
			throws Exception {
		productDao.deletePhoto(no);
		productDao.delete(no);
		
		//해당 제품의 사진 경로를 알아내서
		//파일 시스템에서 지운다.
		
		return "redirect:list.do";
	}

	
	
	
	
	
	/* @RequestMapping
	 * => 요청을 처리할 메서드를 지정하는 애노테이션
	 * => 즉 이 메서드를 호출해라!!
	 */
	@RequestMapping("/list")
	public String list(
			@RequestParam(defaultValue="0")int pageNo,
			@RequestParam(defaultValue="0")int pageSize,
			Model model)
			throws Exception, IOException {


		
			

		
			if (pageNo > 0) {
				pageSize = PAGE_DEFAULT_SIZE;
			}

		/*	if (request.getParameter("pageSize") != null) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}*/
			
			/*	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();*/


			
			HashMap<String,Object> paramMap = new HashMap<>();
	    paramMap.put("startIndex", ((pageNo - 1) * pageSize));
	    paramMap.put("pageSize", pageSize);


			//List<Product> products = productDao.selectList(pageNo, pageSize);
			model.addAttribute("products", productDao.selectList(paramMap));

			// include를 수행할 때는 여기에서 콘텐츠 타입을 설정해야 한다.

			// 결과를 출력하기 위해 JSP에게 위임한다.

			// 다른 서블릿을 실행 => 실행 후 되돌아 제어권이 되돌아 온다.
/*			RequestDispatcher rd = 
					request.getRequestDispatcher("/product/ProductList.jsp");
			rd.include(request, response);*/
			
			return "product/ProductList";


	}

	
	
	@RequestMapping("/update")
	public String update(Product product)
			throws Exception {

/*		Product product = new Product();
		product.setNo(Integer.parseInt(request.getParameter("no")));
		product.setName(request.getParameter("name"));
		product.setQuantity(Integer.parseInt(request.getParameter("qty")));
		product.setMakerNo(Integer.parseInt(request.getParameter("mkno")));*/
		
		productDao.update(product);

		return "redirect:list.do";
	}
	
	
	
	
	
	
	@RequestMapping("/view")
	public String view(int no, Model model)
			throws Exception{

		Product product = productDao.selectOne(no);
		model.addAttribute("product", product);
		model.addAttribute("photos", 
				productDao.selectPhoto(product.getNo()));
		
		model.addAttribute("makers", makerDao.selectNameList());
			return "product/ProductView";
	}
	
	
	
}
