package com.quackfinances.quackfinances.view.controller.dto;

import com.quackfinances.quackfinances.model.AccountType;

import java.math.BigDecimal;

public record AccountUserLoginDTO(Integer id, String name, BigDecimal value, String createDate, AccountType accountType) {
}
