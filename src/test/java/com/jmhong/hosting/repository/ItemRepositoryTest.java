package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemCond;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EntityManager em;

    @Test
    void search() {
        Item item1 = new Item("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = new Item("item2", 22222L, 222L, ItemStatus.SUSPEND);
        itemRepository.save(item1);
        itemRepository.save(item2);

        em.flush();
        em.clear();

        ItemCond itemCond1 = new ItemCond("", null, "", "", "", "");
        ItemCond itemCond2 = new ItemCond("item1", ItemStatus.SALE, "11111", "11111", "111", "111");
        ItemCond itemCond3 = new ItemCond("item2", ItemStatus.SUSPEND, "22222", "22222", "222", "222");
        ItemCond itemCond4 = new ItemCond("tem", null, "11111", "22222", "111", "222");
        ItemCond itemCond5 = new ItemCond("xx", null, "22222", "11111", "222", "111");

        List<Item> findItem1 = itemRepository.search(itemCond1);
        List<Item> findItem2 = itemRepository.search(itemCond2);
        List<Item> findItem3 = itemRepository.search(itemCond3);
        List<Item> findItem4 = itemRepository.search(itemCond4);
        List<Item> findItem5 = itemRepository.search(itemCond5);

        assertEquals(2, findItem1.size());
        assertEquals(1, findItem2.size());
        assertEquals(1, findItem3.size());
        assertEquals(2, findItem4.size());
        assertEquals(0, findItem5.size());

        assertEquals(item1.getId(), findItem2.get(0).getId());
        assertEquals(item2.getId(), findItem3.get(0).getId());
    }
}