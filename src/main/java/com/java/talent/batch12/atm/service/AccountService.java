package com.java.talent.batch12.atm.service;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.model.Role;
import com.java.talent.batch12.atm.repository.AccountRepository;
import com.java.talent.batch12.atm.repository.RoleRespository;
import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.security.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final RoleRespository roleRespository;

    private final PasswordEncoder passwordEncoder;

    private final UserPrincipalService userPrincipalService;

    public ResponseEntity<?> handleCreateAccountRequest(
            RegisterInfo registerInfo
    ){
        String accountId = userPrincipalService.getAccountIdByCurrentUser();
        Account account = createAccount(registerInfo.getName(),registerInfo.getEmail(),registerInfo.getPassword(),registerInfo.getEmail());

        return ResponseUtils.createCommonResponse(HttpStatus.OK,"create-account",
                "create-account","post-method","Account is created",account);
    }

    public Account createAccount(String userName, String password,String email,String address){

        //validation

        Account account = new Account();
        account.setUsername(userName);
        account.setPassword(passwordEncoder.encode(password));
        account.setAddress(address);
        account.setEmail(email);

        Role userRole = new Role();
        userRole.setRoleName("USER");

         roleRespository.save(userRole);


        account.setRole(userRole);

        return accountRepository.save(account);
    }

    boolean loginAccount(String userName, String password){
        return false;
    }


    boolean deleteAccount(String userName){
        return false;
    }

    Account getAccountByUserName(){
        return null;
    }

    Account updateBalance(){
        return null;
    }





}
