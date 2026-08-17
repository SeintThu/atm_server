package com.java.talent.batch12.atm.service;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.model.Transaction;
import com.java.talent.batch12.atm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    Transaction withdrawal(Transaction withdrawal){
        // accountService.updateBalance
        return transactionRepository.save(withdrawal);
    }
    Transaction deposit(Transaction withdrawal){
        // accountService.updateBalance
        return transactionRepository.save(withdrawal);
    }

    List<Transaction> getTransactionsByAccountId(Account account){
        return  null;
    }
    Transaction getTransactionByTransactionId(){
        return null;
    }
}
