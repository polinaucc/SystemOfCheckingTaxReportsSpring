package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.ErrorMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.SignUpIndividualRequest;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.SignUpLegalEntityRequest;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClientRepository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.IndividualRepository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.LegalEntityRepository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.UserRepository;

import javax.transaction.Transactional;

@Transactional
@Service
public class RegistrationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    private LegalEntityRepository legalEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String verifyIfExistsIndividual(SignUpIndividualRequest signUpIndividualRequest) {
        if (userRepository.existsUserByEmail(signUpIndividualRequest.getEmail()))
            return ErrorMessages.EMAIL_EXIST;
        if (individualRepository.existsIndividualByPassport(signUpIndividualRequest.getPassport()))
            return ErrorMessages.PASSPORT_IN_USE;
        if (individualRepository.existsIndividualByIdentCode(signUpIndividualRequest.getIdentCode()))
            return ErrorMessages.IDENT_CODE_IN_USE;

        return ErrorMessages.EMPTY_STRING;
    }

    public String verifyIfExistsLegalEntity(SignUpLegalEntityRequest signUpLegalEntityRequest) {
        if (userRepository.existsUserByEmail(signUpLegalEntityRequest.getEmail()))
            return ErrorMessages.EMAIL_EXIST;
        if (legalEntityRepository.existsLegalEntityByEdrpou(signUpLegalEntityRequest.getEdrpou()))
            return ErrorMessages.EDRPOU_IN_USE;
        if (legalEntityRepository.existsLegalEntityByName(signUpLegalEntityRequest.getName()))
            return ErrorMessages.NAME_IN_USE;
        return ErrorMessages.EMPTY_STRING;
    }

    public boolean saveNewLegalEntity(SignUpLegalEntityRequest legalEntityRequest) {
        User user = new User();
        user.getRoles().add(RoleType.CLIENT);
        user.setEmail(legalEntityRequest.getEmail());
        user.setPassword(passwordEncoder.encode(legalEntityRequest.getPassword()));

        if (userRepository.save(user) == null) return false;

        Client client = new Client();
        client.setUser(user);
        client.setClientType(ClientType.LEGAL_ENTITY);

        if (clientRepository.save(client) == null) return false;

        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setClient(client);
        legalEntity.setName(legalEntityRequest.getName());
        legalEntity.setEdrpou(legalEntityRequest.getEdrpou());
        legalEntity.setMfo(legalEntityRequest.getMfo());
        legalEntity.setAddress(legalEntityRequest.getAddress());

        return (legalEntityRepository.save(legalEntity) != null);
    }

    public boolean saveNewIndividual(SignUpIndividualRequest individualRequest) {
        User user = new User();
        user.getRoles().add(RoleType.CLIENT);
        user.setEmail(individualRequest.getEmail());
        user.setPassword(passwordEncoder.encode(individualRequest.getPassword()));

        if (userRepository.save(user) == null) return false;

        Client client = new Client();
        client.setUser(user);
        client.setClientType(ClientType.INDIVIDUAL);

        if (clientRepository.save(client) == null) return false;

        Individual individual = new Individual();
        individual.setClient(client);
        individual.setSurname(individualRequest.getSurname());
        individual.setFirstName(individualRequest.getFirstName());
        individual.setSecondName(individualRequest.getSecondName());
        individual.setPassport(individualRequest.getPassport());
        individual.setIdentCode(individualRequest.getIdentCode());
        individual.setAddress(individualRequest.getAddress());

        return (individualRepository.save(individual) != null);
    }
}
