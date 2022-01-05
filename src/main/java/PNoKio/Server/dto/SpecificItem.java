package PNoKio.Server.dto;

import PNoKio.Server.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecificItem {
    private String itemName;
    private Integer price;
    private ItemStatus status;
}