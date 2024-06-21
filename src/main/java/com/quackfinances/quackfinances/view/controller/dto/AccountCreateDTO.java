package com.quackfinances.quackfinances.view.controller.dto;

import java.math.BigDecimal;

public record AccountCreateDTO(String name, BigDecimal value, String accountType) {
}

