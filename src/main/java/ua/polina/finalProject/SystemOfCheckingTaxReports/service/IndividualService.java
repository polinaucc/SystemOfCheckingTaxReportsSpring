package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.IndividualsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Individual;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.IndividualRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class IndividualService {
    private final IndividualRepository individualRepository;

    @Autowired
    public IndividualService(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    public Optional<Individual> getById(Long id) {
        return individualRepository.findById(id);
    }

    public Individual saveNewIndividual(Individual individual) {
        return individualRepository.save(individual);
    }

    public Optional<Individual> getByPassport(String passport) {
        return individualRepository.findByPassport(passport);
    }

    public IndividualsDTO getByAddress(String address) {
        return new IndividualsDTO(individualRepository.findByAddress(address));
    }

    public Optional<Individual> getByIdentCode(String identCode) {
        return individualRepository.findByIdentCode(identCode);
    }

    public void deleteById(Long id) {
        individualRepository.deleteById(id);
    }

    public Individual update(Individual individual) {
        Long id = individual.getId();
        return individualRepository.findById(id).map(individualFromDB -> {
            individualFromDB.setClient(individual.getClient());
            individualFromDB.setSurname(individual.getSurname());
            individualFromDB.setFirstName(individual.getFirstName());
            individualFromDB.setSecondName(individual.getSecondName());
            individualFromDB.setPassport(individual.getPassport());
            individualFromDB.setIdentCode(individual.getIdentCode());
            individualFromDB.setAddress(individual.getAddress());

            return individualRepository.save(individualFromDB);
        }).orElseGet(() -> individualRepository.save(individual));
    }
}
