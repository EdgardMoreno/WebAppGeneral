/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.temp.Sic4publcanal;
import com.general.hibernate.temp.Sic4publcanalId;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author emoreno
 */

@Named
@ManagedBean
@ViewScoped
public class PublicidadController {
    
    private List<Sic4publcanal> lstPublCanal;
    private String desFecRegistro;

    
    /*PROPIEDADES*/
    public List<Sic4publcanal> getLstPublCanal() {
        return lstPublCanal;
    }

    public void setLstPublCanal(List<Sic4publcanal> lstPublCanal) {
        this.lstPublCanal = lstPublCanal;
    }

    public String getDesFecRegistro() {
        return desFecRegistro;
    }

    public void setDesFecRegistro(String desFecRegistro) {
        this.desFecRegistro = desFecRegistro;
    }
    
    
    
    @PostConstruct
    public void init() {
        
        try{
            
            MaestroCatalogoServiceImpl objService = new MaestroCatalogoServiceImpl();
            List<Sic1general> lstCatal = objService.getCatalCanalesPublicidad();
            Integer numPeri = Integer.valueOf(UtilClass.getCurrentTime_YYYYMMDD());
            desFecRegistro  = UtilClass.getCurrentDay();
            
            lstPublCanal = new ArrayList<>();
            for(Sic1general obj : lstCatal){
                
                Sic4publcanalId id = new Sic4publcanalId();                
                id.setIdPublcanal(obj.getIdGeneral());
                
                Sic4publcanal objPublCanal = new Sic4publcanal();
                objPublCanal.setDesPublCanal(obj.getDesGeneral());                
                objPublCanal.setId(id);
                
                lstPublCanal.add(objPublCanal);
                
            }

        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }
    }
        
    
    public void grabarCanalPublicidad() throws CustomizerException, ParseException{
        
        try{
            
            BigDecimal numPeri = UtilClass.convertDateToNumber(UtilClass.convertStringToDate(desFecRegistro)); 
            Date currentDay = UtilClass.getCurrentDateTime();
            for(int i = 0; i < lstPublCanal.size(); i++ ){
               lstPublCanal.get(i).getId().setNumPeri(numPeri);
               lstPublCanal.get(i).setIdPers(SessionUtils.getUserId());
               lstPublCanal.get(i).setFecCreacion(currentDay);
            }

            MaestroCatalogoServiceImpl objService = new MaestroCatalogoServiceImpl();
            objService.grabarCanalesPublicidad(this.lstPublCanal);
                        
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
       
            this.lstPublCanal.clear();
            
        }catch(CustomizerException ex){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
}
