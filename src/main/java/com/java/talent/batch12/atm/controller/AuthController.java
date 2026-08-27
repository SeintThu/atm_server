package com.java.talent.batch12.atm.controller;

import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.security.JWTTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.port}")
    private String port;

    private final JWTTokenService jwtTokenService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    @GetMapping("/signup")
    public ResponseEntity<?> registerAccount(@RequestBody @Valid RegisterInfo registerInfo,
                                             @RequestHeader(name = "apiKey") String apikey) {

        LOGGER.info(registerInfo.toString());
        return  null;

    }

    @GetMapping()
    public String greeting(
            @RequestHeader(name = "apiKey") String apikey
    ) {

        LOGGER.info("/api/accounts is reached");

        return "Hello from My ATM app from port: " + port;
    }


    /**
     * Endpoint to display the login page.
     * @return The name of the login view
     */
    @GetMapping("/login")
    public String login() {

        return  jwtTokenService.generateAccessToken("Saung","09753444579","USER")
               + ":/n :" + jwtTokenService.generateRefreshToken("Saung","09753444579","USER")
         ;
    }
}
