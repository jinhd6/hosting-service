package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemCond;
import com.jmhong.hosting.dto.ItemRequestDto;
import com.jmhong.hosting.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(ItemRequestDto dto) {
        Item item = new Item(dto.getName(), dto.getPrice(), dto.getPeriod(), dto.getStatus());
        itemRepository.save(item);
        return item;
    }

    @Transactional(readOnly = true)
    public List<Item> searchItem(ItemCond itemCond) {
        return itemRepository.search(itemCond);
    }

    public Item updateItem(Long itemId, ItemRequestDto dto) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.updateItemInfo(dto.getName(), dto.getPrice(), dto.getPeriod(), dto.getStatus());
        return item;
    }

    @Transactional(readOnly = true)
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Item> findItemsOnSale() {
        List<Item> activeItems = itemRepository.findAll().stream()
                .filter(i -> i.getStatus() == ItemStatus.SALE)
                .collect(Collectors.toList());
        return activeItems;
    }
}
