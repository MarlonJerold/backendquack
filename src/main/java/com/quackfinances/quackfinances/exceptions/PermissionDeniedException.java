package com.quackfinances.quackfinances.exceptions;

public class PermissionDeniedException extends Exception {
    public PermissionDeniedException() {
        super("Usuário não tem permissão para realizar esta transação");
    }
}