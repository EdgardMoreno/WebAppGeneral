/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoReportImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.util.beans.Meta;
import com.general.util.beans.Reporte;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Edgard
 */
public class ReportServiceImpl {
    
    DaoReportImpl daoReportImpl;
    private Session session;
    
    public ReportServiceImpl(){        
        this.daoReportImpl = new DaoReportImpl();
        
    }
    
    /**
     * Metodo que obtiene el total de ventas en EFECTIVO/TARJETA que realizó el vendedor durante un periodo de tiempo      
     * @param numPeri recibe el mes en consulta
     * @param idPers recibe el identificador del vendedor
     * @return 
     * @throws CustomizerException
     */
    public Double getTotalSales(Integer numPeri, Integer idPers) throws CustomizerException {
        
       Double numTotalVentas = 0.0;
        try{            
            
            numTotalVentas = daoReportImpl.getTotalSales(numPeri, idPers);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return numTotalVentas;
        
    }
    
    /**
     * Metodo que obtiene el TOTAL DE VENTAS X LINEA DE PRODUCTOS
     * @param numPeri recibe el mes en consulta
     * @param idPers recibe el identificador del vendedor
     * @return
     * @throws java.sql.SQLException
     */
    public List<Meta> obtTotalVentaXLineaProductos(Integer numPeri, Integer idPers) throws Exception{
        return daoReportImpl.obtTotalVentaXLineaProductos(numPeri, idPers);
    }
    
    /**** REPORTE002: DETALLE DE OPERACIONES  *****/
    public List<Reporte> getReport002(String fecDesde, String fecHasta) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();            
            
            lstResult = daoReportImpl.getReport002(numFecDesde, numFecHasta);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
    }
    
    /**** REPORTE003: DETALLE DE OPERACIONES *****/
    public List<Reporte> getReport003(String fecDesde, String fecHasta) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();
            
            
            lstResult = daoReportImpl.getReport003(numFecDesde, numFecHasta);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        return lstResult;
    }
    
    /**** REPORTE004: DETALLE DE CAPITAL SEGUN STOCK PRODUCTOS  *****/
    public List<Reporte> getReport004(String fecDesde, String fecHasta) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();            
            
            lstResult = daoReportImpl.getReport004(numFecDesde, numFecHasta);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
    }
    
    /**** REPORTE005: ESTADO DE GANANCIAS Y PERDIDAS  *****/
    public List<Reporte> getReport005(String fecDesde, String fecHasta) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();
            
            
            lstResult = daoReportImpl.getReport005(numFecDesde, numFecHasta);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
    }
    
    
    /**** REPORTE SUNAT 001: REPORTE DE LIBRO DE COMPRAS  *****/
    public List<Reporte> getReportSunat001(String fecDesde, String fecHasta, Integer idPersEmpresa) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();
            
            
            lstResult = daoReportImpl.getReportSunat001(numFecDesde, numFecHasta, idPersEmpresa);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
    }
    
    /**** REPORTE SUNAT 001: REPORTE DE LIBRO DE VENTAS  *****/
    public List<Reporte> getReportSunat002(String fecDesde, String fecHasta, Integer idPersEmpresa) throws CustomizerException{
        
        List<Reporte> lstResult;
        Integer numFecDesde;
        Integer numFecHasta;
        try{
            
            if(fecDesde!= null && fecDesde.trim().length()>0){
                numFecDesde = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecDesde)).intValue();
            }else
                numFecDesde = UtilClass.getObtFecIni_YYYYMMDD();
            
            if(fecHasta!= null && fecHasta.trim().length()>0){
                numFecHasta = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(fecHasta)).intValue();
            }else
                numFecHasta = UtilClass.getObtFecInf_YYYYMMDD();
            
            
            lstResult = daoReportImpl.getReportSunat002(numFecDesde, numFecHasta, idPersEmpresa);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
    }
    
    /** REPORTE: DETALLE DE OPERACIONES POR PRODUCTO
     * @param objDocuprod: Recibe los parametros de Producto y Documento
     * @return 
     * @throws java.lang.Exception 
     */
    public List<Sic3docuprod> obtDetOperacionesXProductos(Sic3docuprod objDocuprod) throws Exception{
        
        List<Sic3docuprod> list = new ArrayList<>();
        
        try{
            DaoReportImpl objDao = new DaoReportImpl();
            
            list = objDao.obtDetOperacionesXProductos(objDocuprod);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        return list;
        
    }
}
