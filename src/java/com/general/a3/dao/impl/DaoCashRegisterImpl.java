/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.entity.Sic1usuario;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate.views.ViSiccuaddiario;
import com.general.util.beans.UtilClass;
import com.general.util.dao.ConexionBD;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author emoreno
 */
public class DaoCashRegisterImpl implements Serializable{
    
    
    public DaoCashRegisterImpl(){
        
    }
    
    
    /*METODOS*/
    
    public void insert(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.save(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
     public void update(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.update(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public Sic4cuaddiario getById(Session session, Sic4cuaddiarioId id) throws Exception {
        
        Sic4cuaddiario obj = null;
        try {        
            obj = (Sic4cuaddiario)session.get(Sic4cuaddiario.class,id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }    
    
    
    public List<ViSiccuaddiario> listViSiccuaddiario(Session session, ViSiccuaddiario obj) throws Exception {
                
        int flgFilter = 0;
        Date fecDesde = null, fecHasta = null;
        List<ViSiccuaddiario> lstResult = new ArrayList();
        try{
            
            Criteria criteria = session.createCriteria(ViSiccuaddiario.class);
        
            System.out.println("FecDesde: " + obj.getFecApertura());
            System.out.println("FecHasta: " + obj.getFecCierre());

            if(obj.getFecApertura() != null || obj.getFecCierre() != null){
              fecDesde = obj.getFecApertura() != null?UtilClass.convertStringToDate(obj.getFecApertura()):UtilClass.getObtFecIni();
              fecHasta = obj.getFecCierre() != null?UtilClass.convertStringToDate(obj.getFecCierre()):UtilClass.getObtFecInf();
            }
            
            System.out.println("FecDesde: " + fecDesde);
            System.out.println("FecHasta: " + fecHasta);
            
            if(fecDesde != null){
                criteria.add(Restrictions.between("id.numPeri",UtilClass.convertDateToNumber(fecDesde) , UtilClass.convertDateToNumber(fecHasta)));
                flgFilter = 1;
            }
            criteria.addOrder(Order.desc("id.numPeri"));
            lstResult = criteria.list();      

        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return lstResult;
    }
    
    /*Metodo que obtiene el total de ventas en EFECTIVO/TARJETA que realizó el vendedor durante el dia. */
    public Sic4cuaddiario getSummaryOrders(Session session, Sic4cuaddiario obj) throws Exception {
                            
        String sql =                     
            " SELECT  NVL(SUM(CASE WHEN S1.ID_SCLASEEVEN = 2 AND GEN.COD_VALORGENERAL NOT IN ('VI_SICTRANSFE','VI_SICDEPOSITO') THEN S1.NUM_MTOEFECTIVO - NVL(S1.NUM_MTOVUELTO,0) ELSE 0 END),0) AS NUM_MTOEFECTIVO " +
            "        ,NVL(SUM(CASE WHEN NVL(SCLASE.ID_CLASEEVEN,0) <> 46126 AND GEN.COD_VALORGENERAL = 'VI_SICTARJDEBITO' THEN TO_NUMBER(S1.NUM_MTOTARJETA) ELSE 0 END),0) AS NUM_MTOTARJDEBI  " +
            "        ,NVL(SUM(CASE WHEN NVL(SCLASE.ID_CLASEEVEN,0) <> 46126 AND GEN.COD_VALORGENERAL = 'VI_SICTARJCREDITO' THEN TO_NUMBER(S1.NUM_MTOTARJETA) ELSE 0 END),0) AS NUM_MTOTARJCRED " +
            "        ,NVL(SUM(CASE WHEN SCLASE.ID_CLASEEVEN = 46126 AND S1.ID_MODAPAGO = 46103 THEN NVL(S1.NUM_MTOEFECTIVO,0) - NVL(S1.NUM_MTOVUELTO,0)ELSE 0 END),0) AS NUM_MTOGASTOEFECTIVO " +
            "        ,NVL(SUM(CASE WHEN S1.ID_SCLASEEVEN = 2 AND S1.ID_MODAPAGO IN (46101,46102) THEN NVL(S1.NUM_MTOEFECTIVO,0)  ELSE 0 END),0) AS NUM_MTOTOTALTRANDEPO " +
            " FROM SIC1DOCU S1 " +
            " JOIN SIC1SCLASEEVEN SCLASE ON SCLASE.ID_SCLASEEVEN = S1.ID_SCLASEEVEN " +
            " LEFT JOIN SIC1GENERAL GEN ON GEN.ID_GENERAL = S1.ID_MODAPAGO " +
            " JOIN SIC3DOCUESTA RELESTA ON RELESTA.ID_DOCU = S1.ID_DOCU " +
            "                              AND TO_CHAR(RELESTA.FEC_HASTA,'DD/MM/YYYY') = '31/12/2400' " +
            "                              AND RELESTA.ID_ESTADOCU != PKG_SICCONSGENERAL.FNC_SICOBTIDGEN('VI_SICESTA','VI_SICESTAANULADO') " +
            " WHERE S1.ID_SCLASEEVEN NOT IN (SELECT ID_SCLASEEVEN FROM SIC1SCLASEEVEN WHERE COD_SCLASEEVEN IN ('VI_SICSCLASEEVENORDENCOMPRA','VI_SICSCLASEEVENGASTEXTERNO')) " +
            "       AND S1.ID_PERS = :id_pers " + 
            "       AND TO_CHAR(S1.FEC_DESDE,'YYYYMMDD') = :num_peri" ;

        System.out.println("ID_PERS: "  + obj.getId().getIdPers());
        System.out.println("NUM_PERI: " + obj.getId().getNumPeri());

        Query  query = session.createSQLQuery(sql)                 
                .setParameter("id_pers", obj.getId().getIdPers())
                .setParameter("num_peri", obj.getId().getNumPeri().toString());

        List<Object[]> rows = query.list();

        for(Object[] row : rows){

            System.out.println("NUM_MTOEFECTIVO: " + row[0].toString());
            System.out.println("NUM_MTOTARJDEBI: " + row[1].toString());
            System.out.println("NUM_MTOTARJCRED: " + row[2].toString());

            obj.setNumEfectTotalVentaSiste(new BigDecimal(row[0].toString()).setScale(2, BigDecimal.ROUND_HALF_UP));

            double numTotalTarj = Double.valueOf(row[1].toString()) + Double.valueOf(row[2].toString());
            System.out.println("numTotalTarj:" + numTotalTarj);

            obj.setNumTarjeTotalSiste(new BigDecimal(numTotalTarj).setScale(2, BigDecimal.ROUND_HALF_UP));
            obj.setNumEfectTotalGastoSiste(new BigDecimal(row[3].toString()).setScale(2, BigDecimal.ROUND_HALF_UP));            
            obj.setNumTranDepoTotalSiste(new BigDecimal(row[4].toString()).setScale(2, BigDecimal.ROUND_HALF_UP));

        }
        
        return obj;
    }
    
    
    /* Lista los cierres diarios registrados.
     * @param fecDesde
     * @param fecHasta     
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<Sic4cuaddiario> listarCierresDiarios(String fecDesde, String fecHasta) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;        
        Connection cnConexion       = null;
        List<Sic4cuaddiario> lstResult = new ArrayList<>();
        Integer numFecDesde;
        Integer numFecHasta;
        
        try {
            
            if( fecDesde != null && fecDesde.length() > 0)
                numFecDesde = UtilClass.convertSringToNumber_YYYYMMDD(fecDesde);
            else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if( fecHasta != null && fecHasta.length() > 0)
                numFecHasta = UtilClass.convertSringToNumber_YYYYMMDD(fecHasta);
            else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();
            
            cnConexion = ConexionBD.obtConexion();
            
            String sql = " SELECT " +
                            "        T1.COD_USUARIO " +
                            "       ,UPPER(T2.DES_LUGAR) AS DES_LUGAR " +
                            "       ,UPPER(V1.DES_ESTA) AS DES_ESTA " +
                            "       ,UPPER(V1.COD_ESTA) AS COD_ESTA " +
                            "       ,T0.* " +
                            " FROM SIC4CUADDIARIO T0 " +
                            " JOIN SIC1USUARIO T1 ON T0.ID_PERS = T1.ID_USUARIO " +
                            " JOIN VI_SICESTA V1 ON V1.ID_ESTA = T0.ID_ESTA " +
                            " JOIN SIC1LUGAR T2 ON T2.ID_LUGAR = T0.ID_SUCURSAL " +
                            " WHERE NUM_PERI BETWEEN " + numFecDesde + " AND " + numFecHasta + 
                            " ORDER BY NUM_PERI DESC ";
            
            statement = cnConexion.prepareCall(sql,
                                                    ResultSet.TYPE_SCROLL_SENSITIVE,
                                                    ResultSet.CONCUR_READ_ONLY,
                                                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            
            rsConsulta = statement.executeQuery();
                                    
            while(rsConsulta.next()){
                
                 /*VENDEDOR*/
                Sic1usuario objUsuario = new Sic1usuario();
                objUsuario.setIdUsuario(rsConsulta.getBigDecimal("ID_PERS"));
                objUsuario.setCodUsuario(rsConsulta.getString("COD_USUARIO"));
                
                /*SUCURSAL*/
                Sic1lugar objLugar = new Sic1lugar();
                objLugar.setIdLugar(rsConsulta.getBigDecimal("ID_SUCURSAL"));
                objLugar.setDesLugar(rsConsulta.getString("DES_LUGAR"));
                
                /*ESTADO*/
                Sic3docuesta sic3docuesta = new Sic3docuesta();                
                sic3docuesta.setDesEsta(rsConsulta.getString("DES_ESTA"));
                sic3docuesta.setCodEsta(rsConsulta.getString("COD_ESTA"));

                /*CAJA*/
                Sic4cuaddiarioId id = new Sic4cuaddiarioId();
                id.setIdPers(rsConsulta.getBigDecimal("ID_PERS"));
                id.setNumPeri(rsConsulta.getBigDecimal("NUM_PERI"));                
                               
                
                Sic4cuaddiario obj = new Sic4cuaddiario();                
                obj.setFecApertura(rsConsulta.getDate("FEC_APERTURA"));                
                obj.setFecCierre(rsConsulta.getDate("FEC_CIERRE"));
                obj.setNumEfectDenomTotal(rsConsulta.getBigDecimal("NUM_EFECT_DENOM_TOTAL"));
                obj.setNumEfectGastoTotal(rsConsulta.getBigDecimal("NUM_EFECT_GASTO_TOTAL"));
                obj.setNumEfectApertCaja(rsConsulta.getBigDecimal("NUM_EFECT_APERT_CAJA"));
                obj.setNumEfectTotal(rsConsulta.getBigDecimal("NUM_EFECT_TOTAL"));
                obj.setNumEfectTotalVentaSiste(rsConsulta.getBigDecimal("NUM_EFECT_TOTAL_VENTA_SISTE"));
                obj.setNumEfectTotalGastoSiste(rsConsulta.getBigDecimal("NUM_EFECT_TOTAL_GASTO_SISTE"));
                obj.setNumEfectTotalSistema(rsConsulta.getBigDecimal("NUM_EFECT_TOTAL_SISTEMA"));
                obj.setNumEfectSobraFalta(rsConsulta.getBigDecimal("NUM_EFECT_SOBRA_FALTA"));
                obj.setNumTarjeCrediTotal(rsConsulta.getBigDecimal("NUM_TARJE_CREDI_TOTAL"));
                obj.setNumTarjeDebitTotal(rsConsulta.getBigDecimal("NUM_TARJE_DEBIT_TOTAL"));
                obj.setNumTarjeTotal(rsConsulta.getBigDecimal("NUM_TARJE_TOTAL"));
                obj.setNumTarjeTotalSiste(rsConsulta.getBigDecimal("NUM_TARJE_TOTAL_SISTE"));
                obj.setNumTarjeSobraFalta(rsConsulta.getBigDecimal("NUM_TARJE_SOBRA_FALTA"));
                obj.setNumTarjeGastoTotal(rsConsulta.getBigDecimal("NUM_TARJE_GASTO_TOTAL"));
                obj.setNumTarjeGastoTotalSiste(rsConsulta.getBigDecimal("NUM_TARJE_GASTO_TOTAL_SISTE"));
                
                obj.setNumTranDepoTotal(rsConsulta.getBigDecimal("NUM_TRANDEPO_TOTAL"));
                obj.setNumTranDepoTotalSiste(rsConsulta.getBigDecimal("NUM_TRANDEPO_TOTAL_SISTE"));
                
                obj.setNumTotalVenta(rsConsulta.getBigDecimal("NUM_TOTAL_VENTA"));                
                
                obj.setIdSucursal(rsConsulta.getInt("ID_SUCURSAL"));
                obj.setId(id);
                obj.setSic1lugar(objLugar);
                obj.setSic1usuario(objUsuario);
                obj.setSic3docuesta(sic3docuesta);
                
                lstResult.add(obj);

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
        
        return lstResult;
    }
    
    
}
