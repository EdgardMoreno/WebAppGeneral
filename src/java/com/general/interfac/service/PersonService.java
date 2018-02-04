/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.interfac.service;

import com.general.hibernate.entity.Sic1pers;
import java.util.List;

/**
 *
 * @author emoreno
 */
public interface PersonService{
    
//    public List<Sic1pers> list(Connection cnConexion );
//    
//    public String insert(Connection cnConexion, Sic1pers persona);
//    
//    public Sic1pers get(Connection cnConexion, Integer id);
//    
//    public String update(Connection cnConexion, Sic1pers persona);
//    
//    public String delete(Connection cnConexion, String id);
    
    
    public List<Sic1pers> list();
    
    public List<Sic1pers> listByName(String name) throws Exception;
    
    public String insert(Sic1pers sic1pers) throws Exception;
    
    public Sic1pers get(Integer id) throws Exception;;
    
    public String update(Sic1pers sic1pers);
    
    public String delete(String id);
    
}
