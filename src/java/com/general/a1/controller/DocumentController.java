/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuOrderServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Edgard
 */
@Named
@ManagedBean
@ViewScoped
public class DocumentController implements Serializable{
    
    
    private final static Logger log = LoggerFactory.getLogger(DocumentController.class);    
    private DocuOrderServiceImpl documentServiceImpl;
    private List<SelectItem> itemSTipoDocu  = new ArrayList();
    private List<ViSicestageneral> itemsEstaRol  = new ArrayList();
    private ViSicdocu viSicdocu; 
    private List<ViSicdocu> lstViSicdocus;
    private String desFecDesde;
    private String desFecHasta;
    
    private String desTituloPagina;
    private String codTRolpers;
    private String codSClaseeven;
    
    private BigDecimal idDocuSelected;
    
    public void DocumentController(){
        System.out.println("Aqui");
    }
    
    @PostConstruct
    public void init() {
        
        try{
            
            documentServiceImpl = new DocuOrderServiceImpl();
            viSicdocu           = new ViSicdocu();
            lstViSicdocus       = new ArrayList();
            
            desFecDesde         = UtilClass.getCurrentDay();
            desFecHasta         = UtilClass.getCurrentDay();
            
        
            /*Cargar Catalogo: STIPODOCU*/
            Sic1generalServiceImpl sic1generalServiceImpl = new Sic1generalServiceImpl();
            List<Sic1stipodocu> lstResult = sic1generalServiceImpl.getCataDocumentsType();

            SelectItem si;
            for(Sic1stipodocu obj : lstResult){
                si = new SelectItem();
                si.setLabel(obj.getDesStipodocu());
                si.setValue(obj.getIdStipodocu());
                this.itemSTipoDocu.add(si);
            }

            /*Cargar Catalogo: STIPODOCU*/
            this.itemsEstaRol = sic1generalServiceImpl.getCataEstaRolDocuInf();
            
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }
            
    }
    
    /*PROPIEDADES*/    
    
    public List<SelectItem> getItemSTipoDocu() {
        return itemSTipoDocu;
    }

    public void setItemSTipoDocu(List<SelectItem> itemSTipoDocu) {
        this.itemSTipoDocu = itemSTipoDocu;
    }

    public ViSicdocu getViSicdocu() {
        return viSicdocu;
    }

    public void setViSicdocu(ViSicdocu viSicdocu) {
        this.viSicdocu = viSicdocu;
    }

    public List<ViSicdocu> getLstViSicdocus() {
        return lstViSicdocus;
    }

    public void setLstViSicdocus(List<ViSicdocu> lstViSicdocus) {
        this.lstViSicdocus = lstViSicdocus;
    }

    public String getDesFecDesde() {
        return desFecDesde;
    }

    public void setDesFecDesde(String desFecDesde) {
        this.desFecDesde = desFecDesde;
    }

    public String getDesFecHasta() {
        return desFecHasta;
    }

    public void setDesFecHasta(String desFecHasta) {
        this.desFecHasta = desFecHasta;
    }

    public List<ViSicestageneral> getItemsEstaRol() {
        return itemsEstaRol;
    }

    public void setItemsEstaRol(List<ViSicestageneral> itemsEstaRol) {
        this.itemsEstaRol = itemsEstaRol;
    }

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public DocuOrderServiceImpl getDocumentServiceImpl() {
        return documentServiceImpl;
    }

    public void setDocumentServiceImpl(DocuOrderServiceImpl documentServiceImpl) {
        this.documentServiceImpl = documentServiceImpl;
    }

    public String getCodTRolpers() {
        return codTRolpers;
    }

    public void setCodTRolpers(String codTRolpers) {
        this.codTRolpers = codTRolpers;
    }

    public BigDecimal getIdDocuSelected() {
        return idDocuSelected;
    }

    public void setIdDocuSelected(BigDecimal idDocuSelected) {
        this.idDocuSelected = idDocuSelected;
    }

    public String getCodSClaseeven() {
        return codSClaseeven;
    }

    public void setCodSClaseeven(String codSClaseeven) {
        this.codSClaseeven = codSClaseeven;
    }
    
    
    
    
    /********************************************************************/
    /***METODOS**********************************************************/
    public void filterDocuments() throws CustomizerException{        
        
        try {

            if(this.desFecDesde != null && this.desFecDesde.trim().length() > 0)
                viSicdocu.setFecDesde(UtilClass.convertStringToDate(desFecDesde));
            else
                 viSicdocu.setFecDesde(null);
            if(this.desFecHasta != null && this.desFecHasta.trim().length() > 0)
                viSicdocu.setFecHasta(UtilClass.convertStringToDate(desFecHasta));
            else
                viSicdocu.setFecHasta(null);

            this.lstViSicdocus = documentServiceImpl.listViSicdocu(viSicdocu);  
            
        } catch (Exception e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    public String deleteAction(ViSicdocu obj) {
              
        //lstSic1prod.remove(obj);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Fila eliminada"));        
        return "";
    }
    
    public void clearSearch(){
        viSicdocu = new ViSicdocu();
        lstViSicdocus  = new ArrayList();
    }
    
    public String editAction(ViSicdocu obj ) throws ServletException, IOException{
        
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.clear();
        flash.put("paramIdDocu", obj.getIdDocu());
        flash.put("paramTituloPagina", "VER DETALLE " + obj.getDesSclaseeven() + ": " + obj.getDesStipodocu() + " " + obj.getCodIden());
        flash.put("paramCodTRolpers", this.codTRolpers );
        flash.put("paramCodSClaseeven", obj.getCodSclaseeven());
        
        //flash.setKeepMessages(true);
        
        return "ordenDetalle?faces-redirect=true";
    }
    
    /*Metodo para anular un documento*/
    public void anulDocu() throws CustomizerException{        
        try{            
            System.out.println("id_docu:" + this.getIdDocuSelected());
            documentServiceImpl.relateDocuEsta( this.getIdDocuSelected()
                                               ,Constantes.CONS_COD_ESTADOCUCOMPROBANTE
                                               ,Constantes.CONS_COD_ESTAANULADO);
            this.filterDocuments();

        } catch (Exception e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    /*Metodo que es invocado desde la pagina xhtml: <f:metadata>*/
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            String tituloPagina     = (String)flash.get("paramTituloPagina"); 
            String codSClaseeven    = (String)flash.get("paramCodSClaseeven");
            String codTRolpersTmp      = (String)flash.get("paramCodTRolpers");
            
            /*Codigo que determinara si la operacion es una COMPRA O VENTA*/
            if (codSClaseeven != null){
                this.viSicdocu.setCodSclaseeven(codSClaseeven);
                this.codSClaseeven = codSClaseeven;
            }
            else
                throw new CustomizerException("No se cargo la sub clase del evento.");
            
            
             /*Esto permite que cuando se registra un nueva persona, se guarde con el rol 
               de CLIENTE O PROVEEDOR*/
            if (codTRolpersTmp != null)
                this.codTRolpers = codTRolpersTmp;
            else
                throw new CustomizerException("No se cargo el Tipo de Rol de la persona.");            
            
            this.desTituloPagina = tituloPagina;        
        }
    }    
}
