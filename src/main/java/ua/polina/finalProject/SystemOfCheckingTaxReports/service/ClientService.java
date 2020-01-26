package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Optional<Client> getById(Long id){
        return clientRepository.findById(id);
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }
}
