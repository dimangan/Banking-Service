package ru.dimangan.bankingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimangan.bankingservice.domain.models.BankingAccount;

public interface BankingAccountRepository extends JpaRepository<BankingAccount, Long> {


}
