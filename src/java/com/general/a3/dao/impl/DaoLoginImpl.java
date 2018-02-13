/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1usuario;
import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emoreno
 */
public class DaoLoginImpl implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(DaoPersonImpl.class);

    /*CONSTRUCTORES*/
    public DaoLoginImpl(){
        
    }
    
    public Sic1usuario getByCodIden(Session session, String codIden) throws Exception{
        
        Sic1usuario sic1usuario = (Sic1usuario)session.createCriteria(Sic1usuario.class).add(Restrictions.eq("codUsuario", codIden.toUpperCase())).uniqueResult();
        return sic1usuario;
    }
    
    
    public Sic1usuario validateUsernamePassword(Session session, Sic1usuario obj) throws Exception {        
        
        Sic1usuario sic1usuario = new Sic1usuario();
        
        Criteria criteria = session.createCriteria(Sic1usuario.class);
        
        if( obj.getCodUsuario() != null &&
                obj.getCodUsuario().trim().length() > 0 &&
                obj.getCodPwd()!= null && 
                obj.getCodPwd().trim().length() > 0 ){
            
            criteria.add(Restrictions.eq("codUsuario",obj.getCodUsuario().toUpperCase()));
            criteria.add(Restrictions.eq("codPwd",obj.getCodPwd()));
            
            sic1usuario = (Sic1usuario)criteria.uniqueResult();
        }
        
        /*Si el usuario existe se obtiene los datos complementarios*/
        if (sic1usuario != null){
            
            DaoPersonImpl daoPersonImpl = new DaoPersonImpl();
            Sic1idenpers sic1idenpers = daoPersonImpl.getById(session, sic1usuario.getIdUsuario());
            sic1usuario.setSic1idenpers(sic1idenpers);
        }
        return sic1usuario;
    }
}
