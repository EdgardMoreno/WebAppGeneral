package com.general.hibernate.entity;
// Generated 28/11/2017 03:53:55 PM by Hibernate Tools 4.3.1


import com.general.hibernate.relaentity.Sic3proddocu;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sic1prod generated by hbm2java
 */
public class Sic1prod  implements java.io.Serializable {


     private BigDecimal idProd;
     private String desProd;
     private String codProd;
     private String codProdint;
     private String desProdcome;
     private BigDecimal idStipoprod;
     private String codIden;
     private Date fecDesde;
     private Date fecHasta;
     private BigDecimal numPrecio;
     private BigDecimal numCantstock;
     
     private Set sic3proddocus = new HashSet(0);
     
     private Sic1pers sic1pers;
     private BigDecimal numCantidad;
     private String desFecRegistro;
//     private String codStipoprod;
//     private String desStipoprod;
     private Sic3proddocu sic3proddocu;
     private Sic1general sic1stipoprod;
     
    

    public Sic1prod() {
    }

	
    public Sic1prod(BigDecimal idProd, String codProd) {
        this.idProd = idProd;
        this.codProd = codProd;
    }
    public Sic1prod(BigDecimal idProd, String desProd, String codProd, String desProdcome, BigDecimal idStipoprod, String codIden, Date fecDesde, Date fecHasta, BigDecimal numPrecio, Set sic3proddocus) {
       this.idProd = idProd;
       this.desProd = desProd;
       this.codProd = codProd;
       this.desProdcome = desProdcome;
       this.idStipoprod = idStipoprod;
       this.codIden = codIden;
       this.fecDesde = fecDesde;
       this.fecHasta = fecHasta;
       this.numPrecio = numPrecio;
       this.sic3proddocus = sic3proddocus;
    }
   
    public BigDecimal getIdProd() {
        return this.idProd;
    }
    
    public void setIdProd(BigDecimal idProd) {
        this.idProd = idProd;
    }
    public String getDesProd() {
        return this.desProd;
    }
    
    public void setDesProd(String desProd) {
        this.desProd = desProd;
    }
    public String getCodProd() {
        return this.codProd;
    }

    public String getCodProdint() {
        return codProdint;
    }

    public void setCodProdint(String codProdint) {
        this.codProdint = codProdint;
    }
       
    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }
    public String getDesProdcome() {
        return this.desProdcome;
    }
    
    public void setDesProdcome(String desProdcome) {
        this.desProdcome = desProdcome;
    }
    public BigDecimal getIdStipoprod() {
        return this.idStipoprod;
    }
    
    public void setIdStipoprod(BigDecimal idStipoprod) {
        this.idStipoprod = idStipoprod;
    }
    public String getCodIden() {
        return this.codIden;
    }
    
    public void setCodIden(String codIden) {
        this.codIden = codIden;
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
    public BigDecimal getNumPrecio() {
        return this.numPrecio;
    }
    
    public void setNumPrecio(BigDecimal numPrecio) {
        this.numPrecio = numPrecio;
    }
    public Set getSic3proddocus() {
        return this.sic3proddocus;
    }
    
    public void setSic3proddocus(Set sic3proddocus) {
        this.sic3proddocus = sic3proddocus;
    }

    public Sic1pers getSic1pers() {
        return sic1pers;
    }

    public void setSic1pers(Sic1pers sic1pers) {
        this.sic1pers = sic1pers;
    }

    public BigDecimal getNumCantidad() {
        return numCantidad;
    }

    public void setNumCantidad(BigDecimal numCantidad) {
        this.numCantidad = numCantidad;
    }

    public String getDesFecRegistro() {
        return desFecRegistro;
    }

    public void setDesFecRegistro(String desFecRegistro) {
        this.desFecRegistro = desFecRegistro;
    } 

    public BigDecimal getNumCantstock() {
        return numCantstock;
    }

    public void setNumCantstock(BigDecimal numCantstock) {
        this.numCantstock = numCantstock;
    }

    public Sic3proddocu getSic3proddocu() {
        return sic3proddocu;
    }

    public void setSic3proddocu(Sic3proddocu sic3proddocu) {
        this.sic3proddocu = sic3proddocu;
    }

    public Sic1general getSic1stipoprod() {
        return sic1stipoprod;
    }

    public void setSic1stipoprod(Sic1general sic1stipoprod) {
        this.sic1stipoprod = sic1stipoprod;
    }
    
    
    
    public String toString(){
        return "idProd: " + this.idProd + 
               " desProd: " + this.desProd +
               " codProd: " + this.codProd +
               " fecDesde: " + this.fecDesde +
               " idStipoprod: " + this.idStipoprod +
               " numCantidad: " + this.numCantidad +
               " numPrecio: " + this.numPrecio;
    }
    
}


