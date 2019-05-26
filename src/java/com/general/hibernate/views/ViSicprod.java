package com.general.hibernate.views;
// Generated 19/01/2018 05:18:07 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

/**
 * ViSicprod generated by hbm2java
 */
public class ViSicprod  implements java.io.Serializable {


    private BigDecimal idProd;
     private String codIden;
     private String codProd;
     private String codProdint;
     private String desProd;
     private String desProdcome;
     private BigDecimal idStipoprod;
     private String codStipoprod;
     private String desStipoprod;
     private String codTipoprod;
     private String desTipoprod;
     private Date fecDesde;
     private Date fecHasta;
     private String numPrecio;
     private BigDecimal numCantidad;
     
     /*Agregado*/
     private BigDecimal numUtlcostoiddocu;
     private BigDecimal numUtlcostovalor;     
     private String desPersrelaproveedores;

    public ViSicprod() {
    }

	
    public ViSicprod(BigDecimal idProd, String codIden, String codProd) {
        this.idProd = idProd;
        this.codIden = codIden;
        this.codProd = codProd;
    }
    public ViSicprod(BigDecimal idProd, String codIden, String codProd, String desProd, String desProdcome, BigDecimal idStipoprod, String codStipoprod, String desStipoprod, String codTipoprod, String desTipoprod, Date fecDesde, Date fecHasta, BigDecimal numPrecio, BigDecimal numCantidad) {
       this.idProd = idProd;
       this.codIden = codIden;
       this.codProd = codProd;
       this.desProd = desProd;
       this.desProdcome = desProdcome;
       this.idStipoprod = idStipoprod;
       this.codStipoprod = codStipoprod;
       this.desStipoprod = desStipoprod;
       this.codTipoprod = codTipoprod;
       this.desTipoprod = desTipoprod;
       this.fecDesde = fecDesde;
       this.fecHasta = fecHasta;       
       this.numCantidad = numCantidad;
    }
   
    public BigDecimal getIdProd() {
        return this.idProd;
    }
    
    public void setIdProd(BigDecimal idProd) {
        this.idProd = idProd;
    }
    public String getCodIden() {
        return this.codIden;
    }
    
