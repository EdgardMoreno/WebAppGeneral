package com.general.hibernate.relaentity;
// Generated 21/12/2017 04:19:00 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Sic3docudocu generated by hbm2java
 */
public class Sic3docudocu  implements java.io.Serializable {


     private Sic3docudocuId id;
     private Date fecHasta;
     private String desNotas;

    public Sic3docudocu() {
    }

	
    public Sic3docudocu(Sic3docudocuId id) {
        this.id = id;
    }
    public Sic3docudocu(Sic3docudocuId id, Date fecHasta, String desNotas) {
       this.id = id;
       this.fecHasta = fecHasta;
       this.desNotas = desNotas;
    }
   
    public Sic3docudocuId getId() {
        return this.id;
    }
    
    public void setId(Sic3docudocuId id) {
        this.id = id;
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




}


