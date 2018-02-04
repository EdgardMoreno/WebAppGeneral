
package com.general.interfac.dao;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import java.sql.Connection;
import java.util.List;

public interface DaoDocument {
    
    public List<Sic1docu> listByCodSerie(Connection cnConexion, String value) throws Exception;
    
    public List<Sic1docu> listByNumDocu(Connection cnConexion, String value) throws Exception;
    
    public String insert(Connection cnConexion, Sic1docu sic1docu) throws Exception;
    
    public String relateDocuDocu(Connection cnConexion, Sic3docudocu sic3docudocu) throws Exception;
    
    public String relateDocuEsta(Connection cnConexion, Sic3docuesta sic3docuesta) throws Exception;
    
    public Sic1docu get(Connection cnConexion, Integer id) throws Exception;
    
    public String update(Connection cnConexion, Sic1docu sic1docu);
    
    public String delete(Connection cnConexion, String id);
}
