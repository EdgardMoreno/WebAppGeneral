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
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docudocuId;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
import com.general.hibernate.views.ViSicdocu;
import com.general.security.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import com.general.util.beans.Constantes;
import com.general.util.beans.Impresion;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.text.ParseException;
import javax.faces.bean.SessionScoped;


@Named
@ManagedBean
@SessionScoped
public class OrderController implements Serializable{
 
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);    
    
    private DocuOrderServiceImpl orderServiceImpl;
    private MaestroCatalogoServiceImpl objMaeCataService;    
    
    /*Producto*/
    private Sic1idendocu sic1idendocu;
    private Sic1docu sic1docu;
    private Sic1prod sic1prod;
    
    /*Persona*/
    //private Sic1idenpers sic1idenpersSelected;
    private Sic1pers sic1pers;
    private Sic1idenpersId sic1idenpersId;
    /**/
    
    private Sic3docuprod sic3docuprod;
    private List<Sic3docuprod> lstSic3docuprod;
    private List<ViSicdocu> lstViSicdocus;
    
    private List<Sic1stipodocu> itemSTipoDocu   = new ArrayList();
    private List<Sic1general> itemsPayMode      = new ArrayList();
    private List<SelectItem> itemsTypeCard      = new ArrayList();
    private List<Sic1sclaseeven> itemsSpend         = new ArrayList(); /*Catalogo de Gastos*/
    
    private String desFecRegistro;
    private Integer indexTabla;
    private String desTituloPagina;
    private String codTRolpersExterno;
    private String codSClaseeven;
    private String codClaseeven;
    private Double numMtoComiTarjeta;
    private Double numMtoTotalComiTarjeta;
    
    private Double numMtoPagadoTarjeta;
    private Double numMtoPagadoComiTarjeta;
    private Double numMtoPagadoEfectivo;
    private Double numTotalPagado;
    
    private BigDecimal numSumItemsPrice;
    private BigDecimal numSumItemsAmount;
    private BigDecimal numSumItemsDescuento;
    
    
    private List<Sic1prod> lstProducts;
    
    private String msjValidation;
    private boolean flgPorRecoger;
    private boolean flgPrecioSinIGV;
    private boolean flgIsSale;
    private boolean flgEditPerson;
    private boolean flgEditProducts;
    private boolean flgEditarFecha;
    private boolean flgEditarFormaPago;
    private boolean flgMostrarFormaPago;
    private boolean flgEditarTipoDocumento;
    private boolean flgEditarNroDocumento;
    private BigDecimal idDocuImpresion;
    
    private boolean flgMostrarNumVoucher;
    
    
    public OrderController(){
       
    }
    
    //@PostConstruct
    public void init() throws CustomizerException {
        
        try{
            
            this.flgEditPerson              = true;
            this.flgEditProducts            = true;
            this.flgEditarFecha             = true;
            this.flgEditarFormaPago         = true;
            this.flgMostrarFormaPago        = true;
            this.flgEditarTipoDocumento     = true;
            this.flgEditarNroDocumento      = true;
            
            this.flgPorRecoger              = false;
            this.msjValidation              = "";
            this.lstProducts                = new ArrayList();
            this.lstViSicdocus              = new ArrayList<>();
            this.objMaeCataService          = new MaestroCatalogoServiceImpl();
            this.orderServiceImpl           = new DocuOrderServiceImpl();
           
            this.desFecRegistro             = UtilClass.getCurrentDay();
            this.indexTabla                 = -1;
            this.idDocuImpresion            = new BigDecimal(0);
            
            /*Persona*/            
            this.sic1pers                   = new Sic1pers();
            this.sic1idenpersId             = new Sic1idenpersId();
            
            /*Producto*/
            this.sic1prod                   = new Sic1prod();
            this.sic3docuprod               = new Sic3docuprod();
            this.sic3docuprod.setSic1prod(sic1prod);
            
            this.sic1idendocu               = new Sic1idendocu();            
            this.sic1docu                   = new Sic1docu();
            this.sic1docu.setNumSubtotal(new BigDecimal("0.00"));
            this.sic1docu.setNumIgv(new BigDecimal("0.00"));
            this.sic1docu.setNumMtoTotal(new BigDecimal("0.00"));
            this.sic1docu.setNumMtovuelto(new BigDecimal("0.00"));
            this.sic1docu.setNumMtodscto(new BigDecimal("0.00")); 
            
            this.numMtoPagadoTarjeta        = 0.00;
            this.numMtoPagadoEfectivo       = 0.00;
            this.numTotalPagado             = 0.00;
            this.numMtoPagadoComiTarjeta    = 0.00;
            
            this.numSumItemsPrice           = new BigDecimal("0.00");
            this.numSumItemsAmount          = new BigDecimal("0");            
            this.numSumItemsDescuento       = new BigDecimal("0");            
            
            /*Data*/
            this.lstSic3docuprod            = new ArrayList();
            
            this.loadCatalogs();
            
        }catch(CustomizerException e){            
            throw new CustomizerException(e.getMessage());
        }
    }       
      
    

    /****************************************************/
    /****** PROPIEDADES *********************************/
    /****************************************************/
    
    public BigDecimal getIdDocuImpresion() {
        return idDocuImpresion;
    }
    
    public void setIdDocuImpresion(BigDecimal idDocuImpresion) {
        this.idDocuImpresion = idDocuImpresion;
    }

    public List<Sic1stipodocu> getItemSTipoDocu() {
        return itemSTipoDocu;
    }
    
    public Sic1prod getSic1prod() {
        return sic1prod;
    }

    public void setSic1prod(Sic1prod sic1prod) {
        this.sic1prod = sic1prod;
    }

    public Sic1idendocu getSic1idendocu() {
        return sic1idendocu;
    }

    public void setSic1idendocu(Sic1idendocu sic1idendocu) {
        this.sic1idendocu = sic1idendocu;
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
    
    public void setFlgIsSale(boolean flgIsSale) {
        this.flgIsSale = flgIsSale;
    }
    
    public String getCodTRolpersExterno() {
        return codTRolpersExterno;
    }

    public void setCodTRolpersExterno(String codTRolpersExterno) {
        this.codTRolpersExterno = codTRolpersExterno;
    }

    public String getCodSClaseeven() {
        return codSClaseeven;
    }

    public void setCodSClaseeven(String codSClaseeven) {
        this.codSClaseeven = codSClaseeven;
    }

    public String getCodClaseeven() {
        return codClaseeven;
    }

    public void setCodClaseeven(String codClaseeven) {
        this.codClaseeven = codClaseeven;
    }

    public boolean isFlgPrecioSinIGV() {
        return flgPrecioSinIGV;
    }

    public void setFlgPrecioSinIGV(boolean flgPrecioSinIGV) {
        this.flgPrecioSinIGV = flgPrecioSinIGV;
    }

    public boolean isFlgEditPerson() {
        return flgEditPerson;
    }

    public void setFlgEditPerson(boolean flgEditPerson) {
        this.flgEditPerson = flgEditPerson;
    }    

    public boolean isFlgEditProducts() {
        return flgEditProducts;
    }

    public void setFlgEditProducts(boolean flgEditProducts) {
        this.flgEditProducts = flgEditProducts;
    }

    public boolean isFlgEditarFecha() {
        return flgEditarFecha;
    }

    public void setFlgEditarFecha(boolean flgEditarFecha) {
        this.flgEditarFecha = flgEditarFecha;
    }

    public boolean isFlgEditarFormaPago() {
        return flgEditarFormaPago;
    }

    public void setFlgEditarFormaPago(boolean flgEditarFormaPago) {
        this.flgEditarFormaPago = flgEditarFormaPago;
    }

    public boolean isFlgMostrarFormaPago() {
        return flgMostrarFormaPago;
    }

    public void setFlgMostrarFormaPago(boolean flgMostrarFormaPago) {
        this.flgMostrarFormaPago = flgMostrarFormaPago;
    }

    public boolean isFlgEditarTipoDocumento() {
        return flgEditarTipoDocumento;
    }

    public void setFlgEditarTipoDocumento(boolean flgEditarTipoDocumento) {
        this.flgEditarTipoDocumento = flgEditarTipoDocumento;
    }

    public boolean isFlgEditarNroDocumento() {
        return flgEditarNroDocumento;
    }

    public void setFlgEditarNroDocumento(boolean flgEditarNroDocumento) {
        this.flgEditarNroDocumento = flgEditarNroDocumento;
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

    public BigDecimal getNumSumItemsDescuento() {
        return numSumItemsDescuento;
    }

    public void setNumSumItemsDescuento(BigDecimal numSumItemsDescuento) {
        this.numSumItemsDescuento = numSumItemsDescuento;
    }

    public List<Sic1sclaseeven> getItemsSpend() {
        return itemsSpend;
    }

    public void setItemsSpend(List<Sic1sclaseeven> itemsSpend) {
        this.itemsSpend = itemsSpend;
    }

    public List<ViSicdocu> getLstViSicdocus() {
        return lstViSicdocus;
    }

    public void setLstViSicdocus(List<ViSicdocu> lstViSicdocus) {
        this.lstViSicdocus = lstViSicdocus;
    }

    public Double getNumMtoComiTarjeta() {
        return numMtoComiTarjeta;
    }

    public void setNumMtoComiTarjeta(Double numMtoComiTarjeta) {
        this.numMtoComiTarjeta = numMtoComiTarjeta;
    }

    public Double getNumMtoTotalComiTarjeta() {
        return numMtoTotalComiTarjeta;
    }

    public void setNumMtoTotalComiTarjeta(Double numMtoTotalComiTarjeta) {
        this.numMtoTotalComiTarjeta = numMtoTotalComiTarjeta;
    }   

    public boolean isFlgMostrarNumVoucher() {
        return flgMostrarNumVoucher;
    }

    public void setFlgMostrarNumVoucher(boolean flgMostrarNumVoucher) {
        this.flgMostrarNumVoucher = flgMostrarNumVoucher;
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
            if(this.codClaseeven != null && this.codClaseeven.equals("VI_SICGASTOS"))
                this.itemsSpend = objMaeCataService.objCatalogoTipoGastos();
            
            System.out.println("CodSclaseEven:" + this.codSClaseeven);
            
            List<Sic1general> lstFormaPago = new ArrayList<>();
            
            /*Cargar Catalogo: COMPROBANTES DE PAGO*/
            /*Cargar Catalogo: FORMAS DE PAGO (Transferencia, Depósito, etc)*/
            if(this.codSClaseeven != null && this.codSClaseeven.equals(Constantes.COD_SCLASEEVEN_VENTA)){
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPagoVenta();
                lstFormaPago = objMaeCataService.obtFormasPago();
            }
            else if(this.codSClaseeven != null && this.codSClaseeven.equals(Constantes.COD_SCLASEEVEN_COMPRA)){
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPagoCompra();
                lstFormaPago = objMaeCataService.obtFormasPagoCompra();
            }
            else if(this.codSClaseeven != null && this.codSClaseeven.equals(Constantes.COD_SCLASEEVEN_NOTACREDITO)){
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPagoNotaCredito();
                lstFormaPago = objMaeCataService.obtFormasPagoNotaCredito();
            }
            else if(this.codSClaseeven != null && this.codSClaseeven.equals(Constantes.COD_SCLASEEVEN_ORDENCOMPRA)){
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPagoOrdenCompra();
            }
            else if(this.codClaseeven != null && this.codClaseeven.equals(Constantes.COD_CLASEEVEN_GASTOS)){
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPagoGasto();
                lstFormaPago = objMaeCataService.obtFormasPagoCompra();
            }
            else
                this.itemSTipoDocu = objMaeCataService.obtComprobantesPago();
            
            /*Dando formato para que aparezca el porcentaje a cobrar configurado para las tarjeta de debito y credito*/
            for(Sic1general obj : lstFormaPago){
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
        this.numSumItemsPrice       = new BigDecimal("0.00");
        this.numSumItemsAmount      = new BigDecimal("0");
        this.numSumItemsDescuento   = new BigDecimal("0.00");
        for(Sic3docuprod obj :  lstSic3docuprod){
            if (obj.getNumValor() != null){
                this.numSumItemsPrice       = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                this.numSumItemsAmount      = this.numSumItemsAmount.add(obj.getNumCantidad());
                this.numSumItemsDescuento   = this.numSumItemsAmount.add(obj.getNumMtodscto());
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
        if(this.indexTabla >= 0){
            
            int item = this.indexTabla + 1;
            Sic3docuprod obj  = this.lstSic3docuprod.get(indexTabla);
            this.lstSic3docuprod.remove(obj);
            
            Sic3docuprodId id = new Sic3docuprodId();
            id.setIdProd(this.sic1prod.getIdProd());
            id.setNumItem(item);
            
            this.sic3docuprod.setSic1prod(this.sic1prod);
            this.sic3docuprod.setId(id);
            
            this.sic3docuprod.setNumIndex(item);
            this.sic3docuprod.setFlgNuevo(true);
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
            this.sic3docuprod.setFlgNuevo(true);
            if(this.sic3docuprod.getNumMtodscto() == null)
                this.sic3docuprod.setNumMtodscto(new BigDecimal("0.00"));
            
            this.lstSic3docuprod.add(this.sic3docuprod);
        }
       
        /*Calcular Footer de la tabla*/
        this.numSumItemsPrice = new BigDecimal("0.00");
        this.numSumItemsAmount = new BigDecimal("0");        
        this.numSumItemsDescuento = new BigDecimal("0.00");
        for(Sic3docuprod obj :  lstSic3docuprod){
            if (obj.getNumValor()!=null){
                this.numSumItemsPrice = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                this.numSumItemsAmount = this.numSumItemsAmount.add(obj.getNumCantidad());
                this.numSumItemsDescuento.add(obj.getNumMtodscto());
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
    }
    
    public void selectAutocompleteProduct(Sic1prod obj) throws CustomizerException{
        
        try{
            
            if(this.flgIsSale) {
            
                this.sic1prod = obj;
                //falta hacer cambios para que cuando se elija vinil no haga la validacion, se comenta temporalmente.
//                if (obj.getNumCantstock()!= null && obj.getNumCantstock().intValue() > 0){
                    this.sic3docuprod.setNumValor(obj.getNumPrecio());
//                }else
//                    UtilClass.addErrorMessage("Producto sin Stock.");                
            }
            else{
                this.sic1prod = obj;
            }                        
            
            this.lstProducts.clear();
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }    
  
    /**/
    
    
    public void searchPerson() throws CustomizerException{
        
        
        try {
            
            this.sic1pers.setDesPers("");
            
            String strCodiden = this.sic1idenpersId.getCodIden();
            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
            Sic1idenpers sic1idenpers = personServiceImpl.getByCodiden(strCodiden);

            if (sic1idenpers != null) {

        
                sic1pers = sic1idenpers.getSic1pers();
                sic1pers.setIdTipoiden(sic1idenpers.getId().getIdTipoiden());
        
                
            }
        } catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    /*Metodo que se ejecuta despues que se registra a la persona desde el POPUP*/
//    public void onPersonChosen(SelectEvent event) {        
//        this.sic1idenpersSelected = (Sic1idenpers) event.getObject();
//    }
    
//    public void loadTotals(){
//        if (sic1docu.getNumMtoTotal() != null && sic1docu.getNumMtoTotal().intValue() > 0){
//            this.sic1docu.setNumSubtotal(new BigDecimal(sic1docu.getNumMtoTotal().doubleValue()/(1+Constantes.CONS_VALUE_IGV)).setScale(2, BigDecimal.ROUND_HALF_UP));
//            this.sic1docu.setNumIgv(new BigDecimal(this.sic1docu.getNumSubtotal().doubleValue()* Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));        
//            this.sic1docu.setNumMtoTotal(this.sic1docu.getNumSubtotal().add(sic1docu.getNumIgv()).setScale(2, BigDecimal.ROUND_HALF_UP));
//        }
//    }
    
    /*Calcula los totales para el registro de gastos*/
    public void calcularTotalesGastos(){
        
        double numTotalPrice = 0.00;
        
        if (this.sic1docu.getNumMtoTotal() != null)
            numTotalPrice = this.sic1docu.getNumMtoTotal().doubleValue();
            
        /**************************/
        /**SE CALCULA LOS TOTALES**/
        /**************************/
        double numSubtotal = numTotalPrice/(1+Constantes.CONS_VALUE_IGV);
        this.sic1docu.setNumSubtotal(new BigDecimal(numSubtotal).setScale(2, BigDecimal.ROUND_HALF_UP));
        this.sic1docu.setNumIgv(new BigDecimal(numSubtotal * Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));
        //this.sic1docu.setNumMtoTotal(new BigDecimal(numTotalPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
        //this.sic1docu.setNumMtoTotal(this.sic1docu.getNumSubtotal().add(sic1docu.getNumIgv()).setScale(2, BigDecimal.ROUND_HALF_UP));

        
        /*Calculando los campos de MTOEFECTIVO Y MTO TARJETA segun la forma de pago seleccionada*/
        if (numTotalPrice > 0){
            for(Sic1general obj : this.itemsPayMode){
                if (obj.getIdGeneral().compareTo(sic1docu.getIdModapago()) == 0){
                    if(obj.getCodValorgeneral().equalsIgnoreCase(Constantes.COD_MODOPAGO_EFECTIVO) ||
                            obj.getCodValorgeneral().equalsIgnoreCase(Constantes.COD_MODOPAGO_TRANSFER) || 
                                obj.getCodValorgeneral().equalsIgnoreCase(Constantes.COD_MODOPAGO_DEPOSITO)){
                        
                                    if((this.sic1docu.getNumMtoefectivo() != null && this.sic1docu.getNumMtoefectivo().compareTo(new BigDecimal(0))== 0)){
                                        this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                                        this.sic1docu.setNumMtoefectivo(new BigDecimal(this.sic1docu.getNumMtoTotal().doubleValue()));
                                        break;
                                    }
                    }
                    else if(obj.getCodValorgeneral().contains("VI_SICTARJ")){
                        if((this.sic1docu.getNumMtotarjeta()!= null && this.sic1docu.getNumMtotarjeta().compareTo(new BigDecimal(0))== 0)){
                            this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));    
                            this.sic1docu.setNumMtotarjeta(new BigDecimal(this.sic1docu.getNumMtoTotal().doubleValue()));
                            break;
                        }
                    }
                }
            }
        }        
        
        if (numTotalPrice > 0){
            /*Dando formato a 2 decimales*/
            if (this.sic1docu.getNumMtotarjeta() == null){
                this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
            }else{
                this.sic1docu.getNumMtotarjeta().setScale(2/*,BigDecimal.ROUND_HALF_UP*/);
            }
            if (this.sic1docu.getNumMtoefectivo() == null){
                this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2/*,BigDecimal.ROUND_HALF_UP)*/));
            }
            else
                this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);
            
        }else{
            this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
            this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));            
        }
    }
    
    /*Se invoca cuando solo se quiere visualizar el detalle de la operacion */
    public void calculateTotals(){
        this.sic1docu.setNumMtoTotal(this.sic1docu.getNumSubtotal().add(sic1docu.getNumIgv()).setScale(2, BigDecimal.ROUND_HALF_UP));        
    }
    
    /*Se invoca cuando se va crear o se esta llenando una nueva operacion*/
    public void recalculateTotals(boolean flgPayModeChange) throws CustomizerException{
        
        try{
            
            double numTotalPrice = 0;
            double numTotalPagado = 0;
            
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
                if(this.sic1docu.getIdModapago() != null && this.sic1docu.getIdModapago().equals(obj.getIdGeneral())){
                    if(obj.getNumValor() != null && obj.getNumValor().doubleValue() > 0){
                        numCargoTarjeta = obj.getNumValor().doubleValue()/100;
                        
                    }
                }
            }
            
            /*MODIFICADO 09/08/2018: SE MODIFICA PORQUE LA COMISION NO DEBE FORMAR PARTE DEL TOTAL DE LA VENTA NI DEL CALCULO DEL IGV.            
            if(this.sic1docu.getNumMtotarjeta() != null && this.sic1docu.getNumMtotarjeta().intValue() > 0)
                numTotalPrice = numTotalPrice + (this.sic1docu.getNumMtotarjeta().doubleValue() * numCargoTarjeta);
            else
                numTotalPrice = numTotalPrice + (numTotalPrice * numCargoTarjeta);*/
            
            
            
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
            if (/*this.flgIsSale &&*/ numTotalPrice > 0){
                for(Sic1general obj : this.itemsPayMode){
                    if (obj.getIdGeneral().compareTo(sic1docu.getIdModapago()) == 0){
                        
                        if(obj.getCodValorgeneral().equalsIgnoreCase("VI_SICEFECTIVO") || 
                                obj.getCodValorgeneral().equalsIgnoreCase("VI_SICTRANSFE") 
                                || obj.getCodValorgeneral().equalsIgnoreCase("VI_SICDEPOSITO")){
                            
                                if(flgPayModeChange || (this.sic1docu.getNumMtoefectivo() != null && this.sic1docu.getNumMtoefectivo().compareTo(new BigDecimal(0))== 0)){
                                    this.sic1docu.setNumMtoefectivo(new BigDecimal(this.sic1docu.getNumMtoTotal().doubleValue() - this.numTotalPagado).setScale(2, BigDecimal.ROUND_HALF_UP));
                                    break;
                                }
                        }
                        else if(obj.getCodValorgeneral().contains("VI_SICTARJ")){
                            if(flgPayModeChange || (this.sic1docu.getNumMtotarjeta()!= null && this.sic1docu.getNumMtotarjeta().compareTo(new BigDecimal(0))== 0)){
                                this.sic1docu.setNumMtotarjeta(new BigDecimal(this.sic1docu.getNumMtoTotal().doubleValue() - this.numTotalPagado).setScale(2,BigDecimal.ROUND_HALF_UP));
                                break;
                            }
                        }
                    }
                }
            }
            
            /********************/
            /***CALCULAR COMISION**/
            /********************/
            /*Calculando la comisión segun el monto que cliente quiera pagar con tarjeta */
            if(this.sic1docu.getNumMtotarjeta() != null && this.sic1docu.getNumMtotarjeta().intValue() > 0){
                this.numMtoComiTarjeta = new BigDecimal(this.sic1docu.getNumMtotarjeta().doubleValue() * numCargoTarjeta).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                this.numMtoTotalComiTarjeta = new BigDecimal(this.sic1docu.getNumMtotarjeta().doubleValue() + numMtoComiTarjeta).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            else{
                this.numMtoComiTarjeta = 0.00;
                this.numMtoTotalComiTarjeta = 0.00;
            }
                        
            
            /********************/
            /***CALCULAR VUELTO**/
            /********************/
            /*Solo si es efectivo*/
            //if (sic1docu.getIdModapago() != null && sic1docu.getIdModapago().intValue() == 46103 ){
                /*Si el importe total es igual a 0 no se realiza ningun calculo*/
                double numVuelto = 0.00;
                if (/*this.flgIsSale && */numTotalPrice > 0){
                    /*Dando formato a 2 decimales*/
                    if (this.sic1docu.getNumMtotarjeta() == null){
                        this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                    }else{
                        this.sic1docu.getNumMtotarjeta().setScale(2/*,BigDecimal.ROUND_HALF_UP*/);
                    }
                    if (this.sic1docu.getNumMtoefectivo() == null){
                        this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2/*,BigDecimal.ROUND_HALF_UP)*/));
                    }
                    else
                        this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);

                    /*calcular vuelto*/
                    if (this.sic1docu.getNumMtoefectivo().doubleValue() > 0 || this.sic1docu.getNumMtotarjeta().doubleValue()>0){
                        //numVuelto = (this.sic1docu.getNumMtotarjeta().add(this.sic1docu.getNumMtoefectivo()).doubleValue()) - this.sic1docu.getNumMtoTotal().doubleValue();
                        numVuelto = (this.sic1docu.getNumMtotarjeta().doubleValue() + this.sic1docu.getNumMtoefectivo().doubleValue() + this.numTotalPagado) - this.sic1docu.getNumMtoTotal().doubleValue();
                        this.sic1docu.setNumMtovuelto(new BigDecimal(numVuelto).setScale(2,BigDecimal.ROUND_HALF_UP));
                    }else
                        numVuelto = 0.00;
                        //this.sic1docu.setNumMtovuelto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP));
                    numVuelto =  0.00;
                }
//            }
//            else 
//                this.sic1docu.setNumMtovuelto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
               if (numVuelto > 0)
                   this.sic1docu.setNumMtovuelto(new BigDecimal(numVuelto).setScale(2,BigDecimal.ROUND_HALF_UP));
               else
                   this.sic1docu.setNumMtovuelto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP)); 
               

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
            
            UtilClass.addErrorMessage(ex.getMessage());
            //throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void payModeValueChange() throws CustomizerException {
        
        /*Cargar Catalogo ANIDADO: TIPO DE TARJETA*/
        this.flgMostrarNumVoucher = false;
        try {
            
            this.itemsTypeCard.clear();
            int idRelSec = -1;
            String codModoPagoSeleccionado = "";
            
            System.out.println("Modo Pago:" + sic1docu.getIdModapago());

            /*Cargar el id del catalogo secundario o anidado*/
            for(Sic1general obj : this.itemsPayMode){                
                
                /*Se obtiene el codigo del MODO DE PAGO seleccionado*/
                if(sic1docu.getIdModapago() != null && 
                        obj.getIdGeneral().compareTo(sic1docu.getIdModapago()) == 0){                
                    codModoPagoSeleccionado = obj.getCodValorgeneral();                    
                }
                
                if(sic1docu.getIdModapago() != null && 
                        obj.getIdGeneral().toString().equals(sic1docu.getIdModapago().toString()) && obj.getIdGeneralrelsec() != null ){
                    idRelSec = obj.getIdGeneralrelsec().intValue();
                    codModoPagoSeleccionado = obj.getCodValorgeneral();
                    break;
                }
            }
            
            /*Cuando no se selecciona TRANSFERENCIA O DEPÓSITO, se limpia el campo NUMVOUCHER*/
            if(!codModoPagoSeleccionado.equals(Constantes.COD_MODOPAGO_TRANSFER) && !codModoPagoSeleccionado.equals(Constantes.COD_MODOPAGO_DEPOSITO)){
                this.sic1docu.setNumVoucher(null);
            }else{
                this.flgMostrarNumVoucher = true;
            }
            
            System.out.println("idRelSec:" + idRelSec);
            /*Si tiene un catalogo anidado se procede a obtenerlo*/
            if (idRelSec > 0){
                
                /*Se obtiene los tipo de Tarjeta: VISA - MASTERCARD*/
                List<String> listCat = new ArrayList();
                listCat.add("VI_SICTIPOTARJ");
                this.objMaeCataService = new MaestroCatalogoServiceImpl();
                List<Sic1general> lstSic1general = objMaeCataService.listById_GeneralRel(new BigDecimal(idRelSec));
                
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
            
            /*Solo se ejecuta cuando se registra una nueva venta*/
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
                
                if(this.codClaseeven != null && this.codClaseeven.equals(Constantes.COD_CLASEEVEN_GASTOS))
                    this.calcularTotalesGastos();
                else
                    this.recalculateTotals(true);
            }
        
        } catch(CustomizerException ex) {
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
            System.out.println("ID_SCLASEEVEN: " + this.sic1docu.getIdSclaseeven());
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());
            System.out.println("MONTO TOTAL: " + this.sic1docu.getNumMtoTotal());
            
            System.out.println("COD_IDEN: " + this.sic1idenpersId.getCodIden());
            System.out.println("FECHA: " + this.desFecRegistro);
            System.out.println("IGV: " + this.sic1docu.getNumIgv());
            System.out.println("SUB TOTAL: " + this.sic1docu.getNumSubtotal());
            System.out.println("PRECION SIN IGV: " + this.flgPrecioSinIGV);            
           
            
            BigDecimal idPers = this.sic1pers.getIdPers();

            System.out.println("ID_PERS CLIENTE/PROVEEDOR: " + idPers);

            if ( idPers.intValue() <= 0  ){
                strMessage = "Falta ingresar el Cliente o Proveedor relacionado a la orden.";                    
                throw new ValidationException(strMessage);
            }

            if (this.sic1docu.getIdStipodocu().intValue() < 0 ){
                strMessage = "Debe seleccionar un TIPO DE COMPROBANTE.";                    
                throw new ValidationException(strMessage);
            }

            Sic1idendocu sic1idendoculocal = new Sic1idendocu();

            //this.sic1docu.setDesDocu("Compra Nro. " + strCodigo);
            this.sic1docu.setIdPers(SessionUtils.getUserId()); //Login
            this.sic1docu.setIdSucursal(SessionUtils.getIdSucursal()); //Sucursal
            this.sic1docu.setIdPersexterno(idPers);
            this.sic1docu.setSic1persexterno(this.sic1pers);
            this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));
            this.sic1docu.setFlgPrecsinIGV(this.flgPrecioSinIGV==true?1:0);
            this.sic1docu.setNumMtocomitarjeta(BigDecimal.ZERO);//Para gastos no aplica comision configurada
            
