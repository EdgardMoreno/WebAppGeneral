/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Edgard
 */
public class Comision implements Serializable{
    
    private BigDecimal num_Importe;
    private BigDecimal id_Modapago;
    private BigDecimal id_TipoTarjeta;
    private BigDecimal num_TotalComision_Visa_Debito;
    private BigDecimal num_TotalComision_Visa_Credito;
    private BigDecimal num_TotalComision_Visa_Banco;
    private BigDecimal num_TotalComision_Visa_Visa;
    private BigDecimal num_TotalComision_Visa_Visa_Igv;
    private BigDecimal num_TotalComision_Final;

    public BigDecimal getNum_Importe() {
        return num_Importe;
    }

    public void setNum_Importe(BigDecimal num_Importe) {
        this.num_Importe = num_Importe;
    }

    public BigDecimal getId_Modapago() {
        return id_Modapago;
    }

    public void setId_Modapago(BigDecimal id_Modapago) {
        this.id_Modapago = id_Modapago;
    }

    public BigDecimal getId_TipoTarjeta() {
        return id_TipoTarjeta;
    }

    public void setId_TipoTarjeta(BigDecimal id_TipoTarjeta) {
        this.id_TipoTarjeta = id_TipoTarjeta;
    }

    public BigDecimal getNum_TotalComision_Visa_Debito() {
        return num_TotalComision_Visa_Debito;
    }

    public void setNum_TotalComision_Visa_Debito(BigDecimal num_TotalComision_Visa_Debito) {
        this.num_TotalComision_Visa_Debito = num_TotalComision_Visa_Debito;
    }

    public BigDecimal getNum_TotalComision_Visa_Credito() {
        return num_TotalComision_Visa_Credito;
    }

    public void setNum_TotalComision_Visa_Credito(BigDecimal num_TotalComision_Visa_Credito) {
        this.num_TotalComision_Visa_Credito = num_TotalComision_Visa_Credito;
    }

    public BigDecimal getNum_TotalComision_Visa_Banco() {
        return num_TotalComision_Visa_Banco;
    }

    public void setNum_TotalComision_Visa_Banco(BigDecimal num_TotalComision_Visa_Banco) {
        this.num_TotalComision_Visa_Banco = num_TotalComision_Visa_Banco;
    }

    public BigDecimal getNum_TotalComision_Visa_Visa() {
        return num_TotalComision_Visa_Visa;
    }

    public void setNum_TotalComision_Visa_Visa(BigDecimal num_TotalComision_Visa_Visa) {
        this.num_TotalComision_Visa_Visa = num_TotalComision_Visa_Visa;
    }

    public BigDecimal getNum_TotalComision_Visa_Visa_Igv() {
        return num_TotalComision_Visa_Visa_Igv;
    }

    public void setNum_TotalComision_Visa_Visa_Igv(BigDecimal num_TotalComision_Visa_Visa_Igv) {
        this.num_TotalComision_Visa_Visa_Igv = num_TotalComision_Visa_Visa_Igv;
    }

    public BigDecimal getNum_TotalComision_Final() {
        return num_TotalComision_Final;
    }

    public void setNum_TotalComision_Final(BigDecimal num_TotalComision_Final) {
        this.num_TotalComision_Final = num_TotalComision_Final;
    }
    
    
    
    
    
}
