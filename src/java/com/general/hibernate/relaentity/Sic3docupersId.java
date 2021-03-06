package com.general.hibernate.relaentity;
// Generated 12/01/2018 03:57:41 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Sic3docupersId generated by hbm2java
 */
public class Sic3docupersId  implements java.io.Serializable {


     private BigDecimal idTreladocu;
     private BigDecimal idDocu;
     private BigDecimal idTrolpers;
     private BigDecimal idPers;
     private Date fecDesde;
     
     /*Agregados*/
     private String codTreladocu;
     private String codTrolpers;

    public Sic3docupersId() {
    }

    public Sic3docupersId(BigDecimal idTreladocu, BigDecimal idDocu, BigDecimal idTrolpers, BigDecimal idPers, Date fecDesde) {
       this.idTreladocu = idTreladocu;
       this.idDocu = idDocu;
       this.idTrolpers = idTrolpers;
       this.idPers = idPers;
       this.fecDesde = fecDesde;
    }
   
    public BigDecimal getIdTreladocu() {
        return this.idTreladocu;
    }
    
    public void setIdTreladocu(BigDecimal idTreladocu) {
        this.idTreladocu = idTreladocu;
    }
    public BigDecimal getIdDocu() {
        return this.idDocu;
    }
    
    public void setIdDocu(BigDecimal idDocu) {
        this.idDocu = idDocu;
    }
    public BigDecimal getIdTrolpers() {
        return this.idTrolpers;
    }
    
    public void setIdTrolpers(BigDecimal idTrolpers) {
        this.idTrolpers = idTrolpers;
    }
    public BigDecimal getIdPers() {
        return this.idPers;
    }
    
    public void setIdPers(BigDecimal idPers) {
        this.idPers = idPers;
    }
    public Date getFecDesde() {
        return this.fecDesde;
    }
    
    public void setFecDesde(Date fecDesde) {
        this.fecDesde = fecDesde;
    }

    public String getCodTreladocu() {
        return codTreladocu;
    }

    public void setCodTreladocu(String codTreladocu) {
        this.codTreladocu = codTreladocu;
    }

    public String getCodTrolpers() {
        return codTrolpers;
    }

    public void setCodTrolpers(String codTrolpers) {
        this.codTrolpers = codTrolpers;
    }   
    
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Sic3docupersId) ) return false;
		 Sic3docupersId castOther = ( Sic3docupersId ) other; 
         
		 return ( (this.getIdTreladocu()==castOther.getIdTreladocu()) || ( this.getIdTreladocu()!=null && castOther.getIdTreladocu()!=null && this.getIdTreladocu().equals(castOther.getIdTreladocu()) ) )
 && ( (this.getIdDocu()==castOther.getIdDocu()) || ( this.getIdDocu()!=null && castOther.getIdDocu()!=null && this.getIdDocu().equals(castOther.getIdDocu()) ) )
 && ( (this.getIdTrolpers()==castOther.getIdTrolpers()) || ( this.getIdTrolpers()!=null && castOther.getIdTrolpers()!=null && this.getIdTrolpers().equals(castOther.getIdTrolpers()) ) )
 && ( (this.getIdPers()==castOther.getIdPers()) || ( this.getIdPers()!=null && castOther.getIdPers()!=null && this.getIdPers().equals(castOther.getIdPers()) ) )
 && ( (this.getFecDesde()==castOther.getFecDesde()) || ( this.getFecDesde()!=null && castOther.getFecDesde()!=null && this.getFecDesde().equals(castOther.getFecDesde()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdTreladocu() == null ? 0 : this.getIdTreladocu().hashCode() );
         result = 37 * result + ( getIdDocu() == null ? 0 : this.getIdDocu().hashCode() );
         result = 37 * result + ( getIdTrolpers() == null ? 0 : this.getIdTrolpers().hashCode() );
         result = 37 * result + ( getIdPers() == null ? 0 : this.getIdPers().hashCode() );
         result = 37 * result + ( getFecDesde() == null ? 0 : this.getFecDesde().hashCode() );
         return result;
   }   


}


