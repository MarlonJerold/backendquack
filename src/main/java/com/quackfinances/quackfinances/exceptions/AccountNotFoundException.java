package com.quackfinances.quackfinances.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("Conta de origem não encontrada");
    }
}
