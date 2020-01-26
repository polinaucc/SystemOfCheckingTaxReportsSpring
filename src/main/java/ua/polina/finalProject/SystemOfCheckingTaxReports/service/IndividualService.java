package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Individual;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.IndividualRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IndividualService {
    private final IndividualRepository individualRepository;

    @Autowired
    public IndividualService(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    public List<Individual> getAllIndividuals(Pageable pageable) {
        Page<Individual> allIndividuals = individualRepository.findAll(pageable);
        return allIndividuals.getContent();
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

    public List<Individual> getByAddress(String address) {
        return individualRepository.findByAddress(address);
    }

    public Optional<Individual> getByIdentCode(String identCode) {
        return individualRepository.findByIdentCode(identCode);
    }

    public void deleteById(Long id) {
        individualRepository.deleteById(id);
    }

    @Transactional
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

    public boolean isExistByPassport(String passport){
        return individualRepository.existsIndividualByPassport(passport);
    }

    public boolean isExistByIdentCode(String identCode){
        return individualRepository.existsIndividualByIdentCode(identCode);
    }
}
