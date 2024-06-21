package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.enums.CardEnum;
import com.quackfinances.quackfinances.model.User;
import com.quackfinances.quackfinances.repository.CardRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.CardService;
import com.quackfinances.quackfinances.dto.Card.CardRequestDTO;
import com.quackfinances.quackfinances.dto.Card.CardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardResponseDTO createCard(CardRequestDTO cardRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        CardEnum accountType = CardEnum.valueOf (String.valueOf(cardRequestDTO.cardType()));

        if (cardRequestDTO != null) {

            Optional<User> userBusca = userRepository.findByEmail(user);

            com.quackfinances.quackfinances.model.Card card = new com.quackfinances.quackfinances.model.Card();
            card.setCardName(cardRequestDTO.name());
            card.setCardEnum(accountType);
            card.setValue(cardRequestDTO.value());
            card.setValueUsed(cardRequestDTO.valueUsed());
            card.setUser(userBusca.get());
            card.setBankaName(cardRequestDTO.bankName());

            com.quackfinances.quackfinances.model.Card cardSave = cardRepository.save(card);

            CardResponseDTO cardDTO = new CardResponseDTO(
                    cardSave.getCardName(),
                    cardRequestDTO.cardType(),
                    cardSave.getValue(),
                    cardSave.getValueUsed(),
                    cardSave.getBankName().toString()
            );

            return cardDTO;

        } return null;
    }

    @Override
    public List<CardResponseDTO> getCardAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<com.quackfinances.quackfinances.model.Card> accountList = cardRepository.findAll();
        List<com.quackfinances.quackfinances.model.Card> accounts = new ArrayList<>();

        for (com.quackfinances.quackfinances.model.Card card : accountList) {
            if (card != null && card.getUser() != null && card.getUser().getName() != null) {
                if (card.getUser().getEmail().equals(userId)) {
                    accounts.add(card);
                }
            }
        }

        List<CardResponseDTO> cardDTOs = new ArrayList<>();

        for (com.quackfinances.quackfinances.model.Card card : accounts) {
            CardResponseDTO accountDTO = new CardResponseDTO(card.getCardName(), card.getCardEnum().toString(), card.getValue(), card.getValueUsed(), card.getBankName());
            cardDTOs.add(accountDTO);
        }

        return cardDTOs;
    }
}
