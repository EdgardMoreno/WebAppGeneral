/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.views.ViSicpers;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.el.ELException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author emoreno
 */
@ManagedBean
@RequestScoped
//@SessionScoped
public class MasterPageController implements Serializable{
    
    public Constantes constantes;
    public String desLoginUser;
    public String desTituloPagina;    
    public String desNombrePagina;
    public String codClaseeven;
    public String codSClaseeven;
    public String codTRolpers;
    public String desMensajeError;
    
    private Double numTotalVentasMetaMes;
    private Double numTotalVentasMes;
    private Double numPorcAlcanzado;
    

    public MasterPageController() throws CustomizerException{
        
        this.desLoginUser = SessionUtils.getUserCompleteName();
        
//            this.numTotalVentasMetaMes = Constantes.CONS_METAMESTOTALVENTAPAPEL;
//
//            Integer numPeri = UtilClass.getCurrentTime_YYYYMM();
//            ReportServiceImpl objService = new ReportServiceImpl();
//            this.numTotalVentasMes = objService.getTotalSales(numPeri, SessionUtils.getUserId().intValue());
//
//            this.numPorcAlcanzado = new BigDecimal((this.numTotalVentasMes/this.numTotalVentasMetaMes) * 100).setScale(2,BigDecimal.ROUND_HALF_UP ).doubleValue();
        
    }
    
    /***** PROPIEDADES *****/
    
    public String getDesLoginUser() {
        return desLoginUser;
    }

    public void setDesLoginUser(String desLoginUser) {
        this.desLoginUser = desLoginUser;
    }

    public Double getNumTotalVentasMetaMes() {
        return numTotalVentasMetaMes;
    }

    public void setNumTotalVentasMetaMes(Double numTotalVentasMetaMes) {
        this.numTotalVentasMetaMes = numTotalVentasMetaMes;
    }

    public Double getNumTotalVentasMes() {
        return numTotalVentasMes;
    }

    public void setNumTotalVentasMes(Double numTotalVentasMes) {
        this.numTotalVentasMes = numTotalVentasMes;
    }

    public Double getNumPorcAlcanzado() {
        return numPorcAlcanzado;
    }

    public void setNumPorcAlcanzado(Double numPorcAlcanzado) {
        this.numPorcAlcanzado = numPorcAlcanzado;
    }

    public String getDesMensajeError() {
        return desMensajeError;
    }

    public void setDesMensajeError(String desMensajeError) {
        this.desMensajeError = desMensajeError;
    }
    
    
    
        
    /***** METODOS *****/
    
    public void validarDatos(){
        
        try{
            String codRolesAsignados = SessionUtils.getCodigosRolPers(); //Se obtiene todos los codigos concatenados
            String codEstaCaja = SessionUtils.getCodEstaCaja();
            this.desMensajeError = null;
            
            FacesContext context = FacesContext.getCurrentInstance();

            /*Se limpia los controladores que se tenga en el FACESCONTEXT*/
            context.getExternalContext().getSessionMap().put("documentController", null);
            context.getExternalContext().getSessionMap().put("orderController", null);
            context.getExternalContext().getSessionMap().put("personController", null);
            context.getExternalContext().getSessionMap().put("reportController", null);
            context.getExternalContext().getSessionMap().put("kardexController", null);
            context.getExternalContext().getSessionMap().put("reporteSunatController", null);           
            context.getExternalContext().getSessionMap().put("productController", null);           
            context.getExternalContext().getSessionMap().put("cashRegisterController", null);    
            
            /*Se obtiene los parametros vinculados a los links*/
            this.desTituloPagina  = context.getExternalContext().getRequestParameterMap().get("paramTituloPagina");
            this.desNombrePagina  = context.getExternalContext().getRequestParameterMap().get("paramNombrePagina");
            this.codClaseeven     = context.getExternalContext().getRequestParameterMap().get("paramCodClaseeven");
            this.codSClaseeven    = context.getExternalContext().getRequestParameterMap().get("paramCodSClaseeven");
            this.codTRolpers      = context.getExternalContext().getRequestParameterMap().get("paramCodTRolpers");

            System.out.println("tituloPagina: " + this.desTituloPagina);
            System.out.println("nombrePagina: " + this.desNombrePagina);
            System.out.println("codClaseeven: " + this.codClaseeven);
            System.out.println("codSClaseeven: " + this.codSClaseeven);
            System.out.println("paramCodTRolpers: " + this.codTRolpers);

            /*VALIDAR SI SE HA APERTURADO CAJA*/
            if (codRolesAsignados.contains(Constantes.CONS_COD_VENDEDOR)){

                /*Caja No Aperturada*/
                if (codEstaCaja == null || !codEstaCaja.equals(Constantes.CONS_COD_ESTACREADO)){
                    
                    if(desNombrePagina.equalsIgnoreCase("ordenRegistrar") || 
                            desNombrePagina.equals("gastoRegistrar") || 
                                desNombrePagina.equals("cajaCuadre")){
                        
                        if (codEstaCaja != null && codEstaCaja.equalsIgnoreCase(Constantes.CONS_COD_ESTACERRADO)){
                            this.desMensajeError = "La caja ya ha sido cerrada.";
                        }else
                            this.desMensajeError = "Para continuar se debe realizar la apertura de caja."; 
                    }
                }
            }else if (codRolesAsignados.contains(Constantes.CONS_COD_ADMINISTRADOR)){ 
                
                if(desNombrePagina.equalsIgnoreCase("ordenRegistrar") ||                     
                                desNombrePagina.equals("cajaCuadre")){
                    this.desMensajeError = "No tiene privilegios."; 
                }
                
            }
            
            //Reportes contables        
            if(desNombrePagina.equals("reportes") && !codRolesAsignados.contains(Constantes.CONS_COD_ADMINISTRADOR)){            
                this.desMensajeError = "No tiene privilegios.";                
            }
                
        }catch(Exception e){
            UtilClass.addErrorMessage(e.getMessage());
        }
    }    
    
