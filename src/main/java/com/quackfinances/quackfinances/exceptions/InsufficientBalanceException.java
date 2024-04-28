package com.quackfinances.quackfinances.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Saldo insuficiente na conta de origem");
    }
}