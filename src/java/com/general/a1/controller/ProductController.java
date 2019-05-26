/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.hibernate.entity.Sic1prod;
import com.general.a2.service.impl.ProductServiceImpl;
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
@SessionScoped
public class ProductController implements Serializable{    
    
    private final static Logger log = LoggerFactory.getLogger(ProductController.class);        
    
    private List<Sic1general> items = new ArrayList();
    private List<ViSicprod> listProducts;
    private ViSicprod viSicprod;
    private Sic1prod sic1prod;
    
    //private String flgExternalPage;
    private Integer indexTabla;
    private boolean flgEditProd;
    private int paramPageFlgActivo = 0;
    private int idProd = 0;
    
    private String desTituloPagina;
    
    @PostConstruct
    public void init() {
        
        try{
            
            listProducts = new ArrayList<>();
            sic1prod    = new Sic1prod();
            viSicprod   = new ViSicprod();            
            
        
            List<String> list = new ArrayList<>();
            list.add("VI_SICSTIPOPROD");
            MaestroCatalogoServiceImpl sic1generalServiceImpl = new MaestroCatalogoServiceImpl();
            this.items = sic1generalServiceImpl.listByCod_ValorTipoGeneral_Sic1general(list);

//            SelectItem si;
//            for(Sic1general obj : lstSic1general){
//                si = new SelectItem();
//                si.setLabel(obj.getDesGeneral());
//                si.setValue(obj.getIdGeneral());
//                this.items.add(si);                
//            }
            
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
    
    public List<Sic1general> getItems() {
        return items;
    }
    
    public void setItems(List<Sic1general> items) {
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

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
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
    
    public void deleteAction(ViSicprod obj) throws CustomizerException {
        
        try{
            
            //No existe la tabla SIC3PRODESTA. Se deja como pendiente...
            listProducts.remove(obj);
            UtilClass.addInfoMessage("Producto eliminado.");
            
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void editAction(int index) throws CustomizerException {
      
        ViSicprod obj = listProducts.get(index);
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
    
    public void clearSearch(){
        viSicprod = new ViSicprod();
        listProducts = new ArrayList<>();
    }
    
    public void clearForm(){
        this.sic1prod = new Sic1prod();
        this.flgEditProd = false;
    }
    
    public void saveProduct() throws CustomizerException{
        
        try{
            
            String codStipoprodsele = "";
            String desStipoprodsele = "";
            String desError = "";
            boolean flgNuevoRegistro = true;
            
            if(this.sic1prod.getIdProd()!=null)
                flgNuevoRegistro = false;
            
             /*Obtener el tipo de gasto seleccionado*/
            for(Sic1general s : this.items){
                if(this.sic1prod.getIdStipoprod().intValue() == s.getIdGeneral().intValue()){
                    codStipoprodsele = s.getCodValorgeneral();
                    desStipoprodsele = s.getDesGeneral();
                }
            }
            
            if(flgNuevoRegistro && (codStipoprodsele.equals(Constantes.CONS_COD_STIPOPROD_VINILCORTE) || 
                                    codStipoprodsele.equals(Constantes.CONS_COD_STIPOPROD_STICKER) || 
                                    codStipoprodsele.equals(Constantes.CONS_COD_STIPOPROD_CATALOGO) ||
                                    codStipoprodsele.equals(Constantes.CONS_COD_STIPOPROD_PEGAMENTO))){
                if(sic1prod.getCodProdint()!= null && !sic1prod.getCodProdint().isEmpty())
                    desError = "Para la categoria " + desStipoprodsele.toUpperCase() + " no es necesario especificar el CODIGO INTERNO.";
            }else{
                //Si no es un nuevo registro se tiene que validar que tenga codigo interno tambien
                if(sic1prod.getCodProdint()== null || sic1prod.getCodProdint().isEmpty())
                    desError = "Se debe ingresar el código del Producto Interno.";
                else if(sic1prod.getCodProdint().length() < 3)
                    desError = "El Código del Producto Interno debe tener mas de 2 caracteres.";                
            }
            

            if(!desError.isEmpty())
                UtilClass.addErrorMessage(desError);
            else{
                
                ProductServiceImpl productServiceImpl = new ProductServiceImpl();
                String result = productServiceImpl.insert(this.sic1prod);

                System.out.println("idProd: " + result);

                //Se llena el control oculto con el identificado del nuevo producto
                this.idProd = Integer.valueOf(result);
                this.flgEditProd = false;
                this.sic1prod = new Sic1prod();
                
                UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            }

        }catch(ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch(CustomizerException ex){            
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    public void calc(){
        
    }
    
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            /*Metodo 1: Se obtiene los parametros que son enviados por la url con fancyBox. Por ejemplo cuando
            se quiere registrar una nueva persona desde la pantalla del Registro COMPRA/VENTA*/
            System.out.println("paramPageFlgActivo: " + this.paramPageFlgActivo);
            System.out.println("codProd: " + this.sic1prod.getCodProd());
            
            String codProd = this.sic1prod.getCodProd();
            
            if(codProd != null && codProd.trim().length() > 0){
                ProductServiceImpl productServiceImpl = new ProductServiceImpl();
                this.sic1prod = productServiceImpl.getByCod(this.sic1prod.getCodProd());
                this.flgEditProd = true;
            }else{
                sic1prod = new Sic1prod();
                this.flgEditProd = false;
                this.paramPageFlgActivo = 0;
            }
            
        }
    }   
    
}
