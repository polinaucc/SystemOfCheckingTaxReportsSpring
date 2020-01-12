package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClaimRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Slf4j
@Service
public class ClaimService {

    ClaimRepository claimRepository;

    @Autowired
    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public void saveNewClaim(Claim claim) {
        try {
            claimRepository.save(claim);
        } catch (Exception exc) {
            log.info("Cannot save");
        }
    }

    public ClaimsDTO getAllClaims(int page, int size, String sortParameter, String sortDir) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        Page<Claim> allClaims = claimRepository.findAll(pageReq);
        List<Claim> claims = allClaims.getContent();
        if (claims.size() < 1) {

        }
        return new ClaimsDTO(claims);
    }

    public ClaimsDTO getClaimsByClient(Client client) {
        return new ClaimsDTO(claimRepository.findByClient(client));
    }

    public ClaimsDTO getClaimsByInspector(Inspector inspector) {
        return new ClaimsDTO(claimRepository.findByInspector(inspector));
    }
}
