package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderRequestDto;
import com.jmhong.hosting.dto.OrderSearchDto;
import com.jmhong.hosting.service.ItemService;
import com.jmhong.hosting.service.MemberService;
import com.jmhong.hosting.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;
    private MemberService memberService;
    private ItemService itemService;

    @Autowired
    public OrderController(OrderService orderService, MemberService memberService, ItemService itemService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @GetMapping("/orders/new")
    public String orderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("orderRequestDto", new OrderRequestDto());
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "/orders/orderForm";
    }

    @PostMapping("/orders/new")
    public String order(@ModelAttribute OrderRequestDto orderRequestDto) {
        orderService.saveOrder(orderRequestDto);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(Model model, @ModelAttribute OrderSearchDto orderSearchDto) {
        List<Order> orders = orderService.search(orderSearchDto);
        model.addAttribute("orderSearchDto", new OrderSearchDto());
        model.addAttribute("orders", orders);
        model.addAttribute("orderTypes", OrderType.values());
        model.addAttribute("orderStatuses", OrderStatus.values());
        return "/orders/orderList";
    }
}
