package ru.dimangan.bankingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.models.Banking;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.domain.models.UserEmail;
import ru.dimangan.bankingservice.domain.models.UserPhone;
import ru.dimangan.bankingservice.repositories.BankingRepository;
import ru.dimangan.bankingservice.repositories.EmailRepository;
import ru.dimangan.bankingservice.repositories.PhoneRepository;
import ru.dimangan.bankingservice.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final BankingRepository bankingRepository;



    public User create(User user, UserEmail userEmail, UserPhone userPhone, Banking banking) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        else if (emailRepository.existsByEmail(userEmail.getEmail())) {
            throw new RuntimeException("Пользователь с такой почтой уже существует");
        }
        else if (phoneRepository.existsByPhone(userPhone.getPhone())) {
            throw new RuntimeException("Пользователь с таким телефоном уже существует");
        }
        else if(banking.getBalance() < 0){
            throw new RuntimeException("Баланс не может быть отрицательным");
        }
        else{
            log.info("Creating user: {}", user);
            return save(user, userEmail, userPhone, banking);
        }
    }

    private User save(User user, UserEmail userEmail, UserPhone userPhone, Banking banking) {
        user = userRepository.save(user);
        userEmail.setUser(user);
        userPhone.setUser(user);
        banking.setUser(user);
        user.getEmailList().add(userEmail);
        user.getPhoneList().add(userPhone);
        user.setBankingAccount(banking);
        return userRepository.save(user);
    }

    public User save(User user){
        return userRepository.save(user);
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
