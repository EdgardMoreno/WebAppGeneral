/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuOrderServiceImpl;
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import com.general.a2.service.impl.PersonServiceImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.SendEmail;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Edgard
 */
@Named
@ManagedBean
@SessionScoped
public class DocumentController implements Serializable{
        
    private static final int FILA_INI_EXCEL = 6;
    private final static Logger log = LoggerFactory.getLogger(DocumentController.class);    
    private DocuOrderServiceImpl documentServiceImpl;
    private List<Sic1stipodocu> itemSTipoDocu  = new ArrayList();
    private List<ViSicestageneral> itemsEstaRol  = new ArrayList();
    private ViSicdocu viSicdocu; 
    private List<ViSicdocu> lstViSicdocus;
    private List<ViSicdocu> lstViSicdocusTmp;
    private List<Sic3docuprod> lstProducts;
    private String desFecDesde;
    private String desFecHasta;
    
    private String desTituloPagina;
    private String codTRolpers;
    private String codSClaseeven;
    private String desMotivoAnulacion;
    private String codTRolpersExterno;
    
    private Sic1docu sic1docu;
    private Sic1pers sic1pers;
    private Sic1idenpersId sic1idenpersId;
    
    private BigDecimal idDocuSelected;
    private int paramPageIdDocu = 0;
    
    private String numDocu;
   
    
    public DocumentController(){
        
        try{
            
            if(!FacesContext.getCurrentInstance().isPostback()){
                System.out.println("Aqui");
            }
            
            this.documentServiceImpl = new DocuOrderServiceImpl();
            this.viSicdocu           = new ViSicdocu();
            this.lstViSicdocus       = new ArrayList();
            this.lstViSicdocusTmp    = new ArrayList<>();
            this.lstProducts         = new ArrayList<>(); 

            this.desFecDesde         = UtilClass.getCurrentDay();
            /*desFecHasta         = UtilClass.getCurrentDay();*/

            /*Cargar Catalogo: COMPROBANTES DE PAGO*/
            MaestroCatalogoServiceImpl objMaeCata = new MaestroCatalogoServiceImpl();
            this.itemSTipoDocu = objMaeCata.obtComprobantesPago();

            /*Cargar Catalogo: STIPODOCU*/
            this.itemsEstaRol = objMaeCata.getCataEstaRolDocuInf();
            
            System.out.println("desti" + desTituloPagina);
            System.out.println("codTrolpers" + codTRolpers);
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }
    }    
    /***************************************************/
    /************ *PROPIEDADES *************************/
    /***************************************************/

    public Sic1docu getSic1docu() {
        return sic1docu;
    }

