/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuKardexServiceImpl;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate1.Sic1docukardex;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.StreamedContent;


/**
 *
 * @author emoreno
 */
@ManagedBean
@ViewScoped
public class KardexController {
    
    private static final String FILE_NAME = "C:\\plantKardex.xlsx";
    private Part uploadFile;
    private List<Sic1docukardex> lstUploadKardex;

    public KardexController(){
        lstUploadKardex = new ArrayList<>();
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
    
    
    
    
    /*METODOS*/
    public void downloadTemplate() throws IOException, CustomizerException {
        
        try {
            
            DocuKardexServiceImpl service = new DocuKardexServiceImpl();
            List<Sic1docukardex> listKardex = service.getKardexByNumPeri(201705);

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));        

            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            /*Imprimir Periodo*/
            Row row = sheet.getRow(2);
            Cell cell = row.createCell(1);
            cell.setCellValue(201705);
            
            int filaExcelIni = 6;
            for (Sic1docukardex obj : listKardex) {
                int colNum = 0;
                
                row = sheet.createRow(filaExcelIni++);                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getCodProd());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getDesProd());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getSic1prod().getDesStipoprod());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantini().intValue());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantingr().intValue());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantsali().intValue());
                
                cell = row.createCell(colNum++);
                cell.setCellValue(obj.getNumCantstock().intValue());
                
            }
            
            

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
            Sic1prod prod; 
            
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
                
                if ( nextRow.getRowNum() > 7 ){
                    
                    columna = 0;
                    
                    kardex = new Sic1docukardex();
                    prod = new Sic1prod();
                    
                    Cell cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getStringCellValue());
                    prod.setCodProd(cell.getStringCellValue());
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getStringCellValue());
                    prod.setDesProd(cell.getStringCellValue());
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Tipo Prod:" + cell.getStringCellValue());
                    prod.setDesStipoprod(cell.getStringCellValue());
                    
                    kardex.setSic1prod(prod);
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getNumericCellValue());
                    kardex.setNumCantini(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getNumericCellValue());
                    kardex.setNumCantingr(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getNumericCellValue());
                    kardex.setNumCantsali(new BigDecimal(cell.getNumericCellValue()));
                    
                    cell = nextRow.getCell(columna++);
                    System.out.println("Valor:" + cell.getNumericCellValue());
                    kardex.setNumCantstock(new BigDecimal(cell.getNumericCellValue()));
                    
                    lstUploadKardex.add(kardex);
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
    
    public void save(){
        
    }
}