//            if(this.sic1docu.getCodTrolpersexterno() == null || this.sic1docu.getCodTrolpersexterno().isEmpty())
//                this.sic1docu.setCodTrolpersexterno(this.codTRolpersExterno);

            /*Se setea los datos del COMPROBANTE DE PAGO*/
            for(Sic1stipodocu s : this.itemSTipoDocu){
                if(this.sic1docu.getIdStipodocu().intValue() == s.getIdStipodocu().intValue())
                    this.sic1docu.setSic1stipodocu(s);
            }
            
            /*Obtener el tipo de gasto seleccionado*/
            for(Sic1sclaseeven s : this.itemsSpend){
                if(this.sic1docu.getIdSclaseeven().intValue() == s.getIdSclaseeven().intValue()){
                    Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                    objSclaseeven.setCodSclaseeven(s.getCodSclaseeven());
                    this.sic1docu.setSic1sclaseeven(objSclaseeven);
                    //this.sic1docu.setCodSclaseeven(s.getCodSclaseeven());
                }
            }
            
            /*Codigo del estado*/
            if (this.flgPorRecoger){
                this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPORRECOGER);
            }else
                this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAFINALIZADO);

            /*Guardar Orden*/
            System.out.println("Guardando Gasto");
            //orderServiceImpl = new DocuOrderServiceImpl();
            sic1idendoculocal.setSic1docu(sic1docu);
            strResult = orderServiceImpl.insert(sic1idendoculocal);
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


        } catch (ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());            
        }catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
        
        
        
    }
    
    /**
     * METODO PARA INSERTAR UNA ORDEN
     * @throws CustomizerException
     * @throws ParseException 
     */
    public void saveOrder() throws CustomizerException, ParseException{
        
        String strResult;
        String strMessage = null;
        this.msjValidation = "";
        
        try {
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());
            System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
            System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
            System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
            System.out.println("ID_SCLASEEVEN: " + this.sic1docu.getIdSclaseeven());
            
            System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());           
            System.out.println("ID_MODAPAGO: " + this.sic1docu.getIdModapago());
            System.out.println("ID_TIPOTARJETA: " + this.sic1docu.getIdTipotarjeta());
            System.out.println("NUM_MTOTARJETA: " + this.sic1docu.getNumMtotarjeta());
            System.out.println("NUM_MTOEFECTIVO: " + this.sic1docu.getNumMtoefectivo());
            System.out.println("MONTO DESCUENTO: " + this.sic1docu.getNumMtodscto());
            System.out.println("NOTAS: " + this.sic1docu.getDesNotas());
            
            
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
                
                if (this.sic1docu.getIdStipodocu().intValue() < 0 ){
                    strMessage = "Debe seleccionar un TIPO DE COMPROBANTE.";                    
                    throw new ValidationException(strMessage);
                }
                
                /*Se setea los datos del COMPROBANTE DE PAGO seleccionado*/
                for(Sic1stipodocu s : this.itemSTipoDocu){
                    if(this.sic1docu.getIdStipodocu().intValue() == s.getIdStipodocu().intValue())
                        this.sic1docu.setSic1stipodocu(s);
                }
                
                /*VALIDACION: Excepto si es una NOTA DE CREDITO, se valida que documentos con el mismo tipo de comprobante 
                              de pago no se puedan relacionar */
                if(!this.codSClaseeven.equals(Constantes.COD_SCLASEEVEN_NOTACREDITO)){
                    for(ViSicdocu objVi : this.lstViSicdocus){
                        if(objVi.getCodStipodocu().equals(this.sic1docu.getSic1stipodocu().getCodStipodocu())){
                            strMessage = "No se puede relacionar una " + this.sic1docu.getSic1stipodocu().getDesStipodocu() + 
                                         " con otra " + objVi.getDesStipodocu().toUpperCase() + ".";
                            throw new ValidationException(strMessage);
                        }
                    }
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
                    
                    /*VALIDACION: Cuando es una NOTA DE VENTA no es necesario que se pague el total de la venta*/
                    if(this.sic1docu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){
                        System.out.println("Falta mas regla de negocio");                         
                    }
                    else {                        
                       
                        /*Se valida si la operación se pago completamente */
                        //if(codModoPago.equals(Constantes.COD_MODOPAGO_EFECTIVO) || codModoPago.contains("VI_SICTARJ")){
                            
                            double mtoPagado = (sic1docu.getNumMtoefectivo().doubleValue() + 
                                                this.numTotalPagado +
                                                sic1docu.getNumMtotarjeta().doubleValue()) -
                                                sic1docu.getNumMtovuelto().doubleValue();

                            if(sic1docu.getNumMtoTotal().doubleValue() - mtoPagado != 0){
                                strMessage = "Los montos ingresados no cubren el importe total de la venta.";
                                throw new ValidationException(strMessage);
                            }                            
//                        }else{
//                            this.sic1docu.setNumMtoefectivo(null);
//                            this.sic1docu.setNumMtotarjeta(null);                            
//                        }

                        /*Se obtiene el codigo del MODO DE PAGO*/
                        String codModoPago = "";
                        for(Sic1general obj : this.itemsPayMode){
                            if(this.sic1docu.getIdModapago().compareTo(obj.getIdGeneral())==0){
                                codModoPago = obj.getCodValorgeneral();
                                break;
                            }
                        }
                        //Se limpia el numero del voucher de pago
                        if(!codModoPago.equals(Constantes.COD_MODOPAGO_TRANSFER) && !codModoPago.equals(Constantes.COD_MODOPAGO_DEPOSITO)){
                            this.sic1docu.setNumVoucher(null);
                        }
                    }                    
                    
                    /**/
                }

                /**************** Guardar Documento ************************/
                
                Sic1idendocu sic1idendocu = new Sic1idendocu();
                
                this.sic1docu.setIdPers(SessionUtils.getUserId()); //Login
                this.sic1docu.setIdSucursal(SessionUtils.getIdSucursal());
                this.sic1docu.setIdPersexterno(idPers);
                this.sic1docu.setSic1persexterno(this.sic1pers);
                this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));
                this.sic1docu.setFlgPrecsinIGV(this.flgPrecioSinIGV==true?1:0);
                
                Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
                objSclaseeven.setCodSclaseeven(this.codSClaseeven);
                this.sic1docu.setSic1sclaseeven(objSclaseeven);
                //this.sic1docu.setCodSclaseeven(this.codSClaseeven);
                
