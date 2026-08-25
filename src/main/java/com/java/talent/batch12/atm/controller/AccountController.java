package com.java.talent.batch12.atm.controller;

import com.java.talent.batch12.atm.model.Account;
import com.java.talent.batch12.atm.request.LoginInfo;
import com.java.talent.batch12.atm.request.RegisterInfo;
import com.java.talent.batch12.atm.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseBody
    @GetMapping("/accounts")
    public String helloWorld() {
        return "Hello World from Controller!";
    }

    @GetMapping("/")
    public String display(Model model) {
        model.addAttribute("message", "ATM  MVC Tutorial!!");
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {

        RegisterInfo registerInfo = new RegisterInfo();
        model.addAttribute("registerInfo", registerInfo);
        return "register";
    }

    @PostMapping("/registerAccount")
    public String registerAccount(Model model, @ModelAttribute @Valid RegisterInfo registerInfo) {
        accountService.createAccount(registerInfo.getName(),
                registerInfo.getEmail(), registerInfo.getPassword(),registerInfo.getEmail());
      model.addAttribute("message", "Account created successfully!");
        return "index";
    }


    @GetMapping("/login")
    public String login(Model model) {

        LoginInfo loginInfo = new LoginInfo();
        model.addAttribute("loginInfo", loginInfo);
        return "login";
    }

    @PostMapping("/loginAccount")
    public String loginAccount(Model model, @ModelAttribute LoginInfo loginInfo) {
//        accountService.createAccount(registerInfo.getName(),
//                registerInfo.getEmail(), registerInfo.getPassword(),registerInfo.getEmail());
        model.addAttribute("message", "Account login successfully!");
        return "index";
    }



}
