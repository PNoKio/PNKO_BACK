package PNoKio.Server.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {


    @GetMapping("/")
    public String home(Model model){
        // 매장 목록 반환
        List<StoreDto> stores = new ArrayList<>();
        stores.add(new StoreDto(1L,"카페드림", "상명대점", "donggo2342"));
        stores.add(new StoreDto(2L,"블루포트", "국민대점", "suminlee08"));
        model.addAttribute("stores", stores);
        return "home";
    }

    @GetMapping("/stores/new")
    public String createStore(Model model){
        List<String> owners = new ArrayList<>();
        owners.add("donggoo2342");
        owners.add("suminlee08");
        owners.add("boorubda2909");
        model.addAttribute("owners", owners);
        model.addAttribute("storeForm", new StoreForm());

        return "stores/createStore";
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
