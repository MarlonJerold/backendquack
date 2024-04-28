package com.quackfinances.quackfinances.view.controller.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record AccountCreateDTO(String name, BigDecimal value, String accountType) {
}

