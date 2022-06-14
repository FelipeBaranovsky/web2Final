package com.web.gym.adapters.api.response;

import com.web.gym.domain.model.Cliente;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteResponse {
    private int idCliente;
    private String dni;
    private String apellido;
    private String actividad;
    private int dias;
    private float costo;

    public static ClienteResponse constructor(Cliente cliente){
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .dni(cliente.getDni())
                .apellido(cliente.getApellido())
                .actividad(cliente.getActividad())
                .dias(cliente.getDias())
                .costo(cliente.getCosto())
                .build();
    }
}
