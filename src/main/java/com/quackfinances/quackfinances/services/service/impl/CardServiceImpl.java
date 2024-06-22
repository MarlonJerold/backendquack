package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.enums.CardEnum;
import com.quackfinances.quackfinances.model.Card;
import com.quackfinances.quackfinances.model.User;
import com.quackfinances.quackfinances.repository.CardRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.CardService;
import com.quackfinances.quackfinances.dto.Card.CardRequestDTO;
import com.quackfinances.quackfinances.dto.Card.CardResponseDTO;
import com.quackfinances.quackfinances.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;
    private DateTimeFormatter dateTimeFormatter = DateTimeUtils.FORMATTER;

    @Override
    public CardResponseDTO createCard(CardRequestDTO cardRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        CardEnum accountType = CardEnum.valueOf(cardRequestDTO.cardType().toUpperCase());

        if (cardRequestDTO != null) {

            Optional<User> userBusca = userRepository.findByEmail(user);

            if (accountType == CardEnum.DEBIT) {
                Card card = new Card.Builder()
                        .withCardName(cardRequestDTO.name())
                        .withCardEnum(CardEnum.DEBIT)
                        .withCurrentValue(cardRequestDTO.value())
                        .withValueUsed(cardRequestDTO.valueUsed())
                        .withUser(userBusca.get())
                        .build();

                Card cardSave = cardRepository.save(card);

                CardResponseDTO cardDTO = new CardResponseDTO(
                        cardSave.getCardName(),
                        cardRequestDTO.cardType(),
                        cardSave.getCurrentValue(),
                        cardSave.getValueUsed(),
                        cardSave.getBankName().toString()
                );

                return cardDTO;
            }

            if (accountType == CardEnum.CREDIT) {
                Card card = new Card.Builder()
                        .withCardName(cardRequestDTO.name())
                        .withCardEnum(CardEnum.CREDIT)
                        .withInvoiceDate(LocalDate.parse(cardRequestDTO.invoiceDate(), dateTimeFormatter))
                        .withCurrentValue(cardRequestDTO.value())
                        .withValueUsed(cardRequestDTO.valueUsed())
                        .withBankName(cardRequestDTO.bankName())
                        .withUser(userBusca.get())
                        .build();

                Card cardSave = cardRepository.save(card);

                CardResponseDTO cardDTO = new CardResponseDTO(
                        cardSave.getCardName(),
                        cardRequestDTO.cardType(),
                        cardSave.getCurrentValue(),
                        cardSave.getValueUsed(),
                        cardSave.getBankName().toString(),
                        cardSave.getInvoiceDate()
                );

                return cardDTO;
            }

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
            CardResponseDTO accountDTO = new CardResponseDTO(card.getCardName(), card.getCardEnum().toString(), card.getCurrentValue(), card.getValueUsed(), card.getBankName());
            cardDTOs.add(accountDTO);
        }

        return cardDTOs;
    }
}
