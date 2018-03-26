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
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.a2.service.impl.ProductServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
import com.general.security.SessionUtils;
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
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.text.ParseException;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;


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
    //private Sic1idenpers sic1idenpersSelected;
    private Sic1pers sic1pers;
    private Sic1idenpersId sic1idenpersId;
    /**/
    
    private Sic3docuprod sic3docuprod;
    private List<Sic3docuprod> lstSic3docuprod;        
    
    private List<SelectItem> itemSTipoDocu  = new ArrayList();
    private List<Sic1general> itemsPayMode   = new ArrayList();
    private List<SelectItem> itemsTypeCard  = new ArrayList();
    private List<SelectItem> itemsSpend  = new ArrayList(); /*Catalogo de Gastos*/
    
    private String desFecRegistro;
    private Integer indexTabla;
    private String desTituloPagina;
    private String codTRolpers;
    private String codSClaseeven;
    
    private BigDecimal numSumItemsPrice;
    private BigDecimal numSumItemsAmount;
    
    private List<Sic1prod> lstProducts;
    
    private String msjValidation;
    private boolean flgPorRecoger;
    private boolean flgPrecioSinIGV;
    private boolean flgIsSale;
    private boolean editFields;
    
    public OrderController(){
        String day = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day");
        System.out.println("day+ " + day);
    }
    
    @PostConstruct
    public void init() {
        
        String day = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day");
        System.out.println("day+ " + day);
        
        try{
            
            this.editFields         = true;
            msjValidation           = "";
            lstProducts             = new ArrayList();
            sic1generalServiceImpl  = new Sic1generalServiceImpl();
            orderServiceImpl        = new DocuOrderServiceImpl();
           
            desFecRegistro          = UtilClass.getCurrentDay();
            indexTabla              = -1;
            
            
            /*Persona*/            
            sic1pers                = new Sic1pers();
            sic1idenpersId          = new Sic1idenpersId();
            
            /*Producto*/
            sic1prod                = new Sic1prod();
            sic3docuprod            = new Sic3docuprod();
            sic3docuprod.setSic1prod(sic1prod);
            
            sic1docu                = new Sic1docu();
            sic1docu.setNumSubtotal(new BigDecimal("0.00"));
            sic1docu.setNumIgv(new BigDecimal("0.00"));
            sic1docu.setNumMtoTotal(new BigDecimal("0.00"));
            sic1docu.setNumMtovuelto(new BigDecimal("0.00"));
            sic1docu.setNumMtodscto(new BigDecimal("0.00"));
            
            
            numSumItemsPrice = new BigDecimal("0.00");
            numSumItemsAmount = new BigDecimal("0");
            
            
            /*Data*/
            lstSic3docuprod  = new ArrayList();
            
            this.loadCatalogs();
        
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

    public Sic3docuprod getSic3docuprod() {
        return sic3docuprod;
    }

    public void setSic3docuprod(Sic3docuprod sic3docuprod) {
        this.sic3docuprod = sic3docuprod;
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

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public List<Sic1prod> getLstProducts() {
        return lstProducts;
    }

    public void setLstProducts(List<Sic1prod> lstProducts) {
        this.lstProducts = lstProducts;
    }

    public String getMsjValidation() {
        return msjValidation;
    }

    public List<Sic3docuprod> getLstSic3docuprod() {
        return lstSic3docuprod;
    }

    public void setLstSic3docuprod(List<Sic3docuprod> lstSic3docuprod) {
        this.lstSic3docuprod = lstSic3docuprod;
    }

    public boolean isFlgPorRecoger() {
        return flgPorRecoger;
    }

    public void setFlgPorRecoger(boolean flgPorRecoger) {
        this.flgPorRecoger = flgPorRecoger;
    }

    public boolean isFlgIsSale() {
        return flgIsSale;
    }

    public String getCodTRolpers() {
        return codTRolpers;
    }

    public void setCodTRolpers(String codTRolpers) {
        this.codTRolpers = codTRolpers;
    }

    public String getCodSClaseeven() {
        return codSClaseeven;
    }

    public void setCodSClaseeven(String codSClaseeven) {
        this.codSClaseeven = codSClaseeven;
    }
   

    public boolean isFlgPrecioSinIGV() {
        return flgPrecioSinIGV;
    }

    public void setFlgPrecioSinIGV(boolean flgPrecioSinIGV) {
        this.flgPrecioSinIGV = flgPrecioSinIGV;
    }

    public boolean isEditFields() {
        return editFields;
    }

    public void setEditFields(boolean editFields) {
        this.editFields = editFields;
    }

    public BigDecimal getNumSumItemsPrice() {
        return numSumItemsPrice;
    }

    public void setNumSumItemsPrice(BigDecimal numSumItemsPrice) {
        this.numSumItemsPrice = numSumItemsPrice;
    }

    public BigDecimal getNumSumItemsAmount() {
        return numSumItemsAmount;
    }

    public void setNumSumItemsAmount(BigDecimal numSumItemsAmount) {
        this.numSumItemsAmount = numSumItemsAmount;
    }

    public List<SelectItem> getItemsSpend() {
        return itemsSpend;
    }

    public void setItemsSpend(List<SelectItem> itemsSpend) {
        this.itemsSpend = itemsSpend;
    }
        
    /******************************************************************************/
    /****** METODOS ***************************************************************/
    /******************************************************************************/
    
    public void loadCatalogs() throws CustomizerException{
        
        try{
            
            this.itemSTipoDocu.clear();
            this.itemsPayMode.clear();
            this.itemsSpend.clear();
            
            /*Cargar Catalogo: GASTOS*/
            this.itemsSpend = sic1generalServiceImpl.getCataSpend();
            
            /*Cargar Catalogo: STIPODOCU*/            
            List<String> listCat = new ArrayList<>();
            listCat.add("VI_SICFACTURA");
            listCat.add("VI_SICBOLETA");
            listCat.add("VI_SICSINDOCU");
            System.out.println("HiberNate:" + HibernateUtil.getSessionFactory().isClosed());
            this.itemSTipoDocu = sic1generalServiceImpl.listByCod_STipoDocu_SelectItem(listCat);
            
            //*Cargar Catalogo: MODALIDAD PAGO*/
            listCat.clear();
            listCat.add("VI_SICMODAPAGO");
            List<Sic1general> lstSic1general = sic1generalServiceImpl.listByCod_ValorTipoGeneral_Sic1general(listCat);
            for(Sic1general obj : lstSic1general){                
                /*Valor Por Defecto*/
                /*if(obj.getCodValorgeneral().equalsIgnoreCase("VI_SICEFECTIVO")){
                    sic1docu.setIdModapago(obj.getIdGeneral());                    
                }*/
                
                obj.setDesGeneral(obj.getDesGeneral() + " (" + obj.getNumValor() + "%)");
                
                this.itemsPayMode.add(obj);
            }
            /*Valor Por Defecto*/
            sic1docu.setIdModapago(new BigDecimal(-1)); 
            
            /*Limpiar el catalogo de tipo de tarjea*/
            itemsTypeCard  = new ArrayList();
            
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
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
    
    
    public void deleteAction(int index) throws CustomizerException {
        System.out.println("Eliminar Producto");
        lstSic3docuprod.remove(index-1);
        /*Recalculando el numero de la fila*/
        for(int i=0 ; i < lstSic3docuprod.size() ; i++){
            lstSic3docuprod.get(i).setNumIndex(i+1);
        }
        this.indexTabla   = -1;        
       
        /*Calcular Footer de la tabla*/
        this.numSumItemsPrice = new BigDecimal("0.00");
        this.numSumItemsAmount = new BigDecimal("0");
        for(Sic3docuprod obj :  lstSic3docuprod){
            if (obj.getNumValor()!=null){
                this.numSumItemsPrice = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                this.numSumItemsAmount = this.numSumItemsAmount.add(obj.getNumCantidad());
            }
        }
        
        this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
        this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
        this.recalculateTotals(true);
        
        
    }
    
    public void editAction(int index) {
        
        this.sic3docuprod = lstSic3docuprod.get(index-1);        
        this.sic1prod = this.sic3docuprod.getSic1prod();        
        this.indexTabla = index-1;
        
    }
   
    public void addItem() throws CustomizerException{
        
        System.out.println("Agregar Item");
        
        /*Se agrega un producto que se ha editado*/
        if(this.indexTabla>=0){
            
            int item = this.indexTabla + 1;
            Sic3docuprod obj  = this.lstSic3docuprod.get(indexTabla);
            this.lstSic3docuprod.remove(obj);
            
            Sic3docuprodId id = new Sic3docuprodId();
            id.setIdProd(this.sic1prod.getIdProd());
            id.setNumItem(item);
            
            this.sic3docuprod.setSic1prod(this.sic1prod);
            this.sic3docuprod.setId(id);
            
            this.sic3docuprod.setNumIndex(item);
            //this.sic3docuprod.getId().setNumItem(item);
            this.sic3docuprod.getNumValor().setScale(2,BigDecimal.ROUND_HALF_UP );
            
             if(this.sic3docuprod.getNumMtodscto() == null)
                this.sic3docuprod.setNumMtodscto(new BigDecimal("0.00"));
            
            this.lstSic3docuprod.add(this.indexTabla, sic3docuprod);
            
        }
        /*Se agrega un nuevo item*/
        else{
            /*AGREGAR ID*/
            
            int item = this.lstSic3docuprod.size() + 1;
            
            Sic3docuprodId id = new Sic3docuprodId();
            id.setIdProd(this.sic1prod.getIdProd());
            id.setNumItem(item);

            this.sic3docuprod.setSic1prod(this.sic1prod);
            this.sic3docuprod.setId(id);
            this.sic3docuprod.setNumIndex(item);
            if(this.sic3docuprod.getNumMtodscto() == null)
                this.sic3docuprod.setNumMtodscto(new BigDecimal("0.00"));
            
            this.lstSic3docuprod.add(this.sic3docuprod);
        }
       
        /*Calcular Footer de la tabla*/
        this.numSumItemsPrice = new BigDecimal("0.00");
        this.numSumItemsAmount = new BigDecimal("0");        
        for(Sic3docuprod obj :  lstSic3docuprod){
            if (obj.getNumValor()!=null){
                this.numSumItemsPrice = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                this.numSumItemsAmount = this.numSumItemsAmount.add(obj.getNumCantidad());
            }
        }
        
      
        this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
        this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
        this.recalculateTotals(true);
        
        
        /*Limipiar objetos*/
        this.sic3docuprod = new Sic3docuprod();
        this.sic1prod     = new Sic1prod();
        this.indexTabla   = -1;
        
    }
    
    public void searchProductByCod() throws CustomizerException{
        ProductServiceImpl productServiceImpl = new ProductServiceImpl();
        Sic1prod obj = productServiceImpl.getByCod(this.sic1prod.getCodProd());
        this.sic1prod = obj;
        
        if (flgIsSale)
            this.sic3docuprod.setNumValor(obj.getNumPrecio());
    }
    
    /**** AUTOCOMPLETE ******/
    public void searchProduct() throws CustomizerException{
        
        /*Limpiando producto*/
        this.sic3docuprod = new Sic3docuprod();
        //this.indexTabla = -1;
        this.lstProducts.clear();        
        /**/
        
        System.out.println("Producto:" + this.sic1prod.getCodProd());        
        String strCodProd = this.sic1prod.getCodProd();        
        //
        if (strCodProd != null && strCodProd.trim().length() >= 2 ){
            this.sic1prod.setIdProd(null);
            this.sic1prod.setDesProd(null);
            ProductServiceImpl productServiceImpl = new ProductServiceImpl();
            this.lstProducts = productServiceImpl.getAutocompleteByCodProd(strCodProd);
        }else{
            this.sic1prod.setIdProd(null);
            this.sic1prod.setDesProd(null);
        }
        System.out.println("Lista:" + lstProducts.size());
    }
    
    public void selectAutocompleteProduct(Sic1prod obj) throws CustomizerException{
        
        try{
            System.out.println("Producto:" + obj.getCodProd());
            this.sic1prod = obj;
            
            if (flgIsSale)
                this.sic3docuprod.setNumValor(obj.getNumPrecio());
            
            this.lstProducts.clear();
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
    }    
  
    /**/
    
    
    public void searchPerson() throws CustomizerException{
        
        System.out.println("DOCUMENTO DE IDENTIDAD:" + this.sic1idenpersId.getCodIden());
        try {
            
            this.sic1pers.setDesPers("");
            
            String strCodiden = this.sic1idenpersId.getCodIden();
            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
            Sic1idenpers sic1idenpers = personServiceImpl.getByCodiden(strCodiden);

            if (sic1idenpers != null) {

                System.out.println("Persona:" + sic1idenpers.getSic1pers().getDesPers());
                sic1pers = sic1idenpers.getSic1pers();
                System.out.println("Persona: " + sic1pers);
                
            }
        } catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    /*Metodo que se ejecuta despues que se registra a la persona desde el POPUP*/
//    public void onPersonChosen(SelectEvent event) {        
//        this.sic1idenpersSelected = (Sic1idenpers) event.getObject();
//    }
    
    public void loadTotals(){
        if (sic1docu.getNumMtoTotal() != null && sic1docu.getNumMtoTotal().intValue() > 0){
            this.sic1docu.setNumSubtotal(new BigDecimal(sic1docu.getNumMtoTotal().doubleValue()/(1+Constantes.CONS_VALUE_IGV)).setScale(2, BigDecimal.ROUND_HALF_UP));
            this.sic1docu.setNumIgv(new BigDecimal(this.sic1docu.getNumSubtotal().doubleValue()* Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));        
            this.sic1docu.setNumMtoTotal(this.sic1docu.getNumSubtotal().add(sic1docu.getNumIgv()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }
    
    public void recalculateTotals(boolean flgPayModeChange) throws CustomizerException{
        
        try{
            
            double numTotalPrice = 0;            
            boolean flgTarjeta = false;
            boolean flgErrorDescuento = false;
            BigDecimal numDescuento = new BigDecimal(0);
            this.sic1docu.setNumMtovuelto(new BigDecimal("0.00"));
            
            
            /*Se obtiene el precio total: Sumando el precio * cantidad de cada producto*/
            for(Sic3docuprod obj : this.lstSic3docuprod){
                
                System.out.println("mtoDescuento:" + obj.getNumMtodscto());
                System.out.println("Cantidad:" + obj.getNumCantidad());
                
                numTotalPrice       += obj.getNumValor().doubleValue() * obj.getNumCantidad().doubleValue();
                if (obj.getNumMtodscto() != null)
                    numDescuento = numDescuento.add(obj.getNumMtodscto().multiply(obj.getNumCantidad()));
            }
            System.out.println("numDescuento:" + numDescuento);
            numDescuento = numDescuento.setScale(2, BigDecimal.ROUND_HALF_UP);
            this.sic1docu.setNumMtodscto(numDescuento);
            
            /*******************/
            /**DESCUENTO *******/
            /*******************/         

            /*El descuento no debe ser mayor al IMPORTE TOTAL*/
            if (numDescuento != null && numDescuento.doubleValue() > numTotalPrice){
                numDescuento = new BigDecimal(0);
                flgErrorDescuento = true;                
            }
            /*En caso haya descuento se resta del importe total*/
            if (numDescuento!= null && numDescuento.doubleValue() <= numTotalPrice)
                numTotalPrice = numTotalPrice - numDescuento.doubleValue();
            
            
            /*******************/
            /**PAGO CON TARJETA*/
            /*******************/
            /*Se obtiene cargo adicional en caso se haya configurado*/
            double numCargoTarjeta = 0;
            for(Sic1general obj : this.itemsPayMode){
                if(this.sic1docu.getIdModapago().equals(obj.getIdGeneral())){
                    if(obj.getNumValor() != null && obj.getNumValor().doubleValue() > 0){
                        numCargoTarjeta = obj.getNumValor().doubleValue()/100;
                        flgTarjeta = true;
                    }
                }
            }
            if(this.sic1docu.getNumMtotarjeta() != null && this.sic1docu.getNumMtotarjeta().intValue() > 0)
                numTotalPrice = numTotalPrice + (this.sic1docu.getNumMtotarjeta().doubleValue() * numCargoTarjeta);
            else
                numTotalPrice = numTotalPrice + (numTotalPrice * numCargoTarjeta);
            
            
            /***************************************/
            /**PRECIO DEL PRODUCTO NO INCLUYE  IGV*/
            /***************************************/
            /*Los precios de los productos no incluyen IGV: Algunos proveedores su precio no incluye IGV
             Cuando Precio:
                - Incluye IGV: La sumatoria de precio de todos los productos va en el TOTAL de la FACTURA/BOLETA 
                - No Incluye IGV: La sumatoria de precio de todos los productos va en el SUB-TOTAL de la FACTURA/BOLETA
            */            
            /*Por defecto el precio ya incluye IGV, peri si no incluye IVG, entonces se suma el IGV, con esto igual se obtiene el MONTO TOTAL DE LA OPERACION*/            
            if (this.flgPrecioSinIGV){                
                numTotalPrice = numTotalPrice + numTotalPrice*Constantes.CONS_VALUE_IGV;
            }
            
            /**************************/
            /**SE CALCULA LOS TOTALES**/
            /**************************/
            double numSubtotal = numTotalPrice/(1+Constantes.CONS_VALUE_IGV);
            this.sic1docu.setNumSubtotal(new BigDecimal(numSubtotal).setScale(2, BigDecimal.ROUND_HALF_UP));
            this.sic1docu.setNumIgv(new BigDecimal(numSubtotal * Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));
            //this.sic1docu.setNumMtoTotal(new BigDecimal(numTotalPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            this.sic1docu.setNumMtoTotal(this.sic1docu.getNumSubtotal().add(sic1docu.getNumIgv()).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            if (flgErrorDescuento){
                this.sic1docu.setNumMtodscto(numDescuento);//Se setea a 0 cuando el monto a descontar es mayor al importe total.
                throw new ValidationException("El descuento no puede ser mayor al importe Total.");
            }
            
            ///
            
            //
            /*Calculando los campos de MTOEFECTIVO Y MTO TARJETA segun la forma de pago seleccionada*/
            if (this.flgIsSale && numTotalPrice > 0){
                for(Sic1general obj : this.itemsPayMode){
                    if (obj.getIdGeneral().compareTo(sic1docu.getIdModapago()) == 0){
                        if(obj.getCodValorgeneral().equalsIgnoreCase("VI_SICEFECTIVO")){
                            if(flgPayModeChange || (this.sic1docu.getNumMtoefectivo() != null && this.sic1docu.getNumMtoefectivo().compareTo(new BigDecimal(0))== 0)){
                                this.sic1docu.setNumMtoefectivo(this.sic1docu.getNumMtoTotal());
                                break;
                            }
                        }
                        else if(obj.getCodValorgeneral().contains("VI_SICTARJ")){
                            if(flgPayModeChange || (this.sic1docu.getNumMtotarjeta()!= null && this.sic1docu.getNumMtotarjeta().compareTo(new BigDecimal(0))== 0)){
                                this.sic1docu.setNumMtotarjeta(this.sic1docu.getNumMtoTotal());
                                break;
                            }
                        }
                    }
                }
            }
            
            /********************/
            /***CALCULAR VUELTO**/
            /********************/
            /*Si el importe total es igual a 0 no se realiza ningun calculo*/
            if (this.flgIsSale && numTotalPrice > 0){
                /*Dando formato a 2 decimales*/
                if (this.sic1docu.getNumMtotarjeta() == null){
                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    this.sic1docu.getNumMtotarjeta().setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                if (this.sic1docu.getNumMtoefectivo() == null){
                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                else
                    this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);
                
                /*calcular vuelto*/
                if (this.sic1docu.getNumMtoefectivo().doubleValue() > 0 || this.sic1docu.getNumMtotarjeta().doubleValue()>0){
                    double numVuelto = (this.sic1docu.getNumMtotarjeta().add(this.sic1docu.getNumMtoefectivo()).doubleValue()) - this.sic1docu.getNumMtoTotal().doubleValue();
                    this.sic1docu.setNumMtovuelto(new BigDecimal(numVuelto).setScale(2,BigDecimal.ROUND_HALF_UP));
                }else
                    this.sic1docu.setNumMtovuelto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
            }else{
                this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            
            /*Solo se evalua cuando es un nuevo registro*/
            /*if (this.sic1docu.getIdDocu() == null){
                if(flgTarjeta){
                    this.sic1docu.setNumMtotarjeta(this.sic1docu.getNumMtoTotal());
                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
                }
                else{
                    this.sic1docu.setNumMtoefectivo(this.sic1docu.getNumMtoTotal());
                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
                }
            }*/
            
        }catch(ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void payModeValueChange() throws CustomizerException {
        
        /*Cargar Catalogo ANIDADO: TIPO DE TARJETA*/
        try {
            this.itemsTypeCard.clear();
            int idRelSec = -1;
            
            System.out.println("Modo Pago:" + sic1docu.getIdModapago());

            /*Cargar el id del catalogo secundario o anidado*/
            for(Sic1general obj : this.itemsPayMode){
                if(obj.getIdGeneral().toString().equals(sic1docu.getIdModapago().toString()) && obj.getIdGeneralrelsec() != null ){
                    idRelSec = obj.getIdGeneralrelsec().intValue();
                    break;
                }
            }
            
           
            
            System.out.println("idRelSec:" + idRelSec);
            /*Si tiene un catalogo anidado se procede a obtenerlo*/
            if (idRelSec > 0){
                
                /*Se obtiene los tipo de Tarjeta: VISA - MASTERCARD*/
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
                this.sic1docu.setNumMtotarjeta(new BigDecimal(0));
            }
            
            /*Solo se ejecuta cuando se registrar una nueva venta*/
            if (this.sic1docu.getIdDocu() == null){

                /*Limpiando los campos segun la FORMA DE PAGO seleccionada*/
                if(this.flgIsSale){
                    for(Sic1general obj : this.itemsPayMode){
                       if (obj.getIdGeneral().compareTo(sic1docu.getIdModapago()) == 0)
                           if(obj.getCodValorgeneral().equalsIgnoreCase("VI_SICEFECTIVO")){
                               this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
                               this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
                           }
                           else{
                               this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
                               this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
                           }
                   }
                }                
                
                this.recalculateTotals(true);
            }
        
        } catch(Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
   
    public void saveSpend() throws CustomizerException{
        
        String strResult;
        String strMessage = null;
        this.msjValidation = "";
        try {
            
            System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
            System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
            System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());
            System.out.println("MONTO TOTAL: " + this.sic1docu.getNumMtoTotal());
            
            System.out.println("COD_IDEN: " + this.sic1idenpersId.getCodIden());
            System.out.println("FECHA: " + this.desFecRegistro);
            System.out.println("IGV: " + this.sic1docu.getNumIgv());
            System.out.println("SUB TOTAL: " + this.sic1docu.getNumSubtotal());
            System.out.println("PRECION SIN IGV: " + this.flgPrecioSinIGV);
            
            if (false){
                this.msjValidation = "<UL type = 'square'><LI>" + strMessage + "</LI></UL>";
                System.out.println("ERRROR: " + this.msjValidation);
                 UtilClass.addErrorMessage("holaaa");
            }
            else {
            
                BigDecimal idPers = this.sic1pers.getIdPers();                
                
                System.out.println("ID_PERS CLIENTE/PROVEEDOR: " + idPers);

                if ( idPers.intValue() <= 0  ){
                    strMessage = "Falta ingresar el Cliente o Proveedor relacionado a la orden.";                    
                    throw new ValidationException(strMessage);
                }                
                
                Sic1idendocu sic1idendocu = new Sic1idendocu();

                //this.sic1docu.setDesDocu("Compra Nro. " + strCodigo);
                this.sic1docu.setIdPers(SessionUtils.getUserId()); //Login
                this.sic1docu.setIdPersexterno(idPers);
                this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));
                this.sic1docu.setFlgPrecsinIGV(this.flgPrecioSinIGV==true?1:0);
                //this.sic1docu.setCodSclaseeven(this.codSClaseeven);

                /*Codigo del estado*/
                if (this.flgPorRecoger){
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPORRECOGER);
                }else
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAFINALIZADO);

                /*Guardar Orden*/
                System.out.println("Guardando Gasto");
                //orderServiceImpl = new DocuOrderServiceImpl();
                sic1idendocu.setSic1docu(sic1docu);
                strResult = orderServiceImpl.insert(sic1idendocu);
                System.out.println("Documento:" + strResult);

                UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);

                /*Limpiar Objetos*/
                this.sic1docu = new Sic1docu();
                this.sic1idenpersId = new Sic1idenpersId();
                this.sic1pers = new Sic1pers();
                
                this.desFecRegistro = UtilClass.getCurrentDay();
                this.flgPorRecoger = false;
                
                sic1docu.setNumSubtotal(new BigDecimal("0.00"));
                sic1docu.setNumIgv(new BigDecimal("0.00"));
                sic1docu.setNumMtoTotal(new BigDecimal("0.00"));
                
                this.loadCatalogs();
            }

        } catch (ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());            
        }catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
        
        
        
    }
    
    public void saveOrder() throws CustomizerException, ParseException{
        
        String strResult;
        String strMessage = null;
        this.msjValidation = "";
        try {
            
            System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
            System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
            System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());           
            System.out.println("ID_MODAPAGO: " + this.sic1docu.getIdModapago());
            System.out.println("ID_TIPOTARJETA: " + this.sic1docu.getIdTipotarjeta());
            System.out.println("NUM_MTOTARJETA: " + this.sic1docu.getNumMtotarjeta());
            System.out.println("NUM_MTOEFECTIVO: " + this.sic1docu.getNumMtoefectivo());
            System.out.println("MONTO DESCUENTO: " + this.sic1docu.getNumMtodscto());
            
            System.out.println("COD_IDEN: " + this.sic1idenpersId.getCodIden());
            System.out.println("FECHA: " + this.desFecRegistro);
            System.out.println("IGV: " + this.sic1docu.getNumIgv());
            System.out.println("SUB TOTAL: " + this.sic1docu.getNumSubtotal());
            System.out.println("PRECION SIN IGV: " + this.flgPrecioSinIGV);
            
            if (false){
                this.msjValidation = "<UL type = 'square'><LI>" + strMessage + "</LI></UL>";
                System.out.println("ERRROR: " + this.msjValidation);
                 UtilClass.addErrorMessage("holaaa");
            }
            else {
            
                BigDecimal idPers = this.sic1pers.getIdPers();
                int numItems      = this.lstSic3docuprod.size();
                
                System.out.println("ID_PERS CLIENTE/PROVEEDOR: " + idPers);

                if ( idPers.intValue() <= 0  ){
                    strMessage = "Falta ingresar el Cliente o Proveedor relacionado a la orden.";                    
                    throw new ValidationException(strMessage);
                }
                if (numItems == 0  ){
                    strMessage = "Falta ingresar productos a la orden.";                    
                    throw new ValidationException(strMessage);
                }
                
                /*REGISTRAR VENTA: Validando si los montos de pago cuadran con el importe Total calculado*/
                if (this.flgIsSale){
                    /*Dando formato a 2 decimales*/
                    if (this.sic1docu.getNumMtotarjeta() == null){
                        this.sic1docu.setNumMtotarjeta(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
                    }
                    if (this.sic1docu.getNumMtoefectivo() == null){
                        this.sic1docu.setNumMtoefectivo(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
                    }
                    
                    if (this.sic1docu.getNumMtovuelto().doubleValue() < 0){
                        strMessage = "Vuelto no puede ser menor a 0: Ingrese correctamente los montos de pago.";
                        throw new ValidationException(strMessage);
                    }
                    double mtoPagado = (sic1docu.getNumMtoefectivo().doubleValue() + 
                                        sic1docu.getNumMtotarjeta().doubleValue()) -
                                        sic1docu.getNumMtovuelto().doubleValue();
                    if(sic1docu.getNumMtoTotal().doubleValue() - mtoPagado != 0){
                        strMessage = "El resultado de sumar( Tarjeta + Efectivo - Vuelto) debe ser igual al total de la venta, verifique los montos de pago ingresados.";
                        throw new ValidationException(strMessage);
                    }
                }

                /**************** Guardar Documento ************************/
                //Codiden
//                String strCodigo = this.sic1docu.getIdStipodocu() + "." + /*ID de FACTURA O BOLETA*/
//                                    this.sic1docu.getCodSerie().trim() + "-" + this.sic1docu.getNumDocu();
//                Sic1idendocu sic1idendocu = new Sic1idendocu();
//                sic1idendocu.setCodIden(strCodigo);
                //this.sic1docu.setSic1idendocu(sic1idendocu);
                
                Sic1idendocu sic1idendocu = new Sic1idendocu();

                //this.sic1docu.setDesDocu("Compra Nro. " + strCodigo);
                this.sic1docu.setIdPers(SessionUtils.getUserId()); //Login
                this.sic1docu.setIdPersexterno(idPers);
                this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));
                this.sic1docu.setFlgPrecsinIGV(this.flgPrecioSinIGV==true?1:0);
                this.sic1docu.setCodSclaseeven(this.codSClaseeven);

                /*Agregando lista de productos*/
                this.sic1docu.setLstSic3docuprod(lstSic3docuprod);
                
                /*Codigo del estado*/
                if (this.flgPorRecoger){
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPORRECOGER);
                }else
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAFINALIZADO);

                /*Guardar Orden*/
                System.out.println("Guardando Orden");
                //orderServiceImpl = new DocuOrderServiceImpl();
                sic1idendocu.setSic1docu(sic1docu);
                strResult = orderServiceImpl.insert(sic1idendocu);
                System.out.println("Documento:" + strResult);

                UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);

                /*Limpiar Objetos*/
                this.sic1docu = new Sic1docu();
                this.sic1idenpersId = new Sic1idenpersId();
                this.sic1pers = new Sic1pers();
                this.lstSic3docuprod.clear();
                this.sic3docuprod = new Sic3docuprod();
                this.sic1prod = new Sic1prod();                    
                this.desFecRegistro = UtilClass.getCurrentDay();
                this.flgPorRecoger = false;
                
                sic1docu.setNumSubtotal(new BigDecimal("0.00"));
                sic1docu.setNumIgv(new BigDecimal("0.00"));
                sic1docu.setNumMtoTotal(new BigDecimal("0.00"));
                sic1docu.setNumMtovuelto(new BigDecimal("0.00"));
                
                numSumItemsPrice = new BigDecimal("0.00");
                numSumItemsAmount = new BigDecimal("0");
                
                this.loadCatalogs();
            }

        } catch (ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    /*Metodo que permite finalizar las ordenes pendientes de recojo*/
    public void finishPendingOrder() throws CustomizerException{
        
        try{        
            System.out.println("Finalizar Orden Pendiente: "  + this.sic1docu.getIdDocu());
            this.orderServiceImpl.relateDocuEsta(this.sic1docu.getIdDocu(), Constantes.CONS_COD_ESTADOCU_COMPROBANTE, Constantes.CONS_COD_ESTAFINALIZADO);
            this.flgPorRecoger = false;        

            UtilClass.addInfoMessage("La orden se finalizó correctamente.");
        }catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    /* Metodo que se ejecuta cuando es invocado desde una pagina externa, se llama desde desde el tag <f:metadata> ubicado en la
     * página XHTML
     */
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{

        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

            String tituloPagina     = (String)flash.get("paramTituloPagina");
            BigDecimal idDocu       = (BigDecimal)flash.get("paramIdDocu");  
            String codSClaseevenTmp    = (String)flash.get("paramCodSClaseeven");
            String codTRolpersTmp      = (String)flash.get("paramCodTRolpers");
            

            System.out.println("tituloPagina:" + tituloPagina); 
            System.out.println("codTRolpers:" + codTRolpersTmp); 

            if (tituloPagina != null)
                this.desTituloPagina = tituloPagina;
            
            /*Esto permite que cuando se registra un nueva persona, se guarde con el rol de CLIENTE O PROVEEDOR*/
            if (codTRolpersTmp != null)
                this.codTRolpers = codTRolpersTmp;
            else
                throw new CustomizerException("No se cargo el Tipo de Rol de la persona.");
            
            //Si idDocu viene NULL quiere decir que se está tratando de registrar una nueva orden
            //Si es asi, la subclase del evento no puede esta vacia.
            if (idDocu == null) {
                if (codSClaseevenTmp != null) {
                    this.codSClaseeven = codSClaseevenTmp;                    
                    //Se verifica si está realizando una compra, si es asi se debe ocultar los controles
                    //(Forma de pago, Mto Tarjeta y Efectivo)
                    if (codSClaseevenTmp.equalsIgnoreCase(Constantes.CONS_COD_SCLASEEVEN_VENTA)){
                        this.flgIsSale = true;
                    }
                }
                else
                    throw new CustomizerException("No se cargo la sub clase del evento.");
                
            }


            /*OBTENER LOS DATOS DE LA ORDEN*/
            if (idDocu != null && idDocu.intValue() > 0 ){
                
                /*Verificar si es una venta*/
                if (codSClaseevenTmp != null) {
                    if (codSClaseevenTmp.equalsIgnoreCase(Constantes.CONS_COD_SCLASEEVEN_VENTA))
                        this.flgIsSale = true;
                }else
                    throw new CustomizerException("No se cargo la sub clase del evento.");
                
                /*La FACTURA o BOLETA no se puede editar*/
                this.editFields = false;

                /*Se obtiene los datos de la orden*/
                Sic1idendocu sic1idendocu = orderServiceImpl.getOrderById(idDocu);

                /*Se obtiene los datos del Cliente/Proveedor*/
                PersonServiceImpl personServiceImpl = new PersonServiceImpl();
                Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPersexterno());

                /*Seteando en las variables para que se visualice los datos en la pantalla*/
                this.sic1docu           = sic1idendocu.getSic1docu();
                this.sic1pers           = sic1idenpers.getSic1pers();
                this.sic1idenpersId     = sic1idenpers.getId();
                this.lstSic3docuprod    = sic1idendocu.getSic1docu().getLstSic3docuprod();
                
                /*Recalculando el Nro. item de la tabla detalle de productos*/
                for(int i = 0; i < this.lstSic3docuprod.size(); i++){
                    this.lstSic3docuprod.get(i).setNumIndex(i+1);
                }
                
                /*Formateando Montos*/
                if (this.sic1docu.getNumMtodscto()!= null)
                    this.sic1docu.getNumMtodscto().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtodscto(new BigDecimal("0.00"));
                
                if (this.sic1docu.getNumMtoefectivo()!= null)
                    this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
                
                if (this.sic1docu.getNumMtotarjeta()!= null)
                    this.sic1docu.getNumMtotarjeta().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
                
                /*Se obtiene si el precio incluye o no IGV*/
                this.flgPrecioSinIGV = this.sic1docu.getFlgPrecsinIGV()==1?true:false;
                
                /*Marcado el check PENDIENTE POR RECOGER*/
                if(this.sic1docu.getCodEstadocu().equalsIgnoreCase(Constantes.CONS_COD_ESTAPORRECOGER))
                    this.flgPorRecoger = true;

                this.recalculateTotals(false);
                
                /*Cargar el catalogo de Tipo de Tarjeta*/
                this.payModeValueChange();
            }        
        }
    }
    
    
    /* Metodo que se ejecuta cuando es invocado desde una pagina externa, se llama desde desde el tag <f:metadata> ubicado en la
     * página XHTML
     */
    public void loadOrderDetails(ComponentSystemEvent event) throws CustomizerException{

        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

            String tituloPagina     = (String)flash.get("paramTituloPagina");
            BigDecimal idDocu       = (BigDecimal)flash.get("paramIdDocu");  
            String codSClaseevenTmp    = (String)flash.get("paramCodSClaseeven");            
            
            System.out.println("tituloPagina:" + tituloPagina);             
            System.out.println("idDocu:" + idDocu);
            System.out.println("codSClaseevenTmp:" + codSClaseevenTmp);

            if (tituloPagina != null)
                this.desTituloPagina = tituloPagina;
            
            /*OBTENER LOS DATOS DE LA ORDEN*/
            if (idDocu != null && idDocu.intValue() > 0 ){
                
                /*Verificar si es una venta*/
                if (codSClaseevenTmp != null) {
                    if (codSClaseevenTmp.equalsIgnoreCase(Constantes.CONS_COD_SCLASEEVEN_VENTA))
                        this.flgIsSale = true;
                }else
                    throw new CustomizerException("No se cargo la sub clase del evento.");
                
                /*La FACTURA o BOLETA no se puede editar*/
                this.editFields = false;

                /*Se obtiene los datos de la orden*/
                Sic1idendocu sic1idendocu = orderServiceImpl.getOrderById(idDocu);

                /*Se obtiene los datos del Cliente/Proveedor*/
                PersonServiceImpl personServiceImpl = new PersonServiceImpl();
                Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPersexterno());

                /*Seteando en las variables para que se visualice los datos en la pantalla*/
                this.sic1docu           = sic1idendocu.getSic1docu();
                this.sic1pers           = sic1idenpers.getSic1pers();
                this.sic1idenpersId     = sic1idenpers.getId();
                this.lstSic3docuprod    = sic1idendocu.getSic1docu().getLstSic3docuprod();
                
                /*Recalculando el Nro. item de la tabla detalle de productos*/
                for(int i = 0; i < this.lstSic3docuprod.size(); i++){
                    this.lstSic3docuprod.get(i).setNumIndex(i+1);
                }
                
                 /*Calcular Footer de la tabla*/
                this.numSumItemsPrice = new BigDecimal("0.00");
                this.numSumItemsAmount = new BigDecimal("0");
                for(Sic3docuprod obj :  lstSic3docuprod){
                    if (obj.getNumValor()!=null){
                        this.numSumItemsPrice = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                        this.numSumItemsAmount = this.numSumItemsAmount.add(obj.getNumCantidad());
                    }
                }
                
                
                /*Formateando Montos*/
                if (this.sic1docu.getNumMtodscto()!= null)
                    this.sic1docu.getNumMtodscto().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtodscto(new BigDecimal("0.00"));
                
                if (this.sic1docu.getNumMtoefectivo()!= null)
                    this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
                
                if (this.sic1docu.getNumMtotarjeta()!= null)
                    this.sic1docu.getNumMtotarjeta().setScale(2,BigDecimal.ROUND_HALF_UP);
                else
                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
                
                /*Se obtiene si el precio incluye o no IGV*/
                this.flgPrecioSinIGV = this.sic1docu.getFlgPrecsinIGV()==1?true:false;
                
                /*Marcado el check PENDIENTE POR RECOGER*/
                if(this.sic1docu.getCodEstadocu().equalsIgnoreCase(Constantes.CONS_COD_ESTAPORRECOGER))
                    this.flgPorRecoger = true;

                this.recalculateTotals(false);
                
                /*Cargar el catalogo de Tipo de Tarjeta*/
                this.payModeValueChange();
            }        
        }
    }
    
    /* Metodo que se ejecuta cuando es invocado desde una pagina externa, se llama desde desde el tag <f:metadata> ubicado en la
     * página XHTML
     */
    public void loadRegisterSpend(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

            String tituloPagina = (String)flash.get("paramTituloPagina");
            
            if (tituloPagina != null)
                this.desTituloPagina = tituloPagina;
        }
    }
    
}
