/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.ReportServiceImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.util.beans.Constantes;
import com.general.util.beans.Reporte;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Edgard
 */
@ManagedBean
@SessionScoped
public class ReportController  implements Serializable{
    
    
    private List<SelectItem> itemPersEmpresa  = new ArrayList();
    private Integer idPersEmpresa;
    private String desTituloPagina;
    
    private String strFecDesde;
    private String strFecHasta;
    
    private String strFecDesde3;
    private String strFecHasta3;
    
    private String strFecDesde4;
    private String strFecHasta4;
    
    private String strFecDesde5;
    private String strFecHasta5;
    
    /*Pantalla Reporte Detalle de Operaciones por Producto*/
    private String strFecDesde6;
    private String strFecHasta6;
    private String strCodprod;
    private List<Sic3docuprod> lstDocuprod;
    
  
    
    
    public ReportController(){
    
        try{           
            
                                
            SelectItem si = new SelectItem();           
            
            si.setLabel("INVERSIONES EMZA E.I.R.L");                
            si.setValue(109);
            itemPersEmpresa.add(si);
            
            si = new SelectItem();
            si.setLabel("EDGARD MORENO (DECO STYLOS)");                
            si.setValue(21);
            itemPersEmpresa.add(si);         
                            
        
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }        
    }

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }

    public List<SelectItem> getItemPersEmpresa() {
        return itemPersEmpresa;
    }

    public void setItemPersEmpresa(List<SelectItem> itemPersEmpresa) {
        this.itemPersEmpresa = itemPersEmpresa;
    }  
    
    public Integer getIdPersEmpresa() {
        return idPersEmpresa;
    }

    public void setIdPersEmpresa(Integer idPersEmpresa) {
        this.idPersEmpresa = idPersEmpresa;
    }
     
    public String getStrFecDesde() {
        return strFecDesde;
    }

    public void setStrFecDesde(String strFecDesde) {
        this.strFecDesde = strFecDesde;
    }

    public String getStrFecHasta() {
        return strFecHasta;
    }

    public void setStrFecHasta(String strFecHasta) {
        this.strFecHasta = strFecHasta;
    }

    public String getStrFecDesde3() {
        return strFecDesde3;
    }

    public void setStrFecDesde3(String strFecDesde3) {
        this.strFecDesde3 = strFecDesde3;
    }

    public String getStrFecHasta3() {
        return strFecHasta3;
    }

    public void setStrFecHasta3(String strFecHasta3) {
        this.strFecHasta3 = strFecHasta3;
    }

    public String getStrFecDesde4() {
        return strFecDesde4;
    }

    public void setStrFecDesde4(String strFecDesde4) {
        this.strFecDesde4 = strFecDesde4;
    }

    public String getStrFecHasta4() {
        return strFecHasta4;
    }

    public void setStrFecHasta4(String strFecHasta4) {
        this.strFecHasta4 = strFecHasta4;
    }

    public String getStrFecDesde5() {
        return strFecDesde5;
    }

    public void setStrFecDesde5(String strFecDesde5) {
        this.strFecDesde5 = strFecDesde5;
    }

    public String getStrFecHasta5() {
        return strFecHasta5;
    }

    public void setStrFecHasta5(String strFecHasta5) {
        this.strFecHasta5 = strFecHasta5;
    }

    public String getStrFecDesde6() {
        return strFecDesde6;
    }

    public void setStrFecDesde6(String strFecDesde6) {
        this.strFecDesde6 = strFecDesde6;
    }

    public String getStrFecHasta6() {
        return strFecHasta6;
    }

    public void setStrFecHasta6(String strFecHasta6) {
        this.strFecHasta6 = strFecHasta6;
    }

    public String getStrCodprod() {
        return strCodprod;
    }

    public void setStrCodprod(String strCodprod) {
        this.strCodprod = strCodprod;
    }

    public List<Sic3docuprod> getLstDocuprod() {
        return lstDocuprod;
    }

    public void setLstDocuprod(List<Sic3docuprod> lstDocuprod) {
        this.lstDocuprod = lstDocuprod;
    }
    
    /*METODOS*/
    public void downloadReport002() throws CustomizerException, IOException{
        
        try{

            ReportServiceImpl objService = new ReportServiceImpl();
            List<Reporte> list =  objService.getReport002(this.strFecDesde, this.strFecHasta);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reporte002");
            
            CellStyle styleHeader = workbook.createCellStyle();            
            XSSFFont  font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short)10);
            font.setBold(true);
            styleHeader.setFont(font);
            
            CellStyle styleDecimal = workbook.createCellStyle();
            styleDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

            /*Imprimir Periodo*/
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("FECHA VENTA");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(1);
            cell.setCellValue("COD. PRODUCTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(2);
            cell.setCellValue("DESCRIPCION PRODUCTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(3);
            cell.setCellValue("CANTIDAD VENDIDA");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(4);
            cell.setCellValue("PRECIO X UNIDAD");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(5);
            cell.setCellValue("TOTAL VENTA");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(6);
            cell.setCellValue("COSTO X UNIDAD");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(7);
            cell.setCellValue("TOTAL COSTO");
            cell.setCellStyle(styleHeader);
            
            int fila = 1;
            for (Reporte obj : list) {
                
                int colNum = 0;
                
                row = sheet.createRow(fila);
                
                //System.out.println("Fila: " + fila + " - ID_DOCO: " + obj.getDetSale().getSic1docu().getIdDocu());
                
                /*FECHA VENTA*/
                cell = row.createCell(colNum++);
                cell.setCellValue(UtilClass.convertDateToString(obj.getDetSale().getSic1docu().getFecDesde()));
                
                /*CODIGO DE PRODUCTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetSale().getSic1prod().getCodProd());
                
                /*DESCRIPCION PRODUCTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetSale().getSic1prod().getDesProd());
                
                /*CANTIDAD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetSale().getNumCantidad().doubleValue());
                
                /*PRECIO X UNIDAD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetSale().getNumValor().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                /*TOTAL VENTA*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetSale().getNumCantidad().doubleValue() * obj.getDetSale().getNumValor().doubleValue());                

                /*COSTO X UNIDAD*/
                if (obj.getDetPurchase().getNumValor() != null){
                    
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDetPurchase().getNumValor().doubleValue());

                    /*TOTAL COSTO*/
                    cell = row.createCell(colNum++);
                    cell.setCellValue(obj.getDetSale().getNumCantidad().doubleValue() * obj.getDetPurchase().getNumValor().doubleValue());
                }

                fila++;
                
            }
             
            /*Descargar desde la web*/
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"Reporte002.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
            
            fc.responseComplete();
            
        }catch(CustomizerException e){
            throw new CustomizerException(e.getMessage());
        }
    }
    
    /**** REPORTE003: DETALLE DE OPERACIONES*****/
    public void downloadReport003() throws CustomizerException, IOException{
        
        try{

            ReportServiceImpl objService = new ReportServiceImpl();
            List<Reporte> list =  objService.getReport003(this.strFecDesde3, this.strFecHasta3);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reporte003");
            XSSFFont  font = workbook.createFont();
            
            CellStyle styleDecimal = workbook.createCellStyle();
            styleDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            
            //STILO PARA LOS TOTALES
            CellStyle styletTotalDecimal = workbook.createCellStyle();
            styletTotalDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            font.setBold(true);
            styletTotalDecimal.setFont(font);
            
            CellStyle styleHeader = workbook.createCellStyle();                        
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short)10);
            font.setBold(true);
            styleHeader.setFont(font);
            
            int colum = 1;
            
            /*Imprimir Periodo*/
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("TIPO OPERACION");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("CLIENTE/PROVEEDOR");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("SERIE");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("NRO. DOCUMENTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("NRO. DOCUMENTO RELACIONADO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("TIPO COMPROBANTE");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("FEC. REGISTRO");    
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("FEC. OPERACION");    
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("FORMA PAGO");    
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("NUMERO VOUCHER");    
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("MTO. EFECTIVO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("MTO. TARJETA");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("MTO. DESCUENTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("SUBTOTAL");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("I.G.V.");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("MTO. TOTAL");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("ESTADO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("NOTAS");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colum++);
            cell.setCellValue("RESPONSABLE");
            cell.setCellStyle(styleHeader);

            int fila = 1;
            for (Reporte obj : list) {
                
                int colNum = 0;
                
                row = sheet.createRow(fila);
                
                //System.out.println("Fila: " + fila + " - ID_DOCU: " + obj.getDetOperacion().getSic1docu().getIdDocu());
                
                /*TIPO OPERACION*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1sclaseeven().getDesSclaseeven());
                
                /*CLIENTE/PROVEEDOR*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persexterno().getDesPers());
                
                /*COD_SERIE*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getCodSerie() != null){                                    
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getCodSerie());
                    cell.setCellStyle(styleHeader);
                }
                
                /*NUM_DOCUMENTO*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumDocu()!=null){                                    
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumDocu().toString());
                    cell.setCellStyle(styleHeader);
                }
                
                /*NUM_DOCUMENTO UNIDO RELACIONADO*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getSic3docudocu().getSic1docurel().getNumDocuunido()!=null)
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic3docudocu().getSic1docurel().getNumDocuunido());
                
                /*TIPO DOCUMENTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1stipodocu().getDesStipodocu());
                
                /*FECHA DE REGISTRO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(UtilClass.convertDateToString(obj.getDetOperacion().getSic1docu().getFecCreacion()));
                
                /*FECHA DE OPERACION*/
                cell = row.createCell(colNum++);
                cell.setCellValue(UtilClass.convertDateToString(obj.getDetOperacion().getSic1docu().getFecDesde()));                
                
                 /*FORMA DE PAGO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getObjFormaPago().getDesGeneral());
                
                /*NUMERO DE VOUCHER*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumVoucher());
                
                /*MTO EFECTIVO*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumMtoefectivo() != null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumMtoefectivo().doubleValue());
                    cell.setCellStyle(styleDecimal);
                }

                /*MTO TARJETA*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumMtotarjeta()!= null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumMtotarjeta().doubleValue());
                    cell.setCellStyle(styleDecimal);
                }
                
                /*MTO DESCUENTO*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumMtodscto() != null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumMtodscto().doubleValue());
                    cell.setCellStyle(styleDecimal);
                }

                /*MTO SUBTOTAL*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumSubtotal()!= null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumSubtotal().doubleValue());
                    cell.setCellStyle(styleDecimal);
                }
                
                /*IGV*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumIgv()!= null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumIgv().doubleValue());
                    cell.setCellStyle(styleDecimal);
                }
                
                /*MONTO TOTAL*/
                cell = row.createCell(colNum++);
                if(obj.getDetOperacion().getSic1docu().getNumIgv()!= null){
                    cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumSubtotal().doubleValue() + obj.getDetOperacion().getSic1docu().getNumIgv().doubleValue());
                    cell.setCellStyle(styletTotalDecimal);
                }

                /*ESTADO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getDesEstadocu());
                
                /*NOTAS*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getDesNotas());
                
                /*RESPONSABLE*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persregistrador().getDesPers());

                fila++;
                
            }
             
            /*Descargar desde la web*/
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"DetalleOperaciones.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
            
            fc.responseComplete();
            
        }catch(CustomizerException e){
            throw new CustomizerException(e.getMessage());
        }
    }
    
    /**** REPORTE004: DETALLE DE CAPITAL SEGUN STOCK PRODUCTOS  *****/
    public void downloadReport004() throws CustomizerException, IOException{
        
        int colNum = 0;
        try{

            ReportServiceImpl objService = new ReportServiceImpl();
            List<Reporte> list =  objService.getReport004(this.strFecDesde, this.strFecHasta);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reporte004");
            XSSFFont  font = workbook.createFont();
            
            CellStyle styleDecimal = workbook.createCellStyle();
            styleDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            
            //STILO PARA LOS TOTALES
            CellStyle styletTotalDecimal = workbook.createCellStyle();
            styletTotalDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            font.setBold(true);
            styletTotalDecimal.setFont(font);
            
            CellStyle styleHeader = workbook.createCellStyle();            
            
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short)10);
            font.setBold(true);
            styleHeader.setFont(font);

            /*Imprimir Periodo*/
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(colNum++);
            cell.setCellValue("CÓDIGO PRODUCTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("DESCRIPCIÓN PRODUCTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("TIPO DE PRODUCTO");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("STOCK ACTUAL");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("PRECIO ACTUAL");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("COSTO POR UNIDAD ACTUAL");
            cell.setCellStyle(styleHeader);
            
            //--ULTIMO INVENTARIO
            cell = row.createCell(colNum++);
            cell.setCellValue("INVENTARIO (FECHA)");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("INVENTARIO (STOCK)");
            cell.setCellStyle(styleHeader);
            
            cell = row.createCell(colNum++);
            cell.setCellValue("INVENTARIO (COSTO)");            
            cell.setCellStyle(styleHeader);
                        
            cell = row.createCell(colNum++);
            cell.setCellValue("INVENTARIO (COSTO TOTAL)");
            cell.setCellStyle(styleHeader);
            
            //--ACTUAL
            cell = row.createCell(colNum++);
            cell.setCellValue("ACTUAL (COSTO TOTAL)");
            cell.setCellStyle(styleHeader);
            
            //-- VENTA
            cell = row.createCell(colNum++);
            cell.setCellValue("VENTA (PRECIO TOTAL)");
            cell.setCellStyle(styleHeader);

            int fila = 1;
            for (Reporte obj : list) {
                
                colNum = 0;
                
                row = sheet.createRow(fila);
                
                //System.out.println("Fila: " + fila + " - ID_PRODUCTO: " + obj.getDetOperacion().getSic1prod().getIdProd());
                
                /*CODIGO PRODUCTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1prod().getCodProd());
                
                /*DESCRIPCION PRODUCTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1prod().getDesProd());
                
                /*SUB TIPO DE PRODUCTO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getDetOperacion().getSic1prod().getSic1stipoprod().getDesGeneral());
                
                /*CANTIDAD STOCK*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getSic1prod().getNumCantstock() != null)
                    cell.setCellValue(obj.getDetOperacion().getSic1prod().getNumCantstock().doubleValue());
                
                /*PRECIO*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getSic1prod().getNumPrecio()!= null)
                    cell.setCellValue(obj.getDetOperacion().getSic1prod().getNumPrecio().doubleValue());
                
                /*COSTO POR UNIDAD*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getNumValor()!= null)
                    cell.setCellValue(obj.getDetOperacion().getNumValor().doubleValue());
                
                //--------------------------------------------
                //--INVENTARIO -------------------------------
                
                /*FECHA*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getId().getFecDesde()!= null)
                    cell.setCellValue(UtilClass.convertDateToString(obj.getDetOperacion().getId().getFecDesde()));
                
                /*CANTIDAD*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getNumCantidad()!= null)
                    cell.setCellValue(obj.getDetOperacion().getNumCantidad().doubleValue());
                
                /*COSTO INVENTARIO*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getNumValor()!= null)
                    cell.setCellValue(obj.getDetOperacion().getNumValor().doubleValue());
                
                
                /*COSTO TOTAL*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getNumCantidad()!= null && obj.getDetOperacion().getNumValor() != null)
                    cell.setCellValue(obj.getDetOperacion().getNumCantidad().doubleValue() * obj.getDetOperacion().getNumValor().doubleValue());
                
                
                //--------------------------------------------
                //--COSTO ACTUAL -----------------------------
                
                /*COSTO TOTAL*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getSic1prod().getNumCantstock() != null && obj.getDetPurchase().getNumValor() != null)
                    cell.setCellValue(obj.getDetOperacion().getSic1prod().getNumCantstock().doubleValue() * obj.getDetPurchase().getNumValor().doubleValue());
                
                //--------------------------------------------
                //-- VENTA -----------------------------
                
                /*PRECIO TOTAL*/
                cell = row.createCell(colNum++);
                if (obj.getDetOperacion().getSic1prod().getNumCantstock() != null && obj.getDetOperacion().getSic1prod().getNumPrecio()!= null)
                    cell.setCellValue(obj.getDetOperacion().getSic1prod().getNumCantstock().doubleValue() * obj.getDetOperacion().getSic1prod().getNumPrecio().doubleValue());


                fila++;
                
            }
             
            /*Descargar desde la web*/
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"Reporte004.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
            
            fc.responseComplete();
            
        }catch(CustomizerException e){
            throw new CustomizerException(e.getMessage());
        }
    }
    
    /*REPORTE CONTABLE: ESTADO DE GANANCIAS Y PERDIDAS*/
    public void downloadReportPerdidaGanancia() throws CustomizerException, IOException{

        String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTFOLDER + "Plantilla_EstadoPerdidasGanancias.xlsx");
        
        try {
            
            if(this.strFecDesde5 == null || this.strFecDesde5.trim().length() == 0 )
                UtilClass.addErrorMessage("Debe especificar la fecha de inicio.");
            else if(this.strFecHasta5 == null || this.strFecHasta5.trim().length() == 0 )
                UtilClass.addErrorMessage("Debe especificar la fecha fin.");
            else {
                
                ReportServiceImpl objService = new ReportServiceImpl();
                List<Reporte> list =  objService.getReport005(this.strFecDesde5, this.strFecHasta5);

                FileInputStream excelFile = new FileInputStream(new File(pathFile));

                XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
                XSSFSheet sheet = workbook.getSheetAt(0);

                CellStyle styleDecimal = workbook.createCellStyle();
                styleDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

                /*Imprimir Periodo*/
                Row row = sheet.getRow(2);
                Cell cell = row.createCell(2);
                cell.setCellValue(this.strFecDesde5);            

                row = sheet.getRow(3);
                cell = row.createCell(2);
                cell.setCellValue(this.strFecHasta5);            
                Double numImpRenta = 0.0;

                int fila = 5;
                for(Reporte obj : list){

                    if(obj.getCodTipo().equals("IMPURENTA")){
                        numImpRenta = obj.getNumMtototal().doubleValue();
                        continue;
                    }

                    row = sheet.getRow(fila);                
                    cell = row.getCell(2);                
                    cell.setCellValue(obj.getDesItem());            

                    row = sheet.getRow(fila);
                    cell = row.getCell(3);
                    cell.setCellValue(obj.getNumMtototal().doubleValue());                

                    fila++;

                }
                /*TOTAL GASTOS FIJO. VARIA.*/
                row = sheet.getRow(22);
                cell = row.getCell(3);
                cell.setCellType(CellType.FORMULA);
                cell.setCellFormula("SUM(D12:D22)");

                /*TOTAL INGRESOS*/
                row = sheet.getRow(23);
                cell = row.getCell(3);
                cell.setCellType(CellType.FORMULA);
                cell.setCellFormula("+D11-D23");            

                /*IMPUESTO A LA RENTA*/
                row = sheet.getRow(24);
                cell = row.getCell(3);
                cell.setCellValue(numImpRenta);     

                /*GANANCIA NETA*/
                row = sheet.getRow(25);
                cell = row.getCell(3);
                cell.setCellType(CellType.FORMULA);
                cell.setCellFormula("+D24-D25");

                /*Descargar desde la web*/
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=\"EstadoGananciasPerdidas.xlsx");
                workbook.write(response.getOutputStream());
                workbook.close();

                //excelFile.close();

                fc.responseComplete();
            }
              
            
        } catch (FileNotFoundException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    /*REPORTE SUNAT: LIBRO DE COMPRAS*/
    public void downloadSunatLibroCompraVenta() throws CustomizerException, IOException{
        
        
        try {
            
            String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTFOLDER + "Plantilla_LibroCompraVenta.xlsx");
            
            ReportServiceImpl objService = new ReportServiceImpl();
            List<Reporte> list =  objService.getReportSunat001(this.strFecDesde4, this.strFecHasta4, this.idPersEmpresa);

            FileInputStream excelFile = new FileInputStream(new File(pathFile));

            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheetAt(0);            
            
            
            CellStyle styleDecimal = workbook.createCellStyle();
            styleDecimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            
            /*Imprimir Periodo*/
            Row row = sheet.getRow(2);
            Cell cell = row.createCell(1);
            cell.setCellValue(this.strFecDesde4);            
            
            cell = row.createCell(3);
            cell.setCellValue(this.strFecHasta4);
            
            int fila = 8;           
            int intContador = 1;
            for (Reporte obj : list) {                
                int colNum = 0;
                
                row = sheet.createRow(fila++);
                
                /*CORRELATIVO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(intContador);
                
                /*FEC. OPERACION*/
                cell = row.createCell(colNum++);
                cell.setCellValue(UtilClass.convertDateToString(obj.getDetOperacion().getSic1docu().getFecDesde()));
                
                /*TIPO TABLA 10 SUNAT*/
                /*Tabla 10 Sunat 1: Factura, 3: Boleta de Venta*/
                String strCodvalor = obj.getDetOperacion().getSic1docu().getSic1stipodocu().getCodStipodocu();
                int intValTipTabl = 0;
                if(strCodvalor.equalsIgnoreCase("VI_SICFACTURA"))
                    intValTipTabl = 1;
                else if(strCodvalor.equalsIgnoreCase("VI_SICBOLETA"))
                    intValTipTabl = 3;
                    
                cell = row.createCell(3);
                cell.setCellValue(intValTipTabl);
                
                /*COD_SERIE*/                
                cell = row.createCell(4);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getCodSerie());
                
                /*CORRELATIVO*/
                cell = row.createCell(6);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumDocu().intValue());
                
                /*TIPO TABLA 2 SUNAT*/
                /*Tabla 2 Sunat 6: RUC, 1: DNI*/
                intValTipTabl = 0;
                strCodvalor = obj.getDetOperacion().getSic1docu().getSic1persexterno().getCodTipoiden();
                if(strCodvalor.equalsIgnoreCase("RUC"))
                    intValTipTabl = 6;
                else if(strCodvalor.equalsIgnoreCase("DNI"))
                    intValTipTabl = 1;

                cell = row.createCell(7);
                cell.setCellValue(intValTipTabl);
                
                /*NUMERO IDENTIDAD*/
                cell = row.createCell(8);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persexterno().getCodIden());
                
                /*NOMBRE PROVEEDOR*/
                cell = row.createCell(9);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persexterno().getDesPers());
                
                /*SUB TOTAL*/
                cell = row.createCell(10);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumSubtotal().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                /*IGV*/
                cell = row.createCell(11);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumIgv().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                /*TOTAL*/
                cell = row.createCell(19);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumMtoTotal().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                intContador++;
            }
            
            /*LIBRO DE VENTAS*/
            objService = new ReportServiceImpl();
            list =  objService.getReportSunat002(this.strFecDesde4, this.strFecHasta4, this.idPersEmpresa);            
           
            sheet = workbook.getSheetAt(1);
            
            /*Imprimir Periodo*/
            row = sheet.getRow(2);
            cell = row.createCell(1);
            cell.setCellValue(this.strFecDesde4);            
            
            cell = row.createCell(3);
            cell.setCellValue(this.strFecHasta4);
            
            fila = 8;
            intContador = 1;
            for (Reporte obj : list) {                
                int colNum = 0;
                
                row = sheet.createRow(fila++);
                
                /*CORRELATIVO*/
                cell = row.createCell(0);
                cell.setCellValue(intContador);
                
                /*FEC. OPERACION*/
                cell = row.createCell(1);
                cell.setCellValue(UtilClass.convertDateToString(obj.getDetOperacion().getSic1docu().getFecDesde()));
                
                /*TIPO TABLA 10 SUNAT*/
                /*Tabla 10 Sunat 1: Factura, 3: Boleta de Venta*/
                String strCodvalor = obj.getDetOperacion().getSic1docu().getSic1stipodocu().getCodStipodocu();
                int intValTipTabl = 0;
                if(strCodvalor.equalsIgnoreCase("VI_SICFACTURA"))
                    intValTipTabl = 1;
                else if(strCodvalor.equalsIgnoreCase("VI_SICBOLETA"))
                    intValTipTabl = 3;
                    
                cell = row.createCell(3);
                cell.setCellValue(intValTipTabl);
                
                /*COD_SERIE*/                
                cell = row.createCell(4);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getCodSerie());
                
                /*CORRELATIVO*/
                cell = row.createCell(5);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumDocu().intValue());
                
                /*TIPO TABLA 2 SUNAT*/
                /*Tabla 2 Sunat 6: RUC, 1: DNI*/
                intValTipTabl = 0;
                strCodvalor = obj.getDetOperacion().getSic1docu().getSic1persexterno().getCodTipoiden();
                if(strCodvalor.equalsIgnoreCase("RUC"))
                    intValTipTabl = 6;
                else if(strCodvalor.equalsIgnoreCase("DNI"))
                    intValTipTabl = 1;

                cell = row.createCell(6);
                cell.setCellValue(intValTipTabl);
                
                /*NUMERO IDENTIDAD*/
                cell = row.createCell(7);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persexterno().getCodIden());
                
                /*NOMBRE PROVEEDOR*/
                cell = row.createCell(8);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getSic1persexterno().getDesPers());
                
                /*SUB TOTAL*/
                cell = row.createCell(10);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumSubtotal().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                /*IGV*/
                cell = row.createCell(14);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumIgv().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                /*TOTAL*/
                cell = row.createCell(16);
                cell.setCellValue(obj.getDetOperacion().getSic1docu().getNumMtoTotal().doubleValue());
                cell.setCellStyle(styleDecimal);
                
                intContador++;
            }
            
            
            /*Descargar desde la web*/
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"LibroCompraVenta.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();            
            
            excelFile.close();
            
            fc.responseComplete();
            
        } catch (FileNotFoundException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void obtDetOperacionesXProductos() throws CustomizerException{
        try{
            
            Sic1docu objDocu = new Sic1docu();
            if(strFecDesde6 != null && strFecDesde6.trim().length() > 0)
                objDocu.setFecDesde(UtilClass.convertStringToDate(strFecDesde6));
            if(strFecHasta6 != null && strFecHasta6.trim().length() > 0)
                objDocu.setFecDesde(UtilClass.convertStringToDate(strFecHasta6));
            
            Sic1prod objProd = new Sic1prod();
            objProd.setCodProd(this.strCodprod);

            Sic3docuprod objDocuprod = new Sic3docuprod();
            objDocuprod.setSic1docu(new Sic1docu());
            objDocuprod.setSic1prod(objProd);

            ReportServiceImpl objService = new ReportServiceImpl();
            this.lstDocuprod = objService.obtDetOperacionesXProductos(objDocuprod);
            
            if(this.lstDocuprod.isEmpty())
                UtilClass.addErrorMessage("No se obtuvo resultados.");
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    /**
     * PERMITE VISUALIZAR EL DETALLE DEL DOCUMENTO
     * @param objDocuProd Envia objeto Documento de tipo SIC3DOCUPROD
     * @return
     * @throws CustomizerException 
     */
//    public String verDetalleComprobante(Sic3docuprod objDocuProd) throws Exception{
//        
//        String desTitulo = "VER DETALLE " + objDocuProd.getSic1docu().getSic1sclaseeven().getDesSclaseeven() + ": " + 
//                                        objDocuProd.getSic1docu().getSic1stipodocu().getDesStipodocu() + " " + 
//                                        objDocuProd.getSic1docu().getCodSerie() + "-" + 
//                                        objDocuProd.getSic1docu().getNumDocu();
//        
//        FacesContext context = FacesContext.getCurrentInstance();        
//        context.getExternalContext().getSessionMap().put("orderController", null);        
//        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
//        
//        boolean flgNuevo            = false;
//        boolean flgEditarProductos  = false;
//        boolean flgEditarPersona    = false;
//        boolean flgEditarFecha      = false;
//        boolean flgEditarFormaPago  = false;
//        boolean flgMostrarFormaPago = true;
//        
//        objController.loadOrderDetails(  objDocuProd.getSic1docu().getIdDocu()
//                                        ,desTitulo
//                                        ,objDocuProd.getSic1docu().getSic1sclaseeven().getCodSclaseeven()
//                                        ,objDocuProd.getSic1docu().getSic3docudocu().getSic1docurel().getIdDocu()
//                                        ,new ArrayList<>()
//                                        ,flgNuevo
//                                        ,flgEditarProductos
//                                        ,flgEditarPersona
//                                        ,flgEditarFecha
//                                        ,flgEditarFormaPago
//                                        ,flgMostrarFormaPago );
//        
//        return "ordenDetalle?faces-redirect=true";
//        
//    }
    
    /**
     * PERMITE VISUALIZAR EL DETALLE DEL DOCUMENTO
     * @param objDocu Envia objeto Documento de tipo SICDOCU
     * @return
     * @throws CustomizerException 
     */
    public String verDetalleComprobante2(Sic1docu objDocu) throws Exception{
        
        String desTitulo = "DETALLE DE " + objDocu.getSic1sclaseeven().getDesSclaseeven() + ": " + 
                                            objDocu.getSic1stipodocu().getDesStipodocu() + " " + 
                                            objDocu.getCodSerie() + "-" + 
                                            objDocu.getNumDocu();
        
        FacesContext context = FacesContext.getCurrentInstance();        
        context.getExternalContext().getSessionMap().put("orderController", null);        
        OrderController objController = context.getApplication().evaluateExpressionGet(context, "#{orderController}", OrderController.class);
        
        objController.loadOrderDetailsForView(   objDocu.getIdDocu()
                                                ,desTitulo
                                                ,objDocu.getSic1sclaseeven().getCodSclaseeven() );
        
        return "ordenDetalle?faces-redirect=true";
        
    }
    
}
