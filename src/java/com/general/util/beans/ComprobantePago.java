/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate1.Sic1docufacturadorsunat;
import java.util.List;

/**
 *
 * @author emoreno
 */
public class ComprobantePago {
    
    private String tipOperacion;
    private String fecEmision;
    private String horEmision;
    private String fecVencimiento;
    private Integer codLocalEmisor;
    private String tipDocUsuario;
    private String numDocUsuario;
    private String rznSocialUsuario;
    private String tipMoneda;
    private Double sumTotTributos;
    private Double sumTotValVenta;
    private Double sumPrecioVenta;
    private Double sumDescTotal;
    private Double sumOtrosCargos;
    private Double sumTotalAnticipos;
    private Double sumImpVenta;
    private String ublVersionId;
    private String customizationId;
    
    private List<ComprobantePagoDet> lstCompPagoDet;
    private Sic1docu sic1docu;
    private Sic1docufacturadorsunat sic1docufactsunat;
    
    /*METODOS*/
    public String getTipOperacion() {
        return tipOperacion;
    }

    public void setTipOperacion(String tipOperacion) {
        this.tipOperacion = tipOperacion;
    }

    public String getFecEmision() {
        return fecEmision;
    }

    public void setFecEmision(String fecEmision) {
        this.fecEmision = fecEmision;
    }

    public String getHorEmision() {
        return horEmision;
    }

    public void setHorEmision(String horEmision) {
        this.horEmision = horEmision;
    }

    public String getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(String fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public Integer getCodLocalEmisor() {
        return codLocalEmisor;
    }

    public void setCodLocalEmisor(Integer codLocalEmisor) {
        this.codLocalEmisor = codLocalEmisor;
    }

    public String getTipDocUsuario() {
        return tipDocUsuario;
    }

    public void setTipDocUsuario(String tipDocUsuario) {
        this.tipDocUsuario = tipDocUsuario;
    }

    public String getNumDocUsuario() {
        return numDocUsuario;
    }

    public void setNumDocUsuario(String numDocUsuario) {
        this.numDocUsuario = numDocUsuario;
    }

    public String getRznSocialUsuario() {
        return rznSocialUsuario;
    }

    public void setRznSocialUsuario(String rznSocialUsuario) {
        this.rznSocialUsuario = rznSocialUsuario;
    }

    public String getTipMoneda() {
        return tipMoneda;
    }

    public void setTipMoneda(String tipMoneda) {
        this.tipMoneda = tipMoneda;
    }

    public Double getSumTotTributos() {
        return sumTotTributos;
    }

    public void setSumTotTributos(Double sumTotTributos) {
        this.sumTotTributos = sumTotTributos;
    }

    public Double getSumTotValVenta() {
        return sumTotValVenta;
    }

    public void setSumTotValVenta(Double sumTotValVenta) {
        this.sumTotValVenta = sumTotValVenta;
    }

    public Double getSumPrecioVenta() {
        return sumPrecioVenta;
    }

    public void setSumPrecioVenta(Double sumPrecioVenta) {
        this.sumPrecioVenta = sumPrecioVenta;
    }

    public Double getSumDescTotal() {
        return sumDescTotal;
    }

    public void setSumDescTotal(Double sumDescTotal) {
        this.sumDescTotal = sumDescTotal;
    }

    public Double getSumOtrosCargos() {
        return sumOtrosCargos;
    }

    public void setSumOtrosCargos(Double sumOtrosCargos) {
        this.sumOtrosCargos = sumOtrosCargos;
    }

    public Double getSumTotalAnticipos() {
        return sumTotalAnticipos;
    }

    public void setSumTotalAnticipos(Double sumTotalAnticipos) {
        this.sumTotalAnticipos = sumTotalAnticipos;
    }

    public Double getSumImpVenta() {
        return sumImpVenta;
    }

    public void setSumImpVenta(Double sumImpVenta) {
        this.sumImpVenta = sumImpVenta;
    }

    public String getUblVersionId() {
        return ublVersionId;
    }

    public void setUblVersionId(String ublVersionId) {
        this.ublVersionId = ublVersionId;
    }

    public String getCustomizationId() {
        return customizationId;
    }

    public void setCustomizationId(String customizationId) {
        this.customizationId = customizationId;
    }

    public List<ComprobantePagoDet> getLstCompPagoDet() {
        return lstCompPagoDet;
    }

    public void setLstCompPagoDet(List<ComprobantePagoDet> lstCompPagoDet) {
        this.lstCompPagoDet = lstCompPagoDet;
    }    

    public Sic1docu getSic1docu() {
        return sic1docu;
    }

    public void setSic1docu(Sic1docu sic1docu) {
        this.sic1docu = sic1docu;
    }

    public Sic1docufacturadorsunat getSic1docufactsunat() {
        return sic1docufactsunat;
    }

    public void setSic1docufactsunat(Sic1docufacturadorsunat sic1docufactsunat) {
        this.sic1docufactsunat = sic1docufactsunat;
    }
    
    
}
