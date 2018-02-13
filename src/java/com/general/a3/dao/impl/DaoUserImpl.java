/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emoreno
 */
public class DaoUserImpl implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(DaoPersonImpl.class);

    /*CONSTRUCTORES*/
    public DaoUserImpl(){
        
    }
    
    public Sic1usuario getByCodIden(Session session, String codIden) throws Exception{
        
        Sic1usuario sic1usuario = (Sic1usuario)session.createCriteria(Sic1usuario.class).add(Restrictions.eq("codUsuario", codIden.toUpperCase())).uniqueResult();
        return sic1usuario;
    }
    
}
