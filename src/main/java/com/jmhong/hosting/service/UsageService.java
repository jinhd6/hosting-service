package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageSearchDto;
import com.jmhong.hosting.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsageService {

    private final UsageRepository usageRepository;

    @Autowired
    public UsageService(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    @Transactional
    public Long saveUsage(Usage usage) {
        usageRepository.save(usage);
        return usage.getId();
    }

    public List<Usage> search(UsageSearchDto usageSearchDto) {
        return usageRepository.search(usageSearchDto);
    }
}
