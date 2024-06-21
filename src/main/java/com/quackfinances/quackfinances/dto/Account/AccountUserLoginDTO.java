package com.quackfinances.quackfinances.dto.Account;

import com.quackfinances.quackfinances.enums.AccountEnum;

import java.math.BigDecimal;

public record AccountUserLoginDTO(Integer id, String name, BigDecimal value, String createDate, AccountEnum accountEnum) {
}
