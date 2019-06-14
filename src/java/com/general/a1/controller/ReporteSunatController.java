/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.ComprobantePagoService;
import com.general.a2.service.impl.FacturadorSunatServiceImpl;
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate1.Sic1docufacturadorsunat;
import com.general.hibernate1.Sic1docufacturadorsunatId;
import com.general.security.SessionUtils;
import com.general.util.beans.ComprobantePago;
import com.general.util.beans.ComunicacionBaja;
import com.general.util.beans.Constantes;
import com.general.util.beans.SendEmail;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;


/**
 *
 * @author emoreno
 */
@ManagedBean
@SessionScoped
public class ReporteSunatController implements Serializable{
    
    private List<ComprobantePago> lstCompPago;
    private List<ComunicacionBaja> lstComunicBaja;
    private String strFecDesde;
    private String strFecHasta;
    private String desTituloPagina;
    private List<Sic1general> lstConfFacturador;
    private Boolean flgRolAdmin;
    private List<Sic1docufacturadorsunat> lstFacturador;
    private Sic1docufacturadorsunat objFacturador;
    
    
    public ReporteSunatController() throws CustomizerException{
        
        lstCompPago = new ArrayList<>();
        objFacturador = new Sic1docufacturadorsunat();
    }
    
    /*PROPIEDADES*/

    public List<ComprobantePago> getLstCompPago() {
        return lstCompPago;
    }

    public void setLstCompPago(List<ComprobantePago> lstCompPago) {
        this.lstCompPago = lstCompPago;
    }

    public String getStrFecDesde() {
        return strFecDesde;
    }

    public void setStrFecDesde(String strFecDesde) {
        this.strFecDesde = strFecDesde;
    }

    public String getStrFecHasta() {
        return strFecHasta;
    }

    public void setStrFecHasta(String strFecHasta) {
        this.strFecHasta = strFecHasta;
    }    

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public List<ComunicacionBaja> getLstComunicBaja() {
        return lstComunicBaja;
    }

    public void setLstComunicBaja(List<ComunicacionBaja> lstComunicBaja) {
        this.lstComunicBaja = lstComunicBaja;
    }

    public List<Sic1general> getLstConfFacturador() {
        return lstConfFacturador;
    }

    public void setLstConfFacturador(List<Sic1general> lstConfFacturador) {
        this.lstConfFacturador = lstConfFacturador;
    }

    public Boolean getFlgRolAdmin() {
        return flgRolAdmin;
    }

    public void setFlgRolAdmin(Boolean flgRolAdmin) {
        this.flgRolAdmin = flgRolAdmin;
    }

    public List<Sic1docufacturadorsunat> getLstFacturador() {
        return lstFacturador;
    }

    public void setLstFacturador(List<Sic1docufacturadorsunat> lstFacturador) {
        this.lstFacturador = lstFacturador;
    }

    public Sic1docufacturadorsunat getObjFacturador() {
        return objFacturador;
    }

