package com.java.talent.batch12.atm;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtmApplicationTests {

	@Autowired
	AccountService accountService;

	@Test
	void contextLoads() {
	}

	@Test
	void createAccount() {
  		System.out.println(accountService.createAccount("Saung Phyu 1",
				"Welcome@21","shp18114@gmail.com","kmd"));

	}

}
