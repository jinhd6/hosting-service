package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageRequestDto;
import com.jmhong.hosting.dto.UsageSearchDto;
import com.jmhong.hosting.repository.MemberRepository;
import com.jmhong.hosting.repository.OrderItemRepository;
import com.jmhong.hosting.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsageService {

    private UsageRepository usageRepository;
    private MemberRepository memberRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public UsageService(UsageRepository usageRepository,
                        MemberRepository memberRepository, OrderItemRepository orderItemRepository) {
        this.usageRepository = usageRepository;
        this.memberRepository = memberRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Usage saveUsage(UsageRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();
        OrderItem orderItem = orderItemRepository.findById(dto.getOrderItemId()).orElseThrow();
        Usage usage = new Usage(member, orderItem, dto.getConnectDate(), dto.getDisconnectDate());
        usageRepository.save(usage);
        return usage;
    }

    @Transactional(readOnly = true)
    public List<Usage> search(UsageSearchDto usageSearchDto) {
        return usageRepository.search(usageSearchDto);
    }
}
