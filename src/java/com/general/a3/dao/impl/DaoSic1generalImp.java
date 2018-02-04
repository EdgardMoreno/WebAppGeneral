/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.util.dao.DaoFuncionesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 * @Descripcion: Clase que maneja los catalogos
 */
public class DaoSic1generalImp implements Serializable{
    
    private Session session;    
    
    public DaoSic1generalImp(){
        
    }
    
    public List<Sic1general> listByCod_ValorTipoGeneral(List<String> list) throws Exception {
        List<Sic1general> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();        
            lst = session.createCriteria(Sic1general.class).add(Restrictions.in("codValortipogeneral", list)).list();
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1general> listByCod_ValorGeneral(List<String> list) throws Exception {
        List<Sic1general> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();   
            lst = session.createCriteria(Sic1general.class).add(Restrictions.in("codValorgeneral", list)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1stipodocu> listByCod_STipoDocu(List<String> list) throws Exception {
        List<Sic1stipodocu> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1stipodocu.class).add(Restrictions.in("codStipodocu", list)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }    
    
    public List<Sic1general> listById_GeneralRelSec(BigDecimal id) throws Exception {
        List<Sic1general> lst;        
        try{    
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1general.class).add(Restrictions.eq("idGeneralrelsec", id)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1general> listById_GeneralRel(BigDecimal id) throws Exception {
        List<Sic1general> lst;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1general.class).add(Restrictions.eq("idGeneralrel", id)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1stipodocu> getCataDocumentsType() throws SQLException, Exception {
        List<Sic1stipodocu> lstSic1stipodocu = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            BigDecimal intIdTipoDocu;       

            intIdTipoDocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), "VI_SICTIPODOCU", "COMPRA_VENTA");            
            lstSic1stipodocu = session.createCriteria(Sic1stipodocu.class).add(Restrictions.eq("idTipodocu", intIdTipoDocu)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
            
        return lstSic1stipodocu;
    }
    
    public List<ViSicestageneral> getCataEstaRol(ViSicestageneral obj) throws Exception{
        
        List<ViSicestageneral> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();        
            Criteria criteria = session.createCriteria(ViSicestageneral.class);

            if(obj.getCodTrolesta()!= null && obj.getCodTrolesta().trim().length() > 0)
                criteria.add(Restrictions.eq("codTrolesta",obj.getCodTrolesta()));

            lst = criteria.list();
        
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }

        return lst;        
    }    
}
