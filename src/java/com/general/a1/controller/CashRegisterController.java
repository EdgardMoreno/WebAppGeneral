/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.hibernate1.Sic4cuaddiario;
import com.general.util.exceptions.CustomizerException;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emoreno
 */
@ManagedBean
@ViewScoped
public class CashRegisterController {
    
    private final static Logger log = LoggerFactory.getLogger(CashRegisterController.class);
    private Sic4cuaddiario box;
    private String desTituloPagina;
    
    private BigDecimal numCalcuDenom0200;
    private BigDecimal numCalcuDenom0100;
    private BigDecimal numCalcuDenom0050;
    private BigDecimal numCalcuDenom0020;
    private BigDecimal numCalcuDenom0010;
    private BigDecimal numCalcuDenom0005;
    private BigDecimal numCalcuDenom0002;
    private BigDecimal numCalcuDenom0001;
    private BigDecimal numCalcuDenom0_50;
    private BigDecimal numCalcuDenom0_20;
    private BigDecimal numCalcuDenom0_10;
    private BigDecimal numCalcuDenom0_05;
    
    
    @PostConstruct
    public void init() {
        
        try{
            System.out.println("initia");
            
            
            box = new Sic4cuaddiario();
//            box.setNumEfectDenom0200(new BigDecimal(0));
//            box.setNumEfectDenom0100(new BigDecimal(0));
//            box.setNumEfectDenom0050(new BigDecimal(0));

            box.setNumEfectApertCaja(new BigDecimal(0));
            box.setNumEfectGastoTotal(new BigDecimal(0));

            box.setNumEfectTotalGastoSiste(new BigDecimal(10));
            box.setNumEfectTotalVentaSiste(new BigDecimal(50));
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }   
    }
    
    /*PROPIEDADES*/  

    public Sic4cuaddiario getBox() {
        return box;
    }

    public void setBox(Sic4cuaddiario box) {
        this.box = box;
    }
    
    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public BigDecimal getNumCalcuDenom0200() {
        return numCalcuDenom0200;
    }

    public void setNumCalcuDenom0200(BigDecimal numCalcuDenom0200) {
        this.numCalcuDenom0200 = numCalcuDenom0200;
    }

    public BigDecimal getNumCalcuDenom0100() {
        return numCalcuDenom0100;
    }

    public void setNumCalcuDenom0100(BigDecimal numCalcuDenom0100) {
        this.numCalcuDenom0100 = numCalcuDenom0100;
    }

    public BigDecimal getNumCalcuDenom0050() {
        return numCalcuDenom0050;
    }

    public void setNumCalcuDenom0050(BigDecimal numCalcuDenom0050) {
        this.numCalcuDenom0050 = numCalcuDenom0050;
    }

    public BigDecimal getNumCalcuDenom0020() {
        return numCalcuDenom0020;
    }

    public void setNumCalcuDenom0020(BigDecimal numCalcuDenom0020) {
        this.numCalcuDenom0020 = numCalcuDenom0020;
    }

    public BigDecimal getNumCalcuDenom0010() {
        return numCalcuDenom0010;
    }

    public void setNumCalcuDenom0010(BigDecimal numCalcuDenom0010) {
        this.numCalcuDenom0010 = numCalcuDenom0010;
    }

    public BigDecimal getNumCalcuDenom0005() {
        return numCalcuDenom0005;
    }

    public void setNumCalcuDenom0005(BigDecimal numCalcuDenom0005) {
        this.numCalcuDenom0005 = numCalcuDenom0005;
    }

    public BigDecimal getNumCalcuDenom0002() {
        return numCalcuDenom0002;
    }

    public void setNumCalcuDenom0002(BigDecimal numCalcuDenom0002) {
        this.numCalcuDenom0002 = numCalcuDenom0002;
    }

    public BigDecimal getNumCalcuDenom0001() {
        return numCalcuDenom0001;
    }

    public void setNumCalcuDenom0001(BigDecimal numCalcuDenom0001) {
        this.numCalcuDenom0001 = numCalcuDenom0001;
    }

    public BigDecimal getNumCalcuDenom0_50() {
        return numCalcuDenom0_50;
    }

    public void setNumCalcuDenom0_50(BigDecimal numCalcuDenom0_50) {
        this.numCalcuDenom0_50 = numCalcuDenom0_50;
    }

    public BigDecimal getNumCalcuDenom0_20() {
        return numCalcuDenom0_20;
    }

    public void setNumCalcuDenom0_20(BigDecimal numCalcuDenom0_20) {
        this.numCalcuDenom0_20 = numCalcuDenom0_20;
    }

    public BigDecimal getNumCalcuDenom0_10() {
        return numCalcuDenom0_10;
    }

    public void setNumCalcuDenom0_10(BigDecimal numCalcuDenom0_10) {
        this.numCalcuDenom0_10 = numCalcuDenom0_10;
    }

