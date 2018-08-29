package com.senacor.bankathon2018.service.repository;

import com.senacor.bankathon2018.service.model.ExchangeOffer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {

  List<ExchangeOffer> findByOfferingUser(String user);

  List<ExchangeOffer> findByOfferingUserNot(String user);
}
