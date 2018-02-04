/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.interfac.service;

import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.views.ViSicdocu;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author emoreno
 */
public interface DocumentService{    
    
    
    public String insert(Sic1idendocu obj) throws ValidationException, CustomizerException;
    public String update(Sic1idendocu obj) throws ValidationException, CustomizerException;
    public String delete(String id) throws ValidationException, CustomizerException;
    
    public String relateDocuDocu(Sic3docudocu obj) throws ValidationException, CustomizerException;
    public String relateDocuEsta(Sic3docuesta obj) throws ValidationException, CustomizerException;
    public String relateDocuPers(List<Sic3docupers> list) throws ValidationException, CustomizerException;
    
    public Sic1idendocu getById(BigDecimal id) throws ValidationException, CustomizerException;
    public Sic1idendocu getByCodIden(String cod) throws ValidationException, CustomizerException;
    
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws ValidationException, CustomizerException;
    
}
