package ru.dimangan.bankingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimangan.bankingservice.domain.models.Banking;

public interface BankingRepository extends JpaRepository<Banking, Long> {


}
