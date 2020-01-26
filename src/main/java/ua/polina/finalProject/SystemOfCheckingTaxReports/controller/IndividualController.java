package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.IndividualService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individuals")
public class IndividualController {
    @Autowired
    IndividualService individualService;

    @GetMapping("")
    public List<Individual> getAllIndividuals(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return individualService.getAllIndividuals(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Individual> getIndividualById(@PathVariable(name = "id") Long id) {
        return individualService.getById(id)
                .map(individual -> ResponseEntity.ok(individual))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        individualService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update}")
    public void update(@Valid @RequestBody Individual individual) {
        individualService.saveNewIndividual(individual);
    }
}
