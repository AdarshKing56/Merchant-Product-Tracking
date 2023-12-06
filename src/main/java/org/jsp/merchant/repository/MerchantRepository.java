package org.jsp.merchant.repository;

import org.jsp.merchant.dto.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer>{

}
