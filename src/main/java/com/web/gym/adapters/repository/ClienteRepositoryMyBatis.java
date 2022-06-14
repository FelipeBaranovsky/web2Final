package com.web.gym.adapters.repository;

import com.web.gym.adapters.api.request.ClienteRequest;
import com.web.gym.domain.model.Cliente;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClienteRepositoryMyBatis {
    List<Cliente> getAll();
    void editCliente(ClienteRequest cliente);
    void deleteCliente(ClienteRequest cliente);
    void insertCliente(ClienteRequest cliente);

    List<Cliente> getAllApellido();
    List<Cliente> getAllDocumentos();
    List<Cliente> getAllActividades();


}
