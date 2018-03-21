/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoSic1generalImp;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.hibernate1.Sic1sclaseeven;
import com.general.util.beans.Constantes;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 */
public class Sic1generalServiceImpl implements Serializable{    
    
    //Connection cnConexion;
    private final DaoSic1generalImp daoSic1generalImp;
    
    public Sic1generalServiceImpl() throws Exception{
        daoSic1generalImp = new DaoSic1generalImp();
    }

    /*SE OBTIENE EL CATALOGO MEDIANTE EL CODIGO DE LA VISTA QUE LO CONTIENE*/
    public List<Sic1general> listByCod_ValorTipoGeneral_Sic1general(List<String> list) throws CustomizerException {
        
        List<Sic1general> lstResult;
        try{    
            lstResult = daoSic1generalImp.listByCod_ValorTipoGeneral(list);
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }     
        return lstResult;        
    }
    
    public List<SelectItem> listByCod_ValorTipoGeneral_SelectItem(List<String> list) throws CustomizerException {
        
        List<SelectItem> lstResult = new ArrayList();        
        try{            
            List<Sic1general> lstSic1general = daoSic1generalImp.listByCod_ValorTipoGeneral(list);
        
            SelectItem si;
            for(Sic1general obj : lstSic1general){
                si = new SelectItem();
                si.setLabel(obj.getDesGeneral());
                si.setValue(obj.getIdGeneral());
                lstResult.add(si);                
            }            
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
        return lstResult;        
    }
    
    /*SE OBTIENE EL CATALOGO MEDIANTE EL CODIGO DEL ITEM*/
    public List<Sic1general> listByCod_ValorGeneral_Sic1general(List<String> list) throws CustomizerException {        
        
        List<Sic1general> list2;
        try{
            list2 = daoSic1generalImp.listByCod_ValorGeneral(list);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        return list2;
    }
    
    public List<SelectItem> listByCod_ValorGeneral_SelectItem(List<String> list) throws CustomizerException {
        
        List<SelectItem> lstResult = new ArrayList();        
        try{            
            List<Sic1general> lstSic1general = daoSic1generalImp.listByCod_ValorGeneral(list);
        
            SelectItem si;
            for(Sic1general obj : lstSic1general){
                si = new SelectItem();
                si.setLabel(obj.getDesGeneral());
                si.setValue(obj.getIdGeneral());
                lstResult.add(si);                
            }            
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;        
    }
    
    /**/
    public List<Sic1stipodocu> listByCod_STipoDocu_Sic1stipodocu(List<String> list) throws Exception {
        List<Sic1stipodocu> listResult;
        try{
            listResult = daoSic1generalImp.listByCod_STipoDocu(list);
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        return listResult;
    }
    
    public List<SelectItem> listByCod_STipoDocu_SelectItem(List<String> list) throws CustomizerException {
        
        List<SelectItem> lstResult = new ArrayList();        
        try{            
            List<Sic1stipodocu> lstSic1stipodocu = daoSic1generalImp.listByCod_STipoDocu(list);
        
            SelectItem si;
            for(Sic1stipodocu obj : lstSic1stipodocu){
                si = new SelectItem();
                si.setLabel(obj.getDesStipodocu());
                System.out.println("IdStipodocu:" + obj.getIdStipodocu());
                si.setValue(obj.getIdStipodocu());
                lstResult.add(si);                
            }            
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
        return lstResult;          
    }
    
    /**/
    public List<Sic1general> listById_GeneralRelSec(BigDecimal id) throws Exception {
        List<Sic1general> lstResult;
        try{
            lstResult = daoSic1generalImp.listById_GeneralRelSec(id);
         } catch(Exception ex){
            throw new Exception(ex.getMessage());
        }   
        return lstResult;
    }
    
    public List<Sic1general> listById_GeneralRel(BigDecimal id) throws CustomizerException {
        List<Sic1general> lstResult;
        try{
            lstResult = daoSic1generalImp.listById_GeneralRel(id);
         } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }   
        return lstResult;
    }
    
    public List<Sic1stipodocu> getCataDocumentsType() throws SQLException, Exception {
        return daoSic1generalImp.getCataDocumentsType();
    }
    
    /*CATALOGO DE ESTADOS*/
    public List<ViSicestageneral> getCataEstaRolDocuInf( ) throws Exception{
        ViSicestageneral obj = new ViSicestageneral();
        obj.setCodTrolesta("VI_SICESTADOCUCOMPROBANTE");
        return daoSic1generalImp.getCataEstaRol(obj);
        
    }
    
    /*CATALOGO DE GASTOS*/
    public List<SelectItem> getCataSpend( ) throws Exception{
        
        
        List<SelectItem> lstResult = new ArrayList(); 
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            
            BigDecimal idClaseeven = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_CLASEEVEN
                                                                                , Constantes.CONS_COD_CLASEEVEN_GASTOS);
            
            List<Sic1sclaseeven> list = daoSic1generalImp.getCataSClaseEven(session, idClaseeven);                        
        
            SelectItem si;
            for(Sic1sclaseeven obj : list){
                si = new SelectItem();
                si.setLabel(obj.getDesSclaseeven());
                si.setValue(obj.getIdClaseeven());
                lstResult.add(si);
            }
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        
        return lstResult;
        
    }
    
    
    /*CONSULTAR GENERO*/
    public List<SelectItem> getCataGender() throws Exception {        
        
        List<SelectItem> lstResult = new ArrayList();
        List<String> list = new ArrayList();
        try{
            list.add(Constantes.CONS_COD_GENERO);
            List<Sic1general> lstSic1general = daoSic1generalImp.listByCod_ValorTipoGeneral(list);
        
            SelectItem si;
            for(Sic1general obj : lstSic1general){
                si = new SelectItem();
                si.setLabel(obj.getDesGeneral());
                si.setValue(obj.getIdGeneral());
                lstResult.add(si);                
            }            
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        }        
        return lstResult;        
    }
    
}
