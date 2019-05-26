/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.a2.service.impl.ReportServiceImpl;
import java.util.List;

/**
 *
 * @author emoreno
 */
public class MainReportes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         try {
            
            
            ReportServiceImpl objService = new ReportServiceImpl();
            List<Reporte> list =  objService.getReportSunat001("01/02/2018", "28/02/2018",2);
            System.out.println("");
            
         }catch(Exception e){
         }
        
    }
    
}
