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

    public void getClientsInfo() {
        try {
            List<Client> clients;
            clients = clientRepository.getClientsInfo();

            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
