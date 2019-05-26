/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate1.Sic1docufacturadorsunat;

/**
 *
 * @author emoreno
 */
public class ComunicacionBaja {
    
    private String fecGeneracion;
    private String fecComunicacion;
    private String tipDocBaja;
    private String numDocBaja;
    private String desMotivoBaja;
    
    private Sic1docu sic1docu;
    private Sic1docufacturadorsunat Sic1docufactsunat;

    public String getFecGeneracion() {
        return fecGeneracion;
    }

    public void setFecGeneracion(String fecGeneracion) {
        this.fecGeneracion = fecGeneracion;
    }

    public String getFecComunicacion() {
        return fecComunicacion;
    }

    public void setFecComunicacion(String fecComunicacion) {
        this.fecComunicacion = fecComunicacion;
    }

    public String getTipDocBaja() {
        return tipDocBaja;
    }

    public void setTipDocBaja(String tipDocBaja) {
        this.tipDocBaja = tipDocBaja;
    }

    public String getNumDocBaja() {
        return numDocBaja;
    }

    public void setNumDocBaja(String numDocBaja) {
        this.numDocBaja = numDocBaja;
    }

    public String getDesMotivoBaja() {
        return desMotivoBaja;
    }

    public void setDesMotivoBaja(String desMotivoBaja) {
        this.desMotivoBaja = desMotivoBaja;
    }

    public Sic1docu getSic1docu() {
        return sic1docu;
    }

    public void setSic1docu(Sic1docu sic1docu) {
        this.sic1docu = sic1docu;
    }

    public Sic1docufacturadorsunat getSic1docufactsunat() {
        return Sic1docufactsunat;
    }

    public void setSic1docufactsunat(Sic1docufacturadorsunat Sic1docufactsunat) {
        this.Sic1docufactsunat = Sic1docufactsunat;
    }
    
    
    
}
