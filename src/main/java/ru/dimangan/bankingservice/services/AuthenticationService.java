package ru.dimangan.bankingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.dto.JwtAuthenticationResponse;
import ru.dimangan.bankingservice.domain.dto.SignInRequest;
import ru.dimangan.bankingservice.domain.dto.SignUpRequest;
import ru.dimangan.bankingservice.domain.models.Banking;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.domain.models.UserEmail;
import ru.dimangan.bankingservice.domain.models.UserPhone;


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

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail(request.getEmail());

        UserPhone userPhone = new UserPhone();
        userPhone.setPhone(request.getPhone());

        Banking banking = new Banking();
        banking.setBalance(request.getBalance());

        log.info("Request: {}\n User: {}", request, user);

        userService.create(user, userEmail, userPhone, banking);

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
