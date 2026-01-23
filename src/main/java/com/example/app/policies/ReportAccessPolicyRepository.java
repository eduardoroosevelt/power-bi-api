package com.example.app.policies;

import com.example.app.common.Enums.SubjectType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAccessPolicyRepository extends JpaRepository<ReportAccessPolicy, Long> {
    Optional<ReportAccessPolicy> findTopByReportIdAndSubjectTypeAndSubjectIdAndActiveTrueOrderByPriorityDesc(
        Long reportId,
        SubjectType subjectType,
        Long subjectId
    );

    List<ReportAccessPolicy> findByReportIdAndSubjectTypeAndSubjectIdInAndActiveTrueOrderByPriorityDesc(
        Long reportId,
        SubjectType subjectType,
        List<Long> subjectIds
    );

    List<ReportAccessPolicy> findByReportId(Long reportId);
}
