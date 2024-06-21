package com.quackfinances.quackfinances.dto;

import java.math.BigDecimal;

public record CardResponseDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName) {
}