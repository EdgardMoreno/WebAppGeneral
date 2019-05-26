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
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
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
    public String insert(Session session, Sic1prod sic1prod) throws ValidationException, Exception {
        
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
                        
                        
                        /* Validar si el producto que se quiere registrar ya existe
                        *  Si el ID_PROD es null quiere decir que es un nuevo producto
                        */
                        if( sic1prod.getIdProd()== null){
                            BigDecimal id = DaoFuncionesUtil.FNC_SICOBTIDIDEN(cnctn, "PROD", intIdTipoIden.intValue(), sic1prod.getCodProd());
                            if (id != null && id.intValue() > 0)
                                return "-99";                                
                        }

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
                        sp.addParameter(new InParameter("X_COD_IDEN",       Types.VARCHAR, sic1prod.getCodProd().trim().toUpperCase()));
                        sp.addParameter(new InParameter("X_COD_PRODINT",    Types.VARCHAR, sic1prod.getCodProdint().trim().toUpperCase()));
                        sp.addParameter(new InParameter("X_DES_PROD",       Types.VARCHAR, sic1prod.getDesProd().trim().toUpperCase()));
                        //Persona Juridica
                        sp.addParameter(new InParameter("X_ID_STIPOPROD",   Types.INTEGER, sic1prod.getIdStipoprod()));
                        sp.addParameter(new InParameter("X_DES_PRODCOME",   Types.VARCHAR, sic1prod.getDesProdcome() != null?sic1prod.getDesProdcome().trim().toUpperCase():null));
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
            
            if (result.equals("-99"))
                throw new ValidationException("El código del producto ingresado ya existe");
            
            return result;
        
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());       
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
    
    /**
     * MEDODO QUE ACTUALIZA EL STOCK DEL PRODUCTO
     * @param cnConexion
     * @param idDocu
     * @throws Exception 
     */
    public void updateStock(Connection cnConexion, Integer idDocu) throws Exception{
        
        try{
            
            /*Si es venta, se suma; si es compra, se resta; si no se suma 0*/

            String sql = 
                        "UPDATE SIC1PROD T0 " +
                        "       SET T0.NUM_CANTSTOCK = NUM_CANTSTOCK + (SELECT SUM(TMP1.NUM_CANTIDAD) * DECODE(TMPDOC.ID_SCLASEEVEN,1,-1,2,1,0) " +
                        "                                               FROM SIC3DOCUPROD TMP1 " +
                        "                                               JOIN SIC1DOCU TMPDOC ON TMPDOC.ID_DOCU = TMP1.ID_DOCU " +
                        "                                               WHERE TMP1.ID_DOCU = " + idDocu +" AND TMP1.ID_PROD = T0.ID_PROD " +
                        "                                               GROUP BY TMPDOC.ID_SCLASEEVEN  ) " +
                        "WHERE T0.ID_PROD IN (SELECT ID_PROD " +
                                            " FROM SIC3DOCUPROD TMP1 "
                                            + " JOIN SIC1DOCU TMP2 ON TMP1.ID_DOCU = TMP2.ID_DOCU "
                                            + " WHERE TMP2.ID_STIPODOCU <> 4 AND TMP1.ID_DOCU = " + idDocu+ ")";

            System.out.println("sql:" + sql);
            CallableStatement statement = cnConexion.prepareCall(sql);
            statement.executeUpdate();
                 
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        
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
            criteria.add(Restrictions.like("codProd",obj.getCodProd() + "%").ignoreCase());
            flgFilter = 1;
        }
        if(obj.getDesProd()!= null && obj.getDesProd().trim().length() > 0 ){
            criteria.add(Restrictions.like("desProd","%"  + obj.getDesProd() + "%" ).ignoreCase());
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

        /*Si el codigo ingresado comienza con el caracter "/" se busca el codigo interno del producto*/
        if(codProd != null && codProd.trim().length() > 0 && codProd.trim().substring(0, 1).equals("/")){            
            criteria.add(Restrictions.like("codProdint", "%" + codProd.substring(1) + "%").ignoreCase());
            flgFilter = 1;
        }else if(codProd != null && codProd.trim().length() > 0){
            criteria.add(Restrictions.like("codProd", "%" + codProd + "%").ignoreCase());            
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
            criteria.add(Restrictions.eq("codProd",cod.trim()).ignoreCase());         
            sic1prod = (Sic1prod)criteria.uniqueResult();            
        }

        return sic1prod;        
    }
    
     public List<ViSicprod> listViSicProd(Session session, ViSicprod obj) {
               
        Criteria criteria = session.createCriteria(ViSicprod.class);
        
        if(obj.getIdProd()!= null && obj.getIdProd().intValue() > 0)
            criteria.add(Restrictions.eq("idProd",obj.getIdProd()));
        if(obj.getCodProd()!= null && obj.getCodProd().trim().length() > 0 )
            criteria.add(Restrictions.like("codProd",obj.getCodProd().trim() + '%' ).ignoreCase());
        if(obj.getCodProdint()!= null && obj.getCodProdint().trim().length() > 0 )
            criteria.add(Restrictions.like("codProdint",obj.getCodProdint().trim() + '%' ).ignoreCase());
        if(obj.getIdStipoprod()!= null && obj.getIdStipoprod().intValue() > 0 )
            criteria.add(Restrictions.eq("idStipoprod",obj.getIdStipoprod()));        
        if(obj.getDesProd()!= null && !obj.getDesProd().trim().isEmpty() )
            criteria.add(Restrictions.like("desProd",'%' + obj.getDesProd().trim() + '%').ignoreCase());
        
        List<ViSicprod> lst = criteria.list();

        return lst;
    }
    
}
