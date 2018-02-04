/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.views.ViSicdocu;
import com.general.interfac.service.DocumentService;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author emoreno
 * Clase que utiliza para guardar el CUADRE DE CAJA DIARIO
 */
public class DocuCashBox implements Serializable, DocumentService{

    @Override
    public String insert(Sic1idendocu obj) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update(Sic1idendocu obj) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete(String id) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String relateDocuDocu(Sic3docudocu obj) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String relateDocuEsta(Sic3docuesta obj) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String relateDocuPers(List<Sic3docupers> list) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sic1idendocu getById(BigDecimal id) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sic1idendocu getByCodIden(String cod) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
