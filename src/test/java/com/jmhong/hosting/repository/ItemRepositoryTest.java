package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemSearchDto;
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

        ItemSearchDto itemSearchDto1 = new ItemSearchDto("", null);
        ItemSearchDto itemSearchDto2 = new ItemSearchDto("item1", ItemStatus.SALE);
        ItemSearchDto itemSearchDto3 = new ItemSearchDto("item2", ItemStatus.SUSPEND);
        ItemSearchDto itemSearchDto4 = new ItemSearchDto("tem", null);
        ItemSearchDto itemSearchDto5 = new ItemSearchDto("xx", null);

        List<Item> findItem1 = itemRepository.search(itemSearchDto1);
        List<Item> findItem2 = itemRepository.search(itemSearchDto2);
        List<Item> findItem3 = itemRepository.search(itemSearchDto3);
        List<Item> findItem4 = itemRepository.search(itemSearchDto4);
        List<Item> findItem5 = itemRepository.search(itemSearchDto5);

        assertEquals(2, findItem1.size());
        assertEquals(1, findItem2.size());
        assertEquals(1, findItem3.size());
        assertEquals(2, findItem4.size());
        assertEquals(0, findItem5.size());

        assertEquals(item1.getId(), findItem2.get(0).getId());
        assertEquals(item2.getId(), findItem3.get(0).getId());
    }
}