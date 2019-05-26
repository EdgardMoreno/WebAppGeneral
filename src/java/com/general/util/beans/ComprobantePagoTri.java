/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

/**
 *
 * @author emoreno
 */
public class ComprobantePagoTri {
    
    private Integer ideTributo;
    private String nomTributo;
    private String codTipTributo;
    private Double mtoBaseImponible;
    private Double mtoTributo;

    public Integer getIdeTributo() {
        return ideTributo;
    }

    public void setIdeTributo(Integer ideTributo) {
        this.ideTributo = ideTributo;
    }

    public String getNomTributo() {
        return nomTributo;
    }

    public void setNomTributo(String nomTributo) {
        this.nomTributo = nomTributo;
    }

    public String getCodTipTributo() {
        return codTipTributo;
    }

    public void setCodTipTributo(String codTipTributo) {
        this.codTipTributo = codTipTributo;
    }

    public Double getMtoBaseImponible() {
        return mtoBaseImponible;
    }

    public void setMtoBaseImponible(Double mtoBaseImponible) {
        this.mtoBaseImponible = mtoBaseImponible;
    }

    public Double getMtoTributo() {
        return mtoTributo;
    }

    public void setMtoTributo(Double mtoTributo) {
        this.mtoTributo = mtoTributo;
    }
    
    
    
}
