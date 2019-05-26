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
            this.lstCompPago = objService.listarComprobantesPendienteEnvio(this.strFecDesde, this.strFecHasta, null);
            
            if(lstCompPago.isEmpty())
                UtilClass.addWarnMessage("No hay documentos pendientes de envío.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    public void listarComunicBajaPendienteEnvio() throws Exception{
     
        try{
            
            ComprobantePagoService objService = new ComprobantePagoService();
            this.lstComunicBaja = objService.listarComunicBajaPendienteEnvio(null, null, null);
            
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
     
        try{
            
            int contadorBandeja = 0;
            
            /*Para procesar, se obtiene la información actualizada de los comprobantes.*/
            this.obtenerResultadoDesdeFacturador();
//            ComprobantePagoService objService = new ComprobantePagoService();
//            this.lstCompPago = objService.listarComprobantesPendienteEnvio(this.strFecDesde, this.strFecHasta, null);
            
            List<ComprobantePago> lstComPagoTemp = new ArrayList<>();
//            if(objComPago != null){
//                lstComPagoTemp.add(objComPago);                
//                contadorBandeja = 1; //Si un comprobante se ejecuta de manera individual, se setea valor 1 para evitar que se limpie toda la bandeja del FACTURADOR
//            }
//            else
//                lstComPagoTemp = this.lstCompPago;
            
            lstComPagoTemp = this.lstCompPago;
            
            FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
            int contador = 0;
            
            if(!lstComPagoTemp.isEmpty()){
                
                //Se limpia la bandeja del FACTURADOR siempre y cuando no se haya seleccionado un archivo en particular.
                if(objComPago == null){

                    /*Se evalúa si el listado de los documentos serán procesados por primera vez, si es asi, se limpia toda 
                        la bandeja del FACTURADOR.
                    */                
                    for(ComprobantePago obj: lstComPagoTemp){
                        if(obj.getSic1docufactsunat() != null && obj.getSic1docufactsunat().getIndSitu() != null){
                            contadorBandeja++;
                        }
                    }
                    if(contadorBandeja==0)
                        objFacturadorService.limpiarBDyDirectoriosFacturador();
                    //
                }
                
                List<Sic1docufacturadorsunat> lstFacturador = new ArrayList<>();                
                
                List<ComprobantePago> lstComPagoFiltrado = new ArrayList<>();
                
                for(ComprobantePago objItem : lstComPagoTemp){
                    
                    Sic1docufacturadorsunat objFacturadorLocal = objItem.getSic1docufactsunat();
                    
                    /*Solo se procesa los comprobantes pendientes de envío*/
                    if( objFacturadorLocal.getIndSitu() == null || !objFacturadorLocal.getIndSitu().equals(Constantes.CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO)){ //03 -> Enviado y Aceptado SUNAT

                        /*1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA*/
                        System.out.println("1) Limpia la BD y los Directorios del Facturador: DATA,ENVIO,FIRMA,RPTA");
                        if(objFacturadorLocal.getIndSitu() != null && contadorBandeja > 0)
                            objFacturadorService.limpiarBDyDirectoriosFacturador(objFacturadorLocal);

                        /*2) Grabar archivos a procesar en en tabla SIC1DOCUFACTURADORSUNAT*/
                        System.out.println("2) Grabar archivos a procesar en la tabla SIC1DOCUFACTURADORSUNAT");
                        Sic1docufacturadorsunatId objIdFact = new Sic1docufacturadorsunatId();
                        objIdFact.setCodProc(Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
                        objIdFact.setIdDocu(objItem.getSic1docu().getIdDocu());

                        Sic1docufacturadorsunat objFact = new Sic1docufacturadorsunat();
                        objFact.setNumRuc(Constantes.CONS_NUM_RUC);
                        objFact.setNumDocu(objItem.getSic1docu().getCodSerie() + "-" + objItem.getSic1docu().getNumDocu());
                        //objFact.setFlgActivo(new BigDecimal(1));
                        objFact.setId(objIdFact);

                        lstFacturador.add(objFact);
                        lstComPagoFiltrado.add(objItem);
                        
                        contador++;
                    }                                        
                }
                
                objFacturadorService.creaDocuFacturadorSunat(lstFacturador);
                
                /*3) Generar archivos de texto*/
                if(!lstComPagoFiltrado.isEmpty()){
                    System.out.println("3) Generar archivos de texto");
                    ComprobantePagoService objService = new ComprobantePagoService();
                    objService.generarArchivosFacturacion(lstComPagoFiltrado);
                }
                               
                if(contador == 0){
                    UtilClass.addInfoMessage("No hay archivos pendientes para procesar.");
                }else{
                    UtilClass.addInfoMessage("Se envió " + contador + " archivo(s) para procesar.");
                    this.listarComprobantesPendienteEnvio();
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
            
            if(this.lstCompPago != null && this.lstCompPago.size() > 0){
               
                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
                int contador = 0;
                
                /*4 Verificar por cada archivo si ya terminó de enviarse a la sunat*/
                for(ComprobantePago objCom : this.lstCompPago){
                    
                    String numDocu = objCom.getSic1docu().getCodSerie() + "-" + objCom.getSic1docu().getNumDocu();
                    String numRuc = objCom.getSic1docu().getSic1perscontribuyente().getCodIden();
                    
                    //Se obtiene los datos del comprobante que tiene en las TABLAS del FACTURADOR SUNAT
                    Sic1docufacturadorsunat objDocu = objFacturadorService.obtDatosComprobanteFacturadorSunat(numRuc
                                                                                                             ,numDocu
                                                                                                             ,objCom.getSic1docu().getSic1stipodocu().getCodSunat());
                    
                    System.out.println("Documento:" + objDocu.getNumDocu());
                    
                    if( objDocu.getNumDocu() != null ){
                        
                        /*Completamos los datos faltantes en la tabla SIC1DOCUFACTURADORSUNAT*/
//                        Sic1docufacturadorsunatId idFacturador = new Sic1docufacturadorsunatId();
//                        idFacturador.setCodProc(Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
//                        idFacturador.setIdDocu(objCom.getSic1docu().getIdDocu());
//                        objDocu.setId(idFacturador);
//                        
//                        //Actualizando los datos
//                        List<Sic1docufacturadorsunat> lsttemp = new ArrayList<>();
//                        lsttemp.add(objDocu);
//                        objFacturadorService.creaDocuFacturadorSunat(lsttemp);
                        
                        FacturadorSunatServiceImpl objFacturadorServ = new FacturadorSunatServiceImpl();
                        objFacturadorServ.registrarDocuPendienteEnvioSunat(objCom.getSic1docu(), Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
                        
                        contador++;
                    }                                        
                }
                
                /*  01 -> Por Generar XML
                    02 -> XML Generado
                    03 -> Enviado y Aceptado SUNAT
                    06 -> Con Errores
                */               
                
                /*Actualizando la pantalla*/
                this.listarComprobantesPendienteEnvio();
                
                if(contador == 0)
                    UtilClass.addInfoMessage("No se encontraron actualizaciones.");
                else
                    UtilClass.addInfoMessage("Datos actualizados correctamente.");

            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
            
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
                
                this.obtenerResultadoComunicBajaDesdeFacturador();
                
                 /*Se evalúa si el listado de los documentos serán procesados por primera vez, si es asi, se limpia toda 
                        la bandeja del FACTURADOR.
                */                
                int contadorBandeja = 0;
                
                for(ComunicacionBaja obj: lstComunicBaja){
                    if(obj.getSic1docufactsunat() != null && obj.getSic1docufactsunat().getIndSitu() != null){
                        contadorBandeja++;
                    }
                }
                if(contadorBandeja == 0){
                    FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
                    objFacturadorService.limpiarBDyDirectoriosFacturador();
                }
                //
                
                List<ComunicacionBaja> lstComPagoFiltrado = new ArrayList<>();
                int contador = 0;
                for(ComunicacionBaja objItem : this.lstComunicBaja){
                    
                    Sic1docufacturadorsunat objFacturadorLocal = objItem.getSic1docufactsunat();
                    
                    /*Solo se procesa los comprobantes pendientes de envío*/
                    if( objFacturadorLocal.getIndSitu() == null || !objFacturadorLocal.getIndSitu().equals(Constantes.CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO)){ //03 -> Enviado y Aceptado SUNAT
                        lstComPagoFiltrado.add(objItem);
                        contador++;
                    }
                }
            
                ComprobantePagoService objService = new ComprobantePagoService();
                objService.generarArchivoComunicBaja(lstComPagoFiltrado);                
                

                if(contador == 0){
                    UtilClass.addInfoMessage("No hay archivos pendientes para procesar.");
                }else{
                    UtilClass.addInfoMessage("Se envió " + contador + " archivo(s) para procesar.");
                    this.listarComprobantesPendienteEnvio();
                }
                
            }else
                UtilClass.addErrorMessage("No existen registros para procesar.");
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }   
    
    /**
     * METODO QUE SINCRONIZA LA TABLA DEL FACTURADOR CON LA TABLA DEL SISTEMA DE VENTAS
     * @throws Exception 
     */
    public void obtenerResultadoComunicBajaDesdeFacturador() throws Exception{
     
        try{
            
            if(this.lstComunicBaja != null && this.lstComunicBaja.size() > 0){
               
                FacturadorSunatServiceImpl objFacturadorService = new FacturadorSunatServiceImpl();
                int contador = 0;
                
                /*4 Verificar por cada archivo si ya terminó de enviarse a la sunat*/
                for(ComunicacionBaja objCom : this.lstComunicBaja){
                    
                    String numDocu = objCom.getSic1docu().getCodSerie() + "-" + objCom.getSic1docu().getNumDocu();
                    String numRuc = objCom.getSic1docu().getSic1perscontribuyente().getCodIden();
                    
                    //Se obtiene los datos del comprobante que tiene en las TABLAS del FACTURADOR SUNAT
                    Sic1docufacturadorsunat objDocu = objFacturadorService.obtDatosComprobanteFacturadorSunat(numRuc
                                                                                                             ,numDocu
                                                                                                             ,objCom.getSic1docu().getSic1stipodocu().getCodSunat());
                    
                    System.out.println("Documento:" + objDocu.getNumDocu());
                    
                    if( objDocu.getNumDocu() != null ){
                        
                        /*Completamos los datos faltantes en la tabla SIC1DOCUFACTURADORSUNAT*/
//                        Sic1docufacturadorsunatId idFacturador = new Sic1docufacturadorsunatId();
//                        idFacturador.setCodProc(Constantes.CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA);
//                        idFacturador.setIdDocu(objCom.getSic1docu().getIdDocu());
//                        objDocu.setId(idFacturador);
//                        
//                        //Actualizando los datos
//                        List<Sic1docufacturadorsunat> lsttemp = new ArrayList<>();
//                        lsttemp.add(objDocu);
//                        objFacturadorService.creaDocuFacturadorSunat(lsttemp);
                        
                        FacturadorSunatServiceImpl objFacturadorServ = new FacturadorSunatServiceImpl();
                        objFacturadorServ.registrarDocuPendienteEnvioSunat(objCom.getSic1docu(), Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
                        
                        contador++;
                    }                                        
                }
                
                /*  01 -> Por Generar XML
                    02 -> XML Generado
                    03 -> Enviado y Aceptado SUNAT
                    06 -> Con Errores
                */               
                
                /*Actualizando la pantalla*/
                this.listarComunicBajaPendienteEnvio();
                
                if(contador == 0)
                    UtilClass.addInfoMessage("No se encontraron actualizaciones.");
                else
                    UtilClass.addInfoMessage("Datos actualizados correctamente.");

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
