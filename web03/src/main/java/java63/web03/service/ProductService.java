package java63.web03.service;

import java.util.HashMap;
import java.util.List;
import java63.web03.dao.ProductDao;
import java63.web03.domain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     => 메서드 안의 입력/변경/삭제(manipulation*) 작업을    *API 문서에서 만남
     하나의 작업으로 묶는다.
     => 모든 작업이 성공했을 때만 서버에 반영한다.
     
  
  */
  
  @Transactional(
      rollbackFor=Throwable.class,  //Th 에러 객체 포함 // Ex 에러객체 제외.
      propagation=Propagation.REQUIRED)
  public void add(Product product) {
    productDao.insert(product);
    //product.setNo(1000);
    if (product.getPhoto() != null) {
      productDao.insertPhoto(product);
    }
  }
  
  @Transactional(
      rollbackFor=Exception.class,  //Th 에러 객체 포함 // Ex 에러객체 제외.
      propagation=Propagation.REQUIRED)
  public void update(Product product) {
    productDao.update(product);
  }
  
  @Transactional(
      rollbackFor=Exception.class,  //Th 에러 객체 포함 // Ex 에러객체 제외.
      propagation=Propagation.REQUIRED)
  public void delete(int productNo) {
    productDao.deletePhoto(productNo);
    productDao.delete(productNo);
  }
  
  public Product get(int productNo) {
    Product product = productDao.selectOne(productNo);
    product.setPhotoList( productDao.selectPhoto(productNo));
    //return productDao.selectOne(productNo);
    return product;
  }
}


