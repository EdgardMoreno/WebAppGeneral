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
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.a2.service.impl.ProductServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3docuprodId;
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
    
    private String desFecRegistro;
    private Integer indexTabla;
    private String desTituloPagina;
    
    private List<Sic1prod> lstProducts;
    
    private String msjValidation;
    
    public OrderController(){
        String day = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day");
        System.out.println("day+ " + day);
    }
    
    @PostConstruct
    public void init() {
        
        String day = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day");
        System.out.println("day+ " + day);
        
        try{
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
            
            
            /*Data*/
            lstSic3docuprod  = new ArrayList();
            
            //eliminar
//            Sic1prod sic1prod = new Sic1prod();
//            sic1prod.setCodProd("asdasd");
//            sic1prod.setDesProd("fsfsdf");
//            sic1prod.setNumPrecio(new BigDecimal(30.21).setScale(2, BigDecimal.ROUND_HALF_UP));
//            
//            Sic3proddocu sic3proddocu = new Sic3proddocu();
//            sic3proddocu.setSic1prod(sic1prod);
//            sic3proddocu.setNumValor(sic1prod.getNumPrecio());
//            sic3proddocu.setNumCantidad(new BigDecimal(20));
//            
//            this.lstSic3proddocu.add(sic3proddocu);
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
    
    
    public String deleteAction(Sic3proddocu obj) throws CustomizerException {
      
        lstSic3docuprod.remove(obj);
        /*Recalculando el numero de la fila*/
        for(int i=0 ; i < lstSic3docuprod.size() ; i++){
            lstSic3docuprod.get(i).setNumIndex(i+1);
        }
        
        this.recalculateTotals();
        
        return null;
    }
    
    public String editAction(Sic3docuprod obj) {
      
        this.sic1prod = obj.getSic1prod();
        this.sic3docuprod = obj;
        this.indexTabla = lstSic3docuprod.indexOf(obj);
        return null;
    }
   
    public void addItem() throws CustomizerException{
        
        System.out.println("Agregar Item");
        /*ID*/
        Sic3docuprodId id = new Sic3docuprodId();
        id.setIdProd(this.sic1prod.getIdProd());
        
        this.sic3docuprod.setSic1prod(this.sic1prod);
        this.sic3docuprod.setId(id);
        
        if(this.indexTabla>=0){
            Sic3docuprod obj = this.lstSic3docuprod.get(this.indexTabla);
            this.lstSic3docuprod.remove(obj);
            this.sic3docuprod.setNumIndex(this.indexTabla + 1);
            this.lstSic3docuprod.add(this.indexTabla, sic3docuprod);
            
        }
        else{
            this.sic3docuprod.setNumIndex(this.lstSic3docuprod.size()+1);
            this.lstSic3docuprod.add(sic3docuprod);
        }       
       
        this.recalculateTotals();
        
        /*Limipiar objetos*/
        this.sic3docuprod = new Sic3docuprod();
        this.sic1prod     = new Sic1prod();
        this.indexTabla   = -1;
        
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
        if (strCodProd != null && strCodProd.trim().length() >= 3 ){
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
    
    public void recalculateTotals() throws CustomizerException{
        
        try{
            
            double numTotalPrice = 0;
            BigDecimal numDescuento = this.sic1docu.getNumMtodscto();

            for(Sic3docuprod obj : this.lstSic3docuprod){
                numTotalPrice += obj.getNumValor().doubleValue() * obj.getNumCantidad().doubleValue();
            }

            /*En caso haya descuento se resta del importe total*/
            if (numDescuento!= null && numDescuento.doubleValue() < numTotalPrice)
                numTotalPrice = numTotalPrice - numDescuento.doubleValue();
            else
                this.sic1docu.setNumMtodscto(BigDecimal.ZERO);//Se setea a 0 cuando el monto a descontar es mayor al importe total.

            this.sic1docu.setNumMtoTotal(new BigDecimal(numTotalPrice).setScale(2, BigDecimal.ROUND_HALF_UP));        
            this.sic1docu.setNumSubtotal(new BigDecimal(numTotalPrice / (1 + Constantes.CONS_VALUE_IGV)).setScale(2, BigDecimal.ROUND_HALF_UP));        
            this.sic1docu.setNumIgv(new BigDecimal(this.sic1docu.getNumSubtotal().doubleValue() * Constantes.CONS_VALUE_IGV).setScale(2, BigDecimal.ROUND_HALF_UP));        
            
        }catch(Exception e){
            throw new CustomizerException(e.getMessage());
        }
    }
    
    public void payModeValueChange() throws Exception {
        
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
        String strMessage = null;
        this.msjValidation = "";
        try {
            
            System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
            System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
            System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
            
            if (false){
                this.msjValidation = "<UL type = 'square'><LI>" + strMessage + "</LI></UL>";
                System.out.println("ERRROR: " + this.msjValidation);
                 UtilClass.addErrorMessage("holaaa");
            }
            else {
            
                BigDecimal idPers = this.sic1pers.getIdPers();
                int numItems      = this.lstSic3docuprod.size();

                if ( idPers.intValue() <= 0  ){
                    strMessage = "Falta ingresar el Cliente o Proveedor relacionado a la orden.";                    
                    throw new ValidationException(strMessage);
                }
                if (numItems == 0  ){
                    strMessage = "Falta ingresar productos a la orden.";                    
                    throw new ValidationException(strMessage);
                }

                System.out.println("ID_DOCU: " + this.sic1docu.getIdDocu());
                System.out.println("COD_SERIE: " + this.sic1docu.getCodSerie());
                System.out.println("NUM_DOCU: " + this.sic1docu.getNumDocu());
                System.out.println("ID_STIPODOCU: " + this.sic1docu.getIdStipodocu());
                System.out.println("ID_MODAPAGO: " + this.sic1docu.getIdModapago());
                System.out.println("ID_TIPOTARJETA: " + this.sic1docu.getIdTipotarjeta());
                System.out.println("ID_PERS: " + idPers);
                System.out.println("COD_IDEN: " + this.sic1idenpersId.getCodIden());
                System.out.println("FECHA: " + this.desFecRegistro);

                System.out.println("MONTO DESCUENTO: " + this.sic1docu.getNumMtodscto());
                System.out.println("IGV: " + this.sic1docu.getNumIgv());
                System.out.println("SUB TOTAL: " + this.sic1docu.getNumSubtotal());


                if (strMessage == null) {

                    /**************** Guardar Documento ************************/
                    //Codiden
                    String strCodigo = this.sic1docu.getCodSerie() + "-" + this.sic1docu.getNumDocu();
                    Sic1idendocu sic1idendocu = new Sic1idendocu();
                    sic1idendocu.setCodIden(strCodigo);
                    //this.sic1docu.setSic1idendocu(sic1idendocu);

                    this.sic1docu.setDesDocu("Compra Nro. " + strCodigo);
                    this.sic1docu.setIdPers(idPers);
                    this.sic1docu.setFecDesde(UtilClass.convertStringToDate(this.desFecRegistro));

                    /*Agregando lista de productos*/
                    this.sic1docu.setLstSic3docuprod(lstSic3docuprod);

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

                }
            }

        } catch (ValidationException ex){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch (CustomizerException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{

        if(!FacesContext.getCurrentInstance().isPostback()){
        
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

            String tituloPagina = (String)flash.get("paramTituloPagina");
            BigDecimal idDocu   = (BigDecimal)flash.get("paramIdDocu");            
            

            System.out.println("tituloPagina:" + tituloPagina); 

            if (tituloPagina != null)
                this.desTituloPagina = tituloPagina;


            /*OBTENER LOS DATOS DE LA ORDEN*/
            if (idDocu != null && idDocu.intValue() > 0 ){

                /*Se obtiene los datos de la orden*/
                Sic1idendocu sic1idendocu = orderServiceImpl.getOrderById(idDocu);

                /*Se obtiene los datos del Cliente/Proveedor*/
                PersonServiceImpl personServiceImpl = new PersonServiceImpl();
                Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPers());

                /*Seteando en las variables para que se visualice los datos en la pantalla*/
                this.sic1docu           = sic1idendocu.getSic1docu();
                this.sic1pers           = sic1idenpers.getSic1pers();
                this.sic1idenpersId     = sic1idenpers.getId();
                this.lstSic3docuprod    = sic1idendocu.getSic1docu().getLstSic3docuprod();
                
                /*Recalculando el Nro. item de la tabla detalle de productos*/
                for(int i = 0; i < this.lstSic3docuprod.size(); i++){
                    this.lstSic3docuprod.get(i).setNumIndex(i+1);
                }

                this.recalculateTotals();
            }
                        
        }        
    }    
}
