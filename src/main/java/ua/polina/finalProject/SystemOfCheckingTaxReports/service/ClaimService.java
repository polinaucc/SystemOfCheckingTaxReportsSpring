package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClaimRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class ClaimService {

    ClaimRepository claimRepository;

    @Autowired
    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public Claim saveNewClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public Page<Claim> getAllClaims(Pageable pageable) {
        return claimRepository.findAll(pageable);
    }

    public List<Claim> getClaimsByClient(Client client) {
        return claimRepository.findByClient(client);
    }

    public List<Claim> getClaimsByInspector(Inspector inspector) {
        return claimRepository.findByInspector(inspector);
    }

    @Transactional
    public Claim update(Claim claim, Status status) {
        Long id = claim.getId();

        return claimRepository.findById(id)
                .map(claimFromDB -> {
                    claimFromDB.setStatus(status);
                    return claimRepository.save(claimFromDB);
                }).orElseGet(() -> claimRepository.save(claim));
    }
}
