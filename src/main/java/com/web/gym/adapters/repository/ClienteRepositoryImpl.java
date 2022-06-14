package com.web.gym.adapters.repository;

import com.web.gym.adapters.api.request.ClienteRequest;
import com.web.gym.domain.model.Cliente;
import com.web.gym.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryMyBatis clienteRepositoryMyBatis;
    @Override
    public List<Cliente> getAll() {
        return clienteRepositoryMyBatis.getAll();
    }

    @Override
    public void editCliente(ClienteRequest cliente) { clienteRepositoryMyBatis.editCliente(cliente);}

    @Override
    public void deleteCliente(ClienteRequest cliente) {
        clienteRepositoryMyBatis.deleteCliente(cliente);
    }

    @Override
    public void insertCliente(ClienteRequest cliente) {
        clienteRepositoryMyBatis.insertCliente(cliente);
    }

    @Override
    public List<Cliente> getAllApellido() {
        return clienteRepositoryMyBatis.getAllApellido();
    }



    @Override
    public List<Cliente> getAllDocumentos() {
        return clienteRepositoryMyBatis.getAllDocumentos();
    }
    @Override
    public List<Cliente> getAllActividades() {
        return clienteRepositoryMyBatis.getAllActividades();
    }
}
