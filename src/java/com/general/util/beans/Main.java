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

import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1idenpersId;
import com.general.hibernate.entity.Sic1pers;
import com.general.a2.service.impl.Sic1generalServiceImpl;
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
import com.general.util.dao.DaoFuncionesUtil;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author emoreno
 */
public class Main {

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        
        //UtilClass.stream2file(null, "Reporte.Operaciones.xls");
 
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

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Session sessionTemp = sessionFactory.openSession();
        System.out.println("ejemplo:");
        
        
        
        
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("Periodo:" + df.format(new Date()));
        System.out.println("ejemplo:");
        
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


        if (true){ 
        
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
         if (true){             
            System.out.println("Fecha:" + UtilClass.getCurrentTime_YYYYMMDD());
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            Sic4cuaddiario obj = service.getById(new BigDecimal(3), new BigDecimal("20180225"));
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
        if (true) {
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
            obj.getRelaDocuProdByIdDocu(session , new BigDecimal(28331));
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

        Sic1generalServiceImpl sic1generalService;
        List<String> listCatTipoIden = new ArrayList<>();
        listCatTipoIden.add("RUC");
        listCatTipoIden.add("MIGRADOCU:000247-2003");
        //sic1generalService = new Sic1generalServiceImpl(cnConexion);            
        //List<Sic1general> lstSic1general1 = sic1generalService.listByCod_ValorGeneral(listCatTipoIden);

//         session.reconnect(con);
//        StatelessSession statelessSession = HibernateUtil.getSessionFactory().openStatelessSession();
        //SessionFactory sessionFactory = null;
        //StatelessSession statelessSession =  sessionFactory.openStatelessSession(cnConexion);
        //Session session = sb.connection(connection).openSession();
        //cnConexion = ((SessionImpl)session).connection();
        //Session session = ((Session)con). ;
        System.out.println(cnConexion.isClosed());

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        //Llama con SPRING
        //if (1==1) {

        PersonController personaController = (PersonController) ctx.getBean(PersonController.class);
        //Sic1pers sic1pers2 = (Sic1pers)ctx.getBean(Sic1pers.class);
        //List<Sic1pers> list3 = personaController.getObtenerPersonas();

//        for (Sic1pers per : list3) {
//            System.out.println("antes");
//            //Sic1pers sic1Pers = per.getDesPers()
//            System.out.println(per.getDesPers());
//        }
        //}

        /**
         * ***PRUEBA DE CATALOGOS***
         */
        Sic1generalServiceImpl sic1generalServiceImpl = (Sic1generalServiceImpl) ctx.getBean(Sic1generalServiceImpl.class);
        List<String> listCat = new ArrayList<>();
        listCat.add("RUC");
        listCat.add("DNI");
        List<Sic1general> lstSic1general = sic1generalServiceImpl.listByCod_ValorGeneral_Sic1general(listCat);

        for (Sic1general obj : lstSic1general) {
            System.out.println("Nombre:" + obj.getDesGeneral());
        }

        /**
         * *****************GRABAR EN CASCADA****************
         */
        Sic1pers sic1pers = new Sic1pers();
        Sic1idenpers sic1idenpers = new Sic1idenpers();
        Sic1idenpersId sic1idenpersId = new Sic1idenpersId();

        //SIC1PERS
        sic1pers.setIdPers(new BigDecimal("2509"));
        sic1pers.setDesPers("Usuario Ejemplo");
        sic1pers.setIdTipopers(new BigDecimal("1"));
        sic1pers.setIdTipodomi(new BigDecimal("4576"));

        //SIC1IDENPERS
        sic1idenpersId.setIdTipoiden(new BigDecimal("124"));
        sic1idenpersId.setCodIden("43390561");

        sic1idenpers.setId(sic1idenpersId);
        //sic1idenpers.setIdPers(new BigDecimal("2509"));
        sic1idenpers.setFecDesde(new Date());
        sic1idenpers.setFecHasta(new Date());

        Set<Sic1idenpers> lstSic1idenpers = new HashSet<>();
        lstSic1idenpers.add(sic1idenpers);

        sic1pers.setSic1idenpers(lstSic1idenpers);

        //Llamada convencional
        PersonController personaController2 = (PersonController) ctx.getBean(PersonController.class);
        //PersonaServiceImpl personaServiceImpl = ctx.getBean(PersonaServiceImpl.class);
        //Sic1pers sic1pers2 = (Sic1pers)ctx.getBean(Sic1pers.class);
        //personaController2.agregarPersona();

//            PersonaServiceImpl personaService = new PersonaServiceImpl();
//            personaService.setDaoPersona(new DaoPersonaImpl());
//            personaService.insert(sic1pers);
        //****************** LISTAR *************************
//        List<Sic1pers> list = personaService.list();
//
//        for(Sic1pers per : list){
//            System.out.println("antes");
//            //Sic1pers sic1Pers = per.getDesPers()
//            System.out.println(per.getDesPers());
//            
//            //Convertir SET -> LIST
//            List<Sic1docu> mapValueList = new ArrayList<Sic1docu>(per.getSic1docus());
//            
//            for(Sic1docu docu : mapValueList){                
//                System.out.println("Docuemnto:" + docu.getDesDocu());
//            }
//        }
    }

}
