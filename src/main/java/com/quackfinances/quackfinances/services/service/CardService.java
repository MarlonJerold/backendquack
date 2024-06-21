package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.dto.Card.CardRequestDTO;
import com.quackfinances.quackfinances.dto.Card.CardResponseDTO;

import java.util.List;

public interface CardService {

    List<CardResponseDTO> getCardAll();
    CardResponseDTO createCard(CardRequestDTO cardRequestDTO);
}
