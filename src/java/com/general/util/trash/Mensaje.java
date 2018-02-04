/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.trash;

/**
 *
 * @author Edgard
 */
public class Mensaje {
    
    private Integer idError;
    private String codError;
    private String desError;
    
    private String codValor;

    
    public Mensaje(){
        idError = 0;
    }
    
    /**
     * @return the idError
     */
    public Integer getIdError() {
        return idError;
    }

    /**
     * @param idError the idError to set
     */
    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    /**
     * @return the codError
     */
    public String getCodError() {
        return codError;
    }

    /**
     * @param codError the codError to set
     */
    public void setCodError(String codError) {
        this.codError = codError;
    }

    /**
     * @return the desError
     */
    public String getDesError() {
        return desError;
    }

    /**
     * @param desError the desError to set
     */
    public void setDesError(String desError) {
        this.desError = desError;
    }

    /**
     * @return the codValor
     */
    public String getCodValor() {
        return codValor;
    }

    /**
     * @param codValor the codValor to set
     */
    public void setCodValor(String codValor) {
        this.codValor = codValor;
    }
    
    
    
}
