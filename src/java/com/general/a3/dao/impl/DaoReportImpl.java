/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.util.beans.Constantes;
import com.general.util.beans.Meta;
import com.general.util.beans.Reporte;
import com.general.util.beans.UtilClass;
import com.general.util.dao.ConexionBD;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Edgard
 */
public class DaoReportImpl {
    
    private final static Logger log = LoggerFactory.getLogger(DaoDocumentImpl.class);  
    private Session session;
    
    /**
     * Metodo que obtiene el total de ventas en EFECTIVO/TARJETA que realizó el vendedor durante un periodo de tiempo 
     * @param numPeri recibe el mes en consulta
     * @param idPers recibe el identificador del vendedor
     * @return 
     */
    public Double getTotalSales(Integer numPeri, Integer idPers) throws Exception {

        Double numTotaVentas = 0.0;
        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            
            String sql =                     
                " SELECT NVL(SUM(S1.NUM_SUBTOTAL + S1.NUM_IGV),0) AS NUM_TOTALVENTAS                \n" +
                "       FROM SIC1DOCU S1\n" +
                "       JOIN SIC1STIPODOCU T6 ON T6.ID_STIPODOCU = S1.ID_STIPODOCU\n" +
                "       JOIN SIC3DOCUESTA RELESTA ON RELESTA.ID_DOCU = S1.ID_DOCU  \n" +
                "                                    AND TO_CHAR(RELESTA.FEC_HASTA,'DD/MM/YYYY') = '31/12/2400'\n" +
                "       JOIN VI_SICESTA ESTA ON ESTA.ID_ESTA = RELESTA.ID_ESTADOCU\n" +
                "                               AND ((ESTA.COD_ESTA = 'VI_SICESTAFINALIZADO' AND T6.COD_STIPODOCU IN ('VI_SICFACTURA','VI_SICBOLETA','VI_SICSINDOCU'))\n" +
                "                                   OR (T6.COD_STIPODOCU = 'VI_SICNOTAVENTA' AND ESTA.COD_ESTA = 'VI_SICESTAPORRECOGER')\n" +
                "                                   )  " +
                "   WHERE S1.ID_SCLASEEVEN = 2 " +
                "         AND S1.ID_PERS = :id_pers " +
                "         AND TO_NUMBER(TO_CHAR(S1.FEC_DESDE,'YYYYMM')) = :num_peri ";

            System.out.println("ID_PERS: "  + idPers);
            System.out.println("NUM_PERI: " + numPeri);        

            Query  query = session.createSQLQuery(sql)                 
                    .setParameter("id_pers", new BigDecimal(idPers))
                    .setParameter("num_peri", new BigDecimal(numPeri));

            List<Object[]> rows = query.list();
            
            for(Object row : rows){                    
                numTotaVentas = Double.valueOf(row.toString());
            }

        }catch(NumberFormatException | HibernateException ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();            
        }
        
