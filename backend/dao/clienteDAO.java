package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.clienteDTO;
import util.Conexion;

/**
 *
 * @author Baranovsky-Yanes
 */
public class clienteDAO implements intCRUD<clienteDTO> {
    public static final Conexion con = Conexion.crearConexion();
    public static final String SQL_INSERT = "insert into cliente(idCliente,apellido,dni,actividad,dias,costo) values (?,?,?,?,?,?)";
    public static final String SQL_DELETE = "delete from cliente where idCliente=?";
    public static final String SQL_UPDATE = "update cliente set apellido=?,dni=?,actividad=?,dias=?,costo=? where idCliente=?";
    public static final String SQL_READ = "select * from cliente where idCliente=?";
    public static final String SQL_READALL = "select * from cliente";

    @Override

    public boolean create(clienteDTO e) {
        try {
            int control = 0;
            PreparedStatement ps = con.getCnn().prepareCall(SQL_INSERT);
            ps.setInt(1, e.getCliente_id());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getDni());
            ps.setString(4, e.getActividad());
            ps.setInt(5, e.getDias());
            ps.setFloat(6, e.getCosto());

            control = ps.executeUpdate();
            if (control > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(clienteDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public boolean delete(Object clave) {
        try {
            int control = 0;
            PreparedStatement ps = con.getCnn().prepareCall(SQL_DELETE);
            ps.setInt(1, (int) clave);

            control = ps.executeUpdate();
            if (control > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;

    }

    @Override
    public boolean update(clienteDTO e) {
        try {
            int control = 0;
            PreparedStatement ps = con.getCnn().prepareCall(SQL_UPDATE);

            ps.setString(1, e.getApellido());
            ps.setString(2, e.getDni());
            ps.setString(3, e.getActividad());
            ps.setInt(4, e.getDias());
            ps.setFloat(5, e.getCosto());
            ps.setInt(6, e.getCliente_id());
            control = ps.executeUpdate();
            if (control > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;

    }

    @Override
    public clienteDTO read(Object clave) {

        clienteDTO cliente = null;
        try {

            ResultSet rs = null;
            PreparedStatement ps = con.getCnn().prepareCall(SQL_READ);
            ps.setInt(1, (int) clave);

            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new clienteDTO();
                cliente.setCliente_id(rs.getInt("idCliente"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDni(rs.getString("dni"));
                cliente.setActividad(rs.getString("actividad"));
                cliente.setDias(rs.getInt("dias"));
                cliente.setCosto(rs.getFloat("costo"));
                return cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteDTO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return cliente;

    }

    @Override
    public List<clienteDTO> readAll() {
        clienteDTO cliente;
        List<clienteDTO> lista = new ArrayList<>();

        try {

            ResultSet rs = null;
            PreparedStatement ps = con.getCnn().prepareCall(SQL_READALL);

            rs = ps.executeQuery();

            while (rs.next()) {

                cliente = new clienteDTO();
                cliente.setCliente_id(rs.getInt("idCliente"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDni(rs.getString("dni"));
                cliente.setActividad(rs.getString("actividad"));
                cliente.setDias(rs.getInt("dias"));
                cliente.setCosto(rs.getFloat("costo"));
                lista.add(cliente);

            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteDTO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            con.cerrarConexion();
        }
        return lista;

    }

}
