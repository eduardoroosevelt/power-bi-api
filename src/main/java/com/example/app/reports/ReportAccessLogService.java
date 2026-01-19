package com.example.app.reports;

import com.example.app.common.NotFoundException;
import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportAccessLogService {
    private final PowerBiReportRepository reportRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReportAccessLogRepository logRepository;

    public ReportAccessLogService(PowerBiReportRepository reportRepository,
                                  UsuarioRepository usuarioRepository,
                                  ReportAccessLogRepository logRepository) {
        this.reportRepository = reportRepository;
        this.usuarioRepository = usuarioRepository;
        this.logRepository = logRepository;
    }

    @Transactional
    public ReportAccessLogResponse logAccess(Long reportId, String username, String ipAddress, int durationSeconds) {
        PowerBiReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new NotFoundException("Report not found"));
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"));
        ReportAccessLog log = new ReportAccessLog();
        log.setReport(report);
        log.setUsuario(usuario);
        log.setUsername(usuario.getUsername());
        log.setIpAddress(ipAddress);
        log.setDurationSeconds(durationSeconds);
        log.setAccessedAt(Instant.now());
        ReportAccessLog saved = logRepository.save(log);
        return toResponse(saved);
    }

    private ReportAccessLogResponse toResponse(ReportAccessLog log) {
        ReportAccessLogResponse response = new ReportAccessLogResponse();
        response.setId(log.getId());
        response.setReportId(log.getReport().getId());
        response.setUsuarioId(log.getUsuario().getId());
        response.setUsername(log.getUsername());
        response.setIpAddress(log.getIpAddress());
        response.setDurationSeconds(log.getDurationSeconds());
        response.setAccessedAt(log.getAccessedAt());
        return response;
    }
}
