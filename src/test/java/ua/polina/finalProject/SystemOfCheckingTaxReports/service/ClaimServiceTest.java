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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClaimRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ClaimServiceTest {
    @Mock
    ClaimRepository claimRepository;
    @InjectMocks
    ClaimService claimService;
    List<Claim> claims;
    List<Claim> claimsWithj11elements;
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
        claims = Arrays.asList(claim1, claim2);

        claimsWithj11elements = Stream.concat(
                        Collections.nCopies(5, claim1).stream(),
                        Collections.nCopies(6, claim2).stream()
                ).collect(Collectors.toList());
    }

    @Test
    void saveNewClaim() {
        Claim currentClaim = claims.get(0);
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
        when(claimRepository.findAll(pageReq)).thenReturn(new PageImpl<>(claims));

        List<Claim> actualList = claimService.getAllClaims(pageReq);

        Assert.assertEquals(claims, actualList);
    }

    @Test
    void getClaimsByClient() {
        Claim clientWith2IDClaim = claims.get(1);
        List<Claim> expectedClaimsList = Arrays.asList(clientWith2IDClaim);
        when(claimRepository.findByClient(clientWith2ID)).thenReturn(expectedClaimsList);

        List<Claim> actualClaims = claimService.getClaimsByClient(clientWith2ID);

        Assert.assertEquals(1, actualClaims.size());
        Assert.assertEquals(expectedClaimsList, actualClaims);
    }

    @Test
    public void getClaimById(){
        Claim expectedClaim = claims.get(0);
        Long claimID = expectedClaim.getId();
        Optional<Claim> expectedOptionalClaim = Optional.of(expectedClaim);
        when(claimRepository.findById(claimID)).thenReturn(expectedOptionalClaim);

        Optional<Claim> actualClaim = claimService.getClaimById(claimID);

        Assert.assertEquals(expectedOptionalClaim, actualClaim);
    }

    @Test
    void getAllClaimsMoreThan10() {
        int page = 2;
        int size = 10;
        String sortParameter = "reason";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        List<Claim> expectedClaims = claimsWithj11elements.subList(9, 10);
        when(claimRepository.findAll(pageReq)).thenReturn(new PageImpl<>(expectedClaims));

        List<Claim> actualList = claimService.getAllClaims(pageReq);

        Assert.assertEquals(expectedClaims, actualList);
    }

    @Test
    void getClaimsByInspector() {
        when(claimRepository.findByInspector(inspector)).thenReturn(claims);

        List<Claim> actualList = claimService.getClaimsByInspector(inspector);

        Assert.assertEquals(2, actualList.size());
        Assert.assertEquals(actualList, claims);
    }
}
