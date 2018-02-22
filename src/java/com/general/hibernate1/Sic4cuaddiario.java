package com.general.hibernate1;
// Generated 20/02/2018 11:01:48 AM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Sic4cuaddiario generated by hbm2java
 */
public class Sic4cuaddiario  implements java.io.Serializable {


     private Sic4cuaddiarioId id;
     private Date fecApertura;
     private Date fecCierre;
     private BigDecimal numEfectDenom0200;
     private BigDecimal numEfectDenom0100;
     private BigDecimal numEfectDenom0050;
     private BigDecimal numEfectDenom0020;
     private BigDecimal numEfectDenom0010;
     private BigDecimal numEfectDenom0005;
     private BigDecimal numEfectDenom0002;
     private BigDecimal numEfectDenom0001;
     private BigDecimal numEfectDenom0_50;
     private BigDecimal numEfectDenom0_20;
     private BigDecimal numEfectDenom0_10;
     private BigDecimal numEfectDenom0_05;
     private BigDecimal numEfectDenomTotal;
     private BigDecimal numEfectGastoTotal;
     private BigDecimal numEfectApertCaja;
     private BigDecimal numEfectTotal;
     private BigDecimal numEfectTotalVentaSiste;
     private BigDecimal numEfectTotalGastoSiste;
     private BigDecimal numEfectTotalSistema;
     private BigDecimal numEfectSobraFalta;
     private BigDecimal numTarjeCrediTotal;
     private BigDecimal numTarjeDebitTotal;
     private BigDecimal numTarjeTotal;
     private BigDecimal numTarjeTotalSiste;
     private BigDecimal numTarjeSobraFalta;
     private BigDecimal numTarjeGastoTotal;
     private BigDecimal numTarjeGastoTotalSiste;
     private BigDecimal numGastoSobraFalta;
     private BigDecimal idEsta;

    public Sic4cuaddiario() {
    }

	
    public Sic4cuaddiario(Sic4cuaddiarioId id) {
        this.id = id;
    }
    public Sic4cuaddiario(Sic4cuaddiarioId id, Date fecApertura, Date fecCierre, BigDecimal numEfectDenom0200, BigDecimal numEfectDenom0100, BigDecimal numEfectDenom0050, BigDecimal numEfectDenom0020, BigDecimal numEfectDenom0010, BigDecimal numEfectDenom0005, BigDecimal numEfectDenom0002, BigDecimal numEfectDenom0001, BigDecimal numEfectDenom050, BigDecimal numEfectDenom020, BigDecimal numEfectDenom010, BigDecimal numEfectDenom005, BigDecimal numEfectDenomTotal, BigDecimal numEfectGastoTotal, BigDecimal numEfectApertCaja, BigDecimal numEfectTotal, BigDecimal numEfectTotalVentaSiste, BigDecimal numEfectTotalGastoSiste, BigDecimal numEfectTotalSistema, BigDecimal numEfectSobraFalta, BigDecimal numTarjeCrediTotal, BigDecimal numTarjeDebitTotal, BigDecimal numTarjeTotal, BigDecimal numTarjeTotalSiste, BigDecimal numTarjeSobraFalta, BigDecimal numTarjeGastoTotal, BigDecimal numTarjeGastoTotalSiste, BigDecimal numGastoSobraFalta) {
       this.id = id;
       this.fecApertura = fecApertura;
       this.fecCierre = fecCierre;
       this.numEfectDenom0200 = numEfectDenom0200;
       this.numEfectDenom0100 = numEfectDenom0100;
       this.numEfectDenom0050 = numEfectDenom0050;
       this.numEfectDenom0020 = numEfectDenom0020;
       this.numEfectDenom0010 = numEfectDenom0010;
       this.numEfectDenom0005 = numEfectDenom0005;
       this.numEfectDenom0002 = numEfectDenom0002;
       this.numEfectDenom0001 = numEfectDenom0001;
//       this.numEfectDenom050 = numEfectDenom050;
//       this.numEfectDenom020 = numEfectDenom020;
//       this.numEfectDenom010 = numEfectDenom010;
//       this.numEfectDenom005 = numEfectDenom005;
       this.numEfectDenomTotal = numEfectDenomTotal;
       this.numEfectGastoTotal = numEfectGastoTotal;
       this.numEfectApertCaja = numEfectApertCaja;
       this.numEfectTotal = numEfectTotal;
       this.numEfectTotalVentaSiste = numEfectTotalVentaSiste;
       this.numEfectTotalGastoSiste = numEfectTotalGastoSiste;
       this.numEfectTotalSistema = numEfectTotalSistema;
       this.numEfectSobraFalta = numEfectSobraFalta;
       this.numTarjeCrediTotal = numTarjeCrediTotal;
       this.numTarjeDebitTotal = numTarjeDebitTotal;
       this.numTarjeTotal = numTarjeTotal;
       this.numTarjeTotalSiste = numTarjeTotalSiste;
       this.numTarjeSobraFalta = numTarjeSobraFalta;
       this.numTarjeGastoTotal = numTarjeGastoTotal;
       this.numTarjeGastoTotalSiste = numTarjeGastoTotalSiste;
       this.numGastoSobraFalta = numGastoSobraFalta;
    }
   
