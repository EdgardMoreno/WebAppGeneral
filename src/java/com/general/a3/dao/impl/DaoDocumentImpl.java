/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.HibernateUtil;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docudocuId;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docuestaId;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.relaentity.Sic3docupersId;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
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
import com.general.util.dao.ConexionBD;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
    
    /** 
     * METODO UTILIZADO PARA CREA UN DOCUMENTO
     * @param session Indica la conexion
     * @param sic1idendocu Objeto que contiene todos los datos del documento
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws Exception
     */
    public String insert(Session session, Sic1idendocu sic1idendocu) throws Exception {
                
        try {
            
            String result = session.doReturningWork(new ReturningWork<String>() {
                @Override
                public String execute(Connection cnctn) throws SQLException {
                    
                    String strFecCreacion = null;
                    String strFecDesde = null;
                    String strFecHasta = null;
                    String valor = null;
                    Integer idModapago = null;

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
                        
                        System.out.println("DOCU: " + sic1docu.getIdModapago());
                        
                        if (sic1docu.getIdModapago() != null){ 
                            if( sic1docu.getIdModapago().intValue() == -1 ) {
                                    idModapago = null;
                            }else {
                                idModapago = sic1docu.getIdModapago().intValue();
                            }
                        }

                        System.out.println("FecCreacion: " + strFecCreacion);
                        System.out.println("Notas: " + sic1docu.getDesNotas());

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
                        sp.addParameter(new InParameter("X_ID_SUCURSAL",        Types.INTEGER, sic1docu.getIdSucursal()));

                        sp.addParameter(new InParameter("X_ID_MODAPAGO",        Types.INTEGER, idModapago ));
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
                        sp.addParameter(new InParameter("X_COD_TROLPERSEXTERNO",Types.VARCHAR, sic1docu.getCodTrolpersexterno()));

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
                        sp.addParameter(new InParameter("X_NUM_VOUCHER",        Types.VARCHAR, sic1docu.getNumVoucher()));

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
    
    /**
     * METODO QUE RELACIONA UN DOCUMENTO CON OTRO DOCUMENTO
     * @param session
     * @param sic3docudocu
     * @return
     * @throws Exception 
     */
    public String relateDocuDocu(Session session, Sic3docudocu sic3docudocu) throws Exception {        
        
        try {
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {
                    
                    /*Relacion: Reemplaza documento anulado*/
                    try{                        

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
                    }catch(NumberFormatException | SQLException ex){
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
    
    /**
     * METODO QUE CREA Y VERSIONA UN ESTADO AL DOCUMENTO
     * @param cnConexion
     * @param sic3docuesta
     * @throws Exception 
     */
    public void relateDocuEsta(Connection cnConexion, Sic3docuesta sic3docuesta) throws Exception {
        
        try {
            
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

            BigDecimal idTreladocuesta = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTRELA", "RELESTADODOCUMENTO");

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

            sp.ExecuteNonQuery(cnConexion);

            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0) {
                throw new SQLException((String) sp.getParameter("X_DES_ERROR"));
            }
        
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }        
        
    }
    
    /**
     * METODO QUE RELACIONA UNA LISTA DE PRODUCTOS CON EL DOCUMENTO
     * @param session
     * @param idDocu
     * @param lstSic3docuprod
     * @throws ValidationException
     * @throws CustomizerException 
     */
    public void relateDocuProd(Session session, BigDecimal idDocu, List<Sic3docuprod> lstSic3docuprod) throws ValidationException, CustomizerException {
              
        try {           
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {                    
                  
                    BigDecimal intIdTreladocu;                    
                    
                    try {
                        intIdTreladocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnctn, "VI_SICTRELA", "NOAPLICA");

                        for(Sic3docuprod sic3docuprod : lstSic3docuprod){
                            
                            String strFecDesde = null; 
                            String strFecHasta = null;
                            
                            /*Solo se graba los items que son nuevos*/
//                            if (sic3docuprod.getFlgNuevo()==false)
//                                continue;

                            /*CONVERSION DE FECHAS*/
                            if (sic3docuprod.getId().getFecDesde()!= null){
                                strFecDesde = UtilClass.convertDateToString(sic3docuprod.getId().getFecDesde());
                            }
                            if (sic3docuprod.getFecHasta()!= null){
                                strFecHasta = UtilClass.convertDateToString(sic3docuprod.getFecHasta());
                            }
                            /**/

                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICRELADOCUPROD");

                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, idDocu /*sic3docuprod.getId().getIdDocu()*/));
                            sp.addParameter(new InParameter("X_ID_PROD",        Types.INTEGER, sic3docuprod.getId().getIdProd()));                            
                            sp.addParameter(new InParameter("X_NUM_ITEM",        Types.INTEGER, sic3docuprod.getId().getNumItem()));
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
                                throw new SQLException(sp.getParameter("X_DES_ERROR").toString());
                            }  
                        }                    
                    }catch(Exception ex){
                        throw new HibernateException(ex.getMessage());
                    }
                }
            });            
        
        } catch (HibernateException ex) {
            if(ex.getMessage().toUpperCase().contains("VALIDACION"))
                throw new ValidationException(ex.getMessage());
            else
                throw new CustomizerException(ex.getMessage());
        }
    }  
    
    
    /**
     * PERMITE BORRAR LOS PRODUCTOS RELACIONADOS A UN DOCUMENTO
     * @param session 
     * @param idDocu Indica el identificador del documento
     * @throws Exception 
     */
    public void eliminarProductosXidDocu(Session session, BigDecimal idDocu) throws Exception{
        
        try{

            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {

                    try{

                         String sql = " DELETE FROM SIC3DOCUPROD WHERE ID_DOCU = " + idDocu;

                         CallableStatement statement = cnctn.prepareCall(sql);
                         statement.executeUpdate();                   

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
    }
    
    /**
     * METODO QUE PERMITE RELACIONAR UNA LISTA DE PERSONAS A UN DOCUMENTO
     * @param session
     * @param lstSic3docupers     
     * @throws Exception 
     */
    public void relateDocuPers(Session session, List<Sic3docupers> lstSic3docupers) throws Exception {
              
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
                    }catch(NumberFormatException | SQLException ex){
                        throw new HibernateException(ex.getMessage());
                    }
                }
            }); 
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    } 

    
    /**
     * METODO PARA OBTENER LOS PRODUCTOS DE UN DOCUMENTO
     * @param idDocu
     * @return
     * @throws Exception 
     */
    public List<Sic3docuprod> obtProductosXidDocu(BigDecimal idDocu) throws Exception{
        
        List<Sic3docuprod> list = new ArrayList();
        CallableStatement statement  = null;
        ResultSet rsConsulta = null;
        Connection cnConexion = null;
        
        try{            
        
            cnConexion = ConexionBD.obtConexion();
            
            String sql = " SELECT    T1.*\n" +
                            "       ,UPPER(V1.DES_STIPOPROD) AS DES_STIPOPROD" +
                            "       ,UPPER(V1.COD_STIPOPROD) AS COD_STIPOPROD" + 
                    
                            "      ,T0.ID_DOCU\n" +
                            "      ,T0.ID_TRELADOCU\n" +
                            "      ,T0.FEC_DESDE\n" +
                            "      ,T0.FEC_HASTA\n" +
                            "      ,NVL(T0.DES_NOTAS,'') AS DES_NOTAS\n" +
                            "      ,T0.NUM_VALOR\n" +
                            "      ,NVL(T0.NUM_MTODSCTO,0) AS NUM_MTODSCTO\n" +
                            "      ,T0.NUM_CANTIDAD\n" +
                            "      ,T0.NUM_ITEM\n" +
                            " FROM SIC3DOCUPROD T0\n" +
                            " JOIN SIC1PROD T1 ON T0.ID_PROD = T1.ID_PROD\n" +
                            " JOIN VI_SICSTIPOPROD V1 ON V1.ID_STIPOPROD = T1.ID_STIPOPROD\n" +
                            " WHERE T0.FEC_HASTA = TO_DATE('24001231','YYYYMMDD') AND T0.ID_DOCU = " + idDocu +
                            " ORDER BY T0.NUM_ITEM ASC ";
            
            statement = cnConexion.prepareCall(sql,
                                               ResultSet.TYPE_SCROLL_SENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY,
                                               ResultSet.CLOSE_CURSORS_AT_COMMIT);
            
            rsConsulta = statement.executeQuery();

            while(rsConsulta.next()){
                
                /*SIC1STIPOPROD*/
                Sic1general objStipoprod = new Sic1general();
                objStipoprod.setIdGeneral(rsConsulta.getBigDecimal("ID_STIPOPROD"));
                objStipoprod.setDesGeneral(rsConsulta.getString("DES_STIPOPROD"));
                objStipoprod.setCodValorgeneral(rsConsulta.getString("COD_STIPOPROD"));
                
                /*PRODUCTO*/
                Sic1prod objProd = new Sic1prod();
                objProd.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                objProd.setCodProd(rsConsulta.getString("COD_PROD"));
                objProd.setCodProdint(rsConsulta.getString("COD_PRODINT"));
                objProd.setDesProd(rsConsulta.getString("DES_PROD"));
                objProd.setDesProdcome(rsConsulta.getString("DES_PRODCOME"));
                objProd.setSic1stipoprod(objStipoprod);
                
                /*RELACION ID*/
                Sic3docuprodId id = new Sic3docuprodId();
                id.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                id.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                id.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                id.setNumItem(rsConsulta.getInt("NUM_ITEM"));                
                
                Sic3docuprod objDocuprod = new Sic3docuprod();
                objDocuprod.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                objDocuprod.setFecHasta(rsConsulta.getDate("FEC_HASTA"));
                objDocuprod.setIdTreladocu(rsConsulta.getBigDecimal("ID_TRELADOCU"));
                objDocuprod.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD"));
                objDocuprod.setNumMtodscto(rsConsulta.getBigDecimal("NUM_MTODSCTO").setScale(2,BigDecimal.ROUND_HALF_UP));
                objDocuprod.setNumValor(rsConsulta.getBigDecimal("NUM_VALOR").setScale(2,BigDecimal.ROUND_HALF_UP));                
                objDocuprod.setSic1prod(objProd);
                objDocuprod.setId(id);
                
                list.add(objDocuprod);
            }

        }catch(SQLException ex){
            throw new Exception(ex.getMessage());
        }finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(statement != null)
                statement.close();
            
            if(cnConexion != null)
                cnConexion.close();
        }
        

        return list;        
    }   
       
    
    /**
     * Se obtiene la lista de documentos secundarios que están relacionados al documento principal
     * @param idDocu Contiene el identificador del documento
     * @param cnConexion Contiene el identificador del documento
     * @throws Exception
     * @return 
     */
    public List<Sic3docudocu> obtDocusRelXidDocu(BigDecimal idDocu) throws Exception{
        
        List<Sic3docudocu> list = new ArrayList();
        CallableStatement statement  = null;
        ResultSet rsConsulta = null;        
        Connection cnConexion = null;
        
        try{            
            
            cnConexion = ConexionBD.obtConexion();
            
            String sql = "SELECT T0.* " +
                            " FROM SIC3DOCUDOCU T0 " +
                            " WHERE T0.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF AND T0.ID_DOCU = " + idDocu;                            
            
            statement = cnConexion.prepareCall(sql,
                                               ResultSet.TYPE_SCROLL_SENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY,
                                               ResultSet.CLOSE_CURSORS_AT_COMMIT);
            
            rsConsulta = statement.executeQuery();

            while(rsConsulta.next()){
                Sic3docudocuId id = new Sic3docudocuId();
                id.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                id.setIdDocurel(rsConsulta.getBigDecimal("ID_DOCUREL"));
                id.setFecDesde(rsConsulta.getDate("FEC_HASTA"));
                id.setIdTreladocu(rsConsulta.getBigDecimal("ID_TRELADOCU"));
                
                Sic3docudocu obj = new Sic3docudocu();
                obj.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                obj.setFecHasta(rsConsulta.getDate("FEC_HASTA"));
                obj.setId(id);
                
                list.add(obj);
            }

        }catch(SQLException ex){
            throw new Exception(ex.getMessage());
        }finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(statement != null)
                statement.close();
            
            if(cnConexion != null)
                cnConexion.close();
        }
        

        return list;        
    }
       
    
    /**
     * Se obtiene la lista de documentos principales que están relacionados al documento secundario     
     * @param idDocu Contiene el identificador del documento
     * @param cnConexion Contiene el identificador del documento
     * @throws Exception
     * @return 
     */
    public List<Sic3docudocu> obtDocusRelXidDocuRel(BigDecimal idDocu) throws Exception{
        
        List<Sic3docudocu> list     = new ArrayList();
        CallableStatement statement = null;
        ResultSet rsConsulta    = null;
        Connection cnConexion   = null;

        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            String sql = " SELECT T0.* " +
                         " FROM SIC3DOCUDOCU T0 " +                         
                         " WHERE T0.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF AND T0.ID_DOCUREL = " + idDocu;
            
            statement = cnConexion.prepareCall(sql,
                                               ResultSet.TYPE_SCROLL_SENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY,
                                               ResultSet.CLOSE_CURSORS_AT_COMMIT);
            
            rsConsulta = statement.executeQuery();

            while(rsConsulta.next()){
                Sic3docudocuId id = new Sic3docudocuId();
                id.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                id.setIdDocurel(rsConsulta.getBigDecimal("ID_DOCUREL"));
                id.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                id.setIdTreladocu(rsConsulta.getBigDecimal("ID_TRELADOCU"));
                
                Sic3docudocu obj = new Sic3docudocu();
                obj.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                obj.setFecHasta(rsConsulta.getDate("FEC_HASTA"));
                obj.setId(id);
                
                list.add(obj);
            }

        }catch(SQLException ex){
            throw new Exception(ex.getMessage());
        }finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(statement != null)
                statement.close();
            
            if(cnConexion != null)
                cnConexion.close();
        }        

        return list;        
    }
    
    /**
     * METODO QUE OBTIENE LOS DATOS DEL DOCUMENTO
     * @param session
     * @param obj
     * @return 
     */
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
    
