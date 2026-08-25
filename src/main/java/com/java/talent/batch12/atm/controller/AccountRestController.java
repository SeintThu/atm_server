package com.java.talent.batch12.atm.controller;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    @Value("${server.port}")
    private String port;

    @Value("${jwt.apikey}")
    private String serverApikey;

    private  final AccountService accountService;

    @GetMapping()
    public String helloWorld(
            @RequestHeader(name = "apiKey") String apikey
    ) {

        if(!serverApikey.equals(apikey)){
            return "Incorrect apikey";
        }
            return "Hello World from RestController! from port: " + port;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody @Valid  RegisterInfo registerInfo,
                                             @RequestHeader(name = "apiKey") String apikey) {

        if(!serverApikey.equals(apikey)){
            return ResponseUtils.createCommonErrorResponse(
                    HttpStatus.BAD_REQUEST,"-","","","Invalid apikey"
            );
        }

       return  accountService.handleCreateAccountRequest(registerInfo);

    }

}