    public void setObjFacturador(Sic1docufacturadorsunat objFacturador) {
        this.objFacturador = objFacturador;
    }    
    
    
    /***********************************************************************************************/    
    /*METODOS*/
    /***********************************************************************************************/    
    public void listarComprobantesPendienteEnvio() throws Exception{
     
        try{
            
            ComprobantePagoService objService = new ComprobantePagoService();
            this.lstCompPago = objService.listarComprobantesPendienteEnvio(null);
            
            if(lstCompPago.isEmpty())
                UtilClass.addWarnMessage("No hay documentos pendientes de envío.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public void listarComunicBajaPendienteEnvio() throws Exception{
     
        try{
            
            ComprobantePagoService objService = new ComprobantePagoService();
            this.lstComunicBaja = objService.listarComunicBajaPendienteEnvio(null);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public void grabarParametros() throws Exception{
        try{
            MaestroCatalogoServiceImpl objService = new MaestroCatalogoServiceImpl();
            objService.insertarTablaMaestro(lstConfFacturador);
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
        }catch(Exception ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }
    }
    
    /**
     * Genera los archivos plano de los comprobantes de pago filtrados y los copia en las carpeta del facturador.
     * @param objComPago Indica el objeto que contiene los datos del comprobante de pago a procesar
     * @throws Exception 
     */
    public void generarArchivosFacturacionElect(ComprobantePago objComPago) throws Exception{
     
        List<ComprobantePago> lstCompPagoFiltrado = new ArrayList<>();
        
        try{            
            
            if(!this.lstCompPago.isEmpty()){
                
                int contadorBandeja = 0;            

                /*Se obtiene nuevamente los datos actualizados*/
                ComprobantePagoService objService = new ComprobantePagoService();
                List<ComprobantePago> lstCompPagoLocal = objService.listarComprobantesPendienteEnvio(null);

                /*Se actualiza la informacion en el FACTURADOR SIC*/
                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
                int cantRegistradosActualizados = objFacturadorService.sincronizarCompPagoFacturadorSunat_FacturadorSic(lstCompPagoLocal, Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
                
                if(cantRegistradosActualizados > 0)
                    lstCompPagoLocal = objService.listarComprobantesPendienteEnvio(null);                
                
                /*Se limpia la bandeja del facturador*/
                for(ComprobantePago obj: lstCompPagoLocal){
                    if(obj.getSic1docufactsunat() != null && obj.getSic1docufactsunat().getIndSitu() != null){
                        contadorBandeja++;
                    }
                }
                if(contadorBandeja==0)
                    objFacturadorService.limpiarBDyDirectoriosFacturador();
                /**/    
                
                int contador = 0;
                for(ComprobantePago objItem : lstCompPagoLocal){
                    
                    Sic1docufacturadorsunat objFacturadorLocal = objItem.getSic1docufactsunat();
                    
                    /*Solo se procesa los comprobantes pendientes de envío*/
                    if( objFacturadorLocal.getIndSitu() == null || 
                            !objFacturadorLocal.getIndSitu().equals(Constantes.CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO)){ //03 -> Enviado y Aceptado SUNAT

                        /*1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA*/
                        System.out.println("1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA");
                        if(objFacturadorLocal.getIndSitu() != null && contadorBandeja > 0)
                            objFacturadorService.limpiarBDyDirectoriosFacturador(objFacturadorLocal);

                        /*Se agrega solo los que aun no se han enviado a la SUNAT*/
                        lstCompPagoFiltrado.add(objItem);                                
                                
                        contador++;
                    }
                }                
                
                /*3) Generar archivos de texto*/
                if(!lstCompPagoFiltrado.isEmpty()){
                    objService.generarArchivosFacturacion(lstCompPagoFiltrado);
                }
                               
                if(contador == 0){
                    UtilClass.addInfoMessage("No hay archivos pendientes para procesar.");
                }else{
                    this.lstCompPago = objService.listarComprobantesPendienteEnvio(null);
                    UtilClass.addInfoMessage("Se envió " + contador + " archivo(s) para procesar.");
                }

            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * Despues que se ha enviado todos los comprobantes desde el FACTURADOR, se procede a guardar esos datos en las tablas del sistema de ventas.
     * @throws Exception 
     */
    public void obtenerResultadoDesdeFacturador() throws Exception{
     
        try{
            if(!this.lstCompPago.isEmpty()){
                
                /*Se obtiene nuevamente los datos actualizados*/
                ComprobantePagoService objService = new ComprobantePagoService();
                List<ComprobantePago> lstCompPagoLocal = objService.listarComprobantesPendienteEnvio(null);

                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();                    
                int cantRegistradosActualizados = objFacturadorService.sincronizarCompPagoFacturadorSunat_FacturadorSic(lstCompPagoLocal, Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);

                if(cantRegistradosActualizados == 0)
                    UtilClass.addInfoMessage("No se encontraron actualizaciones.");
                else
                    UtilClass.addInfoMessage("Datos actualizados correctamente.");

                this.lstCompPago = objService.listarComprobantesPendienteEnvio(null);
                
            }else
                UtilClass.addWarnMessage("No hay datos para procesar.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * GENERA COPIA DE SEGURIDAD DE LOS ARCHIVOS PARA LOS DOCUMENTOS QUE YAS SE HAN ENVIADO A LA SUNAT
     * @throws Exception 
     */
    public void ejecutarFinProceso() throws Exception{
     
        try{            
            int contador = 0;
            FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
            
            if(!this.lstCompPago.isEmpty()){
                
                for(ComprobantePago objCom : this.lstCompPago){
                    
                    Sic1docufacturadorsunat objFacturador = objCom.getSic1docufactsunat();
                    
                    if(objFacturador.getIndSitu() != null && objFacturador.getIndSitu().equals(Constantes.CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO)){

                        System.out.println("*5 Hacer copia de seguridad de archivos generados.");
                        objFacturadorService.ejecutarCopiaSeguridadArchivoGenerado(objFacturador);
                        
                        //Indicar que el documento ya fue notificado a sunat
                        ComprobantePagoService objCompagoService = new ComprobantePagoService();
                        objCompagoService.indicarDocumentoNotificadoASunat(objCom.getSic1docu().getIdDocu());
                        
                        contador++;
                    }
                }
                
                if(contador == 0){
                    UtilClass.addWarnMessage("No hay archivos pendientes para generar copia de seguridad.");
                }else{
                    UtilClass.addInfoMessage("Se generó COPIA DE SEGURIDAD de " + contador + " archivos.");  
                    this.listarComprobantesPendienteEnvio();
                }
                                
            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
                        
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * PROCEDIMIENTO PARA NOTIFICAR LOS DOCUMENTOS QUE SE HAN NOTIFICADO HOY Y LOS QUE FALTAN ENVIAR A LA SUNAT
     * @throws Exception 
     */
    public void notificarResumenEnvioDocuSunat() throws Exception{
        
        
        try{
            FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
            List<Sic1docu> lstResult = objFacturadorService.obtResumenEnvioDocuSunat(null,null,null);
            
            if(!lstResult.isEmpty()){
            
                /*Enviar por correo*/

                String destinatario =  Constantes.CONS_DES_EMAIL_ADMINISTRADOR;
                String asunto = "REPORTE ENVIO DE COMPROBANTES ELECTRONICOS A SUNAT - " + UtilClass.getCurrentDay();

                String style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 2px'";

                String cuerpo = "<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\" style=\"border-collapse:collapse;\">"
                                + "<tr style='background-color: " + Constantes.CONS_COD_COLORTABLA + "'>"
                                +       "<th>Item</th>"                                
                                +       "<th>Sucursal</th>"
                                +       "<th>Tipo Operación</th>"
                                +       "<th>Tipo Documento</th>"
                                +       "<th>Nro. Documento</th>"                        
                                +       "<th>Fecha Registro</th>"
                                +       "<th>Tipo Operación Sunat</th>"
                                +       "<th>¿Enviado Sunat?</th>"                        
                                +       "<th>Estado Sunat</th>"
                                +       "<th>Fecha Envío</th>"                                
                                +       "<th>Doc. Identidad</th>"
                                +       "<th>Cliente</th>"                                
                                + "</tr>";

                int contador = 1;
                
                for(Sic1docu objItem : lstResult){
                    
                    String fecEnvio = "";
                    
//                    if(objItem.getSic1docufacturadorsunat().getFecEnvi() != null)
//                        fecEnvio = UtilClass.convertDateToStringDDMMYYYYHHMMSS(objItem.getSic1docufacturadorsunat().getFecEnvi());
                    String codProc = objItem.getSic1docufacturadorsunat().getId().getCodProc()!=null?objItem.getSic1docufacturadorsunat().getId().getCodProc():"";
                    String FecEnvi = objItem.getSic1docufacturadorsunat().getFecEnvi()!=null?objItem.getSic1docufacturadorsunat().getFecEnvi():"";
                    
                    cuerpo += "<tr style='font-size: 11px'>"
                                + "<td style='text-align: center'>" + contador + "</td>"
                                + "<td style='text-align: center'>" + objItem.getSic1lSucursal().getDesLugar() + "</td>"
                                + "<td style='text-align: center'>" + objItem.getSic1sclaseeven().getDesSclaseeven() + "</td>"
                                + "<td style='text-align: center'>" + objItem.getSic1stipodocu().getDesStipodocu() + "</td>"
                                + "<td style='text-align: center; color: blue'>" + objItem.getCodSerie() + "-" + objItem.getNumDocu() + "</td>"
                                + "<td style='text-align: center'>" + objItem.getFecDesde() + "</td>"
                                + "<td style='text-align: center'>" + codProc + "</td>"
                                + "<td style='text-align: center'>" + (objItem.getFlgDeclaradosunat()==1?"Si":"No") + "</td>"
                                + "<td style='text-align: center'>" + (objItem.getSic1docufacturadorsunat().getDesSitu()!=null?objItem.getSic1docufacturadorsunat().getDesSitu():"")+ "</td>"
                                + "<td style='text-align: center'>" + FecEnvi + "</td>"
                                + "<td style='text-align: center'>" + objItem.getSic1persexterno().getCodIden() + "</td>"
                                + "<td style='text-align: left'>" + objItem.getSic1persexterno().getDesPers() + "</td>"
                            + "</tr>";

                    contador++;

                }

                cuerpo += "</table><br/>";

                SendEmail email = new SendEmail();
                email.sendMailSimple(destinatario, asunto, cuerpo);

                UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_EMAIL_MESSAGE);                
              
            }else{
                 UtilClass.addWarnMessage("No hay documentos pendientes para notificar.");
            }         
             
        
        }catch(Exception ex){
            UtilClass.addErrorMessage("Error al enviar el correo " + ex.getMessage());
        }        
    }
    
    /*************************************************************************************************************************/
    /************************************** COMUNICACION DE BAJA **************************************************************
     * METODO QUE GENERA LOS ARCHIVOS PARA LA COMUNICACION DE BAJA (SOLO FACTURAS)
     * @throws Exception 
     */
    public void generarArchivosComunicBaja() throws Exception{
     
        try{
            
            if(this.lstComunicBaja != null && this.lstComunicBaja.size() > 0){
                
                /*Se obtiene nuevamente los datos actualizados*/
                ComprobantePagoService objService = new ComprobantePagoService();
                List<ComunicacionBaja> lstComunicBajaLocal = objService.listarComunicBajaPendienteEnvio(null);
                
                /*Se actualiza la informacion en el FACTURADOR SIC*/
                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();                    
                int cantRegistradosActualizados = objFacturadorService.sincronizarComunicBajaFacturadorSunat_FacturadorSic(lstComunicBajaLocal, Constantes.CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA);
                
                if (cantRegistradosActualizados > 0)
                    lstComunicBajaLocal = objService.listarComunicBajaPendienteEnvio(null);

                int contadorBandeja = 0;
                  /*Se limpia la bandeja del facturador*/
                for(ComunicacionBaja obj: lstComunicBajaLocal){
                    if(obj.getSic1docufactsunat() != null && obj.getSic1docufactsunat().getIndSitu() != null){
                        contadorBandeja++;
                    }
                }
                if(contadorBandeja==0)
                    objFacturadorService.limpiarBDyDirectoriosFacturador();
                /**/    
                
                int contador = 0;
                List<ComunicacionBaja> lstCompPagoFiltrado = new ArrayList<>();
                for(ComunicacionBaja objItem : lstComunicBajaLocal){
                    
                    Sic1docufacturadorsunat objFacturadorLocal = objItem.getSic1docufactsunat();
                    
                    /*Solo se procesa los comprobantes pendientes de envío*/
                    if( objFacturadorLocal.getIndSitu() == null || 
                            !objFacturadorLocal.getIndSitu().equals(Constantes.CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO)){ //03 -> Enviado y Aceptado SUNAT

                        /*1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA*/
                        System.out.println("1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA");
                        if(objFacturadorLocal.getIndSitu() != null && contadorBandeja > 0)
                            objFacturadorService.limpiarBDyDirectoriosFacturador(objFacturadorLocal);

                        /*Se agrega solo los que aun no se han enviado a la SUNAT*/
                        lstCompPagoFiltrado.add(objItem);                                
                                
                        contador++;
                    }
                }          
                
                List<Sic1docufacturadorsunat> lstFacturaSunat = new ArrayList<>();
                for(ComunicacionBaja objItem : lstCompPagoFiltrado){                   
                    
                    Sic1docufacturadorsunatId idFacturador = new Sic1docufacturadorsunatId();
                    idFacturador.setCodProc( Constantes.CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA);
                    idFacturador.setIdDocu(objItem.getSic1docu().getIdDocu());
                    
                    Sic1docufacturadorsunat objFacturador = new Sic1docufacturadorsunat();
                    objFacturador.setTipDocu("RA");
                    objFacturador.setNumDocu("RA");
                    
                    lstFacturaSunat.add(objFacturador);
                }
                
                /*Registrar documentos a anular*/
                FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
                objFacturador.creaDocuFacturadorSunat(lstFacturaSunat);                
                
                
                /*3) Generar archivos de texto*/
                if(!lstCompPagoFiltrado.isEmpty()){
                    objService.generarArchivoComunicBaja(lstCompPagoFiltrado);
                }
                               
                if(contador == 0){
                    UtilClass.addInfoMessage("No hay archivos pendientes para procesar.");
                }else{
                    this.lstComunicBaja = objService.listarComunicBajaPendienteEnvio(null);
                    UtilClass.addInfoMessage("Se envió " + contador + " archivo(s) para procesar.");
                }              
                
            }else{
                UtilClass.addWarnMessage("No hay datos para procesar.");
            }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }   
    
    /**
     * METODO QUE SINCRONIZA LA TABLA DEL FACTURADOR CON LA TABLA DEL SISTEMA DE VENTAS
     * @throws Exception 
     */
    public void sincronizarComunicBajaFacturadorSunat_FacturadorSic() throws Exception{
     
        try{
            
            if(this.lstComunicBaja != null && this.lstComunicBaja.size() > 0){
               
                  /*Se obtiene nuevamente los datos actualizados*/
                ComprobantePagoService objService = new ComprobantePagoService();
                List<ComunicacionBaja> lstComunicBajaLocal = objService.listarComunicBajaPendienteEnvio(null);
                
                /*Se actualiza la informacion en el FACTURADOR SIC*/
                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();                    
                int antRegistradosActualizados = objFacturadorService.sincronizarComunicBajaFacturadorSunat_FacturadorSic(lstComunicBajaLocal, Constantes.CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA);
                
                
                /*  01 -> Por Generar XML
                    02 -> XML Generado
                    03 -> Enviado y Aceptado SUNAT
                    06 -> Con Errores
                */               
                
                if(antRegistradosActualizados == 0)
                    UtilClass.addInfoMessage("No se encontraron actualizaciones.");
                else{
                    UtilClass.addInfoMessage("Datos actualizados correctamente.");
                    this.lstComunicBaja = objService.listarComunicBajaPendienteEnvio(null);
                }
                    

            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /****************************** FIN COMUNICACION BAJA ****************************************************/
    /*********************************************************************************************************/
    
    /**
     * SE OBTIENE LOS DATOS DE CONFIGURACION PARA LA FACTURACION ELECTRONICA
     * @param event
     * @throws CustomizerException 
     */
    public void cargarParametrosConfFacturador(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){            
            
            this.flgRolAdmin = SessionUtils.flgRolAdmin();
            
            //Cargar los parametros de configuracion para el Facturador SFS         
            List<String> listCat = new ArrayList<>();
            listCat.add("VI_SICVERSUDM");
            MaestroCatalogoServiceImpl objMaeCataService = new MaestroCatalogoServiceImpl();
            lstConfFacturador = objMaeCataService.listByCod_ValorTipoGeneral_Sic1general(listCat);            
            
        }
    }
    
    public void buscarDatosFacturadorLocal() throws Exception{
        try{
            
            FacturadorSunatServiceImpl objService = new FacturadorSunatServiceImpl();
            this.lstFacturador = objService.obtDatosFacturadorLocal(this.objFacturador);
            System.out.println(lstFacturador.size());
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public void limpiarBusqueda(){
        this.lstFacturador = new ArrayList<>();
    }
    
    
}
