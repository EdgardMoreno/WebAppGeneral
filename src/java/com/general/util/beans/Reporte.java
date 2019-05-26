/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.hibernate.relaentity.Sic3docuprod;
import java.math.BigDecimal;

/**
 *
 * @author Edgard
 */
public class Reporte {
    
    
    private Integer numItem;
    private String desTipo;
    private String codTipo;
    private String desItem;
    private BigDecimal numMtototal;    
    
    private Sic3docuprod detSale;
    private Sic3docuprod detPurchase;
    private Sic3docuprod detOperacion;

    public Sic3docuprod getDetSale() {
        return detSale;
    }

    public void setDetSale(Sic3docuprod detSale) {
        this.detSale = detSale;
    }

    public Sic3docuprod getDetPurchase() {
        return detPurchase;
    }

    public void setDetPurchase(Sic3docuprod detPurchase) {
        this.detPurchase = detPurchase;
    }

    public Sic3docuprod getDetOperacion() {
        return detOperacion;
    }

    public void setDetOperacion(Sic3docuprod detOperacion) {
        this.detOperacion = detOperacion;
    }

    public Integer getNumItem() {
        return numItem;
    }

    public void setNumItem(Integer numItem) {
        this.numItem = numItem;
    }

    public String getDesTipo() {
        return desTipo;
    }

    public void setDesTipo(String desTipo) {
        this.desTipo = desTipo;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getDesItem() {
        return desItem;
    }

    public void setDesItem(String desItem) {
        this.desItem = desItem;
    }

    public BigDecimal getNumMtototal() {
        return numMtototal;
    }

    public void setNumMtototal(BigDecimal numMtototal) {
        this.numMtototal = numMtototal;
    }
}
