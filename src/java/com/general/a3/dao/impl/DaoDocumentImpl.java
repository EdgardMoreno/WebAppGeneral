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
import com.general.hibernate.relaentity.Sic3docuprod;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
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

                    try{
                    
                        intIdTipoIden = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTIPOIDEN", "DOCUMENTO");
                        /*Rol de la Persona: Empleado de Ventas*/
                        //intIdTRolPers  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTROLPERS", "VI_SICVENDEDOR");
                        /*Rol del estado del documento*/
                        //intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTROLESTA", "VI_SICESTADOCUCOMPROBANTE");

                        Sic1docu sic1docu = sic1idendocu.getSic1docu();
                        
                        System.out.println("intIdTipoIden:" + intIdTipoIden);
                        System.out.println("intIdTRolPers:" + sic1docu.getIdTrolpers());
                        System.out.println("intIdEstaDocu:" + sic1docu.getSic3docuesta().getId().getIdEstadocu());
                        System.out.println("intIdTRolEsta:" + sic1docu.getSic3docuesta().getId().getIdTrolestadocu());
                        System.out.println("idSclaseeven:" + sic1docu.getIdSclaseeven());
                        System.out.println("idSTipodocu:" + sic1docu.getIdStipodocu());
                        System.out.println("idPersExterno:" + sic1docu.getIdPersexterno());

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
                        String codSerie = null;
                        if (sic1docu.getCodSerie()!=null && sic1docu.getCodSerie().trim().length()>0)
                            codSerie = sic1docu.getCodSerie().trim().toUpperCase();
                                
                        System.out.println("FecCreacion: " + strFecCreacion);

                        StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICCREADOCU");

                        sp.addParameter(new InParameter("X_ID_TIPOIDEN",        Types.INTEGER, intIdTipoIden));
                        sp.addParameter(new InParameter("X_COD_IDEN",           Types.VARCHAR, sic1idendocu.getCodIden().trim().toUpperCase()));
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

                        sp.addParameter(new InParameter("X_COD_SERIE",          Types.VARCHAR, codSerie));
                        sp.addParameter(new InParameter("X_NUM_DOCU",           Types.INTEGER, sic1docu.getNumDocu()));
                        sp.addParameter(new InParameter("X_NUM_MTODSCTO",       Types.NUMERIC, sic1docu.getNumMtodscto()));
                        sp.addParameter(new InParameter("X_NUM_SUBTOTAL",       Types.NUMERIC, sic1docu.getNumSubtotal()));
                        sp.addParameter(new InParameter("X_NUM_IGV",            Types.NUMERIC, sic1docu.getNumIgv()));
                        
                        sp.addParameter(new InParameter("X_NUM_MTOVUELTO",      Types.NUMERIC, sic1docu.getNumMtovuelto()));
                        sp.addParameter(new InParameter("X_FLG_PRECSINIGV",     Types.NUMERIC, sic1docu.getFlgPrecsinIGV()));
                        
                        sp.addParameter(new InParameter("X_ID_SCLASEEVEN",      Types.NUMERIC, sic1docu.getIdSclaseeven())); //Indica si es Compra o Venta
                        sp.addParameter(new InParameter("X_ID_PERSEXTERNO",     Types.NUMERIC, sic1docu.getIdPersexterno())); //Indica el IDPers el Cliente/Proveedor

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
                        sp.addParameter(new InParameter("X_ID_ESTAREL",         Types.INTEGER, sic1docu.getSic3docuesta().getId().getIdEstadocu()));
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
                    
                    try {
                    
                        /*CONVERSION DE FECHAS*/            
                        if (sic3docuesta.getId().getFecDesde() != null){
                            strFecDesde = UtilClass.convertDateToString(sic3docuesta.getId().getFecDesde());
                        }
                        if (sic3docuesta.getFecHasta()!= null){
                            strFecHasta = UtilClass.convertDateToString(sic3docuesta.getFecHasta());
                        }
                        /**/

                        BigDecimal idTreladocuesta = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTRELA", "RELESTADODOCUMENTO");
                        
                        System.out.println("idTreladocuesta:" + idTreladocuesta);    
                        
                        StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUESTA");

                        sp.addParameter(new InParameter("X_ID_DOCU",            Types.INTEGER, sic3docuesta.getId().getIdDocu()));
                        sp.addParameter(new InParameter("X_ID_ESTAREL",         Types.INTEGER, sic3docuesta.getId().getIdEstadocu()));
                        sp.addParameter(new InParameter("X_ID_TROLESTADOCU",    Types.INTEGER, sic3docuesta.getId().getIdTrolestadocu()));
                        sp.addParameter(new InParameter("X_ID_TRELADOCU",       Types.INTEGER, idTreladocuesta));
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
                    
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(DaoDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public String relateDocuProd(Session session, List<Sic3docuprod> lstSic3docuprod) throws Exception {
              
        try {           
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {                   
                    
                    String strFecDesde = null; 
                    String strFecHasta = null; 
                    BigDecimal intIdTreladocu;                    
                    
                    try {
                        intIdTreladocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTRELA", "NOAPLICA");

                        for(Sic3docuprod sic3docuprod : lstSic3docuprod){

                            /*CONVERSION DE FECHAS*/
                            if (sic3docuprod.getId().getFecDesde()!= null){
                                strFecDesde = UtilClass.convertDateToString(sic3docuprod.getId().getFecDesde());
                            }
                            if (sic3docuprod.getFecHasta()!= null){
                                strFecHasta = UtilClass.convertDateToString(sic3docuprod.getFecHasta());
                            }
                            /**/

                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUPROD");

                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, sic3docuprod.getId().getIdDocu()));
                            sp.addParameter(new InParameter("X_ID_PROD",        Types.INTEGER, sic3docuprod.getId().getIdProd()));                            
                            sp.addParameter(new InParameter("X_ID_TRELADOCU",   Types.INTEGER, intIdTreladocu));
                            //Persona Juridica
                            sp.addParameter(new InParameter("X_FEC_DESDE",      Types.INTEGER, strFecDesde));
                            sp.addParameter(new InParameter("X_FEC_HASTA",      Types.VARCHAR, strFecHasta));
                            sp.addParameter(new InParameter("X_DES_NOTAS",      Types.VARCHAR, sic3docuprod.getDesNotas()));
                            sp.addParameter(new InParameter("X_NUM_VALOR",      Types.VARCHAR, sic3docuprod.getNumValor()));
                            sp.addParameter(new InParameter("X_NUM_MTODSCTO",   Types.NUMERIC, sic3docuprod.getNumMtodscto()));
                            sp.addParameter(new InParameter("X_NUM_CANTIDAD",   Types.VARCHAR, sic3docuprod.getNumCantidad()));

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
    
    
    public List<Sic3docuprod> getRelaDocuProdByIdDocu(Session session, BigDecimal id_docu){
        
        List<Sic3docuprod> list = new ArrayList();        
        Criteria criteria = session.createCriteria(Sic3docuprod.class);        
        
        if(id_docu!= null){
            
            criteria.add(Restrictions.eq("id.idDocu",id_docu));
            list = criteria.list();
            
            for (Sic3docuprod obj : list){
                if(obj != null)
                    Hibernate.initialize(obj.getSic1prod());
            }
        }

        return list;
        
    }
    
    public Sic1idendocu get(Session session, Sic1idendocu obj) {
        
        Sic1idendocu sic1idendocu = null;
        int flgFilter = 0;
        Criteria criteria = session.createCriteria(Sic1idendocu.class);
        
        if(obj.getIdDocu() != null && obj.getIdDocu().intValue() > 0){
            criteria.add(Restrictions.eq("sic1docu.idDocu",obj.getIdDocu()));
            flgFilter = 1;
        }
        if(obj.getId() != null && obj.getId().getCodIden() != null && obj.getId().getCodIden().trim().length() > 0 ){
            criteria.add(Restrictions.eq("codIden",obj.getId().getCodIden()).ignoreCase());
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
    
    
    public List<ViSicdocu> listViSicdocu(Session session, ViSicdocu obj) throws Exception {
                
        int flgFilter = 0;
        Date fecDesde = null, fecHasta = null;
        List<ViSicdocu> lstResult = new ArrayList();
        try{
            
            Criteria criteria = session.createCriteria(ViSicdocu.class);
        
            System.out.println("FecDesde: " + obj.getFecDesde());
            System.out.println("FecHasta: " + obj.getFecHasta());

            if(obj.getFecDesde() != null || obj.getFecHasta() != null){
              fecDesde = obj.getFecDesde() != null?obj.getFecDesde():UtilClass.getObtFecIni();
              fecHasta = obj.getFecHasta() != null?obj.getFecHasta():UtilClass.getObtFecInf();
            }
            
            System.out.println("FecDesde: " + fecDesde);
            System.out.println("FecHasta: " + fecHasta);

            if(obj.getCodSclaseeven()!= null && obj.getCodSclaseeven().length()> 0){
                criteria.add(Restrictions.eq("codSclaseeven",obj.getCodSclaseeven()).ignoreCase());
                flgFilter = 1;
            }

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

            if(obj.getIdStipodocu()!= null && obj.getIdStipodocu().intValue() > 0){
                criteria.add(Restrictions.eq("idStipodocu",obj.getNumDocu()));
                flgFilter = 1;
            }

            if(obj.getIdEstadocu() != null && obj.getIdEstadocu().intValue() > 0){
                criteria.add(Restrictions.eq("idEstadocu",obj.getNumDocu()));
                flgFilter = 1;
            }
            
            if(obj.getCodIdenClieprov()!= null && obj.getCodIdenClieprov().trim().length() > 0 ){
                criteria.add(Restrictions.eq("codIdenClieprov",obj.getCodIdenClieprov()));
                flgFilter = 1;
            }
            
            if(obj.getDesPersClieprov()!= null && obj.getDesPersClieprov().trim().length() > 0 ){
                criteria.add(Restrictions.like("desPersClieprov","%" + obj.getDesPersClieprov() + "%").ignoreCase());
                flgFilter = 1;
            }

            if(fecDesde != null){
                criteria.add(Restrictions.between("fecDesde",fecDesde, fecHasta));
                flgFilter = 1;
            }
            
            if(obj.getDesPersCreador()!= null && obj.getDesPersCreador().trim().length() > 0 ){                
                criteria.add(Restrictions.like("desPersCreador","%" + obj.getDesPersCreador() + "%").ignoreCase());
                flgFilter = 1;
            }

            if(flgFilter == 1){
                criteria.addOrder(Order.desc("codSerie")).addOrder(Order.desc("numDocu"));
                lstResult = criteria.list();    
            }

        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return lstResult;
    }
    
    
    public String getLastCodEstaDocu(Session session, BigDecimal id_docu){        
        
        String result = session.doReturningWork(new ReturningWork<String>() {
            @Override
            public String execute(Connection cnctn) throws SQLException {
                
                String codEstaDocu = "";
                Statement stmt = cnctn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COD_ESTA FROM SIC3DOCUESTA T0 JOIN VI_SICESTA T1 ON T0.ID_ESTADOCU = T1.ID_ESTA WHERE T0.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF AND T0.ID_DOCU = " + id_docu.toString());
                while(rs.next()){
                    codEstaDocu = rs.getString(1);
                }
                return codEstaDocu;
            }            
        });       
        
        return result;        
    }
}
