package com.usa.retos.service;

import com.usa.retos.model.Client;
import com.usa.retos.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public Optional<Client> getClient(int id) {
        return clientRepository.getClient(id);
    }

    public Client save(Client client) {
        if (client.getIdClient() == null) {
            return clientRepository.save(client);
        } else {
            Optional<Client> tmpClient = clientRepository.getClient(client.getIdClient());
            if (tmpClient.isEmpty()) {
                return clientRepository.save(client);
            } else {
                return client;
            }
        }
    }

    public Client update(Client client) {
        if (client.getIdClient() != null) {
            Optional<Client> tmpClient = clientRepository.getClient(client.getIdClient());
            if (!tmpClient.isEmpty()) {
                return clientRepository.save(client);
            }
        }
        return client;
    }

    public boolean deleteClient(int id) {
        Boolean result = getClient(id).map(client -> {
            clientRepository.delete(client);
            return true;
        }).orElse(false);
        return result;
    }
}
