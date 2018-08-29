package com.senacor.bankathon2018.service.repository;

import com.senacor.bankathon2018.service.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FigoLoginTable extends JpaRepository<UserDto, String> {
}
