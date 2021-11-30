package PNoKio.Server.controller;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.service.OwnerService;
import PNoKio.Server.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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




    @GetMapping("/login")
    public String loginForm(@ModelAttribute("OwnerDto") OwnerDto ownerDto){
        return "/login/Form";
    }


    @PostMapping("/login")
    public String login(@Validated OwnerDto ownerDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Owner owner = ownerService.login(ownerDto);

        if(owner ==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
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
        return "redirect:/";
    }


    @GetMapping("/signup")
    public String signup(@Validated OwnerDto ownerDto){
        ownerService.create(ownerDto);
        return "/home";
    }
}
