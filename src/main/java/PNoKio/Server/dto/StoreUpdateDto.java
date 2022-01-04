package PNoKio.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreUpdateDto {
    private String storeName;
    private String branch;
}