    public void setCodIden(String codIden) {
        this.codIden = codIden;
    }
    public String getCodProd() {
        return this.codProd;
    }
    
    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }
    public String getDesProd() {
        return this.desProd;
    }
    
    public void setDesProd(String desProd) {
        this.desProd = desProd;
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
    public String getCodStipoprod() {
        return this.codStipoprod;
    }
    
    public void setCodStipoprod(String codStipoprod) {
        this.codStipoprod = codStipoprod;
    }
    public String getDesStipoprod() {
        return this.desStipoprod;
    }
    
    public void setDesStipoprod(String desStipoprod) {
        this.desStipoprod = desStipoprod;
    }
    public String getCodTipoprod() {
        return this.codTipoprod;
    }
    
    public void setCodTipoprod(String codTipoprod) {
        this.codTipoprod = codTipoprod;
    }
    public String getDesTipoprod() {
        return this.desTipoprod;
    }
    
    public void setDesTipoprod(String desTipoprod) {
        this.desTipoprod = desTipoprod;
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

    public String getNumPrecio() {
        return numPrecio;
    }

    public void setNumPrecio(String numPrecio) {
        this.numPrecio = numPrecio;
    }
    
    public BigDecimal getNumCantidad() {
        return this.numCantidad;
    }
    
    public void setNumCantidad(BigDecimal numCantidad) {
        this.numCantidad = numCantidad;
    }

    public BigDecimal getNumUtlcostoiddocu() {
        return numUtlcostoiddocu;
    }

    public void setNumUtlcostoiddocu(BigDecimal numUtlcostoiddocu) {
        this.numUtlcostoiddocu = numUtlcostoiddocu;
    }

    public BigDecimal getNumUtlcostovalor() {
        return numUtlcostovalor;
    }

    public void setNumUtlcostovalor(BigDecimal numUtlcostovalor) {
        this.numUtlcostovalor = numUtlcostovalor;
    }

    public String getDesPersrelaproveedores() {
        return desPersrelaproveedores;
    }

    public void setDesPersrelaproveedores(String desPersrelaproveedores) {
        this.desPersrelaproveedores = desPersrelaproveedores;
    }

    public String getCodProdint() {
        return codProdint;
    }

    public void setCodProdint(String codProdint) {
        this.codProdint = codProdint;
    }
    
    
    
    
    public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ViSicprod) ) return false;
		 ViSicprod castOther = ( ViSicprod ) other; 
         
		 return ( (this.getIdProd()==castOther.getIdProd()) || ( this.getIdProd()!=null && castOther.getIdProd()!=null && this.getIdProd().equals(castOther.getIdProd()) ) )
 && ( (this.getCodIden()==castOther.getCodIden()) || ( this.getCodIden()!=null && castOther.getCodIden()!=null && this.getCodIden().equals(castOther.getCodIden()) ) )
 && ( (this.getCodProd()==castOther.getCodProd()) || ( this.getCodProd()!=null && castOther.getCodProd()!=null && this.getCodProd().equals(castOther.getCodProd()) ) )
 && ( (this.getDesProd()==castOther.getDesProd()) || ( this.getDesProd()!=null && castOther.getDesProd()!=null && this.getDesProd().equals(castOther.getDesProd()) ) )
 && ( (this.getDesProdcome()==castOther.getDesProdcome()) || ( this.getDesProdcome()!=null && castOther.getDesProdcome()!=null && this.getDesProdcome().equals(castOther.getDesProdcome()) ) )
 && ( (this.getIdStipoprod()==castOther.getIdStipoprod()) || ( this.getIdStipoprod()!=null && castOther.getIdStipoprod()!=null && this.getIdStipoprod().equals(castOther.getIdStipoprod()) ) )
 && ( (this.getCodStipoprod()==castOther.getCodStipoprod()) || ( this.getCodStipoprod()!=null && castOther.getCodStipoprod()!=null && this.getCodStipoprod().equals(castOther.getCodStipoprod()) ) )
 && ( (this.getDesStipoprod()==castOther.getDesStipoprod()) || ( this.getDesStipoprod()!=null && castOther.getDesStipoprod()!=null && this.getDesStipoprod().equals(castOther.getDesStipoprod()) ) )
 && ( (this.getCodTipoprod()==castOther.getCodTipoprod()) || ( this.getCodTipoprod()!=null && castOther.getCodTipoprod()!=null && this.getCodTipoprod().equals(castOther.getCodTipoprod()) ) )
 && ( (this.getDesTipoprod()==castOther.getDesTipoprod()) || ( this.getDesTipoprod()!=null && castOther.getDesTipoprod()!=null && this.getDesTipoprod().equals(castOther.getDesTipoprod()) ) )
 && ( (this.getFecDesde()==castOther.getFecDesde()) || ( this.getFecDesde()!=null && castOther.getFecDesde()!=null && this.getFecDesde().equals(castOther.getFecDesde()) ) )
 && ( (this.getFecHasta()==castOther.getFecHasta()) || ( this.getFecHasta()!=null && castOther.getFecHasta()!=null && this.getFecHasta().equals(castOther.getFecHasta()) ) )
 && ( (this.getNumPrecio()==castOther.getNumPrecio()) || ( this.getNumPrecio()!=null && castOther.getNumPrecio()!=null && this.getNumPrecio().equals(castOther.getNumPrecio()) ) )
 && ( (this.getNumCantidad()==castOther.getNumCantidad()) || ( this.getNumCantidad()!=null && castOther.getNumCantidad()!=null && this.getNumCantidad().equals(castOther.getNumCantidad()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdProd() == null ? 0 : this.getIdProd().hashCode() );
         result = 37 * result + ( getCodIden() == null ? 0 : this.getCodIden().hashCode() );
         result = 37 * result + ( getCodProd() == null ? 0 : this.getCodProd().hashCode() );
         result = 37 * result + ( getDesProd() == null ? 0 : this.getDesProd().hashCode() );
         result = 37 * result + ( getDesProdcome() == null ? 0 : this.getDesProdcome().hashCode() );
         result = 37 * result + ( getIdStipoprod() == null ? 0 : this.getIdStipoprod().hashCode() );
         result = 37 * result + ( getCodStipoprod() == null ? 0 : this.getCodStipoprod().hashCode() );
         result = 37 * result + ( getDesStipoprod() == null ? 0 : this.getDesStipoprod().hashCode() );
         result = 37 * result + ( getCodTipoprod() == null ? 0 : this.getCodTipoprod().hashCode() );
         result = 37 * result + ( getDesTipoprod() == null ? 0 : this.getDesTipoprod().hashCode() );
         result = 37 * result + ( getFecDesde() == null ? 0 : this.getFecDesde().hashCode() );
         result = 37 * result + ( getFecHasta() == null ? 0 : this.getFecHasta().hashCode() );
         result = 37 * result + ( getNumPrecio() == null ? 0 : this.getNumPrecio().hashCode() );
         result = 37 * result + ( getNumCantidad() == null ? 0 : this.getNumCantidad().hashCode() );
         return result;
   }  




}


