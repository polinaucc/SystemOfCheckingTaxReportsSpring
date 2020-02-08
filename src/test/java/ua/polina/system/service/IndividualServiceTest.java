package ua.polina.system.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.polina.system.entity.Client;
import ua.polina.system.entity.Individual;
import ua.polina.system.entity.Inspector;
import ua.polina.system.repository.IndividualRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class IndividualServiceTest {

    @Mock
    IndividualRepository individualRepository;

    @InjectMocks
    IndividualService individualService;
    List<Individual> individuals;
    Client client;
    Inspector inspector;
    Client clientWith1ID;
    Client clientWith2ID;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        individuals = Arrays.asList(
                        Individual.builder()
                                .id(1L)
                                .client(clientWith1ID)
                                .surname("Smith")
                                .passport("ВТ123456")
                                .identCode("3456123455")
                                .address("Peremogy Street, 25")
                                .build(),
                        Individual.builder()
                                .id(2L)
                                .client(clientWith2ID)
                                .passport("ВТ987654")
                                .identCode("3456123456")
                                .address("Peremogy Street, 25")
                                .surname("Morgan")
                                .build()
                );
    }

    @Test
    public void getById() {
        Individual expectedIndividual = individuals.get(0);
        Long individualID = expectedIndividual.getId();
        Optional<Individual> expectedOptionalIndividual = Optional.of(expectedIndividual);
        when(individualRepository.findById(individualID)).thenReturn(expectedOptionalIndividual);

        Optional<Individual> actualIndividual = individualService.getById(individualID);

        Assert.assertEquals(expectedOptionalIndividual, actualIndividual);
    }

    @Test
    void saveNewIndividual() {
        Individual currentIndividual = individuals.get(0);
        individualService.saveNewIndividual(currentIndividual);
        verify(individualRepository, times(1)).save(currentIndividual);
    }

    @Test
    void getByPassport() {
        Individual expectedIndividual = individuals.get(0);
        String individualPassport = "ВТ123456";
        Optional<Individual> expectedOptionalIndividual = Optional.of(expectedIndividual);
        when(individualRepository.findByPassport(individualPassport)).thenReturn(expectedOptionalIndividual);

        Optional<Individual> actualIndividual = individualService.getByPassport(individualPassport);

        Assert.assertEquals(expectedOptionalIndividual, actualIndividual);
    }

    @Test
    void getByAddress() {
        String address = "Peremogy Street, 25";
        when(individualRepository.findByAddress(address)).thenReturn(individuals);

        List<Individual> actualIndividuals = individualService.getByAddress(address);

        Assert.assertEquals(individuals, actualIndividuals);
    }

    @Test
    void getByIdentCode() {
        Individual expectedIndividual = individuals.get(0);
        String individualIdentCode = "3456123456";
        Optional<Individual> expectedOptionalIndividual = Optional.of(expectedIndividual);
        when(individualRepository.findByIdentCode(individualIdentCode)).thenReturn(expectedOptionalIndividual);

        Optional<Individual> actualIndividual = individualService.getByIdentCode(individualIdentCode);

        Assert.assertEquals(expectedOptionalIndividual, actualIndividual);
    }

    @Test
    void update() {
        Individual currentIndividual = individuals.get(0);
        individualService.update(currentIndividual);
        verify(individualRepository, times(1)).save(currentIndividual);
    }

    @Test
    void deleteById() {
        Long currentIndividualID = individuals.get(0).getId();
        individualService.deleteById(currentIndividualID);
        verify(individualRepository, times(1)).deleteById(currentIndividualID);
    }
}