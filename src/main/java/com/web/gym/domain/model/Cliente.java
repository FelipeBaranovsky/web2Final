package com.web.gym.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cliente {

    private int idCliente;
    private String apellido;
    private String dni;
    private String actividad;
    private int dias;
    private float costo;


}
