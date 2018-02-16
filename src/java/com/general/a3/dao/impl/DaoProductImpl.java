/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.util.dao.DaoFuncionesUtil;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3proddocu;
import com.general.hibernate.views.ViSicprod;
import com.general.util.beans.Constantes;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.general.util.beans.UtilClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

/**
 *
 * @author Edgard
 */

public class DaoProductImpl implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(DaoProductImpl.class);
    private Session session;
    private Transaction tx;
    
    /*CONSTRUCTORES*/
    public DaoProductImpl(){        
        
    }
    
    /*METODOS*/
    public String insert(Session session, Sic1prod sic1prod) throws Exception {
        
        try {
            
            System.out.println("************* REGISTRAR PRODUCTO*************" + sic1prod);            
            
            String result = session.doReturningWork(new ReturningWork<String>() {
                @Override
                public String execute(Connection cnctn) throws SQLException {
                    
                    String strFecDesde = null;
                    String strFecHasta = null;
                    BigDecimal intIdTipoIden;  
                    String valor = null;
                    
                    try{
                    
                        intIdTipoIden = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTIPOIDEN", "IDENPROD");

                        System.out.println("intIdTipoIden:" + intIdTipoIden);

                        /*CONVERSION DE FECHAS*/            
                        if (sic1prod.getFecDesde()!= null){
                            strFecDesde = UtilClass.convertDateToString(sic1prod.getFecDesde());
                        }
                        if (sic1prod.getFecHasta()!= null){
                            strFecHasta = UtilClass.convertDateToString(sic1prod.getFecHasta());
                        }
                        /**/

                        StoredProcedure sp = new StoredProcedure("PKG_SICMANTPROD.PRC_SICCREAPROD");                

                        sp.addParameter(new InParameter("X_ID_TIPOIDEN",    Types.INTEGER, intIdTipoIden));
                        sp.addParameter(new InParameter("X_COD_IDEN",       Types.VARCHAR, sic1prod.getCodProd().toUpperCase()));
                        sp.addParameter(new InParameter("X_DES_PROD",       Types.VARCHAR, sic1prod.getDesProd().toUpperCase()));
                        //Persona Juridica
                        sp.addParameter(new InParameter("X_ID_STIPOPROD",   Types.INTEGER, sic1prod.getIdStipoprod()));
                        sp.addParameter(new InParameter("X_DES_PRODCOME",   Types.VARCHAR, sic1prod.getDesProdcome() != null?sic1prod.getDesProdcome().toUpperCase():null));
                        sp.addParameter(new InParameter("X_FEC_DESDE",      Types.VARCHAR, strFecDesde));
                        sp.addParameter(new InParameter("X_FEC_HASTA",      Types.VARCHAR, strFecHasta));
                        sp.addParameter(new InParameter("X_NUM_VALOR",      Types.NUMERIC, sic1prod.getNumPrecio()));

                        sp.addParameter(new OutParameter("X_ID_PROD",       Types.INTEGER));
                        sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
                        sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
                        sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));

                        sp.ExecuteNonQuery(cnctn);

                        if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                            throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                        }

                        valor = sp.getParameter("X_ID_PROD").toString();
                    
                    } catch (Exception ex) {
                        throw new HibernateException(ex.getMessage());
                    }
                    return valor;
                }
            });
            
            return result;
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    
    public String update(Sic1prod sic1prod) {
        String result = null;
        tx = session.getTransaction();
        
        try {
            session.clear();
            session.update(sic1prod);
            tx.commit();
            
        } catch (Exception e) {
            result = e.getMessage();
            tx.rollback();
        }
        
        return result;        
    }

    
    public String delete(Session session, String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String relateProdDocu(Session session, List<Sic3proddocu> lstSic3proddocus) throws Exception {
              
//        try {           
//            
//            session.doWork(new Work() {
//                @Override
//                public void execute(Connection cnctn) throws SQLException {                   
//                    
//                    String strFecDesde = null; 
//                    String strFecHasta = null; 
//                    BigDecimal intIdTrelaprod;                    
//                    
//                    try {
//                        intIdTrelaprod = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTRELA", "NOAPLICA");
//
//                        System.out.println("intIdTipoIden:" + intIdTrelaprod);
//
//                        for(Sic3proddocu sic3proddocu : lstSic3proddocus){
//
//                            /*CONVERSION DE FECHAS*/
//                            if (sic3proddocu.getId().getFecDesde()!= null){
//                                strFecDesde = UtilClass.convertDateToString(sic3proddocu.getId().getFecDesde());
//                            }
//                            if (sic3proddocu.getFecHasta()!= null){
//                                strFecHasta = UtilClass.convertDateToString(sic3proddocu.getFecHasta());
//                            }
//                            /**/
//
//                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTPROD.PRC_SICRELAPRODDOCU");                
//
//                            sp.addParameter(new InParameter("X_ID_PROD",        Types.INTEGER, sic3proddocu.getId().getIdProd()));
//                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, sic3proddocu.getId().getIdDocu()));
//                            sp.addParameter(new InParameter("X_ID_TRELAPROD",   Types.INTEGER, intIdTrelaprod));
//                            //Persona Juridica
//                            sp.addParameter(new InParameter("X_FEC_DESDE",      Types.INTEGER, strFecDesde));
//                            sp.addParameter(new InParameter("X_FEC_HASTA",      Types.VARCHAR, strFecHasta));
//                            sp.addParameter(new InParameter("X_DES_NOTAS",      Types.VARCHAR, sic3proddocu.getDesNotas()));
//                            sp.addParameter(new InParameter("X_NUM_VALOR",      Types.VARCHAR, sic3proddocu.getNumValor()));
//                            sp.addParameter(new InParameter("X_NUM_MTODSCTO",   Types.NUMERIC, sic3proddocu.getNumMtodscto()));
//                            sp.addParameter(new InParameter("X_NUM_CANTIDAD",   Types.VARCHAR, sic3proddocu.getNumCantidad()));
//
//                            sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
//                            sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
//                            sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));
//
//                            sp.ExecuteNonQuery(cnctn);
//
//                            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
//                                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
//                            }  
//                        }
//                    }catch(Exception ex){
//                        throw new HibernateException(ex.getMessage());
//                    }
//                }
//            });
//            
//            return null;
//            
//        } catch (HibernateException ex) {
//            throw new HibernateException(ex.getMessage());
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
        return  "";
    }    
    
    public List<Sic1prod> getAll(Session session, Sic1prod obj) throws SQLException, Exception{        

        List<Sic1prod> lstResult = new ArrayList();
        int flgFilter = 0;
        
        Criteria criteria = session.createCriteria(Sic1prod.class);

        if(obj.getIdProd()!= null && obj.getIdProd().intValue() > 0){
            criteria.add(Restrictions.eq("idProd",obj.getIdProd()));
            flgFilter = 1;
        }
        if(obj.getCodProd()!= null && obj.getCodProd().trim().length() > 0 ){
            criteria.add(Restrictions.like("codProd",obj.getCodProd().toUpperCase() + "%"));
            flgFilter = 1;
        }
        if(obj.getDesProd()!= null && obj.getDesProd().trim().length() > 0 ){
            criteria.add(Restrictions.like("desProd","%"  + obj.getDesProd().toUpperCase() + "%" ));
            flgFilter = 1;
        }
        
        if(flgFilter == 1){
            criteria.addOrder(Order.asc("codProd"));
            lstResult = criteria.list();  
        }
        
        return lstResult;       
    }
    
    public List<Sic1prod> getAutocompleteByCodProd(Session session, String codProd) throws SQLException, Exception{        

        List<Sic1prod> lstResult = new ArrayList();
        int flgFilter = 0;
        
        Criteria criteria = session.createCriteria(Sic1prod.class);
       
        if(codProd != null && codProd.trim().length() > 0 ){
            criteria.add(Restrictions.like("codProd", codProd.toUpperCase() + "%"));
            flgFilter = 1;
        }
        
        if(flgFilter == 1){
            criteria.addOrder(Order.asc("codProd"));
            criteria.setMaxResults(Constantes.CONS_VALUE_AUTOCOMPLETE_NUMROWS);
            lstResult = criteria.list();
        }
        
        return lstResult;       
    }
    
    public Sic1prod getByCod(Session session, String cod) {
        
        Sic1prod sic1prod = null;        
        Criteria criteria = session.createCriteria(Sic1prod.class);        
        
        if(cod!= null){
            criteria.add(Restrictions.eq("codProd",cod.trim()));         
            sic1prod = (Sic1prod)criteria.uniqueResult();            
        }

        return sic1prod;        
    }
    
     public List<ViSicprod> listViSicProd(Session session, ViSicprod obj) {
               
        Criteria criteria = session.createCriteria(ViSicprod.class);
        
        if(obj.getIdProd()!= null && obj.getIdProd().intValue() > 0)
            criteria.add(Restrictions.eq("idProd",obj.getIdProd()));
        if(obj.getCodProd()!= null && obj.getCodProd().trim().length() > 0 )
            criteria.add(Restrictions.like("codProd",obj.getCodProd().toUpperCase() + '%' ));
        if(obj.getIdStipoprod()!= null && obj.getIdStipoprod().intValue() > 0 )
            criteria.add(Restrictions.eq("idStipoprod",obj.getIdStipoprod()));        
        if(obj.getDesProd()!= null && !obj.getDesProd().trim().isEmpty() )
            criteria.add(Restrictions.like("desProd",'%' + obj.getDesProd().toUpperCase() + '%'));
        
        List<ViSicprod> lst = criteria.list();

        return lst;
    }
    
}
