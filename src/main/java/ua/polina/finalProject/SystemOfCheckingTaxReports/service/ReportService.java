package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ReportsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ReportRepository;

import javax.transaction.Transactional;
import java.util.Date;
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

    public ReportsDTO getAllReports(int page, int size, String sortParametr, String sortDir){
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParametr);
        Page<Report> allReports = reportRepository.findAll(pageReq);
        return new ReportsDTO(allReports.getContent());
    }

    public Optional<Report> getById(Long id){
        return reportRepository.findById(id);
    }

    public Report saveNewReport(Report report){
        return reportRepository.save(report);
    }

    public ReportsDTO getByClient(Client client){
        return new ReportsDTO(reportRepository.findByClient(client));
    }

    public ReportsDTO getByInspector(Inspector inspector){
        return new ReportsDTO(reportRepository.findByInspector(inspector));
    }

    public ReportsDTO getByDate(Date date){
        return new ReportsDTO(reportRepository.findByDate(date));
    }

    public ReportsDTO getBetweenDates (Date startDate, Date endDate) {
        return new ReportsDTO(reportRepository.findByDateBetween(startDate, endDate));
    }

    public ReportsDTO getByStatus(Status status){
        return new ReportsDTO(reportRepository.findByStatus(status));
    }

    public void deleteById (Long id) {
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
