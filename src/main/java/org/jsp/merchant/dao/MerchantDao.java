package org.jsp.merchant.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.merchant.dto.Merchant;
import org.jsp.merchant.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class MerchantDao {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	public Merchant saveMerchant(Merchant merchant) {
		return merchantRepository.save(merchant);
	}
	
	public Optional<Merchant> findByid(int id){
		return merchantRepository.findById(id);
	}
	
	public List<Merchant> findAll(Merchant merchant){
		return merchantRepository.findAll();
	}

}
