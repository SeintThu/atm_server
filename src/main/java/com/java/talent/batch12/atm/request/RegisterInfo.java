package com.java.talent.batch12.atm.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterInfo {

    @NotEmpty
    String name;
    @NotNull
    String password;

    @Email
    String email;

    @NotEmpty
    String address;

}