    public BigDecimal getNumCalcuDenom0_05() {
        return numCalcuDenom0_05;
    }

    public void setNumCalcuDenom0_05(BigDecimal numCalcuDenom0_05) {
        this.numCalcuDenom0_05 = numCalcuDenom0_05;
    }
    
    
    
    /*METODOS*/
    
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            String tituloPagina     = (String)flash.get("paramTituloPagina"); 
            
            
            
            
            this.desTituloPagina = tituloPagina;        
        }
    }
    
    public void calcular(){
        
        /*CALCULADO SEGUN DENOMINACION*/
        double totalDenom = 0;
        
        if (this.box.getNumEfectDenom0200() != null){
            totalDenom            += this.box.getNumEfectDenom0200().multiply(new BigDecimal(200)).doubleValue();
            this.numCalcuDenom0200 = this.box.getNumEfectDenom0200().multiply(new BigDecimal(200)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0100() != null){
            totalDenom            += this.box.getNumEfectDenom0100().multiply(new BigDecimal(100)).doubleValue();
            this.numCalcuDenom0100 = this.box.getNumEfectDenom0100().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0050() != null){
            totalDenom            += this.box.getNumEfectDenom0050().multiply(new BigDecimal(50)).doubleValue();
            this.numCalcuDenom0050 = this.box.getNumEfectDenom0050().multiply(new BigDecimal(50)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0020() != null){
            totalDenom            += this.box.getNumEfectDenom0020().multiply(new BigDecimal(20)).doubleValue();
            this.numCalcuDenom0020 = this.box.getNumEfectDenom0020().multiply(new BigDecimal(20)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0010() != null){
            totalDenom            += this.box.getNumEfectDenom0010().multiply(new BigDecimal(10)).doubleValue();
            this.numCalcuDenom0010 = this.box.getNumEfectDenom0010().multiply(new BigDecimal(10)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0005() != null){
            totalDenom            += this.box.getNumEfectDenom0005().multiply(new BigDecimal(5)).doubleValue();
            this.numCalcuDenom0005 = this.box.getNumEfectDenom0005().multiply(new BigDecimal(5)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0002() != null){
            totalDenom            += this.box.getNumEfectDenom0002().multiply(new BigDecimal(2)).doubleValue();
            this.numCalcuDenom0002 = this.box.getNumEfectDenom0002().multiply(new BigDecimal(2)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0001() != null){
            totalDenom            += this.box.getNumEfectDenom0001().multiply(new BigDecimal(1)).doubleValue();
            this.numCalcuDenom0001 = this.box.getNumEfectDenom0001().multiply(new BigDecimal(1)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0_50() != null){
            totalDenom            += this.box.getNumEfectDenom0_50().multiply(new BigDecimal(0.5)).doubleValue();
            this.numCalcuDenom0_50 = this.box.getNumEfectDenom0_50().multiply(new BigDecimal(0.5)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0_20() != null){
            totalDenom            += this.box.getNumEfectDenom0_20().multiply(new BigDecimal(0.2)).doubleValue();
            this.numCalcuDenom0_20 = this.box.getNumEfectDenom0_20().multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0_10() != null){
            totalDenom            += this.box.getNumEfectDenom0_10().multiply(new BigDecimal(0.1)).doubleValue();
            this.numCalcuDenom0_10 = this.box.getNumEfectDenom0_10().multiply(new BigDecimal(0.1)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (this.box.getNumEfectDenom0_05() != null){
            totalDenom            += this.box.getNumEfectDenom0_05().multiply(new BigDecimal(0.05)).doubleValue();
            this.numCalcuDenom0_05 = this.box.getNumEfectDenom0_05().multiply(new BigDecimal(0.05)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        
        System.out.println("TOTAL DENOMINACION :" + totalDenom);
        
        this.box.setNumEfectDenomTotal(new BigDecimal(totalDenom));
        
        /*CALCULAR: TOTAL EFECTIVO:*/
        totalDenom -=  box.getNumEfectApertCaja().add(this.box.getNumEfectGastoTotal()).doubleValue();
        
        System.out.println("TOTAL EFECTIVO USUARIO:" + totalDenom);
        
        this.box.setNumEfectTotal(new BigDecimal(totalDenom).setScale(2,BigDecimal.ROUND_HALF_UP));
        
        /*CALCULAR: TOTAL EFECTIVO DEL SISTEMA*/
        box.setNumEfectTotalSistema(box.getNumEfectTotalGastoSiste().add(box.getNumEfectTotalVentaSiste()));
        System.out.println("TOTAL EFECTIVO DEL SISTEMA:" + box.getNumEfectTotalSistema());
        
        /*CALCULAR: SOBRANTE / FALTANTE*/
        box.setNumEfectSobraFalta(box.getNumEfectTotal().add(box.getNumEfectTotalSistema()));
        System.out.println("SOBRANTE / FALTANTE:" + box.getNumEfectSobraFalta());
        
        
    }
    
    
}
