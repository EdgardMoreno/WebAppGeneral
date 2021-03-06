package com.general.hibernate.relaentity;
// Generated 12/02/2018 11:32:38 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

/**
 * Sic3docuprodId generated by hbm2java
 */
public class Sic3docuprodId implements java.io.Serializable {

    private BigDecimal idDocu;
    private BigDecimal idProd;
    private Date fecDesde;
    private Integer numItem;

    public Sic3docuprodId() {
    }

    public Sic3docuprodId(BigDecimal idDocu, BigDecimal idProd, Date fecDesde) {
        this.idDocu = idDocu;
        this.idProd = idProd;
        this.fecDesde = fecDesde;
    }

    public BigDecimal getIdDocu() {
        return this.idDocu;
    }

    public void setIdDocu(BigDecimal idDocu) {
        this.idDocu = idDocu;
    }

    public BigDecimal getIdProd() {
        return this.idProd;
    }

    public void setIdProd(BigDecimal idProd) {
        this.idProd = idProd;
    }

    public Date getFecDesde() {
        return this.fecDesde;
    }

    public void setFecDesde(Date fecDesde) {
        this.fecDesde = fecDesde;
    }

    public Integer getNumItem() {
        return numItem;
    }

    public void setNumItem(Integer numItem) {
        this.numItem = numItem;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof Sic3docuprodId)) {
            return false;
        }
        Sic3docuprodId castOther = (Sic3docuprodId) other;

        return ((this.getIdDocu() == castOther.getIdDocu()) || (this.getIdDocu() != null && castOther.getIdDocu() != null && this.getIdDocu().equals(castOther.getIdDocu())))
                && ((this.getIdProd() == castOther.getIdProd()) || (this.getIdProd() != null && castOther.getIdProd() != null && this.getIdProd().equals(castOther.getIdProd())))
                && ((this.getFecDesde() == castOther.getFecDesde()) || (this.getFecDesde() != null && castOther.getFecDesde() != null && this.getFecDesde().equals(castOther.getFecDesde())))
                && ((this.getNumItem() == castOther.getNumItem()) || (this.getNumItem() != null && castOther.getNumItem() != null && this.getNumItem().equals(castOther.getNumItem())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getIdDocu() == null ? 0 : this.getIdDocu().hashCode());
        result = 37 * result + (getIdProd() == null ? 0 : this.getIdProd().hashCode());
        result = 37 * result + (getFecDesde() == null ? 0 : this.getFecDesde().hashCode());
        return result;
    }

}
