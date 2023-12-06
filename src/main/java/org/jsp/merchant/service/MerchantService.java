package org.jsp.merchant.service;

import java.util.List;
import java.util.Optional;

import org.jsp.merchant.dao.MerchantDao;
import org.jsp.merchant.dto.Merchant;
import org.jsp.merchant.dto.Product;
import org.jsp.merchant.dto.ResponseStructure;
import org.jsp.merchant.exception.MerchantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

	@Autowired
	private MerchantDao dao;
	ResponseStructure<Merchant> stucture = new ResponseStructure<>();

	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(Merchant merchant) {
		stucture.setData(dao.saveMerchant(merchant));
		stucture.setMessage("Merchant Save");
		stucture.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Merchant>>(stucture, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(Merchant merchant){
		Optional<Merchant> db=dao.findByid(merchant.getId());
		if(db.isPresent()) {
			Merchant dbmerchant=db.get();
			dbmerchant.setName(merchant.getName());
			dbmerchant.setEmail(merchant.getEmail());
			dbmerchant.setPhone(merchant.getPhone());
			dbmerchant.setPassword(merchant.getPassword());
			stucture.setData(dao.saveMerchant(dbmerchant));
			stucture.setMessage("Merchant Updated");
			stucture.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(stucture,HttpStatus.ACCEPTED);
		}
		throw new MerchantNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Merchant>> findById(int id){
		Optional<Merchant> db=dao.findByid(id);
		if(db.isPresent()) {
			stucture.setData(db.get());
			stucture.setMessage("Merchant Found With Id "+id+" ");
			stucture.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(stucture,HttpStatus.OK);
		}
		throw new MerchantNotFoundException();
	}
	public ResponseEntity<ResponseStructure<List<Merchant>>> findAll(Merchant merchant){
		ResponseStructure<List<Merchant>> stucture = new ResponseStructure<>();
		List<Merchant> ls=dao.findAll(merchant);
		if(ls.size()>0) {
			stucture.setData(ls);
			stucture.setMessage("List of all Merchant");
			stucture.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Merchant>>>(stucture, HttpStatus.OK);
		}
		throw new MerchantNotFoundException();
	}
}
