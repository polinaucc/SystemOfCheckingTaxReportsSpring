package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
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

    public Optional<Claim> getClaimById(Long id){
        return claimRepository.findById(id);
    }

    public List<Claim> getAllClaims(Pageable pageable) {
        Page<Claim> allClaims = claimRepository.findAll(pageable);
        List<Claim> claims = allClaims.getContent();
        return claims;
    }

    public List<Claim> getClaimsByClient(Client client) {
        return claimRepository.findByClient(client);
    }

    public List<Claim> getClaimsByInspector(Inspector inspector) {
        return claimRepository.findByInspector(inspector);
    }
}
