package com.example.app.service;

import com.example.app.entity.Client;
import com.example.app.repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService() {
        clientRepository = new ClientRepository();
    }

    public List<Client> getClientsInfo() throws SQLException {
        return clientRepository.getClientsInfo();
    }

    public void registrateClient(Client client) throws SQLException {
        clientRepository.registerClient(client);
    }
}
