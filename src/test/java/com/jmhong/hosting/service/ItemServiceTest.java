package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemCond;
import com.jmhong.hosting.dto.ItemRequestDto;
import com.jmhong.hosting.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    void saveItem() {
        // Given
        ItemRequestDto itemRequestDto = new ItemRequestDto("item1", 11111L, 111L, ItemStatus.SALE);

        // When
        Item item = itemService.saveItem(itemRequestDto);

        // Then
        assertEquals("item1", item.getName());
        assertEquals(11111L, item.getPrice());
        assertEquals(111L, item.getPeriod());
        assertEquals(ItemStatus.SALE, item.getStatus());
    }

    @Test
    void searchItem() {
        // Given
        Item item1 = createItem("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = createItem("item2", 22222L, 222L, ItemStatus.SUSPEND);
        ItemCond itemCond = new ItemCond("item", null, "11111", "22222", "111", "222");

        // When
        List<Item> findItems = itemService.searchItem(itemCond);

        // Then
        assertEquals(2, findItems.size());
    }

    @Test
    void updateItem() {
        // Given
        Item item = createItem("item1", 11111L, 111L, ItemStatus.SALE);
        ItemRequestDto itemRequestDto = new ItemRequestDto("item2", 22222L, 222L, ItemStatus.SUSPEND);

        // When
        itemService.updateItem(item.getId(), itemRequestDto);

        //Then
        assertEquals("item2", item.getName());
        assertEquals(22222L, item.getPrice());
        assertEquals(222L, item.getPeriod());
        assertEquals(ItemStatus.SUSPEND, item.getStatus());
    }

    private Item createItem(String name, Long price, Long period, ItemStatus status) {
        Item item = new Item(name, price, period, status);
        em.persist(item);
        return item;
    }
}