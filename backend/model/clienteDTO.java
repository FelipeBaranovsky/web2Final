package model;

/**
 *
 * @author Baranovsky-Yanes
 */

public class clienteDTO {
    private int cliente_id;
    private String apellido;
    private String dni;
    private String actividad;
    private int dias;
    private float costo;

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return "clienteDTO{" + "cliente_id=" + cliente_id + ", apellido=" + apellido + ", dni=" + dni + ", actividad=" + actividad + ", dias=" + dias + ", costo=" + costo +  "}";
    }
    
}
