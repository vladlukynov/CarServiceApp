package com.example.app.service;

import com.example.app.entity.Client;
import com.example.app.exception.NoClientByLoginException;
import com.example.app.repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository = new ClientRepository();

    public List<Client> getClientsInfo() throws SQLException {
        return clientRepository.getClientsInfo();
    }

    public Client getClientInfo(String userLogin) throws SQLException, NoClientByLoginException {
        List<Client> clients = getClientsInfo();
        Optional<Client> optionalClient = clients.stream().filter(client -> client.getUserLogin().equals(userLogin)).findFirst();
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        throw new NoClientByLoginException("No client by login " + userLogin);
    }

    public void registerClient(Client client) throws SQLException {
        clientRepository.registerClient(client);
    }
}
