package PNoKio.Server.controller;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.ItemStatus;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.CategoryNameDto;
import PNoKio.Server.dto.ItemDto;
import PNoKio.Server.dto.ItemReturn;
import PNoKio.Server.dto.SpecificItem;
import PNoKio.Server.service.CategoryService;
import PNoKio.Server.service.ItemService;
import PNoKio.Server.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final StoreService storeService;
    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String itemList(Model model, @RequestParam Long storeId) {
        Store store = storeService.findByStoreId(storeId);
        StoreDto storeDto = new StoreDto(
                store.getId(),
                store.getStoreName(),
                store.getBranch(),
                store.getOwner().getOwnerName());

        model.addAttribute("store", storeDto);

        List<ItemResponseDto> items = new ArrayList<>();
        List<ItemReturn> itemReturns = itemService.findItems(storeId);

        for (ItemReturn itemReturn : itemReturns) {
            for (SpecificItem item : itemReturn.getItems()) {
                items.add(new ItemResponseDto(
                        itemReturn.getCategoryName(),
                        item.getItemName(),
                        item.getPrice(),
                        item.getStatus()
                ));
            }
        }

        return "items/itemList";
    }

    @GetMapping("/new")
    public String createItemForm(Model model, @RequestParam Long storeId) {
        List<Category> categories = categoryService.findAllCategory(storeId);
        model.addAttribute("categories", categories);
        model.addAttribute("status", ItemStatus.values());
        model.addAttribute("itemForm", new ItemForm());

        return "items/createItem";
    }

    @PostMapping("/new")
    public String createItem(ItemForm form, @RequestParam Long storeId) {

        itemService.addItem(
                form.categoryId,
                new ItemDto(
                        form.itemName,
                        form.price
                )
        );
        return "redirect:/items?storeId="+storeId;
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
        int price;
        Long categoryId;
        ItemStatus status;
    }

    @Data
    @AllArgsConstructor
    static class ItemResponseDto {
        String category;
        String name;
        int price;
        ItemStatus status;
    }

}
