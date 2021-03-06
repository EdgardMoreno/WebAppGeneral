package com.general.hibernate.entity;
// Generated 09/02/2018 11:20:37 AM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Sic1usuario generated by hbm2java
 */
public class Sic1usuario  implements java.io.Serializable {


    private BigDecimal idUsuario;
    private String codUsuario;
    private String codPwd;
    private Date fecCreacion;
    private Date fecDesde;
    private Date fecHasta;
    private String desNotas;
    private String codEmail;
    private Integer idSucursal;
     
    /*Agregado*/
    private String codEstacaja;
    private String codTrolpers;
     
    /*Agregado*/
    private Sic1idenpers sic1idenpers;
    private List<Sic7persrol> lstSic7persrol;

     

    public Sic1usuario() {
        
    }
	
    public Sic1usuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Sic1usuario(BigDecimal idUsuario, String codUsuario, String codPwd, Date fecCreacion, Date fecDesde, Date fecHasta, String desNotas, String codEmail) {
       this.idUsuario = idUsuario;
       this.codUsuario = codUsuario;
       this.codPwd = codPwd;
       this.fecCreacion = fecCreacion;
       this.fecDesde = fecDesde;
       this.fecHasta = fecHasta;
       this.desNotas = desNotas;
       this.codEmail = codEmail;
    }
   
    public BigDecimal getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getCodUsuario() {
        return this.codUsuario;
    }
    
    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
    public String getCodPwd() {
        return this.codPwd;
    }
    
    public void setCodPwd(String codPwd) {
        this.codPwd = codPwd;
    }
    public Date getFecCreacion() {
        return this.fecCreacion;
    }
    
    public void setFecCreacion(Date fecCreacion) {
        this.fecCreacion = fecCreacion;
    }
    public Date getFecDesde() {
        return this.fecDesde;
    }
    
    public void setFecDesde(Date fecDesde) {
        this.fecDesde = fecDesde;
    }
    public Date getFecHasta() {
        return this.fecHasta;
    }
    
    public void setFecHasta(Date fecHasta) {
        this.fecHasta = fecHasta;
    }
    public String getDesNotas() {
        return this.desNotas;
    }
    
    public void setDesNotas(String desNotas) {
        this.desNotas = desNotas;
    }
    public String getCodEmail() {
        return this.codEmail;
    }
    
    public void setCodEmail(String codEmail) {
        this.codEmail = codEmail;
    }

    public Sic1idenpers getSic1idenpers() {
        return sic1idenpers;
    }

    public void setSic1idenpers(Sic1idenpers sic1idenpers) {
        this.sic1idenpers = sic1idenpers;
    }

    public String getCodEstacaja() {
        return codEstacaja;
    }

    public void setCodEstacaja(String codEstacaja) {
        this.codEstacaja = codEstacaja;
    }

    public String getCodTrolpers() {
        return codTrolpers;
    }

    public void setCodTrolpers(String codTrolpers) {
        this.codTrolpers = codTrolpers;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<Sic7persrol> getLstSic7persrol() {
        return lstSic7persrol;
    }

    public void setLstSic7persrol(List<Sic7persrol> lstSic7persrol) {
        this.lstSic7persrol = lstSic7persrol;
    }
    
    
}


