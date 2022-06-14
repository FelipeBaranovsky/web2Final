package com.web.gym.domain.repository;

import com.web.gym.adapters.api.request.ClienteRequest;
import com.web.gym.domain.model.Cliente;

import java.util.List;

public interface ClienteRepository{
    List<Cliente> getAll();
    void editCliente(ClienteRequest cliente);
    void deleteCliente(ClienteRequest cliente);
    void insertCliente(ClienteRequest cliente);

    List<Cliente> getAllApellido();

    List<Cliente> getAllDocumentos();
    List<Cliente> getAllActividades();

}
