package com.java.talent.batch12.atm.repository;

import com.java.talent.batch12.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

}
