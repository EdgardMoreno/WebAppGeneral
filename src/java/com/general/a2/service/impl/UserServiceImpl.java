/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoUserImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1usuario;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import org.hibernate.Session;

/**
 *
 * @author emoreno
 */
public class UserServiceImpl implements Serializable{
    
    private final DaoUserImpl daoUserImpl;
    private Session session;
    
    /*CONSTRUCTORES*/
    public UserServiceImpl(){
        this.daoUserImpl  = new DaoUserImpl();
    }

    /*METODOS*/    
    public Sic1usuario getByCodIden(String codIden) throws CustomizerException{
        
        Sic1usuario result;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = daoUserImpl.getByCodIden(session, codIden);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return result;
    }
}
