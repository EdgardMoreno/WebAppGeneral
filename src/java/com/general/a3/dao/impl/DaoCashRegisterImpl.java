/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate1.Sic4cuaddiario;
import com.general.hibernate1.Sic4cuaddiarioId;
import org.hibernate.Session;

/**
 *
 * @author emoreno
 */
public class DaoCashRegisterImpl {
    
    
    public DaoCashRegisterImpl(){
        
    }
    
    
    /*METODOS*/
    
    public void insert(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.save(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public Sic4cuaddiario getById(Session session, Sic4cuaddiarioId id) throws Exception {
        
        Sic4cuaddiario obj = null;
        try {        
            obj = (Sic4cuaddiario)session.load(Sic4cuaddiario.class,id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }
    
    
}
