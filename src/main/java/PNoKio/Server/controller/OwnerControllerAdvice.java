package PNoKio.Server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "PNoKio.Server.controller")
public class OwnerControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicate(RuntimeException e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "/home";
    }
}
