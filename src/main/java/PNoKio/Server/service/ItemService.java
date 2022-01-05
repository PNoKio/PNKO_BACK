package PNoKio.Server.service;

import PNoKio.Server.domain.Item;
import PNoKio.Server.dto.ItemDto;
import PNoKio.Server.dto.ItemReturn;
import PNoKio.Server.dto.ItemUpdateDto;

import java.util.List;

public interface ItemService {
    Item addItem(Long categoryId,ItemDto itemDto);
    Item updateItem(Long categoryId, Long itemId, ItemUpdateDto itemUpdateDto);
    void removeItem(Long itemId);
    List<ItemReturn> findItems(Long storeId);
}
