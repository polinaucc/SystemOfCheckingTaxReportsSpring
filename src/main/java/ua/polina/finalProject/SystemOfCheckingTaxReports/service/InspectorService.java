package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.InspectorRepository;

import javax.transaction.Transactional;
import java.util.List;
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

    public List<Inspector> getAllInspectors(Pageable pageable) {
        Page<Inspector> allInspectors = inspectorRepository.findAll(pageable);
        return allInspectors.getContent();
    }

    public Optional<Inspector> getById(Long id){
        return inspectorRepository.findById(id);
    }

    public Inspector saveNewInspector(Inspector inspector){
        return inspectorRepository.save(inspector);
    }

}
