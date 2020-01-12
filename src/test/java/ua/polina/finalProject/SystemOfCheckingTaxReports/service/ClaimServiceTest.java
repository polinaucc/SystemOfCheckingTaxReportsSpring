package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimsDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClaimRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ClaimServiceTest {
    @Mock
    ClaimRepository claimRepository;
    @InjectMocks
    ClaimService claimService;
    ClaimsDTO claims;
    ClaimsDTO claimsWithj11elements;
    Inspector inspector;
    Client clientWith1ID;
    Client clientWith2ID;

    @BeforeEach
    public void initMethod() {
        Claim claim1 = Claim.builder()
                .id(1L)
                .client(clientWith1ID)
                .inspector(inspector)
                .build();
        Claim claim2 = Claim.builder()
                .client(clientWith2ID)
                .id(2L)
                .inspector(inspector)
                .build();
        MockitoAnnotations.initMocks(this);
        claims = ClaimsDTO.builder()
                .claims(Arrays.asList(claim1, claim2))
                .build();

        claimsWithj11elements = ClaimsDTO.builder()
                .claims(Stream.concat(
                        Collections.nCopies(5, claim1).stream(),
                        Collections.nCopies(6, claim2).stream()
                ).collect(Collectors.toList()))
                .build();
    }

    @Test
    void saveNewClaim() {
        Claim currentClaim = claims.getClaims().get(0);
        claimService.saveNewClaim(currentClaim);
        verify(claimRepository, times(1)).save(currentClaim);
    }

    @Test
    void getAllClaims() {
        int page = 1;
        int size = 10;
        String sortParameter = "reason";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        when(claimRepository.findAll(pageReq)).thenReturn(new PageImpl<>(claims.getClaims()));

        ClaimsDTO actualClaimsDTO = claimService.getAllClaims(page, size, sortParameter, sortDir);

        Assert.assertEquals(claims, actualClaimsDTO);
    }

    @Test
    void getClaimsByClient() {
        Claim clientWith2IDClaim = claims.getClaims().get(1);
        List<Claim> expectedClaimsList = Arrays.asList(clientWith2IDClaim);
        when(claimRepository.findByClient(clientWith2ID)).thenReturn(expectedClaimsList);

        ClaimsDTO actualClaims = claimService.getClaimsByClient(clientWith2ID);

        Assert.assertEquals(1, actualClaims.getClaims().size());
        Assert.assertEquals(expectedClaimsList, actualClaims.getClaims());
    }

    @Test
    void getAllClaimsMoreThan10() {
        int page = 2;
        int size = 10;
        String sortParameter = "reason";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        List<Claim> expectedClaims = claimsWithj11elements.getClaims().subList(9, 10);
        when(claimRepository.findAll(pageReq)).thenReturn(new PageImpl<>(expectedClaims));

        ClaimsDTO actualClaimsDTO = claimService.getAllClaims(page, size, sortParameter, sortDir);

        Assert.assertEquals(expectedClaims, actualClaimsDTO.getClaims());
    }

    @Test
    void getClaimsByInspector() {
        when(claimRepository.findByInspector(inspector)).thenReturn(claims.getClaims());

        ClaimsDTO actualClaimsDTO = claimService.getClaimsByInspector(inspector);

        Assert.assertEquals(2, actualClaimsDTO.getClaims().size());
        Assert.assertEquals(actualClaimsDTO, claims);
    }
}