    public void setSic1docu(Sic1docu sic1docu) {
        this.sic1docu = sic1docu;
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
        
    public List<Sic1stipodocu> getItemSTipoDocu() {
        return itemSTipoDocu;
    }

    public void setItemSTipoDocu(List<Sic1stipodocu> itemSTipoDocu) {
        this.itemSTipoDocu = itemSTipoDocu;
    }

    public ViSicdocu getViSicdocu() {
        return viSicdocu;
    }

    public void setViSicdocu(ViSicdocu viSicdocu) {
        this.viSicdocu = viSicdocu;
    }

    public List<ViSicdocu> getLstViSicdocus() {
        return lstViSicdocus;
    }

    public void setLstViSicdocus(List<ViSicdocu> lstViSicdocus) {
        this.lstViSicdocus = lstViSicdocus;
    }

    public List<ViSicdocu> getLstViSicdocusTmp() {
        return lstViSicdocusTmp;
    }

    public void setLstViSicdocusTmp(List<ViSicdocu> lstViSicdocusTmp) {
        this.lstViSicdocusTmp = lstViSicdocusTmp;
    }    
    
    public String getDesFecDesde() {
        return desFecDesde;
    }

    public void setDesFecDesde(String desFecDesde) {
        this.desFecDesde = desFecDesde;
    }

    public String getDesFecHasta() {
        return desFecHasta;
    }

    public void setDesFecHasta(String desFecHasta) {
        this.desFecHasta = desFecHasta;
    }

    public List<ViSicestageneral> getItemsEstaRol() {
        return itemsEstaRol;
    }

    public void setItemsEstaRol(List<ViSicestageneral> itemsEstaRol) {
        this.itemsEstaRol = itemsEstaRol;
    }

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public DocuOrderServiceImpl getDocumentServiceImpl() {
        return documentServiceImpl;
    }

    public void setDocumentServiceImpl(DocuOrderServiceImpl documentServiceImpl) {
        this.documentServiceImpl = documentServiceImpl;
    }

    public String getCodTRolpers() {
        return codTRolpers;
    }

    public void setCodTRolpers(String codTRolpers) {
        this.codTRolpers = codTRolpers;
    }

    public BigDecimal getIdDocuSelected() {
        return idDocuSelected;
    }

    public void setIdDocuSelected(BigDecimal idDocuSelected) {
        this.idDocuSelected = idDocuSelected;
    }

    public String getCodSClaseeven() {
        return codSClaseeven;
    }

    public void setCodSClaseeven(String codSClaseeven) {
        this.codSClaseeven = codSClaseeven;
    }

    public String getDesMotivoAnulacion() {
        return desMotivoAnulacion;
    }

    public void setDesMotivoAnulacion(String desMotivoAnulacion) {
        this.desMotivoAnulacion = desMotivoAnulacion;
    }    

    public String getNumDocu() {
        return numDocu;
    }

    public void setNumDocu(String numDocu) {
        this.numDocu = numDocu;
    }
    
    public int getParamPageIdDocu() {
        return paramPageIdDocu;
    }

    public void setParamPageIdDocu(int paramPageIdDocu) {
        this.paramPageIdDocu = paramPageIdDocu;
    }

    

    public List<Sic3docuprod> getLstProducts() {
        return lstProducts;
    }

    public void setLstProducts(List<Sic3docuprod> lstProducts) {
        this.lstProducts = lstProducts;
    }   

    public String getCodTRolpersExterno() {
        return codTRolpersExterno;
    }

    public void setCodTRolpersExterno(String codTRolpersExterno) {
        this.codTRolpersExterno = codTRolpersExterno;
    }    
    
    
    /*****************************************************************************************/
    /*** METODOS ****/
    /*****************************************************************************************/
    
    public void buscarOrdenes() throws CustomizerException{
     
        try {
            
            /*Validar*/
             if(!this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_ORDENCOMPRA) && (this.numDocu == null || this.numDocu.isEmpty()))
                 throw new ValidationException("Debe ingresar un número de documento.");            
            
            this.viSicdocu = new ViSicdocu();
            
            if(this.numDocu != null && this.numDocu.trim().length() > 0)
                this.viSicdocu.setNumDocuunido(this.numDocu);
            
            if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_NOTACREDITO)){
                this.viSicdocu.setCodSclaseeven(Constantes.CONS_COD_SCLASEEVEN_VENTA);
                this.viSicdocu.setCodStipodocu(Constantes.CONS_COD_STIPODOCU_FACTURA);
                this.viSicdocu.setCodEsta(Constantes.CONS_COD_ESTAFINALIZADO);
            }
            else if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_ORDENCOMPRA)){
                this.viSicdocu.setCodSclaseeven(this.codSClaseeven);
                this.viSicdocu.setCodEsta(Constantes.CONS_COD_ESTANOTIFICADO);
            }                        
            
            this.documentServiceImpl = new DocuOrderServiceImpl();
            this.lstViSicdocus = documentServiceImpl.listViSicdocu(this.viSicdocu);            

            if(lstViSicdocus.isEmpty())
                UtilClass.addWarnMessage(Constantes.CONS_WARN_SIN_RESULTADO);
            else{
                this.lstViSicdocusTmp.clear();            
                this.lstProducts.clear();
            }            
            
        } catch (ValidationException e) {
            UtilClass.addWarnMessage(e.getMessage());
        } catch (CustomizerException e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    /********************************************************************/
    /***METODOS**********************************************************/
    public void filterDocuments() throws CustomizerException{        
        
        try {

            if(this.desFecDesde != null && this.desFecDesde.trim().length() > 0)
                viSicdocu.setFecDesde(UtilClass.convertStringToDate(desFecDesde));
            else
                 viSicdocu.setFecDesde(null);
            if(this.desFecHasta != null && this.desFecHasta.trim().length() > 0)
                viSicdocu.setFecHasta(UtilClass.convertStringToDate(desFecHasta));
            else
                viSicdocu.setFecHasta(null);
            
            documentServiceImpl = new DocuOrderServiceImpl();
            this.lstViSicdocus = documentServiceImpl.listViSicdocu(viSicdocu);  
            
            if(this.lstViSicdocus.isEmpty())
                UtilClass.addWarnMessage(Constantes.CONS_WARN_SIN_RESULTADO);
            
        } catch (CustomizerException | ParseException e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    public String deleteAction(ViSicdocu obj) {
              
        //lstSic1prod.remove(obj);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Fila eliminada"));        
        return "";
    }
    
    public void clearSearch(){
        //viSicdocu = new ViSicdocu();
        lstViSicdocus  = new ArrayList();
    }
    
    /**
     * PERMITE VER EL DETALLE DE UNA ORDEN
     * @param viSicdocu
     * @return
     * @throws CustomizerException 
     */
    public String verDetalle(ViSicdocu viSicdocu ) throws CustomizerException, Exception{       
        
        String desTitulo = "DETALLE DE " + viSicdocu.getDesSclaseeven() + ": " + viSicdocu.getDesStipodocu() + " " + viSicdocu.getCodSerie() + "-" + viSicdocu.getNumDocu();
        
        FacesContext context = FacesContext.getCurrentInstance();
        /*Se limpia el objeto "orderController" que se tenga en el FACESCONTEXT*/
        context.getExternalContext().getSessionMap().put("orderController", null);
        /*Se instancia y crear el objecto "orderController" */
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        objController.loadOrderDetails(viSicdocu.getIdDocu()
                                       ,desTitulo
                                       ,viSicdocu.getCodSclaseeven()
                                       ,viSicdocu.getIdDocurel()
                                       ,new ArrayList<>()
                                       ,false
                                       ,false);
        
        return "ordenDetalle?faces-redirect=true";
    }
    
    /**
     * PERMITE EDITAR UNA ORDEN
     * @param viSicdocu
     * @return
     * @throws CustomizerException 
     */
    public String editarOrden(ViSicdocu viSicdocu ) throws CustomizerException, Exception{       
        
        String desTitulo = "EDITAR " + viSicdocu.getDesStipodocu() + " " + viSicdocu.getCodSerie() + "-" + viSicdocu.getNumDocu();        
        
        FacesContext context = FacesContext.getCurrentInstance();        
        context.getExternalContext().getSessionMap().put("orderController", null);        
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        objController.loadOrderDetails(viSicdocu.getIdDocu()
                                       ,desTitulo
                                       ,viSicdocu.getCodSclaseeven()
                                       ,new BigDecimal(0)
                                       ,new ArrayList<>()
                                       ,false /*flgNuevo*/
                                       ,true);/*flgEditarProductos*/
        
        return "ordenRegistrar?faces-redirect=true";
    }
    
    
    /**
     * PERMITE NOTIFICAR EL DOCUMENTO SELECCIONADO POR CORREO
     */
    public void notificarPorCorreo() throws Exception{        
        
        try{
            
            
            BigDecimal idDocu = this.idDocuSelected;
            
            DocuOrderServiceImpl objService = new DocuOrderServiceImpl();
            Sic1idendocu sic1idendocu = objService.getOrderByIdDocu(idDocu);
            String codSclaseeventmp = sic1idendocu.getSic1docu().getSic1sclaseeven().getCodSclaseeven();
            
            //CAMBIANDO DE ESTADO A NOTIFICADO PARA UNA ORDEN DE COMPRA
            if(codSclaseeventmp.equals(Constantes.CONS_COD_SCLASEEVEN_ORDENCOMPRA)
                && sic1idendocu.getSic1docu().getCodEstadocu().equals(Constantes.CONS_COD_ESTACREADO)){                
                
                try {
                    
                    objService.relateDocuEsta(   sic1idendocu.getSic1docu().getIdDocu()
                                                ,Constantes.CONS_COD_ESTADOCU_COMPROBANTE
                                                ,Constantes.CONS_COD_ESTANOTIFICADO
                                                ,null );
                    
                
                }catch(Exception ex){
                    throw new CustomizerException(ex.getMessage());
                }
                
                this.filterDocuments();
            }
            
            
            
            /*SE OBTIENE DATOS PARA ENVIAR CORREO*/
            /*Se obtiene los datos del Cliente/Proveedor*/
            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
            Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPersexterno());

            /*Seteando en las variables para que se visualice los datos en la pantalla*/
            Sic1docu objDocu                    = sic1idendocu.getSic1docu();
            Sic1pers objPers                    = sic1idenpers.getSic1pers();
            Sic1idenpersId objPersId            = sic1idenpers.getId();
             List<Sic3docuprod> lstSic3docuprod = sic1idendocu.getSic1docu().getLstSic3docuprod();

            /*Recalculando el Nro. item de la tabla detalle de productos*/
            for(int i = 0; i < lstSic3docuprod.size(); i++){
                lstSic3docuprod.get(i).setNumIndex(i+1);
            }
            
            /*Enviar por correo*/
            String numCompPago = "";
            if(objDocu.getCodSerie() != null)
                numCompPago = objDocu.getCodSerie() + "-" + objDocu.getNumDocu();
            else
                numCompPago = objDocu.getNumDocu().toString();
            
            String destinatario =  Constantes.CONS_DES_EMAIL_ADMINISTRADOR;
            String asunto = Constantes.CONS_DES_AMBIENTESISTEMA + " NOTIFICACION DE " + objDocu.getSic1sclaseeven().getDesSclaseeven() + " (" + numCompPago + ")";
            
            String numeroComp = "";
            if(objDocu.getCodSerie() != null)
                numeroComp = objDocu.getCodSerie() + " - " + objDocu.getNumDocu();
            else
                numeroComp = objDocu.getNumDocu().toString();
            
            String style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 2px'";
            
            String cuerpo = "<table>"
                            + "<tr>"
                                + "<td " + style + ">Documento:</td>"
                                + "<td>" + objDocu.getSic1sclaseeven().getDesSclaseeven() + "</td>" 
                            + "</tr>"
                            + "<tr>"
                                + "<td " + style + ">Tipo Documento:</td>"
                                + "<td>" + objDocu.getSic1stipodocu().getDesStipodocu() + "</td>" 
                            + "</tr>"
                            + "<tr>"
                                + "<td " + style + ">Número:</td>"
                                + "<td>" + numeroComp + "</td>" 
                            + "</tr>"
                            + "<tr>"
                                + "<td " + style + ">Prov./Cliente:</td>"
                                + "<td>" + objPersId.getCodIden() + " - " + objPers.getDesPers() + "</td>"
                            + "</tr>"
                            + "<tr>"
                                + "<td " + style + ">Estado:</td>"
                                + "<td>" + objDocu.getSic3docuesta().getDesEsta() + "</td>"
                            + "</tr>"
                            + "<tr>"
                                + "<td " + style + ">Imp. Total:</td>"
                                + "<td>" + objDocu.getNumMtoTotal() + "</td>"
                            + "</tr>"
                            + "<tr>"
                                + "<td colspan=2 " + style + ">Productos " + (objDocu.getFlgPrecsinIGV()==1?"NO":"SI") + " Incluye IGV:</td>"                                
                            + "</tr>";
            cuerpo += "</table><br/>";

            /*Se imprime el detalle de la orden*/            
            if(!lstSic3docuprod.isEmpty()){

                cuerpo += "<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\" style=\"border-collapse:collapse;\">"
                        + "<tr style='background-color: " + Constantes.CONS_COD_COLORTABLA + "'>"
                        +       "<th>Item</th>"
                        +       "<th>Código</th>"
                        +       "<th>Descripción</th>"
                        +       "<th>Precio</th>"
                        +       "<th>Descuento</th>"
                        +       "<th>Cantidad</th>"
                        +       "<th>Total</th>"
                        + "</tr>";

                double numImporteTotal = 0.00;
                int numCantidadTotal = 0;

                for(Sic3docuprod objItem : lstSic3docuprod){

                    cuerpo += "<tr>"
                                + "<td>" + objItem.getNumIndex() + "</td>"
                                + "<td>" + objItem.getSic1prod().getCodProd() + "</td>"
                                + "<td>" + objItem.getSic1prod().getDesProd() + "</td>"
                                + "<td style='text-align: center'>" + objItem.getNumValor()    + "</td>"                            
                                + "<td style='text-align: center'>" + objItem.getNumMtodscto() + "</td>"
                                + "<td style='text-align: center'>" + objItem.getNumCantidad() + "</td>"
                                + "<td style='text-align: right'> S/." + (objItem.getNumValor().doubleValue() - objItem.getNumMtodscto().doubleValue()) * objItem.getNumCantidad().doubleValue() +"</td>"                            
                            + "</tr>";

                    numCantidadTotal += objItem.getNumCantidad().intValue();
                    numImporteTotal  += (objItem.getNumValor().doubleValue() - objItem.getNumMtodscto().doubleValue()) * objItem.getNumCantidad().doubleValue();
                }

                cuerpo += "<tr>" 
                            + "<td colspan='5' style='text-align: right; font-weight: bold; font-size: 18px'> TOTALES: </td>"
                            + "<td style='text-align: center; font-weight: bold; font-size: 18px'>  " + numCantidadTotal + "</td>"     
                            + "<td style='text-align: right; font-weight: bold; font-size: 18px'> S/." + numImporteTotal + "</td>"     
                        + "</tr>"; 

                cuerpo += "</table><br/>";
            
            }

            SendEmail email = new SendEmail();
            email.sendMailSimple(destinatario, asunto, cuerpo);

            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_EMAIL_MESSAGE);
        
        }catch(CustomizerException | MessagingException ex){            
            UtilClass.addErrorMessage("Error al enviar el correo: " + ex.getMessage());             
        }
        
    }
    
    /**
     * SE UTILIZA PARA TERMINAR UNA VENTA A PARTIR DE UNA NOTA DE VENTA
     * @param viSicdocu
     * @return
     * @throws CustomizerException 
     */
    public String terminarVenta(ViSicdocu viSicdocu ) throws CustomizerException, Exception{       
        
        try {
            
            /*Se agrega validación para evitar que se vuelva a finalizar la venta de un documento a causa de utilizar 
              las flechas de navegacion del navegador*/
            String codEstaDocu = documentServiceImpl.getLastCodEstaDocu(viSicdocu.getIdDocu());
            if (!codEstaDocu.equals(Constantes.CONS_COD_ESTAPORRECOGER))
                throw new ValidationException("El documento ya no tiene estado PENDIENTE.");

            String desTitulo = "FINALIZAR VENTA";
            
            FacesContext context = FacesContext.getCurrentInstance();
            /*Se limpia el objeto "orderController" que se tenga en el FACESCONTEXT*/
            context.getExternalContext().getSessionMap().put("orderController", null);            
            OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);        
            objController.loadOrderDetails(viSicdocu.getIdDocu()
                                           ,desTitulo
                                           ,viSicdocu.getCodSclaseeven()
                                           ,viSicdocu.getIdDocu()
                                           ,new ArrayList<>()
                                           ,true
                                           ,true);
        }catch(ValidationException e){
            UtilClass.addErrorMessage(e.getMessage());
            return "";
        }
        
        return "ordenRegistrar?faces-redirect=true";
    }
    
    
    /**
     * SE UTILIZA PARA CREAR UNA COMPRA A PARTIR DE UNA ORDEN DE COMPRA     
     * @return
     * @throws CustomizerException 
     */
    public String crearCompraDesdeOC() throws CustomizerException, Exception{       
        
        String desTituloPaginaLocal = null;
        String codSClaseevenlocal = null;
        try {
            
            BigDecimal idDocuLocal = this.lstViSicdocusTmp.get(0).getIdDocu();
            
            /*Se verifica si la ORDEN DE COMPRA está pendiente de descargo*/            
            String codEstaDocu = documentServiceImpl.getLastCodEstaDocu(idDocuLocal);
                    
            if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_NOTACREDITO) && 
                    !codEstaDocu.equals(Constantes.CONS_COD_ESTAFINALIZADO)){
                
                throw new ValidationException("El documento no está finalizado.");                
            }
            else if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_ORDENCOMPRA) && 
                    codEstaDocu.equals(Constantes.CONS_COD_ESTAFINALIZADO)){
                
                throw new ValidationException("El documento ya está finalizado.");
            }
            
            if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_NOTACREDITO)){
                desTituloPaginaLocal = "ANULAR VENTA CON NOTA DE CREDITO";
                codSClaseevenlocal = this.codSClaseeven;
            }else if(this.codSClaseeven.equals(Constantes.CONS_COD_SCLASEEVEN_ORDENCOMPRA)){
                desTituloPaginaLocal = "REGISTRAR COMPRA DESDE UNA NOTA DE PEDIDO";
                codSClaseevenlocal = Constantes.CONS_COD_SCLASEEVEN_COMPRA;
            }
            
            FacesContext context = FacesContext.getCurrentInstance();
            
            /*Se obtiene solos los productos que fueron seleccionados*/
            List<Sic3docuprod> lstProductosSeleccionados = new ArrayList<>();
            for(Sic3docuprod objProd : this.lstProducts){
                if(objProd.getFlgSeleccionado()){
                    lstProductosSeleccionados.add(objProd);
                }                
            }      
            
            if(lstProductosSeleccionados.isEmpty()){
                UtilClass.addWarnMessage("Debe seleccionar productos.");
                return "";
            }
                
            
            context.getExternalContext().getSessionMap().put("orderController", null);            
            OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
            objController.loadOrderDetails( new BigDecimal(0)
                                           ,desTituloPaginaLocal
                                           ,codSClaseevenlocal
                                           ,idDocuLocal
                                           ,lstProductosSeleccionados
                                           ,true
                                           ,false);
        }catch(ValidationException e){
            UtilClass.addErrorMessage(e.getMessage());
            return "";
        }
        
        return "ordenRegistrar?faces-redirect=true";
    }
    
    /**
     * METODO QUE ANULA UN DOCUMENTO
     * @throws CustomizerException 
     */
    public void anularDocumento() throws CustomizerException{        
        
        try{
            
            String codigosRoles = SessionUtils.getCodigosRolPers(); //Se obtiene todos los codigos concatenados
            
            if (!codigosRoles.contains(Constantes.CONS_COD_SUPERVISOR) && !codigosRoles.contains(Constantes.CONS_COD_ADMINISTRADOR)){
                //Validar si se está anulando una operacion de un mes anterior.            
                if(UtilClass.convertDateToNumberYYYYMM(this.sic1docu.getFecDesde()).intValue() < UtilClass.convertDateToNumberYYYYMM(new Date()).intValue()){
                    throw new ValidationException("No tiene privilegios para anular una operación realizada en un mes diferente al actual. ");
                }
            }
            
            documentServiceImpl.anularDocumento(this.sic1docu
                                               ,Constantes.CONS_COD_ESTADOCU_COMPROBANTE
                                               ,Constantes.CONS_COD_ESTAANULADO
                                               ,this.desMotivoAnulacion);

            //this.filterDocuments();

        } catch (ValidationException e) {
            this.paramPageIdDocu = 0;
            UtilClass.addErrorMessage(e.getMessage());            
        } catch (Exception e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    
    /**
     * METODO PARA EXPORTAR EL RESULTADO DE LA BUSQUEDA DE DOCUMENTOS
     * @throws IOException
     * @throws CustomizerException 
     */
    public void exportReport() throws IOException, CustomizerException {
        
        String nombreReporte = "";
        
        try {
            
            if(this.desFecDesde != null && this.desFecDesde.trim().length() > 0)
                viSicdocu.setFecDesde(UtilClass.convertStringToDate(desFecDesde));
            else
                viSicdocu.setFecDesde(null);
            if(this.desFecHasta != null && this.desFecHasta.trim().length() > 0)
                viSicdocu.setFecHasta(UtilClass.convertStringToDate(desFecHasta));
            else
                viSicdocu.setFecHasta(null);

            this.lstViSicdocus = documentServiceImpl.listViSicdocu(viSicdocu);
            
            if (lstViSicdocus.size() > 0 ){
            
                if (this.codSClaseeven.equalsIgnoreCase("VI_SICSCLASEEVENVENTA")){
                    nombreReporte = "VENTA";
                }
                else if (this.codSClaseeven.equalsIgnoreCase("VI_SICSCLASEEVENCOMPRA")){
                    nombreReporte = "COMPRA";
                }else if (this.codSClaseeven.equalsIgnoreCase("VI_SICSCLASEEVENORDENCOMPRA")){
                    nombreReporte = "ORDEN DE COMPRA";
                }
                

                String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTFOLDER + "ReporteOperaciones.xlsx");
                FileInputStream excelFile = new FileInputStream(new File(pathFile));     

                XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
                XSSFSheet sheet = workbook.getSheetAt(0);

                /*Imprimir */
                Row rowStyle = sheet.getRow(0);
                
                Row row = sheet.getRow(0);
                Cell cell = row.getCell(0);
                cell.setCellValue("REPORTE DE " + nombreReporte);
                

                row = sheet.getRow(2);
                cell = row.createCell(1);
                cell.setCellValue(this.desFecDesde);

                row = sheet.getRow(3);
                cell = row.createCell(1);
                cell.setCellValue(this.desFecHasta);

                rowStyle = sheet.getRow(4);
                int fila = FILA_INI_EXCEL;
                for (ViSicdocu obj : lstViSicdocus) {
                    int colNum = 0;

                    row = sheet.createRow(fila++);

                    /*EVENTO*/
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDesDocu());

                    /*FEC_REGISTRO*/
                    cell = row.createCell(colNum++);
                    //cell.setCellStyle(rowStyle.getCell(1).getCellStyle());
                    cell.setCellValue(UtilClass.convertDateToString(obj.getFecDesde()));

                    /*CLIENTE / PROVEEDOR*/
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDesPersClieprov());

                    /*ESTADO*/
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDesEstadocu());

                    /*SUB_TOTAL*/
                    cell = row.createCell(colNum++);
                    if (obj.getNumSubtotal() != null)
                        cell.setCellValue(Double.valueOf(obj.getNumSubtotal().replace(",", ".")));

                    /*I.G.V.*/
                    cell = row.createCell(colNum++);
                    if (obj.getNumMtoigv() != null)
                        cell.setCellValue(Double.valueOf(obj.getNumMtoigv().replace(",", ".")));

                    /*DESCUENTO*/
                    cell = row.createCell(colNum++);
                    if (obj.getNumMtodscto() != null)
                        cell.setCellValue(obj.getNumMtodscto().replace(",", "."));

                    /*TOTAL*/
                    cell = row.createCell(colNum++);
                    if (obj.getNumMtototal() != null)
                        cell.setCellValue(obj.getNumMtototal());

                    /*MODO PAGO*/
                    cell = row.createCell(colNum++);                    
                    cell.setCellValue(obj.getDesModoPago());

                    /*RESPONSABLE*/
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDesPersCreador());               

                }

                /*Descargar desde la web*/
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=\"Reporte_" + nombreReporte + ".xlsx");
                workbook.write(response.getOutputStream());
                workbook.close();

                excelFile.close();

                fc.responseComplete();                
            }
            
        } catch (FileNotFoundException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (IOException | CustomizerException | NumberFormatException | ParseException ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void seleccionarOrdenCompra(ViSicdocu objDocu) throws Exception{
        
        try{
            this.lstViSicdocusTmp = new ArrayList<>();
            this.lstViSicdocusTmp.add(objDocu);
            
            //Listar los productos relacionados
            this.lstProducts = documentServiceImpl.obtProductosXidDocu(objDocu.getIdDocu());            
                        
            this.lstViSicdocus.clear();
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }        
    }
    
    /*Metodo que es invocado desde la pagina xhtml: <f:metadata>*/
//    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
//        
//        if(!FacesContext.getCurrentInstance().isPostback()){
//            
//            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
//            String tituloPagina     = (String)flash.get("paramTituloPagina"); 
//            String codSClaseeven    = (String)flash.get("paramCodSClaseeven");
//            String codTRolpersTmp      = (String)flash.get("paramCodTRolpers");
//            
//            /*Codigo que determinara si la operacion es una COMPRA O VENTA*/
//            if (codSClaseeven != null){
//                this.viSicdocu.setCodSclaseeven(codSClaseeven);
//                this.codSClaseeven = codSClaseeven;
//            }
//            else
//                throw new CustomizerException("No se cargo la sub clase del evento.");
//            
//            
//             /*Esto permite que cuando se registra un nueva persona, se guarde con el rol 
//               de CLIENTE O PROVEEDOR*/
//            if (codTRolpersTmp != null)
//                this.codTRolpers = codTRolpersTmp;
//            else
//                throw new CustomizerException("No se cargo el Tipo de Rol de la persona.");            
//            
//            this.desTituloPagina = tituloPagina;        
//        }
//    }
    
    public String registrarNuevaOrden() throws CustomizerException{
        
        //Validar        
        String codEstaCaja = SessionUtils.getCodEstaCaja();        
        
        if (codEstaCaja != null && !codEstaCaja.equals(Constantes.CONS_COD_ESTACREADO)){
            UtilClass.addErrorMessage("Para continuar se debe realizar la apertura de caja.");
            return "";
        }
        
        //
        String desTituloPagina = null;
        boolean flgEsVenta = false;
        String desNombrePagina = null;
        
        if(this.codSClaseeven.equals("VI_SICSCLASEEVENORDENCOMPRA")){
            desTituloPagina = "Registrar Orden de Compra";
            desNombrePagina = "ordenRegistrar";
        }else if(this.codSClaseeven.equals("VI_SICSCLASEEVENCOMPRA")){
            desTituloPagina = "Registrar Compra";
            desNombrePagina = "ordenRegistrar";
        }else if(this.codSClaseeven.equals("VI_SICSCLASEEVENVENTA")){
            desTituloPagina = "Registrar Venta";
            desNombrePagina = "ordenRegistrar";
            flgEsVenta = true;
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        objController.inicializarDatosRegistro(this.codSClaseeven, this.codTRolpers, desTituloPagina, flgEsVenta);
        
        return  desNombrePagina + "?faces-redirect=true";
        
    }
      
    /**
     * METODO QUE ES LLAMADO DESDE OTRAS PAGINAS
     * @param codSClaseeven
     * @param codTRolpersexterno
     * @param desTituloPagina     
     * @throws CustomizerException 
     */
    public void inicializarDatosRegistro( String codSClaseeven
                                         ,String codTRolpersexterno
                                         ,String desTituloPagina ) throws CustomizerException{
        
        try{
            
            this.desTituloPagina = desTituloPagina;
            this.codSClaseeven = codSClaseeven;
            this.codTRolpersExterno = codTRolpersexterno;                                   
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }
     
      
//    public void getParamsExternalPageAnularDocumento(ComponentSystemEvent event) throws CustomizerException, Exception{
//        
//        if(!FacesContext.getCurrentInstance().isPostback()){
//            
//            /*Metodo 1: Se obtiene los parametros que son enviados por la url con fancyBox. Por ejemplo cuando
//            se quiere registrar una nueva persona desde la pantalla del Registro COMPRA/VENTA*/
//            
//            System.out.println("idDocu: " + this.paramPageIdDocu );
//            
//            /*Se obtiene los datos de la orden*/
//            DocuOrderServiceImpl orderServiceImpl = new DocuOrderServiceImpl();
//            Sic1idendocu sic1idendocu = orderServiceImpl.getOrderByIdDocu(new BigDecimal(this.paramPageIdDocu));
//
//            /*Se obtiene los datos del Cliente/Proveedor*/
//            PersonServiceImpl personServiceImpl = new PersonServiceImpl();
//            Sic1idenpers sic1idenpers           = personServiceImpl.getById(sic1idendocu.getSic1docu().getIdPersexterno());
//
//            /*Seteando en las variables para que se visualice los datos en la pantalla*/
//            this.sic1docu           = sic1idendocu.getSic1docu();
//            this.sic1pers           = sic1idenpers.getSic1pers();
//            this.sic1idenpersId     = sic1idenpers.getId();
//            //this.lstSic3docuprod    = sic1idendocu.getSic1docu().getLstSic3docuprod();
//            
//            //this.desTituloPagina = "ANULAR DOCUMENTO DE " + this.sic1docu.getSic1sclaseeven().getDesSclaseeven() + ": " + this.sic1docu.getSic1stipodocu().getDesStipodocu() + " " + this.sic1docu.getCodSerie() + " - " + this.sic1docu.getNumDocu();
//            
//        }
//    }
    
}
