package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepository extends JpaRepository<Usage, Long>, UsageRepositoryCustom {
}
