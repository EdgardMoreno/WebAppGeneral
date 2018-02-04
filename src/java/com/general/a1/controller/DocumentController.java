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
import com.general.util.beans.UtilClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
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
    
    @PostConstruct
    public void init() {
        
        try{
            
            documentServiceImpl = new DocuOrderServiceImpl();
            viSicdocu           = new ViSicdocu();
            lstViSicdocus       = new ArrayList();
            
        
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
    
    
    /********************************************************************/
    /***METODOS**********************************************************/
    public void filterDocuments() throws Exception{        
        
        try {

            if(this.desFecDesde != null && this.desFecDesde.trim().length() > 0)
                viSicdocu.setFecDesde(UtilClass.convertStringToDate(desFecDesde));
            if(this.desFecHasta != null && this.desFecHasta.trim().length() > 0)
                viSicdocu.setFecHasta(UtilClass.convertStringToDate(desFecHasta));

            this.lstViSicdocus = documentServiceImpl.listViSicdocu(viSicdocu);  
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public String deleteAction(ViSicdocu obj) {
              
        //lstSic1prod.remove(obj);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Fila eliminada"));        
        return "";
    }
    
    public String newDocument(){
        return "";
    }
    
    public String editAction(ViSicdocu obj ){
        return "";
    }
    
}
