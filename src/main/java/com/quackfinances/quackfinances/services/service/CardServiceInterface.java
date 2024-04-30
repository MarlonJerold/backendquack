package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.view.controller.dto.CardRequestDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardResponseDTO;

import java.util.List;

public interface CardServiceInterface {

    List<CardResponseDTO> getCardAll();
    CardResponseDTO createCard(CardRequestDTO cardRequestDTO);
}