//                if(this.sic1docu.getCodTrolpersexterno() == null || this.sic1docu.getCodTrolpersexterno().isEmpty())
//                    this.sic1docu.setCodTrolpersexterno(this.codTRolpersExterno);
                
                if(this.numMtoComiTarjeta != null)
                    this.sic1docu.setNumMtocomitarjeta(new BigDecimal(this.numMtoComiTarjeta));//Calculo rapido de Comision en base a lo configurado en el sistema
                else
                    this.sic1docu.setNumMtocomitarjeta(new BigDecimal(0));
                
                this.sic1docu.setNumMtoPagadoEfectivo(this.numMtoPagadoEfectivo);
                this.sic1docu.setNumMtoPagadoTarjeta(this.numMtoPagadoTarjeta);
                this.sic1docu.setNumMtoPagadoComiTarjeta(this.numMtoPagadoComiTarjeta);
                
                /* SETEANDO DOCUMENTO REFERENCIADO*/                
                
                
                if(this.lstViSicdocus.size() > 0){

                    //this.sic1docu.setIdDocu(null);

                    Sic3docudocuId id = new Sic3docudocuId();
                    id.setIdDocurel(this.lstViSicdocus.get(0).getIdDocu());
                    Sic3docudocu sic3docudocu = new Sic3docudocu();
                    sic3docudocu.setId(id);
                    this.sic1docu.setSic3docudocu(sic3docudocu);

                } 
                
                /*VALIDACION*/
                if(this.flgPorRecoger && !this.sic1docu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){
                    strMessage = "Si es PENDIENTE DE RECOJO se debe seleccionar una NOTA DE VENTA.";                    
                    throw new ValidationException(strMessage);
                }

                /*Agregando lista de productos*/
                this.sic1docu.setLstSic3docuprod(this.lstSic3docuprod);
                
                /*Codigo del estado*/
                if (this.flgPorRecoger){
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPORRECOGER);
                }
                else if(this.sic1docu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){
                    //this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPENDIENTE);
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAPORRECOGER);
                }else if(this.sic1docu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAPEDIDO)){
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTACREADO);                        
                }else
                    this.sic1docu.setCodEstadocu(Constantes.CONS_COD_ESTAFINALIZADO);

                /*Guardar Orden*/
                System.out.println("Guardando Orden");
                //orderServiceImpl = new DocuOrderServiceImpl();
                sic1idendocu.setSic1docu(sic1docu);
                sic1idendocu.setCodIden(this.sic1idendocu.getCodIden());
                strResult = orderServiceImpl.insert(sic1idendocu);
                
                System.out.println("Documento:" + strResult);

                UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);

                this.idDocuImpresion = new BigDecimal(strResult);
                
                /*Limpiar Objetos*/                
                this.sic1docu           = new Sic1docu();
                this.sic1idenpersId     = new Sic1idenpersId();
                this.sic1pers           = new Sic1pers();                
                this.lstSic3docuprod.clear();
                this.lstViSicdocus      = new ArrayList<>();
                this.sic3docuprod       = new Sic3docuprod();
                this.sic1prod           = new Sic1prod();                    
                this.desFecRegistro     = UtilClass.getCurrentDay();
                this.flgPorRecoger      = false;
                this.flgPrecioSinIGV    = false;
                
                this.sic1docu.setNumSubtotal(new BigDecimal("0.00"));
                this.sic1docu.setNumIgv(new BigDecimal("0.00"));
                this.sic1docu.setNumMtoTotal(new BigDecimal("0.00"));
                this.sic1docu.setNumMtovuelto(new BigDecimal("0.00"));
                
                this.numSumItemsPrice = new BigDecimal("0.00");
                this.numSumItemsAmount = new BigDecimal("0");
                this.numSumItemsDescuento = new BigDecimal("0");
                
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
            this.orderServiceImpl.relateDocuEsta(this.sic1docu.getIdDocu(), Constantes.CONS_COD_ESTADOCU_COMPROBANTE, Constantes.CONS_COD_ESTAFINALIZADO, null);
            this.flgPorRecoger = false;        

            UtilClass.addInfoMessage("La orden se finalizó correctamente.");
        }catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    /* Metodo que se ejecuta cuando es invocado desde una pagina externa, se llama desde desde el tag <f:metadata> ubicado en la
     * página XHTML
     */
