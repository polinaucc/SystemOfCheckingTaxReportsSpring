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
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ReportRepository;

import java.util.*;

import static org.mockito.Mockito.*;

class ReportServiceTest {
    @Mock
    ReportRepository reportRepository;

    @InjectMocks
    ReportService reportService;
    List<Report> reports;
    Client clientWith1ID;
    Client clientWith2ID;
    Inspector inspector;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reports = Arrays.asList(
                        Report.builder()
                                .id(1L)
                                .client(clientWith1ID)
                                .status(Status.ACCEPTED)
                                .build(),
                        Report.builder()
                                .id(2L)
                                .client(clientWith2ID)
                                .status(Status.REJECTED)
                                .build()
                );
    }

//    @Test
//    void getAll() {
//        int page = 1;
//        int size = 10;
//        String sortParameter = "status";
//        String sortDir = "asc";
//        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
//        when(reportRepository.findAll(pageReq)).thenReturn(new PageImpl<>(reports));
//
//        List<Report> actualReportsDTO = reportService.getAllReports(pageReq);
//
//        Assert.assertEquals(reports, actualReportsDTO);
//    }

    @Test
    void getById() {
        Report expectedReport = reports.get(0);
        Long reportID = expectedReport.getId();
        Optional<Report> expectedOptionalReport = Optional.of(expectedReport);
        when(reportRepository.findById(reportID)).thenReturn(expectedOptionalReport);

        Optional<Report> actualReport = reportService.getById(reportID);

        Assert.assertEquals(expectedOptionalReport, actualReport);
    }

    @Test
    void saveNewReport() {
        Report currentReport = reports.get(0);
        reportService.saveNewReport(currentReport);
        verify(reportRepository, times(1)).save(currentReport);
    }

    @Test
    void getByClient() {
        when(reportRepository.findByClient(clientWith1ID)).thenReturn(reports);

        List<Report> actualReports = reportService.getByClient(clientWith1ID);

        Assert.assertEquals(reports, actualReports);
    }


//    @Test
//    void getByDate() {
//        Date date = new Date();
//        List<Report> expectedReports = Collections.singletonList(reports.get(0));
//        when(reportRepository.findByDate(date)).thenReturn(Collections.singletonList(reports.get(0)));
//
//        List<Report> actualReports = reportService.getByDate(date);
//
//        Assert.assertEquals(expectedReports, actualReports);
//    }

//    @Test
//    void getBetweenDates () {
//        Date startDate = new Date();
//        Date endDate = new Date();
//        List<Report> expectedReports = Arrays.asList(reports.get(0));
//        when(reportRepository.findByDateBetween(startDate, endDate)).thenReturn(Arrays.asList(reports.get(0)));
//
//        List<Report> actualReports = reportService.getBetweenDates(startDate, endDate);
//
//        Assert.assertEquals(expectedReports, actualReports);
//    }

    @Test
    void getByStatus() {
        when(reportRepository.findByStatus(Status.ACCEPTED)).thenReturn(reports);

        List<Report> actualReports = reportService.getByStatus(Status.ACCEPTED);

        Assert.assertEquals(reports, actualReports);
    }

    @Test
    void update() {
        Report currentReport = reports.get(0);
        reportService.update(currentReport, Status.NOT_CHECKED);
        verify(reportRepository, times(1)).save(currentReport);
    }

    @Test
    void deleteById() {
        Long currentReportID = reports.get(0).getId();
        reportService.deleteById(currentReportID);
        verify(reportRepository, times(1)).deleteById(currentReportID);
    }
}