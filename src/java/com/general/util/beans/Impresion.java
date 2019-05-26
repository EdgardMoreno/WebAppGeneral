/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import com.general.a2.service.impl.DocuOrderServiceImpl;
import com.general.a2.service.impl.MaestroCatalogoServiceImpl;
import com.general.a3.dao.impl.DaoMaestroCatalogoImpl;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1lugar;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.util.dao.ConexionBD;
import com.general.util.dao.DaoFuncionesUtil;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.primefaces.model.StreamedContent;



/**
 *
 * @author Edgard
 */
public class Impresion {
    
    /**
     * IMPRIME COMPROBANTE DE PAGO EN IMPRESORA POS
     * @param idDocu Indica el identificador del documento
     * @throws Exception 
     */
    public void imprimirComprobantePagoArchivoTexto(BigDecimal idDocu) throws Exception {
        
        DocuOrderServiceImpl orderService = new DocuOrderServiceImpl();
        Connection cnConexion = null;
        
        try{
            
            /*Se obtiene los datos de la orden*/
            Sic1idendocu sic1idendocu = orderService.getOrderByIdDocu(idDocu);
            
            Sic1docu objDocu = sic1idendocu.getSic1docu();
            Sic1lugar objSucursal = objDocu.getSic1lSucursal();

            PrinterMatrix printer = new PrinterMatrix();
            Extenso e = new Extenso();
            e.setNumber(101.85);
                       
            /*Se calcula para determinar el tamaño del voucher dependiendo de los productos que tenga la orden*/
            Integer numTamanioFilasVoucher = 27; //Como minimo tendra 31 filas
            for (Sic3docuprod objProd : objDocu.getLstSic3docuprod()){
                numTamanioFilasVoucher++;
            }

            //Definir el tamaño del papel para la impresion de 25 lineas y 48 columnas
            printer.setOutSize(numTamanioFilasVoucher, 48);
            //Imprimir * de la 2da linea a 25 en la columna 1;
            // printer.printCharAtLin(2, 25, 1, "*");
            //Imprimir * 1ra linea de la columa de 1 a 48
           
            //Imprimir Encabezado nombre del La EMpresa
            //printer.printTextWrap(linI, linE, colI, colE, null);
            printer.printTextWrap(1, 1, 18, 48, Constantes.CONS_DES_NOMB_COMERCIAL);
            printer.printTextWrap(2, 2, 1,  48, "RUC:" + Constantes.CONS_NUM_RUC + " - "  + Constantes.CONS_DES_RAZON_SOCIAL );
            printer.printTextWrap(3, 3, 1,  48, objSucursal.getDesDireccion());
            
            String direccion = "";
            if(objDocu.getSic1persexterno().getDesDireccion()!= null)
                direccion = objDocu.getSic1persexterno().getDesDireccion();

            printer.printTextWrap(4, 4, 1,  48, objDocu.getSic1stipodocu().getDesStipodocu() + " DE " + objDocu.getSic1sclaseeven().getDesSclaseeven() + " ELECTRONICA");
            printer.printTextWrap(5, 5, 1, 48, "SERIE: " + objDocu.getCodSerie() + " - CORRELATIVO: " + objDocu.getNumDocu());
            printer.printTextWrap(6, 6, 1, 48, "CLIENTE: " + objDocu.getSic1persexterno().getDesPers());
            
            if(objDocu.getSic1stipodocu().getCodStipodocu().equals("VI_SICFACTURA"))
                printer.printTextWrap(7, 7, 1, 48, "RUC: " + objDocu.getSic1persexterno().getCodIden());
            else
                printer.printTextWrap(7, 7, 1, 48, "DNI: " + objDocu.getSic1persexterno().getCodIden());
            
            printer.printTextWrap(8, 8, 1, 48, "DIRECCION: " + direccion);

            //printer.printCharAtCol(11, 1, 48, "=");
            printer.printTextWrap(9,  9,  1, 48, "===============================================");
            printer.printTextWrap(10, 10, 1, 48, "Codigo      Descripcion          Cant.  P.Unit.");
            printer.printTextWrap(11, 11, 1, 48, "-----------------------------------------------");
            //printer.printCharAtCol(13, 1, 48, "-");
            int fila = 11;
            int i = 0;

            System.out.println("== Imprimiendo el detalle de los productos ==");
            for (Sic3docuprod objProd : objDocu.getLstSic3docuprod()){            

                System.out.println("CodProd:" + objProd.getSic1prod().getCodProdint()+ " Cantidad: " + objProd.getNumCantidad() + " - S/. " + objProd.getSic1prod().getNumPrecio());
                
                String codProd = "";
                if(objProd.getSic1prod().getCodProdint() != null)
                    codProd = objProd.getSic1prod().getCodProdint();
                else 
                    codProd = objProd.getSic1prod().getCodProd();
                
                String desProd = "";
                if(objProd.getSic1prod().getDesProd() != null)                                        
                    desProd = objProd.getSic1prod().getDesProd();
                
                /*Formateando el tamaño se de mostrara en el voucher*/
                if(desProd.length() > 20 )
                    desProd = desProd.substring(0, 20);
                
                
                fila++;
                printer.printTextWrap(fila, 10,  1, 48, codProd);
                printer.printTextWrap(fila, 10, 13, 48, desProd);
                printer.printTextWrap(fila, 10, 35, 48, objProd.getNumCantidad().toString());
                printer.printTextWrap(fila, 10, 41, 48, objProd.getNumValor().toString());
                i++;

            }            

            fila++;            
            printer.printTextWrap(fila, fila, 1, 48, "===============================================");
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "OP. GRAVADA:");
            printer.printTextWrap(fila, fila, 35, 48, objDocu.getNumSubtotal().toString());
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "I.G.V:");
            printer.printTextWrap(fila, fila, 35, 48, objDocu.getNumIgv().toString());
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "IMPORTE TOTAL:");
            printer.printTextWrap(fila, fila, 35, 48, objDocu.getNumMtoTotal().toString() );      

            fila++;
            //printer.printCharAtCol(24, 1, 48, "=");
            printer.printTextWrap(fila, fila, 1, 48, "===============================================");
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "F. Emision: " + UtilClass.convertDateToString(objDocu.getFecCreacion()));
            printer.printTextWrap(fila, fila, 30, 48, "Hora: " + UtilClass.convertDateToStringHHMMSS(objDocu.getFecCreacion()));
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "Vendedor: " + objDocu.getSic1persregistrador().getDesPers());
            
            //IMPRIMIENDO EL TOTAL EN LETRAS
            cnConexion       = ConexionBD.obtConexion();
            String TotalEnLetras = DaoFuncionesUtil.FNC_SICCONVNROLETRAFINAL(cnConexion, objDocu.getNumMtoTotal());
            
            System.out.println("TotalEnLetras:" + TotalEnLetras );
            fila++;
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "SON: " + TotalEnLetras);
            
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, "Representacion impresa del Comprobante Electronico.");
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, " ");
            fila++;
            printer.printTextWrap(fila, fila, 1, 48, " ");
            //printer.printCharAtCol(28, 1, 48, "=");
            //printer.printCharAtCol(24, 1, 48, "=");

            
            printer.toFile("impresion.txt");

            FileInputStream inputStream = new FileInputStream("impresion.txt");
           
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            byte[] bytes = {27, 100, 3};
            Doc document = new SimpleDoc(inputStream, docFormat, null);

            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                
                printJob.print(document, attributeSet);

                //CODIGO PARA CORTAR
                DocPrintJob job2 = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
                byte[] bytes2 = {27, 109, 1};
                DocFlavor flavor2 = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc2 = new SimpleDoc(bytes2, flavor2, null);
                job2.print(doc2, null);
                
            } else {
                System.err.println("No existen impresoras instaladas");
            }
         
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(ex.getMessage());            
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        } finally{
            if(cnConexion != null)
                cnConexion.close();
        }       
     
    }
    
    /**
     * METODO PARA IMPRIMIR VOUCHER DE VENTA
     * @param idDocu Indica el identificador del comprobante de pago
     * @throws Exception 
     */
    public void imprimirVoucherVentaJasper(Integer idDocu) throws Exception {
        
        Connection cnConexion = null;
        
        try{
            
            String nombreImpresora = MaestroCatalogoServiceImpl.obtenerNombreImpresoraVoucher();
                    
            if(nombreImpresora == null)
                throw new Exception("La impresora no ha sido configurada.");
            
//            String pathFile = "C:\\ARCHIVOS\\PRE_PRODUCCION\\PROYECTOS_DESARROLLO_SOFTWARE\\APLICACIONES\\WEBAPPGENERAL\\WebAppGeneral\\web\\reportesjasper\\RptVoucher.jasper";
//            String logo_path = "C:\\ARCHIVOS\\PRE_PRODUCCION\\PROYECTOS_DESARROLLO_SOFTWARE\\APLICACIONES\\WEBAPPGENERAL\\WebAppGeneral\\web\\resources\\img\\logo.png";
            String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTJASPERFOLDER + "RptVoucher.jasper");
            String rutaLogo = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_LOGO_PNG);
            
            JasperReport reporte = (JasperReport)JRLoader.loadObjectFromFile(pathFile);
            Map parametro = new HashMap();
            parametro.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);//Permite que el separador decimal sea el Punto(.) y no la Coma(,). En Jasper Studio, en el XML, se debe agregar al campo "textField" la palabra "pattern", quedaria asi: <textField pattern="#,##0.00">
            parametro.put("paramIdDocu", idDocu);
            parametro.put("paramRutaLogo", rutaLogo);
            
            cnConexion          = ConexionBD.obtConexion();            
            JasperPrint jPrint  = JasperFillManager.fillReport (reporte, parametro, cnConexion);
            /*Para visualizar los documentos desde la clase main*/
            //JasperViewer.viewReport(jPrint, false);

            /*Se obtiene todas las impresoras disponibles*/
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService selectedService = null;

            /*Se busca la impresora que se ha configurado para imprimir*/
            if(services != null && services.length != 0) {
                for(PrintService service : services){
                    String existingPrinter = service.getName().toLowerCase();
                    //System.out.println("existingPrinter:" + existingPrinter);
                    if(existingPrinter.equalsIgnoreCase(nombreImpresora)){
                        selectedService = service;
                        break;
                    }
                }

                if(selectedService != null){
                    printerJob.setPrintService(selectedService);
                    boolean printSucceed = JasperPrintManager.printReport(jPrint, false);
                    if(!printSucceed)
                        throw new Exception("Error al imprimir.");
                }
            }            
          
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        } finally{
            if(cnConexion != null)
                cnConexion.close();
        }        
    }
    
    /**
     * METODO PARA DESCARGAR EL COMPROBANTE DE PAGO HECHO EN JASPER REPORT EN FORMATO PDF
     * @param idDocu Indica el identificador del Comprobante de Pago
     * @param numDocu Indica el numero de comprobante EJEM: F001-2048
     * @throws Exception 
     */
    public void descargarComprobantePDFJasper(Integer idDocu, String numDocu) throws Exception {
        
        Connection cnConexion = null;
        
        try{
            
            //String pathFile = "C:\\Users\\Edgard\\JaspersoftWorkspace\\SicAppReportes\\RptVoucher.jasper";            
            String pathFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constantes.CONS_RUTA_RPTJASPERFOLDER + "RptComprobante.jasper");            
            
            JasperReport reporte = (JasperReport)JRLoader.loadObjectFromFile(pathFile);
            Map parametro = new HashMap();
            parametro.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);//Permite que el separador decimal sea el Punto(.) y no la Coma(,). En Jasper Studio, en el XML, se debe agregar al campo "textField" la palabra "pattern", quedaria asi: <textField pattern="#,##0.00">
            parametro.put("paramIdDocu", idDocu);            
            
            cnConexion       = ConexionBD.obtConexion();            
            JasperPrint jPrint = JasperFillManager.fillReport (reporte, parametro, cnConexion);
            
            /*Para visualizar los documentos desde la clase main*/
            //JasperViewer.viewReport(jPrint, false);
            
            String nameFilePDF = "Comprobante." + numDocu + ".pdf";

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + nameFilePDF  );
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jPrint, stream);
            facesContext.responseComplete();            

          
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        } finally{
            if(cnConexion != null)
                cnConexion.close();
        }        
    }

}
