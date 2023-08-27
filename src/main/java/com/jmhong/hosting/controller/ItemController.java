package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.Item;
import com.jmhong.hosting.domain.ItemStatus;
import com.jmhong.hosting.dto.ItemRequestDto;
import com.jmhong.hosting.dto.ItemCond;
import com.jmhong.hosting.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("itemRequestDto", new ItemRequestDto());
        model.addAttribute("itemStatuses", ItemStatus.values());
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute ItemRequestDto itemRequestDto) {
        itemService.saveItem(itemRequestDto);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model, @ModelAttribute ItemCond itemCond) {
        List<Item> items = itemService.searchItem(itemCond);
        model.addAttribute("itemSearchDto", itemCond);
        model.addAttribute("items", items);
        model.addAttribute("itemStatuses", ItemStatus.values());
        return "/items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemId", itemId);
        model.addAttribute("itemRequestDto", new ItemRequestDto());
        model.addAttribute("itemStatuses", ItemStatus.values());
        return "/items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute ItemRequestDto itemRequestDto) {
        itemService.updateItem(itemId, itemRequestDto);
        return "redirect:/";
    }
}
