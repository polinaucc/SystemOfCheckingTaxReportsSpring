package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.ErrorMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.SuccessMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.RoleType;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    RegistrationService registrationService;
    UserService userService;
    LegalEntityService legalEntityService;
    IndividualService individualService;
    InspectorService inspectorService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          UserService userService,
                          LegalEntityService legalEntityService,
                          IndividualService individualService,
                          InspectorService inspectorService,
                          PasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.legalEntityService = legalEntityService;
        this.individualService = individualService;
        this.inspectorService = inspectorService;
        this.passwordEncoder = passwordEncoder;

    }

    @RequestMapping("/sign-up-individual")
    public String getRegisterIndividualPage(Model model) {
        List<Inspector> inspectors = inspectorService.getAllInspectors();
        model.addAttribute("inspectors", inspectors);
        model.addAttribute("sign", new SignUpIndividualRequest());
        model.addAttribute("error", null);
        model.addAttribute("error2", null);
        return "RegisterIndividual";
    }

    @PostMapping("/sign-up-individual")
    public String addIndividual(@Valid @ModelAttribute("sign") SignUpIndividualRequest signUpIndividualRequest,
                                BindingResult bindingResult, Model model) {

        String error = registrationService.verifyIfExistsIndividual(signUpIndividualRequest);
        if (!error.equals(ErrorMessages.EMPTY_STRING)) {
            model.addAttribute("error", error);
            return "RegisterIndividual";
        }
        String resultMessage = registrationService.saveNewIndividual(signUpIndividualRequest) ?
                SuccessMessages.SUCCESS_MESSAGE :
                ErrorMessages.CANNOT_SAVE;
        if (resultMessage.equals(ErrorMessages.CANNOT_SAVE)) {
            model.addAttribute("error2", resultMessage);
            return "RegisterIndividual";
        } else {
            return "logIn";
        }
    }

    @RequestMapping("/sign-up-legal-entity")
    public String getRegisterLegalEntityPage(Model model) {
        model.addAttribute("sign", new SignUpLegalEntityRequest());
        model.addAttribute("error", null);
        model.addAttribute("error2", null);
        return "RegisterLegalEntity";
    }

    @PostMapping("/sign-up-legal-entity")
    public String registerLegalEntity(@Valid @ModelAttribute("sign") SignUpLegalEntityRequest signUpLegalEntityRequest,
                                      BindingResult bindingResult, Model model) {
        String error = registrationService.verifyIfExistsLegalEntity(signUpLegalEntityRequest);
        if (!error.equals(ErrorMessages.EMPTY_STRING)) {
            model.addAttribute("error", error);
            return "RegisterLegalEntity";
        }
        String resultMessage = registrationService.saveNewLegalEntity(signUpLegalEntityRequest) ?
                SuccessMessages.SUCCESS_MESSAGE : ErrorMessages.CANNOT_SAVE;
        if (resultMessage.equals(ErrorMessages.CANNOT_SAVE)) {
            model.addAttribute("error2", resultMessage);
            return "RegisterLegalEntity";
        }
        return "logIn";
    }

    @RequestMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return "logIn";
    }

    @RequestMapping("/default-success")
    public String getSuccessPage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(RoleType.ADMIN)) {
            System.out.println("Success-----------------------------------------------------");
            return "redirect:/admin/index";
        } else if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(RoleType.CLIENT)) {
            return "redirect:/client/index";
        } else if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(RoleType.INSPECTOR)) {
            return "redirect:/inspector/index";
        }
        return "redirect:/client/index";
    }
}