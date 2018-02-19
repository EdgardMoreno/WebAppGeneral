/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.hibernate.entity.Sic1prod;
import com.general.a2.service.impl.ProductServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.views.ViSicprod;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Edgard
 */
@Named
@ManagedBean
@ViewScoped
public class ProductController implements Serializable{    
    
    private final static Logger log = LoggerFactory.getLogger(ProductController.class);        
    
    private List<SelectItem> items = new ArrayList();
    private List<ViSicprod> listProducts;
    private ViSicprod viSicprod;
    private Sic1prod sic1prod;
    
    //private String flgExternalPage;
    private Integer indexTabla;
    private boolean flgEditProd;
    private int paramPageFlgActivo = 0;
    private int idProd = 0;
    
    @PostConstruct
    public void init() {
        
        try{
            
            listProducts = new ArrayList<>();
            sic1prod    = new Sic1prod();
            viSicprod   = new ViSicprod();            
            
        
            List<String> list = new ArrayList<>();
            list.add("VI_SICSTIPOPROD");
            Sic1generalServiceImpl sic1generalServiceImpl = new Sic1generalServiceImpl();
            List<Sic1general> lstSic1general = sic1generalServiceImpl.listByCod_ValorTipoGeneral_Sic1general(list);

            SelectItem si;
            for(Sic1general obj : lstSic1general){
                si = new SelectItem();
                si.setLabel(obj.getDesGeneral());
                si.setValue(obj.getIdGeneral());
                this.items.add(si);                
            }
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }
    }
    
    /***********************************************************/
    /************* PROPIEDADES ***********/
    /***********************************************************/

    public List<ViSicprod> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ViSicprod> listProducts) {
        this.listProducts = listProducts;
    }

    public ViSicprod getViSicprod() {
        return viSicprod;
    }

    public void setViSicprod(ViSicprod viSicprod) {
        this.viSicprod = viSicprod;
    }

    public Sic1prod getSic1prod() {
        return sic1prod;
    }

    public void setSic1prod(Sic1prod sic1prod) {
        this.sic1prod = sic1prod;
    }
    
    public List<SelectItem> getItems() {
        return items;
    }
    
    public void setItems(List<SelectItem> items) {
        this.items = items;
    }

    public boolean isFlgEditProd() {
        return flgEditProd;
    }

    public void setFlgEditProd(boolean flgEditProd) {
        this.flgEditProd = flgEditProd;
    }

    public int getParamPageFlgActivo() {
        return paramPageFlgActivo;
    }

    public void setParamPageFlgActivo(int paramPageFlgActivo) {
        this.paramPageFlgActivo = paramPageFlgActivo;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }


    
    
    
    /********************************************************************/
    /******************METODOS*******************************************/
    /********************************************************************/
    public void filterProducts() throws CustomizerException{
        
        ProductServiceImpl productServiceImpl = new ProductServiceImpl();
        this.listProducts = productServiceImpl.listViSicProd(this.viSicprod);
        
    }
    
    public void selectProductFromDialog(ViSicprod obj) {
        Sic1prod prod = new Sic1prod();
        prod.setIdProd(obj.getIdProd());
        prod.setCodIden(obj.getCodProd());
        prod.setCodProd(obj.getCodProd());
        prod.setDesProd(obj.getDesProd());
        RequestContext.getCurrentInstance().closeDialog(prod);
    }
    
    public String deleteAction(Sic1prod obj) {
              
        listProducts.remove(obj);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Fila eliminada"));        
        return "";
    }
    
    public void editAction(ViSicprod obj) throws CustomizerException {
      
        this.indexTabla = listProducts.indexOf(obj);
        ProductServiceImpl productServiceImpl = new ProductServiceImpl();
        this.sic1prod = productServiceImpl.getByCod(obj.getCodProd());
        this.flgEditProd = true;

    }
    
    public void newProduct(){
        
        this.sic1prod = new Sic1prod();
        this.sic1prod.setCodProd(viSicprod.getCodProd());
        this.sic1prod.setDesProd(viSicprod.getDesProd());
        
        this.flgEditProd = false;
        
    }
    
    public void clearForm(){
        this.sic1prod = new Sic1prod();
    }
    
    public void saveProduct() throws CustomizerException{
        
        try{
            
            ProductServiceImpl productServiceImpl = new ProductServiceImpl();
            String result = productServiceImpl.insert(this.sic1prod);
            
            System.out.println("idProd: " + result);
            
            //Se llena el control oculto con el identificado del nuevo producto
            this.idProd = Integer.valueOf(result);
            
            
            this.sic1prod = new Sic1prod();
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
        
        }catch(ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch(CustomizerException ex){            
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    public void calc(){
        
    }
    
}
