package ru.dimangan.bankingservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.domain.models.UserEmail;
import ru.dimangan.bankingservice.repositories.EmailRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final UserService userService;
    private final EmailRepository emailRepository;

    public void save(UserEmail email){
        log.info("Updating user info with new email {}", email.getEmail());
        addEmail(email);
    }

    public boolean isEmailOccupied(UserEmail userEmail){
        return emailRepository.existsByEmail(userEmail.getEmail());
    }

    public void addEmail(UserEmail email) {
        User currentUser = userService.getCurrentUser();
        email.setUser(currentUser);
        currentUser.getEmailList().add(email);
        userService.save(currentUser);
    }

    public void deleteEmail(UserEmail userEmail) {
        List<String> emailStringList = userService.getCurrentUser().getEmailList().stream().map(email -> email.getEmail()).toList();
        log.info("Deleting user email {}", userEmail.getEmail());
        if (emailStringList.size() > 1 && emailStringList.contains(userEmail.getEmail())) {
            emailRepository.delete(emailRepository.findByEmail(userEmail.getEmail()));
            log.info("Email deleted");
        }
        else{
            log.info("Deleting email failed. Email list:");
            for (String email : emailStringList) {
                log.info(email);
            }
        }
    }
}
