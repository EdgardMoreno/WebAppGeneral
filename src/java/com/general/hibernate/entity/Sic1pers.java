package com.general.hibernate.entity;
// Generated 28/10/2017 06:46:21 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Sic1pers generated by hbm2java
 */
public class Sic1pers implements java.io.Serializable {


     private BigDecimal idPers;
     private String desPers;
     private BigDecimal idTipopers;
     private BigDecimal idTipodomi;
     private BigDecimal idPaisresi;
     private String desCargoRep;
     private Set sic1docus = new HashSet(0);
     private Set sic1idenpers = new HashSet(0);
     private Sic1persindi sic1persindi;
     private Sic1persorga sic1persorga;
     private Sic1lugar sic1lugar;
     
     private String codEmail;
     private String desDireccion;
     private String codNumtele;
    
     /*Agregado*/
     private String codIden;
     private String codTipoiden;
     private String codTipopers;
     private BigDecimal idTipoiden;
     
     private BigDecimal idTrolpers;
     private String codTrolpers;

    public Sic1pers() {
    }

	
    public Sic1pers(BigDecimal idPers) {
        this.idPers = idPers;
    }
    public Sic1pers(BigDecimal idPers, String desPers, BigDecimal idTipopers, BigDecimal idTipodomi, BigDecimal idPaisresi, String desCargoRep, Set sic1docus, Set sic1idenpers) {
       this.idPers = idPers;
       this.desPers = desPers;
       this.idTipopers = idTipopers;
       this.idTipodomi = idTipodomi;
       this.idPaisresi = idPaisresi;
       this.desCargoRep = desCargoRep;
       this.sic1docus = sic1docus;
       this.sic1idenpers = sic1idenpers;       
       
    }
   
    public BigDecimal getIdPers() {
        return this.idPers;
    }
    
    public void setIdPers(BigDecimal idPers) {
        this.idPers = idPers;
    }
    public String getDesPers() {
        return this.desPers;
    }
    
    public void setDesPers(String desPers) {
        this.desPers = desPers;
    }
    public BigDecimal getIdTipopers() {
        return this.idTipopers;
    }
    
    public void setIdTipopers(BigDecimal idTipopers) {
        this.idTipopers = idTipopers;
    }
    public BigDecimal getIdTipodomi() {
        return this.idTipodomi;
    }
    
    public void setIdTipodomi(BigDecimal idTipodomi) {
        this.idTipodomi = idTipodomi;
    }
    public BigDecimal getIdPaisresi() {
        return this.idPaisresi;
    }
    
    public void setIdPaisresi(BigDecimal idPaisresi) {
        this.idPaisresi = idPaisresi;
    }
    public String getDesCargoRep() {
        return this.desCargoRep;
    }
    
    public void setDesCargoRep(String desCargoRep) {
        this.desCargoRep = desCargoRep;
    }

    public String getCodEmail() {
        return codEmail;
    }

    public void setCodEmail(String codEmail) {
        this.codEmail = codEmail;
    }

    public String getDesDireccion() {
        return desDireccion;
    }

    public void setDesDireccion(String desDireccion) {
        this.desDireccion = desDireccion;
    }   

    public String getCodNumtele() {
        return codNumtele;
    }

    public void setCodNumtele(String codNumtele) {
        this.codNumtele = codNumtele;
    }

  
    
    
    
    public Set getSic1docus() {
        return this.sic1docus;
    }
    
    public void setSic1docus(Set sic1docus) {
        this.sic1docus = sic1docus;
    }

    public Set getSic1idenpers() {
        return sic1idenpers;
    }

    public void setSic1idenpers(Set sic1idenpers) {
        this.sic1idenpers = sic1idenpers;
    }
    

    public Sic1persindi getSic1persindi() {
        return sic1persindi;
    }

    public void setSic1persindi(Sic1persindi sic1persindi) {
        this.sic1persindi = sic1persindi;
    }

    public Sic1persorga getSic1persorga() {
        return sic1persorga;
    }

    public void setSic1persorga(Sic1persorga sic1persorga) {
        this.sic1persorga = sic1persorga;
    }

    public Sic1lugar getSic1lugar() {
        return sic1lugar;
    }

    public void setSic1lugar(Sic1lugar sic1lugar) {
        this.sic1lugar = sic1lugar;
    }

    public String getCodIden() {
        return codIden;
    }

    public void setCodIden(String codIden) {
        this.codIden = codIden;
    }

    public String getCodTipoiden() {
        return codTipoiden;
    }

    public void setCodTipoiden(String codTipoiden) {
        this.codTipoiden = codTipoiden;
    }

    public String getCodTipopers() {
        return codTipopers;
    }

    public void setCodTipopers(String codTipopers) {
        this.codTipopers = codTipopers;
    }  

    public BigDecimal getIdTrolpers() {
        return idTrolpers;
    }

    public void setIdTrolpers(BigDecimal idTrolpers) {
        this.idTrolpers = idTrolpers;
    }

    public String getCodTrolpers() {
        return codTrolpers;
    }

    public void setCodTrolpers(String codTrolpers) {
        this.codTrolpers = codTrolpers;
    }

    public BigDecimal getIdTipoiden() {
        return idTipoiden;
    }

    public void setIdTipoiden(BigDecimal idTipoiden) {
        this.idTipoiden = idTipoiden;
    }   
    
    
    
    public String toString(){
        return "idPers: " + this.idPers + 
               " desPers: " + this.desPers +
               " desCargoRep: " + this.desCargoRep +
               " idTipodomi: " + this.idTipodomi +
               " idTipopers: " + this.idTipopers +
               " idPaisresi: " + this.idPaisresi;
    }
}


