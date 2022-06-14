package com.web.gym.adapters.api.controllers;

import com.web.gym.adapters.api.request.ClienteRequest;
import com.web.gym.adapters.api.response.ClienteResponse;
import com.web.gym.domain.model.Cliente;
import com.web.gym.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @CrossOrigin("*")
    @GetMapping("/listarTodos")
    public List<ClienteResponse> getAll(){

        List<Cliente> clientes = clienteService.getAll();
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        for (Cliente cliente:
             clientes) {
            clienteResponses.add(ClienteResponse.constructor(cliente));
        }
        return  clienteResponses;
    }

    @CrossOrigin("*")
    @GetMapping("/listarApellidos")
    public List<ClienteResponse> getAllApellido(){

        List<Cliente> clientes = clienteService.getAllApellido();
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        for (Cliente cliente:
                clientes) {
            clienteResponses.add(ClienteResponse.constructor(cliente));
        }
        return  clienteResponses;
    }


    @CrossOrigin("*")
    @GetMapping("/listarDocumentos")
    public List<ClienteResponse> getAllDocumentos(){

        List<Cliente> clientes = clienteService.getAllDocumentos();
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        for (Cliente cliente:
                clientes) {
            clienteResponses.add(ClienteResponse.constructor(cliente));
        }
        return  clienteResponses;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/insertar")
    public void insertCliente(
            @RequestBody ClienteRequest clienteRequest
    ){
        clienteService.insertCliente(clienteRequest);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/edit")
    public void editCliente(
            @RequestBody ClienteRequest clienteRequest
    ){
        clienteService.editCliente(clienteRequest);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete")
    public void deleteCliente(
            @RequestBody ClienteRequest clienteRequest
    ){
        clienteService.deleteCliente(clienteRequest);
    }


    @CrossOrigin("*")
    @GetMapping("/listarActividades")
    public List<ClienteResponse> getAllActividades(){

        List<Cliente> clientes = clienteService.getAllActividades();
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        for (Cliente cliente:
                clientes) {
            clienteResponses.add(ClienteResponse.constructor(cliente));
        }
        return  clienteResponses;
    }

}
