package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.dto.CardRequestDTO;
import com.quackfinances.quackfinances.dto.CardResponseDTO;

import java.util.List;

public interface CardServiceInterface {

    List<CardResponseDTO> getCardAll();
    CardResponseDTO createCard(CardRequestDTO cardRequestDTO);
}
