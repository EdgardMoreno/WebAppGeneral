/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate1.ViSiccuaddiario;
import com.general.util.beans.UtilClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
    
     public void update(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.update(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public Sic4cuaddiario getById(Session session, Sic4cuaddiarioId id) throws Exception {
        
        Sic4cuaddiario obj = null;
        try {        
            obj = (Sic4cuaddiario)session.get(Sic4cuaddiario.class,id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }    
    
    
    public List<ViSiccuaddiario> listViSiccuaddiario(Session session, ViSiccuaddiario obj) throws Exception {
                
        int flgFilter = 0;
        Date fecDesde = null, fecHasta = null;
        List<ViSiccuaddiario> lstResult = new ArrayList();
        try{
            
            Criteria criteria = session.createCriteria(ViSiccuaddiario.class);
        
            System.out.println("FecDesde: " + obj.getFecApertura());
            System.out.println("FecHasta: " + obj.getFecCierre());

            if(obj.getFecApertura() != null || obj.getFecCierre() != null){
              fecDesde = obj.getFecApertura() != null?obj.getFecApertura():UtilClass.getObtFecIni();
              fecHasta = obj.getFecCierre() != null?obj.getFecCierre():UtilClass.getObtFecInf();
            }
            
            System.out.println("FecDesde: " + fecDesde);
            System.out.println("FecHasta: " + fecHasta);
            
            if(fecDesde != null){
                criteria.add(Restrictions.between("numPeri",UtilClass.convertDateToNumber(fecDesde) , UtilClass.convertDateToNumber(fecHasta)));
                flgFilter = 1;
            }
            
            lstResult = criteria.list();      

        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return lstResult;
    }
    
    
}
