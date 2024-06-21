package com.quackfinances.quackfinances.dto.Account;

import java.math.BigDecimal;

public record AccountCreateDTO(String name, BigDecimal value, String accountType) {
}

