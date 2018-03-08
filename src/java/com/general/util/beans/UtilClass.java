/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class UtilClass implements Serializable{
    
    
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
    
    
    public static Date getCurrentDateTime(){        
        return new Date();
    }
    
    public static String getCurrentTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(new Date());
    }
    
    public static String getCurrentTime_YYYYMMDD(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }
    
    public static BigDecimal convertDateToNumber(Date value){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return new BigDecimal(df.format(value));
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
    
    /*Devuelve la fecha inicial*/
    public static int getObtFecIni_YYYYMMDD() throws ParseException{        
        return 19000101;
    }
    
     /*Devuelve la fecha infinito*/
    public static int getObtFecInf_YYYYMMDD() throws ParseException{
        return 24003112;
    }
    
    public static byte[] getBytes(InputStream is) throws IOException {

           int len;
           int size = 1024;
           byte[] buf;

           if (is instanceof ByteArrayInputStream) {
               size = is.available();
               buf = new byte[size];
               len = is.read(buf, 0, size);
           } else {
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               buf = new byte[size];
               while ((len = is.read(buf, 0, size))!= -1)
                   bos.write(buf, 0, len);
               buf = bos.toByteArray();
           }
           return buf;
    }
   
    
}
