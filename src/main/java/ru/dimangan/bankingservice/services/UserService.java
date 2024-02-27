package ru.dimangan.bankingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с такой почтой уже существует");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new RuntimeException("Пользователь с таким телефоном уже существует");
        }
        if(user.getBankingAccount().getBalance() < 0){
            throw new RuntimeException("Баланс не может быть отрицательным");
        }
        log.info("Creating user: {}", user);
        return save(user);
    }

    public User getByUsername(String username) {
        log.info("Get user by username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        log.info("Get user details service");
        return this::getByUsername;
    }

    public User getCurrentUser() {
        log.info("Get current user");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

}
