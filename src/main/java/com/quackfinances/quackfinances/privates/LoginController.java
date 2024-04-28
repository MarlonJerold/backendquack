package com.quackfinances.quackfinances.privates;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    public record LoginRequest(String username, String password) {
    }

}