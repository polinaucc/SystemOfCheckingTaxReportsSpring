package ua.polina.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.system.entity.Inspector;
import ua.polina.system.entity.User;
import ua.polina.system.repository.InspectorRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InspectorService {
    InspectorRepository inspectorRepository;

    @Autowired
    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public Page<Inspector> getAllInspectors(Pageable pageable) {
        return inspectorRepository.findAll(pageable);
    }

    public List<Inspector> getAllInspectors() {
        return inspectorRepository.findAll();

    }

    //TODO: not used method
    public Optional<Inspector> getByUser(User user) {
        return inspectorRepository.findByUser(user);
    }

    public Optional<Inspector> getById(Long id) {
        return inspectorRepository.findById(id);
    }

    public Inspector saveNewInspector(Inspector inspector) {
        return inspectorRepository.save(inspector);
    }

}
