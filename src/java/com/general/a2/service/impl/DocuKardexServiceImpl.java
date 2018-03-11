/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoDocuKardexImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate1.Sic1docukardex;
import com.general.util.exceptions.CustomizerException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author Edgard
 */
public class DocuKardexServiceImpl {
    
    private final DaoDocuKardexImpl daoDocuKardexImpl;
    private Session session;
    
    public DocuKardexServiceImpl(){        
        this.daoDocuKardexImpl = new DaoDocuKardexImpl();
        
    }
    
    public List<Sic1docukardex> getKardexByNumPeri(Integer numPeri ) throws CustomizerException {

        List<Sic1docukardex> list;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();            
            list = daoDocuKardexImpl.getKardexByNumPeri(((SessionImpl)session).connection(), numPeri);
            
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
        return list;
    }
}
