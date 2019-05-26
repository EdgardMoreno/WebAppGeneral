/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.util.beans.Constantes;
import com.general.util.beans.SendEmail;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.File;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;

/**
 *
 * @author emoreno
 */
@ManagedBean
@ViewScoped
public class EmailController {
    
    private String desRecipients;
    private String desSubject;
    private String desMessage;
    private Part uploadFile;
    
    private int paramPageFlgActivo = 0;
    
    /*PROPIEDADES*/

    public String getDesRecipients() {
        return desRecipients;
    }

    public void setDesRecipients(String desRecipients) {
        this.desRecipients = desRecipients;
    }

    public String getDesSubject() {
        return desSubject;
    }

    public void setDesSubject(String desSubject) {
        this.desSubject = desSubject;
    }

    public String getDesMessage() {
        return desMessage;
    }

    public void setDesMessage(String desMessage) {
        this.desMessage = desMessage;
    }

    public Part getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(Part uploadFile) {
        this.uploadFile = uploadFile;
    }

    public int getParamPageFlgActivo() {
        return paramPageFlgActivo;
    }

    public void setParamPageFlgActivo(int paramPageFlgActivo) {
        this.paramPageFlgActivo = paramPageFlgActivo;
    }
    
    
    
    /*METODOS*/
    public void send(){
        
        try{
        
            File attachFile = null;
            SendEmail email = new SendEmail();
            
            if(this.uploadFile != null){                
                String fileName = this.uploadFile.getSubmittedFileName();
                attachFile = UtilClass.stream2file(this.uploadFile.getInputStream(), fileName);
            }
            
            email.sendMailAttachFile(this.desRecipients.trim(), this.desSubject.trim(), this.desMessage, attachFile);
            
            uploadFile    = null;
            desRecipients = null;
            desSubject    = null;
            desMessage    = null;

            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_EMAIL_MESSAGE);
        }catch(Exception ex){
            UtilClass.addErrorMessage(Constantes.CONS_ERROR_EMAIL_MESSAGE + " Detalle: " + ex.getMessage());
        }
    }
    
    public void clear(){
        uploadFile    = null;
        desRecipients = null;
        desSubject    = null;
        desMessage    = null;
    }
    
    
    public void loadPage(ComponentSystemEvent event) throws CustomizerException{
        
        System.out.println("paramPageFlgActivo: " + this.paramPageFlgActivo);
        System.out.println("desRecipients: " + this.desRecipients );
        System.out.println("desSubject: " + this.desSubject);

    }
}
