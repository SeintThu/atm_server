package com.java.talent.batch12.atm.controller;

import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.response.ResponseUtils;
import com.java.talent.batch12.atm.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Endpoints for creating and retrieving products")
public class AccountRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

    @Value("${server.port}")
    private String port;

    @Value("${jwt.apikey}")
    private String serverApikey;


    @GetMapping()
    @Operation(summary = "Get product details by ID", description = "Fetches a single product record using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully found"),
            @ApiResponse(responseCode = "404", description = "Provided product ID does not exist")
    })
    public String greeting(
    ) {

        LOGGER.info("/api/accounts is reached");

        return "Hello from My ATM app from port: " + port;
    }


    @DeleteMapping()
    public String deleteAccount(
            @RequestHeader(name = "apiKey") String apikey
    ) {

        LOGGER.info("/api/accounts is reached");
        if (!serverApikey.equals(apikey)) {
            return "Incorrect apikey";
        }
        return "Hello Worlds from RestController! from port: " + port;
    }

    @GetMapping("/transactions")
    public String getTransactions() {
        return "transactions";
    }



}
