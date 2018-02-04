/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;


import com.general.a2.service.impl.PersonServiceImpl;
import com.general.hibernate.entity.Sic1idenpers;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;



@FacesConverter("personaConverter")
public class PersonaConverter implements Converter{    
    
    
    /*Metodo que se ejecuta cuando se selecciona uno de los resultados del campo AUTOCOMPLETE*/
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        Sic1idenpers sic1idenpers = null;
        
        if(value != null && value.trim().length() > 0) {
            try {

                System.out.println("personaConverter " + value);
                PersonServiceImpl personaServiceImpl = new PersonServiceImpl();
                sic1idenpers = personaServiceImpl.getByCodiden(value);                  

            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            } catch(Exception e) {
                
            }
        }
        
        return sic1idenpers;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //if(((Sic1idenpers)value).getSic1pers().getIdPers() != null) {
        if(((Sic1idenpers) value).getId() != null) {
            return ((Sic1idenpers) value).getId().getCodIden();
        }
        else {
            return "";
        }
    }
    
}
