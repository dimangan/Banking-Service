package ru.dimangan.bankingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.dto.JwtAuthenticationResponse;
import ru.dimangan.bankingservice.domain.dto.SignInRequest;
import ru.dimangan.bankingservice.domain.dto.SignUpRequest;
import ru.dimangan.bankingservice.domain.models.BankingAccount;
import ru.dimangan.bankingservice.domain.models.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        BankingAccount account = new BankingAccount();
        account.setBalance(request.getBalance());
        user.setBankingAccount(account);

        log.info("Request: {}\n User: {}", request, user);

        userService.create(user);

        String jwt = jwtService.generateToken(user);
        log.info("User registered. Token: " + jwt);

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        log.info("User logged in. Token: " + jwt);
        return new JwtAuthenticationResponse(jwt);
    }

}
