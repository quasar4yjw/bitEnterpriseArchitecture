package java63.web03.domain;

import java.io.Serializable;

public class ProductPhoto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected int 		no;
	protected int 		productNo;
	protected String 	url;
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ProductPhoto [no=" + no + ", productNo=" + productNo + ", url="
				+ url + "]";
	}
	
	
	
	
	
} //9:23 toString반복문 ~ mac air 20xx 9:27 Tomcat 혼자
// 10:02 soft dev japan
