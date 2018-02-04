
package com.general.interfac.dao;

import com.general.hibernate.entity.Sic1pers;
import java.sql.Connection;
import java.util.List;

public interface DaoPerson {
    
    public List<Sic1pers> list(Connection cnConexion);
    
    public List<Sic1pers> listByName(Connection cnConexion, String nombre) throws Exception;;
    
    public String insert(Connection cnConexion, Sic1pers persona) throws Exception;
    
    public Sic1pers get(Connection cnConexion, Integer id) throws Exception;
    
    public String update(Connection cnConexion, Sic1pers persona);
    
    public String delete(Connection cnConexion, String id);
}
