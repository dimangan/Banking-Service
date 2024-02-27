package ru.dimangan.bankingservice.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;


import java.util.Date;

@Data
public class SignUpRequest {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "balance")
    private Float balance;
}
