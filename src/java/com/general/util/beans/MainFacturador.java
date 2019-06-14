/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.a2.service.impl.ComprobantePagoService;
import com.general.a2.service.impl.FacturadorSunatServiceImpl;
import com.general.a3.dao.impl.DaoFacturadorSunatImpl;
import com.general.hibernate1.Sic1docufacturadorsunat;
import com.general.hibernate1.Sic1docufacturadorsunatId;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emoreno
 */
public class MainFacturador {
    
    
    
    
    public static void main(String[] args) throws Exception {
        
        if(true){
            DaoFacturadorSunatImpl obj = new DaoFacturadorSunatImpl(); 
            obj.obtResumenEnvioDocuSunat(null,null,null);
        }
        
        /*Limpiar Directorios del facturador*/
        if(false){
            
            ComprobantePagoService objService = new ComprobantePagoService();
            List<ComprobantePago> lstCompPago = objService.listarComprobantesPendienteEnvio(null);            
            
            /*1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA*/
            FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
            objFacturador.limpiarBDyDirectoriosFacturador();               
            
        }
        
        if(false){
            DaoFacturadorSunatImpl.borrarDatosTablaFacturadorSunat();
        }
        
        if(false){
            
            ComprobantePagoService objService = new ComprobantePagoService();
            List<ComprobantePago> lstCompPago = objService.listarComprobantesPendienteEnvio(null);
            FacturadorSunatServiceImpl objServiceFacturador = new FacturadorSunatServiceImpl();
            objServiceFacturador.limpiarBDyDirectoriosFacturador();
        }
        
        /*SIMULA EL PROCESO DE GENERACION DE FACTURACION ELECTRONICA*/
        if(false){
            
            ComprobantePagoService objService = new ComprobantePagoService();
            List<ComprobantePago> lstCompPago = objService.listarComprobantesPendienteEnvio( null);            
            
            /*1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA*/
            FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
            objFacturador.limpiarBDyDirectoriosFacturador();                                

            /*2) Generar archivos de texto*/                            
            objService.generarArchivosFacturacion(lstCompPago);      
            
        }
        
        /*consultar la bd del facturador BD*/
        if(false){
            DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();            
            objDao.obtDatosFacturadorBD();
        }
        
        if(false){
            UtilClass.copiarContenidoDirectorio("C:\\SFS_v1.2\\sunat_archivos\\sfs\\DATA", "C:\\ARCHIVOS_SUNAT\\DATA");
        }
        
        /**
     * Genera los archivos plano de los comprobantes de pago filtrados.
     * @throws Exception 
     */
        if(false){
     
        try{
            
            ComprobantePagoService objService = new ComprobantePagoService();
            List<ComprobantePago> lstCompPago = objService.listarComprobantesPendienteEnvio(null);
            
            if(lstCompPago != null && lstCompPago.size() > 0){                              
                
//                //1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA
//                
//                System.out.println("1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA");
//                FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
//                objFacturador.limpiarBDyDirectoriosFacturador(lstCompPago);
//                
//                //2) Grabar archivos a procesar en en tabla SIC1DOCUFACTURADORSUNAT
//                
//                System.out.println("2) Grabar archivos a procesar en en tabla SIC1DOCUFACTURADORSUNAT");
//                List<Sic1docufacturadorsunat> lstFacturador = new ArrayList<>();
//                String codProc = "L" + UtilClass.getCurrentTime_YYYYMMDDHHMISS();
//                for(ComprobantePago objCom : lstCompPago){
//                    
//                    Sic1docufacturadorsunatId objIdFact = new Sic1docufacturadorsunatId();
//                    objIdFact.setCodProc(codProc);
//                    objIdFact.setIdDocu(objCom.getSic1docu().getIdDocu());
//                                       
//                    Sic1docufacturadorsunat objFact = new Sic1docufacturadorsunat();
//                    objFact.setNumRuc(Constantes.CONS_NUM_RUC);
//                    objFact.setNumDocu(objCom.getSic1docu().getCodSerie() + "-" + objCom.getSic1docu().getNumDocu());
//                    objFact.setFlgActivo(new BigDecimal(1));
//                    objFact.setId(objIdFact);
//                    
//                    lstFacturador.add(objFact);
//                    
//                }
//                
//                objFacturador.creaDocuFacturadorSunat(lstFacturador);
//
//                
//                //3) Generar archivos de texto
//                
//                System.out.println("3) Generar archivos de texto");                
//                objService.generarArchivosFacturacion(lstCompPago);
               
                
                //4 Verificar por cada archivo si ya terminó de enviarse a la sunat
                for(ComprobantePago objCom : lstCompPago){                   
                                        
                    String numDocu = objCom.getSic1docu().getCodSerie() + "-" + objCom.getSic1docu().getNumDocu();
                    String numRuc = objCom.getSic1docu().getSic1perscontribuyente().getCodIden();
                    
                    DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
                    Sic1docufacturadorsunat objDocu = objDao.obtDatosComprobanteFacturadorSunat( numRuc
                                                                                                ,numDocu
                                                                                                ,objCom.getSic1docu().getSic1stipodocu().getCodSunat());
                    System.out.println("Documento:" + objDocu.getNumDocu());
                    if( objDocu.getNumDocu() != null ){
                        /*Completamos los datos faltantes en la tabla SIC1DOCUFACTURADORSUNAT*/
                    }
                }
                
                
                /*6 Hacer copia de seguridad de archivos generados*/
                
                System.out.println("*5 Hacer copia de seguridad de archivos generados");
                //objFacturador.ejecutarCopiaSeguridadArchivosGenerados(lstCompPago);
                

            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
            
        
    }
    
}