    public Sic4cuaddiarioId getId() {
        return this.id;
    }
    
    public void setId(Sic4cuaddiarioId id) {
        this.id = id;
    }
    public Date getFecApertura() {
        return this.fecApertura;
    }
    
    public void setFecApertura(Date fecApertura) {
        this.fecApertura = fecApertura;
    }
    public Date getFecCierre() {
        return this.fecCierre;
    }
    
    public void setFecCierre(Date fecCierre) {
        this.fecCierre = fecCierre;
    }
    public BigDecimal getNumEfectDenom0200() {
        return this.numEfectDenom0200;
    }
    
    public void setNumEfectDenom0200(BigDecimal numEfectDenom0200) {
        this.numEfectDenom0200 = numEfectDenom0200;
    }
    public BigDecimal getNumEfectDenom0100() {
        return this.numEfectDenom0100;
    }
    
    public void setNumEfectDenom0100(BigDecimal numEfectDenom0100) {
        this.numEfectDenom0100 = numEfectDenom0100;
    }
    public BigDecimal getNumEfectDenom0050() {
        return this.numEfectDenom0050;
    }
    
    public void setNumEfectDenom0050(BigDecimal numEfectDenom0050) {
        this.numEfectDenom0050 = numEfectDenom0050;
    }
    public BigDecimal getNumEfectDenom0020() {
        return this.numEfectDenom0020;
    }
    
    public void setNumEfectDenom0020(BigDecimal numEfectDenom0020) {
        this.numEfectDenom0020 = numEfectDenom0020;
    }
    public BigDecimal getNumEfectDenom0010() {
        return this.numEfectDenom0010;
    }
    
    public void setNumEfectDenom0010(BigDecimal numEfectDenom0010) {
        this.numEfectDenom0010 = numEfectDenom0010;
    }
    public BigDecimal getNumEfectDenom0005() {
        return this.numEfectDenom0005;
    }
    
    public void setNumEfectDenom0005(BigDecimal numEfectDenom0005) {
        this.numEfectDenom0005 = numEfectDenom0005;
    }
    public BigDecimal getNumEfectDenom0002() {
        return this.numEfectDenom0002;
    }
    
    public void setNumEfectDenom0002(BigDecimal numEfectDenom0002) {
        this.numEfectDenom0002 = numEfectDenom0002;
    }
    public BigDecimal getNumEfectDenom0001() {
        return this.numEfectDenom0001;
    }
    
    public void setNumEfectDenom0001(BigDecimal numEfectDenom0001) {
        this.numEfectDenom0001 = numEfectDenom0001;
    }

    public BigDecimal getNumEfectDenom0_50() {
        return numEfectDenom0_50;
    }

    public void setNumEfectDenom0_50(BigDecimal numEfectDenom0_50) {
        this.numEfectDenom0_50 = numEfectDenom0_50;
    }

    public BigDecimal getNumEfectDenom0_20() {
        return numEfectDenom0_20;
    }

    public void setNumEfectDenom0_20(BigDecimal numEfectDenom0_20) {
        this.numEfectDenom0_20 = numEfectDenom0_20;
    }

    public BigDecimal getNumEfectDenom0_10() {
        return numEfectDenom0_10;
    }

    public void setNumEfectDenom0_10(BigDecimal numEfectDenom0_10) {
        this.numEfectDenom0_10 = numEfectDenom0_10;
    }

    public BigDecimal getNumEfectDenom0_05() {
        return numEfectDenom0_05;
    }

    public void setNumEfectDenom0_05(BigDecimal numEfectDenom0_05) {
        this.numEfectDenom0_05 = numEfectDenom0_05;
    }
    
    public BigDecimal getNumEfectDenomTotal() {
        return this.numEfectDenomTotal;
    }
    
