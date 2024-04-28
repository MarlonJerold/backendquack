package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.model.UserModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
    List<Optional<TransactionModel>> findBySourceAccountId(Integer sourceAccountId);

}
