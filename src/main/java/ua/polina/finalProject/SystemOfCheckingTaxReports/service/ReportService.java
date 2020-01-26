package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ReportRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports(Pageable pageable) {
        Page<Report> allReports = reportRepository.findAll(pageable);
        return allReports.getContent();
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

    public List<Report> getByInspector(Inspector inspector) {
        return reportRepository.findByInspector(inspector);
    }

    public List<Report> getByDate(Date date) {
        return reportRepository.findByDate(date);
    }

    public List<Report> getBetweenDates(Date startDate, Date endDate) {
        return reportRepository.findByDateBetween(startDate, endDate);
    }

    public List<Report> getByStatus(Status status) {
        return reportRepository.findByStatus(status);
    }

    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }

    public Report update(Report report) {
        Long id = report.getId();
        return reportRepository.findById(id).map(reportFromDB -> {
            reportFromDB.setClient(report.getClient());
            reportFromDB.setInspector(report.getInspector());
            reportFromDB.setDate(report.getDate());
            reportFromDB.setComment(report.getComment());
            reportFromDB.setStatus(report.getStatus());

            return reportRepository.save(reportFromDB);
        }).orElseGet(() -> reportRepository.save(report));
    }
}
