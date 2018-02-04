package com.general.hibernate1;
// Generated 04/01/2018 06:43:52 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Sic7estarolId generated by hbm2java
 */
public class Sic7estarolId  implements java.io.Serializable {


     private BigDecimal idTrolesta;
     private BigDecimal idEsta;

    public Sic7estarolId() {
    }

    public Sic7estarolId(BigDecimal idTrolesta, BigDecimal idEsta) {
       this.idTrolesta = idTrolesta;
       this.idEsta = idEsta;
    }
   
    public BigDecimal getIdTrolesta() {
        return this.idTrolesta;
    }
    
    public void setIdTrolesta(BigDecimal idTrolesta) {
        this.idTrolesta = idTrolesta;
    }
    public BigDecimal getIdEsta() {
        return this.idEsta;
    }
    
    public void setIdEsta(BigDecimal idEsta) {
        this.idEsta = idEsta;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Sic7estarolId) ) return false;
		 Sic7estarolId castOther = ( Sic7estarolId ) other; 
         
		 return ( (this.getIdTrolesta()==castOther.getIdTrolesta()) || ( this.getIdTrolesta()!=null && castOther.getIdTrolesta()!=null && this.getIdTrolesta().equals(castOther.getIdTrolesta()) ) )
 && ( (this.getIdEsta()==castOther.getIdEsta()) || ( this.getIdEsta()!=null && castOther.getIdEsta()!=null && this.getIdEsta().equals(castOther.getIdEsta()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdTrolesta() == null ? 0 : this.getIdTrolesta().hashCode() );
         result = 37 * result + ( getIdEsta() == null ? 0 : this.getIdEsta().hashCode() );
         return result;
   }   


}


