/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoCashRegisterImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.hibernate1.ViSiccuaddiario;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 */
public class CashRegisterServiceImpl implements Serializable{
    
    private Session session;
    
    
    public void open(Sic4cuaddiario obj) throws CustomizerException {
        
        Transaction tx = null;        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            /*Se obtiene el estado inicial de la caja: CREADO*/
            BigDecimal idEsta = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_ESTA
                                                                                , Constantes.CONS_COD_ESTACREADO);
            obj.setIdEsta(idEsta);
            obj.setFecApertura(UtilClass.getCurrentDateTime());
            
            tx = session.beginTransaction();            
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            dao.insert(session, obj);
            tx.commit();
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            throw new CustomizerException(e.getMessage());
        }finally{
            session.close();
        }
    }
    
    
    public void close(Sic4cuaddiario obj) throws CustomizerException {
        
        Transaction tx = null;        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            /*Se obtiene el estado inicial de la caja: CREADO*/
            BigDecimal idEsta = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                , Constantes.CONS_COD_ESTA
                                                                , Constantes.CONS_COD_ESTACERRADO);            
            obj.setIdEsta(idEsta);
            System.out.println("Fecha Hora: " + UtilClass.getCurrentDateTime());
            obj.setFecCierre(UtilClass.getCurrentDateTime());
            
            tx = session.beginTransaction();            
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            dao.update(session, obj);
            tx.commit();
            
            
            
            
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            throw new CustomizerException(e.getMessage());
        }finally{
            session.close();
        }
    }
    
     public Sic4cuaddiario getById(Sic4cuaddiarioId id) throws CustomizerException {
        
        Sic4cuaddiario obj = null;
        try {        
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            session = HibernateUtil.getSessionFactory().openSession();
            obj = dao.getById(session, id);
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return obj;
    }
     
     public Sic4cuaddiario getById(BigDecimal idPers, BigDecimal numPeri) throws CustomizerException {
        
        Sic4cuaddiario obj = null;
        try {
            
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(idPers);
            id.setNumPeri(numPeri);            
            
            DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
            session = HibernateUtil.getSessionFactory().openSession();
            obj = dao.getById(session, id);
            
            /*Se obtiene los totales de la VENTA en EFECTIVO/TARJETA que se realizó desde el sistema*/
            obj = dao.getSummaryOrders(session, obj);
            
            if(obj != null && obj.getNumEfectApertCaja()!= null)
                obj.getNumEfectApertCaja().setScale(2,BigDecimal.ROUND_HALF_UP);
            
            System.out.println("Apertura Caja: " + obj.getNumEfectApertCaja());
            
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return obj;
    }
     
     
    public List<ViSiccuaddiario> listViSiccuaddiario(ViSiccuaddiario obj) throws CustomizerException{

       List<ViSiccuaddiario> result;
       try{
           DaoCashRegisterImpl dao = new DaoCashRegisterImpl();
           session = HibernateUtil.getSessionFactory().openSession();
           result = dao.listViSiccuaddiario(session, obj);
       }catch(Exception ex){
           throw new CustomizerException(ex.getMessage());
       }finally{
           session.close();
       }
       return result;
    }
     
    
    
}
