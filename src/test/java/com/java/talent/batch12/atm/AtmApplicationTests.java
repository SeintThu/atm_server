package com.java.talent.batch12.atm;

import com.java.talent.batch12.atm.service.AccountService;
import org.junit.jupiter.api.Assertions;
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


		int accountId = accountService.createAccount("Saung Phyu 1",
				"Welcome@21","shp18114@gmail.com","kmd").getAccountId();

		Assertions.assertNotNull(accountId);
		Assertions.assertEquals(12,accountId);

	}

}
