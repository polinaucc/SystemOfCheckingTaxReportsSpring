package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ClaimService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ClientService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.InspectorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    @Autowired
    ClaimService claimService;

    @Autowired
    ClientService clientService;

    @Autowired
    InspectorService inspectorService;

    @GetMapping("")
    public List<Claim> getAllClaims(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return claimService.getAllClaims(pageable);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void saveNewClaim(@RequestBody ClaimDTO reqClaim) throws Exception {
        Optional<Client> client = clientService.getById(reqClaim.getClientId());
        Optional<Inspector> inspector = inspectorService.getById(reqClaim.getInspectorId());

        if (client.isEmpty()) throw new ResourceNotFoundException("claim", "client", client);
        if (inspector.isEmpty()) throw new ResourceNotFoundException("claim", "inspector", inspector);
        Status status = Status.valueOf(reqClaim.getStatus().toUpperCase());
        Claim claim = Claim.builder()
                .client(client.get())
                .inspector(inspector.get())
                .reason(reqClaim.getReason())
                .status(status)
                .build();
        this.claimService.saveNewClaim(claim);
    }

    @GetMapping("/{id}")
    public ResponseEntity getClaimById(@PathVariable(name="id") Long id) {
        return claimService.getClaimById(id)
                .map(claim -> ResponseEntity.ok(claim))
                .orElse(ResponseEntity.notFound().build());
    }
}