        return numTotaVentas;
    }
    
    /**
     * Metodo que obtiene el TOTAL DE VENTAS X LINEA DE PRODUCTOS
     * @param numPeri recibe el mes en consulta
     * @param idPers recibe el identificador del vendedor
     * @return
     * @throws java.sql.SQLException
     */
    public List<Meta> obtTotalVentaXLineaProductos(Integer numPeri, Integer idPers) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        List<Meta> lstResult = new ArrayList<>();
        
        try {
            
            cnConexion = ConexionBD.obtConexion();
            String strFiltro = "";
            
            if(idPers  > 0 ){
                strFiltro +=  " AND S1.ID_PERS = " + idPers;
            }
            
            String sql = "SELECT " +
                "                  S1.ID_PERS\n" +
                "                 ,USU.COD_USUARIO\n" +
                "                 ,CASE WHEN V2.COD_STIPOPROD =  'VI_SICVINILCORTE' THEN 'VI_SICVINILCORTE' ELSE 'VI_SICPAPELTAPIZ' END AS COD_STIPOPROD\n" +
                "                 ,CASE WHEN V2.COD_STIPOPROD =  'VI_SICVINILCORTE' THEN 'VINIL CORTE' ELSE 'PAPEL TAPIZ' END AS DES_STIPOPROD\n" +
                "                 ,SUM( DECODE(SCLASEEVEN.COD_SCLASEEVEN,'VI_SICSCLASEEVENNOTACREDITO',-1,1) * (T1.NUM_VALOR  - NVL(T1.NUM_MTODSCTO,0)) * T1.NUM_CANTIDAD) AS NUM_TOTALVENTA " +
                "             FROM SIC3DOCUPROD T1\n" +
                "             JOIN SIC1DOCU S1 ON T1.ID_DOCU = S1.ID_DOCU  \n" +
                "             JOIN SIC1PROD T3 ON T3.ID_PROD = T1.ID_PROD  \n" +
                "             JOIN VI_SICSTIPOPROD V2 ON V2.ID_STIPOPROD = T3.ID_STIPOPROD " +
                "             JOIN SIC1STIPODOCU T6 ON T6.ID_STIPODOCU = S1.ID_STIPODOCU " +
                "             JOIN SIC3DOCUESTA RELESTA ON RELESTA.ID_DOCU = S1.ID_DOCU " +
                "                                         AND TO_CHAR(RELESTA.FEC_HASTA,'DD/MM/YYYY') = '31/12/2400'  " +
                "             JOIN VI_SICESTA ESTA ON ESTA.ID_ESTA = RELESTA.ID_ESTADOCU " +
                "                                    AND ((ESTA.COD_ESTA IN ('VI_SICESTAFINALIZADO','VI_SICESTAPORRECOGER') AND T6.COD_STIPODOCU IN ('VI_SICFACTURA','VI_SICBOLETA','VI_SICSINDOCU'))) " +
                "             JOIN SIC1USUARIO USU ON USU.ID_USUARIO = S1.ID_PERS " +
                "             JOIN SIC1SCLASEEVEN SCLASEEVEN ON SCLASEEVEN.ID_SCLASEEVEN = S1.ID_SCLASEEVEN" +
                "             WHERE SCLASEEVEN.COD_SCLASEEVEN IN ('VI_SICSCLASEEVENVENTA','VI_SICSCLASEEVENNOTACREDITO') " +                           
                "                   AND TO_NUMBER(TO_CHAR(S1.FEC_DESDE,'YYYYMM')) = " + numPeri + strFiltro +
                " GROUP BY   CASE WHEN COD_STIPOPROD =  'VI_SICVINILCORTE' THEN 'VI_SICVINILCORTE' ELSE 'VI_SICPAPELTAPIZ' END " +
                "           ,CASE WHEN V2.COD_STIPOPROD =  'VI_SICVINILCORTE' THEN 'VINIL CORTE' ELSE 'PAPEL TAPIZ' END  " +
                "                     ,USU.COD_USUARIO " +
                "                     ,S1.ID_PERS";
                        
            statement = cnConexion.prepareCall( sql,
                                                ResultSet.TYPE_SCROLL_SENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY,
                                                ResultSet.CLOSE_CURSORS_AT_COMMIT );
            
            rsConsulta = statement.executeQuery();            
                        
            while(rsConsulta.next()){

                Meta obj = new Meta();
                
                String codStipoprod = rsConsulta.getString("COD_STIPOPROD");
                BigDecimal numTotalVentasMes = rsConsulta.getBigDecimal("NUM_TOTALVENTA");
                
                BigDecimal numPorcAlcanzado;
                if(codStipoprod.equals("VI_SICVINILCORTE")){
                    obj.setDesMeta("% Logrado Meta(Vinil)");
                    numPorcAlcanzado = new BigDecimal((numTotalVentasMes.doubleValue()/Constantes.CONS_METAMESTOTALVENTAVINIL) * 100).setScale(2,BigDecimal.ROUND_HALF_UP);
                    obj.setNumTotalventameta(new BigDecimal(Constantes.CONS_METAMESTOTALVENTAVINIL));
                }
                else{
                    obj.setDesMeta("% Logrado Meta(Papel)");
                    numPorcAlcanzado = new BigDecimal((numTotalVentasMes.doubleValue()/Constantes.CONS_METAMESTOTALVENTAPAPEL) * 100).setScale(2,BigDecimal.ROUND_HALF_UP);
                    obj.setNumTotalventameta(new BigDecimal(Constantes.CONS_METAMESTOTALVENTAPAPEL));
                }
                
                Sic1pers objPers = new Sic1pers();
                objPers.setIdPers(rsConsulta.getBigDecimal("ID_PERS"));
                objPers.setDesPers(rsConsulta.getString("COD_USUARIO"));
                
                obj.setCodStipoprod(codStipoprod);
                obj.setDesStipoprod(rsConsulta.getString("DES_STIPOPROD"));
                obj.setNumTotalventalogrado(numTotalVentasMes);
                obj.setNumPorclogrado(numPorcAlcanzado);
                obj.setSic1pers(objPers);
                
                lstResult.add(obj);                
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(statement != null)
                statement.close();
            if(rsConsulta != null)
                rsConsulta.close();
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return lstResult;
    }
    
    /**** REPORTE: GANANCIA POR PRODUCTO VENDID
     * @param fecDesde*
     * @param fecHasta*
     * @throws java.lang.Exception***/
    public List<Reporte> getReport002(Integer fecDesde, Integer fecHasta) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== obtReport002 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPT002");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));            
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),3);
            
            
            Reporte objReport;
            while(rsConsulta.next()){
                
                
                //Detalle de Venta
                Sic1docu docuVenta = new Sic1docu();
                docuVenta.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU_VENTA"));
                docuVenta.setFecDesde(rsConsulta.getDate("FEC_VENTA"));
                docuVenta.setCodSerie(rsConsulta.getString("COD_SERIE"));
                docuVenta.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                
                Sic1prod prodVenta = new Sic1prod();
                prodVenta.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                prodVenta.setCodProd(rsConsulta.getString("COD_PROD"));
                prodVenta.setDesProd(rsConsulta.getString("DES_PROD"));
                
                Sic3docuprod detVenta = new Sic3docuprod();
                detVenta.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD_VENDIDA"));
                detVenta.setNumValor(rsConsulta.getBigDecimal("NUM_PRECIO_X_UNIDAD"));
                detVenta.setSic1docu(docuVenta);
                detVenta.setSic1prod(prodVenta);
                
                //Detalle de compra
                Sic1docu docuCompra = new Sic1docu();
                docuCompra.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU_COMPRA"));                
                
                Sic3docuprod detCompra = new Sic3docuprod();
                detCompra.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD_VENDIDA"));
                detCompra.setNumValor(rsConsulta.getBigDecimal("NUM_COSTO_X_UNIDAD"));               
                detCompra.setSic1docu(docuVenta);
                detCompra.setSic1prod(prodVenta);
                
                objReport = new Reporte();
                objReport.setDetPurchase(detCompra);
                objReport.setDetSale(detVenta);
                
                list.add(objReport);
                
            }
        
        } catch (Exception e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
            
        }
        
        return list;        
    }
    
    /** REPORTE: DETALLE DE OPERACIONES     
     * @param fecDesde Indica la fecha de inicio
     * @param fecHasta Indica la fecha final
     * @return 
     * @throws java.lang.Exception
     */
    public List<Reporte> getReport003(Integer fecDesde, Integer fecHasta) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== obtReport003 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPT003");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));            
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),3);
            
            
            Reporte objReport;
            while(rsConsulta.next()){                
                
                Sic1pers objPers = new Sic1pers();
                objPers.setDesPers(rsConsulta.getString("DES_PERS"));
                
                //SUBTIPODOCUMENTO
                Sic1stipodocu sic1stipodocu = new Sic1stipodocu();
                sic1stipodocu.setIdStipodocu(rsConsulta.getBigDecimal("ID_STIPODOCU"));
                sic1stipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                sic1stipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                
                //SUBSLCASEDOCUMENTO
                Sic1sclaseeven sic1sclaseeven = new Sic1sclaseeven();
                sic1sclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));
                
                //FORMA DE PAGO
                Sic1general objFormaPago = new Sic1general();
                objFormaPago.setIdGeneral(rsConsulta.getBigDecimal("ID_FORMAPAGO"));
                objFormaPago.setDesGeneral(rsConsulta.getString("DES_FORMAPAGO"));
                objFormaPago.setCodValorgeneral(rsConsulta.getString("COD_FORMAPAGO"));
                
                Sic1pers objPersResponsable = new Sic1pers();
                objPersResponsable.setDesPers(rsConsulta.getString("COD_USUARIO"));
                
                //DATOS DEL DOCUMENTO RELACIONADO
                Sic1docu objDocuRela = new Sic1docu();
                objDocuRela.setIdDocu(rsConsulta.getBigDecimal("ID_DOCUREL"));
                objDocuRela.setNumDocuunido(rsConsulta.getString("NUM_DOCUUNIDORELA"));                
                Sic3docudocu objRelaDocuDocu = new Sic3docudocu();
                objRelaDocuDocu.setSic1docurel(objDocuRela);
                
                //DETALLE DE LA OPERACION
                Sic1docu docuOperacion = new Sic1docu();
                docuOperacion.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                docuOperacion.setFecCreacion(rsConsulta.getDate("FEC_CREACION"));
                docuOperacion.setFecDesde(rsConsulta.getDate("FECHA_OPERACION"));
                docuOperacion.setCodSerie(rsConsulta.getString("COD_SERIE"));
                docuOperacion.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));                
                docuOperacion.setNumMtodscto(rsConsulta.getBigDecimal("NUM_MTODSCTO"));
                docuOperacion.setNumMtoefectivo(rsConsulta.getBigDecimal("NUM_MTOEFECTIVO"));
                docuOperacion.setNumMtotarjeta(rsConsulta.getBigDecimal("NUM_MTOTARJETA"));
                docuOperacion.setNumSubtotal(rsConsulta.getBigDecimal("NUM_SUBTOTAL"));
                docuOperacion.setNumIgv(rsConsulta.getBigDecimal("NUM_IGV"));
                docuOperacion.setDesEstadocu(rsConsulta.getString("DES_ESTADO"));
                docuOperacion.setDesNotas(rsConsulta.getString("DES_NOTAS"));
                docuOperacion.setNumVoucher(rsConsulta.getString("NUM_VOUCHER"));
                docuOperacion.setSic1stipodocu(sic1stipodocu);
                docuOperacion.setSic1sclaseeven(sic1sclaseeven);
                docuOperacion.setSic1persexterno(objPers);
                docuOperacion.setSic1persregistrador(objPersResponsable);
                docuOperacion.setObjFormaPago(objFormaPago);
                docuOperacion.setSic3docudocu(objRelaDocuDocu);
                
                Sic3docuprod detOperacion = new Sic3docuprod();              
                detOperacion.setSic1docu(docuOperacion);
                
                objReport = new Reporte();
                objReport.setDetOperacion(detOperacion);
                
                list.add(objReport);
                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session!=null)
                session.close();
            
        }
        
        return list;        
    }
    
    /**** REPORTE: DETALLE CAPITAL SEGUN STOCK PRODUCTOS  *****/
    public List<Reporte> getReport004(Integer fecDesde, Integer fecHasta) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== obtReport004 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPT004");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));            
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),3);
            
            
            Reporte objReport;
            while(rsConsulta.next()){               
                                
                Sic1general objStipoprod = new Sic1general();
                objStipoprod.setDesGeneral(rsConsulta.getString("DES_STIPOPROD"));
               
                Sic1prod objProd = new Sic1prod();
                objProd.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                objProd.setCodProd(rsConsulta.getString("COD_PROD"));
                objProd.setDesProd(rsConsulta.getString("DES_PROD"));                
                objProd.setNumCantstock(rsConsulta.getBigDecimal("NUM_CANTSTOCK"));
                objProd.setNumPrecio(rsConsulta.getBigDecimal("NUM_PRECIO"));
                objProd.setSic1stipoprod(objStipoprod);
                
                
                Sic1docu docu = new Sic1docu();
                docu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCUKARDEX"));

                //--ULTIMO INVENTARIO: COSTO DEL PRODUCTO SEGUN EL ULTIMO INVENTARIO APROBADO
                Sic3docuprodId id = new Sic3docuprodId();
                id.setFecDesde(rsConsulta.getDate("FEC_INVENTARIO"));
                
                Sic3docuprod detCostoUltInventario = new Sic3docuprod();                
                detCostoUltInventario.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTSTOCKKARDEX"));
                detCostoUltInventario.setNumValor(rsConsulta.getBigDecimal("NUM_COSTOKARDEX_X_UNIDAD"));
                detCostoUltInventario.setSic1docu(docu);
                detCostoUltInventario.setSic1prod(objProd);
                detCostoUltInventario.setId(id);
                
                //--STOCK ACTUAL(COSTO): COSTO DEL PRODUCTO SEGUN EL STOCK A LA FECHA
                Sic3docuprod detCostoActual = new Sic3docuprod();
                detCostoActual.setNumValor(rsConsulta.getBigDecimal("NUM_COSTO_X_UNIDAD"));
                
                //FIN
                objReport = new Reporte();
                objReport.setDetOperacion(detCostoUltInventario);
                objReport.setDetPurchase(detCostoActual);
                
                list.add(objReport);
                
            }
        
        } catch (Exception e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
            
        }
        
        return list;        
    } 
    
    /**** REPORTE: ESTADO DE GANANCIAS Y PERDIDAS  *****/
    public List<Reporte> getReport005(Integer fecDesde, Integer fecHasta) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== obtReport005 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPT005");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));            
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),3);            
            
            Reporte objReport;
            while(rsConsulta.next()){
                
                objReport = new Reporte();
                objReport.setNumItem(rsConsulta.getInt("NUM_ORDEN"));
                objReport.setDesTipo(rsConsulta.getString("DES_TIPO"));
                objReport.setCodTipo(rsConsulta.getString("COD_TIPO"));
                objReport.setDesItem(rsConsulta.getString("DES_ITEM"));
                objReport.setNumMtototal(rsConsulta.getBigDecimal("NUM_MTOTOTAL"));
                
                list.add(objReport);                
            }        
        } catch (Exception e){
            throw new Exception(e.getMessage());            
        } finally{
           
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
           
        }
        
        return list;        
    }
    
    
    
    /**** REPORTE: LIBRO DE COMPRAS  *****/
    public List<Reporte> getReportSunat001( Integer fecDesde, 
                                            Integer fecHasta,
                                            Integer idPersEmpresa) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== getReportSunat001 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPTSUNAT001");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));
            Sp.addParameter(new InParameter("X_ID_PERSEMPRESA", Types.INTEGER, idPersEmpresa));
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),4);
            
            
            Reporte objReport;
            while(rsConsulta.next()){
                
                Sic1pers pers = new Sic1pers();
                pers.setCodTipoiden(rsConsulta.getString("COD_TIPOIDEN"));
                pers.setCodIden(rsConsulta.getString("NUM_DOCUIDEN"));
                pers.setDesPers(rsConsulta.getString("DES_NOMBPROVEEDOR"));
                
                Sic1stipodocu sic1stipodocu = new Sic1stipodocu();
                sic1stipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
               
                Sic1docu docu = new Sic1docu();                
                docu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                docu.setFecCreacion(rsConsulta.getTimestamp("FEC_CREACION"));
                docu.setFecDesde(rsConsulta.getDate("FEC_OPERACION"));                
                docu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                docu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                docu.setNumSubtotal(rsConsulta.getBigDecimal("MTO_SUBTOTAL"));
                docu.setNumIgv(rsConsulta.getBigDecimal("MTO_IGV"));
                docu.setNumMtoTotal(rsConsulta.getBigDecimal("MTO_TOTAL"));
                docu.setSic1persexterno(pers);
                docu.setSic1stipodocu(sic1stipodocu);
                
                Sic3docuprod detOperacion = new Sic3docuprod();
                detOperacion.setSic1docu(docu);
                
                //FIN
                objReport = new Reporte();                
                objReport.setDetOperacion(detOperacion);
                
                list.add(objReport);
                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
            
        }
        
        return list;        
    } 
    
    
    /**** REPORTE: LIBRO DE VENTAS  *****/
    public List<Reporte> getReportSunat002( Integer fecDesde, 
                                            Integer fecHasta, 
                                            Integer idPersEmpresa) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== getReportSunat002 ==================");
            log.debug("fecDesde:" + fecDesde);
            log.debug("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPTSUNAT002");
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));
            Sp.addParameter(new InParameter("X_ID_PERSEMPRESA", Types.INTEGER, idPersEmpresa));
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),4);
            
            
            Reporte objReport;
            while(rsConsulta.next()){
                
                Sic1pers pers = new Sic1pers();
                pers.setCodTipoiden(rsConsulta.getString("COD_TIPOIDEN"));
                pers.setCodIden(rsConsulta.getString("NUM_DOCUIDEN"));
                pers.setDesPers(rsConsulta.getString("DES_NOMBPROVEEDOR"));
                
                Sic1stipodocu sic1stipodocu = new Sic1stipodocu();
                sic1stipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
               
                Sic1docu docu = new Sic1docu();                
                docu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                docu.setFecCreacion(rsConsulta.getTimestamp("FEC_CREACION"));
                docu.setFecDesde(rsConsulta.getDate("FEC_OPERACION"));                
                docu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                docu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                docu.setNumSubtotal(rsConsulta.getBigDecimal("MTO_SUBTOTAL"));
                docu.setNumIgv(rsConsulta.getBigDecimal("MTO_IGV"));
                docu.setNumMtoTotal(rsConsulta.getBigDecimal("MTO_TOTAL"));
                docu.setSic1persexterno(pers);
                docu.setSic1stipodocu(sic1stipodocu);
                
                Sic3docuprod detOperacion = new Sic3docuprod();
                detOperacion.setSic1docu(docu);
                
                //FIN
                objReport = new Reporte();
                objReport.setDetOperacion(detOperacion);
                
                list.add(objReport);
                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        } finally{
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
            
        }
        
        return list;        
    } 
    
    /******************************************************************************************************************/
    /************ REPORTE DE PRODUCTOS ********************************************************************************/
    /******************************************************************************************************************/
    
    /** REPORTE: DETALLE DE OPERACIONES POR PRODUCTO
     * @param objDocuprod: Recibe los parametros de Producto y Documento
     * @return 
     * @throws java.lang.Exception 
     */
    public List<Sic3docuprod> obtDetOperacionesXProductos(Sic3docuprod objDocuprod) throws Exception{
        
        List<Sic3docuprod> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;
        
        BigDecimal fecDesde = null;
        BigDecimal fecHasta = null;
        String strCodProd = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            log.debug("=============== obtDetOperacionesXProductos ==================");
            
            if(objDocuprod.getSic1docu() != null && 
                    objDocuprod.getSic1docu().getFecDesde() != null){
                fecDesde = UtilClass.convertDateToNumber(objDocuprod.getSic1docu().getFecDesde());
            }
            
            if(objDocuprod.getSic1docu() != null && 
                    objDocuprod.getSic1docu().getFecHasta()!= null){
                fecDesde = UtilClass.convertDateToNumber(objDocuprod.getSic1docu().getFecHasta());
            }
            
            if(objDocuprod.getSic1prod() != null && objDocuprod.getSic1prod().getCodProd() !=null)
                strCodProd = objDocuprod.getSic1prod().getCodProd().trim();
            
            Sp = new StoredProcedure("PKG_SICCONSPROD.PRC_SICOBTDETPROD");
            Sp.addParameter(new InParameter("X_ID_PROD",    Types.INTEGER, objDocuprod.getSic1prod().getIdProd()));
            Sp.addParameter(new InParameter("X_COD_PROD",   Types.VARCHAR, strCodProd));
            Sp.addParameter(new InParameter("X_ID_SCLASEEVEN", Types.INTEGER, objDocuprod.getSic1docu().getIdSclaseeven()));
            Sp.addParameter(new InParameter("X_FEC_DESDE",  Types.NUMERIC, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA",  Types.NUMERIC, fecHasta));
            Sp.addParameter(new InParameter("X_ID_DOCU",    Types.INTEGER, objDocuprod.getSic1docu().getIdDocu()));
            Sp.addParameter(new InParameter("X_COD_SERIE",  Types.VARCHAR, objDocuprod.getSic1docu().getCodSerie()));
            Sp.addParameter(new InParameter("X_NUM_DOCU",   Types.INTEGER, objDocuprod.getSic1docu().getNumDocu()));
            Sp.addParameter(new InParameter("X_ID_ESTADOCU",Types.INTEGER, null));
            
            Sp.addParameter(new OutParameter("X_CURSOR",    OracleTypes.CURSOR));            
            Sp.addParameter(new OutParameter("X_ID_ERROR",  Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),10);            
                        
            while(rsConsulta.next()){
                
                //Proveedor O Cliente
                Sic1pers objPers = new Sic1pers();
                objPers.setDesPers(rsConsulta.getString("DES_PERS"));
                
                //TIPO DE OPERACION
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setIdClaseeven(rsConsulta.getBigDecimal("ID_SCLASEEVEN"));
                objSclaseeven.setCodSclaseeven(rsConsulta.getString("COD_SCLASEEVEN"));
                objSclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));

                /*ESTADO DE LA OPERACION*/
                Sic3docuesta objDocuesta = new Sic3docuesta();
                objDocuesta.setDesEsta(rsConsulta.getString("DES_ESTA"));
                
                /*TIPO DE DOCUMENTO*/
                Sic1stipodocu sic1stipodocu = new Sic1stipodocu();
                sic1stipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                
                /*Documentos Relacionados*/
                Sic1docu objDocuRel = new Sic1docu();
                objDocuRel.setIdDocu(rsConsulta.getBigDecimal("ID_DOCUREL"));
                objDocuRel.setNumDocuunido(rsConsulta.getString("NUM_DOCUUNIDO"));
                        
                Sic3docudocu objDocusRela = new Sic3docudocu();
                objDocusRela.setSic1docurel(objDocuRel);
                
                //Cabecera de la operacion(Compra o Venta)
                Sic1docu objDocu = new Sic1docu();
                objDocu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_OPERACION"));
                objDocu.setSic1stipodocu(sic1stipodocu);
                objDocu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                objDocu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                objDocu.setSic1persexterno(objPers);
                objDocu.setSic1sclaseeven(objSclaseeven);
                objDocu.setSic3docuesta(objDocuesta);
                objDocu.setSic3docudocu(objDocusRela);
                
                /*DATOS DE PRODUCTO*/
                Sic1prod objProd = new Sic1prod();
                objProd.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                objProd.setCodProd(rsConsulta.getString("COD_PROD"));
                objProd.setDesProd(rsConsulta.getString("DES_PROD"));
                objProd.setNumCantstock(rsConsulta.getBigDecimal("NUM_CANTSTOCK"));
                               
                
                
                /**/
                Sic3docuprod detOperacion = new Sic3docuprod();
                detOperacion.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD"));
                detOperacion.setNumValor(rsConsulta.getBigDecimal("NUM_VALOR"));
                detOperacion.setSic1docu(objDocu);
                detOperacion.setSic1prod(objProd);
                
                list.add(detOperacion);                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        }finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(session != null)
                session.close();
            
        }
        
        return list;        
    }
}
