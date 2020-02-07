package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Renouncement;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.RenouncementRepository;

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
