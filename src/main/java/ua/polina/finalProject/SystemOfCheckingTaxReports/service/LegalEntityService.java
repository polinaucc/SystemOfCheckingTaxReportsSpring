package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.LegalEntitiesDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.LegalEntity;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.LegalEntityRepository;

import javax.transaction.Transactional;
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

    public LegalEntitiesDTO getAllLegalEntities(int page, int size, String sortParametr, String sortDir) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParametr);
        Page<LegalEntity> allLegalEntities = legalEntityRepository.findAll(pageReq);
        return new LegalEntitiesDTO(allLegalEntities.getContent());
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
        return legalEntityRepository.findById(id).map(individualFromDB -> {
            individualFromDB.setClient(legalEntity.getClient());
            individualFromDB.setName(legalEntity.getName());
            individualFromDB.setEdrpou(legalEntity.getEdrpou());
            individualFromDB.setMfo(legalEntity.getMfo());
            individualFromDB.setAddress(legalEntity.getAddress());

            return legalEntityRepository.save(individualFromDB);
        }).orElseGet(() -> legalEntityRepository.save(legalEntity));
    }

    public void deleteById (Long id) {
        legalEntityRepository.deleteById(id);
    }
}