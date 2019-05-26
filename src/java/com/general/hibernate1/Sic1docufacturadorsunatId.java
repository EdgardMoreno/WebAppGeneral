package com.general.hibernate1;
// Generated 31/03/2019 04:18:33 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Sic1docufacturadorsunatId generated by hbm2java
 */
public class Sic1docufacturadorsunatId  implements java.io.Serializable {


     private String codProc;
     private BigDecimal idDocu;

    public Sic1docufacturadorsunatId() {
    }

    public Sic1docufacturadorsunatId(String codProc, BigDecimal idDocu) {
       this.codProc = codProc;
       this.idDocu = idDocu;
    }
   
    public String getCodProc() {
        return this.codProc;
    }
    
    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }
    public BigDecimal getIdDocu() {
        return this.idDocu;
    }
    
    public void setIdDocu(BigDecimal idDocu) {
        this.idDocu = idDocu;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Sic1docufacturadorsunatId) ) return false;
		 Sic1docufacturadorsunatId castOther = ( Sic1docufacturadorsunatId ) other; 
         
		 return ( (this.getCodProc()==castOther.getCodProc()) || ( this.getCodProc()!=null && castOther.getCodProc()!=null && this.getCodProc().equals(castOther.getCodProc()) ) )
 && ( (this.getIdDocu()==castOther.getIdDocu()) || ( this.getIdDocu()!=null && castOther.getIdDocu()!=null && this.getIdDocu().equals(castOther.getIdDocu()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCodProc() == null ? 0 : this.getCodProc().hashCode() );
         result = 37 * result + ( getIdDocu() == null ? 0 : this.getIdDocu().hashCode() );
         return result;
   }   


}


