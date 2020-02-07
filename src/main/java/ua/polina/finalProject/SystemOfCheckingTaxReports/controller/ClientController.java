package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ClaimDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.ReportDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.ResourceNotFoundException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ClaimService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ClientService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.InspectorService;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.ReportService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {
    ClientService clientService;
    InspectorService inspectorService;
    ReportService reportService;
    ClaimService claimService;


    @Autowired
    public ClientController(ClientService clientService,
                            InspectorService inspectorService,
                            ReportService reportService,
                            ClaimService claimService) {
        this.clientService = clientService;
        this.inspectorService = inspectorService;
        this.reportService = reportService;
        this.claimService = claimService;
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "client/client-page";
    }

    @GetMapping("/create-new-report")
    public String getNewReportPage(Model model) {
        model.addAttribute("report", new ReportDTO());
        return "client/new-report";
    }

    @PostMapping("/create-new-report")
    public String addNewReport(@ModelAttribute("report") ReportDTO reqReport, BindingResult result, Model model, @CurrentUser User user) {
        Optional<Client> client = clientService.getByUser(user);
        Status status = Status.NOT_CHECKED;
        //TODO check optional
        Report report = Report.builder()
                .client(client.get())
                .date(LocalDateTime.now())
                .comment(reqReport.getComment())
                .status(status)
                .build();

        this.reportService.saveNewReport(report);
        return "redirect:/client/my-reports";
    }


    @GetMapping("/my-reports")
    public String getReportsPage(Model model, @CurrentUser User user) {
        //ToDO check optional
        List<Report> reports = reportService.getByClient(clientService.getByUser(user).get());
        model.addAttribute("reports", reports);
        return "client/get-reports-page";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model){
        Report report = reportService.getById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid id"+id));
        model.addAttribute("report", report);
        return "client/update-report";
    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable("id") Long id, Report report,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            report.setId(id);
            return "client/update-report";
        }
        reportService.update(report, Status.NOT_CHECKED);
        return "redirect:/client/my-reports";
    }

    @GetMapping("/create-claim")
    public String getAddClaimPage(Model model){
        model.addAttribute("claim", new ClaimDTO());
        return "client/new-claim";
    }

    @PostMapping("/create-claim")
    public String saveNewClaim(@ModelAttribute ClaimDTO reqClaim, Model model, BindingResult result, @CurrentUser User user) {
        Optional<Client> client = clientService.getByUser(user);
        System.out.println(user.getClient().getIndividual().getSurname());
        if (client.isEmpty()) throw new ResourceNotFoundException("claim", "client", client);

        Claim claim = Claim.builder()
                .client(client.get())
                .inspector(client.get().getInspector())
                .reason(reqClaim.getReason())
                .status(Status.NOT_CHECKED)
                .build();
        this.claimService.saveNewClaim(claim);
        return "redirect:/client/my-claims";
    }

    @GetMapping("/my-claims")
    public String getClaimsPage(Model model, @CurrentUser User user) {
        //TODO check optional
        List<Claim> claims = claimService.getClaimsByClient(clientService.getByUser(user).get());
        model.addAttribute("claims", claims);
        return "client/get-claims-page";
    }
}