//    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
//
//        if(!FacesContext.getCurrentInstance().isPostback()){
//            
//            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//
//            String tituloPagina     = (String)flash.get("paramTituloPagina");
//            BigDecimal idDocu       = (BigDecimal)flash.get("paramIdDocu");  
//            String codSClaseevenTmp    = (String)flash.get("paramCodSClaseeven");
//            String codTRolpersTmp      = (String)flash.get("paramCodTRolpers");
//            
//
//            System.out.println("tituloPagina:" + tituloPagina); 
//            System.out.println("codTRolpers:" + codTRolpersTmp); 
//
//            if (tituloPagina != null)
//                this.desTituloPagina = tituloPagina;
//            
//            /*Esto permite que cuando se registra un nueva persona, se guarde con el rol de CLIENTE O PROVEEDOR*/
//            if (codTRolpersTmp != null)
//                this.codTRolpers = codTRolpersTmp;
//            else
//                throw new CustomizerException("No se cargo el Tipo de Rol de la persona.");
//            
//            //Si idDocu viene NULL quiere decir que se está tratando de registrar una nueva orden
//            //Si es asi, la subclase del evento no puede esta vacia.
//            if (idDocu == null) {
//                if (codSClaseevenTmp != null) {
//                    this.codSClaseeven = codSClaseevenTmp;                    
//                    //Se verifica si está realizando una compra, si es asi se debe ocultar los controles
//                    //(Forma de pago, Mto Tarjeta y Efectivo)
//                    if (codSClaseevenTmp.equalsIgnoreCase(Constantes.CONS_COD_SCLASEEVEN_VENTA)){
//                        this.setFlgIsSale(true);
//                    }
//                }
//                else
//                    throw new CustomizerException("No se cargo la sub clase del evento.");
//                
//            }
//
//
//            /*OBTENER LOS DATOS DE LA ORDEN*/
//            if (idDocu != null && idDocu.intValue() > 0 ){
//                
//                /*Verificar si es una venta*/
//                if (codSClaseevenTmp != null) {
//                    if (codSClaseevenTmp.equalsIgnoreCase(Constantes.CONS_COD_SCLASEEVEN_VENTA))
//                        this.setFlgIsSale(true);
//                }else
//                    throw new CustomizerException("No se cargo la sub clase del evento.");
//                
//                /*La FACTURA o BOLETA no se puede editar*/
//                this.editFields = false;
//
//                /*Se obtiene los datos de la orden*/
//                Sic1idendocu sic1idendocu = orderServiceImpl.getOrderByIdDocu(idDocu);
//
//                /*Se obtiene los datos del Cliente/Proveedor*/
//                PersonServiceImpl personServiceImpl = new PersonServiceImpl();
//                Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPersexterno());
//
//                /*Seteando en las variables para que se visualice los datos en la pantalla*/
//                this.sic1docu           = sic1idendocu.getSic1docu();
//                this.sic1pers           = sic1idenpers.getSic1pers();
//                this.sic1idenpersId     = sic1idenpers.getId();
//                this.lstSic3docuprod    = sic1idendocu.getSic1docu().getLstSic3docuprod();
//                
//                /*Recalculando el Nro. item de la tabla detalle de productos*/
//                for(int i = 0; i < this.lstSic3docuprod.size(); i++){
//                    this.lstSic3docuprod.get(i).setNumIndex(i+1);
//                }
//                
//                /*Formateando Montos*/
//                if (this.sic1docu.getNumMtodscto()!= null)
//                    this.sic1docu.getNumMtodscto().setScale(2,BigDecimal.ROUND_HALF_UP);
//                else
//                    this.sic1docu.setNumMtodscto(new BigDecimal("0.00"));
//                
//                if (this.sic1docu.getNumMtoefectivo()!= null)
//                    this.sic1docu.getNumMtoefectivo().setScale(2,BigDecimal.ROUND_HALF_UP);
//                else
//                    this.sic1docu.setNumMtoefectivo(new BigDecimal("0.00"));
//                
//                if (this.sic1docu.getNumMtotarjeta()!= null)
//                    this.sic1docu.getNumMtotarjeta().setScale(2,BigDecimal.ROUND_HALF_UP);
//                else
//                    this.sic1docu.setNumMtotarjeta(new BigDecimal("0.00"));
//                
//                /*Se obtiene si el precio incluye o no IGV*/
//                this.flgPrecioSinIGV = this.sic1docu.getFlgPrecsinIGV()==1?true:false;
//                
//                /*Marcado el check PENDIENTE POR RECOGER*/
//                if(this.sic1docu.getCodEstadocu().equalsIgnoreCase(Constantes.CONS_COD_ESTAPORRECOGER))
//                    this.flgPorRecoger = true;
//
//                this.recalculateTotals(false);
//                
//                /*Cargar el catalogo de Tipo de Tarjeta*/
//                this.payModeValueChange();
//            }        
//        }
//    }

    
    public void inicializarDatosRegistro( String codSClaseeven
                                         ,String codTRolpersexterno
                                         ,String desTituloPagina
                                         ,boolean flgIsSale ) throws CustomizerException{
        
        try{
            
            this.desTituloPagina = desTituloPagina;
            this.codSClaseeven = codSClaseeven;
            this.codTRolpersExterno = codTRolpersexterno;
            this.flgIsSale = flgIsSale;
            
            this.init();
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    public void inicializarDatosRegistroGasto(   String codClaseeven
                                                ,String codTRolpersexterno
                                                ,String desTituloPagina
                                                ,boolean flgIsSale ) throws CustomizerException{
        
        try{
            
            this.desTituloPagina = desTituloPagina;
            this.codClaseeven = codClaseeven;            
            this.flgIsSale = flgIsSale;
            this.codTRolpersExterno = codTRolpersexterno;
            
            this.init();
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    /**
     * METODO PARA DESCARGAR EL COMPROBANTE EN FORMATO PDF
     * @throws CustomizerException 
     */
    public void descargarComprobantePDF() throws CustomizerException{
        try{
            
            Impresion imp = new Impresion();
            imp.descargarComprobantePDFJasper(this.idDocuImpresion.intValue(), this.sic1docu.getNumDocuunido());
            
        }catch(Exception ex){
            UtilClass.addErrorMessage(Constantes.CONS_SUCCESS_ERROR + " : " + ex.getMessage());
        }
    }
    
    /**
     * METODO PARA IMPRIMIR EL COMPROBANTE DE PAGO
     * @throws CustomizerException 
     */
    public void imprimirVoucher() throws CustomizerException{
        try{
            
            Impresion imp = new Impresion();
            imp.imprimirVoucherVentaJasper(this.idDocuImpresion.intValue());
            
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            
        }catch(Exception ex){
            UtilClass.addErrorMessage(Constantes.CONS_SUCCESS_ERROR + " : " + ex.getMessage());
        }
    }
    
    /**
     * METODO QUE ES INVOCADO DESDE OTRA PAGINA, A TRAVES DE SU CONTROLADOR
     * @param idDocuPrinc Indica el identificador el documento principal
     * @param desTituloPagina Indica el titulo que se mostrará en la página.
     * @param codSClaseevenTmp Indica el tipo de operacion, si es COMPRA, VENTA, ORDEN DE COMPRA, ETC
     * @param idDocurel Indica el identificador el documento relacionado
     * @param lstProductosSeleccionados Indica la lista de productos seleccionados
     * @param flgNuevo 
     * @param flgEditarProductos Indica si se podrá agregar productos
     * @throws CustomizerException 
     */    
    public void loadOrderDetails( BigDecimal idDocuPrinc
                                 ,String desTituloPagina
                                 ,String codSClaseevenTmp
                                 ,BigDecimal idDocurel
                                 ,List<Sic3docuprod> lstProductosSeleccionados
                                 ,boolean flgNuevo
                                 ,boolean flgEditarProductos
                                 ,boolean flgEditarPersona
                                 ,boolean flgEditarFecha
                                 ,boolean flgEditarFormaPago
                                 ,boolean flgMostrarFormaPago 
                                 ,boolean flgEditarTipoDocumento /* Factura, Boleta, etc*/
                                 ,boolean flgEditarNroDocumento  /* Serie - Correlativo */
                                 /*,boolean flgMostrarMontosCalculadosFormaPago*/ ) throws CustomizerException, Exception {
                    
            
            System.out.println("idDocu:" + idDocuPrinc);
            System.out.println("desTituloPagina:" + desTituloPagina);
            System.out.println("codSClaseevenTmp:" + codSClaseevenTmp);
            System.out.println("idDocurel:" + idDocurel);
            System.out.println("lstProductosSeleccionados:" + lstProductosSeleccionados.size());
            System.out.println("flgNuevo:" + flgNuevo);
            System.out.println("flgEditarProductos:" + flgEditarProductos);
            System.out.println("flgEditarPersona:" + flgEditarPersona);
            System.out.println("flgEditarFecha:" + flgEditarFecha);
            System.out.println("flgEditarFormaPago:" + flgEditarFormaPago);
            System.out.println("flgMostrarFormaPago:" + flgMostrarFormaPago);

            this.desTituloPagina        = desTituloPagina;
            this.codSClaseeven          = codSClaseevenTmp;

            this.init();

            this.flgEditProducts        = flgEditarProductos;
            this.flgEditPerson          = flgEditarPersona;
            this.flgEditarFecha         = flgEditarFecha;
            this.flgEditarFormaPago     = flgEditarFormaPago;
            this.flgMostrarFormaPago    = flgMostrarFormaPago;
            this.flgEditarTipoDocumento = flgEditarTipoDocumento;
            this.flgEditarNroDocumento  = flgEditarNroDocumento;

            /*Se Verificar si es una venta*/
            if (codSClaseevenTmp != null) {
                if (codSClaseevenTmp.equalsIgnoreCase(Constantes.COD_SCLASEEVEN_VENTA))
                    this.flgIsSale = true;
            }else
                throw new CustomizerException("No se cargo la sub clase del evento.");                
            
            
            BigDecimal idDocuLocal  = new BigDecimal(0);
            
            if(idDocuPrinc.intValue() > 0)
                idDocuLocal = idDocuPrinc;
            else if(idDocurel.intValue() > 0)
                idDocuLocal = idDocurel;                
                      
            /*OBTENER LOS DATOS DE LA ORDEN*/
            if (idDocuLocal.intValue() > 0 ){
                
                this.idDocuImpresion = idDocuLocal;

                /*Se obtiene los datos de la orden*/
                Sic1idendocu sic1idendocuLocal = orderServiceImpl.getOrderByIdDocu(idDocuLocal);

                /*Se obtiene los datos del Cliente/Proveedor*/
                PersonServiceImpl personServiceImpl = new PersonServiceImpl();
                Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocuLocal.getSic1docu().getIdPersexterno());

                /*Seteando en las variables para que se visualice los datos en la pantalla*/
                this.sic1idendocu       = sic1idendocuLocal;
                this.sic1docu           = sic1idendocuLocal.getSic1docu();
                this.sic1idenpersId     = sic1idenpers.getId();
                this.sic1pers           = sic1idenpers.getSic1pers();
                this.sic1pers.setIdTipoiden(sic1idenpersId.getIdTipoiden());
                
                if(lstProductosSeleccionados.size() > 0)
                    this.lstSic3docuprod    = lstProductosSeleccionados;
                else
                    this.lstSic3docuprod    = sic1idendocuLocal.getSic1docu().getLstSic3docuprod();
                
                /*Si se está editanto una ORDEN DE COMPRA se setea valor null a los items para que se puedan editar*/
                if (flgEditarProductos && codSClaseevenTmp.equals(Constantes.COD_SCLASEEVEN_ORDENCOMPRA)){
                    for(int i=0; i<lstSic3docuprod.size(); i++){
                        lstSic3docuprod.get(i).getId().setIdDocu(null);
                    }
                }
                
                /*Recalculando el Nro. item de la tabla detalle de productos*/
                for(int i = 0; i < this.lstSic3docuprod.size(); i++){
                    this.lstSic3docuprod.get(i).setNumIndex(i+1);
                }
                
                 /*Calcular Footer de la tabla*/
                this.numSumItemsPrice = new BigDecimal("0.00");
                this.numSumItemsAmount = new BigDecimal("0");
                this.numSumItemsDescuento = new BigDecimal("0.00");
                for(Sic3docuprod obj :  lstSic3docuprod){
                    if (obj.getNumValor()!=null){
                        this.numSumItemsPrice       = this.numSumItemsPrice.add(obj.getNumValor().multiply(obj.getNumCantidad()));
                        this.numSumItemsAmount      = this.numSumItemsAmount.add(obj.getNumCantidad());
                        this.numSumItemsDescuento   = this.numSumItemsDescuento.add(obj.getNumMtodscto());
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

                if(flgNuevo)
                    this.recalculateTotals(false);
                else
                    this.calculateTotals();//Cuando es solo para visualizar se hace un calculo simple de los totales
                
                /*Cargar el catalogo de Tipo de Tarjeta*/
                this.payModeValueChange();
                
            }
                
            /*Obteniendo la lista de los documentos relacionados al documento principal*/
            if(idDocurel != null && idDocurel.intValue() > 0){

                ViSicdocu viSicdocutmp = new ViSicdocu();
                viSicdocutmp.setIdDocu(idDocurel);
                this.lstViSicdocus = orderServiceImpl.listViSicdocu(viSicdocutmp);

                //Double numMtoTotalVenta = 0.00;
                /*Se obtiene los montos pagados por el cliente*/
                if(!codSClaseevenTmp.equals(Constantes.COD_SCLASEEVEN_NOTACREDITO)){
                    for(ViSicdocu objVi : this.lstViSicdocus){
                        
                        this.numMtoPagadoTarjeta        += objVi.getNumMtopagadotarjeta();
                        this.numMtoPagadoEfectivo       += objVi.getNumMtopagadoefectivo();
                        this.numTotalPagado             += objVi.getNumTotalpagado();
                        this.numMtoPagadoComiTarjeta    += objVi.getNumMtopagadocomitarjeta();
                        //numMtoTotalVenta +=  objVi.getNumMtototal();
                    }
                }

                //boolean flgOperacionPagada = false;
                /*Validar si la operación ya ha sido PAGADA TOTALMENTE*/
                /*if( (numMtoTotalVenta - this.numTotalPagado) == 0 )
                    flgOperacionPagada = true;*/

                /*Si es un nuevo documento se inicializan algunas variables*/
                if(flgNuevo){
                    
                    /*Cuando es una NOTA DE CREDITO se mantiene el tipo de documento: FACTURA, BOLETA.*/
                    if(!codSClaseevenTmp.equals(Constantes.COD_SCLASEEVEN_NOTACREDITO)){
                        this.sic1docu.setIdStipodocu(new BigDecimal(-1));
                    }
                    
                    /*Despues de haber cargado los datos del documento, el campo ID_DOCU se pone en NULL para especificar que se va crear un nuevo 
                    documento el cual tendrá como referencia al anterior*/                   
                    this.sic1docu.setIdDocu(null);
                    this.sic1docu.setCodSerie(null);
                    this.sic1docu.setNumDocu(null);
                    this.sic1docu.setFecCreacion(null);
                    this.desFecRegistro  = UtilClass.getCurrentDay();
                    this.setNumMtoComiTarjeta(0.00);
                    this.setNumMtoTotalComiTarjeta(0.00);

                    /*Se limpian los datos*/
                    if(flgMostrarFormaPago && flgEditarFormaPago){

                        this.sic1docu.setNumMtotarjeta(null);
                        this.sic1docu.setNumMtoefectivo(null);
                        this.sic1docu.setNumMtovuelto(null);
                        //this.sic1docu.setNumMtodscto(null);
                        this.sic1docu.setNumMtocomi(null);
                        this.sic1docu.setIdModapago(new BigDecimal(-1));
                        this.sic1docu.setNumVoucher(null);
                        this.sic1docu.setIdTipotarjeta(null);
                        this.flgPorRecoger = false;

                        /*Limpiar el catalogo de tipo de tarjea*/
                        itemsTypeCard  = new ArrayList();
                    }
                    
                }
            }               
    }
}
