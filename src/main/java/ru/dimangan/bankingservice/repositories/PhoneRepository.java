package ru.dimangan.bankingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimangan.bankingservice.domain.models.UserPhone;

public interface PhoneRepository extends JpaRepository<UserPhone, Long> {


}
