package org.jsp.merchant.service;

import java.util.List;
import java.util.Optional;

import org.jsp.merchant.dao.MerchantDao;
import org.jsp.merchant.dao.ProductDao;
import org.jsp.merchant.dto.Merchant;
import org.jsp.merchant.dto.Product;
import org.jsp.merchant.dto.ResponseStructure;
import org.jsp.merchant.exception.MerchantNotFoundException;
import org.jsp.merchant.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private MerchantDao merchantDao;
	
	ResponseStructure<Product> stucture=new ResponseStructure<>();
	
	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product,int merchant_id){
		Optional<Merchant> db=merchantDao.findByid(merchant_id);
		if(db.isPresent()) {
			Merchant merchant=db.get();
			merchant.getProduct().add(product);
			product.setMerchant(merchant);
			merchantDao.saveMerchant(merchant);
			stucture.setData(productDao.saveProduct(product));
			stucture.setMessage("Product added");
			stucture.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Product>>(stucture, HttpStatus.CREATED);
		}
		throw new  MerchantNotFoundException();
	}
	
	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product){
		Optional<Product> db=productDao.findById(product.getId());
		if(db.isPresent()) {
			Product dbProduct=db.get();
			dbProduct.setBrand(product.getBrand());
			dbProduct.setCategory(product.getCategory());
			dbProduct.setDescription(product.getDescription());
			dbProduct.setCost(product.getCost());
			dbProduct.setImage_url(product.getImage_url());
			dbProduct.setName(product.getName());
			stucture.setData(productDao.saveProduct(product));
			stucture.setMessage("Product Updated");
			stucture.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Product>>(stucture,HttpStatus.ACCEPTED);
		}
		throw new ProductNotFoundException("Invalid ID");
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> findByBrand(String brand){
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		List<Product> db=productDao.findByBrand(brand);
		if(db.size()>0) {
			structure.setData(db);
			structure.setMessage("Product Found By Brand");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure,HttpStatus.OK);
		}
		throw new ProductNotFoundException("Invalid Brand");
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> findByCategory(String category){
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		List<Product> db=productDao.findByCategory(category);
		if(db.size()>0) {
			structure.setData(db);
			structure.setMessage("Product Found By Category");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure,HttpStatus.OK);
		}
		throw new ProductNotFoundException("Invalid Category");
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> findByMerchantId(int id){
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		List<Product> db=productDao.findByMerchantId(id);
		if(db.size()>0) {
			structure.setData(db);
			structure.setMessage("Product Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure,HttpStatus.OK);
		}
		throw new ProductNotFoundException("Invalid Merchant Id");
	}
	
	

}
