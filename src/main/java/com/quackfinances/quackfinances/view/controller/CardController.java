package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.services.service.CardServiceInterface;
import com.quackfinances.quackfinances.view.controller.dto.CardRequestDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
