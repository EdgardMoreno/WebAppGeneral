/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author emoreno
 */
public class UtilClass {
    
    
    public static void addInfoMessage(/*String summary,*/ String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }
    
    public static void addErrorMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void addWarnMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static String getCurrentDay(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(new Date());
    }
    
    public static String getCurrentTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(new Date());
    }
    
    public static String getCurrentTime_YYYYMMDD(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }
    
    public static String convertDateToString(Date value){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(value);
    }
    
    public static Date convertStringToDate(String value) throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse(value);
    }
    
    /*Devuelve la fecha infinito*/
    public static Date getObtFecInf() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse("31/12/2400");
    }
    
    /*Devuelve la fecha inicial*/
    public static Date getObtFecIni() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse("01/01/1900");
    }
   
    
}
