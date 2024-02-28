package ru.dimangan.bankingservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.domain.models.UserEmail;
import ru.dimangan.bankingservice.domain.models.UserPhone;
import ru.dimangan.bankingservice.services.EmailService;
import ru.dimangan.bankingservice.services.PhoneService;
import ru.dimangan.bankingservice.services.UserService;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/authorize")
public class UserEditController {
    private final EmailService emailService;
    private final PhoneService phoneService;

    @PostMapping("/add-email")
    public void addEmail(@RequestBody UserEmail email) {
        if(emailService.isEmailOccupied(email)){
            throw new RuntimeException("Пользователь с такой почтой уже существует");
        }
        emailService.save(email);
    }

    @PostMapping("/add-phone")
    public void addPhone(@RequestBody UserPhone userPhone) {
        if(phoneService.isPhoneOccupied(userPhone)){
            throw new RuntimeException("Пользователь с таким телефоном уже существует");
        }
        phoneService.save(userPhone);
    }

    @DeleteMapping("/delete-email")
    public String deleteEmail(@RequestBody UserEmail userEmail) {
        log.info("Delete email request");
        emailService.deleteEmail(userEmail);
        System.out.println("Delete email request");
        return "Delete email";
    }

    @DeleteMapping("/delete-phone")
    public String deletePhone(@RequestBody UserPhone userPhone) {
        log.info("Delete phone request");
        phoneService.deletePhone(userPhone);
        System.out.println("Delete phone request");
        return "Delete phone";
    }
}
