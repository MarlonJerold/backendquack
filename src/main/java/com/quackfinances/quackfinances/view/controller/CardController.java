package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.model.*;
import com.quackfinances.quackfinances.repository.CardRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.CardServiceInterface;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardRequestDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/card")
@RestController
public class CardController {

    @Autowired(required = false)
    private CardServiceInterface cardServiceInterface;

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardRequestDTO cardRequestDTO) {
        return ResponseEntity.ok(cardServiceInterface.createCard(cardRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getCardAll() {
        return ResponseEntity.of(Optional.ofNullable(cardServiceInterface.getCardAll()));
    }
}
