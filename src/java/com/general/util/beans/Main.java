/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.a1.controller.KardexController;
import com.general.a1.controller.PersonController;
import com.general.a2.service.impl.CashRegisterServiceImpl;

import com.general.a3.dao.impl.DaoPersonImpl;
import com.general.a3.dao.impl.DaoProductImpl;

import com.general.hibernate.entity.HibernateUtil;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1pers;
import com.general.a2.service.impl.ReportServiceImpl;
import com.general.a3.dao.impl.DaoCashRegisterImpl;
import com.general.a3.dao.impl.DaoDocuKardexImpl;
import com.general.a3.dao.impl.DaoDocumentImpl;
import com.general.a3.dao.impl.DaoLoginImpl;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1persindi;
import com.general.hibernate.entity.Sic1persorga;

import com.general.hibernate.views.ViSicpers;
import com.general.hibernate.views.ViSicprod;
import com.general.hibernate.entity.Sic1usuario;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate.views.ViSiccuaddiario;
import com.general.hibernate.views.ViSicdocu;
import com.general.util.dao.ConexionBD;
import com.general.util.dao.DaoFuncionesUtil;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author emoreno
 */
public class Main {

    
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        
        log.debug("Holaaaa");
        log.error("Error");
        System.out.println(String.format("%03d", 10));
        
        String codProd = "/ysvg/";
        System.out.println("Codigo: " + codProd.substring(0, 1));
        System.out.println("Codigo: " + codProd.substring(1));
        
        if(true){
            DaoDocumentImpl objDao = new DaoDocumentImpl();
            ViSicdocu objVi = new ViSicdocu();
            objVi.setNumDocuunido("f001-4");
            objVi.setCodEsta(Constantes.CONS_COD_ESTAFINALIZADO);
            objDao.listViSicdocu(objVi);
            System.out.println("");
                
        }
        
        if(false){
            Impresion pr = new Impresion();
            pr.descargarComprobantePDFJasper(1254,"F001-225");
            System.out.println("fin");
        }
        
        if(false){
            Impresion pr = new Impresion();
            pr.imprimirVoucherVentaJasper(2598);
            System.out.println("fin");
        }
      
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        if(false){
            DaoCashRegisterImpl objs = new DaoCashRegisterImpl();
            objs.listarCierresDiarios("04/05/2019", "04/05/2019");
        }
        
        
        if(false){
            Connection cnConexion       = ConexionBD.obtConexion();
            String letras = DaoFuncionesUtil.FNC_SICCONVNROLETRAFINAL(cnConexion, new BigDecimal(74.06));
            System.out.println("letras:" + letras );
        }
                
        
        if(true){
            String[] arrCodigos = new String[2];
            arrCodigos[0] = "'VI_SICFACTURA'";
            arrCodigos[1] = "'VI_SICBOLETA'";
            System.out.println(String.join("," , arrCodigos ));
            System.out.println("");
//            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
//            objDao.obtComprobantesPagoXCodigos(arrCodigos);            
            
            
        }        
        
        if(false){
            Impresion pr = new Impresion();
            pr.imprimirComprobantePagoArchivoTexto(new BigDecimal(2357));
            System.out.println("fin");
        }
        
        if(false){           
            ReportServiceImpl objService = new ReportServiceImpl();
            objService.getTotalSales(201809, 1);
        }
        
        //UtilClass.stream2file(null, "Reporte.Operaciones.xls");
 
