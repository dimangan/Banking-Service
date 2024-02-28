package ru.dimangan.bankingservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.repositories.BankingRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankingService {
    private final BankingRepository bankingRepository;



}
