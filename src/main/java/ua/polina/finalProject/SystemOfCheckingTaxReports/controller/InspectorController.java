package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.RenouncementDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes("report")
@RequestMapping("/inspector")
public class InspectorController {
    private InspectorService inspectorService;
    private UserService userService;
    private ClientService clientService;
    private ReportService reportService;
    private RenouncementService renouncementService;

    @Autowired
    public InspectorController(InspectorService inspectorService,
                               UserService userService,
                               ClientService clientService,
                               ReportService reportService,
                               RenouncementService renouncementService) {
        this.inspectorService = inspectorService;
        this.userService = userService;
        this.clientService = clientService;
        this.reportService = reportService;
        this.renouncementService = renouncementService;
    }

    @GetMapping("/index")
    public String getIndex() {
        return "inspector/inspector-page";
    }

    @GetMapping("/individuals")
    public String getIndividualsPage(Model model, @CurrentUser User user) {
        List<Client> individuals = clientService.getByClientType(ClientType.INDIVIDUAL, user.getInspector());

        model.addAttribute("individuals", individuals);
        return "inspector/get-individuals-page";
    }

    @GetMapping("/legals")
    public String getLegalsPage(Model model, @CurrentUser User user) {
        List<Client> legals = clientService.getByClientType(ClientType.LEGAL_ENTITY, user.getInspector());

        model.addAttribute("legals", legals);
        return "inspector/get-legals-page";
    }

    @GetMapping("/reports")
    public String getReportsPage(Model model, @CurrentUser User user) {
        List<Report> reports = reportService.getByInspector(user.getInspector());

        model.addAttribute("reports", reports);
        return "inspector/get-reports-page";
    }

    @GetMapping("/accept-report/{id}")
    public String acceptReport(@PathVariable("id") Long id, Model model) {
        //TODO check optional
        Report report = reportService.getById(id).get();

        reportService.update(report, Status.ACCEPTED);
        return "redirect:inspector/reports";
    }

    @GetMapping("/reject-report/{id}")
    public String getRejectReportForm(@PathVariable("id") Long id, Model model, @CurrentUser User user) {
        //TODO check optional
        Report report = reportService.getById(id).get();

        model.addAttribute("report", report);
        model.addAttribute("renouncement", new RenouncementDTO());
        return "inspector/new-reject";
    }

    @PostMapping("/reject-report")
    public String rejectReport(@ModelAttribute("report") Report report,
                               @ModelAttribute("renouncement") RenouncementDTO renouncementDTO,
                               BindingResult bindingResult, Model model) {
        //TODO transactional method in service
        reportService.update(report, Status.REJECTED);
        Renouncement renouncement = Renouncement.builder()
                .report(report)
                .date(LocalDateTime.now())
                .reason(renouncementDTO.getReason())
                .build();

        renouncementService.save(renouncement);
        return "redirect:/inspector/reports";
    }

    @GetMapping("/info-report/{id}")
    public String getInfo(@PathVariable("id") Long id, Model model) {
        //TODO check optional
        Report report = reportService.getById(id).get();

        model.addAttribute("renouncements", renouncementService.getByReport(report));
        return "inspector/get-info";
    }
}
