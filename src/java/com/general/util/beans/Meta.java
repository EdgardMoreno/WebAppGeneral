/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.hibernate.entity.Sic1pers;
import java.math.BigDecimal;

/**
 *
 * @author emoreno
 */
public class Meta {
    
    private String desMeta;
    private Integer numPeri;
    private Sic1pers sic1pers;
    private String codStipoprod;
    private String desStipoprod;
    private BigDecimal numTotalventameta;
    private BigDecimal numTotalventalogrado;
    private BigDecimal numPorclogrado;
    

    public String getDesMeta() {
        return desMeta;
    }

    public void setDesMeta(String desMeta) {
        this.desMeta = desMeta;
    }
    
    public Integer getNumPeri() {
        return numPeri;
    }

    public void setNumPeri(Integer numPeri) {
        this.numPeri = numPeri;
    }

    public Sic1pers getSic1pers() {
        return sic1pers;
    }

    public void setSic1pers(Sic1pers sic1pers) {
        this.sic1pers = sic1pers;
    }

    public String getCodStipoprod() {
        return codStipoprod;
    }

    public String getDesStipoprod() {
        return desStipoprod;
    }

    public void setDesStipoprod(String desStipoprod) {
        this.desStipoprod = desStipoprod;
    }

    public void setCodStipoprod(String codStipoprod) {
        this.codStipoprod = codStipoprod;
    }

    public BigDecimal getNumTotalventameta() {
        return numTotalventameta;
    }

    public void setNumTotalventameta(BigDecimal numTotalventameta) {
        this.numTotalventameta = numTotalventameta;
    }

    public BigDecimal getNumTotalventalogrado() {
        return numTotalventalogrado;
    }

    public void setNumTotalventalogrado(BigDecimal numTotalventalogrado) {
        this.numTotalventalogrado = numTotalventalogrado;
    }   

    public BigDecimal getNumPorclogrado() {
        return numPorclogrado;
    }

    public void setNumPorclogrado(BigDecimal numPorclogrado) {
        this.numPorclogrado = numPorclogrado;
    }
    
    
    
}
