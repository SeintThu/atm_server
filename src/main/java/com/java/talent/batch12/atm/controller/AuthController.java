package com.java.talent.batch12.atm.controller;

import com.java.talent.batch12.atm.request.LoginInfo;
import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.security.JWTTokenService;
import com.java.talent.batch12.atm.security.UserPrincipalService;
import com.java.talent.batch12.atm.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.java.talent.batch12.atm.response.ResponseUtils.createCommonResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.port}")
    private String port;

    private final JWTTokenService jwtTokenService;

    private final AccountService accountService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    @GetMapping("/signup")
    public ResponseEntity<?> registerAccount(@RequestBody @Valid RegisterInfo registerInfo,
                                             @RequestHeader(name = "apiKey") String apikey) {

        LOGGER.info(registerInfo.toString());
        return  accountService.handleCreateAccountRequest(registerInfo);

    }

    @GetMapping()
    public String greeting(
            @RequestHeader(name = "apiKey") String apikey
    ) {

        LOGGER.info("/api/accounts is reached");

        return "Hello from My ATM app from port: " + port;
    }

    @GetMapping("/logout")
    public String logout(
            @RequestHeader(name = "apiKey") String apikey
    ) {
        return "Logout now." + port;
    }



    /**
     * Endpoint to display the login page.
     * @return The name of the login view
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInfo loginInfo) {

        return accountService.handleLoginAccountRequest(loginInfo);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {

        String accountId = jwtTokenService.getAccountIdByLoginToken(refreshToken);
        String name = jwtTokenService.getUserNameByLoginToken(refreshToken);
        String role = jwtTokenService.getAccountRoleFromLoginToken(refreshToken);

        String accessToken = jwtTokenService.generateAccessToken(name,accountId,role);

        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);

        return createCommonResponse(HttpStatus.OK,"refresh-token","access-token-getting","test",
                "Access token is retrieved successfully",map);




    }
}

