package com.web.gym.domain.service;

import com.web.gym.adapters.api.request.ClienteRequest;
import com.web.gym.domain.model.Cliente;
import com.web.gym.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> getAll(){
        return clienteRepository.getAll();
    }

    public List<Cliente> getAllApellido(){
        return clienteRepository.getAllApellido();
    }

    public List<Cliente> getAllDocumentos(){
        return clienteRepository.getAllDocumentos();
    }

    public List<Cliente> getAllActividades(){
        return clienteRepository.getAllActividades();
    }

   public void editCliente(ClienteRequest cliente){ clienteRepository.editCliente(cliente);}

    public void deleteCliente(ClienteRequest cliente){
        clienteRepository.deleteCliente(cliente);
    }

    public void insertCliente(ClienteRequest cliente){
        clienteRepository.insertCliente(cliente);
    }


}
//Servicios de clientes -> ClienteRepository