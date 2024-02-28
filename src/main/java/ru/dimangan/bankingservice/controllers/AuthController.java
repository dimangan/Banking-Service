package ru.dimangan.bankingservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dimangan.bankingservice.domain.dto.JwtAuthenticationResponse;
import ru.dimangan.bankingservice.domain.dto.SignInRequest;
import ru.dimangan.bankingservice.domain.dto.SignUpRequest;
import ru.dimangan.bankingservice.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/free")
public class AuthController {
    private final AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        log.info("signUp request: {}", request);
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        log.info("signIn request: {}", request);
        return authenticationService.signIn(request);
    }


}
