/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.CashRegisterServiceImpl;
import com.general.hibernate1.Sic4cuaddiario;
import com.general.hibernate1.Sic4cuaddiarioId;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author emoreno
 */
@ManagedBean
@RequestScoped
public class IndexController {
    
    private int flgCajaAperturada;
    private BigDecimal numMtoApertura;

    
    @PostConstruct
    public void init(){
        
        try {
            
            this.flgCajaAperturada = 0;
            
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(new BigDecimal(3)); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));
            Sic4cuaddiario obj = service.getById(id);
            
            if (obj != null)
                this.flgCajaAperturada = 1;
            
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*PROPIEDAS*/
    public int getFlgCajaAperturada() {
        return flgCajaAperturada;
    }

    public void setFlgCajaAperturada(int flgCajaAperturada) {
        this.flgCajaAperturada = flgCajaAperturada;
    }

    public BigDecimal getNumMtoApertura() {
        return numMtoApertura;
    }

    public void setNumMtoApertura(BigDecimal numMtoApertura) {
        this.numMtoApertura = numMtoApertura;
    }
    
    
    
    /*METODOS*/
    
    public void openCashRegister() throws CustomizerException{
        
        try{
        
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();

            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(new BigDecimal(3)); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));

            Sic4cuaddiario obj = new Sic4cuaddiario();
            obj.setId(id);
            service.open(obj);
            
            
            this.flgCajaAperturada = 1;

            UtilClass.addInfoMessage("Caja aperturada correctamente.");
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }    
}
