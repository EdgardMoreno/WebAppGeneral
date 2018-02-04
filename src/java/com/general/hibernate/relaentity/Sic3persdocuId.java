package com.general.hibernate.relaentity;
// Generated 12/01/2018 03:16:12 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Sic3persdocuId generated by hbm2java
 */
public class Sic3persdocuId  implements java.io.Serializable {


     private BigDecimal idTrelapers;
     private BigDecimal idTrolpers;
     private BigDecimal idPers;
     private BigDecimal idDocu;
     private Date fecDesde;

    public Sic3persdocuId() {
    }

    public Sic3persdocuId(BigDecimal idTrelapers, BigDecimal idTrolpers, BigDecimal idPers, BigDecimal idDocu, Date fecDesde) {
       this.idTrelapers = idTrelapers;
       this.idTrolpers = idTrolpers;
       this.idPers = idPers;
       this.idDocu = idDocu;
       this.fecDesde = fecDesde;
    }
   
    public BigDecimal getIdTrelapers() {
        return this.idTrelapers;
    }
    
    public void setIdTrelapers(BigDecimal idTrelapers) {
        this.idTrelapers = idTrelapers;
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
    public BigDecimal getIdDocu() {
        return this.idDocu;
    }
    
    public void setIdDocu(BigDecimal idDocu) {
        this.idDocu = idDocu;
    }
    public Date getFecDesde() {
        return this.fecDesde;
    }
    
    public void setFecDesde(Date fecDesde) {
        this.fecDesde = fecDesde;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Sic3persdocuId) ) return false;
		 Sic3persdocuId castOther = ( Sic3persdocuId ) other; 
         
		 return ( (this.getIdTrelapers()==castOther.getIdTrelapers()) || ( this.getIdTrelapers()!=null && castOther.getIdTrelapers()!=null && this.getIdTrelapers().equals(castOther.getIdTrelapers()) ) )
 && ( (this.getIdTrolpers()==castOther.getIdTrolpers()) || ( this.getIdTrolpers()!=null && castOther.getIdTrolpers()!=null && this.getIdTrolpers().equals(castOther.getIdTrolpers()) ) )
 && ( (this.getIdPers()==castOther.getIdPers()) || ( this.getIdPers()!=null && castOther.getIdPers()!=null && this.getIdPers().equals(castOther.getIdPers()) ) )
 && ( (this.getIdDocu()==castOther.getIdDocu()) || ( this.getIdDocu()!=null && castOther.getIdDocu()!=null && this.getIdDocu().equals(castOther.getIdDocu()) ) )
 && ( (this.getFecDesde()==castOther.getFecDesde()) || ( this.getFecDesde()!=null && castOther.getFecDesde()!=null && this.getFecDesde().equals(castOther.getFecDesde()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdTrelapers() == null ? 0 : this.getIdTrelapers().hashCode() );
         result = 37 * result + ( getIdTrolpers() == null ? 0 : this.getIdTrolpers().hashCode() );
         result = 37 * result + ( getIdPers() == null ? 0 : this.getIdPers().hashCode() );
         result = 37 * result + ( getIdDocu() == null ? 0 : this.getIdDocu().hashCode() );
         result = 37 * result + ( getFecDesde() == null ? 0 : this.getFecDesde().hashCode() );
         return result;
   }   


}


