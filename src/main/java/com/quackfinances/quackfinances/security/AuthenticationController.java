package com.quackfinances.quackfinances.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/authenticate")
@RestController
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public String authenticate(@RequestBody LoginController.LoginRequest loginRequest) {

        var authenticationTokenoken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        var authentication = authenticationManager.authenticate(authenticationTokenoken);

        String authenticationResponse =
                this.service.authenticate(authentication);

        return authenticationResponse;
    }

    public Authentication convertToAuthentication(LoginController.LoginRequest loginRecord) {
        return new UsernamePasswordAuthenticationToken(loginRecord.username(), loginRecord.password());
    }

    @GetMapping
    public String getMessage() {
        return "Hello from private API controller";
    }
}