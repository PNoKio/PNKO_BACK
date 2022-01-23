package PNoKio.Server.controller;

import PNoKio.Server.exception.EmailDuplicateException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "PNoKio.Server.controller")
public class OwnerControllerAdvice {

    @ExceptionHandler
    public String duplicate(IllegalStateException e){
        String errorMessage = "error";
        return "redirect:/login?errorMessage="+errorMessage;
    }

    @ExceptionHandler
    public String emailDuplicate(EmailDuplicateException e){
        String errorMessage = "error";
        return "redirect:/signup?errorMessage="+errorMessage;
    }
}
