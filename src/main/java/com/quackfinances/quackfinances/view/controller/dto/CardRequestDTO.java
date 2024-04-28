package com.quackfinances.quackfinances.view.controller.dto;

import com.quackfinances.quackfinances.model.CardType;

import java.math.BigDecimal;

public record CardRequestDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName) {
}
