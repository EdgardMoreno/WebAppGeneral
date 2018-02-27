/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1usuario;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate1.ViSiccuaddiario;
import com.general.util.beans.UtilClass;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author emoreno
 */
public class DaoCashRegisterImpl {
    
    
    public DaoCashRegisterImpl(){
        
    }
    
    
    /*METODOS*/
    
    public void insert(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.save(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
     public void update(Session session, Sic4cuaddiario obj) throws Exception {        
        try {            
            session.update(obj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public Sic4cuaddiario getById(Session session, Sic4cuaddiarioId id) throws Exception {
        
        Sic4cuaddiario obj = null;
        try {        
            obj = (Sic4cuaddiario)session.get(Sic4cuaddiario.class,id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }    
    
    
    public List<ViSiccuaddiario> listViSiccuaddiario(Session session, ViSiccuaddiario obj) throws Exception {
                
        int flgFilter = 0;
        Date fecDesde = null, fecHasta = null;
        List<ViSiccuaddiario> lstResult = new ArrayList();
        try{
            
            Criteria criteria = session.createCriteria(ViSiccuaddiario.class);
        
            System.out.println("FecDesde: " + obj.getFecApertura());
            System.out.println("FecHasta: " + obj.getFecCierre());

            if(obj.getFecApertura() != null || obj.getFecCierre() != null){
              fecDesde = obj.getFecApertura() != null?UtilClass.convertStringToDate(obj.getFecApertura()):UtilClass.getObtFecIni();
              fecHasta = obj.getFecCierre() != null?UtilClass.convertStringToDate(obj.getFecCierre()):UtilClass.getObtFecInf();
            }
            
            System.out.println("FecDesde: " + fecDesde);
            System.out.println("FecHasta: " + fecHasta);
            
            if(fecDesde != null){
                criteria.add(Restrictions.between("numPeri",UtilClass.convertDateToNumber(fecDesde) , UtilClass.convertDateToNumber(fecHasta)));
                flgFilter = 1;
            }
            
            lstResult = criteria.list();      

        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }

        return lstResult;
    }
    
    /*Metodo que obtiene el total de ventas en EFECTIVO/TARJETA que realizó el vendedor durante el dia. */
    public Sic4cuaddiario getSummaryOrders(Session session, Sic4cuaddiario obj) throws Exception {
                            
        String sql =                     
            " SELECT  NVL(SUM(CASE WHEN V1.COD_MODOPAGO = 'VI_SICEFECTIVO' THEN TO_NUMBER(V1.NUM_MTOTOTAL) ELSE 0 END ),0) AS NUM_MTOEFECTIVO " +
            "        ,NVL(SUM(CASE WHEN V1.COD_MODOPAGO = 'VI_SICTARJDEBITO' THEN TO_NUMBER(V1.NUM_MTOTOTAL) ELSE 0 END),0) AS NUM_MTOTARJDEBI " +
            "        ,NVL(SUM(CASE WHEN V1.COD_MODOPAGO = 'VI_SICTARJCREDITO' THEN TO_NUMBER(V1.NUM_MTOTOTAL) ELSE 0 END),0) AS NUM_MTOTARJCRED " +                    
            " FROM VI_SICDOCU V1 " +                
            " WHERE V1.ID_PERS_CREADOR = :id_pers " +
            "       AND TO_CHAR(V1.FEC_DESDE,'YYYYMMDD') = :num_peri" ;
        
        System.out.println("ID_PERS: "  + obj.getId().getIdPers());
        System.out.println("NUM_PERI: " + obj.getId().getNumPeri());        

        Query  query = session.createSQLQuery(sql)                 
                .setParameter("id_pers", obj.getId().getIdPers())
                .setParameter("num_peri", obj.getId().getNumPeri().toString());

        List<Object[]> rows = query.list();

        for(Object[] row : rows){

            System.out.println("NUM_MTOEFECTIVO: " + row[0].toString());
            System.out.println("NUM_MTOTARJDEBI: " + row[1].toString());
            System.out.println("NUM_MTOTARJCRED: " + row[2].toString());

            obj.setNumEfectTotalVentaSiste(new BigDecimal(row[0].toString()).setScale(2, BigDecimal.ROUND_HALF_UP));

            double numTotalTarj = Double.valueOf(row[1].toString()) + Double.valueOf(row[2].toString());
            System.out.println("numTotalTarj:" + numTotalTarj);

            obj.setNumTarjeTotalSiste(new BigDecimal(numTotalTarj).setScale(2, BigDecimal.ROUND_HALF_UP));
            obj.setNumEfectTotalGastoSiste(new BigDecimal("0.00"));

        }
        
        return obj;
    }
    
    
}
