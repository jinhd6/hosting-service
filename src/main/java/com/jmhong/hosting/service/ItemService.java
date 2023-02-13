package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.dto.ItemSearchDto;
import com.jmhong.hosting.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> search(ItemSearchDto itemSearchDto) {
        return itemRepository.search(itemSearchDto);
    }
}
