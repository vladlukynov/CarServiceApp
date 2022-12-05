package com.example.app.service;

import com.example.app.entity.Service;
import com.example.app.exception.NoServiceByIdException;
import com.example.app.repository.ServiceRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceService {
    private final ServiceRepository serviceRepository = new ServiceRepository();
    public List<Service> getServices() throws SQLException {
        return serviceRepository.getServices();
    }

    public Service getService(int serviceId) throws SQLException, NoServiceByIdException {
        List<Service> services = serviceRepository.getServices();

        Optional<Service> serviceOptional = services.stream().filter(service -> service.getServiceId() == serviceId).findFirst();
        if (serviceOptional.isPresent()) {
            return serviceOptional.get();
        }

        throw new NoServiceByIdException("No service by id " + serviceId);
    }

    public void activateService(int serviceId) throws SQLException {
        serviceRepository.activateService(serviceId);
    }

    public void deactivateService(int serviceId) throws SQLException {
        serviceRepository.deactivateService(serviceId);
    }

    public void addService(Service service) throws SQLException {
        serviceRepository.addService(service);
    }

    public void updateService(int serviceId, Service newService) throws SQLException {
        serviceRepository.addService(newService);
        serviceRepository.deactivateService(serviceId);
    }
}
