
package com.general.interfac.dao;

import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3proddocu;
import java.sql.Connection;
import java.util.List;

public interface DaoProduct {
    
    public List<Sic1prod> listByCodProd(Connection cnConexion, String value) throws Exception;
    
    public List<Sic1prod> listByDesProd(Connection cnConexion, String value) throws Exception;
    
    public String insert(Connection cnConexion, Sic1prod obj) throws Exception;
    
    public String relateProdDocu(Connection cnConexion, List<Sic3proddocu> list) throws Exception;
    
    public Sic1prod get(Connection cnConexion, Integer id) throws Exception;
    
    public String update(Connection cnConexion, Sic1prod obj);
    
    public String delete(Connection cnConexion, String id);
}
