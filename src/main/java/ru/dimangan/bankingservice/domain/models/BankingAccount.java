package ru.dimangan.bankingservice.domain.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "banking_accounts")
@AllArgsConstructor
@NoArgsConstructor
public class BankingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;
    @Column(name = "balance", nullable = false)
    private Float balance;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

}
