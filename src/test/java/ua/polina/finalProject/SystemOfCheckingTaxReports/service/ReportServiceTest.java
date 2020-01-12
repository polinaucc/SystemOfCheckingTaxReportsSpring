package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ReportsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ReportRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {
    @Mock
    ReportRepository reportRepository;

    @InjectMocks
    ReportService reportService;
    ReportsDTO reports;
    Client clientWith1ID;
    Client clientWith2ID;
    Inspector inspector;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reports = ReportsDTO.builder()
                .reports(Arrays.asList(
                        Report.builder()
                                .id(1L)
                                .client(clientWith1ID)
                                .inspector(inspector)
                                .status(Status.ACCEPTED)
                                .build(),
                        Report.builder()
                                .id(2L)
                                .client(clientWith2ID)
                                .inspector(inspector)
                                .status(Status.REJECTED)
                                .build()
                ))
                .build();
    }

    @Test
    void getAll() {
        int page = 1;
        int size = 10;
        String sortParameter = "status";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        when(reportRepository.findAll(pageReq)).thenReturn(new PageImpl<>(reports.getReports()));

        ReportsDTO actualReportsDTO = reportService.getAllReports(page, size, sortParameter, sortDir);

        Assert.assertEquals(reports, actualReportsDTO);
    }

    @Test
    void getById() {
        Report expectedReport = reports.getReports().get(0);
        Long reportID = expectedReport.getId();
        Optional<Report> expectedOptionalReport = Optional.of(expectedReport);
        when(reportRepository.findById(reportID)).thenReturn(expectedOptionalReport);

        Optional<Report> actualReport = reportService.getById(reportID);

        Assert.assertEquals(expectedOptionalReport, actualReport);
    }

    @Test
    void saveNewReport() {
        Report currentReport = reports.getReports().get(0);
        reportService.saveNewReport(currentReport);
        verify(reportRepository, times(1)).save(currentReport);
    }

    @Test
    void getByClient() {
        when(reportRepository.findByClient(clientWith1ID)).thenReturn(reports.getReports());

        ReportsDTO actualReports = reportService.getByClient(clientWith1ID);

        Assert.assertEquals(reports, actualReports);
    }

    @Test
    void getByInspector() {
        when(reportRepository.findByInspector(inspector)).thenReturn(reports.getReports());

        ReportsDTO actualReports = reportService.getByInspector(inspector);

        Assert.assertEquals(reports, actualReports);
    }

    @Test
    void getByDate() {
        Date date = new Date();
        ReportsDTO expectedReports = new ReportsDTO(Arrays.asList(reports.getReports().get(0)));
        when(reportRepository.findByDate(date)).thenReturn(Arrays.asList(reports.getReports().get(0)));

        ReportsDTO actualReports = reportService.getByDate(date);

        Assert.assertEquals(expectedReports, actualReports);
    }

    @Test
    void getBetweenDates () {
        Date startDate = new Date();
        Date endDate = new Date();
        ReportsDTO expectedReports = new ReportsDTO(Arrays.asList(reports.getReports().get(0)));
        when(reportRepository.findByDateBetween(startDate, endDate)).thenReturn(Arrays.asList(reports.getReports().get(0)));

        ReportsDTO actualReports = reportService.getBetweenDates(startDate, endDate);

        Assert.assertEquals(expectedReports, actualReports);
    }

    @Test
    void getByStatus() {
        when(reportRepository.findByStatus(Status.ACCEPTED)).thenReturn(reports.getReports());

        ReportsDTO actualReports = reportService.getByStatus(Status.ACCEPTED);

        Assert.assertEquals(reports, actualReports);
    }

    @Test
    void update() {
        Report currentReport = reports.getReports().get(0);
        reportService.update(currentReport);
        verify(reportRepository, times(1)).save(currentReport);
    }

    @Test
    void deleteById() {
        Long currentReportID = reports.getReports().get(0).getId();
        reportService.deleteById(currentReportID);
        verify(reportRepository, times(1)).deleteById(currentReportID);
    }
}