        if(false){            
        
            String destinatario =  "edgardmr07@gmail.com"; //A quien le quieres escribir.
            String asunto = "Correo de prueba enviado desde Java";
            String cuerpo = "<b>Esta es una prueba de correo...</b>";
            String file = "C:\\ARCHIVOS\\ReporteOperaciones.xlsx";

            String style = "style='text-align: right; font-weight: bold; font-size: 12px; padding: 4px'";
            cuerpo = "<table>"
                    + "<tr><td colspan='2' style='font-weight: bold; font-size: 12px; padding:5px'>EFECTIVO</td></tr>"
                    + "<tr><td " + style + ">Total Efectivo(Usuario):</td><td>100</td></tr>"
                    + "<tr><td " + style + ">Total Efectivo(Sistema):</td><td>100</td></tr>"
                    + "<tr><td " + style + ">Descuadre:</td><td>100</td></tr>";        
            cuerpo += "</table>";

            cuerpo +="</br>";

            cuerpo += "<table>"
                    + "<tr><td colspan='2' style='font-weight: bold; font-size: 12px; padding:5px'>TARJETA</td></tr>"
                    + "<tr><td " + style + ">Total Tarjeta(Usuario):</td><td>100</td></tr>"
                    + "<tr><td " + style + ">Total Tarjeta(Sistema):</td><td>100</td></tr>"
                    + "<tr><td " + style + ">Descuadre:</td><td>100</td></tr>";
            cuerpo += "</table>";

            File attachFile = new File(file);
            SendEmail email = new SendEmail();
            email.sendMailSimple(destinatario, asunto, cuerpo);
            //email.sendMailAttachFile(destinatario, asunto, cuerpo, attachFile);

            System.out.println("valor: " + UtilClass.getCurrentTime_YYYYMMDDHHMISS());

            Connection cnConexion = null;

           
            //Session sessionTemp = sessionFactory.openSession();
            System.out.println("ejemplo:");                

            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            System.out.println("Periodo:" + df.format(new Date()));
            System.out.println("ejemplo:");
        
        }
        if (false) {
            try{

                Query  query = session.createSQLQuery(
                "select s.id_pers, s.num_peri from sic4cuaddiario s where s.id_pers = :id_pers")
                .setParameter("id_pers", "3");
                List<Object[]> rows = query.list();

                for(Object[] row : rows){
                    System.out.println("hola" + row[0].toString());
                    System.out.println("hola" + row[1].toString());
                }

                System.out.println("hola");

            }catch(Exception ex){

            }
        }
        
        
        //cnConexion = DaoConexion.verificarConexion(cnConexion);
//         try {             
//             
//         
//         DocumentServiceImpl ob = new DocumentServiceImpl(cnConexion);
//         ob.insert(null);
//             
//         
//         }catch (ValidationException e){
//             System.out.println("Mensaje:" + e.getMessage());
//         }catch (Exception e) {
//             System.out.println("Mensaje:" + e.getMessage());
//         }
//         
//         
//         
//         DaoDocumentImpl obj2 = new DaoDocumentImpl();
//         obj2.get(cnConexion, "1", 1);
//         
//         /*TEST COMISIONES*/
//         
//         
//         System.out.println("Comision:" + DaoFuncionesUtil.FNC_SICOBTCOMISION(cnConexion, 211.7, 46112, 46114));
//         
//         /**/


        if (false){ 
        
            DaoDocuKardexImpl dao = new DaoDocuKardexImpl();
            dao.getKardexLastPeriActi(session);
        }


        /*OBTENER PLANTILLA KARDEX*/
        if (false){ 
        
//            DaoDocuKardexImpl dao = new DaoDocuKardexImpl();
//            dao.getKardexByNumPeri(((SessionImpl)session).connection(), 201705);

            KardexController controller = new KardexController();
            controller.downloadTemplate();
        }

        /*OBTENER CUADRE CAJA*/
        System.out.println("Date Time:" + UtilClass.getCurrentDateTime());
        
        if (false){            
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();            
            ViSiccuaddiario obj = new ViSiccuaddiario();
            obj.setFecApertura("21/02/2018");
            dao.listViSiccuaddiario(session, obj);
            System.out.println("Sic4cuaddiario: " + obj);
            
        }
        
        /*CUADRE DIARIO: OBTENER TOTALES DE EFECTIVO Y TARJETA */
         if (false){             
            System.out.println("Fecha:" + UtilClass.getCurrentTime_YYYYMMDD());
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            Sic4cuaddiario obj = service.obtenerDatosApertura(new BigDecimal(3), new BigDecimal("20180225"));
            System.out.println("Sic4cuaddiario: " + obj);
            
        }
        
