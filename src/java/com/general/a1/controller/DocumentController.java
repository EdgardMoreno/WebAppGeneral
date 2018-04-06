/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.DocuOrderServiceImpl;
import com.general.a2.service.impl.Sic1generalServiceImpl;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.util.beans.Constantes;
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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
@ViewScoped
public class DocumentController implements Serializable{
    
    private static final String FILE_NAME = "C:\\ARCHIVOS\\ReporteOperaciones.xlsx";
    private static final int FILA_INI_EXCEL = 6;
    private final static Logger log = LoggerFactory.getLogger(DocumentController.class);    
    private DocuOrderServiceImpl documentServiceImpl;
    private List<SelectItem> itemSTipoDocu  = new ArrayList();
    private List<ViSicestageneral> itemsEstaRol  = new ArrayList();
    private ViSicdocu viSicdocu; 
    private List<ViSicdocu> lstViSicdocus;
    private String desFecDesde;
    private String desFecHasta;
    
    private String desTituloPagina;
    private String codTRolpers;
    private String codSClaseeven;
    
    private BigDecimal idDocuSelected;
    
    public void DocumentController(){
        System.out.println("Aqui");
    }
    
    @PostConstruct
    public void init() {
        
        try{
            
            documentServiceImpl = new DocuOrderServiceImpl();
            viSicdocu           = new ViSicdocu();
            lstViSicdocus       = new ArrayList();
            
            /*desFecDesde         = UtilClass.getCurrentDay();
            desFecHasta         = UtilClass.getCurrentDay();*/
            
        
            /*Cargar Catalogo: STIPODOCU*/
            Sic1generalServiceImpl sic1generalServiceImpl = new Sic1generalServiceImpl();
            List<Sic1stipodocu> lstResult = sic1generalServiceImpl.getCataDocumentsType();

            SelectItem si;
            for(Sic1stipodocu obj : lstResult){
                si = new SelectItem();
                si.setLabel(obj.getDesStipodocu());
                si.setValue(obj.getIdStipodocu());
                this.itemSTipoDocu.add(si);
            }

            /*Cargar Catalogo: STIPODOCU*/
            this.itemsEstaRol = sic1generalServiceImpl.getCataEstaRolDocuInf();
            
            
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());
        }
            
    }
    
    /*PROPIEDADES*/    
    
    public List<SelectItem> getItemSTipoDocu() {
        return itemSTipoDocu;
    }

    public void setItemSTipoDocu(List<SelectItem> itemSTipoDocu) {
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

            this.lstViSicdocus = documentServiceImpl.listViSicdocu(viSicdocu);  
            
        } catch (Exception e) {
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
    
    public String editAction(ViSicdocu obj ) throws ServletException, IOException{
        
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.clear();
        flash.put("paramIdDocu", obj.getIdDocu());
        flash.put("paramTituloPagina", "VER DETALLE " + obj.getDesSclaseeven() + ": " + obj.getDesStipodocu() + " " + obj.getCodSerie() + "-" + obj.getNumDocu());
        flash.put("paramCodTRolpers", this.codTRolpers );
        flash.put("paramCodSClaseeven", obj.getCodSclaseeven());
        
        //flash.setKeepMessages(true);
        
        return "ordenDetalle?faces-redirect=true";
    }
    
    /*Metodo para anular un documento*/
    public void anulDocu() throws CustomizerException{        
        try{            
            System.out.println("id_docu:" + this.getIdDocuSelected());
            documentServiceImpl.relateDocuEsta(this.getIdDocuSelected()
                                               ,Constantes.CONS_COD_ESTADOCU_COMPROBANTE
                                               ,Constantes.CONS_COD_ESTAANULADO);
            this.filterDocuments();

        } catch (Exception e) {
            throw new CustomizerException(e.getMessage());
        }
    }
    
    
    /*Metodo para generar reporte*/
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
                }

                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));        

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
                        cell.setCellValue(Double.valueOf(obj.getNumMtototal().replace(",", ".")));

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
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }
    }
    
    
    /*Metodo que es invocado desde la pagina xhtml: <f:metadata>*/
    public void getParamsExternalPage(ComponentSystemEvent event) throws CustomizerException{
        
        if(!FacesContext.getCurrentInstance().isPostback()){
            
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            String tituloPagina     = (String)flash.get("paramTituloPagina"); 
            String codSClaseeven    = (String)flash.get("paramCodSClaseeven");
            String codTRolpersTmp      = (String)flash.get("paramCodTRolpers");
            
            /*Codigo que determinara si la operacion es una COMPRA O VENTA*/
            if (codSClaseeven != null){
                this.viSicdocu.setCodSclaseeven(codSClaseeven);
                this.codSClaseeven = codSClaseeven;
            }
            else
                throw new CustomizerException("No se cargo la sub clase del evento.");
            
            
             /*Esto permite que cuando se registra un nueva persona, se guarde con el rol 
               de CLIENTE O PROVEEDOR*/
            if (codTRolpersTmp != null)
                this.codTRolpers = codTRolpersTmp;
            else
                throw new CustomizerException("No se cargo el Tipo de Rol de la persona.");            
            
            this.desTituloPagina = tituloPagina;        
        }
    }

      public void loadSpendReport(ComponentSystemEvent event) throws CustomizerException{
          
          if(!FacesContext.getCurrentInstance().isPostback()){
              
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();        
            String tituloPagina    = (String)flash.get("paramTituloPagina"); 
            String codClaseeven    = (String)flash.get("paramCodClaseeven");
            
            this.desTituloPagina = tituloPagina;
            
            /*Codigo que determinara si la operacion es una COMPRA O VENTA*/
            if (codClaseeven != null){
                this.viSicdocu.setCodClaseeven(codClaseeven);                
            }
            else
                throw new CustomizerException("No se cargo la clase del evento.");
          }
      }
    
}
