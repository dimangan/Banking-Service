package ru.dimangan.bankingservice.domain.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emails")
@NoArgsConstructor
@AllArgsConstructor
public class UserEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
