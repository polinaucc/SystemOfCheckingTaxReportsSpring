package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.InspectorDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.InspectorService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/inspectors")
public class InspectorController {
    @Autowired
    private InspectorService inspectorService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<Inspector> getAllInspectors(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return inspectorService.getAllInspectors(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity getInspectorById(@PathVariable(name="id") Long id) {
        return inspectorService.getById(id)
                .map(inspector -> ResponseEntity.ok(inspector))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void saveNewInspector(@Valid @RequestBody InspectorDTO reqInspector) {
        User user = userService.getByEmail(reqInspector.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user",
                        "email",
                        reqInspector.getUserEmail())
                );

        Set<RoleType> newRoles = user.getRoles();
        newRoles.add(RoleType.INSPECTOR);
        user.setRoles(newRoles);
        userService.saveNewUser(user);

        Inspector inspector = Inspector.builder()
                .surname(reqInspector.getSurname())
                .firstName(reqInspector.getFirstName())
                .secondName(reqInspector.getSecondName())
                .employmentDate(reqInspector.getEmploymentDate())
                .user(user)
                .build();

        this.inspectorService.saveNewInspector(inspector);
    }
}
