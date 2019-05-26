/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.entity.Sic1docukardex;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
import com.general.util.beans.Reporte;
import com.general.util.beans.UtilClass;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.io.Serializable;
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
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;

/**
 *
 * @author emoreno
 */
public class DaoDocuKardexImpl implements Serializable{
    
    private Session session;

    /*METODOS*/
    
    /*Graba el inventario realizado*/
    public void insert(Session session, List<Sic1docukardex> list, BigDecimal idDocu) throws Exception {
              
        try {           
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {                   
                    
                    try {                        

                        for(Sic1docukardex obj : list){

                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICCREADOCUKARDEX");

                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, idDocu));
                            sp.addParameter(new InParameter("X_NUM_PERI",       Types.INTEGER, obj.getId().getNumPeri()));
                            sp.addParameter(new InParameter("X_ID_ESTAB",       Types.INTEGER, new BigDecimal(1)/*obj.getId().getIdEstab()*/));
                            sp.addParameter(new InParameter("X_ID_PROD",        Types.INTEGER, obj.getId().getIdProd()));
                            
                            //Persona Juridica
                            sp.addParameter(new InParameter("X_FEC_CREACION",   Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_NUM_CANTINI",    Types.NUMERIC, obj.getNumCantini()));
                            sp.addParameter(new InParameter("X_NUM_CANTINGR",   Types.NUMERIC, obj.getNumCantingr()));
                            sp.addParameter(new InParameter("X_NUM_CANTSALI",   Types.NUMERIC, obj.getNumCantsali()));
                            sp.addParameter(new InParameter("X_NUM_CANTSTOCK",  Types.NUMERIC, obj.getNumCantstock()));
                            sp.addParameter(new InParameter("X_ID_ESTA",        Types.INTEGER, null));
                            sp.addParameter(new InParameter("X_ID_SUCURSAL",    Types.INTEGER, obj.getIdSucursal()));

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
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }  
    
    
    /*Se obtiene el ultimo periodo activo del inventario*/
    public int getKardexLastPeriActi(Session session) throws Exception{

        int result = 0;
        try{
        
            CallableStatement call = ((SessionImpl)session).connection().prepareCall("{ ? = call FNC_SICOBTKARDEXPERIACTIVO() }");
            call.registerOutParameter( 1, Types.INTEGER );
            call.execute();

            result =  call.getInt(1);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
            
        return result;        
    }
    
    /*Se obtiene el kardex del periodo en consulta*/
    public List<Sic1docukardex> getKardexByNumPeri(Connection conexion, Integer numPeri ) throws Exception {

        List<Sic1docukardex> list;
        
        try {
            
            ResultSet rsConsulta;                        
            StoredProcedure sp = new StoredProcedure("PRC_SICOBTPLANTKARDEX");

            sp.addParameter(new InParameter("X_NUM_PERI", Types.INTEGER, numPeri));
            sp.addParameter(new OutParameter("X_CURSOR",  OracleTypes.CURSOR));

            rsConsulta = sp.ExecuteResultCursor(conexion, 2);        
            
            Sic1docukardex obj;
            list = new ArrayList<>();
            
            while (rsConsulta.next()) {
                
                Sic1general objStipoprod = new Sic1general();
                objStipoprod.setDesGeneral(rsConsulta.getString("DES_STIPOPROD"));
                
                Sic1prod prod = new Sic1prod();
                prod.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                prod.setCodProd(rsConsulta.getString("COD_PROD"));
                prod.setDesProd(rsConsulta.getString("DES_PROD"));
                prod.setSic1stipoprod(objStipoprod);                
                
                obj = new Sic1docukardex();
                obj.setSic1prod(prod);                
                obj.setNumCantini(rsConsulta.getBigDecimal("NUM_CANTINI"));
                obj.setNumCantingr(rsConsulta.getBigDecimal("NUN_CANTINGR"));
                obj.setNumCantsali(rsConsulta.getBigDecimal("NUM_CANTSALI"));
                obj.setNumCantstock(rsConsulta.getBigDecimal("NUM_CANTSTOCK"));
                
                list.add(obj);
            }
                   
            
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return list;
    }
    
    /** REPORTE KARDEX RESUMEN: Lista todas la entradas y salidas de los productos
     * @param objDocuprod: Recibe los parametros de Producto y Documento
     * @return 
     * @throws java.lang.Exception 
     */
    public List<Reporte> obtKardexResumen(Sic3docuprod objDocuprod) throws Exception{
        
        List<Reporte> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;
        
        BigDecimal fecDesde = null;
        BigDecimal fecHasta = null;
        String strCodProd = null;

        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            
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
            
            Sp = new StoredProcedure("PKG_SICREPORTES.PRC_SICRPTKARDEXRESUMEN");
            Sp.addParameter(new InParameter("X_ID_PROD",    Types.INTEGER, objDocuprod.getSic1prod().getIdProd()));
            Sp.addParameter(new InParameter("X_COD_PROD",   Types.VARCHAR, strCodProd));
            Sp.addParameter(new InParameter("X_FEC_DESDE",  Types.NUMERIC, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA",  Types.NUMERIC, fecHasta));
            
            Sp.addParameter(new OutParameter("X_CURSOR",    OracleTypes.CURSOR));            
            Sp.addParameter(new OutParameter("X_ID_ERROR",  Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            
            rsConsulta = Sp.ExecuteResultCursor(((SessionImpl)session).connection(),5);            
                        
            while(rsConsulta.next()){                
                               
                Sic1general objStipoprod = new Sic1general();
                objStipoprod.setDesGeneral(rsConsulta.getString("DES_STIPOPROD"));
                
                /*DATOS DE PRODUCTO*/
                Sic1prod objProd = new Sic1prod();
                objProd.setIdProd(rsConsulta.getBigDecimal("ID_PROD"));
                objProd.setCodProd(rsConsulta.getString("COD_PROD"));
                objProd.setDesProd(rsConsulta.getString("DES_PROD"));                
                objProd.setNumCantstock(rsConsulta.getBigDecimal("NUM_STOCK_SISTEMA"));
                objProd.setSic1stipoprod(objStipoprod);
                
                /*COMPRA*/
                Sic3docuprodId detCompraPK = new Sic3docuprodId();
                detCompraPK.setFecDesde(rsConsulta.getDate("ULT_OPER_COMPRA"));
                
                Sic3docuprod detCompra = new Sic3docuprod();
                detCompra.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD_COMP_EMR"));
                detCompra.setNumCantidadEMZA(rsConsulta.getBigDecimal("NUM_CANTIDAD_COMP_EMZA"));
                detCompra.setNumValor(rsConsulta.getBigDecimal("NUM_COSTO"));
                detCompra.setSic1prod(objProd);
                detCompra.setId(detCompraPK);
                
                
                /*VENTA*/
                Sic3docuprodId detVentaPK = new Sic3docuprodId();
                detVentaPK.setFecDesde(rsConsulta.getDate("ULT_OPER_VENTA"));
                
                Sic3docuprod detVenta = new Sic3docuprod();
                detVenta.setNumCantidad(rsConsulta.getBigDecimal("NUM_CANTIDAD_VENT"));
                detVenta.setId(detVentaPK);                
                
                /*FINAL*/
                Reporte objReporte = new Reporte();
                objReporte.setDetPurchase(detCompra);
                objReporte.setDetSale(detVenta);
                
                list.add(objReporte);
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        }finally{
            if(session != null)
                session.close();
            if(rsConsulta != null)
                rsConsulta.close();
        }
        
        return list;        
    }
    
}
