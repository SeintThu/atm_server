package com.java.talent.batch12.atm.service;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public String handleNException(Model model) {
        model.addAttribute("err", "Number Format Exception");
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public String handleArrayIndexOutOfBoundsException(Model model) {
        model.addAttribute("err", "Number Format Exception");
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(Model model) {
        model.addAttribute("err", "Number Format Exception");
        return "error";
    }


}
