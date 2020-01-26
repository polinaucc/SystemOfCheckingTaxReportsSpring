package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.ErrorMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.DateDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.DatesDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ReportDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ClientService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.InspectorService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ReportService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    ReportService reportService;

    @Autowired
    ClientService clientService;

    @Autowired
    InspectorService inspectorService;

    @GetMapping("")
    public List<Report> getAllReports(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return reportService.getAllReports(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReportById(@PathVariable(name = "id") Long id) {
        return reportService.getById(id)
                .map(claim -> ResponseEntity.ok(claim))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void saveNewClaim(@Valid @RequestBody ReportDTO reqReport) throws Exception {
        Client client = clientService.getById(reqReport.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("client", "id", reqReport.getClientId()));
        Inspector inspector = inspectorService.getById(reqReport.getInspectorId())
                .orElseThrow(() -> new ResourceNotFoundException("inspector", "id", reqReport.getInspectorId()));

        Status status = Status.valueOf(reqReport.getStatus().toUpperCase());
        Report report = Report.builder()
                .client(client)
                .inspector(inspector)
                .date(reqReport.getDate())
                .comment(reqReport.getComment())
                .status(status)
                .build();

        this.reportService.saveNewReport(report);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity getReportByClientId(@PathVariable(name = "clientId") Long clientId) throws ResourceNotFoundException {
        Client client = clientService.getById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("report", "id", clientId));
        return ResponseEntity.ok(
                reportService.getByClient(client)
        );
    }

    @GetMapping("/{inspectorId}")
    public ResponseEntity getReportByInspectorId(@PathVariable(name = "inspectorId") Long inspectorId) throws ResourceNotFoundException {
        Inspector inspector = inspectorService.getById(inspectorId).orElseThrow(() -> new ResourceNotFoundException("report", "id", inspectorId));
        return ResponseEntity.ok(
                reportService.getByInspector(inspector)
        );
    }

    @GetMapping("/date")
    public ResponseEntity getReportByDate(@Valid @RequestBody DateDTO reqDate) throws ResourceNotFoundException {
        List<Report> reports = reportService.getByDate(reqDate.getDate());
        if (reports.size() < 1) throw new ResourceNotFoundException("report", "date", reqDate.getDate());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/between")
    public ResponseEntity getReportBetweenDates(@Valid @RequestBody DatesDTO reqDates) throws ResourceNotFoundException {
        List<Report> reports = reportService.getBetweenDates(reqDates.getDate1(), reqDates.getDate2());
        if (reports.size() < 1) throw new ResourceNotFoundException("report", "date", reqDates.getDate1());
        return ResponseEntity.ok(reports);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        reportService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update}")
    public void update(@Valid @RequestBody Report report) {
        this.reportService.saveNewReport(report);
    }
}
