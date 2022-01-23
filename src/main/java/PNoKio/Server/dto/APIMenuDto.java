package PNoKio.Server.dto;

import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class APIMenuDto {

    public String categoryName;
    public List<ItemListDto> items;

    public APIMenuDto(String categoryName, List<Item> items) {
        this.categoryName = categoryName;
        this.items= items.stream().map(a -> new ItemListDto(a.getItemName(), a.getPrice(),a.getStatus()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ItemListDto{
        public String itemName;
        public int price;
        public ItemStatus status;
    }
}
