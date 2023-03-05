package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.UsageRequestDto;
import com.jmhong.hosting.dto.UsageSearchDto;
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
    private OrderItemRepository orderItemRepository;

    @Autowired
    public UsageService(UsageRepository usageRepository, OrderItemRepository orderItemRepository) {
        this.usageRepository = usageRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Usage saveUsage(UsageRequestDto dto) {
        OrderItem orderItem = orderItemRepository.findById(dto.getOrderItemId()).orElseThrow();
        Usage usage = Usage.createUsage(orderItem, dto.getConnectDate(), dto.getDisconnectDate());
        usageRepository.save(usage);
        return usage;
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
