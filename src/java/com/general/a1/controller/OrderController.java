/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuOrderServiceImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3proddocu;
import com.general.hibernate.relaentity.Sic3proddocuId;
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.a2.service.impl.ProductServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.interfac.dao.DaoProduct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.text.ParseException;
import javax.faces.event.ValueChangeEvent;


@Named
@ManagedBean
@ViewScoped
public class OrderController implements Serializable{
 
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);    
    
    
    
    private DocuOrderServiceImpl orderServiceImpl;
    private Sic1generalServiceImpl sic1generalServiceImpl;    
    
    /*Producto*/
    private Sic1docu sic1docu;
    private Sic1prod sic1prod;
    
    /*Persona*/
    private Sic1idenpers sic1idenpersSelected;
    private Sic1pers sic1pers;
    private Sic1idenpersId sic1idenpersId;
    /**/
    
    private Sic3proddocu sic3proddocu;
    private List<Sic3proddocu> lstSic3proddocu;        
    
    private List<SelectItem> itemSTipoDocu  = new ArrayList();
    private List<Sic1general> itemsPayMode   = new ArrayList();
    private List<SelectItem> itemsTypeCard  = new ArrayList();
    
    private String desFecRegistro;
    private Integer indexTabla;
    
    public OrderController(){
    }
    
    @PostConstruct
    public void init() {
        
        try{
            
            sic1generalServiceImpl = new Sic1generalServiceImpl();
            orderServiceImpl = new DocuOrderServiceImpl();
           
            desFecRegistro   = UtilClass.getCurrentDay();
            indexTabla       = -1;            
            
            /*Persona*/
            sic1idenpersSelected = new Sic1idenpers();
            sic1pers             = new Sic1pers();
            sic1idenpersId       = new Sic1idenpersId();
            
            /*Producto*/
            sic1prod         = new Sic1prod();
            sic3proddocu     = new Sic3proddocu();
            sic3proddocu.setSic1prod(sic1prod);
            
            sic1docu         = new Sic1docu();
            sic1docu.setNumSubtotal(new BigDecimal("0.00"));
            sic1docu.setNumIgv(new BigDecimal("0.00"));
            
            /*Data*/
            lstSic3proddocu  = new ArrayList();
            
            //eliminar
            Sic1prod sic1prod = new Sic1prod();
            sic1prod.setCodProd("asdasd");
            sic1prod.setDesProd("fsfsdf");
            sic1prod.setNumPrecio(new BigDecimal(30.21).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            Sic3proddocu sic3proddocu = new Sic3proddocu();
            sic3proddocu.setSic1prod(sic1prod);
            sic3proddocu.setNumValor(sic1prod.getNumPrecio());
            sic3proddocu.setNumCantidad(new BigDecimal(20));
            
            this.lstSic3proddocu.add(sic3proddocu);
            //
            
            /*Variable que el valor será enviado como parametro dependiendo de los que se quiera realizar: COMPRA O VENTA*/
            String strDesEvento = "COMPRA";
            
            /*Cargar Catalogo: STIPODOCU*/
            List<String> listCat = new ArrayList<>();
            listCat.add("VI_SICFACTURA");
            listCat.add("VI_SICBOLETA");
            System.out.println("HiberNate:" + HibernateUtil.getSessionFactory().isClosed());
            this.itemSTipoDocu = sic1generalServiceImpl.listByCod_STipoDocu_SelectItem(listCat);
            
            //*Cargar Catalogo: MODALIDAD PAGO*/
            listCat.clear();
            listCat.add("VI_SICMODAPAGO");
            List<Sic1general> lstSic1general = sic1generalServiceImpl.listByCod_ValorTipoGeneral_Sic1general(listCat);
            for(Sic1general obj : lstSic1general){                
                /*Valor Por Defecto*/
                if(obj.getCodValorgeneral().equalsIgnoreCase("VI_SICEFECTIVO")){
                    sic1docu.setIdModapago(obj.getIdGeneral());                    
                }                
                this.itemsPayMode.add(obj);
            }
            /**/            
            
        
        }catch(Exception e){            
            System.out.println("Error:" + e.getMessage());
        }
    }    
    
    /****************************************************/
    /****** PROPIEDADES *********************************/
    /****************************************************/
    public List<SelectItem> getItemSTipoDocu() {
        return itemSTipoDocu;
    }
    
    public Sic1prod getSic1prod() {
        return sic1prod;
    }

    public void setSic1prod(Sic1prod sic1prod) {
        this.sic1prod = sic1prod;
    }

    public List<Sic3proddocu> getLstSic3proddocu() {
        return lstSic3proddocu;
    }

    public Sic1docu getSic1docu() {
        return sic1docu;
    }

    public void setSic1docu(Sic1docu sic1docu) {
        this.sic1docu = sic1docu;
    }

    public String getDesFecRegistro() {
        return desFecRegistro;
    }

    public void setDesFecRegistro(String desFecRegistro) {
        this.desFecRegistro = desFecRegistro;
    }

    public Sic3proddocu getSic3proddocu() {
        return sic3proddocu;
    }

    public void setSic3proddocu(Sic3proddocu sic3proddocu) {
        this.sic3proddocu = sic3proddocu;
    }

    public Integer getIndexTabla() {
        return indexTabla;
    }

    public void setIndexTabla(Integer indexTabla) {
        this.indexTabla = indexTabla;
    }
    
    public List<Sic1general> getItemsPayMode() {
        return itemsPayMode;
    }
    
    public void setItemsPayMode(List<Sic1general> itemsPayMode) {
        this.itemsPayMode = itemsPayMode;
    }
    
    public List<SelectItem> getItemsTypeCard() {
        return itemsTypeCard;
    }
    
    public void setItemsTypeCard(List<SelectItem> itemsTypeCard) {
        this.itemsTypeCard = itemsTypeCard;
    }

    public Sic1idenpers getSic1idenpersSelected() {
        return sic1idenpersSelected;
    }

    public void setSic1idenpersSelected(Sic1idenpers sic1idenpersSelected) {
        this.sic1idenpersSelected = sic1idenpersSelected;
    }

    public Sic1pers getSic1pers() {
        return sic1pers;
    }

    public void setSic1pers(Sic1pers sic1pers) {
        this.sic1pers = sic1pers;
    }

    public Sic1idenpersId getSic1idenpersId() {
        return sic1idenpersId;
    }

    public void setSic1idenpersId(Sic1idenpersId sic1idenpersId) {
        this.sic1idenpersId = sic1idenpersId;
    }

    /******************************************************************************/
    /****** METODOS ***************************************************************/
    /******************************************************************************/
    public List<Sic1idenpers> autoCompletePersona(String queryr) throws CustomizerException {
        
        List<Sic1idenpers> lstSic1pers;
        
        try {            
            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
            Sic1pers sic1pers = new Sic1pers();
            sic1pers.setIdPers(new BigDecimal(2));
            lstSic1pers = personServiceImpl.getAll(sic1pers);

        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());                    
        } 
        
        return lstSic1pers;
    }
    
    
    public String deleteAction(Sic3proddocu obj) {
      
        lstSic3proddocu.remove(obj);
        /*Recalculando el numero de la fila*/
        for(int i=0 ; i < lstSic3proddocu.size() ; i++){
            lstSic3proddocu.get(i).setNumIndex(i+1);
        }
        
        this.recalculateTotals();
        
        return null;
    }
    
    public String editAction(Sic3proddocu obj) {
      
        this.sic1prod = obj.getSic1prod();
        this.sic3proddocu = obj;
        this.indexTabla = lstSic3proddocu.indexOf(obj);
        return null;
    }
   
    public void addItem(){
        
        /*ID*/       
        Sic3proddocuId id = new Sic3proddocuId();
        id.setIdProd(this.sic1prod.getIdProd());
        
        this.sic3proddocu.setSic1prod(this.sic1prod);
        this.sic3proddocu.setId(id);
        
        if(this.indexTabla>=0){
            Sic3proddocu obj = this.lstSic3proddocu.get(this.indexTabla);
            this.lstSic3proddocu.remove(obj);
            this.sic3proddocu.setNumIndex(this.indexTabla + 1);
            this.lstSic3proddocu.add(this.indexTabla, sic3proddocu);
            
        }
        else{
            this.sic3proddocu.setNumIndex(this.lstSic3proddocu.size()+1);
            this.lstSic3proddocu.add(sic3proddocu);
        }       
       
        this.recalculateTotals();
        
        /*Limipiar objetos*/
        this.sic3proddocu = new Sic3proddocu();
        this.sic1prod     = new Sic1prod();
        this.indexTabla   = -1;
        
    }
    
    public void searchProduct() throws CustomizerException{
        
        /*Limpiando producto*/
        this.sic3proddocu = new Sic3proddocu();
        this.indexTabla = -1;
        /**/
        
        ProductServiceImpl productServiceImpl = new ProductServiceImpl();
        Sic1prod objProd = productServiceImpl.getByCod(this.sic1prod.getCodProd());
        
        if (objProd != null){
            this.sic1prod = objProd;
        }
        else {
            
            Map<String, Object> options = new HashMap<>();
            options.put("modal", true);
            options.put("resizable", true);
            options.put("draggable", true);
            options.put("position", "center");
            options.put("contentWidth", 1000);
            options.put("contentHeight", 400);
            options.put("includeViewParams", true);

            Map<String, List<String>> params = new HashMap<>();
            List<String> values = new ArrayList<>();
            values.add("true");
            params.put("paramExternalPage", values);

            values = new ArrayList<>();

            if (sic1prod.getCodProd() != null && sic1prod.getCodProd().trim().length() > 0) {
                values.add(sic1prod.getCodProd());
                params.put("paramCodProd", values);
            } else {
                values.add("");
                params.put("paramCodProd", values);
            }

            RequestContext.getCurrentInstance().openDialog("popups/popupProductoRegistrar", options, params);
        }
    }
    
    public void onProductChosen(SelectEvent event) {
        
        this.sic1prod = (Sic1prod)event.getObject();
    }
    
    public void searchPerson() throws CustomizerException{
        
        System.out.println("DOCUMENTO DE IDENTIDAD:" + this.sic1idenpersId.getCodIden());
        try {
            
            String strCodiden = this.sic1idenpersId.getCodIden();
            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
            sic1idenpersSelected = personServiceImpl.getByCodiden(strCodiden);

            if (sic1idenpersSelected != null) {

                System.out.println("Persona:" + sic1idenpersSelected.getSic1pers().getDesPers());
                sic1pers = sic1idenpersSelected.getSic1pers();
                System.out.println("Persona: " + sic1pers);

            } else {

                Map<String, Object> options = new HashMap<>();
                options.put("modal", true);
                options.put("resizable", true);
                options.put("draggable", true);
                options.put("position", "center");
                options.put("contentWidth", 1000);
                options.put("contentHeight", 400);
                options.put("includeViewParams", true);

                Map<String, List<String>> params = new HashMap<>();
                List<String> values = new ArrayList<>();
                values.add("true");
                params.put("paramExternalPage", values);
                values = new ArrayList<>();

                if (strCodiden != null && strCodiden.trim().length() > 0) {

                    values.add(strCodiden.trim());
                    params.put("paramCodIden", values);

                } else {
                    values.add("");
                    params.put("paramCodIden", values);
                }

                RequestContext.getCurrentInstance().openDialog("popups/popupPersonaRegistrar", options, params);
            }

        } catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());

        }
    }
    /*Metodo que se ejecuta despues que se registra a la persona desde el POPUP*/
    public void onPersonChosen(SelectEvent event) {        
        this.sic1idenpersSelected = (Sic1idenpers) event.getObject();
    }
    
    public void recalculateTotals(){
        
        double varPrecio = 0;
        for(Sic3proddocu obj : this.lstSic3proddocu){
            varPrecio += obj.getNumValor().doubleValue();
        }
        
        this.sic1docu.setNumMtoTotal(new BigDecimal(varPrecio).setScale(2, BigDecimal.ROUND_HALF_UP));        
        this.sic1docu.setNumSubtotal(new BigDecimal(varPrecio / (1 + Constantes.CONS_VALUE_IGV)).setScale(2, BigDecimal.ROUND_HALF_UP));        
        this.sic1docu.setNumIgv(new BigDecimal(this.sic1docu.getNumSubtotal().doubleValue() * Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));
        
    }
    
    public void payModeValueChange(ValueChangeEvent e) throws Exception {
        
        /*Cargar Catalogo ANIDADO: TIPO DE TARJETA*/
        try {
            this.itemsTypeCard.clear();
            int idRelSec = -1;

            /*Cargar el id del catalogo secundario o anidado*/
            for(Sic1general obj : this.itemsPayMode){                
                if(obj.getIdGeneral().toString().equals(e.getNewValue().toString()) && obj.getIdGeneralrelsec() != null ){
                    idRelSec = obj.getIdGeneralrelsec().intValue();
                    break;
                }
            }
            System.out.println("idRelSec:" + idRelSec);
            /*Si tiene un catalogo anidado se procede a obtenerlo*/
            if (idRelSec > 0){

                List<String> listCat = new ArrayList();
                listCat.add("VI_SICTIPOTARJ");
                this.sic1generalServiceImpl = new Sic1generalServiceImpl();
                List<Sic1general> lstSic1general = sic1generalServiceImpl.listById_GeneralRel(new BigDecimal(idRelSec));
                
                SelectItem si;
                for (Sic1general obj : lstSic1general) {
                    si = new SelectItem();
                    si.setLabel(obj.getDesGeneral());
                    si.setValue(obj.getIdGeneral());
                    this.itemsTypeCard.add(si);
                }
            }else{
                this.itemsTypeCard.clear();
            }
        
        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    
    
    public void saveOrder() throws CustomizerException, ParseException{
        
        String strResult;
        try {            
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());
            System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
            System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
            System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
            System.out.println("ID_MODAPAGO: " + this.sic1docu.getIdModapago());
            System.out.println("ID_TIPOTARJETA: " + this.sic1docu.getIdTipotarjeta());
            System.out.println("ID_PERS: " + this.sic1idenpersSelected.getSic1pers().getIdPers());
            System.out.println("COD_IDEN: " + this.sic1idenpersSelected.getId().getCodIden());
            System.out.println("FECHA: " + this.desFecRegistro);
            
            System.out.println("MONTO DESCUENTO: " + this.sic1docu.getNumMtodscto());
            System.out.println("IGV: " + this.sic1docu.getNumIgv());
            System.out.println("SUB TOTAL: " + this.sic1docu.getNumSubtotal());
            
            
            /**************** Guardar Documento ************************/
            //Codiden
            String strCodigo = this.sic1docu.getCodSerie() + "-" + this.sic1docu.getNumDocu();
            Sic1idendocu sic1idendocu = new Sic1idendocu();
            sic1idendocu.setCodIden(strCodigo);
            //this.sic1docu.setSic1idendocu(sic1idendocu);

            this.sic1docu.setDesDocu("Compra Nro. " + strCodigo);
            this.sic1docu.setIdPers(this.sic1idenpersSelected.getSic1pers().getIdPers());
            this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));
            
            /*Agregando lista de productos*/
            this.sic1docu.setLstSic3proddocu(lstSic3proddocu);
            
            /*Guardar Orden*/
            System.out.println("Guardando Orden");
            //orderServiceImpl = new DocuOrderServiceImpl();
            sic1idendocu.setSic1docu(sic1docu);
            strResult = orderServiceImpl.insert(sic1idendocu);
            System.out.println("Documento:" + strResult);
                       
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            
            /*Limpiar Objetos*/
            this.sic1docu = new Sic1docu();
            this.lstSic3proddocu.clear();
            this.sic3proddocu = new Sic3proddocu();
            this.sic1prod = new Sic1prod();
            this.sic1idenpersSelected = new Sic1idenpers();
            this.desFecRegistro = UtilClass.getCurrentDay();        

        } catch (ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
}
