package com.web.gym.adapters.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteRequest {
    private int idCliente;
    private String dni;
    private String apellido;
    private String actividad;
    private int dias;
    private float costo;

}
