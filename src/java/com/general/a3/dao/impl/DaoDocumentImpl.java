/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.util.dao.DaoFuncionesUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.views.ViSicdocu;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
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
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

/**
 *
 * @author Edgard
 */
//@Lazy
//@Component
public class DaoDocumentImpl implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(DaoDocumentImpl.class);    
    private Transaction tx;
    
    /*CONSTRUCTORES*/
    public DaoDocumentImpl(){
    }    
    
    
    /*METODOS*/    
    public String insert(Session session, Sic1idendocu sic1idendocu) throws Exception {
                
        try {
            
            String result = session.doReturningWork(new ReturningWork<String>() {
                @Override
                public String execute(Connection cnctn) throws SQLException {
                    
                    String strFecCreacion = null;
                    String strFecDesde = null;
                    String strFecHasta = null;
                    String valor = null;

                    BigDecimal intIdTipoIden;
                    //int intIdTRolPers = -1;
                    //int intIdTRolEsta = -1; 

                    try{
                    
                        intIdTipoIden = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTIPOIDEN", "DOCUMENTO");
                        /*Rol de la Persona: Empleado de Ventas*/
                        //intIdTRolPers  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTROLPERS", "VI_SICVENDEDOR");
                        /*Rol del estado del documento*/
                        //intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTROLESTA", "VI_SICESTADOCUCOMPROBANTE");

                        Sic1docu sic1docu = sic1idendocu.getSic1docu();
                        
                        System.out.println("intIdTipoIden:" + intIdTipoIden);
                        System.out.println("intIdTRolPers:" + sic1docu.getIdTrolpers());
                        System.out.println("intIdTRolEsta:" + sic1docu.getSic3docuesta().getId().getIdTrolestadocu());

                        /*CONVERSION DE FECHAS*/            
                        if (sic1docu.getFecCreacion() != null){
                            strFecCreacion = UtilClass.convertDateToString(sic1docu.getFecCreacion());
                        }
                        if (sic1docu.getFecDesde()!= null){
                            strFecDesde = UtilClass.convertDateToString(sic1docu.getFecDesde());
                        }
                        if (sic1docu.getFecHasta()!= null){
                            strFecHasta = UtilClass.convertDateToString(sic1docu.getFecHasta());
                        }
                        /**/


                        StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICCREADOCU");

                        sp.addParameter(new InParameter("X_ID_TIPOIDEN",        Types.INTEGER, intIdTipoIden));
                        sp.addParameter(new InParameter("X_COD_IDEN",           Types.VARCHAR, sic1idendocu.getCodIden().toUpperCase()));
                        sp.addParameter(new InParameter("X_DES_TITULO",         Types.VARCHAR, null));
                        sp.addParameter(new InParameter("X_DES_NOTAS",          Types.VARCHAR, sic1docu.getDesNotas()));
                        sp.addParameter(new InParameter("X_FEC_CREACION",       Types.VARCHAR, strFecCreacion));
                        sp.addParameter(new InParameter("X_DES_DOCU",           Types.VARCHAR, sic1docu.getDesDocu()));
                        sp.addParameter(new InParameter("X_ID_TROLPERS",        Types.INTEGER, sic1docu.getIdTrolpers()));
                        sp.addParameter(new InParameter("X_ID_STIPODOCU",       Types.INTEGER, sic1docu.getIdStipodocu()));
                        sp.addParameter(new InParameter("X_ID_PERS",            Types.INTEGER, sic1docu.getIdPers()));

                        sp.addParameter(new InParameter("X_ID_MODAPAGO",        Types.INTEGER, sic1docu.getIdModapago()== new BigDecimal(-1)?null:sic1docu.getIdModapago()));
                        sp.addParameter(new InParameter("X_ID_TIPOTARJETA",     Types.INTEGER, sic1docu.getIdTipotarjeta() == new BigDecimal(-1)?null:sic1docu.getIdTipotarjeta()));
                        sp.addParameter(new InParameter("X_NUM_MTOTARJETA",     Types.NUMERIC, sic1docu.getNumMtotarjeta()));
                        sp.addParameter(new InParameter("X_NUM_MTOCOMI",        Types.NUMERIC, sic1docu.getNumMtocomi()));
                        sp.addParameter(new InParameter("X_NUM_MTOEFECTIVO",    Types.NUMERIC, sic1docu.getNumMtoefectivo()));

                        sp.addParameter(new InParameter("X_COD_SERIE",          Types.INTEGER, sic1docu.getCodSerie()));
                        sp.addParameter(new InParameter("X_NUM_DOCU",           Types.INTEGER, sic1docu.getNumDocu()));
                        sp.addParameter(new InParameter("X_NUM_MTODSCTO",       Types.NUMERIC, sic1docu.getNumMtodscto()));
                        sp.addParameter(new InParameter("X_NUM_SUBTOTAL",       Types.NUMERIC, sic1docu.getNumSubtotal()));
                        sp.addParameter(new InParameter("X_NUM_IGV",            Types.NUMERIC, sic1docu.getNumIgv()));

                        sp.addParameter(new InParameter("X_FEC_DESDE",          Types.VARCHAR, strFecDesde));
                        sp.addParameter(new InParameter("X_FEC_HASTA",          Types.VARCHAR, strFecHasta));

                        /*DOCUMENTO BINARIO*/
                        sp.addParameter(new InParameter("X_ID_TRELADOCU",       Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_COD_DOCUBINA",       Types.VARCHAR, null));            
                        sp.addParameter(new InParameter("X_NUM_BYTES",          Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_NUM_FOLIOS",         Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_DES_LOCAURI",        Types.VARCHAR, null));
                        sp.addParameter(new InParameter("X_DES_NOMBREAL",       Types.VARCHAR, null));
                        sp.addParameter(new InParameter("X_ID_EXTEDOCU",        Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_ID_LENGDOCU",        Types.INTEGER, null));

                        /*ESTADO DEL DOCUMENTO: Se manda null para que todo se registre por defecto*/
                        sp.addParameter(new InParameter("X_ID_ESTAREL",         Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_ID_TROLESTADOCU",    Types.INTEGER, sic1docu.getSic3docuesta().getId().getIdTrolestadocu()));
                        sp.addParameter(new InParameter("X_ID_TRELADOCUESTA",   Types.INTEGER, null));
                        sp.addParameter(new InParameter("X_DES_NOTASESTA",      Types.VARCHAR, null));            

                        sp.addParameter(new OutParameter("X_ID_DOCU", Types.INTEGER));
                        sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
                        sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
                        sp.addParameter(new OutParameter("X_FEC_ERROR", Types.DATE));

                        sp.ExecuteNonQuery(cnctn);

                        if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                            throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                        }
                        
                        valor = sp.getParameter("X_ID_DOCU").toString();                        
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

    
    public String update(Session session, Sic1docu sic1docu) {
        String result = null;
        tx = session.getTransaction();
        
        try {
            session.clear();
            session.update(sic1docu);
            tx.commit();
            
        } catch (Exception e) {
            result = e.getMessage();
            tx.rollback();
        }
        
        return result;        
    }

    
    public String delete(Connection cnConexion, String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String relateDocuDocu(Session session, Sic3docudocu sic3docudocu) throws Exception {        
        
        try {
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {
                    
                    /*Relacion: Reemplaza documento anulado*/
                    try{
                        //int intIdTRela = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTRELA", "RELDOCUANULADO");

                        /*CONVERSION DE FECHAS*/
                        String strFecHasta = null;
                        String strFecDesde = null;
                        if (sic3docudocu.getId().getFecDesde() != null){
                            strFecDesde = UtilClass.convertDateToString(sic3docudocu.getId().getFecDesde());
                        }
                        if (sic3docudocu.getFecHasta()!= null){
                            strFecHasta = UtilClass.convertDateToString(sic3docudocu.getFecHasta());
                        }
                        /**/

                        StoredProcedure sp = null;
                        sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUDOCU");

                        sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, sic3docudocu.getId().getIdDocu()));
                        sp.addParameter(new InParameter("X_ID_DOCUREL",     Types.INTEGER, sic3docudocu.getId().getIdDocurel()));
                        sp.addParameter(new InParameter("X_ID_TRELADOCU",   Types.INTEGER, sic3docudocu.getId().getIdTreladocu()));
                        sp.addParameter(new InParameter("X_DES_NOTAS",      Types.VARCHAR, sic3docudocu.getDesNotas()));
                        sp.addParameter(new InParameter("X_FEC_DESDE",      Types.VARCHAR, strFecDesde));
                        sp.addParameter(new InParameter("X_FEC_HASTA",      Types.VARCHAR, strFecHasta));

                        sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
                        sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
                        sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));

                        sp.ExecuteNonQuery(cnctn);

                        if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                            throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                        }
                    }catch(Exception ex){
                        throw new HibernateException(ex.getMessage());
                    }
                }
            });
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
        return null;
    }

    
    public String relateDocuEsta(Session session, Sic3docuesta sic3docuesta) throws Exception {
        
        try {
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {

                    String strFecHasta = null;
                    String strFecDesde = null;
                    
                    /*CONVERSION DE FECHAS*/            
                    if (sic3docuesta.getId().getFecDesde() != null){
                        strFecDesde = UtilClass.convertDateToString(sic3docuesta.getId().getFecDesde());
                    }
                    if (sic3docuesta.getFecHasta()!= null){
                        strFecHasta = UtilClass.convertDateToString(sic3docuesta.getFecHasta());
                    }
                    /**/

                    StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUESTA");

                    sp.addParameter(new InParameter("X_ID_DOCU",            Types.INTEGER, sic3docuesta.getId().getIdDocu()));
                    sp.addParameter(new InParameter("X_ID_ESTAREL",         Types.INTEGER, sic3docuesta.getId().getIdEstadocu()));
                    sp.addParameter(new InParameter("X_ID_TROLESTADOCU",    Types.INTEGER, sic3docuesta.getId().getIdTrolestadocu()));
                    sp.addParameter(new InParameter("X_ID_TRELADOCU",       Types.INTEGER, sic3docuesta.getId().getIdTreladocu()));
                    sp.addParameter(new InParameter("X_DES_NOTAS",          Types.VARCHAR, sic3docuesta.getDesNotas()));
                    sp.addParameter(new InParameter("X_FEC_DESDE",          Types.VARCHAR, strFecDesde));
                    sp.addParameter(new InParameter("X_FEC_HASTA",          Types.VARCHAR, strFecHasta));

                    sp.addParameter(new OutParameter("X_ID_ERROR",          Types.INTEGER));
                    sp.addParameter(new OutParameter("X_DES_ERROR",         Types.VARCHAR));
                    sp.addParameter(new OutParameter("X_FEC_ERROR",         Types.DATE));

                    sp.ExecuteNonQuery(cnctn);

                    if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0) {
                        throw new SQLException((String) sp.getParameter("X_DES_ERROR"));
                    }

                }
            });
            
            
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
        return null;
    }
    
    public String relateDocuPers(Session session, List<Sic3docupers> lstSic3docupers) throws Exception {
              
        try {           
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {                   
                    
                    String strFecDesde = null; 
                    String strFecHasta = null; 
                    int intIdTrelaprod = -1;
                    
                    try {

                        System.out.println("intIdTipoIden:" + intIdTrelaprod);

                        for(Sic3docupers sic3docupers : lstSic3docupers){

                            /*CONVERSION DE FECHAS*/
                            if (sic3docupers.getId().getFecDesde()!= null){
                                strFecDesde = UtilClass.convertDateToString(sic3docupers.getId().getFecDesde());
                            }
                            if (sic3docupers.getFecHasta()!= null){
                                strFecHasta = UtilClass.convertDateToString(sic3docupers.getFecHasta());
                            }
                            /**/

                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUPERS");                

                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, sic3docupers.getId().getIdDocu()));
                            sp.addParameter(new InParameter("X_ID_PERS",        Types.INTEGER, sic3docupers.getId().getIdPers()));
                            sp.addParameter(new InParameter("X_ID_TROLPERS",    Types.INTEGER, sic3docupers.getId().getIdTrolpers()));
                            sp.addParameter(new InParameter("X_ID_TRELADOCU",   Types.INTEGER, sic3docupers.getId().getIdTreladocu()));
                            
                            sp.addParameter(new InParameter("X_COD_VALORATRIB", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_NOTAS",      Types.VARCHAR, sic3docupers.getDesNotas()));
                            
                            sp.addParameter(new InParameter("X_FEC_DESDE",      Types.INTEGER, strFecDesde));
                            sp.addParameter(new InParameter("X_FEC_HASTA",      Types.VARCHAR, strFecHasta));                            

                            sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
                            sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
                            sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));

                            sp.ExecuteNonQuery(cnctn);

                            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                            }  
                        }
                    }catch(Exception ex){
                        throw new HibernateException(ex.getMessage());
                    }
                }
            });
            
            return null;
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
//    public Sic1docu get(Session session, Sic1docu obj) {
//        
//        Criteria criteria = session.createCriteria(Sic1docu.class);
//        
//        if(obj.getIdDocu() != null && obj.getIdDocu().intValue() > 0)
//            criteria.add(Restrictions.eq("idDocu",obj.getIdDocu()));        
//        if(obj.getCodSerie()!= null && obj.getCodSerie().trim().length() > 0 )
//            criteria.add(Restrictions.eq("codSerie",obj.getCodSerie()));
//        if(obj.getNumDocu() != null && obj.getNumDocu().intValue() > 0)
//            criteria.add(Restrictions.eq("numDocu",obj.getNumDocu()));
//        
//        return (Sic1docu)criteria.uniqueResult();
//        
//    }
    
    public Sic1idendocu get(Session session, Sic1idendocu obj) {
        
        Sic1idendocu sic1idendocu = null;
        int flgFilter = 0;
        Criteria criteria = session.createCriteria(Sic1idendocu.class);
        
        if(obj.getIdDocu() != null && obj.getIdDocu().intValue() > 0){
            criteria.add(Restrictions.eq("sic1docu.idDocu",obj.getIdDocu()));
            flgFilter = 1;
        }
        if(obj.getId() != null && obj.getId().getCodIden() != null && obj.getId().getCodIden().trim().length() > 0 ){
            criteria.add(Restrictions.eq("codIden",obj.getId().getCodIden()));
            flgFilter = 1;
        }

        if (flgFilter == 1){
            
            sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
            if(sic1idendocu != null)
                Hibernate.initialize(sic1idendocu.getSic1docu());
        }

        return sic1idendocu;
        
    }
    
    public Sic1idendocu getByCodiden(Session session, String codIden) {
        
        Sic1idendocu sic1idendocu = null;        
        Criteria criteria = session.createCriteria(Sic1idendocu.class);        
        
        if(codIden!= null){
            criteria.add(Restrictions.eq("codIden",codIden));         
            sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
            if(sic1idendocu != null)
                Hibernate.initialize(sic1idendocu.getSic1docu());
        }

        return sic1idendocu;
        
    }
    
    public Sic1idendocu getById(Session session, BigDecimal id_docu ) {
        
        Sic1idendocu sic1idendocu = null;        
        Criteria criteria = session.createCriteria(Sic1idendocu.class);        
        
        if(id_docu!= null){
            
            criteria.add(Restrictions.eq("sic1docu.idDocu",id_docu));            
            sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
            if(sic1idendocu != null)
                Hibernate.initialize(sic1idendocu.getSic1docu());
        }

        return sic1idendocu;
        
    }
    
    
    public List<ViSicdocu> listViSicdocu(Session session, ViSicdocu obj) {
                
        int flgFilter = 0;
        List<ViSicdocu> lstResult = new ArrayList();
        Criteria criteria = session.createCriteria(ViSicdocu.class);
        
        if(obj.getIdDocu() != null && obj.getIdDocu().intValue() > 0){
            criteria.add(Restrictions.eq("idDocu",obj.getIdDocu()));
            flgFilter = 1;
        }
        if(obj.getCodIden() != null && obj.getCodIden().trim().length() > 0 ){
            criteria.add(Restrictions.eq("codIden",obj.getCodIden()));
            flgFilter = 1;
        }
        if(obj.getCodSerie()!= null && obj.getCodSerie().trim().length() > 0 ){
            criteria.add(Restrictions.eq("codSerie",obj.getCodSerie()));
            flgFilter = 1;
        }
        if(obj.getNumDocu() != null && obj.getNumDocu().intValue() > 0){
            criteria.add(Restrictions.eq("numDocu",obj.getNumDocu()));
            flgFilter = 1;
        }
        
        if(flgFilter == 1){
            criteria.addOrder(Order.desc("codSerie")).addOrder(Order.desc("numDocu"));
            lstResult = criteria.list();    
        }

        return lstResult;
    }
    
}
