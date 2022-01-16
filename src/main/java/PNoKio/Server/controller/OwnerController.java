package PNoKio.Server.controller;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.LoginDto;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.service.OwnerService;
import PNoKio.Server.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;


    @GetMapping("/")
    public String home(){
        return "/home";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute (name = "ownerDto") OwnerDto ownerDto){
        return "/ownerForm";
    }

    @PostMapping("/signup")
    public String save(@Validated @ModelAttribute(name = "ownerDto")OwnerDto ownerDto, BindingResult bindingResult ){

        if(bindingResult.hasErrors()){
            bindingResult.reject("loginFail", "아이디 양식은 email 입니다.");
            return "/ownerForm";
        }
        ownerService.create(ownerDto);
        return "redirect:/";
    }



    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto){
        return "/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "loginDto")LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "/loginForm";
        }

        Owner owner = ownerService.login(loginDto);
        if(owner ==null ){
            bindingResult.reject("loginFail","아이디 혹은 비밀번호가 틀렸습니다.");
            return "/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,owner);
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/loginForm";
    }
}
