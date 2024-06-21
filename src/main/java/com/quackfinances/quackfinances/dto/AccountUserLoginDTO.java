package com.quackfinances.quackfinances.dto;

import com.quackfinances.quackfinances.enums.AccountType;

import java.math.BigDecimal;

public record AccountUserLoginDTO(Integer id, String name, BigDecimal value, String createDate, AccountType accountType) {
}
