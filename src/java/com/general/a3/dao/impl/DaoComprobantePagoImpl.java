/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate1.Sic1docufacturadorsunat;
import com.general.util.beans.ComprobantePago;
import com.general.util.beans.ComprobantePagoDet;
import com.general.util.beans.ComunicacionBaja;
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

/**
 *
 * @author emoreno
 */
public class DaoComprobantePagoImpl {
        
    /** REPORTE: DETALLE DE OPERACIONES DE LOS COMPROBANTES DE PAGOS EMITIDOS 
     * @param idDocu Indica el identificador del documento
     * @param fecDesde Indica la fecha de inicio
     * @param fecHasta Indica la fecha final
     * @return 
     * @throws java.lang.Exception
     */
    public List<ComprobantePago> objDatosFacturacion(String fecDesde, String fecHasta, Integer idDocu ) throws Exception{
        
        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;
        List<ComprobantePago> lstCompPagos = new ArrayList<>();
        boolean lSiguiente = false;
        Connection cnConexion = null;

        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            System.out.println("=============== objDatosFacturacion ==================");
            System.out.println("idDocu:" + idDocu);
            System.out.println("fecDesde:" + fecDesde);
            System.out.println("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICMANTFACTURADORSUNAT.PRC_SICOBTFACTURACION");
            Sp.addParameter(new InParameter("X_ID_DOCU", Types.INTEGER, idDocu));
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.VARCHAR, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.VARCHAR, fecHasta));
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(cnConexion,4);            
            
            List<ComprobantePagoDet> lstCompPagoDet;
            ComprobantePago objComprobante;
            BigDecimal idDocutmp;
            lSiguiente = rsConsulta.next();

            while(lSiguiente){                
                
                lstCompPagoDet = new ArrayList<>();
                
                idDocutmp = rsConsulta.getBigDecimal("ID_DOCU");
                
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));
                objSclaseeven.setCodSclaseeven(rsConsulta.getString("COD_SCLASEEVEN"));
                
                Sic1stipodocu objStipodocu = new Sic1stipodocu();
                objStipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                objStipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                objStipodocu.setCodSunat(rsConsulta.getString("COD_SUNAT"));
                
                /*Datos del contribuyente emisor (INVERSIONES EMZA EIRL)*/
                Sic1pers objPersContribu = new Sic1pers();
                objPersContribu.setIdPers(rsConsulta.getBigDecimal("ID_PERSCONTRIBU"));
                objPersContribu.setCodIden(rsConsulta.getString("NUM_DOCUCONTRIBU"));
                objPersContribu.setDesPers(rsConsulta.getString("DES_PERSCONTRIBU"));                               
                
                Sic1docu objDocu = new Sic1docu();
                objDocu.setIdDocu(idDocutmp);
                objDocu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                objDocu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_CREACION"));
                objDocu.setSic1sclaseeven(objSclaseeven);
                objDocu.setSic1stipodocu(objStipodocu);
                objDocu.setSic1perscontribuyente(objPersContribu);
                
                //DATOS DEL FACTURADOR SUNAT
                Sic1docufacturadorsunat objFacturador = new Sic1docufacturadorsunat();
                objFacturador.setIndSitu(rsConsulta.getString("IND_SITU"));
                objFacturador.setDesSitu(rsConsulta.getString("DES_SITU"));
                objFacturador.setDesObse(rsConsulta.getString("DES_OBSE"));
                objFacturador.setFecCarg(rsConsulta.getString("FEC_CARG"));
                objFacturador.setFecGene(rsConsulta.getString("FEC_GENE"));
                objFacturador.setFecEnvi(rsConsulta.getString("FEC_ENVI"));
                objFacturador.setNomArch(rsConsulta.getString("NOM_ARCH"));
                objFacturador.setNumRuc(rsConsulta.getString("NUM_RUC"));
                objFacturador.setNumDocu(rsConsulta.getString("NUM_DOCUFACTURADOR"));
                objFacturador.setTipDocu(rsConsulta.getString("TIP_DOCU"));
                
                //DATOS DEL COMPROBANTE SEGUN ESTRUCTURA QUE SOLICITA SUNAT PARA LA DECLARACION
                objComprobante = new ComprobantePago();
                objComprobante.setTipOperacion(rsConsulta.getString("tipOperacion"));
                objComprobante.setFecEmision(rsConsulta.getString("fecEmision"));
                objComprobante.setHorEmision(rsConsulta.getString("horEmision"));
                objComprobante.setFecVencimiento(rsConsulta.getString("fecVencimiento"));
                objComprobante.setCodLocalEmisor(rsConsulta.getInt("codLocalEmisor"));
                objComprobante.setTipDocUsuario(rsConsulta.getString("tipDocUsuario"));
                objComprobante.setNumDocUsuario(rsConsulta.getString("numDocUsuario"));
                objComprobante.setRznSocialUsuario(rsConsulta.getString("rznSocialUsuario"));
                objComprobante.setTipMoneda(rsConsulta.getString("tipMoneda"));
                objComprobante.setSumTotTributos(rsConsulta.getDouble("sumTotTributos"));
                objComprobante.setSumTotValVenta(rsConsulta.getDouble("sumTotValVenta"));
                objComprobante.setSumPrecioVenta(rsConsulta.getDouble("sumPrecioVenta"));
                objComprobante.setSumDescTotal(rsConsulta.getDouble("sumDescTotal"));
                objComprobante.setSumOtrosCargos(rsConsulta.getDouble("sumOtrosCargos"));
                objComprobante.setSumTotalAnticipos(rsConsulta.getDouble("sumTotalAnticipos"));
                objComprobante.setSumImpVenta(rsConsulta.getDouble("sumImpVenta"));
                objComprobante.setUblVersionId(rsConsulta.getString("ublVersionId"));
                objComprobante.setCustomizationId(rsConsulta.getString("customizationId"));
                objComprobante.setSic1docu(objDocu);
                objComprobante.setSic1docufactsunat(objFacturador);
                
                while(lSiguiente && idDocutmp.compareTo(rsConsulta.getBigDecimal("ID_DOCU"))== 0){
                                    
                    ComprobantePagoDet objCompPagoDet = new ComprobantePagoDet();
                    objCompPagoDet.setCodUnidadMedida(rsConsulta.getString("codUnidadMedida"));                
                    objCompPagoDet.setCtdUnidadItem(rsConsulta.getString("ctdUnidadItem"));
                    objCompPagoDet.setCodProducto(rsConsulta.getString("codProducto"));                
                    objCompPagoDet.setCodProductoSUNAT(rsConsulta.getString("codProductoSUNAT"));
                    objCompPagoDet.setDesItem(rsConsulta.getString("desItem"));                
                    objCompPagoDet.setMtoValorUnitario(rsConsulta.getDouble("mtoValorUnitario"));                
                    objCompPagoDet.setSumTotTributosItem(rsConsulta.getDouble("sumTotTributosItem"));
                    objCompPagoDet.setCodTriIGV(rsConsulta.getInt("codTriIGV"));
                    objCompPagoDet.setMtoIgvItem(rsConsulta.getString("mtoIgvItem"));                
                    objCompPagoDet.setMtoBaseIgvItem(rsConsulta.getDouble("mtoBaseIgvItem"));
                    objCompPagoDet.setNomTributoIgvItem(rsConsulta.getString("nomTributoIgvItem"));
                    objCompPagoDet.setCodTipTributoIgvItem(rsConsulta.getString("codTipTributoIgvItem"));
                    objCompPagoDet.setTipAfeIGV(rsConsulta.getString("tipAfeIGV"));
                    objCompPagoDet.setPorIgvItem(rsConsulta.getString("porIgvItem"));
                    objCompPagoDet.setCodTriISC(rsConsulta.getString("codTriISC"));
                    objCompPagoDet.setMtoIscItem(rsConsulta.getDouble("mtoIscItem"));                
                    objCompPagoDet.setMtoBaseIscItem(rsConsulta.getDouble("mtoBaseIscItem"));
                    objCompPagoDet.setNomTributoIscItem(rsConsulta.getString("nomTributoIscItem"));
                    objCompPagoDet.setCodTipTributoIscItem(rsConsulta.getString("codTipTributoIscItem"));
                    objCompPagoDet.setTipSisISC(rsConsulta.getString("tipSisISC"));
                    objCompPagoDet.setPorIscItem(rsConsulta.getDouble("porIscItem"));                
                    objCompPagoDet.setCodTriOtroItem(rsConsulta.getString("codTriOtroItem"));
                    objCompPagoDet.setMtoTriOtroItem(rsConsulta.getDouble("mtoTriOtroItem"));
                    objCompPagoDet.setMtoBaseTriOtroItem(rsConsulta.getDouble("mtoBaseTriOtroItem"));
                    objCompPagoDet.setNomTributoIOtroItem(rsConsulta.getString("nomTributoIOtroItem"));
                    objCompPagoDet.setCodTipTributoIOtroItem(rsConsulta.getString("codTipTributoIOtroItem"));
                    objCompPagoDet.setPorTriOtroItem(rsConsulta.getDouble("porTriOtroItem"));
                    objCompPagoDet.setMtoPrecioVentaUnitario(rsConsulta.getDouble("mtoPrecioVentaUnitario"));
                    objCompPagoDet.setMtoValorVentaItem(rsConsulta.getDouble("mtoValorVentaItem"));
                    objCompPagoDet.setMtoValorReferencialUnitario(rsConsulta.getDouble("mtoValorReferencialUnitario"));

                    lstCompPagoDet.add(objCompPagoDet);
                    
                    lSiguiente = rsConsulta.next();
                    
                }
                
                //Agregando el detalle del comprobante
                objComprobante.setLstCompPagoDet(lstCompPagoDet);
                
                lstCompPagos.add(objComprobante);
                                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion!=null)
                cnConexion.close();
            
        }
        
        return lstCompPagos;        
    }
    
    
    /** REPORTE: DETALLE DE OPERACIONES ANULADAS
     * @param idDocu Indica el identificador del documento
     * @param fecDesde Indica la fecha de inicio
     * @param fecHasta Indica la fecha final
     * @return 
     * @throws java.lang.Exception
     */
    public List<ComunicacionBaja> objDatosComunicacionBaja(Integer idDocu, Integer fecDesde, Integer fecHasta) throws Exception{
        
        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;
        List<ComunicacionBaja> lstComunicPagos = new ArrayList<>();        
        
        Connection cnConexion = null;
        
        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            System.out.println("=============== objDatosComunicacionBaja ==================");
            System.out.println("idDocu:" + idDocu);
            System.out.println("fecDesde:" + fecDesde);
            System.out.println("fecHasta:" + fecHasta);
            
            Sp = new StoredProcedure("PKG_SICMANTFACTURADORSUNAT.PRC_SICOBTCOMUNICBAJA");
            Sp.addParameter(new InParameter("X_ID_DOCU", Types.INTEGER, idDocu));
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(cnConexion,4);            
            
            ComunicacionBaja objBaja;

            while(rsConsulta.next()){
                
                
                /*Datos del contribuyente emisor (INVERSIONES EMZA EIRL)*/
                Sic1pers objPersContribu = new Sic1pers();
                objPersContribu.setIdPers(rsConsulta.getBigDecimal("ID_PERSCONTRIBU"));
                objPersContribu.setCodIden(rsConsulta.getString("NUM_DOCUCONTRIBU"));
                objPersContribu.setDesPers(rsConsulta.getString("DES_PERSCONTRIBU"));   
                
                 //DATOS DEL FACTURADOR SUNAT
                Sic1docufacturadorsunat objFacturador = new Sic1docufacturadorsunat();
                objFacturador.setIndSitu(rsConsulta.getString("IND_SITU"));
                objFacturador.setDesSitu(rsConsulta.getString("DES_SITU"));
                objFacturador.setDesObse(rsConsulta.getString("DES_OBSE"));
                objFacturador.setFecCarg(rsConsulta.getString("FEC_CARG"));
                objFacturador.setFecGene(rsConsulta.getString("FEC_GENE"));
                objFacturador.setFecEnvi(rsConsulta.getString("FEC_ENVI"));
                objFacturador.setNomArch(rsConsulta.getString("NOM_ARCH"));
                objFacturador.setNumRuc(rsConsulta.getString("NUM_RUC"));
                objFacturador.setNumDocu(rsConsulta.getString("NUM_DOCUFACTURADOR"));
                objFacturador.setTipDocu(rsConsulta.getString("TIP_DOCU"));
                                
                /*Tipo de Operacion*/
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));
                objSclaseeven.setCodSclaseeven(rsConsulta.getString("COD_SCLASEEVEN"));
                
                /*Tipo De Documento*/
                Sic1stipodocu objStipodocu = new Sic1stipodocu();
                objStipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                objStipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                objStipodocu.setCodSunat(rsConsulta.getString("COD_SUNAT"));
                
                /*Documento*/
                Sic1docu objDocu = new Sic1docu();
                objDocu.setIdDocu(rsConsulta.getBigDecimal("ID_DOCU"));
                objDocu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                objDocu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_CREACION"));
                objDocu.setSic1sclaseeven(objSclaseeven);
                objDocu.setSic1stipodocu(objStipodocu);
                objDocu.setSic1perscontribuyente(objPersContribu);
                  
                /*Comunicacion Baja*/
                objBaja = new ComunicacionBaja();
                objBaja.setFecGeneracion(rsConsulta.getString("FECGENERACION"));
                objBaja.setFecComunicacion(rsConsulta.getString("FECCOMUNICACION"));
                objBaja.setTipDocBaja(rsConsulta.getString("TIPDOCBAJA"));
                objBaja.setNumDocBaja(rsConsulta.getString("NUMDOCBAJA"));
                objBaja.setDesMotivoBaja(rsConsulta.getString("DESMOTIVOBAJA"));
                objBaja.setSic1docu(objDocu);
                objBaja.setSic1docufactsunat(objFacturador);
                
                lstComunicPagos.add(objBaja);
                                
            }
        
        } catch (SQLException | HibernateException e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion!=null)
                cnConexion.close();
            
        }
        
        return lstComunicPagos;        
    }
    
    /**
     * PERMITE INDICA QUE UN DOCUMENTO YA HA SIDO ENVIADO A SUNAT COMO FACTURACION ELECTRONICA
     * @param idDocu Indica el identificador del documento
     * @throws Exception 
     */
    public void indicarDocumentoNotificadoASunat(BigDecimal idDocu) throws Exception{

        Connection cnConexion = null;
        try{

            cnConexion = ConexionBD.obtConexion();

            String sql = " UPDATE SIC1DOCU SET FLG_DECLARADOSUNAT = 1 WHERE ID_DOCU = " + idDocu;

            System.out.println("sql:" + sql);
            CallableStatement statement = cnConexion.prepareCall(sql);
            statement.executeUpdate();            
            
            cnConexion.commit();
            
        } catch (Exception ex) {
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion!= null)
                cnConexion.close();
        }
        
    }
    
}
