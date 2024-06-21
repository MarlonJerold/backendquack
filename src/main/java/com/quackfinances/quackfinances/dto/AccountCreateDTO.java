package com.quackfinances.quackfinances.dto;

import java.math.BigDecimal;

public record AccountCreateDTO(String name, BigDecimal value, String accountType) {
}

