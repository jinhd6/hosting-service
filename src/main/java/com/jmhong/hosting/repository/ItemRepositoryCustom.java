package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.dto.ItemSearchDto;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> search(ItemSearchDto itemSearchDto);
}
