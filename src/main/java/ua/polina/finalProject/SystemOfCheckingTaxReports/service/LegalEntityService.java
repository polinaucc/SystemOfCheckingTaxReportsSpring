package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.LegalEntity;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.LegalEntityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;

    @Autowired
    public LegalEntityService(LegalEntityRepository legalEntityRepository) {
        this.legalEntityRepository = legalEntityRepository;
    }

    public List<LegalEntity> getAllLegalEntities(Pageable pageable) {
        Page<LegalEntity> allLegalEntities = legalEntityRepository.findAll(pageable);
        return allLegalEntities.getContent();
    }

    public Optional<LegalEntity> getById(Long id) {
        return legalEntityRepository.findById(id);
    }

    public LegalEntity saveNewLegalEntity(LegalEntity legalEntity) {
        return legalEntityRepository.save(legalEntity);
    }

    public Optional<LegalEntity> getByEdrpou(String edrpou) {
        return legalEntityRepository.findByEdrpou(edrpou);
    }

    public LegalEntity update(LegalEntity legalEntity) {
        Long id = legalEntity.getId();

        return legalEntityRepository.findById(id)
                .map(individualFromDB -> {
                    individualFromDB.setClient(legalEntity.getClient());
                    individualFromDB.setName(legalEntity.getName());
                    individualFromDB.setEdrpou(legalEntity.getEdrpou());
                    individualFromDB.setMfo(legalEntity.getMfo());
                    individualFromDB.setAddress(legalEntity.getAddress());

                    return legalEntityRepository.save(individualFromDB);
                }).orElseGet(() -> legalEntityRepository.save(legalEntity));
    }

    public void deleteById(Long id) {
        legalEntityRepository.deleteById(id);
    }

    //TODO: not used methods
    public boolean isExistByEdrpou(String edrpou) {
        return legalEntityRepository.existsLegalEntityByEdrpou(edrpou);
    }

    public boolean isExistByName(String name) {
        return legalEntityRepository.existsLegalEntityByEdrpou(name);
    }
}