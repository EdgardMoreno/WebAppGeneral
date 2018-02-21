/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoCashRegisterImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate1.Sic4cuaddiario;
import com.general.hibernate1.Sic4cuaddiarioId;
import com.general.util.exceptions.CustomizerException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author emoreno
 */
public class CashRegisterServiceImpl {
    
    private Session session;
    
    
    public void insert(Sic4cuaddiario obj) throws CustomizerException {
        
        Transaction tx = null;        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.clear();
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            dao.insert(session, obj);
            tx.commit();
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            throw new CustomizerException(e.getMessage());
        }finally{
            session.close();
        }
    }
    
     public Sic4cuaddiario getById(Sic4cuaddiarioId id) throws Exception {
        
        Sic4cuaddiario obj = null;
        try {        
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            session = HibernateUtil.getSessionFactory().openSession();
            obj = dao.getById(session, id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return obj;
    }
    
}
