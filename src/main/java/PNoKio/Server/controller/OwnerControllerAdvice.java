package PNoKio.Server.controller;

import PNoKio.Server.argumentresolver.SessionDto;
import PNoKio.Server.exception.EmailDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "PNoKio.Server.controller")
public class OwnerControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicate(IllegalStateException e, Model model){
        model.addAttribute("ownerDto", new SessionDto(1L,e.getMessage()));
        return "/loginForm";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String emailDuplicate(EmailDuplicateException e, Model model){
        return "/ownerForm";
    }
}
