package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageRequestDto;
import com.jmhong.hosting.dto.UsageSearchDto;
import com.jmhong.hosting.service.MemberService;
import com.jmhong.hosting.service.OrderItemService;
import com.jmhong.hosting.service.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsageController {

    private UsageService usageService;
    private MemberService memberService;
    private OrderItemService orderItemService;

    @Autowired
    public UsageController(UsageService usageService, MemberService memberService, OrderItemService orderItemService) {
        this.usageService = usageService;
        this.memberService = memberService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/usages/new")
    public String usageForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<OrderItem> orderItems = orderItemService.findOrderItems();
        model.addAttribute("usageRequestDto", new UsageRequestDto());
        model.addAttribute("members", members);
        model.addAttribute("orderItems", orderItems);
        return "/usages/usageForm";
    }

    @PostMapping("/usages/new")
    public String usage(@ModelAttribute UsageRequestDto usageRequestDto) {
        usageService.saveUsage(usageRequestDto);
        return "redirect:/";
    }

    @GetMapping("/usages")
    public String usageList(Model model, @ModelAttribute UsageSearchDto usageSearchDto) {
        List<Usage> usages = usageService.search(usageSearchDto);
        model.addAttribute("usageSearchDto", new UsageSearchDto());
        model.addAttribute("usages", usages);
        return "/usages/usageList";
    }
}