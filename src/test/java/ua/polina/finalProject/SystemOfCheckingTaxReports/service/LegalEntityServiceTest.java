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
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.LegalEntitiesDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Individual;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.LegalEntity;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.LegalEntityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LegalEntityServiceTest {
    private final String edrpo1 = "14312553";
    private final String edrpo2 = "14312551";

    @Mock
    LegalEntityRepository legalEntityRepository;

    @InjectMocks
    LegalEntityService legalEntityService;
    LegalEntitiesDTO legalEntities;
    Client clientWith1ID;
    Client clientWith2ID;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LegalEntity legalEntity1 = LegalEntity.builder()
                .id(1L)
                .client(clientWith1ID)
                .name("FirstLegalEntity")
                .edrpou(edrpo1)
                .mfo("mfo")
                .address("address")
                .build();
        LegalEntity legalEntity2 = LegalEntity.builder()
                .id(1L)
                .client(clientWith2ID)
                .name("FirstLegalEntity")
                .edrpou(edrpo2)
                .mfo("mfo")
                .address("address")
                .build();

        legalEntities = LegalEntitiesDTO.builder()
                .legalEntities(
                        Arrays.asList(
                                legalEntity1,
                                legalEntity2
                        )
                )
                .build();
    }

    @Test
    void saveNewLegalEntity () {
        LegalEntity currentLegacyEntity = legalEntities.getLegalEntities().get(0);
        legalEntityService.saveNewLegalEntity(currentLegacyEntity);
        verify(legalEntityRepository, times(1)).save(currentLegacyEntity);
    }

    @Test
    void getAllLegalEntities() {
        int page = 1;
        int size = 5;
        String sortParameter = "name";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        when(legalEntityRepository.findAll(pageReq)).thenReturn(new PageImpl<>(legalEntities.getLegalEntities()));

        LegalEntitiesDTO actualLegalEntitiesDTO = legalEntityService.getAllLegalEntities(page, size, sortParameter, sortDir);

        Assert.assertEquals(legalEntities, actualLegalEntitiesDTO);
    }

    @Test
    void getById() {
        LegalEntity expectedLegalEntity = legalEntities.getLegalEntities().get(0);
        Long legalEntityID = expectedLegalEntity.getId();
        Optional<LegalEntity> expectedOptionalLegalEntity = Optional.of(expectedLegalEntity);
        when(legalEntityRepository.findById(legalEntityID)).thenReturn(expectedOptionalLegalEntity);

        Optional<LegalEntity> actualLegalEntity = legalEntityService.getById(legalEntityID);

        Assert.assertEquals(expectedOptionalLegalEntity, actualLegalEntity);
    }

    @Test
    void getByEdrpou() {
        LegalEntity expectedLegalEntity = legalEntities.getLegalEntities().get(0);
        Optional<LegalEntity> expectedOptionalLegalEntity = Optional.of(expectedLegalEntity);
        when(legalEntityRepository.findByEdrpou(edrpo1)).thenReturn(expectedOptionalLegalEntity);

        Optional<LegalEntity> actualLegalEntity = legalEntityService.getByEdrpou(edrpo1);

        Assert.assertEquals(expectedOptionalLegalEntity, actualLegalEntity);
    }

    @Test
    void update() {
        LegalEntity currentLegacyEntity = legalEntities.getLegalEntities().get(0);
        legalEntityService.update(currentLegacyEntity);
        verify(legalEntityRepository, times(1)).save(currentLegacyEntity);
    }

    @Test
    void deleteById() {
        Long currentLegacyEntityID = legalEntities.getLegalEntities().get(0).getId();
        legalEntityService.deleteById(currentLegacyEntityID);
        verify(legalEntityRepository, times(1)).deleteById(currentLegacyEntityID);
    }
}