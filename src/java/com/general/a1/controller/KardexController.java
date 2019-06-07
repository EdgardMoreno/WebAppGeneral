/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuKardexServiceImpl;

import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.entity.Sic1docukardex;
import com.general.hibernate.entity.Sic1docukardexId;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.Reporte;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author emoreno
 */
@ManagedBean
@SessionScoped
public class KardexController implements Serializable{
    
    
    private static final int FILA_INI_EXCEL = 5;
    private Part uploadFile;
    private List<Sic1docukardex> lstUploadKardex;
    private String period;
    private String desTituloPagina;
    
    /*Pantalla Reporte KARDEX RESUMEN*/
    private String strFecDesde;
    private String strFecHasta;
    private String strCodprod;
    private List<Reporte> lstKardexResumen;

    
    
    public KardexController() throws CustomizerException{
        lstUploadKardex = new ArrayList<>();
        
        try{
        
            DocuKardexServiceImpl service = new DocuKardexServiceImpl();
            int numPeri = service.getKardexLastPeriActi();
            if ( numPeri < 0){
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                this.period = df.format(new Date());
            }else
                this.period = String.valueOf(numPeri);
        
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
    }
    
    /*PROPIEDADES*/
    public Part getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(Part uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<Sic1docukardex> getLstUploadKardex() {
        return lstUploadKardex;
    }

    public void setLstUploadKardex(List<Sic1docukardex> lstUploadKardex) {
        this.lstUploadKardex = lstUploadKardex;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getStrCodprod() {
        return strCodprod;
    }

    public void setStrCodprod(String strCodprod) {
        this.strCodprod = strCodprod;
    }

    public List<Reporte> getLstKardexResumen() {
        return lstKardexResumen;
    }

    public void setLstKardexResumen(List<Reporte> lstKardexResumen) {
        this.lstKardexResumen = lstKardexResumen;
    }

    public String getDesTituloPagina() {
        return desTituloPagina;
    }

    public void setDesTituloPagina(String desTituloPagina) {
        this.desTituloPagina = desTituloPagina;
    }
    
    
        
    /*METODOS*/    
    public void triggerChangedFile() {
        lstUploadKardex.clear();        
    }
    
    public void downloadTemplate() throws IOException, CustomizerException {
        
        try {
            
            String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTFOLDER + "Plantilla_Kardex.xlsx");
            
            DocuKardexServiceImpl service = new DocuKardexServiceImpl();            
            List<Sic1docukardex> listKardex = service.getKardexByNumPeri(Integer.valueOf(this.period));

            FileInputStream excelFile = new FileInputStream(new File(pathFile));        

            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            /*Estilo para Desbloquear Celda*/
            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);
            
            /*Imprimir Periodo*/
            Row row = sheet.getRow(2);
            Cell cell = row.createCell(2);
            cell.setCellValue(this.period);
            
            int fila = FILA_INI_EXCEL;
            for (Sic1docukardex obj : listKardex) {
                int colNum = 0;
                
                row = sheet.createRow(fila++);
                
                /*ID_PROD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getIdProd().intValue());
                
                /*COD_PROD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getCodProd());
                
                /*DES_PROD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getDesProd());
                
                /*DES_STIPOPROD*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getSic1stipoprod().getDesGeneral());
                
                /*CANTIDAD INICIAL*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantini().intValue());
                
                /*CANTIDAD INGRESADA*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantingr().intValue());
                
                /*CANTIDAD SALIENTE*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantsali().intValue());
                
                /*CANTIDAD STOCK*/
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantstock().intValue());
                
                /*STOCK FISICO*/
                cell = row.createCell(colNum++);
                cell.setCellValue(0);
                //Solo se deja desbloqueado esta celda
                cell.setCellStyle(unlockedCellStyle);
                
            }
            
            /*Proteger Archivo*/            
            sheet.protectSheet("Oracle01");
            
//            filaExcelIni = 6;
//            for (Sic1docukardex obj : listKardex) {                
//                Row rowBloq = sheet.getRow(filaExcelIni++);
//                for (int i=0; i<4; i++){
//                    Cell cellBloq = rowBloq.getCell(i);
//                    cellBloq.setCellStyle(unlockedCellStyle);
//                }
//            }

            /*Descargar directamente en un archivo local*/
            /*FileOutputStream outputStream = new FileOutputStream("Resultado.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();*/
            
            /*Descargar desde la web*/
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"Inventario.xlsx");
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
    
    public void loadFile() throws CustomizerException{
        
        int fila = -1;
        int columna = -1;
        
        try {
            
            lstUploadKardex.clear();
            Sic1docukardex kardex; 
            Sic1docukardexId kardexId;
            Sic1prod objProd; 
            
            if (uploadFile == null)
                throw new ValidationException("Se debe cargar un archivo.");
                
            
            XSSFWorkbook workbook = new XSSFWorkbook(uploadFile.getInputStream());
            
            if (workbook.getNumberOfSheets() < 2)
                throw new ValidationException("Archivo cargado no es el original.");
            
            XSSFSheet sheet = workbook.getSheetAt(1);
            
            if (!sheet.getSheetName().equalsIgnoreCase("PLANTILLA"))    
                throw new ValidationException("Archivo cargado no es el original.");
            
            sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                fila = nextRow.getRowNum();
                
                if ( nextRow.getRowNum() >= FILA_INI_EXCEL  ){
                    
                    columna = 0;
                    
                    
                    kardex = new Sic1docukardex();
                    objProd = new Sic1prod();
                    kardexId = new Sic1docukardexId();
                    
                    Cell cell = nextRow.getCell(columna++);
                    System.out.print("ID_PROD: " + cell.getNumericCellValue());
                    objProd.setIdProd(new BigDecimal(cell.getNumericCellValue()));
                    
                    //System.out.println("COD_PROD:" + cell.getStringCellValue());
                    cell = nextRow.getCell(columna++);                    
                    if (CellType.NUMERIC == cell.getCellTypeEnum()){
                        objProd.setCodProd(String.valueOf(cell.getNumericCellValue()));
                    }else{
                        objProd.setCodProd(cell.getStringCellValue());
                    }
                    
                    cell = nextRow.getCell(columna++);
                    System.out.print("- DES_PROD:" + cell.getStringCellValue());
                    objProd.setDesProd(cell.getStringCellValue());
                    
                    /*DES_STIPOPROD*/                    
                    Sic1general objStipoprod = new Sic1general();
                    objStipoprod.setDesGeneral(cell.getStringCellValue());
                    cell = nextRow.getCell(columna++);
                    objProd.setSic1stipoprod(objStipoprod);
                    
                    //System.out.print("- Periodo Actual: " + UtilClass.getCurrentTime_YYYYMMDD());
                    kardexId.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));
                    kardexId.setIdProd(objProd.getIdProd());
                    
                    /*Seteando parte de la llave*/
                    kardex.setId(kardexId);
                    kardex.setSic1prod(objProd);
                    /**/
                    
                    
                    cell = nextRow.getCell(columna++);
                    //System.out.println("CANTIDAD INICIAL:" + cell.getNumericCellValue());
                    kardex.setNumCantini(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    //System.out.println("CANTIDAD INGRESA:" + cell.getNumericCellValue());
                    kardex.setNumCantingr(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    //System.out.println("CANTIDAD SALIENTE:" + cell.getNumericCellValue());
                    kardex.setNumCantsali(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    //System.out.println("STOCK:" + cell.getNumericCellValue());
                    kardex.setNumCantstock(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    //System.out.println("STOCK INGRESADO:" + cell.getNumericCellValue());
                    kardex.setNumCantstockusu(new BigDecimal(cell.getNumericCellValue()));
                    
                    lstUploadKardex.add(kardex);
                    
                    System.out.println("");
                }
            }
            
            workbook.close();
            
        } catch (ValidationException ex) {
            UtilClass.addErrorMessage(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex) {
            UtilClass.addErrorMessage("Error al cargar Excel: Fila(" + fila + ") Colum(" + (columna - 1) + ") - " +  ex.getMessage());
        }
    }
    
    public void save() throws CustomizerException{
        
        try{
            
            if (uploadFile == null)
                throw new ValidationException("Se debe cargar un archivo.");
            if (lstUploadKardex.isEmpty())
                throw new ValidationException("Se debe cargar el inventario");
            
            /*Se valida que el stock ingresado por el usuario cuadre con el Stock que muestra el sistema*/
            int filaError = 0;
            for(Sic1docukardex obj : lstUploadKardex){
                
                BigDecimal idProd = obj.getSic1prod().getIdProd();
                
                lstUploadKardex.get(filaError).getId().setNumItem(new BigDecimal(this.period));
                lstUploadKardex.get(filaError).getId().setIdProd(idProd);
                lstUploadKardex.get(filaError).setIdSucursal(SessionUtils.getIdSucursal());

                if(obj.getNumCantstock().intValue() != obj.getNumCantstockusu().intValue()){
                    String mensaje = "Error en fila " + (filaError + 1) + ": el stock ingresado no coincide.";
                    throw new ValidationException(mensaje);
                }
                filaError++;
            }
            
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Sic1idendocu sic1idendocu = new Sic1idendocu();
            sic1idendocu.setCodIden(df.format(new Date()));

            Sic1docu sic1docu = new Sic1docu();
            sic1docu.setDesDocu("Control de Inventario. Periodo: " + this.period);
            sic1docu.setIdPers(SessionUtils.getUserId()); //Login
            
            Sic1sclaseeven objSclaseeven = new Sic1sclaseeven();
            objSclaseeven.setCodSclaseeven(Constantes.COD_SCLASEEVEN_CTRL_INVENTARIO);            
            sic1docu.setSic1sclaseeven(objSclaseeven);
            //sic1docu.setCodSclaseeven(Constantes.CONS_COD_SCLASEEVEN_CTRL_INVENTARIO);
            
            sic1idendocu.setSic1docu(sic1docu);
            
            DocuKardexServiceImpl service = new DocuKardexServiceImpl();            
            service.saveKardex(sic1idendocu, lstUploadKardex);  
            
            /*Despues de Grabar*/
            lstUploadKardex.clear();
            UtilClass.addInfoMessage(Constantes.CONS_SUCCESS_MESSAGE);
            uploadFile = null;

            
        }catch (ValidationException ex ){
            UtilClass.addErrorMessage(ex.getMessage());
        }catch (Exception ex ){
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    public void generarRptKardexResumen() throws CustomizerException{
     
        try{
            
            Sic1docu objDocu = new Sic1docu();
            if(strFecDesde != null && strFecDesde.trim().length() > 0)
                objDocu.setFecDesde(UtilClass.convertStringToDate(strFecDesde));
            if(strFecHasta != null && strFecHasta.trim().length() > 0)
                objDocu.setFecDesde(UtilClass.convertStringToDate(strFecHasta));
            
            Sic1prod objProd = new Sic1prod();
            objProd.setCodProd(this.strCodprod);

            Sic3docuprod objDocuprod = new Sic3docuprod();
            objDocuprod.setSic1docu(new Sic1docu());
            objDocuprod.setSic1prod(objProd);

            DocuKardexServiceImpl objService = new DocuKardexServiceImpl();
            this.lstKardexResumen = objService.obtKardexResumen(objDocuprod);
            
            if(this.lstKardexResumen.isEmpty())
                UtilClass.addErrorMessage("No se obtuvo resultados.");

        }catch (Exception ex ){
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    public String obtMovimientosProducto(String codProd) throws CustomizerException{
        
        String strDesTituloPagina = "Movimientos del Producto: " + codProd;
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("reportController", null);
        
        ReportController objController = context.getApplication().evaluateExpressionGet(context, "#{reportController}", ReportController.class);
        objController.setDesTituloPagina(strDesTituloPagina);
        objController.setStrCodprod(codProd);
        objController.obtDetOperacionesXProductos();
        
         return "faces/rptOperacionesPorProducto?faces-redirect=true";
    }
            
}