    public void setNumEfectDenomTotal(BigDecimal numEfectDenomTotal) {
        this.numEfectDenomTotal = numEfectDenomTotal;
    }
    public BigDecimal getNumEfectGastoTotal() {
        return this.numEfectGastoTotal;
    }
    
    public void setNumEfectGastoTotal(BigDecimal numEfectGastoTotal) {
        this.numEfectGastoTotal = numEfectGastoTotal;
    }
    public BigDecimal getNumEfectApertCaja() {
        return this.numEfectApertCaja;
    }
    
    public void setNumEfectApertCaja(BigDecimal numEfectApertCaja) {
        this.numEfectApertCaja = numEfectApertCaja;
    }
    public BigDecimal getNumEfectTotal() {
        return this.numEfectTotal;
    }
    
    public void setNumEfectTotal(BigDecimal numEfectTotal) {
        this.numEfectTotal = numEfectTotal;
    }
    public BigDecimal getNumEfectTotalVentaSiste() {
        return this.numEfectTotalVentaSiste;
    }
    
    public void setNumEfectTotalVentaSiste(BigDecimal numEfectTotalVentaSiste) {
        this.numEfectTotalVentaSiste = numEfectTotalVentaSiste;
    }
    public BigDecimal getNumEfectTotalGastoSiste() {
        return this.numEfectTotalGastoSiste;
    }
    
    public void setNumEfectTotalGastoSiste(BigDecimal numEfectTotalGastoSiste) {
        this.numEfectTotalGastoSiste = numEfectTotalGastoSiste;
    }
    public BigDecimal getNumEfectTotalSistema() {
        return this.numEfectTotalSistema;
    }
    
    public void setNumEfectTotalSistema(BigDecimal numEfectTotalSistema) {
        this.numEfectTotalSistema = numEfectTotalSistema;
    }
    public BigDecimal getNumEfectSobraFalta() {
        return this.numEfectSobraFalta;
    }
    
    public void setNumEfectSobraFalta(BigDecimal numEfectSobraFalta) {
        this.numEfectSobraFalta = numEfectSobraFalta;
    }
    public BigDecimal getNumTarjeCrediTotal() {
        return this.numTarjeCrediTotal;
    }
    
    public void setNumTarjeCrediTotal(BigDecimal numTarjeCrediTotal) {
        this.numTarjeCrediTotal = numTarjeCrediTotal;
    }
    public BigDecimal getNumTarjeDebitTotal() {
        return this.numTarjeDebitTotal;
    }
    
    public void setNumTarjeDebitTotal(BigDecimal numTarjeDebitTotal) {
        this.numTarjeDebitTotal = numTarjeDebitTotal;
    }
    public BigDecimal getNumTarjeTotal() {
        return this.numTarjeTotal;
    }
    
    public void setNumTarjeTotal(BigDecimal numTarjeTotal) {
        this.numTarjeTotal = numTarjeTotal;
    }
    public BigDecimal getNumTarjeTotalSiste() {
        return this.numTarjeTotalSiste;
    }
    
    public void setNumTarjeTotalSiste(BigDecimal numTarjeTotalSiste) {
        this.numTarjeTotalSiste = numTarjeTotalSiste;
    }
    public BigDecimal getNumTarjeSobraFalta() {
        return this.numTarjeSobraFalta;
    }
    
    public void setNumTarjeSobraFalta(BigDecimal numTarjeSobraFalta) {
        this.numTarjeSobraFalta = numTarjeSobraFalta;
    }
    public BigDecimal getNumTarjeGastoTotal() {
        return this.numTarjeGastoTotal;
    }
    
    public void setNumTarjeGastoTotal(BigDecimal numTarjeGastoTotal) {
        this.numTarjeGastoTotal = numTarjeGastoTotal;
    }
    public BigDecimal getNumTarjeGastoTotalSiste() {
        return this.numTarjeGastoTotalSiste;
    }
    
    public void setNumTarjeGastoTotalSiste(BigDecimal numTarjeGastoTotalSiste) {
        this.numTarjeGastoTotalSiste = numTarjeGastoTotalSiste;
    }
    public BigDecimal getNumGastoSobraFalta() {
        return this.numGastoSobraFalta;
    }
    
    public void setNumGastoSobraFalta(BigDecimal numGastoSobraFalta) {
        this.numGastoSobraFalta = numGastoSobraFalta;
    }

    public BigDecimal getIdEsta() {
        return idEsta;
    }

    public void setIdEsta(BigDecimal idEsta) {
        this.idEsta = idEsta;
    }

   




}

