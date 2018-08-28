package com.senacor.bankathon2018.service.repository;

import com.senacor.bankathon2018.service.model.BoughtVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoughtVoucherRepository extends JpaRepository<BoughtVoucher, Long> {

  List<BoughtVoucher> findByUser(String user);

}