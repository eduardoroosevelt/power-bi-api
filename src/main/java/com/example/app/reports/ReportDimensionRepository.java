package com.example.app.reports;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDimensionRepository extends JpaRepository<ReportDimension, Long> {
    List<ReportDimension> findByReportIdAndActiveTrue(Long reportId);

    boolean existsByReportIdAndDimensionKey(Long reportId, String dimensionKey);
}
