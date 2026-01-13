package com.example.app.reports;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PowerBiReportRepository extends JpaRepository<PowerBiReport, Long> {
    Optional<PowerBiReport> findByIdAndAtivoTrue(Long id);
}
