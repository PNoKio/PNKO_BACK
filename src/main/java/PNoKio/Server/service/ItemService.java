package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.ItemStatus;

import java.util.List;

public interface ItemService {
    Long saveItem(Item item);
    void updateItem(Long itemId, String name, int price, ItemStatus status);
    List<Item> findItems();
    Item findOne(Long itemId);
    Long removeItem(Long id);
}
