package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.LegalEntity;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.LegalEntityService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/legal")
public class LegalEntityController {
    @Autowired
    LegalEntityService legalEntityService;

    @GetMapping("")
    public List<LegalEntity> getAllClaims(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return legalEntityService.getAllLegalEntities(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity getLegalEntityById(@PathVariable(name="id") Long id) {
        return legalEntityService.getById(id)
                .map(legalEntity -> ResponseEntity.ok(legalEntity))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        legalEntityService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update}")
    public void update(@Valid @RequestBody LegalEntity legalEntity) {
        this.legalEntityService.saveNewLegalEntity(legalEntity);
    }
}