    public String irRegistrarCompra() throws CustomizerException{
              
        FacesContext context = FacesContext.getCurrentInstance();
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        objController.inicializarDatosRegistro(this.codSClaseeven, this.codTRolpers, this.desTituloPagina, false);
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
    public String irRegistrarNotaCreditoDebito() throws CustomizerException{
              
        FacesContext context = FacesContext.getCurrentInstance();
        DocumentController objController = context.getApplication().evaluateExpressionGet(context, "#{documentController}", DocumentController.class);
        objController.inicializarDatosRegistro(this.codSClaseeven, this.codTRolpers, this.desTituloPagina);
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
    public String irRegistrarGasto() throws CustomizerException{
              
        FacesContext context = FacesContext.getCurrentInstance();
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        objController.inicializarDatosRegistroGasto(this.codClaseeven, this.codTRolpers, this.desTituloPagina, false);
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
    
    public String irRegistrarVenta() throws CustomizerException, Exception{

        if(this.desMensajeError != null){
            UtilClass.addErrorMessage(this.desMensajeError);
            return "";
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
//        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
//        objController.inicializarDatosRegistro(this.codSClaseeven, this.codTRolpers, this.desTituloPagina, true);        
        
        
        context.getExternalContext().getSessionMap().put("orderController", null);            
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);

        boolean flgNuevo                = true;
        boolean flgAgregarProductos     = true;
        boolean flgEditarItemProducto   = true;
        boolean flgEditarPersona        = true;
        boolean flgEditarFecha          = true;
        boolean flgEditarFormaPago      = true;
        boolean flgMostrarFormaPago     = true;
        boolean flgEditarTipoDocumento  = true;
        boolean flgEditarNroDocumento   = true;


        objController.loadOrderDetailsForEdit(   new BigDecimal(0)
                                                ,this.desTituloPagina
                                                ,this.codSClaseeven
                                                ,new BigDecimal(0)
                                                ,new ArrayList<>()
                                                ,flgNuevo
                                                ,flgAgregarProductos
                                                ,flgEditarItemProducto
                                                ,flgEditarPersona
                                                ,flgEditarFecha
                                                ,flgEditarFormaPago
                                                ,flgMostrarFormaPago
                                                ,flgEditarTipoDocumento
                                                ,flgEditarNroDocumento);
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
    public String irRptComprasVentas(){
              
        FacesContext context = FacesContext.getCurrentInstance();
        /*Se instancia y crear el objecto "documentController" */
        DocumentController objController = context.getApplication().evaluateExpressionGet(context, "#{documentController}", DocumentController.class);
        objController.setCodSClaseeven(this.codSClaseeven);
        ViSicdocu viSicdocu = new ViSicdocu();
        viSicdocu.setCodSclaseeven(this.codSClaseeven);
        viSicdocu.setCodClaseeven(this.codClaseeven);//Para gastos
        objController.setViSicdocu(viSicdocu);
        objController.setCodTRolpers(this.codTRolpers);
        objController.setDesTituloPagina(this.desTituloPagina);
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
    public String irRptPersonas(){
              
        FacesContext context = FacesContext.getCurrentInstance();        
        PersonController objController = context.getApplication().evaluateExpressionGet(context, "#{personController}", PersonController.class);
        ViSicpers objVi = new ViSicpers();
        objVi.setCodTrolpers(codTRolpers);
        objController.setDesTituloPagina(this.desTituloPagina);
        objController.setViSicpers(objVi);
        objController.setCodTRolpers(codTRolpers);
        
        return  desNombrePagina + "?faces-redirect=true";
    }    

    
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
    
    
    public void notificarResumenEnvioDocuSunat() throws Exception{
        
        try{
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("reporteSunatController", null);
            ReporteSunatController objController = context.getApplication().evaluateExpressionGet(context, "#{reporteSunatController}", ReporteSunatController.class);
            objController.notificarResumenEnvioDocuSunat();
            
        }catch(Exception ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }
    }
    
    
    public String irPagina(){        
        
        
        try{
            
            if(this.desMensajeError != null){
                UtilClass.addErrorMessage(this.desMensajeError);
                return "";
            }
            
            System.out.println("Inicio:" + new Date());
            
            FacesContext context = FacesContext.getCurrentInstance();

            /*Se limpia los controladores que se tenga en el FACESCONTEXT*/
//            context.getExternalContext().getSessionMap().put("documentController", null);
//            context.getExternalContext().getSessionMap().put("orderController", null);
//            context.getExternalContext().getSessionMap().put("personController", null);
//            context.getExternalContext().getSessionMap().put("reportController", null);
//            context.getExternalContext().getSessionMap().put("kardexController", null);
//            context.getExternalContext().getSessionMap().put("reporteSunatController", null);           
//            context.getExternalContext().getSessionMap().put("productController", null); 

            if (desNombrePagina.contains("rptKardexResumen")){
                //context = FacesContext.getCurrentInstance();
                KardexController objController = context.getApplication().evaluateExpressionGet(context, "#{kardexController}", KardexController.class);
                objController.setDesTituloPagina(desTituloPagina);
            }else if(desNombrePagina.contains("rptOperacionesPorProducto")){
                //context = FacesContext.getCurrentInstance();
                ReportController objController = context.getApplication().evaluateExpressionGet(context, "#{reportController}", ReportController.class);
                objController.setDesTituloPagina(desTituloPagina);            
            }else if(desNombrePagina.contains("rptComprobantesPendientesEnvioSunat") || 
                        desNombrePagina.contains("rptFacturadorSunat") ||
                            desNombrePagina.contains("rptComunicBajaPendientesEnvioSunat")){                
                ReporteSunatController objController = context.getApplication().evaluateExpressionGet(context, "#{reporteSunatController}", ReporteSunatController.class);
                objController.setDesTituloPagina(desTituloPagina);
            }else if(desNombrePagina.contains("rptListadoProductos")){
                //context = FacesContext.getCurrentInstance();
                ProductController objController = context.getApplication().evaluateExpressionGet(context, "#{productController}", ProductController.class);
                objController.setDesTituloPagina(desTituloPagina);           
            }else if(desNombrePagina.contains("cajaCuadre")){
                //context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("cashRegisterController", null);
                CashRegisterController objController = context.getApplication().evaluateExpressionGet(context, "#{cashRegisterController}", CashRegisterController.class);
                objController.cargarDatosPagina(new BigDecimal(0), new BigDecimal(0), null, desTituloPagina, null);
            }
            
        
        }catch(ELException ex){
            UtilClass.addErrorMessage("Error:" + ex.getMessage());
        }catch(Exception ex){
            UtilClass.addErrorMessage("Error:" + ex.getMessage());
        }
        
        System.out.println("Fin:" + new Date());
        
        return  desNombrePagina + "?faces-redirect=true";
    }
    
}
