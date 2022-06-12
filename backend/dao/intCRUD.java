package dao;

import java.util.List;

/**
 *
 * @author csimes
 */
public interface intCRUD<EntidadDTO> {
    public boolean create(EntidadDTO e);

    public boolean delete(Object clave);

    public boolean update(EntidadDTO e);

    public EntidadDTO read(EntidadDTO clave);

    public List<EntidadDTO> readAll();

}