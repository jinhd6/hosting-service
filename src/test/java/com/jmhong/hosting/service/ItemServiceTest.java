package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemSearchDto;
import com.jmhong.hosting.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void saveItem() {
        Item item1 = new Item("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = new Item("item2", 22222L, 222L, ItemStatus.SUSPEND);

        Long saveItemId1 = itemService.saveItem(item1);
        Long saveItemId2 = itemService.saveItem(item2);

        assertEquals(item1.getId(), saveItemId1);
        assertEquals(item2.getId(), saveItemId2);
    }

    @Test
    void search() {
        Item item1 = new Item("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = new Item("item2", 22222L, 222L, ItemStatus.SUSPEND);
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        ItemSearchDto itemSearchDto1 = new ItemSearchDto("item1", ItemStatus.SALE);
        ItemSearchDto itemSearchDto2 = new ItemSearchDto("item2", ItemStatus.SUSPEND);

        List<Item> findItems1 = itemService.search(itemSearchDto1);
        List<Item> findItems2 = itemService.search(itemSearchDto2);

        assertEquals(1, findItems1.size());
        assertEquals(1, findItems2.size());
        assertEquals(item1.getId(), findItems1.get(0).getId());
        assertEquals(item2.getId(), findItems2.get(0).getId());
    }
}