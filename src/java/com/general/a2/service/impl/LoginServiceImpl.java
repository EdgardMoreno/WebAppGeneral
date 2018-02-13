/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoLoginImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1usuario;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import org.hibernate.Session;

/**
 *
 * @author emoreno
 */
public class LoginServiceImpl implements Serializable{
    
    private final DaoLoginImpl daoLoginImpl;
    private Session session;
    
    /*CONSTRUCTORES*/
    public LoginServiceImpl(){
        this.daoLoginImpl  = new DaoLoginImpl();
    }

    /*METODOS*/    
    public Sic1usuario getByCodIden(String codIden) throws CustomizerException{
        
        Sic1usuario result;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = daoLoginImpl.getByCodIden(session, codIden);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return result;
    }
    
    
    public Sic1usuario validateUsernamePassword(Sic1usuario obj) throws CustomizerException {
        
        Sic1usuario result;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = daoLoginImpl.validateUsernamePassword(session, obj);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return result;        
    }
}
