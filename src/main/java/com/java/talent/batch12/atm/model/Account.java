package com.java.talent.batch12.atm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "accounts")
@Data
public class Account extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(name = "username", nullable = false, length = 512)
    private String username;
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 512)
    private String password;
    @Column(name = "balance")
    private double balance;
    @Column(name = "address", length = 1000)
    private String address;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    @OneToMany(mappedBy = "accounts")
//    List<Transaction> transactions;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("accountId=").append(accountId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", address='").append(address).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
