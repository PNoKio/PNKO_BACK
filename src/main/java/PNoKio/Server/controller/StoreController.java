package PNoKio.Server.controller;

import PNoKio.Server.argumentresolver.Login;
import PNoKio.Server.argumentresolver.SessionDto;
import PNoKio.Server.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/")
    public String home(Model model){
        // 매장 목록 반환
        List<StoreDto> stores = storeService.findStores().stream().map(input ->
                new StoreDto(input.getId(),
                        input.getStoreName(),
                        input.getBranch(),
                        input.getOwner().toString())).collect(Collectors.toList());
        model.addAttribute("stores", stores);
        return "/home";
    }

    @GetMapping("/new")
    public String createStoreForm(Model model){
        // owner 관련
        List<String> owners = new ArrayList<>();
        owners.add("donggoo2342");
        owners.add("suminlee08");
        owners.add("boorubda2909");
        model.addAttribute("owners", owners);
        model.addAttribute("storeForm", new StoreForm());

        return "stores/createStore";
    }

    @PostMapping("/new")
    public String createStore(StoreForm form, @Login SessionDto sessionDto) {
        PNoKio.Server.dto.StoreDto store = new PNoKio.Server.dto.StoreDto(
                form.storeName,
                form.branch
        );
        storeService.addStore(store, sessionDto);

        return "redirect:/";
    }

    @Data
    @AllArgsConstructor
    static class StoreDto {
        Long storeId;
        String storeName;
        String branch;
        String owner;
    }

    @Data
    static class StoreForm{
        String storeName;
        String branch;
        String onwer;
    }
}
