package ua.polina.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.system.entity.Renouncement;
import ua.polina.system.entity.Report;
import ua.polina.system.repository.RenouncementRepository;

import java.util.List;

@Service
public class RenouncementService {
    RenouncementRepository renouncementRepository;

    @Autowired
    public RenouncementService(RenouncementRepository renouncementRepository) {
        this.renouncementRepository = renouncementRepository;
    }

    public Renouncement save(Renouncement renouncement) {
        return renouncementRepository.save(renouncement);
    }

    public List<Renouncement> getByReport(Report report) {
        return renouncementRepository.findByReport(report);
    }
}