        if (false){
            System.out.println("Fecha:" + UtilClass.getCurrentTime_YYYYMMDD());
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(new BigDecimal(3)); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal("20180225"));
            Sic4cuaddiario obj = dao.getById(session, id);
            System.out.println("Sic4cuaddiario: " + obj);
            
        }

        /*VALIDAR USUARIO*/        
        if (false) {
            DaoLoginImpl daoObj = new DaoLoginImpl();
            Sic1usuario obj = new Sic1usuario();
            obj.setCodUsuario("emoreno");
            obj.setCodPwd("123");
            obj = daoObj.validateUsernamePassword(session,"admin","123");
        }
        
        /*VALIDAR USUARIO*/        
        if (false) {
            DaoLoginImpl daoObj = new DaoLoginImpl();
            Sic1usuario obj = new Sic1usuario();
            obj.setCodUsuario("emoreno");
            obj.setCodPwd("123");
            obj = daoObj.validateUsernamePassword(session,"", "");
        }

        /////---- OBTENER LOS PRODUCTOS RELACIONADOS AL DOCUMENTO
        if (false) {
            DaoDocumentImpl obj = new DaoDocumentImpl();
            obj.obtProductosXidDocu(new BigDecimal(28331));
        }
        
        /////---- OBTENER EL CODIGO DEL ULTIMO ESTADO DEL DOCUMENTO
        if (false) {
            DaoDocumentImpl obj = new DaoDocumentImpl();
            obj.getLastCodEstaDocu(session , new BigDecimal(28335));
        }
        
        /*COMISION*/
        if(false){
            try{            
                BigDecimal obj = DaoFuncionesUtil.FNC_SICOBTCOMISION(session, 100.20, 46104, 46106);
                System.out.println("COMISION" + obj.toString());
            }catch(Exception ex){
                System.out.println("ex:" + ex.getMessage());
            }
        }
        
        if(false){
            DaoProductImpl service = new DaoProductImpl();
            ViSicprod obj = new ViSicprod();
            obj.setIdProd(new BigDecimal(1));
            List<ViSicprod> list = service.listViSicProd(session, obj);
            System.out.println("");
        }
        
        /*CONSULTAR PO ID_DOCU*/
        if(false){
            try{            
                BigDecimal obj = DaoFuncionesUtil.FNC_SICOBTCOMISION(session, 100.20, 46104, 46106);
                System.out.println("COMISION" + obj.toString());
            }catch(Exception ex){
                System.out.println("ex:" + ex.getMessage());
            }
        }
        
        ///////////////
        /**/
        if (false) {
            
            DaoDocumentImpl daoDocumentImpl = new DaoDocumentImpl();
            Sic1idendocu obj = new Sic1idendocu();
            obj.setIdDocu(new BigDecimal(28327));
            Sic1idendocu result = daoDocumentImpl.get(session, obj);
            System.out.println("IDENPERS:" + result);
            System.out.println("IDENPERS:" + result.getSic1docu());
        }

        /*MANY TO ONE EXAMPLE*/
        if (false) {
            //session.beginTransaction();
            DaoPersonImpl daoPersonImpl = new DaoPersonImpl();
            Sic1pers obj = new Sic1pers();
            //Retrieve all Sic1idenpers firts time
            List<Sic1idenpers> lista = daoPersonImpl.getAll(session, obj);

            //session.clear();
            //obj.setIdPers(new BigDecimal(2));
            lista = daoPersonImpl.getAll(session, obj);

            System.out.println(sessionFactory.getStatistics().getEntityFetchCount());           //Prints 1
            System.out.println(sessionFactory.getStatistics().getSecondLevelCacheHitCount());   //Prints 1

            //session.getTransaction().commit();
            session.close();
            for (Sic1idenpers obj5 : lista) {
                Sic1pers sic1pers = obj5.getSic1pers();
                System.out.println("Persona:" + sic1pers);
                Sic1persindi sic1persindi = sic1pers.getSic1persindi();
                System.out.println("Persona Natural:" + sic1persindi);
                Sic1persorga sic1persorga = sic1pers.getSic1persorga();
                System.out.println("Persona Juridica:" + sic1persorga);
            }
            System.out.println("final");
            session.close();
        }

        if (false) {
            DaoPersonImpl daoPersonImpl = new DaoPersonImpl();
            ViSicpers obj = new ViSicpers();
            obj.setCodIden("43390561");
            daoPersonImpl.listViSicPers(session, obj);
        }     


    }

}
