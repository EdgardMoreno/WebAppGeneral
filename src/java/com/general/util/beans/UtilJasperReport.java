/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Edgard
 */
public class UtilJasperReport {
    
    public static ByteArrayOutputStream getOutputStreamFromReport(List list, Map map, String pathJasper) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, dataSource);

        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();
        return os;
    }
    
    public static ByteArrayOutputStream getOutputStreamFromReport(Map map, String pathJasper, Connection cnConexion) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, cnConexion);

        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();
        return os;
    }

    public static StreamedContent getStreamContentFromOutputStream(ByteArrayOutputStream os, String contentType, String nameFile) throws Exception {
        StreamedContent file = null;
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        file = new DefaultStreamedContent(is, contentType, nameFile);
        return file;
    }

    public static StreamedContent getStreamContentReport(List list, Map map, String pathJasper, String nameFilePdf) throws Exception {
        StreamedContent pdf = null;
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, dataSource);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        pdf = new DefaultStreamedContent(is, "application/pdf", nameFilePdf);
        return pdf;
    }
    
}
