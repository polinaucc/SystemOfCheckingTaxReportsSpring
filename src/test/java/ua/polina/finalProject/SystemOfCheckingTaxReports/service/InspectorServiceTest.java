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
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.InspectorsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Individual;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.InspectorRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InspectorServiceTest {
    @Mock
    InspectorRepository inspectorRepository;

    @InjectMocks
    InspectorService inspectorService;
    InspectorsDTO inspectors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        inspectors = InspectorsDTO.builder()
                .inspectors(Arrays.asList(
                        Inspector.builder()
                                .id(1L)
                                .build(),
                        Inspector.builder()
                                .id(2L)
                                .build()
                ))
                .build();
    }

    @Test
    void getAll() {
        int page = 1;
        int size = 10;
        String sortParameter = "reason";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        when(inspectorRepository.findAll(pageReq)).thenReturn(new PageImpl<>(inspectors.getInspectors()));

        InspectorsDTO actualInspectors = inspectorService.getAllInspectors(page, size, sortParameter, sortDir);

        Assert.assertEquals(actualInspectors, inspectors);
    }

    @Test
    void getById() {
        Inspector expectedInspector = inspectors.getInspectors().get(0);
        Long inspectorID = expectedInspector.getId();
        Optional<Inspector> expectedOptionalInspector = Optional.of(expectedInspector);
        when(inspectorRepository.findById(inspectorID)).thenReturn(expectedOptionalInspector);

        Optional<Inspector> actualInspector = inspectorService.getById(inspectorID);

        Assert.assertEquals(expectedOptionalInspector, actualInspector);
    }

    @Test
    void saveNewInspector() {
        Inspector currentInspector = inspectors.getInspectors().get(0);
        inspectorService.saveNewInspector(currentInspector);
        verify(inspectorRepository, times(1)).save(currentInspector);
    }
}