package com.senacor.bankathon2018.service.repository;

import com.senacor.bankathon2018.service.model.LoyaltyCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCodeRepository extends JpaRepository<LoyaltyCode, String> {

  List<LoyaltyCode> findByUserAndDeletedFalse(String user);
  List<LoyaltyCode> findByUser(String user);

}
