package com.quackfinances.quackfinances.dto.Card;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardResponseDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName,  LocalDate invoiceDate) {

    public CardResponseDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName) {
        this(name, cardType, value, valueUsed, bankName, null);
    }
}