package com.java.talent.batch12.atm.service;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.repository.AccountRepository;
import com.java.talent.batch12.atm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(String userName, String password,String email){

        //validation

        Account account = new Account();
        account.setUsername(userName);
        account.setPassword(password);
        account.setEmail(email);

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
