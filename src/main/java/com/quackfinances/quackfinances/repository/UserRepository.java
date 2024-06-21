package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findUserById(Integer userId);
}
