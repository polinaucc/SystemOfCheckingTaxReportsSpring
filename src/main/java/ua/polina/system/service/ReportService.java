package ua.polina.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.system.entity.Client;
import ua.polina.system.entity.Inspector;
import ua.polina.system.entity.Report;
import ua.polina.system.entity.Status;
import ua.polina.system.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    //TODO: not used method
    public Page<Report> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    public Optional<Report> getById(Long id) {
        return reportRepository.findById(id);
    }

    public Report saveNewReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getByClient(Client client) {
        return reportRepository.findByClient(client);
    }

    public List<Report> getByStatus(Status status) {
        return reportRepository.findByStatus(status);
    }

    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }

    public Report update(Report report, Status status) {
        Long id = report.getId();

        return reportRepository.findById(id)
                .map(reportFromDB -> {
                    reportFromDB.setDate(LocalDateTime.now());
                    reportFromDB.setStatus(status);
                    reportFromDB.setComment(report.getComment());

                    return reportRepository.save(reportFromDB);
                }).orElseGet(() -> reportRepository.save(report));
    }

    public List<Report> getByInspector(Inspector inspector) {
        return reportRepository.findByInspector(inspector);
    }
}


