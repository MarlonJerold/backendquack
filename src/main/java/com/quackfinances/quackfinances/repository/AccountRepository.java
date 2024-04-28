package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findById(Long id);
}