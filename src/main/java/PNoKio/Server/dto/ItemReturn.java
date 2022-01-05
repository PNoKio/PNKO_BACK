package PNoKio.Server.dto;

import PNoKio.Server.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemReturn {
    private String CategoryName;
    private List<SpecificItem> items;

    public ItemReturn(Category category) {
        CategoryName = category.getCategoryName();
        items = category.getItems().stream().map(a -> new SpecificItem(a.getItemName(), a.getPrice(), a.getStatus()))
                .collect(Collectors.toList());
    }
}