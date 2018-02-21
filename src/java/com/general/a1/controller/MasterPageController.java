/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author emoreno
 */
@ManagedBean
@RequestScoped
public class MasterPageController {
    
    public  Constantes constantes;
    
    public String redirect(){
        
        /*VALIDAR SI SE HA APERTURADO CAJA*/
        if (false){
            UtilClass.addErrorMessage("Para continuar se debe realizar la apertura de caja.");
            return null;
        }
        
        /*Se obtiene los parametros vinculados a los links*/
        String tituloPagina  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramTituloPagina");
        String nombrePagina  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramNombrePagina");
        String codSClaseeven = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramCodSClaseeven");
        String codTRolpers   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramCodTRolpers");
        
        
        System.out.println("tituloPagina: " + tituloPagina);
        System.out.println("nombrePagina: " + nombrePagina);
        System.out.println("codSClaseeven: " + codSClaseeven);
        System.out.println("paramCodTRolpers: " + codTRolpers);
        
        /*Se guarda los valores en JSF Flash Scope*/
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.clear();
        flash.put("paramTituloPagina", tituloPagina);
        flash.put("paramCodSClaseeven", codSClaseeven);
        flash.put("paramCodTRolpers", codTRolpers);
        flash.setKeepMessages(true);
        
        return  nombrePagina + "?faces-redirect=true";
    }    
}
