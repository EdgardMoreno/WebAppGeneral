package com.general.hibernate.relaentity;
// Generated 12/02/2018 11:32:38 PM by Hibernate Tools 4.3.1


import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1prod;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Sic3docuprod generated by hbm2java
 */
public class Sic3docuprod  implements java.io.Serializable {


     private Sic3docuprodId id;
     private Sic1prod sic1prod;
     private BigDecimal idTreladocu;
     private Date fecHasta;
     private String desNotas;
     private BigDecimal numValor;
     private BigDecimal numMtodscto;
     private BigDecimal numCantidad;
     
     /*Se agrega temporalmente*/
     private BigDecimal numCantidadEMZA;
     
     
      /*Agregado*/
     private Integer numIndex;
     private Sic1docu sic1docu;
     private Boolean flgNuevo;
     private Boolean flgSeleccionado;

    public Sic3docuprod() {
        this.flgNuevo = false;
        //this.flgSeleccionado = true;
    }

	
    public Sic3docuprod(Sic3docuprodId id) {
        this.id = id;        
    }
    public Sic3docuprod(Sic3docuprodId id, BigDecimal idTreladocu, Date fecHasta, String desNotas, BigDecimal numValor, BigDecimal numMtodscto, BigDecimal numCantidad, Sic1prod sic1prod) {
       this.id = id;
       this.idTreladocu = idTreladocu;
       this.fecHasta = fecHasta;
       this.desNotas = desNotas;
       this.numValor = numValor;
       this.numMtodscto = numMtodscto;
       this.numCantidad = numCantidad;
       this.sic1prod = sic1prod;
    }
   
    public Sic3docuprodId getId() {
        return this.id;
    }
    
    public void setId(Sic3docuprodId id) {
        this.id = id;
    }

    public Sic1prod getSic1prod() {
        return sic1prod;
    }

    public void setSic1prod(Sic1prod sic1prod) {
        this.sic1prod = sic1prod;
    }      
    
    public BigDecimal getIdTreladocu() {
        return this.idTreladocu;
    }
    
    public void setIdTreladocu(BigDecimal idTreladocu) {
        this.idTreladocu = idTreladocu;
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
    public BigDecimal getNumValor() {
        return this.numValor;
    }
    
    public void setNumValor(BigDecimal numValor) {
        this.numValor = numValor;
    }
    public BigDecimal getNumMtodscto() {
        return this.numMtodscto;
    }
    
    public void setNumMtodscto(BigDecimal numMtodscto) {
        this.numMtodscto = numMtodscto;
    }
    public BigDecimal getNumCantidad() {
        return this.numCantidad;
    }
    
    public void setNumCantidad(BigDecimal numCantidad) {
        this.numCantidad = numCantidad;
    }

    public Integer getNumIndex() {
        return numIndex;
    }

    public void setNumIndex(Integer numIndex) {
        this.numIndex = numIndex;
    }

    public Sic1docu getSic1docu() {
        return sic1docu;
    }

    public void setSic1docu(Sic1docu sic1docu) {
        this.sic1docu = sic1docu;
    }

    public Boolean getFlgNuevo() {
        return flgNuevo;
    }

    public void setFlgNuevo(Boolean flgNuevo) {
        this.flgNuevo = flgNuevo;
    }

    public BigDecimal getNumCantidadEMZA() {
        return numCantidadEMZA;
    }

    public void setNumCantidadEMZA(BigDecimal numCantidadEMZA) {
        this.numCantidadEMZA = numCantidadEMZA;
    }

    public Boolean getFlgSeleccionado() {
        return flgSeleccionado;
    }

    public void setFlgSeleccionado(Boolean flgSeleccionado) {
        this.flgSeleccionado = flgSeleccionado;
    }

    
    
    
}


