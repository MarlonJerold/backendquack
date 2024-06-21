package com.quackfinances.quackfinances.dto;

import java.math.BigDecimal;

public record CardRequestDTO(String name, String cardType, BigDecimal value, BigDecimal valueUsed, String bankName) {
}
