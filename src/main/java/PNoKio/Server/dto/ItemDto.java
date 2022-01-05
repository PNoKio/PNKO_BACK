package PNoKio.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {

    private String itemName;
    private Integer price;
}
