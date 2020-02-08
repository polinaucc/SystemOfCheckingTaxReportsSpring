package ua.polina.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.system.constants.ErrorMessages;
import ua.polina.system.constants.SuccessMessages;
import ua.polina.system.dto.ChangeInspectorDTO;
import ua.polina.system.dto.InspectorDTO;
import ua.polina.system.entity.*;
import ua.polina.system.service.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("client")
@RequestMapping("/admin")
public class AdminController {
    RegistrationService registrationService;
    IndividualService individualService;
    InspectorService inspectorService;
    ClaimService claimService;
    ClientService clientService;

    @Autowired
    public AdminController(RegistrationService registrationService,
                           IndividualService individualService,
                           InspectorService inspectorService,
                           ClaimService claimService,
                           ClientService clientService) {
        this.registrationService = registrationService;
        this.individualService = individualService;
        this.inspectorService = inspectorService;
        this.claimService = claimService;
        this.clientService = clientService;
    }

    @GetMapping("/save-inspector")
    public String getRegisterInspectorPage(Model model) {
        model.addAttribute("signInspector", new InspectorDTO());
        model.addAttribute("standardDate", LocalDate.now());
        return "admin/RegisterInspector";
    }

    @PostMapping("/save-inspector")
    public String saveInspector(@Valid @ModelAttribute("signInspector") InspectorDTO reqInspector,
                                BindingResult bindingResult, Model model) {
        String error = registrationService.verifyIfExistsInspector(reqInspector);

        if(bindingResult.hasErrors()){
            return "admin/RegisterInspector";
        }

        if (!error.equals(ErrorMessages.EMPTY_STRING)) {
            model.addAttribute("error", error);
            return "admin/RegisterInspector";
        }

        String resultMessage = registrationService.saveNewInspector(reqInspector) ?
                SuccessMessages.SUCCESS_MESSAGE :
                ErrorMessages.CANNOT_SAVE;

        if (resultMessage.equals(ErrorMessages.CANNOT_SAVE)) {
            model.addAttribute("error2", resultMessage);
            return "admin/RegisterInspector";
        } else {
            return "redirect:/admin/inspectors";
        }
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "admin/AdminPage";
    }

    @GetMapping("/individuals")
    public String getIndividualsPage(Model model,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Individual> individualPage = individualService.getAllIndividuals(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("individualPage", individualPage);

        int totalPages = individualPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/get-individuals-page";
    }


    @GetMapping("/delete-individual/{id}")
    public String deleteIndividual(@PathVariable("id") Long id, Model model) {
        individualService.deleteById(id);
        return "redirect:/admin/individuals";
    }

    @GetMapping("/edit-individual/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model) {
        Individual individual = individualService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id" + id));

        model.addAttribute("individual", individual);
        return "admin/update-individual";
    }

    @PostMapping("/update-individual/{id}")
    public String updateIndividual(@PathVariable("id") Long id, Individual individual,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            individual.setId(id);
            return "admin/update-individual";
        }
        individualService.update(individual);
        return "redirect:/admin/individuals";
    }

    @GetMapping("/inspectors")
    public String getInspectorsPage(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Inspector> inspectorPage = inspectorService.getAllInspectors(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("inspectorPage", inspectorPage);

        int totalPages = inspectorPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/get-inspectors-page";
    }

    @GetMapping("/claims")
    public String getClaimsPage(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Claim> claimPage = claimService.getAllClaims(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("claimPage", claimPage);

        int totalPages = claimPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/admin/get-claims-page";
    }

    @GetMapping("/reject-claim/{id}")
    public String rejectClaim(@PathVariable("id") Long id, Model model) {
        //TODO check optionals
        Claim claim = claimService.getClaimById(id).get();

        claimService.update(claim, Status.REJECTED);
        return "redirect:/admin/claims";
    }

    @GetMapping("/accept-claim/{id}")
    public String getAcceptClaimForm(@PathVariable("id") Long id, Model model) {
        //TODO check optionals
        Claim claim = claimService.getClaimById(id).get();

        claimService.update(claim, Status.ACCEPTED);
        Inspector insp = claim.getClient().getInspector();
        List<Inspector> inspectorList = inspectorService.getAllInspectors();

        inspectorList.remove(insp);
        model.addAttribute("client", claim.getClient());
        model.addAttribute("inspectors", inspectorList);
        model.addAttribute("changeInsp", new ChangeInspectorDTO());
        return "admin/process-claim-page";
    }


    @PostMapping("/accept-claim")
    public String acceptClaim(@ModelAttribute("changeInsp") ChangeInspectorDTO changeInspectorDTO,
                              @ModelAttribute("client") Client client, BindingResult result, Model model) {
        //TODO check optionals
        Inspector inspector = inspectorService.getById(changeInspectorDTO.getInspectorId()).get();

        clientService.update(client, inspector);
        return "redirect:/admin/claims";
    }

}
