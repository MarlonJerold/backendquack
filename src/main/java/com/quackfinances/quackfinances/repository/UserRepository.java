package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);

    UserModel findUserById(Integer userId);
}
