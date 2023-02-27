package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.MemberResponseDto;
import com.jmhong.hosting.dto.MemberSearchDto;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String usageMemberList(Model model, @ModelAttribute MemberSearchDto memberSearchDto) {
        List<MemberResponseDto> searchResults = memberService.searchMember(memberSearchDto);
        model.addAttribute("memberSearchDto", new MemberSearchDto());
        model.addAttribute("results", searchResults);
        model.addAttribute("memberTypes", MemberType.values());
        return "/usages/usageMemberList";
    }

    @GetMapping("/usages/{memberId}/new")
    public String usageForm(Model model, @PathVariable Long memberId) {
        List<OrderItem> orderItems = orderItemService.findActiveOrderItems(memberId);
        String username = memberService.searchById(memberId).getUsername();

        model.addAttribute("usageRequestDto", new UsageRequestDto());
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("username", username);
        return "/usages/usageForm";
    }

    @PostMapping("/usages/{memberId}/new")
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
