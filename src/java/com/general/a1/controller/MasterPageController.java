/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author emoreno
 */
@ManagedBean
@ViewScoped
public class MasterPageController {
    
    public String redirect(){
        
        /*Se obtiene los parametros vinculados a los links*/
        String tituloPagina = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tituloPagina");
        String nombrePagina = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombrePagina");
        
        System.out.println("tituloPagina+ " + tituloPagina);
        System.out.println("nombrePagina+ " + nombrePagina);
        
        /*Se guarda los valores en JSF Flash Scope*/
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("tituloPagina", tituloPagina);        
        flash.setKeepMessages(true);
        
        return  nombrePagina + "?faces-redirect=true";
    }    
}
