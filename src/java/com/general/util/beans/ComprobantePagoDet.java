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
public class ComprobantePagoDet {
 
    private String codUnidadMedida;
    private String ctdUnidadItem;
    private String codProducto;
    private String codProductoSUNAT;
    private String desItem;
    private Double mtoValorUnitario;
    private Double sumTotTributosItem;
    private Integer codTriIGV;
    private String mtoIgvItem;
    private Double mtoBaseIgvItem;
    private String nomTributoIgvItem;
    private String codTipTributoIgvItem;
    private String tipAfeIGV;
    private String porIgvItem;

    //Tributo ISC (2000)
    
    private String codTriISC;
    private Double mtoIscItem;
    private Double mtoBaseIscItem;
    private String nomTributoIscItem;
    private String codTipTributoIscItem;
    private String tipSisISC;
    private Double porIscItem;
    
    //Tributo Otro 9999
    private String codTriOtroItem;
    private Double mtoTriOtroItem;
    private Double mtoBaseTriOtroItem;
    private String nomTributoIOtroItem;
    private String codTipTributoIOtroItem;
    private Double porTriOtroItem;
    private Double mtoPrecioVentaUnitario;
    private Double mtoValorVentaItem;
    private Double mtoValorReferencialUnitario;

    public String getCodUnidadMedida() {
        return codUnidadMedida;
    }

    public void setCodUnidadMedida(String codUnidadMedida) {
        this.codUnidadMedida = codUnidadMedida;
    }

    public String getCtdUnidadItem() {
        return ctdUnidadItem;
    }

    public void setCtdUnidadItem(String ctdUnidadItem) {
        this.ctdUnidadItem = ctdUnidadItem;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getCodProductoSUNAT() {
        return codProductoSUNAT;
    }

    public void setCodProductoSUNAT(String codProductoSUNAT) {
        this.codProductoSUNAT = codProductoSUNAT;
    }

    public String getDesItem() {
        return desItem;
    }

    public void setDesItem(String desItem) {
        this.desItem = desItem;
    }

    public Double getMtoValorUnitario() {
        return mtoValorUnitario;
    }

    public void setMtoValorUnitario(Double mtoValorUnitario) {
        this.mtoValorUnitario = mtoValorUnitario;
    }

    public Double getSumTotTributosItem() {
        return sumTotTributosItem;
    }

    public void setSumTotTributosItem(Double sumTotTributosItem) {
        this.sumTotTributosItem = sumTotTributosItem;
    }

    public Integer getCodTriIGV() {
        return codTriIGV;
    }

    public void setCodTriIGV(Integer codTriIGV) {
        this.codTriIGV = codTriIGV;
    }

    public String getMtoIgvItem() {
        return mtoIgvItem;
    }

    public void setMtoIgvItem(String mtoIgvItem) {
        this.mtoIgvItem = mtoIgvItem;
    }

    public Double getMtoBaseIgvItem() {
        return mtoBaseIgvItem;
    }

    public void setMtoBaseIgvItem(Double mtoBaseIgvItem) {
        this.mtoBaseIgvItem = mtoBaseIgvItem;
    }

    public String getNomTributoIgvItem() {
        return nomTributoIgvItem;
    }

    public void setNomTributoIgvItem(String nomTributoIgvItem) {
        this.nomTributoIgvItem = nomTributoIgvItem;
    }

    public String getCodTipTributoIgvItem() {
        return codTipTributoIgvItem;
    }

    public void setCodTipTributoIgvItem(String codTipTributoIgvItem) {
        this.codTipTributoIgvItem = codTipTributoIgvItem;
    }

    public String getTipAfeIGV() {
        return tipAfeIGV;
    }

    public void setTipAfeIGV(String tipAfeIGV) {
        this.tipAfeIGV = tipAfeIGV;
    }

    public String getPorIgvItem() {
        return porIgvItem;
    }

    public void setPorIgvItem(String porIgvItem) {
        this.porIgvItem = porIgvItem;
    }

    public String getCodTriISC() {
        return codTriISC;
    }

    public void setCodTriISC(String codTriISC) {
        this.codTriISC = codTriISC;
    }

    public Double getMtoIscItem() {
        return mtoIscItem;
    }

    public void setMtoIscItem(Double mtoIscItem) {
        this.mtoIscItem = mtoIscItem;
    }

    public Double getMtoBaseIscItem() {
        return mtoBaseIscItem;
    }

    public void setMtoBaseIscItem(Double mtoBaseIscItem) {
        this.mtoBaseIscItem = mtoBaseIscItem;
    }

    public String getNomTributoIscItem() {
        return nomTributoIscItem;
    }

    public void setNomTributoIscItem(String nomTributoIscItem) {
        this.nomTributoIscItem = nomTributoIscItem;
    }

    public String getCodTipTributoIscItem() {
        return codTipTributoIscItem;
    }

    public void setCodTipTributoIscItem(String codTipTributoIscItem) {
        this.codTipTributoIscItem = codTipTributoIscItem;
    }

    public String getTipSisISC() {
        return tipSisISC;
    }

    public void setTipSisISC(String tipSisISC) {
        this.tipSisISC = tipSisISC;
    }

    public Double getPorIscItem() {
        return porIscItem;
    }

    public void setPorIscItem(Double porIscItem) {
        this.porIscItem = porIscItem;
    }

    public String getCodTriOtroItem() {
        return codTriOtroItem;
    }

    public void setCodTriOtroItem(String codTriOtroItem) {
        this.codTriOtroItem = codTriOtroItem;
    }

    public Double getMtoTriOtroItem() {
        return mtoTriOtroItem;
    }

    public void setMtoTriOtroItem(Double mtoTriOtroItem) {
        this.mtoTriOtroItem = mtoTriOtroItem;
    }

    public Double getMtoBaseTriOtroItem() {
        return mtoBaseTriOtroItem;
    }

    public void setMtoBaseTriOtroItem(Double mtoBaseTriOtroItem) {
        this.mtoBaseTriOtroItem = mtoBaseTriOtroItem;
    }

    public String getNomTributoIOtroItem() {
        return nomTributoIOtroItem;
    }

    public void setNomTributoIOtroItem(String nomTributoIOtroItem) {
        this.nomTributoIOtroItem = nomTributoIOtroItem;
    }

    public String getCodTipTributoIOtroItem() {
        return codTipTributoIOtroItem;
    }

    public void setCodTipTributoIOtroItem(String codTipTributoIOtroItem) {
        this.codTipTributoIOtroItem = codTipTributoIOtroItem;
    }

    public Double getPorTriOtroItem() {
        return porTriOtroItem;
    }

    public void setPorTriOtroItem(Double porTriOtroItem) {
        this.porTriOtroItem = porTriOtroItem;
    }
        
    
    public Double getMtoPrecioVentaUnitario() {
        return mtoPrecioVentaUnitario;
    }

    public void setMtoPrecioVentaUnitario(Double mtoPrecioVentaUnitario) {
        this.mtoPrecioVentaUnitario = mtoPrecioVentaUnitario;
    }

    public Double getMtoValorVentaItem() {
        return mtoValorVentaItem;
    }

    public void setMtoValorVentaItem(Double mtoValorVentaItem) {
        this.mtoValorVentaItem = mtoValorVentaItem;
    }

    public Double getMtoValorReferencialUnitario() {
        return mtoValorReferencialUnitario;
    }

    public void setMtoValorReferencialUnitario(Double mtoValorReferencialUnitario) {
        this.mtoValorReferencialUnitario = mtoValorReferencialUnitario;
    }

    
    
    
}
