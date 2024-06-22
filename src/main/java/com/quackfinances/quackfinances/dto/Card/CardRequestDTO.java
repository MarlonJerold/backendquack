package com.quackfinances.quackfinances.dto.Card;

import java.math.BigDecimal;

public record CardRequestDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName, String invoiceDate) {

    public CardRequestDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName) {
      this (name, cardType, value, valueUsed, bankName, null) ;
    }
}
