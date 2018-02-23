/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

/**
 *
 * @author emoreno
 */
@ManagedBean
@RequestScoped
public class MasterPageController {
    
    public  Constantes constantes;
    public String desLoginUser;

    public MasterPageController(){
        this.desLoginUser = SessionUtils.getUserCompleteName();
    }
    
    /***** PROPIEDADES *****/
    
    public String getDesLoginUser() {
        return desLoginUser;
    }

    public void setDesLoginUser(String desLoginUser) {
        this.desLoginUser = desLoginUser;
    }
    
    /***** METODOS *****/
    public String redirect(){
        
        String codTrolpers = SessionUtils.getCodTRolPers();
        String codEstaCaja = SessionUtils.getCodEstaCaja();
        /*Se obtiene los parametros vinculados a los links*/
        String tituloPagina  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramTituloPagina");
        String nombrePagina  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramNombrePagina");
        String codSClaseeven = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramCodSClaseeven");
        String codTRolpers   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramCodTRolpers");
        
        System.out.println("tituloPagina: " + tituloPagina);
        System.out.println("nombrePagina: " + nombrePagina);
        System.out.println("codSClaseeven: " + codSClaseeven);
        System.out.println("paramCodTRolpers: " + codTRolpers);
        
        /*VALIDAR SI SE HA APERTURADO CAJA*/
        if (codTrolpers.equalsIgnoreCase(Constantes.CONS_COD_VENDEDOR)){
            
            /*Caja No Aperturada*/
            if (codEstaCaja == null){
                UtilClass.addErrorMessage("Para continuar se debe realizar la apertura de caja.");
                return "";
            }
            
            /*Caja Cerrada*/
            else if (codEstaCaja != null && codEstaCaja.equalsIgnoreCase(Constantes.CONS_COD_ESTACERRADO)){
                
                /*Si está cerrada la caja no puede realizar ninguna VENTA*/
                if (codSClaseeven != null && codSClaseeven.equals("VI_SICSCLASEEVENVENTA")){
                    UtilClass.addErrorMessage("La caja ya ha sido cerrada.");
                    return "";
                }
                
                /*Si la caja está cerrada ya no se puede ver la pantalla: cajaCuadre.xhtml*/
                else if (nombrePagina.equalsIgnoreCase("cajaCuadre")){
                    UtilClass.addErrorMessage("La caja ya ha sido cerrada.");
                    return "";
                }
                
                /*switch (nombrePagina) {
                    //Si la caja esta cerrada ya no se puede ver la pantalla: cajaCuadre.xhtml
                    case "cajaCuadre": 
                         UtilClass.addErrorMessage("La caja ya ha sido cerrada.");
                         return "";
                         break;
                     default:
                         System.out.println("Ninguna opción");
                         break;
                }*/
                
            }
        }
        
        /*Se guarda los valores en JSF Flash Scope*/
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.clear();
        flash.put("paramTituloPagina", tituloPagina);
        flash.put("paramCodSClaseeven", codSClaseeven);
        flash.put("paramCodTRolpers", codTRolpers);
        flash.setKeepMessages(true);
        
        return  nombrePagina + "?faces-redirect=true";
    }
    
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
