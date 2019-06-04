/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate1.Sic1docufacturadorsunat;
import com.general.hibernate1.Sic1docufacturadorsunatId;
import com.general.util.beans.UtilClass;
import com.general.util.dao.ConexionBD;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author emoreno
 */
public class DaoFacturadorSunatImpl {
    
    private static DaoMaestroCatalogoImpl daoCatalogo = new DaoMaestroCatalogoImpl();    
        
    /**
     * BORRA LOS DATOS DE LA TABLA "DOCUMENTO" DEL FACTURADOR
     * @throws Exception 
     */
    public static void borrarDatosTablaFacturadorSunat() throws Exception{
        
        Connection cnConexion = null;
        try{
            cnConexion = ConexionBD.obtConexionBDFacturadorSunat();
            PreparedStatement st = cnConexion.prepareStatement("DELETE FROM DOCUMENTO");
            st.executeUpdate();
            
            cnConexion.commit();
        }catch(Exception ex){
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion!= null)
                cnConexion.close();
        }        
    }
    
    /**
     * BORRA LOS DATOS DE CIERTO COMPROBANTE EN LA TABLA "DOCUMENTO" DEL FACTURADOR 
     * @param numRuc Indica el Numero de RUC de la empresa declarante
     * @param numDocu Indica el nro de documento
     * @param codTipodocuSunat Indica si es boleta o factura
     * @throws Exception 
     */     
    public static void borrarDatosTablaFacturadorSunat(String numRuc, String numDocu, String codTipodocuSunat) throws Exception{
        
        Connection cnConexion = null;
        
        try{
            cnConexion = ConexionBD.obtConexionBDFacturadorSunat();
            
            String sql = "DELETE FROM DOCUMENTO WHERE IND_SITU IS NOT NULL AND TIP_DOCU = '" + codTipodocuSunat + "' AND NUM_RUC = '" + numRuc +  "' AND NUM_DOCU = '" + numDocu + "'";
            
            PreparedStatement st = cnConexion.prepareStatement(sql) ;
            st.executeUpdate();
            
            cnConexion.commit();
        }catch(Exception ex){
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion!= null)
                cnConexion.close();
        }        
    }    
        
    /**
     * GUARDA LOS DATOS DEL COMPROBANTE QUE HAN SIDO PROCESADOS PARA ENVIAR A LA SUNAT (SIC1DOCUFACTURADORSUNAT)
     * @param obj Indica el objeto que contiene los datos del comprobante
     * @param cnConexion Indica la conexion     
     * @throws Exception 
     */
    public void creaDocuFacturadorSunat(Connection cnConexion, Sic1docufacturadorsunat obj ) throws Exception{
        
        try{         
            
            StoredProcedure sp = new StoredProcedure("PKG_SICMANTFACTURADORSUNAT.PRC_SICCREADOCUFACTURADOR");

            sp.addParameter(new InParameter("X_COD_PROC",       Types.VARCHAR, obj.getId().getCodProc()));
            sp.addParameter(new InParameter("X_ID_DOCU",        Types.NUMERIC, obj.getId().getIdDocu()));                
            sp.addParameter(new InParameter("X_NUM_RUC",        Types.VARCHAR, obj.getNumRuc()));
            sp.addParameter(new InParameter("X_TIP_DOCU",       Types.VARCHAR, obj.getTipDocu()));
            sp.addParameter(new InParameter("X_NUM_DOCU",       Types.VARCHAR, obj.getNumDocu()));
            sp.addParameter(new InParameter("X_FEC_CARG",       Types.VARCHAR, obj.getFecCarg()));
            sp.addParameter(new InParameter("X_FEC_GENE",       Types.VARCHAR, obj.getFecGene()));
            sp.addParameter(new InParameter("X_FEC_ENVI",       Types.VARCHAR, obj.getFecEnvi()));
            sp.addParameter(new InParameter("X_DES_OBSE",       Types.VARCHAR, obj.getDesObse()));
            sp.addParameter(new InParameter("X_NOM_ARCH",       Types.VARCHAR, obj.getNomArch()));
            sp.addParameter(new InParameter("X_IND_SITU",       Types.VARCHAR, obj.getIndSitu()));
            sp.addParameter(new InParameter("X_TIP_ARCH",       Types.VARCHAR, obj.getTipArch()));
            sp.addParameter(new InParameter("X_FIRM_DIGITAL",   Types.VARCHAR, obj.getFirmDigital()));
            sp.addParameter(new InParameter("X_FLG_ACTIVO",     Types.INTEGER, obj.getFlgActivo()));                

            sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
            sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
            sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));

            sp.ExecuteNonQuery(cnConexion);

            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
            }
            
        
        }catch(SQLException ex){
            throw new Exception(ex.getMessage());
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public void obtDatosFacturadorBD() throws Exception{
                
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        
        try{           
                        
            cnConexion = ConexionBD.obtConexionBDFacturadorSunat();
            
            String sql = " SELECT * FROM DOCUMENTO";
            
            PreparedStatement st = cnConexion.prepareStatement(sql);
            rsConsulta = st.executeQuery();
            
             while(rsConsulta.next()){
                 System.out.print("NumDocu:" + rsConsulta.getString("NUM_DOCU"));
                 System.out.print("// NUM_RUC:" + rsConsulta.getString("NUM_RUC"));
                 System.out.print("// IND_SITU:" + rsConsulta.getString("IND_SITU"));
                 System.out.print("// TIP_ARCH:" + rsConsulta.getString("TIP_ARCH"));
                 System.out.print("// TIP_DOCU:" + rsConsulta.getString("TIP_DOCU"));
                 System.out.println("// DES_OBSE:" + rsConsulta.getString("DES_OBSE"));
                 
             }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(rsConsulta != null)
                rsConsulta.close();
            if(cnConexion != null)
                cnConexion.close();
            
        }
    }
    
    /**
     * TRAE LOS DATOS DEL DOCUMENTO QUE YA HA SIDO ENVIADO A LA SUNAT
     * @param numRuc
     * @param numDocu
     * @return
     * @throws Exception 
     */
    public Sic1docufacturadorsunat obtDatosComprobanteEnviadoSunat( String numRuc, String numDocu) throws Exception{
                
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        Sic1docufacturadorsunat objResult = new Sic1docufacturadorsunat();
        
        try{
            
            System.out.println("obtDatosComprobanteEnviadoSunat -- numRuc: " + numRuc +  " - numDocu: " + numDocu);

            cnConexion = ConexionBD.obtConexionBDFacturadorSunat();
            
            String sql = " SELECT * FROM DOCUMENTO WHERE IND_SITU = '03' AND NUM_RUC = '" + numRuc +  "' AND NUM_DOCU = '" + numDocu + "'" ;
            
            PreparedStatement st = cnConexion.prepareStatement(sql);
            rsConsulta = st.executeQuery();
            
            while(rsConsulta.next()){
            
                objResult.setNumRuc(rsConsulta.getString("NUM_RUC"));
                objResult.setTipDocu(rsConsulta.getString("TIP_DOCU"));
                objResult.setNumDocu(rsConsulta.getString("NUM_DOCU"));
                System.out.println(rsConsulta.getString("FEC_CARG"));
                objResult.setFecCarg(rsConsulta.getString("FEC_CARG"));
                objResult.setFecGene(rsConsulta.getString("FEC_GENE"));
                objResult.setFecEnvi(rsConsulta.getString("FEC_ENVI"));
                objResult.setDesObse(rsConsulta.getString("DES_OBSE"));
                objResult.setNomArch(rsConsulta.getString("NOM_ARCH"));
                objResult.setIndSitu(rsConsulta.getString("IND_SITU"));
                objResult.setTipArch(rsConsulta.getString("TIP_ARCH"));
                objResult.setFirmDigital(rsConsulta.getString("FIRM_DIGITAL"));
                                 
             }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(rsConsulta != null)
                rsConsulta.close();
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return objResult;
    }
    
    /**
     * TRAE LOS DATOS ACTUALES DEL DOCUMENTO QUE ESTÁ SIENDO PROCESADO POR EL FACTURADOR SUNAT
     * @param numRuc Indica el nro de ruc del contribuyente
     * @param numDocu Indica el nro del comprobante F001-1545
     * @param codTipodocuSunat Indica el Tipo de comprobante (FACTURA, BOLETA)
     * @return
     * @throws Exception 
     */
    public Sic1docufacturadorsunat obtDatosComprobanteFacturadorSunat( String numRuc, String numDocu, String codTipodocuSunat) throws Exception{
                
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        Sic1docufacturadorsunat objResult = new Sic1docufacturadorsunat();
        
        try{
            
            System.out.println("obtDatosComprobanteFacturadorSunat -- numRuc: " + numRuc +  " - numDocu: " + numDocu);

            cnConexion = ConexionBD.obtConexionBDFacturadorSunat();
            
            String sql = " SELECT * FROM DOCUMENTO WHERE IND_SITU IS NOT NULL AND TIP_DOCU = '" + codTipodocuSunat + "' AND NUM_RUC = '" + numRuc +  "' AND NUM_DOCU = '" + numDocu + "'" ;
            
            PreparedStatement st = cnConexion.prepareStatement(sql);
            rsConsulta = st.executeQuery();
            
            while(rsConsulta.next()){
            
                objResult.setNumRuc(rsConsulta.getString("NUM_RUC"));
                objResult.setTipDocu(rsConsulta.getString("TIP_DOCU"));
                objResult.setNumDocu(rsConsulta.getString("NUM_DOCU"));
                
                objResult.setFecCarg(rsConsulta.getString("FEC_CARG"));
                objResult.setFecGene(rsConsulta.getString("FEC_GENE"));
                objResult.setFecEnvi(rsConsulta.getString("FEC_ENVI"));
                
                objResult.setDesObse(rsConsulta.getString("DES_OBSE"));
                objResult.setNomArch(rsConsulta.getString("NOM_ARCH"));
                objResult.setIndSitu(rsConsulta.getString("IND_SITU"));
                objResult.setTipArch(rsConsulta.getString("TIP_ARCH"));
                objResult.setFirmDigital(rsConsulta.getString("FIRM_DIGITAL"));
                                 
             }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(rsConsulta != null)
                rsConsulta.close();
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return objResult;
    }
    
    /**
     * TRAE EL LISTADO DE TODOS LOS DOCUMENTOS QUE HAN SIDO PROCESADOS POR EL FACTURADOR DE LA SUNAT     
     * @param objFacturador
     * @return
     * @throws Exception 
     */
    public List<Sic1docufacturadorsunat> obtDatosFacturadorLocal( Sic1docufacturadorsunat objFacturador) throws Exception{
                
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        List<Sic1docufacturadorsunat> lstResult = new ArrayList<>();
        
        try{            
            

            cnConexion = ConexionBD.obtConexion();
            
            String sql = "SELECT T1.* " +
                            "       ,T1.TIP_DOCU || '-' || CASE T1.TIP_DOCU WHEN '01' THEN 'FACTURA'\n" +
                            "                                               WHEN '03' THEN 'BOLETA' END AS DES_TIPDOCU " +
                            "       ,T1.IND_SITU || '-' || CASE T1.IND_SITU WHEN '01' THEN 'POR GENERAR XML' " +
                            "                                    WHEN '02' THEN 'XML GENERADO'\n" +
                            "                                    WHEN '03' THEN 'ENVIADO Y ACEPTADO SUNAT' " +
                            "                                    WHEN '06' THEN 'CON ERRORES' END AS DES_SITU " +
                            "FROM SIC1DOCUFACTURADORSUNAT T1 WHERE 1 = 1";
            
            
            if( objFacturador.getTipDocu() != null && Integer.valueOf(objFacturador.getTipDocu()) > 0)
                sql += " AND TIP_DOCU = '" + objFacturador.getTipDocu() + "'";
            if( !objFacturador.getNumDocu().isEmpty() )
                sql += " AND UPPER(NUM_DOCU) LIKE '%" + objFacturador.getNumDocu().toUpperCase() + "%'";
            if( objFacturador.getIndSitu()!= null && Integer.valueOf(objFacturador.getIndSitu()) > 0)
                sql += " AND IND_SITU = '" + objFacturador.getIndSitu() + "'";
            if( !objFacturador.getFecDesde().isEmpty() )
                sql += " AND TO_NUMBER(TO_CHAR(TRUNC(FEC_ENVI),'YYYYMMDD')) >= " + UtilClass.convertSringToNumber_YYYYMMDD(objFacturador.getFecDesde());
            if( objFacturador.getFecHasta()!= null && objFacturador.getFecHasta().length() > 0)
                sql += " AND TO_NUMBER(TO_CHAR(TRUNC(FEC_ENVI),'YYYYMMDD')) <= " + UtilClass.convertSringToNumber_YYYYMMDD(objFacturador.getFecHasta());
                
            
            PreparedStatement st = cnConexion.prepareStatement(sql);
            rsConsulta = st.executeQuery();
            
            while(rsConsulta.next()){
            
                Sic1docufacturadorsunat obj = new Sic1docufacturadorsunat();
                
                obj.setNumRuc(rsConsulta.getString("NUM_RUC"));
                obj.setTipDocu(rsConsulta.getString("DES_TIPDOCU"));
                obj.setNumDocu(rsConsulta.getString("NUM_DOCU"));                
                obj.setFecCarg(rsConsulta.getString("FEC_CARG"));
                obj.setFecGene(rsConsulta.getString("FEC_GENE"));
                obj.setFecEnvi(rsConsulta.getString("FEC_ENVI"));
                obj.setDesObse(rsConsulta.getString("DES_OBSE"));
                obj.setNomArch(rsConsulta.getString("NOM_ARCH"));
                obj.setIndSitu(rsConsulta.getString("IND_SITU"));
                obj.setDesSitu(rsConsulta.getString("DES_SITU"));
                obj.setTipArch(rsConsulta.getString("TIP_ARCH"));
                obj.setFirmDigital(rsConsulta.getString("FIRM_DIGITAL"));
                lstResult.add(obj);
                                 
             }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(rsConsulta != null)
                rsConsulta.close();
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return lstResult;
    }
    
    
     public List<Sic1docu> obtResumenEnvioDocuSunat(Integer idDocu, String fecDesde, String fecHasta) throws Exception{
         
        List<Sic1docu> list = new ArrayList<>();

        StoredProcedure Sp = null;
        ResultSet rsConsulta = null;
        
        Connection cnConexion = null;

        try{
            
            cnConexion = ConexionBD.obtConexion();            
            
            Sp = new StoredProcedure("PKG_SICMANTFACTURADORSUNAT.PRC_SICRPTENVIODOCUSUNAT");
            Sp.addParameter(new InParameter("X_ID_DOCU", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_DESDE", Types.INTEGER, fecDesde));
            Sp.addParameter(new InParameter("X_FEC_HASTA", Types.INTEGER, fecHasta));
            Sp.addParameter(new OutParameter("X_CURSOR", OracleTypes.CURSOR));
            
            Sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
            Sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            
            rsConsulta = Sp.ExecuteResultCursor(cnConexion,4);
            
            while(rsConsulta.next()){
                
                
                BigDecimal idDocutmp = rsConsulta.getBigDecimal("ID_DOCU");
                
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setDesSclaseeven(rsConsulta.getString("DES_SCLASEEVEN"));
                objSclaseeven.setCodSclaseeven(rsConsulta.getString("COD_SCLASEEVEN"));
                
                Sic1stipodocu objStipodocu = new Sic1stipodocu();
                objStipodocu.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                objStipodocu.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                objStipodocu.setCodSunat(rsConsulta.getString("COD_SUNAT"));
                
                /*Datos del Cliente Proveedor*/
                Sic1pers objPersExterno = new Sic1pers();
                objPersExterno.setCodIden(rsConsulta.getString("COD_IDENPERSEXTERNO"));
                objPersExterno.setDesPers(rsConsulta.getString("DES_PERSEXTERNO"));
                
                /*Datos del contribuyente emisor (INVERSIONES EMZA EIRL)*/
                Sic1pers objPersContribu = new Sic1pers();
                objPersContribu.setIdPers(rsConsulta.getBigDecimal("ID_PERSCONTRIBU"));
                objPersContribu.setCodIden(rsConsulta.getString("NUM_DOCUCONTRIBU"));
                objPersContribu.setDesPers(rsConsulta.getString("DES_PERSCONTRIBU"));                               
                
                Sic1lugar objLugar = new Sic1lugar();
                objLugar.setDesLugar(rsConsulta.getString("DES_SUCURSAL"));
                
                /*Documento*/
                Sic1docu objDocu = new Sic1docu();
                objDocu.setIdDocu(idDocutmp);
                objDocu.setCodSerie(rsConsulta.getString("COD_SERIE"));
                objDocu.setNumDocu(rsConsulta.getBigDecimal("NUM_DOCU"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_DESDE"));
                objDocu.setFecDesde(rsConsulta.getDate("FEC_CREACION"));
                objDocu.setSic1sclaseeven(objSclaseeven);
                objDocu.setSic1stipodocu(objStipodocu);
                objDocu.setFlgDeclaradosunat(rsConsulta.getInt("FLG_DECLARADOSUNAT"));
                objDocu.setSic1perscontribuyente(objPersContribu);
                objDocu.setSic1persexterno(objPersExterno);
                objDocu.setSic1lSucursal(objLugar);                                
                
                //DATOS DEL FACTURADOR SUNAT
                Sic1docufacturadorsunatId idFact = new Sic1docufacturadorsunatId();
                idFact.setCodProc(rsConsulta.getString("COD_PROC"));
                idFact.setIdDocu(idDocutmp);
                
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
                objFacturador.setId(idFact);
                
                objDocu.setSic1docufacturadorsunat(objFacturador);
                
                list.add(objDocu);
                
            }
        
        } catch (Exception e){
            throw new Exception(e.getMessage());            
        } finally{
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion != null)
                cnConexion.close();
            
        }
        
        return list;  
        
    }
    
}
