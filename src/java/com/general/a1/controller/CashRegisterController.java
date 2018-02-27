/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.CashRegisterServiceImpl;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate1.ViSiccuaddiario;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emoreno
 */
@ManagedBean
@ViewScoped
public class CashRegisterController implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(CashRegisterController.class);
    private Sic4cuaddiario box;
    private String desTituloPagina;
    
    private BigDecimal numCalcuDenom0200;
    private BigDecimal numCalcuDenom0100;
    private BigDecimal numCalcuDenom0050;
    private BigDecimal numCalcuDenom0020;
    private BigDecimal numCalcuDenom0010;
    private BigDecimal numCalcuDenom0005;
    private BigDecimal numCalcuDenom0002;
    private BigDecimal numCalcuDenom0001;
    private BigDecimal numCalcuDenom0_50;
    private BigDecimal numCalcuDenom0_20;
    private BigDecimal numCalcuDenom0_10;
    private BigDecimal numCalcuDenom0_05;
    
    private String desTotalVentas;
    private String desFecDesde;
    private String desFecHasta;
    
    private List<ViSiccuaddiario> listViDayBox;
    private ViSiccuaddiario viDayBox;
    
    private boolean editFields;
    private String desPersCajero;
    
    @PostConstruct
    public void init() {
        
        try{
            System.out.println("iniciando");            
            
            editFields = true;
            listViDayBox = new ArrayList();
            viDayBox = new ViSiccuaddiario();
            
            int valIni = 0;
            
            this.setNumCalcuDenom0200(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0100(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0050(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0020(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0010(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0005(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0002(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0001(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0_50(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0_20(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0_10(new BigDecimal(valIni).setScale(2));
            this.setNumCalcuDenom0_05(new BigDecimal(valIni).setScale(2));
            
            this.desTotalVentas = "S/ 0.00";
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }   
    }
    
    /*PROPIEDADES*/  

    public Sic4cuaddiario getBox() {
        return box;
    }

    public void setBox(Sic4cuaddiario box) {
        this.box = box;
    }
    
    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public String getDesPersCajero() {
        return desPersCajero;
    }

    public void setDesPersCajero(String desPersCajero) {
        this.desPersCajero = desPersCajero;
    }
    
    

    public BigDecimal getNumCalcuDenom0200() {
        return numCalcuDenom0200;
    }

    public void setNumCalcuDenom0200(BigDecimal numCalcuDenom0200) {
        this.numCalcuDenom0200 = numCalcuDenom0200;
    }

    public BigDecimal getNumCalcuDenom0100() {
        return numCalcuDenom0100;
    }

    public void setNumCalcuDenom0100(BigDecimal numCalcuDenom0100) {
        this.numCalcuDenom0100 = numCalcuDenom0100;
    }

    public BigDecimal getNumCalcuDenom0050() {
        return numCalcuDenom0050;
    }

    public void setNumCalcuDenom0050(BigDecimal numCalcuDenom0050) {
        this.numCalcuDenom0050 = numCalcuDenom0050;
    }

    public BigDecimal getNumCalcuDenom0020() {
        return numCalcuDenom0020;
    }

    public void setNumCalcuDenom0020(BigDecimal numCalcuDenom0020) {
        this.numCalcuDenom0020 = numCalcuDenom0020;
    }

    public BigDecimal getNumCalcuDenom0010() {
        return numCalcuDenom0010;
    }

    public void setNumCalcuDenom0010(BigDecimal numCalcuDenom0010) {
        this.numCalcuDenom0010 = numCalcuDenom0010;
    }

    public BigDecimal getNumCalcuDenom0005() {
        return numCalcuDenom0005;
    }

    public void setNumCalcuDenom0005(BigDecimal numCalcuDenom0005) {
        this.numCalcuDenom0005 = numCalcuDenom0005;
    }

    public BigDecimal getNumCalcuDenom0002() {
        return numCalcuDenom0002;
    }

    public void setNumCalcuDenom0002(BigDecimal numCalcuDenom0002) {
        this.numCalcuDenom0002 = numCalcuDenom0002;
    }

    public BigDecimal getNumCalcuDenom0001() {
        return numCalcuDenom0001;
    }

    public void setNumCalcuDenom0001(BigDecimal numCalcuDenom0001) {
        this.numCalcuDenom0001 = numCalcuDenom0001;
    }

    public BigDecimal getNumCalcuDenom0_50() {
        return numCalcuDenom0_50;
    }

    public void setNumCalcuDenom0_50(BigDecimal numCalcuDenom0_50) {
        this.numCalcuDenom0_50 = numCalcuDenom0_50;
    }

    public BigDecimal getNumCalcuDenom0_20() {
        return numCalcuDenom0_20;
    }

    public void setNumCalcuDenom0_20(BigDecimal numCalcuDenom0_20) {
        this.numCalcuDenom0_20 = numCalcuDenom0_20;
    }

    public BigDecimal getNumCalcuDenom0_10() {
        return numCalcuDenom0_10;
    }

    public void setNumCalcuDenom0_10(BigDecimal numCalcuDenom0_10) {
        this.numCalcuDenom0_10 = numCalcuDenom0_10;
    }

    public BigDecimal getNumCalcuDenom0_05() {
        return numCalcuDenom0_05;
    }

    public void setNumCalcuDenom0_05(BigDecimal numCalcuDenom0_05) {
        this.numCalcuDenom0_05 = numCalcuDenom0_05;
    }

    public String getDesTotalVentas() {
        return desTotalVentas;
    }

    public void setDesTotalVentas(String desTotalVentas) {
        this.desTotalVentas = desTotalVentas;
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

    public List<ViSiccuaddiario> getListViDayBox() {
        return listViDayBox;
    }

    public void setListViDayBox(List<ViSiccuaddiario> listViDayBox) {
        this.listViDayBox = listViDayBox;
    }

    public ViSiccuaddiario getViDayBox() {
        return viDayBox;
    }

    public void setViDayBox(ViSiccuaddiario viDayBox) {
        this.viDayBox = viDayBox;
    }

    public boolean isEditFields() {
        return editFields;
    }

    public void setEditFields(boolean editFields) {
        this.editFields = editFields;
    }

    

    
    

    /*METODOS*/
    public void calculate(){
        
        try{
        
            /*************************************/
            /*** TOTAL EFECTIVO ******************/
            /*************************************/

            /*CALCULADO SEGUN DENOMINACION*/
            double totalDenom = 0;

            if (this.box.getNumEfectDenom0200() != null){
                totalDenom            += this.box.getNumEfectDenom0200().multiply(new BigDecimal(200)).doubleValue();
                this.numCalcuDenom0200 = this.box.getNumEfectDenom0200().multiply(new BigDecimal(200)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0100() != null){
                totalDenom            += this.box.getNumEfectDenom0100().multiply(new BigDecimal(100)).doubleValue();
                this.numCalcuDenom0100 = this.box.getNumEfectDenom0100().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0050() != null){
                totalDenom            += this.box.getNumEfectDenom0050().multiply(new BigDecimal(50)).doubleValue();
                this.numCalcuDenom0050 = this.box.getNumEfectDenom0050().multiply(new BigDecimal(50)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0020() != null){
                totalDenom            += this.box.getNumEfectDenom0020().multiply(new BigDecimal(20)).doubleValue();
                this.numCalcuDenom0020 = this.box.getNumEfectDenom0020().multiply(new BigDecimal(20)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0010() != null){
                totalDenom            += this.box.getNumEfectDenom0010().multiply(new BigDecimal(10)).doubleValue();
                this.numCalcuDenom0010 = this.box.getNumEfectDenom0010().multiply(new BigDecimal(10)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0005() != null){
                totalDenom            += this.box.getNumEfectDenom0005().multiply(new BigDecimal(5)).doubleValue();
                this.numCalcuDenom0005 = this.box.getNumEfectDenom0005().multiply(new BigDecimal(5)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0002() != null){
                totalDenom            += this.box.getNumEfectDenom0002().multiply(new BigDecimal(2)).doubleValue();
                this.numCalcuDenom0002 = this.box.getNumEfectDenom0002().multiply(new BigDecimal(2)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0001() != null){
                totalDenom            += this.box.getNumEfectDenom0001().multiply(new BigDecimal(1)).doubleValue();
                this.numCalcuDenom0001 = this.box.getNumEfectDenom0001().multiply(new BigDecimal(1)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0_50() != null){
                totalDenom            += this.box.getNumEfectDenom0_50().multiply(new BigDecimal(0.5)).doubleValue();
                this.numCalcuDenom0_50 = this.box.getNumEfectDenom0_50().multiply(new BigDecimal(0.5)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0_20() != null){
                totalDenom            += this.box.getNumEfectDenom0_20().multiply(new BigDecimal(0.2)).doubleValue();
                this.numCalcuDenom0_20 = this.box.getNumEfectDenom0_20().multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0_10() != null){
                totalDenom            += this.box.getNumEfectDenom0_10().multiply(new BigDecimal(0.1)).doubleValue();
                this.numCalcuDenom0_10 = this.box.getNumEfectDenom0_10().multiply(new BigDecimal(0.1)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            if (this.box.getNumEfectDenom0_05() != null){
                totalDenom            += this.box.getNumEfectDenom0_05().multiply(new BigDecimal(0.05)).doubleValue();
                this.numCalcuDenom0_05 = this.box.getNumEfectDenom0_05().multiply(new BigDecimal(0.05)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }

            System.out.println("TOTAL DENOMINACION :" + totalDenom);        
            this.box.setNumEfectDenomTotal(new BigDecimal(totalDenom).setScale(2, BigDecimal.ROUND_HALF_UP));

            /*CALCULAR: TOTAL EFECTIVO:*/
            this.box.setNumEfectTotal(box.getNumEfectApertCaja().add(box.getNumEfectDenomTotal()).setScale(2,BigDecimal.ROUND_HALF_UP));        

            /*CALCULAR: TOTAL EFECTIVO DEL SISTEMA*/
            box.setNumEfectTotalSistema(box.getNumEfectTotalGastoSiste().add(box.getNumEfectTotalVentaSiste()).setScale(2, BigDecimal.ROUND_HALF_UP));
            System.out.println("TOTAL EFECTIVO DEL SISTEMA:" + box.getNumEfectTotalSistema());

            /*CALCULAR: EFECTIVO SOBRANTE / FALTANTE*/
            //box.setNumEfectSobraFalta(box.getNumEfectTotalSistema().subtract(box.getNumEfectTotal()).add(box.getNumEfectApertCaja()).setScale(2, BigDecimal.ROUND_HALF_UP));
            box.setNumEfectSobraFalta   (box.getNumEfectDenomTotal().subtract(box.getNumEfectTotalSistema()).setScale(2, BigDecimal.ROUND_HALF_UP));
            System.out.println("SOBRANTE / FALTANTE:" + box.getNumEfectSobraFalta());

            /***********************************/
            /*** CALCULO DE TARJETA ***********/
            /***********************************/
            if(box.getNumTarjeCrediTotal() == null)
                box.setNumTarjeCrediTotal(new BigDecimal("0.00"));
            if(box.getNumTarjeDebitTotal() == null)
                box.setNumTarjeDebitTotal(new BigDecimal("0.00"));
            
            box.setNumTarjeTotal(box.getNumTarjeCrediTotal().add(box.getNumTarjeDebitTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));

            /*CALCULAR: TARJETA SOBRANTE / FALTANTE*/
            box.setNumTarjeSobraFalta(box.getNumTarjeTotal().subtract(box.getNumTarjeTotalSiste()).setScale(2,BigDecimal.ROUND_HALF_UP ));

            /***********************************/
            /*** TOTAL VENTAS ******************/
            /***********************************/

            this.desTotalVentas = "S/" + box.getNumEfectTotalSistema().add(box.getNumTarjeTotalSiste()).toString();
            this.box.setNumTotalVenta(box.getNumEfectTotalSistema().add(box.getNumTarjeTotalSiste()));

        }catch(Exception ex){
            UtilClass.addErrorMessage("Lo datos no se ingresaron correctamente, favor verificar.");
        }
        
    }
    
    public void save() throws CustomizerException{
        System.out.println("valor:" + box.getNumEfectTotal());
        
        try{
            
            if (Math.abs(this.box.getNumEfectSobraFalta().doubleValue()) > 15){
                throw new ValidationException("La diferencia de Sobrante / Faltante en Efectivo es mayor al permitido ");
            }
            if (Math.abs(this.box.getNumTarjeSobraFalta().doubleValue()) > 15){
                throw new ValidationException("La diferencia de Sobrante / Faltante en Tarjeta es mayor al permitido ");
            }
            
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(SessionUtils.getUserId()); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));
            
            box.setId(id);
            service.close(box);
            
            /*Actualizando el nuevo estado de la caja en la Session*/
            SessionUtils.setCodEstaCaja(Constantes.CONS_COD_ESTACERRADO);            
            
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            
            box = new Sic4cuaddiario();
            this.editFields = false;
            
        }catch(ValidationException ex ){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch(Exception ex ){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void filter() throws CustomizerException{        
        
         try {

            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
             
            if(this.desFecDesde != null && this.desFecDesde.trim().length() > 0)
                viDayBox.setFecApertura(desFecDesde);
            else
                 viDayBox.setFecApertura(null);
            
            if(this.desFecHasta != null && this.desFecHasta.trim().length() > 0)
                viDayBox.setFecCierre(desFecHasta);
            else
                viDayBox.setFecCierre(null);

            this.listViDayBox = service.listViSiccuaddiario(viDayBox);  
            
        } catch (Exception e) {
            throw new CustomizerException(e.getMessage());
        }        
    }
    
    public String viewDetail(ViSiccuaddiario obj){
        
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.clear();
        flash.put("paramExternalPage", "1");
        flash.put("paramIdPers", obj.getId().getIdPers());
        flash.put("paramDesPersCajero", obj.getDesPers());
        flash.put("paramNumPeri", obj.getId().getNumPeri());
        flash.put("paramTituloPagina", "VER CUADRE DE CAJA: " + obj.getFecApertura().substring(0, 9) );
        
        flash.setKeepMessages(true);
        
        return "cajaCuadre?faces-redirect=true";
        
        
    }
    
    /*Metodo que es invocado desde la pagina xhtml: <f:metadata>*/
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            String externalPage     = (String)flash.get("paramExternalPage");
            String tituloPagina     = (String)flash.get("paramTituloPagina"); 
            BigDecimal idPers       = (BigDecimal)flash.get("paramIdPers");
            String desPersCajeroTmp    = (String)flash.get("paramDesPersCajero");
            BigDecimal numPeri      = (BigDecimal)flash.get("paramNumPeri");
            
            System.out.println("externalPage: " + externalPage);
            System.out.println("tituloPagina: " + tituloPagina);
            System.out.println("idPers: " + idPers);
            System.out.println("desPersCajero: " + desPersCajeroTmp);
            System.out.println("numPeri: " + numPeri);
            
            
            this.desTituloPagina = tituloPagina;
            
            /*Se evalua si se llama de una pagina externa*/
            if (externalPage != null && externalPage.equals("1")){
                
                this.editFields = false;
                this.desPersCajero = desPersCajeroTmp;
                
                /*OBTENER LOS DATOS DE LA CAJA DIARIA QUE SE QUIERE VER SU DETALLE*/
                if (numPeri != null && numPeri.intValue() > 0 && 
                    idPers != null && idPers.intValue() > 0){
                
                    CashRegisterServiceImpl service = new CashRegisterServiceImpl();
                    this.box = service.getById(idPers, numPeri);
                    
                    this.calculate();
                    
                }else{
                    throw new CustomizerException("Los parámetros no se cargaron correctamente.");
                }
            }else{
                /*Ingresa cuando se da clic directamente en la opcion de CUADRAR CAJA*/
                
                /******************************************/
                /***SE OBTIENE LOS DATOS DE LA APERTURA***/
                /******************************************/
                this.desPersCajero = SessionUtils.getUserCompleteName();
                
                CashRegisterServiceImpl service = new CashRegisterServiceImpl();
                this.box = service.getById(SessionUtils.getUserId(), new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));

                if (box == null)
                    box = new Sic4cuaddiario();

                int val = 0;           

                box.setNumEfectDenom0200(new BigDecimal(val));
                box.setNumEfectDenom0100(new BigDecimal(val));
                box.setNumEfectDenom0050(new BigDecimal(val));
                box.setNumEfectDenom0020(new BigDecimal(val));
                box.setNumEfectDenom0010(new BigDecimal(val));
                box.setNumEfectDenom0005(new BigDecimal(val));
                box.setNumEfectDenom0002(new BigDecimal(val));
                box.setNumEfectDenom0001(new BigDecimal(val));
                box.setNumEfectDenom0_50(new BigDecimal(val));
                box.setNumEfectDenom0_20(new BigDecimal(val));/*0.20*/
                box.setNumEfectDenom0_10(new BigDecimal(val));/*0.10*/
                box.setNumEfectDenom0_05(new BigDecimal(val));/*0.05*/

                box.setNumEfectDenomTotal(new BigDecimal(val).setScale(2));

                box.setNumEfectGastoTotal(new BigDecimal(val).setScale(2));
                //box.setNumEfectApertCaja(new BigDecimal(0).setScale(2));            
                box.setNumEfectTotal(new BigDecimal(val).setScale(2));

                /*EFECTIVO: INGRESO SISTEMA*/
                if(box.getNumEfectTotalVentaSiste() == null)
                    box.setNumEfectTotalVentaSiste(new BigDecimal(val).setScale(2));
                
                box.setNumEfectTotalGastoSiste(new BigDecimal(val).setScale(2));
                box.setNumEfectTotalSistema(box.getNumEfectTotalVentaSiste().setScale(2));

                /*EFECTIVO: SOBRANTE / FALTANTE*/
                box.setNumEfectSobraFalta(new BigDecimal(val).setScale(2));

                /*TARJETA: INGRESO USUARIO*/
                box.setNumTarjeCrediTotal(new BigDecimal(val).setScale(2));
                box.setNumTarjeDebitTotal(new BigDecimal(val).setScale(2));
                box.setNumTarjeTotal(new BigDecimal(val).setScale(2));

                /*TARJETA: INGRESO SISTEMA*/
                if(box.getNumTarjeTotalSiste()==null)
                    box.setNumTarjeTotalSiste(new BigDecimal(val).setScale(2));

                /*TARJETA: SOBRANTE / FALTANTE*/
                box.setNumTarjeSobraFalta(new BigDecimal(val).setScale(2));
            }
        }
    }
}
