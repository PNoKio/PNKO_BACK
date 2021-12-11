package PNoKio.Server.controller;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.ItemStatus;
import PNoKio.Server.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ItemController {

    @GetMapping("/items")
    public String itemList(Model model, @RequestParam Long storeId) {
        StoreDto store = new StoreDto(storeId, "블루포트", "상명대점", "donggoo2342");

        model.addAttribute("store", store);

        List<Item> items = new ArrayList<>();
        Category iceCoffee = new Category("커피(ice)");

        items.add(new Item(1L, "아이스 아메리카노", 4500, ItemStatus.ON_SALE, iceCoffee));
        items.add(new Item(2L, "아이스 카페라떼", 5000, ItemStatus.ON_SALE, iceCoffee));

        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/new")
    public String createItem(Model model, @RequestParam Long storeId) {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("커피(ice)"));
        categories.add(new Category("커피(hot)"));

        model.addAttribute("categories", categories);
        model.addAttribute("status", ItemStatus.values());
        model.addAttribute("itemForm", new ItemForm());

        return "items/createItem";
    }

    @Data
    @AllArgsConstructor
    static class StoreDto { // 임시로 사용. 연관관계 설정 후 Store로 대체.
        Long storeId;
        String storeName;
        String branch;
        String owner;
    }

    @Data
    static class ItemForm{
        String itemName;
        String price;
        Long categoryId;
        ItemStatus status;
    }
}
