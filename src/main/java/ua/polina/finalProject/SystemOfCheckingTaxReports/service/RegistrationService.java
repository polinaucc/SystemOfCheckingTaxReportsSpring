package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.ErrorMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.InspectorDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.SignUpIndividualDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.SignUpLegalEntityDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.*;

import javax.transaction.Transactional;

@Transactional
@Service
public class RegistrationService {
    InspectorRepository inspectorRepository;
    UserRepository userRepository;
    ClientRepository clientRepository;
    IndividualRepository individualRepository;
    LegalEntityRepository legalEntityRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(InspectorRepository inspectorRepository,
                               UserRepository userRepository,
                               ClientRepository clientRepository,
                               IndividualRepository individualRepository,
                               LegalEntityRepository legalEntityRepository,
                               PasswordEncoder passwordEncoder) {
        this.inspectorRepository = inspectorRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.individualRepository = individualRepository;
        this.legalEntityRepository = legalEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String verifyIfExistsIndividual(SignUpIndividualDTO signUpIndividualDTO) {
        if (userRepository.existsUserByEmail(signUpIndividualDTO.getEmail()))
            return ErrorMessages.EMAIL_EXIST;
        if (individualRepository.existsIndividualByPassport(signUpIndividualDTO.getPassport()))
            return ErrorMessages.PASSPORT_IN_USE;
        if (individualRepository.existsIndividualByIdentCode(signUpIndividualDTO.getIdentCode()))
            return ErrorMessages.IDENT_CODE_IN_USE;

        return ErrorMessages.EMPTY_STRING;
    }

    public String verifyIfExistsInspector(InspectorDTO reqInspector) {
        if (userRepository.existsUserByEmail(reqInspector.getUserEmail()))
            return ErrorMessages.EMAIL_EXIST;
        return ErrorMessages.EMPTY_STRING;
    }

    public String verifyIfExistsLegalEntity(SignUpLegalEntityDTO signUpLegalEntityDTO) {
        if (userRepository.existsUserByEmail(signUpLegalEntityDTO.getEmail()))
            return ErrorMessages.EMAIL_EXIST;
        if (legalEntityRepository.existsLegalEntityByEdrpou(signUpLegalEntityDTO.getEdrpou()))
            return ErrorMessages.EDRPOU_IN_USE;
        if (legalEntityRepository.existsLegalEntityByName(signUpLegalEntityDTO.getName()))
            return ErrorMessages.NAME_IN_USE;
        return ErrorMessages.EMPTY_STRING;
    }

    public boolean saveNewInspector(InspectorDTO reqInspector) {
        User user = new User();

        user.getAuthorities().add(RoleType.INSPECTOR);
        user.setEmail(reqInspector.getUserEmail());
        user.setPassword(passwordEncoder.encode(reqInspector.getPassword()));

        //TODO: condition is always false
        if (userRepository.save(user) == null) return false;

        Inspector inspector = Inspector.builder()
                .surname(reqInspector.getSurname())
                .firstName(reqInspector.getFirstName())
                .secondName(reqInspector.getSecondName())
                .employmentDate(reqInspector.getEmploymentDate())
                .user(user)
                .build();

        //TODO: condition is always true
        return (inspectorRepository.save(inspector) != null);
    }

    public boolean saveNewLegalEntity(SignUpLegalEntityDTO legalEntityRequest) {
        User user = new User();

        user.getAuthorities().add(RoleType.CLIENT);
        user.setEmail(legalEntityRequest.getEmail());
        user.setPassword(passwordEncoder.encode(legalEntityRequest.getPassword()));

        //TODO: condition is always false
        if (userRepository.save(user) == null) return false;

        Client client = new Client();

        client.setUser(user);
        client.setClientType(ClientType.LEGAL_ENTITY);
        //TODO: check optional
        client.setInspector(inspectorRepository.findById(legalEntityRequest.getInspectorId()).get());

        //TODO: condition is always false
        if (clientRepository.save(client) == null) return false;

        LegalEntity legalEntity = new LegalEntity();

        legalEntity.setClient(client);
        legalEntity.setName(legalEntityRequest.getName());
        legalEntity.setEdrpou(legalEntityRequest.getEdrpou());
        legalEntity.setMfo(legalEntityRequest.getMfo());
        legalEntity.setAddress(legalEntityRequest.getAddress());

        //TODO: condition is always true
        return (legalEntityRepository.save(legalEntity) != null);
    }

    public boolean saveNewIndividual(SignUpIndividualDTO individualRequest) {
        User user = new User();

        user.getAuthorities().add(RoleType.CLIENT);
        user.setEmail(individualRequest.getEmail());
        user.setPassword(passwordEncoder.encode(individualRequest.getPassword()));

        //TODO: condition is always false
        if (userRepository.save(user) == null) return false;

        Client client = new Client();

        client.setUser(user);
        client.setClientType(ClientType.INDIVIDUAL);
        //TODO: check optional
        client.setInspector(inspectorRepository.findById(individualRequest.getInspectorId()).get());

        //TODO: condition is always false
        if (clientRepository.save(client) == null) return false;

        Individual individual = new Individual();

        individual.setClient(client);
        individual.setSurname(individualRequest.getSurname());
        individual.setFirstName(individualRequest.getFirstName());
        individual.setSecondName(individualRequest.getSecondName());
        individual.setPassport(individualRequest.getPassport());
        individual.setIdentCode(individualRequest.getIdentCode());
        individual.setAddress(individualRequest.getAddress());

        //TODO: condition is always true
        return (individualRepository.save(individual) != null);
    }
}
