package ru.dimangan.bankingservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.models.User;
import ru.dimangan.bankingservice.domain.models.UserPhone;
import ru.dimangan.bankingservice.repositories.PhoneRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService {
    private final UserService userService;
    private final PhoneRepository phoneRepository;

    public void save(UserPhone phone){
        log.info("Updating user info with new phone {}", phone.getPhone());
        addPhone(phone);
    }

    public boolean isPhoneOccupied(UserPhone userPhone) {
        return phoneRepository.existsByPhone(userPhone.getPhone());
    }

    public void addPhone(UserPhone phone) {
        User currentUser = userService.getCurrentUser();
        phone.setUser(currentUser);
        currentUser.getPhoneList().add(phone);
        userService.save(currentUser);
    }


    public void deletePhone(UserPhone userPhone) {
        List<UserPhone> phoneList = userService.getCurrentUser().getPhoneList();
        if (phoneList.size() > 1 && phoneList.contains(userPhone)) {
            log.info("Deleting user phone {}", userPhone.getPhone());
            phoneRepository.delete(userPhone);
        }
    }
}
