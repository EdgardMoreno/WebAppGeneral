/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoFacturadorSunatImpl;
import com.general.a3.dao.impl.DaoMaestroCatalogoImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate1.Sic1docufacturadorsunat;
import com.general.hibernate1.Sic1docufacturadorsunatId;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.dao.ConexionBD;
import com.general.util.exceptions.CustomizerException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author emoreno
 */
public class FacturadorSunatServiceImpl {
    
    private String desRutaArchivosFacturador;
    private static DaoMaestroCatalogoImpl daoCatalogo = new DaoMaestroCatalogoImpl();
    
    public FacturadorSunatServiceImpl() throws Exception{
        this.desRutaArchivosFacturador = this.obtRutaArchivosFacturadorSFS();
    }
    
    /**
     * LIMPIA LA BD Y ELIMINA LOS DIRECTORIOS DATA,ENVIO,FIRMA,RPTA DEL FACTURADOR SUNAT     
     * @throws Exception 
     */
    public void limpiarBDyDirectoriosFacturador() throws Exception{
        
        try{
            
            /*Limpiar la BD del Facturador Sunat*/
            DaoFacturadorSunatImpl.borrarDatosTablaFacturadorSunat();
            
            /*Eliminando en DIRECTORIO DATA*/
            String rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_DATA;
            UtilClass.eliminarAchivosDirectorio(rutaDirectorio);
            
//            /*Eliminando en DIRECTORIO ENVIO*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
//            UtilClass.eliminarAchivosDirectorio(rutaDirectorio);
//            
//            /*Eliminando en DIRECTORIO FIRMA*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
//            UtilClass.eliminarAchivosDirectorio(rutaDirectorio);
//            
//            /*Eliminando en DIRECTORIO RPTA*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_RPTA;
//            UtilClass.eliminarAchivosDirectorio(rutaDirectorio);            
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }    
    
    /**
     * LIMPIA LA BD Y ELIMINA LOS DIRECTORIOS DATA,ENVIO,FIRMA,RPTA DEL FACTURADOR SUNAT DE UN COMPROBANTE ESPECIFICO
     * @param objFacturador Contiene los datos del comprobante
     * @throws Exception 
     */
    public void limpiarBDyDirectoriosFacturador(Sic1docufacturadorsunat objFacturador) throws Exception{
        
        try{
            
            /*Limpiar la BD del Facturador Sunat*/
            DaoFacturadorSunatImpl.borrarDatosTablaFacturadorSunat(objFacturador.getNumRuc(), objFacturador.getNumDocu(), objFacturador.getTipDocu());
            
            /*Eliminando en DIRECTORIO DATA*/
            String rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_DATA;
            UtilClass.eliminarAchivo(rutaDirectorio, objFacturador.getNomArch());
            
//            /*Eliminando en DIRECTORIO ENVIO*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
//            UtilClass.eliminarAchivo(rutaDirectorio, objFacturador.getNomArch());
//            
//            /*Eliminando en DIRECTORIO FIRMA*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
//            UtilClass.eliminarAchivo(rutaDirectorio, objFacturador.getNomArch());
//            
//            /*Eliminando en DIRECTORIO RPTA*/
//            rutaDirectorio = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_RPTA;
//            UtilClass.eliminarAchivo(rutaDirectorio, objFacturador.getNomArch());
            
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }        
    
    /**
     * REALIZA LA COPIA DE SEGURIDAD DE TODOS LOS ARCHIVOS GENERADOS
     * @throws Exception 
     */
    public void ejecutarCopiaSeguridadArchivosGenerados() throws Exception{
        
        try{                      
            
            String rutaDirectorioDestino = this.obtRutaArchivosGenerados();
            
            /*Eliminando en DIRECTORIO DATA*/
            String rutaDirectorioOrigen  = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_DATA;
            rutaDirectorioDestino = rutaDirectorioDestino + Constantes.CONS_RUTA_FACTURADOR_DATA;
            UtilClass.copiarContenidoDirectorio(rutaDirectorioOrigen, rutaDirectorioDestino);
            
            /*Eliminando en DIRECTORIO ENVIO*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
            rutaDirectorioDestino = rutaDirectorioDestino + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
            UtilClass.copiarContenidoDirectorio(rutaDirectorioOrigen, rutaDirectorioDestino);
            
            /*Eliminando en DIRECTORIO FIRMA*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
            rutaDirectorioDestino = rutaDirectorioDestino + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
            UtilClass.copiarContenidoDirectorio(rutaDirectorioOrigen, rutaDirectorioDestino);
            
            /*Eliminando en DIRECTORIO RPTA*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_RPTA;
            rutaDirectorioDestino = rutaDirectorioDestino + Constantes.CONS_RUTA_FACTURADOR_RPTA;
            UtilClass.copiarContenidoDirectorio(rutaDirectorioOrigen, rutaDirectorioDestino);            
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    
    /**
     * REALIZA LA COPIA DE SEGURIDAD DE TODOS LOS ARCHIVOS GENERADOS
     * @param objFacturador Indica el objeto que contiene los datos del comprobante a generar su copia de seguridad
     * @throws Exception 
     */
    public void ejecutarCopiaSeguridadArchivoGenerado(Sic1docufacturadorsunat objFacturador) throws Exception{
        
        try{                      
            String rutaDirectorioDestino = "";
            String rutaDirectorioDestinoConfig = this.obtRutaArchivosGenerados();
            
            /*Eliminando en DIRECTORIO DATA*/
            String rutaDirectorioOrigen  = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_DATA;
            rutaDirectorioDestino = rutaDirectorioDestinoConfig + Constantes.CONS_RUTA_FACTURADOR_DATA;
            UtilClass.copiarArchivo(rutaDirectorioOrigen, rutaDirectorioDestino, objFacturador.getNomArch());
            
            /*Eliminando en DIRECTORIO ENVIO*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
            rutaDirectorioDestino = rutaDirectorioDestinoConfig + Constantes.CONS_RUTA_FACTURADOR_ENVIO;
            UtilClass.copiarArchivo(rutaDirectorioOrigen, rutaDirectorioDestino, objFacturador.getNomArch());
            
            /*Eliminando en DIRECTORIO FIRMA*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
            rutaDirectorioDestino = rutaDirectorioDestinoConfig + Constantes.CONS_RUTA_FACTURADOR_FIRMA;
            UtilClass.copiarArchivo(rutaDirectorioOrigen, rutaDirectorioDestino, objFacturador.getNomArch());
            
            /*Eliminando en DIRECTORIO RPTA*/
            rutaDirectorioOrigen = this.desRutaArchivosFacturador + Constantes.CONS_RUTA_FACTURADOR_RPTA;
            rutaDirectorioDestino = rutaDirectorioDestinoConfig + Constantes.CONS_RUTA_FACTURADOR_RPTA;
            UtilClass.copiarArchivo(rutaDirectorioOrigen, rutaDirectorioDestino, objFacturador.getNomArch());
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * GUARDA LOS DATOS DE LOS COMPROBANTES QUE HAN SIDO PROCESADOS PARA ENVIAR A LA SUNAT (SIC1DOCUFACTURADORSUNAT)
     * @param lst Indica la lista de los comprobantes que se están procesando
     * @throws CustomizerException
     */
    public void creaDocuFacturadorSunat(List<Sic1docufacturadorsunat> lst) throws CustomizerException, Exception{
        
        Connection cnConexion = null;
        
        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            for(Sic1docufacturadorsunat obj : lst){
                
                DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
                objDao.creaDocuFacturadorSunat(cnConexion, obj);                
            }
            
        }catch(Exception ex){
            if(cnConexion != null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion != null)
                cnConexion.close();
        }
            
    }
    
   /**
     * GUARDA LOS DATOS DEL COMPROBANTE QUE HAN SIDO PROCESADOS PARA ENVIAR A LA SUNAT (SIC1DOCUFACTURADORSUNAT)
     * @param obj Indica el objeto que contiene los datos del comprobante
     * @param cnConexion Indica la conexion
     * @throws CustomizerException     
     */
//    public void creaDocuFacturadorSunat(Sic1docufacturadorsunat obj, Connection cnConexion) throws CustomizerException{
//        
//        try{
//            
//            DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
//            objDao.creaDocuFacturadorSunat(obj,cnConexion);
//            
//        }catch(Exception ex){
//            throw new CustomizerException(ex.getMessage());
//        }
//            
//    }   
    
    /**
     * METODO QUE PERMITE REGISTRAR LA OPERACION EN LA TABLA "SIC1DOCUFACTURADORSUNAT" PARA LUEGO ENVIARLA A LA SUNAT
     * @param cnConexion
     * @param objDocu
     * @param codTipoOpeSunat
     * @throws Exception 
     */
    public void registrarDocuPendienteEnvioSunat(Connection cnConexion, Sic1docu objDocu, String codTipoOpeSunat) throws Exception{
        
        try{
            
            /*GRABAR EN TABLA DE LA SUNAT SOLO LAS OPERACIONES DE TIPO VENTA Y LAS QUE TENGAN CODIGO DE SUNAT*/
            if((objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.CONS_COD_SCLASEEVEN_VENTA) || 
                    objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.CONS_COD_SCLASEEVEN_NOTACREDITO)) &&
                        (objDocu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_FACTURA) ||
                            objDocu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_BOLETA)) &&
                                objDocu.getSic1stipodocu().getCodSunat() != null && 
                                    !objDocu.getSic1stipodocu().getCodSunat().isEmpty()){
            
                String codProc = codTipoOpeSunat;                
                
                Sic1docufacturadorsunatId objIdFact = new Sic1docufacturadorsunatId();
                objIdFact.setCodProc(codProc);
                objIdFact.setIdDocu(objDocu.getIdDocu());

                Sic1docufacturadorsunat objFact = new Sic1docufacturadorsunat();
                objFact.setNumRuc(Constantes.CONS_NUM_RUC);
                objFact.setTipDocu(objDocu.getSic1stipodocu().getCodSunat());
                
                String numDocuUnido = null;
                if(objDocu.getNumDocu() != null &&  objDocu.getNumDocu().intValue() > 0)
                    numDocuUnido = objDocu.getCodSerie() + "-" + objDocu.getNumDocu();
                
                objFact.setNumDocu(numDocuUnido);
                objFact.setId(objIdFact);

                DaoFacturadorSunatImpl objFacturadorDao = new DaoFacturadorSunatImpl();
                objFacturadorDao.creaDocuFacturadorSunat(cnConexion, objFact);
            }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * METODO QUE PERMITE REGISTRAR LA OPERACION EN LA TABLA "SIC1DOCUFACTURADORSUNAT" PARA LUEGO ENVIARLA A LA SUNAT
     * @param cnConexion
     * @param objDocu
     * @param codTipoOpeSunat
     * @throws Exception 
     */
    public void registrarDocuPendienteEnvioSunat( Sic1docu objDocu, String codTipoOpeSunat) throws Exception{
        
        Connection cnConexion = null;
        
        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            /*GRABAR EN TABLA DE LA SUNAT SOLO LAS OPERACIONES DE TIPO VENTA Y LAS QUE TENGAN CODIGO DE SUNAT*/
            if(objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.CONS_COD_SCLASEEVEN_VENTA) && 
                    objDocu.getSic1stipodocu().getCodSunat() != null && 
                        !objDocu.getSic1stipodocu().getCodSunat().isEmpty()){
            
                String codProc = codTipoOpeSunat;                
                
                Sic1docufacturadorsunatId objIdFact = new Sic1docufacturadorsunatId();
                objIdFact.setCodProc(codProc);
                objIdFact.setIdDocu(objDocu.getIdDocu());

                Sic1docufacturadorsunat objFact = new Sic1docufacturadorsunat();
                objFact.setNumRuc(Constantes.CONS_NUM_RUC);
                objFact.setNumDocu(objDocu.getCodSerie() + "-" + objDocu.getNumDocu());
                //objFact.setFlgActivo(new BigDecimal(1));
                objFact.setId(objIdFact);

                DaoFacturadorSunatImpl objFacturadorDao = new DaoFacturadorSunatImpl();
                objFacturadorDao.creaDocuFacturadorSunat(cnConexion, objFact);
            }
            
            cnConexion.commit();
            
        }catch(Exception ex){
            if(cnConexion != null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion != null)
                cnConexion.close();
        }
    }
    
    
    /**
     * DEVUELVE LOS DATOS DEL DOCUMENTO QUE YA HA SIDO ENVIADO A LA SUNAT
     * @param numRuc
     * @param numDocu
     * @return
     * @throws Exception 
     */
    public Sic1docufacturadorsunat obtDatosComprobanteEnviadoSunat( String numRuc, String numDocu) throws Exception{
        
        DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
        return objDao.obtDatosComprobanteEnviadoSunat(numRuc, numDocu);
            
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
        
        DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
        return objDao.obtDatosComprobanteFacturadorSunat(numRuc, numDocu, codTipodocuSunat);
            
    }
    
     /**
     * TRAE EL LISTADO DE TODOS LOS DOCUMENTOS QUE HAN SIDO PROCESADOS POR EL FACTURADOR DE LA SUNAT     
     * @param objFacturador
     * @return
     * @throws Exception 
     */
    public List<Sic1docufacturadorsunat> obtDatosFacturadorLocal( Sic1docufacturadorsunat objFacturador) throws Exception{
        DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
        return objDao.obtDatosFacturadorLocal(objFacturador);
    }
    
    /**
     * 
     * @return SE OBTIENE EL RESUMEN DEL PROCESO DE FACTURACION
     * @throws CustomizerException 
     */
    public List<Sic1docu> obtResumenEnvioDocuSunat(Integer idDocu, String fecDesde, String fecHasta) throws CustomizerException {
        
        List<Sic1docu> lstResult;        
        try{
           
            DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
            lstResult = objDao.obtResumenEnvioDocuSunat(idDocu, fecDesde, fecHasta);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        return lstResult;
        
    }
    
    
    
    /*********************************************************************************/
    /*************************** OBTENER PARAMETROS **********************************/
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE LA RUTA DONDE SE ALMACENARA LOS ARCHIVOS DE TEXTO GENERADOS EN SISVENTAS
     * @return
     * @throws Exception 
     */
    public static String obtRutaGrabarArchTextoFacturador() throws Exception {        
        Sic1general objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_RUTAGRABARARCHIVOSTEXTO");
        return objGeneral.getDesValor();
    }
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE LA RUTA RAIZ DEL FACTURADOR SFS SUNAT
     * @return
     * @throws Exception 
     */
    public static String obtRutaRaizFacturador() throws Exception {        
        Sic1general objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_RUTAFACTURADORSFS");
        return objGeneral.getDesValor();
    }
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE LA RUTA DE LA BASE DE DATOS DEL FACTURADOR SFS SUNAT
     * @return
     * @throws Exception 
     */
    public static String obtRutaBDFacturador() throws Exception {        
        Sic1general objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_RUTABDFACTURADORSFS");
        return objGeneral.getDesValor();
    }
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE EL EMAIL DONDE SE ENVIARA EL REPORTE DE ENVIO DE FACTURACION
     * @return
     * @throws Exception
     */
    public static String obtEmailEnvioReporteFacturacion() throws Exception {
        Sic1general objGeneral;
        objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_EMAILNOTIFACTURACION");
        return objGeneral.getDesValor();
    }
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE LA RUTA DONDE SE COPIARAN LOS ARCHIVOS FINALES GENERADOS POR EL FACTURADOR
     * @return
     * @throws Exception
     */
    public static String obtRutaArchivosGenerados() throws Exception {
        Sic1general objGeneral;
        objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_RUTAARCHIVOSGENERADOS");
        return objGeneral.getDesValor();
    }
    
    /**
     * FACTURACION ELECTRONICA: SE OBTIENE LA RUTA DONDE EL FACTURADOR SFS ALMACENA SUS ARCHIVOS
     * @return
     * @throws Exception
     */
    public static String obtRutaArchivosFacturadorSFS() throws Exception {
        Sic1general objGeneral;
        objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_RUTAARCHIVOSFACTURADOR");
        return objGeneral.getDesValor();
    }
    
    
    
    
    
}
