package PNoKio.Server.controller;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.ErrorMessage;
import PNoKio.Server.dto.LoginDto;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.exception.EmailDuplicateException;
import PNoKio.Server.service.OwnerService;
import PNoKio.Server.service.StoreService;
import PNoKio.Server.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final StoreService storeService;

    @GetMapping("/")
    public String home(Model model){
        // 매장 목록 반환
        List<StoreController.StoreDto> stores = storeService.findStores().stream().map(input ->
                new StoreController.StoreDto(input.getId(),
                        input.getStoreName(),
                        input.getBranch(),
                        input.getOwner().getOwnerName())
        ).collect(Collectors.toList());
        model.addAttribute("stores", stores);
        return "/home";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute (name = "ownerDto") OwnerDto ownerDto,
                         @RequestParam(defaultValue = "notError") String errorMessage,Model model){
        if(!errorMessage.equals("notError")){
            ErrorMessage errorMessage1 = new ErrorMessage("이미 가입된 이메일입니다.");
            model.addAttribute("errorMessage", errorMessage1);
        }
        return "/ownerForm";
    }

    @PostMapping("/signup")
    public String save(@Validated @ModelAttribute(name = "ownerDto") OwnerDto ownerDto, BindingResult bindingResult
    , Model model)throws
            EmailDuplicateException {

        if(bindingResult.hasErrors()){
            bindingResult.reject("loginFail", "아이디 양식은 email 입니다.");
            return "/ownerForm";
        }
        ownerService.create(ownerDto);
        return "redirect:/";
    }



    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto,Model model
    ,@RequestParam(defaultValue = "notError") String errorMessage){

        if(!errorMessage.equals("notError")){
            ErrorMessage errorMessage1 = new ErrorMessage("아이디 혹은 비밀번호가 틀렸습니다.");
            model.addAttribute("errorMessage", errorMessage1);
        }
        return "/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "loginDto")LoginDto loginDto,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model) throws IllegalStateException{
        Owner owner = ownerService.login(loginDto);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,new PNoKio.Server.argumentresolver.SessionDto(owner.getId(),owner.getEmail()));
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
