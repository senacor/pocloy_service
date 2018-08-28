package com.senacor.bankathon2018.service.repository;

import com.senacor.bankathon2018.service.model.LoyaltyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCodeRepository extends JpaRepository<LoyaltyCode, String> {

}
