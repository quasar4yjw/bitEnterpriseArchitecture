package java63.web03.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import java63.web03.dao.ProductDao;
import java63.web03.domain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/* Service 컴포넌트의 역할
 * => 비즈니스 로직 수행
 * => 트랜잭션 관리
 */

@Service
public class ProductService {
  @Autowired
  ProductDao productDao;
  
  public List<?> getList(int pageNo, int pageSize) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("startIndex", ((pageNo - 1) * pageSize));
    paramMap.put("pageSize", pageSize);
    
    return productDao.selectList(paramMap);
  }
  
  public int getMaxPageNo(int pageSize) {
    int totalSize = productDao.totalSize();
    int maxPageNo = totalSize / pageSize;
    if ((totalSize % pageSize) > 0 ) maxPageNo++;
    
    return maxPageNo;
  }
  
  /* @Transactional 선언하는 의미
     => 메서드 안의 입력/변경/삭제(manipluation*) 작업을    *API 문서에서 만남
     하나의 작업으로 묶는다.
     => 모든 작업이 성공했을 때만 서버에 반영한다.
     
  
  */
  
  @Transactional(
      rollbackFor=Exception.class, 
      propagation=Propagation.REQUIRED)
  public void add(Product product) {
    productDao.insert(product);
    productDao.insertPhoto(product);
  }
}

