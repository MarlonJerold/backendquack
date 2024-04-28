package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.model.*;
import com.quackfinances.quackfinances.repository.CardRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardRequestDTO;
import com.quackfinances.quackfinances.view.controller.dto.CardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/card")
@RestController
public class CardController {

    private CardRepository cardRepository;
    private UserRepository userRepository;

    @Autowired
    CardController(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public CardResponseDTO createCard(@RequestBody CardRequestDTO cardRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        CardType accountType = CardType.valueOf (String.valueOf(cardRequestDTO.cardType()));

        if (cardRequestDTO != null) {

            Optional<UserModel> userBusca = userRepository.findByEmail(user);

            Card card = new Card();
            card.setCardName(cardRequestDTO.name());
            card.setCardType(accountType);
            card.setValue(cardRequestDTO.value());
            card.setValueUsed(cardRequestDTO.valueUsed());
            card.setUser(userBusca.get());
            card.setBankaName(cardRequestDTO.bankName());

            Card cardSave = cardRepository.save(card);

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

    @GetMapping
    public List<CardResponseDTO> getCardAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Card> accountList = cardRepository.findAll();
        List<Card> accounts = new ArrayList<>();

        for (Card card : accountList) {
            if (card != null && card.getUser() != null && card.getUser().getName() != null) {
                if (card.getUser().getEmail().equals(userId)) {
                    accounts.add(card);
                }
            }
        }

        List<CardResponseDTO> cardDTOs = new ArrayList<>();

        for (Card card : accounts) {
            CardResponseDTO accountDTO = new CardResponseDTO(card.getCardName(), card.getCardType().toString(), card.getValue(), card.getValueUsed(), card.getBankName());
            cardDTOs.add(accountDTO);
        }

        return cardDTOs;
    }


}
