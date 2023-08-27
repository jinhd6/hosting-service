package com.jmhong.hosting.controller;

import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.dto.MemberCond;
import com.jmhong.hosting.dto.MemberResponseDto;
import com.jmhong.hosting.dto.MemberUpdateDto;
import com.jmhong.hosting.service.MemberService;
import com.jmhong.hosting.dto.MemberCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createMemberForm(Model model) {
        model.addAttribute("memberForm", new MemberCreateDto());
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@ModelAttribute MemberCreateDto memberCreateDto) {
        memberService.join(memberCreateDto);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model, @ModelAttribute MemberCond memberCond) {
        List<MemberResponseDto> searchResults = memberService.searchMember(memberCond);
        model.addAttribute("memberSearchDto", new MemberCond());
        model.addAttribute("results", searchResults);
        model.addAttribute("memberTypes", MemberType.values());
        return "/members/memberList";
    }

    @GetMapping("/members/{memberId}/edit")
    public String updateMemberForm(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberUpdateDto", new MemberUpdateDto());
        return "/members/updateMemberForm";
    }

    @PostMapping("/members/{memberId}/edit")
    public String updateMember(@PathVariable Long memberId, @ModelAttribute MemberUpdateDto memberUpdateDto) {
        memberService.updateMember(memberId, memberUpdateDto);
        return "redirect:/";
    }
}
