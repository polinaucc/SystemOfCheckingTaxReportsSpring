package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.InspectorsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.InspectorRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class InspectorService {
    InspectorRepository inspectorRepository;

    @Autowired
    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public InspectorsDTO getAllInspectors(int page, int size, String sortParameter, String sortDir) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        Page<Inspector> allInspectors = inspectorRepository.findAll(pageReq);
        return new InspectorsDTO(allInspectors.getContent());
    }

    public Optional<Inspector> getById(Long id){
        return inspectorRepository.findById(id);
    }

    public Inspector saveNewInspector(Inspector inspector){
        return inspectorRepository.save(inspector);
    }

}