//    public Sic1idendocu getByCodiden(Session session, String codIden) {
//        
//        Sic1idendocu sic1idendocu = null;        
//        Criteria criteria = session.createCriteria(Sic1idendocu.class);        
//        
//        if(codIden!= null){
//            criteria.add(Restrictions.eq("codIden",codIden));         
//            sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
//            if(sic1idendocu != null)
//                Hibernate.initialize(sic1idendocu.getSic1docu());
//        }
//
//        return sic1idendocu;        
//    }
    
    /**
     * METODO QUE VERIFICA SI EL DOCUMENTO YA EXISTE
     * @param session
     * @param codIden
     * @return 
     */
    public boolean verifyByCodiden(Session session, String codIden) {
        
        boolean exist = false;        
        Criteria criteria = session.createCriteria(Sic1idendocu.class);
        
        if(codIden!= null){
            criteria.add(Restrictions.eq("id.codIden",codIden).ignoreCase());
            Sic1idendocu sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
            if (sic1idendocu != null)
                exist = true;            
        }
        return exist;
    }
    
//    public Sic1idendocu getById(Session session, BigDecimal id_docu ) {
//        
//        Sic1idendocu sic1idendocu = null;        
//        Criteria criteria = session.createCriteria(Sic1idendocu.class);        
//        
//        if(id_docu!= null){
//            
//            criteria.add(Restrictions.eq("sic1docu.idDocu",id_docu));            
//            sic1idendocu = (Sic1idendocu)criteria.uniqueResult();
//            if(sic1idendocu != null)
//                Hibernate.initialize(sic1idendocu.getSic1docu());
//        }
//
//        return sic1idendocu;
//        
//    }
    
    
    /**
     * METODO QUE OBTIENE LOS DATOS DEL DOCUMENTO X EL IDDOCU
     * @param idDocu
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Sic1idendocu obtDocuXidDocu(BigDecimal idDocu) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;
        Sic1idendocu objIdenDocu    = null;
        Connection cnConexion       = null;

        System.out.println("idDocu: " + idDocu);

        try {
            
            cnConexion = ConexionBD.obtConexion();
            
            String sql = " SELECT    T1.* " +
                            "       ,IDENDOCU.COD_IDEN " +
                            "       ,T2.DES_STIPODOCU " +
                            "       ,T2.COD_STIPODOCU " +
                            "       ,T2.COD_SUNAT " +
                            "       ,UPPER(T3.DES_SCLASEEVEN) AS DES_SCLASEEVEN " +
                            "       ,T3.COD_SCLASEEVEN " +
                            "       ,DECODE(T1.COD_SERIE,NULL,TRIM(T1.NUM_DOCU),T1.COD_SERIE || ' - ' || TRIM(T1.NUM_DOCU)) AS NUM_DOCUUNIDO " +

                            "       ,UPPER(MODOPAGO.DES_GENERAL) AS DES_MODOPAGO " +
                            "       ,UPPER(TIPOTARJE.DES_GENERAL) AS DES_TIPOTARJETA " +

                            "       ,T4.FEC_DESDE AS FEC_DESDEESTADOCU" +
                            "       ,T4.ID_ESTADOCU " +
                            "       ,UPPER(V1.DES_ESTA) AS DES_ESTADOCU " +
                            "       ,V1.COD_ESTA AS COD_ESTADOCU" +
                            "       ,UPPER(T4.DES_NOTAS) AS DES_NOTASESTADOCU" +
                            
                            "       ,PERSEXTERNO.* " +
                            "       ,PERSREGISTRADOR.* " +
                    
                            "       ,SUCUR.ID_LUGAR AS ID_SUCURSAL" +
                            "       ,UPPER(SUCUR.DES_LUGAR) AS DES_SUCURSAL " +
                            "       ,UPPER(SUCUR.DES_DIRECCION) AS DES_DIRESUCURSAL " +
                    
                            " FROM SIC1DOCU T1 " +
                            " JOIN SIC1IDENDOCU IDENDOCU  ON IDENDOCU.ID_DOCU = T1.ID_DOCU " +
                            " JOIN SIC1STIPODOCU T2  ON T1.ID_STIPODOCU = T2.ID_STIPODOCU " +
                            " JOIN SIC1SCLASEEVEN T3 ON T3.ID_SCLASEEVEN = T1.ID_SCLASEEVEN " +
                            " JOIN SIC3DOCUESTA T4   ON T4.ID_DOCU = T1.ID_DOCU " +
                            "                           AND T4.FEC_HASTA = TO_DATE('24001231','YYYYMMDD') " +
                            " JOIN VI_SICESTA V1     ON V1.ID_ESTA = T4.ID_ESTADOCU " +
                    
                            " LEFT JOIN SIC1GENERAL MODOPAGO ON MODOPAGO.ID_GENERAL = T1.ID_MODAPAGO " +
                            " LEFT JOIN SIC1GENERAL TIPOTARJE ON TIPOTARJE.ID_GENERAL = T1.ID_TIPOTARJETA " +
                            
                            " LEFT JOIN SIC1LUGAR SUCUR ON SUCUR.ID_LUGAR = T1.ID_SUCURSAL " +
                    
                            " JOIN (SELECT  TMP1.ID_PERS                AS ID_PERS_EXTERNO" +
                            "              ,TMP1.DES_PERS               AS DES_PERS_EXTERNO" +
                            "              ,UPPER(TMPV2.DES_TIPOPERS)   AS DES_TIPOPERS_EXTERNO" +
                            "              ,UPPER(TMPV2.COD_TIPOPERS)   AS COD_TIPOPERS_EXTERNO" +
                            "              ,TMP2.ID_TIPOIDEN            AS ID_TIPOIDEN_EXTERNO" +
                            "              ,UPPER(TMPV1.DES_TIPOIDEN)   AS DES_TIPOIDEN_EXTERNO" +
                            "              ,UPPER(TMPV1.COD_TIPOIDEN)   AS COD_TIPOIDEN_EXTERNO" +
                            "              ,TMP2.COD_IDEN               AS COD_IDEN_EXTERNO" +
                            "      FROM SIC1PERS TMP1 " +
                            "      JOIN SIC1IDENPERS TMP2 ON TMP1.ID_PERS = TMP2.ID_PERS  " +
                            "                                AND TMP2.ID_TIPOIDEN IN (124,125) " +
                            "      JOIN VI_SICTIPOIDEN TMPV1 ON TMPV1.ID_TIPOIDEN = TMP2.ID_TIPOIDEN  " +
                            "      JOIN VI_SICTIPOPERS TMPV2 ON TMPV2.ID_TIPOPERS = TMP1.ID_TIPOPERS ) PERSEXTERNO ON PERSEXTERNO.ID_PERS_EXTERNO = T1.ID_PERSEXTERNO " +

                            " JOIN (SELECT  TMP1.ID_PERS                AS ID_PERS_REGISTRADOR" +
                            "              ,TMP1.DES_PERS               AS DES_PERS_REGISTRADOR" +
                            "              ,UPPER(TMPV2.COD_TIPOPERS)   AS COD_TIPOPERS_REGISTRADOR" +
                            "              ,UPPER(TMPV2.DES_TIPOPERS)   AS DES_TIPOPERS_REGISTRADOR" +
                            "              ,TMP2.ID_TIPOIDEN            AS ID_TIPOIDEN_REGISTRADOR" +
                            "              ,UPPER(TMPV1.DES_TIPOIDEN)   AS DES_TIPOIDEN_REGISTRADOR" +
                            "              ,UPPER(TMPV1.COD_TIPOIDEN)   AS COD_TIPOIDEN_REGISTRADOR" +
                            "              ,TMP2.COD_IDEN               AS COD_IDEN_REGISTRADOR" +
                            "      FROM SIC1PERS TMP1 " +
                            "      JOIN SIC1IDENPERS TMP2 ON TMP1.ID_PERS = TMP2.ID_PERS  " +
                            "                                AND TMP2.ID_TIPOIDEN IN (124,125) " +
                            "      JOIN VI_SICTIPOIDEN TMPV1 ON TMPV1.ID_TIPOIDEN = TMP2.ID_TIPOIDEN  " +
                            "      JOIN VI_SICTIPOPERS TMPV2 ON TMPV2.ID_TIPOPERS = TMP1.ID_TIPOPERS ) PERSREGISTRADOR ON PERSREGISTRADOR.ID_PERS_REGISTRADOR = T1.ID_PERS " +                            
                            " WHERE T1.ID_DOCU = " + idDocu;
            
            
            statement = cnConexion.prepareCall(sql,
                                                    ResultSet.TYPE_SCROLL_SENSITIVE,
                                                    ResultSet.CONCUR_READ_ONLY,
                                                    ResultSet.CLOSE_CURSORS_AT_COMMIT);            
            
            rsConsulta = statement.executeQuery();            
                        
            while(rsConsulta.next()){
                
                 /*ESTADO DEL DOCUMENTO*/
                Sic3docuestaId objEstaPK = new Sic3docuestaId();
                objEstaPK.setIdEstadocu(rsConsulta.getBigDecimal("ID_ESTADOCU"));
                objEstaPK.setFecDesde(rsConsulta.getTimestamp("FEC_DESDEESTADOCU"));                
                
                Sic3docuesta objEstadocu = new Sic3docuesta();
                objEstadocu.setCodEsta(rsConsulta.getString("COD_ESTADOCU"));
                objEstadocu.setDesEsta(rsConsulta.getString("DES_ESTADOCU"));
                objEstadocu.setDesNotas(rsConsulta.getString("DES_NOTASESTADOCU"));
                objEstadocu.setId(objEstaPK);

                /*Proveedor O Cliente*/
                Sic1pers objPersexterno = new Sic1pers();
                objPersexterno.setIdPers(rsConsulta.getBigDecimal("ID_PERS_EXTERNO"));
                objPersexterno.setDesPers(rsConsulta.getString("DES_PERS_EXTERNO"));
                objPersexterno.setCodIden(rsConsulta.getString("COD_IDEN_EXTERNO"));
                objPersexterno.setCodTipoiden(rsConsulta.getString("COD_TIPOIDEN_EXTERNO"));
                objPersexterno.setCodTipopers(rsConsulta.getString("COD_TIPOPERS_EXTERNO"));
                
                /*Registrador*/
                Sic1pers objPersRegistrador = new Sic1pers();
                objPersRegistrador.setIdPers(rsConsulta.getBigDecimal("ID_PERS_REGISTRADOR"));
                objPersRegistrador.setDesPers(rsConsulta.getString("DES_PERS_REGISTRADOR"));
                objPersRegistrador.setCodIden(rsConsulta.getString("COD_IDEN_REGISTRADOR"));
                objPersRegistrador.setCodTipoiden(rsConsulta.getString("COD_TIPOIDEN_REGISTRADOR"));
                objPersRegistrador.setCodTipopers(rsConsulta.getString("COD_TIPOPERS_REGISTRADOR"));
                
                /*Tipo de operacion*/
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setIdSclaseeven(rsConsulta.getBigDecimal("ID_SCLASEEVEN"));
                objSclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));
                objSclaseeven.setCodSclaseeven(rsConsulta.getString("COD_SCLASEEVEN"));
                
                /*Tipo de Comprobante*/
                Sic1stipodocu objStipodocu = new Sic1stipodocu();
                objStipodocu.setIdStipodocu(rsConsulta.getBigDecimal("ID_STIPODOCU"));
                objStipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                objStipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                objStipodocu.setCodSunat(rsConsulta.getString("COD_SUNAT"));
                
                /*Modo Pago*/
                Sic1general objModoPago = new Sic1general();
                objModoPago.setIdGeneral(rsConsulta.getBigDecimal("ID_MODAPAGO"));
                objModoPago.setDesGeneral(rsConsulta.getString("DES_MODOPAGO"));

                /*Tipo Tarjeta*/
                Sic1general objTipoTarjeta = new Sic1general();
                objTipoTarjeta.setIdGeneral(rsConsulta.getBigDecimal("ID_TIPOTARJETA"));
                objTipoTarjeta.setDesGeneral(rsConsulta.getString("DES_TIPOTARJETA"));
                
                /*Datos de Sucursal*/
                Sic1lugar objSucursal = new Sic1lugar();
                objSucursal.setIdLugar(rsConsulta.getBigDecimal("ID_SUCURSAL"));
                objSucursal.setDesLugar(rsConsulta.getString("DES_SUCURSAL"));
                objSucursal.setDesDireccion(rsConsulta.getString("DES_DIRESUCURSAL"));

                /*Documento*/
                Sic1docu objDocu = new Sic1docu();
                objDocu.setFecCreacion(rsConsulta.getTimestamp("FEC_CREACION"));
                objDocu.setIdTrolpers(rsConsulta.getBigDecimal("ID_TROLPERS"));
                objDocu.setIdStipodocu(rsConsulta.getBigDecimal("ID_STIPODOCU"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                objDocu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                objDocu.setIdPers(rsConsulta.getBigDecimal("ID_PERS_REGISTRADOR"));
                objDocu.setIdModapago(rsConsulta.getBigDecimal("ID_MODAPAGO"));
                objDocu.setIdTipotarjeta(rsConsulta.getBigDecimal("ID_TIPOTARJETA"));
                objDocu.setNumVoucher(rsConsulta.getString("NUM_VOUCHER"));
                objDocu.setFlgDeclaradosunat(rsConsulta.getInt("FLG_DECLARADOSUNAT"));
                objDocu.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                objDocu.setSic1lSucursal(objSucursal);
                
                if(rsConsulta.getBigDecimal("NUM_MTOTARJETA") == null)
                    objDocu.setNumMtotarjeta(new BigDecimal(BigInteger.ZERO));
                else
                    objDocu.setNumMtotarjeta(rsConsulta.getBigDecimal("NUM_MTOTARJETA").setScale(2,BigDecimal.ROUND_HALF_UP));
                
                if(rsConsulta.getBigDecimal("NUM_MTOCOMI") == null)
                    objDocu.setNumMtocomi(new BigDecimal(BigInteger.ZERO));
                else
                    objDocu.setNumMtocomi(rsConsulta.getBigDecimal("NUM_MTOCOMI").setScale(2,BigDecimal.ROUND_HALF_UP));
                
                if(rsConsulta.getBigDecimal("NUM_MTOEFECTIVO") == null)
                    objDocu.setNumMtoefectivo(new BigDecimal(BigInteger.ZERO));
                else
                    objDocu.setNumMtoefectivo(rsConsulta.getBigDecimal("NUM_MTOEFECTIVO").setScale(2,BigDecimal.ROUND_HALF_UP));
                
                if(rsConsulta.getBigDecimal("NUM_MTODSCTO") == null)
                    objDocu.setNumMtodscto(new BigDecimal(BigInteger.ZERO));
                else
                    objDocu.setNumMtodscto(rsConsulta.getBigDecimal("NUM_MTODSCTO").setScale(2,BigDecimal.ROUND_HALF_UP));
                
                if(rsConsulta.getBigDecimal("NUM_MTOVUELTO") == null)
                    objDocu.setNumMtovuelto(new BigDecimal(BigInteger.ZERO));
                else
                    objDocu.setNumMtovuelto(rsConsulta.getBigDecimal("NUM_MTOVUELTO").setScale(2,BigDecimal.ROUND_HALF_UP));
                
                objDocu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                objDocu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                objDocu.setNumDocuunido(rsConsulta.getString("NUM_DOCUUNIDO"));
                objDocu.setNumSubtotal(rsConsulta.getBigDecimal("NUM_SUBTOTAL"));
                objDocu.setNumIgv(rsConsulta.getBigDecimal("NUM_IGV"));
                objDocu.setIdSclaseeven(rsConsulta.getBigDecimal("ID_SCLASEEVEN"));
                objDocu.setIdPersexterno(rsConsulta.getBigDecimal("ID_PERSEXTERNO"));
                objDocu.setFlgPrecsinIGV(rsConsulta.getInt("FLG_PRECSINIGV"));                

                Double mtoTotal = objDocu.getNumSubtotal().doubleValue() + objDocu.getNumIgv().doubleValue() -  objDocu.getNumMtodscto().doubleValue();
                objDocu.setNumMtoTotal(new BigDecimal(mtoTotal).setScale(2, BigDecimal.ROUND_HALF_UP));

                objDocu.setObjFormaPago(objModoPago);
                objDocu.setObjTipoTarjeta(objTipoTarjeta);
                objDocu.setSic3docuesta(objEstadocu);
                objDocu.setSic1stipodocu(objStipodocu);
                objDocu.setSic1sclaseeven(objSclaseeven);
                objDocu.setSic1persexterno(objPersexterno);
                objDocu.setSic1persregistrador(objPersRegistrador);
                
                objIdenDocu = new Sic1idendocu();
                objIdenDocu.setSic1docu(objDocu);
                objIdenDocu.setCodIden(rsConsulta.getString("COD_IDEN"));

            }
            
        } catch (SQLException e){
            throw new SQLException("obtDocumentoXIdDocu()-FE:" + e.getMessage());
        } catch (Exception e){
            throw new Exception("obtDocumentoXIdDocu()-FE:" + e.getMessage());
        } finally{
            
            if(statement != null){
                statement.close();
            }
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion != null)
                cnConexion.close();
            
        }
        
        return objIdenDocu;
    }
    
    public ViSicdocu obtViSicdocu(BigDecimal idDocu) throws Exception {
        
        ViSicdocu obj;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
            Criteria criteria = session.createCriteria(ViSicdocu.class);
            criteria.add(Restrictions.eq("idDocu",idDocu));
            
            obj = (ViSicdocu)criteria.uniqueResult();
            
        }catch(HibernateException ex){
             throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        
        return obj;
    }
    
    
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws Exception {
                
        int flgFilter = 0;
        Date fecDesde = null, fecHasta = null;
        List<ViSicdocu> lstResult = new ArrayList();
        Session session = null;
        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(ViSicdocu.class);
        
            System.out.println("FecDesde: " + obj.getFecDesde());
            System.out.println("FecHasta: " + obj.getFecHasta());

            if(obj.getFecDesde() != null || obj.getFecHasta() != null){
              fecDesde = obj.getFecDesde() != null?obj.getFecDesde():UtilClass.getObtFecIni();
              fecHasta = obj.getFecHasta() != null?obj.getFecHasta():UtilClass.getObtFecInf();
            }
            
            System.out.println("FecDesde: " + fecDesde);
            System.out.println("FecHasta: " + fecHasta);

            if(obj.getCodClaseeven()!= null && obj.getCodClaseeven().length()> 0){
                criteria.add(Restrictions.eq("codClaseeven",obj.getCodClaseeven()).ignoreCase());
                flgFilter = 1;
            }
            
            if(obj.getCodSclaseeven()!= null && obj.getCodSclaseeven().length()> 0){
                criteria.add(Restrictions.eq("codSclaseeven",obj.getCodSclaseeven()).ignoreCase());
                flgFilter = 1;
            }

            if(obj.getIdDocu() != null && obj.getIdDocu().intValue() > 0){
                criteria.add(Restrictions.eq("idDocu",obj.getIdDocu()));
                flgFilter = 1;
            }
            if(obj.getCodIden() != null && obj.getCodIden().trim().length() > 0 ){
                criteria.add(Restrictions.eq("codIden",obj.getCodIden().trim()));
                flgFilter = 1;
            }
            
            if(obj.getNumDocuunido()!= null && obj.getNumDocuunido().trim().length() > 0 ){
                criteria.add(Restrictions.like("numDocuunido" ,"%" + obj.getNumDocuunido().trim() + "%").ignoreCase());
                flgFilter = 1;
            }
            
            if(obj.getCodSerie()!= null && obj.getCodSerie().trim().length() > 0 ){
                criteria.add(Restrictions.eq("codSerie",obj.getCodSerie().trim().toUpperCase()));
                flgFilter = 1;
            }
            if(obj.getNumDocu() != null && obj.getNumDocu().intValue() > 0){
                criteria.add(Restrictions.eq("numDocu",obj.getNumDocu()));
                flgFilter = 1;
            }

            if(obj.getIdStipodocu()!= null && obj.getIdStipodocu().intValue() > 0){
                criteria.add(Restrictions.eq("idStipodocu",obj.getIdStipodocu()));
                flgFilter = 1;
            }

            if(obj.getIdEstadocu() != null && obj.getIdEstadocu().intValue() > 0){
                criteria.add(Restrictions.eq("idEstadocu",obj.getIdEstadocu()));
                flgFilter = 1;
            }
            
            if(obj.getCodEsta()!= null && obj.getCodEsta().length()> 0){
                criteria.add(Restrictions.eq("codEsta",obj.getCodEsta()).ignoreCase());
                flgFilter = 1;
            }
            
            if(obj.getCodIdenClieprov()!= null && obj.getCodIdenClieprov().trim().length() > 0 ){
                criteria.add(Restrictions.eq("codIdenClieprov",obj.getCodIdenClieprov().trim()));
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
            
            if(obj.getFecEnvisunat() != null){
                criteria.add(Restrictions.eq("fecEnvisunat",obj.getFecEnvisunat()));
                flgFilter = 1;
            }
            
            if(obj.getDesPersCreador()!= null && obj.getDesPersCreador().trim().length() > 0 ){                
                criteria.add(Restrictions.like("desPersCreador","%" + obj.getDesPersCreador().trim() + "%").ignoreCase());
                flgFilter = 1;
            }
            
            if(obj.getFlgDeclaradosunat()!= null && obj.getFlgDeclaradosunat() != -1 ){                
                criteria.add(Restrictions.eq("flgDeclaradosunat",obj.getFlgDeclaradosunat()));
                flgFilter = 1;
            }

            if(flgFilter == 1){
                criteria.addOrder(Order.desc("fecDesde")).addOrder(Order.desc("codSerie")).addOrder(Order.desc("numDocu"));
                lstResult = criteria.list();    
            }

        }catch(ParseException | HibernateException ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }

        return lstResult;
    }
    
    /**
     * METODO QUE OBTIENE EL ULTIMO ESTADO DEL DOCUMENTO
     * @param session
     * @param id_docu
     * @return 
     */
    public String getLastCodEstaDocu(Session session, BigDecimal id_docu){        
        
        String result = session.doReturningWork(new ReturningWork<String>() {
            @Override
            public String execute(Connection cnctn) throws SQLException {
                
                String codEstaDocu = "";
                Statement stmt = cnctn.createStatement();
                ResultSet rsConsulta = stmt.executeQuery("SELECT COD_ESTA FROM SIC3DOCUESTA T0 JOIN VI_SICESTA T1 ON T0.ID_ESTADOCU = T1.ID_ESTA WHERE T0.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF AND T0.ID_DOCU = " + id_docu.toString());
                while(rsConsulta.next()){
                    codEstaDocu = rsConsulta.getString(1);
                }
                
                if(rsConsulta != null)
                    rsConsulta.close();
                
                return codEstaDocu;
            }            
        });       
        
        return result;        
    }
    
    /**
     * METODO QUE OBTIENE EL ULTIMO ESTADO DEL DOCUMENTO
     * @param cnConexion
     * @param id_docu
     * @throws Exception 
     * @return 
     */
    public String getLastCodEstaDocu(Connection cnConexion,  BigDecimal id_docu) throws Exception{        

        CallableStatement statement  = null;
        ResultSet rsConsulta = null;
        String codEstaDocu = "";        
        
        try{            
            
            String sql = " SELECT T1.COD_ESTA " +
                         " FROM SIC3DOCUESTA T0 " +
                         " JOIN VI_SICESTA T1 ON T0.ID_ESTADOCU = T1.ID_ESTA " +
                         " WHERE T0.FEC_HASTA = PKG_SICCONSGENERAL.FNC_SICOBTFECINF AND T0.ID_DOCU = " + id_docu.toString();
            
            statement = cnConexion.prepareCall(sql,
                                               ResultSet.TYPE_SCROLL_SENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY,
                                               ResultSet.CLOSE_CURSORS_AT_COMMIT);
            
            rsConsulta = statement.executeQuery();

            while(rsConsulta.next()){
                codEstaDocu = rsConsulta.getString("COD_ESTA");
            }

        }catch(SQLException ex){
            throw new Exception(ex.getMessage());
        }finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(statement != null)
                statement.close();
        }
              
        return codEstaDocu;
        
    }
    
    
    /*
     * @param cnConexion
     * @param idDocu     
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<Sic3docupers> obtPersRelXidDocu(BigDecimal idDocu) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;
        List<Sic3docupers> lstResultado = new ArrayList<>();
        Connection cnConexion       = null;

        System.out.println("idDocu: " + idDocu);

        try {
            
            cnConexion = ConexionBD.obtConexion();
            
            String sql = " SELECT   DOCUPERS.ID_TRELADOCU\n"    +
                            "        ,V2.COD_TRELADOCU\n"       +
                            "        ,DOCUPERS.FEC_HASTA\n"     +
                            "        ,DOCUPERS.DES_NOTAS\n"     +
                            "        ,DOCUPERS.FEC_DESDE\n"     +
                            "        ,DOCUPERS.ID_TROLPERS\n"   +
                            "        ,V1.COD_TROLPERS\n"        +
                            "        ,DOCUPERS.ID_DOCU\n"       +
                            "        ,DOCUPERS.ID_PERS\n"       +
                            " FROM SIC3DOCUPERS DOCUPERS \n"    +
                            " JOIN VI_SICTROLPERS V1 ON DOCUPERS.ID_TROLPERS = V1.ID_TROLPERS\n"    +
                            " JOIN VI_SICTRELADOCU V2 ON DOCUPERS.ID_TRELADOCU = V2.ID_TRELADOCU\n" +
                            " WHERE DOCUPERS.ID_DOCU " + idDocu;            
            
            statement = cnConexion.prepareCall(sql,
                                                    ResultSet.TYPE_SCROLL_SENSITIVE,
                                                    ResultSet.CONCUR_READ_ONLY,
                                                    ResultSet.CLOSE_CURSORS_AT_COMMIT);            
            
            rsConsulta = statement.executeQuery();
                        
            while(rsConsulta.next()){
                
                 /*ESTADO DEL DOCUMENTO*/
                Sic3docupersId objDocupersPK = new Sic3docupersId();
                objDocupersPK.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                objDocupersPK.setIdPers(rsConsulta.getBigDecimal("ID_PERS"));
                objDocupersPK.setIdTreladocu(rsConsulta.getBigDecimal("ID_TRELADOCU"));
                objDocupersPK.setCodTreladocu(rsConsulta.getString("COD_TRELADOCU"));
                objDocupersPK.setIdTrolpers(rsConsulta.getBigDecimal("ID_TROLPERS"));
                objDocupersPK.setCodTrolpers(rsConsulta.getString("COD_TROLPERS"));
                objDocupersPK.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                
                Sic3docupers objDocupers = new Sic3docupers();
                objDocupers.setFecHasta(rsConsulta.getDate("FEC_HASTA"));
                objDocupers.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                
                lstResultado.add(objDocupers);
            }
            
        } catch (SQLException e){
            throw new SQLException("obtPersRelXidDocu()-FE:" + e.getMessage());
        } catch (Exception e){
            throw new Exception("obtPersRelXidDocu()-FE:" + e.getMessage());
        } finally{
            
            if(statement != null){
                statement.close();
            }
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion != null)
                cnConexion.close();
            
        }
        
        return lstResultado;
    }
}
