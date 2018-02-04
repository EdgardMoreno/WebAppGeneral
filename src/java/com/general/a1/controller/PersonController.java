/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1persindi;
import com.general.hibernate.entity.Sic1persorga;
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.views.ViSicpers;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ManagedBean
@ViewScoped
public class PersonController implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(PersonController.class);
    
    private PersonServiceImpl personServiceImpl;
    private Sic1generalServiceImpl sic1generalServiceImpl;
    
    
    private ViSicpers viSicpers;
    
    private Sic1pers sic1pers;    
    private Sic1idenpers sic1idenpers;
    private Sic1idenpersId sic1idenpersId;
    private Sic1persindi sic1persindi;
    private Sic1persorga sic1persorga;
    private Sic1lugar sic1lugar;
    private String desFecNaci;
    
    private List<ViSicpers> lstPersonas = new ArrayList();
    private List<SelectItem> itemsTipoIden = new ArrayList();
    private List<SelectItem> itemsTipoPers = new ArrayList();
    private List<SelectItem> itemsGeneroPers = new ArrayList();
    
    /*Almacenada los datos que son enviados desde otra pagina*/
    private boolean paramPageFlgActivo = false;
    private String paramPageCodIden;
    
    public PersonController(){
        
    }
    
    @PostConstruct
    public void init() {
        
        try{
            
            System.out.println("paramPageFlgActivo:" + paramPageFlgActivo);
            System.out.println("paramPageCodIden:" + paramPageCodIden);
            
            viSicpers = new ViSicpers();
            
            sic1pers = new Sic1pers();
            sic1idenpers = new Sic1idenpers();
            sic1idenpersId = new Sic1idenpersId();
            sic1persindi = new Sic1persindi();
            sic1persorga = new Sic1persorga();
            sic1lugar = new Sic1lugar();
                        
            sic1pers.setIdTipopers(new BigDecimal("1"));
            
            sic1generalServiceImpl = new Sic1generalServiceImpl();
            personServiceImpl     = new PersonServiceImpl();

            /*CARGAR TIPO DE IDENTIFICADORES*/
            List<String> listCatTipoIden = new ArrayList<>();
            listCatTipoIden.add("RUC");
            listCatTipoIden.add("DNI");
            itemsTipoIden = sic1generalServiceImpl.listByCod_ValorGeneral_SelectItem(listCatTipoIden);            
            
            /*CARGAR TIPO DE PERSONA*/
            listCatTipoIden = new ArrayList<>();
            listCatTipoIden.add("VI_SICTIPOPERS"); 
            itemsTipoPers = sic1generalServiceImpl.listByCod_ValorTipoGeneral_SelectItem(listCatTipoIden);
            
            /*CARGAR GENERO*/
            itemsGeneroPers = sic1generalServiceImpl.getCataGender();
            
            if(this.paramPageFlgActivo == true)
                sic1idenpersId.setCodIden(this.paramPageCodIden);
            
        
        }catch(Exception e){            
            System.out.println("Error:" + e.getMessage());
        }
    }
   
    /*****************************************************************/ 
    /****** PROPIEDADES **********************************************/
    /*****************************************************************/ 
     public List<SelectItem> getItemsTipoIden() {
        return itemsTipoIden;
    }
    
    public List<SelectItem> getItemsTipoPers() {    
        return itemsTipoPers;
    }

    public List<SelectItem> getItemsGeneroPers() {
        return itemsGeneroPers;
    }

    public void setItemsGeneroPers(List<SelectItem> itemsGeneroPers) {
        this.itemsGeneroPers = itemsGeneroPers;
    }

    public List<ViSicpers> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<ViSicpers> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }
   

    public void setPersonaService(PersonServiceImpl personaServiceImpl) {
        this.personServiceImpl = personaServiceImpl;
    }
    
    public Sic1pers getSic1pers() {
        return sic1pers;
    }

    public void setSic1pers(Sic1pers sic1pers) {
        this.sic1pers = sic1pers;
    }

    public Sic1idenpers getSic1idenpers() {
        return sic1idenpers;
    }

    public void setSic1idenpers(Sic1idenpers sic1idenpers) {
        this.sic1idenpers = sic1idenpers;
    }

    public Sic1idenpersId getSic1idenpersId() {
        return sic1idenpersId;
    }

    public void setSic1idenpersId(Sic1idenpersId sic1idenpersId) {
        this.sic1idenpersId = sic1idenpersId;
    }

    public Sic1persindi getSic1persindi() {
        return sic1persindi;
    }

    public void setSic1persindi(Sic1persindi sic1persindi) {
        this.sic1persindi = sic1persindi;
    }

    public Sic1persorga getSic1persorga() {
        return sic1persorga;
    }

    public void setSic1persorga(Sic1persorga sic1persorga) {
        this.sic1persorga = sic1persorga;
    }

    public Sic1lugar getSic1lugar() {
        return sic1lugar;
    }

    public void setSic1lugar(Sic1lugar sic1lugar) {
        this.sic1lugar = sic1lugar;
    }

    public String getDesFecNaci() {
        return desFecNaci;
    }

    public void setDesFecNaci(String desFecNaci) {
        this.desFecNaci = desFecNaci;
    }

    public String getParamPageCodIden() {
        return paramPageCodIden;
    }

    public void setParamPageCodIden(String paramPageCodIden) {
        this.paramPageCodIden = paramPageCodIden;
    }

    public boolean isParamPageFlgActivo() {
        return paramPageFlgActivo;
    }

    public void setParamPageFlgActivo(boolean paramPageFlgActivo) {
        this.paramPageFlgActivo = paramPageFlgActivo;
    }

    public ViSicpers getViSicpers() {
        return viSicpers;
    }

    public void setViSicpers(ViSicpers viSicpers) {
        this.viSicpers = viSicpers;
    }   
    
    
    /*****************************************************************/ 
    /****** METODOS **************************************************/
    /*****************************************************************/ 
    public List<ViSicpers> filterPersons() throws CustomizerException {
        
         this.lstPersonas = personServiceImpl.listViSicPers(viSicpers);
        return lstPersonas;
    }
    
    public void newPerson(){
        
        Sic1idenpersId  sic1idenpersId = new Sic1idenpersId();
        sic1idenpersId.setCodIden(viSicpers.getCodIden());
        sic1idenpers = new Sic1idenpers();
        sic1idenpers.setId(sic1idenpersId);        
    
    }
    /*Regresar resultado a la pagina que invocó*/
    public void selectFromDialog(Sic1idenpers obj) {
        RequestContext.getCurrentInstance().closeDialog(obj);
    }
    
    public String deleteAction(Sic1idenpers obj) {

        lstPersonas.remove(obj);
        UtilClass.addInfoMessage("Fila Eliminada");
        return "";
    }
    
    public void editAction(ViSicpers obj) throws CustomizerException {
      
        /*Limpiar*/
        this.clearPers();

        //this.indexTabla = lstPersonas.indexOf(obj);
        this.sic1idenpers = this.personServiceImpl.getByCodiden(obj.getCodIden());
        this.sic1pers = this.sic1idenpers.getSic1pers();
        this.sic1idenpersId = this.sic1idenpers.getId();
        
        if(sic1pers.getSic1persindi() != null)
            this.sic1persindi = sic1pers.getSic1persindi();
        else if(sic1pers.getSic1persorga() != null)
            this.sic1persorga = sic1pers.getSic1persorga();
        
        
    }    
    
    public void clearPers(){
        
        sic1pers = new Sic1pers();
        sic1idenpers = new Sic1idenpers();
        sic1idenpersId = new Sic1idenpersId();
        sic1persindi = new Sic1persindi();
        sic1persorga = new Sic1persorga();
    }
    
     
    public void savePerson() throws CustomizerException{
        
        String strResul = "";
        try {
            
            //System.out.println("Valor:" + sic1pers.getIdTipopers());
            System.out.println("Persona:" + sic1pers.getDesPers());
            System.out.println("Fecha Nacimiento:" + this.desFecNaci);
            
            /*Deduciendo el Tipo de documento y persona*/
            if(sic1idenpersId.getCodIden().trim().length() == Constantes.CONS_VALUE_TIPOPERS_RUC 
                    && sic1idenpersId.getCodIden().startsWith("10", 0)){
                
                sic1pers.setCodTipoiden(Constantes.CONS_COD_TIPOIDEN_RUC);
                sic1pers.setCodTipopers(Constantes.CONS_COD_TIPOPERS_NATURAL);
                
            }else if(sic1idenpersId.getCodIden().trim().length() == Constantes.CONS_VALUE_TIPOPERS_RUC
                    && sic1idenpersId.getCodIden().startsWith("20", 0)){
                
                sic1pers.setCodTipoiden(Constantes.CONS_COD_TIPOIDEN_RUC);
                sic1pers.setCodTipopers(Constantes.CONS_COD_TIPOPERS_JURIDICO);                
            
            }else if(sic1idenpersId.getCodIden().trim().length() == Constantes.CONS_VALUE_TIPOPERS_DNI){
                
                sic1pers.setCodTipoiden(Constantes.CONS_COD_TIPOIDEN_DNI);
                sic1pers.setCodTipopers(Constantes.CONS_COD_TIPOPERS_NATURAL);
                
            }else {
                
                sic1pers.setCodTipoiden(Constantes.CONS_COD_TIPOIDEN_OTROS_NAT);
                sic1pers.setCodTipopers(Constantes.CONS_COD_TIPOPERS_NATURAL);
            }
            

//            Set<Sic1idenpers> lstSic1idenpers = new HashSet<>();
//            this.sic1idenpers.setId(this.sic1idenpersId);
//            lstSic1idenpers.add(this.sic1idenpers);
//            this.sic1pers.setSic1idenpers(lstSic1idenpers);

            /*PERSONA NATURAL*/
            if(desFecNaci != null && !desFecNaci.isEmpty())
                this.sic1persindi.setFecNaci(UtilClass.convertStringToDate(this.desFecNaci));            
            this.sic1pers.setSic1persindi(this.sic1persindi);
            
            /*PERSONA JURIDICA*/
            this.sic1pers.setSic1persorga(this.sic1persorga);
            this.sic1pers.setSic1lugar(this.sic1lugar);            
            
            personServiceImpl = new PersonServiceImpl();
            this.sic1idenpers.setSic1pers(sic1pers);
            this.sic1idenpers.setId(sic1idenpersId);
            
            strResul = this.personServiceImpl.insert(this.sic1idenpers);
            
            System.out.println("Resultado:" + strResul);
            
            sic1pers = new Sic1pers();
            sic1idenpers = new Sic1idenpers();
            sic1idenpersId = new Sic1idenpersId();
            sic1persindi = new Sic1persindi();
            sic1persorga = new Sic1persorga();
            sic1lugar = new Sic1lugar();
            
            if(this.paramPageFlgActivo == true)
                RequestContext.getCurrentInstance().closeDialog(sic1idenpers);
           
            
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);       
                    
        } catch (Exception e) {
          throw new CustomizerException(e.getMessage());
        } 
    }
    
    public void eliminarPersona(Sic1pers sic1pers){
        
    }
    
}
