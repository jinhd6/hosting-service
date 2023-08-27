package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.*;
import com.jmhong.hosting.repository.OrderItemRepository;
import com.jmhong.hosting.repository.UsageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsageServiceTest {

    @Mock
    private UsageRepository usageRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private UsageService usageService;

    @Test
    void saveUsage() {
        // Given
        OrderItem orderItem = mock(OrderItem.class);
        Order order = mock(Order.class);
        when(orderItem.getOrder()).thenReturn(order);
        when(orderItem.getActivateDate())
                .thenReturn(LocalDateTime.of(2022, 12, 31, 23, 59));
        when(orderItem.getExpireDate())
                .thenReturn(LocalDateTime.of(2023, 1, 2, 0, 1));
        LocalDateTime connectTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime disconnectTime = LocalDateTime.of(2023, 1, 2, 0, 0);
        UsageRequestDto usageRequestDto = new UsageRequestDto(1L, 2L, connectTime, disconnectTime);
        when(orderItemRepository.findById(2L)).thenReturn(Optional.of(orderItem));

        // When
        Usage usage = usageService.saveUsage(usageRequestDto);

        // Then
        assertEquals(orderItem, usage.getOrderItem());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0), usage.getConnectDate());
        assertEquals(LocalDateTime.of(2023, 1, 2, 0, 0), usage.getDisconnectDate());
    }

    @Test
    void search() {
        Usage usage1 = mock(Usage.class);
        Usage usage2 = mock(Usage.class);
        UsageCond usageCond = mock(UsageCond.class);
        when(usageRepository.search(usageCond)).thenReturn(new ArrayList<>(Arrays.asList(usage1, usage2)));

        List<Usage> usages = usageService.search(usageCond);

        assertEquals(2, usages.size());
        assertTrue(usages.contains(usage1));
        assertTrue(usages.contains(usage2));
    }
}