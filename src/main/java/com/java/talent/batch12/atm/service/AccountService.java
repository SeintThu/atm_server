package com.java.talent.batch12.atm.service;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.model.Role;
import com.java.talent.batch12.atm.repository.AccountRepository;
import com.java.talent.batch12.atm.repository.RoleRespository;
import com.java.talent.batch12.atm.request.LoginInfo;
import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.security.JWTTokenService;
import com.java.talent.batch12.atm.security.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final RoleRespository roleRespository;

    private final PasswordEncoder passwordEncoder;

    private final UserPrincipalService userPrincipalService;

    private  final JWTTokenService jwtTokenService;

    public ResponseEntity<?> handleCreateAccountRequest(
            RegisterInfo registerInfo
    ){
        Account account = createAccount(registerInfo.getName(),registerInfo.getPassword(),registerInfo.getEmail(), registerInfo.getAddress());

        return ResponseUtils.createCommonResponse(HttpStatus.OK,"create-account",
                "create-account","post-method","Account is created",account);
    }

    public ResponseEntity<?> handleLoginAccountRequest(
            LoginInfo loginInfo
    ){
       // check account

        Account account =null;
        Optional<Account> accountOptional = accountRepository.findByUsername(loginInfo.getName());
        if(accountOptional.isEmpty()) {

            return ResponseUtils.createCommonErrorResponse(HttpStatus.BAD_REQUEST, "login", "login", "test",
                    "Account not found");
        }
        account = accountOptional.get();
            if(!passwordEncoder.matches(loginInfo.getPassword(),account.getPassword())){
                return ResponseUtils.createCommonErrorResponse(HttpStatus.BAD_REQUEST,"login","login","test",
                        "Password is incorrect");
            }

        String accessToken =  jwtTokenService.generateAccessToken(account.getUsername(), String.valueOf(account.getAccountId()),account.getRole().getRoleName());
        String refreshToken = jwtTokenService.generateRefreshToken(account.getUsername(), String.valueOf(account.getAccountId()),account.getRole().getRoleName());
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);

        return ResponseUtils.createCommonResponse(HttpStatus.OK,"login","login","test",
                "Login is retrieved successfully",map);

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
