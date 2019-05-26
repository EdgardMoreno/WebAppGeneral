/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.CashRegisterServiceImpl;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate.views.ViSiccuaddiario;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.SendEmail;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emoreno
 */
@ManagedBean
@SessionScoped
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
    private String desTotalEfectivo;
    private String desFecDesde;
    private String desFecHasta;
    
    private List<Sic4cuaddiario> lstCierresDiarios;
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
            this.desTotalEfectivo = "S/ 0.00";
            
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

    public String getDesTotalEfectivo() {
        return desTotalEfectivo;
    }

    public void setDesTotalEfectivo(String desTotalEfectivo) {
        this.desTotalEfectivo = desTotalEfectivo;
    }

    public List<Sic4cuaddiario> getLstCierresDiarios() {
        return lstCierresDiarios;
    }

    public void setLstCierresDiarios(List<Sic4cuaddiario> lstCierresDiarios) {
        this.lstCierresDiarios = lstCierresDiarios;
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
            box.setNumEfectTotalSistema((box.getNumEfectTotalVentaSiste()).setScale(2, BigDecimal.ROUND_HALF_UP));
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
            
            /****************************************************/
            /*** CALCULO DE TRANSFERENCIA Y DEPOSITOS ***********/
            /****************************************************/
            
            if(box.getNumTranDepoTotal() == null)
                box.setNumTranDepoTotal(new BigDecimal("0.00"));

            /***********************************/
            /*** TOTAL VENTAS ******************/
            /***********************************/

            this.desTotalVentas = "S/" + box.getNumEfectTotalSistema().add(box.getNumTarjeTotalSiste()).add(box.getNumTranDepoTotalSiste()).toString();
            this.desTotalEfectivo = "S/" + box.getNumEfectTotalSistema().add(box.getNumEfectApertCaja()).subtract(box.getNumEfectTotalGastoSiste()).toString();
            this.box.setNumTotalVenta(box.getNumEfectTotalSistema().add(box.getNumTarjeTotalSiste()).add(box.getNumTranDepoTotalSiste()));

        }catch(Exception ex){
            UtilClass.addErrorMessage("Lo datos no se ingresaron correctamente, favor verificar.");
        }
        
    }
    
    public void save() throws CustomizerException{
        
        System.out.println("valor:" + box.getNumEfectTotal());
        
        try{
            
            if (Math.abs(this.box.getNumEfectSobraFalta().doubleValue()) > 5){
                throw new ValidationException("La diferencia de Sobrante / Faltante en Efectivo es mayor al permitido ");
            }
            if (Math.abs(this.box.getNumTarjeSobraFalta().doubleValue()) > 0){
                throw new ValidationException("La diferencia de Sobrante / Faltante en Tarjeta es mayor al permitido ");
            }            
            
            if(this.box.getNumTranDepoTotal()== null)
                this.box.setNumTranDepoTotal(new BigDecimal("0.00"));
                        
            if (this.box.getNumTranDepoTotal().subtract(this.box.getNumTranDepoTotalSiste()).intValue() != 0){
                throw new ValidationException("Hay diferencias en las ventas por Transferencia y Depósito.");
            }
            
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(SessionUtils.getUserId()); //Ira el ID_PERS DEL USUARIO LOGUEADO            
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));
            
            box.setId(id);
            box.setIdSucursal(SessionUtils.getIdSucursal());
            service.cerrarCaja(box);

            /*Actualizando el nuevo estado de la caja en la Session*/
            SessionUtils.setCodEstaCaja(Constantes.CONS_COD_ESTACERRADO);
            
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            
            /*Mandando correo notificando cierre de caja*/
            try{
                this.notifyByEmail(box);
            }catch(Exception e){
                System.out.println("Error al enviar email:" + e.getMessage());
            }
            
            /*Limpiar Controles*/
            box = new Sic4cuaddiario();
            this.editFields = false;
            
            
        }catch(ValidationException ex ){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch(CustomizerException ex ){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void filter() throws CustomizerException, Exception{        
        
         try {

            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            this.lstCierresDiarios = service.listarCierresDiarios(this.desFecDesde, this.desFecHasta);            
                        
        } catch (CustomizerException e) {
            throw new CustomizerException(e.getMessage());
        }        
    }
    
    public void notifyByEmail(Sic4cuaddiario obj){         
        
        try{

                String destinatario =  Constantes.CONS_DES_EMAIL_DESTINATARIO; //A quien le quieres escribir.
                String asunto = Constantes.CONS_DES_AMBIENTESISTEMA + " NOTIFICACION DE CIERRE CAJA (" + obj.getFecCierre() + ")";
                String cuerpo = "";     
                
                String stylePrincipal = "style='font-weight: bold; font-size: 12px; padding:5px; background-color:" + Constantes.CONS_COD_COLORTABLA + "'";
                String stylePrincipaltTotal = "style='font-weight: bold; font-size: 12px; padding:5px; background-color:" + Constantes.CONS_COD_COLORTABLATOTAL + "'";
                String style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 2px'";
            
                cuerpo = "<table>"
                                + "<tr>"
                                    + "<td " + style + ">Usuario:</td>"
                                    + "<td>" + SessionUtils.getUserName() + "</td>" 
                                + "</tr>"
                                + "<tr>"
                                    + "<td " + style + ">Periodo:</td>"
                                    + "<td>" + obj.getId().getNumPeri() + "</td>"
                                + "</tr>";
                cuerpo += "</table><br/>";

                style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 4px'";
                
                cuerpo += "<table width=\"400\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" style=\"border-collapse:collapse;\">"
                        
                /*APERTURA CAJA*/                        
                        + "<tr><td colspan='2' " + stylePrincipal + ">APERTURA CAJA</td></tr>"
                        + "<tr " + style + "><td>CAJA INICIAL:</td><td> S/." + obj.getNumEfectApertCaja()+ "</td></tr>"
                        
                /*EFECTIVO*/
                        + "<tr><td colspan='2' " + stylePrincipal + ">VENTA EFECTIVO</td></tr>"
                        + "<tr " + style + "><td>TOTAL EFECTIVO:</td><td> S/." + obj.getNumEfectTotalVentaSiste() + "</td></tr>"                        
                        + "<tr " + style + "><td>Descuadre:</td><td> S/." + obj.getNumEfectSobraFalta() + "</td></tr>"

                /*TARJETA*/
                        + "<tr><td colspan='2' " + stylePrincipal + ">VENTA TARJETA</td></tr>"
                        + "<tr " + style + "><td>TOTAL TARJETA:</td><td> S/." + obj.getNumTarjeTotalSiste()+ "</td></tr>"
                        + "<tr " + style + "><td>Descuadre:</td><td> S/." + obj.getNumTarjeSobraFalta()+ "</td></tr>"
                        
                /*TRANFERENCIA DEPOSITO*/
                        + "<tr><td colspan='2' " + stylePrincipal + ">VENTA TRANS/DEPOSITO</td></tr>"
                        + "<tr " + style + "><td>TOTAL TRANS/DEPO:</td><td> S/." + obj.getNumTranDepoTotalSiste()+ "</td></tr>"
                        
                /*GASTO EFECTIVO*/  
                        + "<tr><td colspan='2' " + stylePrincipal + ">GASTOS EFECTIVO</td></tr>"                        
                        + "<tr " + style + "><td>TOTAL GASTOS:</td><td> S/." + obj.getNumEfectTotalGastoSiste() + "</td></tr>"

                /*TOTAL VENTA*/
                        + "<tr><td colspan='2' " + stylePrincipaltTotal + ">TOTAL VENTA</td></tr>"
                        + "<tr " + style + "><td>TOTAL VENTAS(EFECT + TARJ + TRANS/DEPO):</td><td> S/." + obj.getNumEfectTotalVentaSiste().add(obj.getNumTarjeTotalSiste()).add(obj.getNumTranDepoTotalSiste()) + "</td></tr>"
                
                /*TOTAL EFECTIVO: VENTA EFECTIVO - GASTO EFECTIVO*/  
                        + "<tr><td colspan='2' " + stylePrincipaltTotal + ">TOTAL EFECTIVO CAJA</td></tr>" 
                        + "<tr " + style + "><td>TOTAL EFCTIVO EN CAJA:</td><td> S/." + obj.getNumEfectApertCaja().add(obj.getNumEfectTotalVentaSiste()).subtract(obj.getNumEfectTotalGastoSiste()) + "</td></tr>";
                
                cuerpo += "</table><br/>";

                SendEmail email = new SendEmail();
                email.sendMailSimple(destinatario, asunto, cuerpo);

            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_EMAIL_MESSAGE);
            
        }catch(MessagingException ex){
            UtilClass.addErrorMessage(Constantes.CONS_ERROR_EMAIL_MESSAGE + " Detalle: " + ex.getMessage());
        }       
    
    }
    
    public String obtFormatoEmail(String numEfectTotalVentaSiste
                                  ,String numEfectTotalGastoSiste) throws CustomizerException, Exception{        
        
        String cuerpo;
        
        try{
                       
            //METODO AUN SIN UTILIZAR, SERVIRA PARA UNIFICAR UN SOLO FORMATO

            String style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 4px'";
            String stylePrincipal = "style='font-weight: bold; font-size: 12px; padding:5px; background-color:" + Constantes.CONS_COD_COLORTABLA + "'";
            
            cuerpo = "<table border='1'>"
                    + "<tr><td colspan='2' " + stylePrincipal + ">EFECTIVO</td></tr>"
                    + "<tr><td " + style + ">TOTAL EFECTIVO:</td><td>" + this.box.getNumEfectTotalVentaSiste() + "</td></tr>"
                    + "<tr><td " + style + ">TOTAL GASTO EFECTIVO:</td><td>" + this.box.getNumEfectTotalGastoSiste() + "</td></tr>"
                    + "<tr><td " + style + ">TOTAL EFECTIVO(EFEC - GAST):</td><td>" + this.desTotalEfectivo + "</td></tr>"
                    + "<tr><td " + style + ">Descuadre:</td><td> S/." + box.getNumEfectSobraFalta() + "</td></tr>";
            cuerpo += "</table><br/>";

            /*TARJETA*/
            cuerpo += "<table border='1'>"
                    + "<tr><td colspan='2'  " + stylePrincipal + ">TARJETA</td></tr>"
                    + "<tr><td " + style + ">TOTAL TARJETA:</td><td>" + this.box.getNumTarjeTotalSiste()+ "</td></tr>"
                    + "<tr><td " + style + ">Descuadre:</td><td> S/." + this.box.getNumTarjeSobraFalta()+ "</td></tr>";
            cuerpo += "</table><br/>";

            /*TOTAL VENTA*/
            cuerpo += "<table border='1'>"
                    + "<tr><td colspan='2' " + stylePrincipal + ">TOTAL VENTA</td></tr>"
                    + "<tr><td " + style + ">TOTAL VENTA:</td><td>" + (this.box.getNumEfectTotalVentaSiste().doubleValue() + this.box.getNumTarjeTotalSiste().doubleValue()) + "</td></tr>";
            cuerpo += "</table><br/>";

        }catch(Exception ex ){
            throw new Exception(ex.getMessage());
        }
        
        return cuerpo;
    }
    
    public String viewDetail(Sic4cuaddiario obj) throws CustomizerException{        

        String desTitulo = "VER CUADRE DE CAJA: " + obj.getFecApertura();
        
        FacesContext context = FacesContext.getCurrentInstance();
        CashRegisterController objController = context.getApplication().evaluateExpressionGet(context, "#{cashRegisterController}", CashRegisterController.class);
        objController.cargarDatosPagina(obj.getId().getNumPeri()
                                       ,obj.getId().getIdPers()
                                       ,obj.getSic1usuario().getCodUsuario()
                                       ,desTitulo
                                       ,obj.getSic3docuesta().getCodEsta() );
                
        return "cajaCuadre?faces-redirect=true";        
        
    }
    
    /*Metodo que es invocado desde la pagina xhtml: <f:metadata>*/
    //public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
    public void cargarDatosPagina( BigDecimal numPeri
                                  ,BigDecimal idPers
                                  ,String desPersCajeroLocal
                                  ,String desTituloPagina
                                  ,String codEsta) throws CustomizerException{
        
        //if(!FacesContext.getCurrentInstance().isPostback()){
            
            //Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            //String externalPage     = (String)flash.get("paramExternalPage");
            //String tituloPagina     = (String)flash.get("paramTituloPagina"); 
            //BigDecimal idPers       = (BigDecimal)flash.get("paramIdPers");
            //String desPersCajeroTmp    = (String)flash.get("paramDesPersCajero");
            //BigDecimal numPeri      = (BigDecimal)flash.get("paramNumPeri");
            
            //System.out.println("externalPage: " + externalPage);
            //System.out.println("tituloPagina: " + tituloPagina);
            System.out.println("idPers: " + idPers);
            //System.out.println("desPersCajero: " + desPersCajeroTmp);
            System.out.println("numPeri: " + numPeri);
            
            
            this.desTituloPagina = desTituloPagina;
            
            /*Se evalua si se llama de una pagina externa*/
            if (numPeri.intValue() > 0){
                
                this.editFields = false;
                this.desPersCajero = desPersCajeroLocal;
                
                /*OBTENER LOS DATOS DE LA CAJA DIARIA QUE SE QUIERE VER SU DETALLE*/
                if (idPers != null && idPers.intValue() > 0){
                
                    CashRegisterServiceImpl service = new CashRegisterServiceImpl();
                    if(codEsta!= null && codEsta.equalsIgnoreCase(Constantes.CONS_COD_ESTACERRADO))
                        this.box = service.obtenerDatosPeriodoCerrado(idPers, numPeri);
                    else
                        this.box = service.obtenerDatosApertura(idPers, numPeri);
                    
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
                this.box = service.obtenerDatosApertura(SessionUtils.getUserId(), new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));

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
                
                //box.setNumEfectTotalGastoSiste(new BigDecimal(val).setScale(2));
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
        //}
    }
}
