package ru.dimangan.bankingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimangan.bankingservice.domain.models.UserEmail;

public interface EmailRepository extends JpaRepository<UserEmail, Long> {

}
