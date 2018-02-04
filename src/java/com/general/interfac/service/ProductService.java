/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.interfac.service;

import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3proddocu;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author emoreno
 */
public interface ProductService{
    
    public List<Sic1prod> listByCodProd(String value) throws Exception;
    
    public List<Sic1prod> listByDesProd(String value) throws Exception;
    
    public String insert(Sic1prod sic1prod) throws Exception;
    
    public Sic1prod get(Integer id) throws Exception;
    
    public String update(Sic1prod sic1prod) throws Exception;
    
    public String delete(String id) throws Exception;
    
    public String relateProdDocu(Connection cnConexion, List<Sic3proddocu> list) throws Exception;
    
}
