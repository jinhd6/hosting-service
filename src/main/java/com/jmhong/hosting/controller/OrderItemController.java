package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.OrderItemStatus;
import com.jmhong.hosting.dto.OrderItemCond;
import com.jmhong.hosting.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class OrderItemController {

    private OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/orderItems")
    public String orderItemList(Model model, @ModelAttribute OrderItemCond orderItemCond) {
        List<OrderItem> orderItems = orderItemService.search(orderItemCond);
        model.addAttribute("orderItemSearchDto", new OrderItemCond());
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("orderItemStatuses", OrderItemStatus.values());
        model.addAttribute("activeStatus", OrderItemStatus.ACTIVE);
        return "/orderItems/orderItemList";
    }
}
