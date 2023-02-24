package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
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
        completeOrder(orderItem.getOrder());
        Usage usage = new Usage(member, orderItem, dto.getConnectDate(), dto.getDisconnectDate());
        usageRepository.save(usage);
        return usage;
    }

    private static void completeOrder(Order order) {
        if (order.getStatus() == OrderStatus.ORDER) {
            order.updateStatus(OrderStatus.COMPLETE);
        }
    }

    @Transactional(readOnly = true)
    public List<Usage> search(UsageSearchDto usageSearchDto) {
        return usageRepository.search(usageSearchDto);
    }

    @Transactional(readOnly = true)
    public List<Usage> findUsages() {
        return usageRepository.findAll();
    }
}
