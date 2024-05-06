package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
    List<Optional<TransactionModel>> findBySourceAccountId(Integer sourceAccountId);

}
