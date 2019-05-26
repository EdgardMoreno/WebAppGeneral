/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1usuario;
import com.general.hibernate.entity.Sic7persrol;
import com.general.util.beans.UtilClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
    
    
    public Sic1usuario validateUsernamePassword(Session session, String userName, String passWord) throws Exception {        
        
        Sic1usuario sic1usuario;
        
        Criteria criteria = session.createCriteria(Sic1usuario.class);
            
        criteria.add(Restrictions.eq("codUsuario",userName).ignoreCase());
        criteria.add(Restrictions.eq("codPwd",passWord));
            
        sic1usuario = (Sic1usuario)criteria.uniqueResult();        
        
        /*Si el usuario existe se obtiene los datos complementarios*/
        if (sic1usuario != null){
            
            DaoPersonImpl daoPersonImpl = new DaoPersonImpl();
            Sic1idenpers sic1idenpers = daoPersonImpl.getById(session, sic1usuario.getIdUsuario());
            sic1usuario.setSic1idenpers(sic1idenpers);
            
            /*Se obtiene el rol del usuario y el codigo del estado de la caja*/
            String sql =                     
                " SELECT  PKG_SICCONSGENERAL.FNC_SICOBTCODGEN('VI_SICTROLPERS',ROL.ID_TROLPERS) AS COD_TROLPERS " +
                "        ,PKG_SICCONSGENERAL.FNC_SICOBTCODGEN('VI_SICESTA',T0.ID_ESTA) AS COD_ESTACAJA      " +
                " FROM SIC1PERS PERS " +
                " JOIN SIC7PERSROL ROL ON ROL.ID_PERS = PERS.ID_PERS " +
                "                         AND ROL.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF " +
                " LEFT JOIN SIC4CUADDIARIO T0 ON T0.ID_PERS = PERS.ID_PERS " +
                " AND T0.NUM_PERI = :num_peri " +
                " WHERE PERS.ID_PERS = :id_pers ";

            Query  query = session.createSQLQuery(sql)
                     .setParameter("num_peri", UtilClass.getCurrentTime_YYYYMMDD())
                     .setParameter("id_pers", sic1usuario.getIdUsuario());
            
            List<Object[]> rows = query.list();
            
            List<Sic7persrol> lstRoles = new ArrayList<>();
            for(Object[] row : rows){
                
                System.out.println("COD_TROLPERS: " + row[0].toString());
                System.out.println("COD_ESTACAJA: " + (String)row[1]);
                
                Sic7persrol objRol = new Sic7persrol();
                objRol.setCodTrolpers(row[0].toString());
                //sic1usuario.setCodTrolpers(row[0].toString());
                sic1usuario.setCodEstacaja((String)row[1]);
                
                lstRoles.add(objRol);               
                
            }
            
            sic1usuario.setLstSic7persrol(lstRoles);
        }
        return sic1usuario;
    }
}
