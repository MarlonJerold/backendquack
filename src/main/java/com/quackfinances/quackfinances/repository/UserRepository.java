package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);

    UserModel findUserById(Integer userId);
}
