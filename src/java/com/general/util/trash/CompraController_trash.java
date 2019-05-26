/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.trash;


import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1persindi;
import com.general.hibernate.entity.Sic1persorga;
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.general.interfac.service.PersonService;


@Named
@ManagedBean
@ViewScoped
public class CompraController_trash implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(CompraController_trash.class);    
    
    private PersonService personaService;
    
    private MaestroCatalogoServiceImpl sic1generalService;
    
    private Sic1pers sic1pers;
    private Sic1idenpers sic1idenpers;
    private Sic1idenpersId sic1idenpersId;
    private Sic1persindi sic1persindi;
    private Sic1persorga sic1persorga;    
    
    
    private String countryName;
    private String strSelectedCodiden;
    private String strSelectedNombre;
    private String strSelectedIdpers;
    
    private List<Sic1pers> selectedThemes;
    private Sic1pers theme2;
    
    private Connection cnConexion;
    
    private List<Sic1pers> lstPersonas = new ArrayList();
    private List<SelectItem> items = new ArrayList <SelectItem> ();
    
    public CompraController_trash(){
        
        /*CARGAR TIPO DE DOCUMENTO*/
        System.out.println("Hola1");
    }
    
    @PostConstruct
    public void init() {
        
        try{
        
//            cnConexion = DaoConexion.verificarConexion(cnConexion);
//
            sic1pers = new Sic1pers();
            sic1idenpers = new Sic1idenpers();
            sic1idenpersId = new Sic1idenpersId();
            sic1idenpersId.setCodIden("123455");
//            sic1persindi = new Sic1persindi();
//            sic1persorga = new Sic1persorga();
//            sic1lugar = new Sic1lugar();
//
//            System.out.println("Hola0");
//
//            List<String> listCatTipoIden = new ArrayList<>();
//            listCatTipoIden.add("RUC");
//            listCatTipoIden.add("DNI");
//            sic1generalService = new Sic1generalServiceImpl(cnConexion);            
//            List<Sic1general> lstSic1general = sic1generalService.listByCod_ValorGeneral(listCatTipoIden);
//
//            sic1pers.setIdTipopers(new BigDecimal("1"));
//
//            SelectItem si;
//            for(Sic1general obj : lstSic1general){
//                si = new SelectItem();
//                si.setLabel(obj.getDesGeneral());
//                si.setValue(obj.getIdGeneral());
//                items.add(si);
//                System.out.println("Nombre:" + obj.getDesGeneral());
//            }

            System.out.println("Hola2");
        
        }catch(Exception e){            
            System.out.println("Error:" + e.getMessage());
        }
    }
    
    /****** PROPIEDADES *****/
    public List<SelectItem> getItems() {
        return items;
    } 

    public List<Sic1pers> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<Sic1pers> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }   

    public void setPersonaService(PersonServiceImpl personaServiceImpl) {
        //this.personaService = personaServiceImpl;
    }
    
    public Sic1pers getSic1pers() {
        return sic1pers;
    }

    public void setSic1pers(Sic1pers sic1pers) {
        this.sic1pers = sic1pers;
    }

    public Sic1idenpers getSic1idenpers() {
        return sic1idenpers;
    }

    public void setSic1idenpers(Sic1idenpers sic1idenpers) {
        this.sic1idenpers = sic1idenpers;
    }

    public Sic1idenpersId getSic1idenpersId() {
        return sic1idenpersId;
    }

    public void setSic1idenpersId(Sic1idenpersId sic1idenpersId) {
        this.sic1idenpersId = sic1idenpersId;
    }

    public Sic1persindi getSic1persindi() {
        return sic1persindi;
    }

    public void setSic1persindi(Sic1persindi sic1persindi) {
        this.sic1persindi = sic1persindi;
    }

    public Sic1persorga getSic1persorga() {
        return sic1persorga;
    }

    public void setSic1persorga(Sic1persorga sic1persorga) {
        this.sic1persorga = sic1persorga;
    }
    
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStrSelectedCodiden() {
        return strSelectedCodiden;
    }

    public void setStrSelectedCodiden(String strSelectedCodiden) {
        this.strSelectedCodiden = strSelectedCodiden;
    }

    public String getStrSelectedNombre() {
        return strSelectedNombre;
    }

    public void setStrSelectedNombre(String strSelectedNombre) {
        this.strSelectedNombre = strSelectedNombre;
    }

    public String getStrSelectedIdpers() {
        return strSelectedIdpers;
    }

    public void setStrSelectedIdpers(String strSelectedIdpers) {
        this.strSelectedIdpers = strSelectedIdpers;
    }
    
    public Sic1pers getTheme2() {
        return theme2;
    }
 
    public void setTheme2(Sic1pers theme2) {
        this.theme2 = theme2;
    }
 
    
    
    /****** METODOS *****/   
    public List countryList() {        

        ArrayList countryList = new ArrayList(); 
        countryList.add("India"); 
        countryList.add("Australia"); 
        countryList.add("Germany"); 
        countryList.add("Italy"); 
        countryList.add("United States Of America"); 
        countryList.add("Russia");
        countryList.add("Sweden");
        Collections.sort(countryList);
        return countryList; 
    }
    
    public List<Sic1pers> completeTheme(String query) throws SQLException {
        
        List<Sic1pers> lstSic1pers = null;
        
        try {
            //cnConexion = DaoConexion.verificarConexion(cnConexion);
            //personaService = new PersonServiceImpl(cnConexion);
            lstSic1pers = personaService.listByName("MEG");

        } catch (SQLException e) {
            cnConexion.rollback();
        } catch (Exception e) {
            cnConexion.rollback();
        } finally{
            if(this.cnConexion != null)
                this.cnConexion.close();
        }
        
        return lstSic1pers;
    }
    
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
    
    public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selected", event.getObject().toString()));
        event.getObject().toString();
        sic1idenpersId.setCodIden(event.getObject().toString());
    }    
    
    
    public List<Sic1pers> getSelectedThemes() {
        return selectedThemes;
    }
    
    public void setSelectedThemes(List<Sic1pers> selectedThemes) {
        this.selectedThemes = selectedThemes;
    }
    
}
