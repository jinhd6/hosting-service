package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageCond;

import java.util.List;

public interface UsageRepositoryCustom {

    List<Usage> search(UsageCond usageCond);
}
