/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoComprobantePagoImpl;
import com.general.a3.dao.impl.DaoFacturadorSunatImpl;
import com.general.util.beans.ComprobantePago;
import com.general.util.beans.ComprobantePagoDet;
import com.general.util.beans.ComunicacionBaja;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author emoreno
 */
public class ComprobantePagoService {
    
    private final DaoFacturadorSunatImpl objDaoFacturador = new DaoFacturadorSunatImpl();
    
    /**
     * Metodo que devuelve la lista de comprobantes de pago (Facturas y Boletas) que aun no han sido enviados a Sunat
     * @param fecDesde
     * @param fecHasta
     * @param codEstado Codigo que indica que el Comprobante no ha sido enviado a Sunat
     * @return
     * @throws Exception 
     */
    public List<ComprobantePago> listarComprobantesPendienteEnvio(String fecDesde, String fecHasta, Integer idDocu) throws Exception{

        List<ComprobantePago> objLstComPagos;
        try{

            DaoComprobantePagoImpl objDao = new DaoComprobantePagoImpl();
            objLstComPagos = objDao.objDatosFacturacion(fecDesde, fecHasta, idDocu);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return objLstComPagos;        
    }
    
    /**
     * LISTA TODOS LOS COMPROBANTES DE PAGO QUE ESTAN LISTO PARA INFORMAR A LA SUNAT QUE HAN SIDO DADOS DE BAJA
     * @param fecDesde
     * @param fecHasta
     * @param codEstado
     * @return
     * @throws Exception 
     */
    public List<ComunicacionBaja> listarComunicBajaPendienteEnvio(Integer idDocu, Integer fecDesde, Integer fecHasta ) throws Exception{

        List<ComunicacionBaja> objLista;
        try{

            DaoComprobantePagoImpl objDao = new DaoComprobantePagoImpl();
            objLista = objDao.objDatosComunicacionBaja(idDocu, fecDesde, fecHasta);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return objLista;        
    }
    
    /**
     * GENERA LOS ARCHIVOS DE TEXTO PARA LA FACTURACION ELECTRONICA
     * @param objLstComPagos Contiene la lista de todos los comprobantes de pago que se generará su archivo de texto
     * @throws IOException
     * @throws Exception 
     */
    public void generarArchivosFacturacion(List<ComprobantePago> objLstComPagos) throws IOException, Exception{        
                
        try{
            
            //Se obtiene la ruta donde se almacenara los archivos.
            String strRuta =  FacturadorSunatServiceImpl.obtRutaGrabarArchTextoFacturador();
                        
            //DaoComprobantePagoImpl objDao = new DaoComprobantePagoImpl();
            //List<ComprobantePago> objLstComPagos = objDao.objDatosFacturacion(idDocu, fecDesde, fecHasta);
            
            for(ComprobantePago objComprobante : objLstComPagos){
            
                /********************************************************************************/
                /**** CABECERA ******/
                /********************************************************************************/
                
                String nomComprobante = Constantes.CONS_NUM_RUC + "-" + 
                                        objComprobante.getSic1docu().getSic1stipodocu().getCodSunat() + "-" + 
                                        objComprobante.getSic1docu().getCodSerie() + "-" + 
                                        objComprobante.getSic1docu().getNumDocu();
                
                if(objComprobante.getSic1docu().getSic1sclaseeven().getCodSclaseeven().equals("VI_SICSCLASEEVENNOTACREDITO"))
                    nomComprobante = nomComprobante + ".NOT";
                else
                    nomComprobante = nomComprobante + ".CAB";

                File archivo = new File(strRuta + nomComprobante);

                //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                FileWriter escribir = new FileWriter(archivo, false);
                escribir.write(objComprobante.getTipOperacion() + "|");
                escribir.write(objComprobante.getFecEmision()+ "|");
                escribir.write(objComprobante.getHorEmision()+ "|");
                escribir.write(objComprobante.getFecVencimiento()+ "|");
                escribir.write(objComprobante.getCodLocalEmisor()+ "|");
                escribir.write(objComprobante.getTipDocUsuario()+ "|");
                escribir.write(objComprobante.getNumDocUsuario()+ "|");
                escribir.write(objComprobante.getRznSocialUsuario()+ "|");
                escribir.write(objComprobante.getTipMoneda()+ "|");
                escribir.write(objComprobante.getSumTotTributos() + "|");
                escribir.write(objComprobante.getSumTotValVenta() + "|");
                escribir.write(objComprobante.getSumPrecioVenta()+ "|");
                escribir.write(objComprobante.getSumDescTotal()+ "|");
                escribir.write(objComprobante.getSumOtrosCargos()+ "|");
                escribir.write(objComprobante.getSumTotalAnticipos()+ "|");
                escribir.write(objComprobante.getSumImpVenta()+ "|");
                escribir.write(objComprobante.getUblVersionId()+ "|");
                escribir.write(objComprobante.getCustomizationId()+ "|");
                
                escribir.close();                

                /********************************************************************************/
                /**** DETALLE ******/
                /********************************************************************************/

                archivo = new File(strRuta + nomComprobante + ".DET");

                List<ComprobantePagoDet> lstCompPagoDet = objComprobante.getLstCompPagoDet();
                
                for( ComprobantePagoDet objDet : lstCompPagoDet){

                    escribir = new FileWriter(archivo, false);
                    escribir.write(objDet.getCodUnidadMedida() + "|");
                    escribir.write(objDet.getCtdUnidadItem() + "|");
                    escribir.write(objDet.getCodProducto() + "|");

                    if(objDet.getCodProductoSUNAT()==null)
                        escribir.write("|");
                    else
                        escribir.write(objDet.getCodProductoSUNAT() + "|");

                    escribir.write(objDet.getDesItem() + "|");
                    escribir.write(objDet.getMtoValorUnitario() + "|");
                    escribir.write(objDet.getSumTotTributosItem() + "|");
                    escribir.write(objDet.getCodTriIGV()+ "|");
                    escribir.write(objDet.getMtoIgvItem() + "|");
                    escribir.write(objDet.getMtoBaseIgvItem() + "|");
                    escribir.write(objDet.getNomTributoIgvItem() + "|");
                    escribir.write(objDet.getCodTipTributoIgvItem() + "|");
                    escribir.write(objDet.getTipAfeIGV() + "|");
                    escribir.write(objDet.getPorIgvItem() + "|");

                    /** IMPUESTO ISC ***/

                    escribir.write(objDet.getCodTriISC() + "|");
                    escribir.write(objDet.getMtoIscItem() + "|");
                    escribir.write(objDet.getMtoBaseIscItem() + "|");

                    if(objDet.getNomTributoIscItem()== null){
                        escribir.write("|");
                        escribir.write("|");
                        escribir.write("|");
                    }else{
                        escribir.write(objDet.getNomTributoIscItem() + "|");
                        escribir.write(objDet.getCodTipTributoIscItem() + "|");
                        escribir.write(objDet.getTipSisISC() + "|");
                    }

                    escribir.write(objDet.getPorIscItem() + "|");

                    /** OTROS TRIBUTOS ***/

                    if(objDet.getCodTriOtroItem()== null)
                        escribir.write("|");
                    else 
                        escribir.write(objDet.getCodTriOtroItem() + "|");

                    escribir.write(objDet.getMtoTriOtroItem() + "|");
                    escribir.write(objDet.getMtoBaseTriOtroItem() + "|");

                    if(objDet.getNomTributoIOtroItem()==null){
                        escribir.write("|");
                        escribir.write("|");
                    }
                    else{
                        escribir.write(objDet.getNomTributoIOtroItem() + "|");
                        escribir.write(objDet.getCodTipTributoIOtroItem() + "|");
                    }            

                    escribir.write(objDet.getPorTriOtroItem() + "|");
                    escribir.write(objDet.getMtoPrecioVentaUnitario() + "|");
                    escribir.write(objDet.getMtoValorVentaItem() + "|");

                    if(objDet.getMtoValorReferencialUnitario() == null)
                        escribir.write("|");
                    else
                        escribir.write(objDet.getMtoValorReferencialUnitario() + "|");

                    escribir.close();

                    System.out.println("FIN DETALLE");
            
                }
                
                
                /********************************************************************************/
                /**** TRIBUTOS GENERALES ******/
                /********************************************************************************/
                archivo = new File(strRuta + nomComprobante + ".TRI");                
                
                for( ComprobantePagoDet objDet : lstCompPagoDet){

                    escribir = new FileWriter(archivo, false);
                    escribir.write(objDet.getCodTriIGV()+ "|");
                    escribir.write(objDet.getNomTributoIgvItem()+ "|");
                    escribir.write(objDet.getCodTipTributoIgvItem()+ "|");
                    escribir.write(objDet.getMtoIgvItem()+ "|");
                    
                    escribir.close();
                }                
            }
            
        }catch(IOException ex){
            throw new IOException(ex.getMessage());
        }catch(Exception ex){
            throw new IOException(ex.getMessage());
        }
    }
    
    /**
     * GENERA LOS ARCHIVOS DE TEXTO PARA DAR DE BAJA A LOS COMPROBANTES ANULADOS
     * @param objLstComunicBaja Contiene la lista de todos los comprobantes de pago que se generará su archivo de texto
     * @throws IOException
     * @throws Exception 
     */
    public void generarArchivoComunicBaja(List<ComunicacionBaja> objLstComunicBaja) throws IOException, Exception{
         
        try{
            
             //Se obtiene la ruta donde se almacenara los archivos.
            String strRuta =  FacturadorSunatServiceImpl.obtRutaGrabarArchTextoFacturador();
            String codFechaEnvio = UtilClass.getCurrentTime_YYYYMMDD();
            int contador = 1;

            for(ComunicacionBaja objComunicBaja : objLstComunicBaja){

                String nomComprobante = Constantes.CONS_NUM_RUC + "-RA" + "-"+ codFechaEnvio + "-" + String.format("%03d", contador);

                File archivo = new File(strRuta + nomComprobante + ".CAB");

                //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                FileWriter escribir = new FileWriter(archivo, false);
                escribir.write(objComunicBaja.getFecGeneracion()+ "|");
                escribir.write(objComunicBaja.getFecComunicacion()+ "|");
                escribir.write(objComunicBaja.getTipDocBaja()+ "|");
                escribir.write(objComunicBaja.getNumDocBaja()+ "|");
                escribir.write(objComunicBaja.getDesMotivoBaja()+ "|");

                escribir.close();
                contador++;
            }
            
        }catch(IOException ex){
            throw new IOException(ex.getMessage());
        }
     }
    
    /**
     * PERMITE INDICA QUE UN DOCUMENTO YA HA SIDO ENVIADO A SUNAT COMO FACTURACION ELECTRONICA
     * @param idDocu Indica el identificador del documento
     * @throws Exception 
     */
    public void indicarDocumentoNotificadoASunat(BigDecimal idDocu) throws Exception{
        DaoComprobantePagoImpl objCompago = new DaoComprobantePagoImpl();
        objCompago.indicarDocumentoNotificadoASunat(idDocu);
    }
    
}
