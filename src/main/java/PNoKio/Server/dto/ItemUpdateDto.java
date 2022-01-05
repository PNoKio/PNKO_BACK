package PNoKio.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ItemUpdateDto extends ItemDto{

    private String itemName;
    private Integer price;

    public ItemUpdateDto(String itemName, Integer price) {
        super(itemName, price);
    }
}
