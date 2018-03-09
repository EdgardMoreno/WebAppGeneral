/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.util.beans.UtilClass;
import com.general.util.dao.DaoFuncionesUtil;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author emoreno
 */
public class DaoKardexImpl {
    


    /*METODOS*/    
    public String getKardexByNumPeri(Session session, Sic1idendocu sic1idendocu) throws Exception {
                
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
    
}
