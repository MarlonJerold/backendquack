package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
