/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoMaestroCatalogoImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.temp.Sic4publcanal;
import com.general.util.beans.Constantes;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 */
public class MaestroCatalogoServiceImpl implements Serializable{    
    
    //Connection cnConexion;
    private final DaoMaestroCatalogoImpl daoSic1generalImp;
    
    public MaestroCatalogoServiceImpl(){
        daoSic1generalImp = new DaoMaestroCatalogoImpl();
    }
    
    /**
     * SE OBTIENE EL CATALOGO DE CANALES DE PUBLICIDAD
     * @return
     * @throws Exception 
     */
    public List<Sic1general> getCatalCanalesPublicidad() throws Exception {
        
        List<Sic1general> lst;
        List<String> listCat = new ArrayList<>();
        listCat.add("VI_SICPUBLCANAL");
        try{
            lst = daoSic1generalImp.listByCod_ValorTipoGeneral(listCat);
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        return lst;
    }    
    
    /**
     * SE GRABA LOS CANALES DE PUBLICIDAD INGRESADOS
     * @param list
     * @return
     * @throws CustomizerException 
     */
    public String grabarCanalesPublicidad(List<Sic4publcanal> list) throws CustomizerException {
        
        String result = null;
        Session session = null;
        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            for(Sic4publcanal obj : list ){                     
                daoSic1generalImp.insertCanalesPublicidad(session, obj);            
            }
            session.getTransaction().commit();
            
        }catch(Exception ex){
            if(session!= null)
                session.getTransaction().rollback();
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session!= null)
                session.close();
        }
        
        return result;
    }

    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" DE TODO EL CATALOGO
     * @param list Indicar las listas de "Nombres de Vista" que se desea obtener
     * @return Se obtiene un objeto LIST<SIC1GENERAL>
     * @throws CustomizerException 
     */
    public List<Sic1general> listByCod_ValorTipoGeneral_Sic1general(List<String> list) throws CustomizerException {
        
        List<Sic1general> lstResult;
        try{    
            lstResult = daoSic1generalImp.listByCod_ValorTipoGeneral(list);
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }     
        return lstResult;        
    }
    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" QUE INDICA LA FORMA DE PAGO (Transferencia, Deposito, etc.)
     * @return Se obtiene un objeto LIST<SIC1GENERAL>
     * @throws CustomizerException 
     */
    public List<Sic1general> obtFormasPago() throws CustomizerException {
        
        List<Sic1general> lstResult;
        try{                
            
            List<String> list = new ArrayList<>();
            list.add("VI_SICMODAPAGO");
            lstResult = daoSic1generalImp.listByCod_ValorTipoGeneral(list);
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }     
        return lstResult;        
    }
    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" QUE INDICA LA FORMA DE PAGO (Transferencia, Deposito, etc.)
     * @return Se obtiene un objeto LIST<SIC1GENERAL>
     * @throws CustomizerException 
     */
    public List<Sic1general> obtFormasPagoCompra() throws CustomizerException {
        
        List<Sic1general> lstResult;
        try{                
            
            List<String> list = new ArrayList<>();
            list.add(Constantes.COD_MODOPAGO_TRANSFER);
            list.add(Constantes.COD_MODOPAGO_DEPOSITO);
            list.add(Constantes.COD_MODOPAGO_EFECTIVO);
            lstResult = daoSic1generalImp.listByCod_ValorGeneral(list);
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }     
        return lstResult;        
    }
    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" DE TODO EL CATALOGO
     * @param list Indicar las listas de "Nombres de Vista" que se desea obtener
     * @return Se obtiene un objeto LIST<SELECTITEM>
     * @throws CustomizerException 
     */
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
    
    public List<SelectItem> listByCod_ValorGeneral_SelectItem(List<String> list) throws Exception {
        
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
            throw new Exception(ex.getMessage());
        }
        
        return lstResult;        
    }
    
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
    
    
    
    /*CATALOGO DE ESTADOS*/
    public List<ViSicestageneral> getCataEstaRolDocuInf( ) throws Exception{
        ViSicestageneral obj = new ViSicestageneral();
        obj.setCodTrolesta("VI_SICESTADOCUCOMPROBANTE");
        return daoSic1generalImp.getCataEstaRol(obj);        
    }       
    
    /** 
     * METODO QUE DEVUELVE EL CATALOGO DE COMPROBANTES DE PAGOS PARA GASTO(FACTURA, BOLETA, ETC)
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws CustomizerException
     */
    public List<Sic1stipodocu> obtComprobantesPagoGasto() throws Exception {
        
        List<Sic1stipodocu> lstResult = new ArrayList();
        
        try{
            String[] arrCodigos = new String[6];
            arrCodigos[0] = "'" + Constantes.CONS_COD_STIPODOCU_FACTURA + "'";
            arrCodigos[1] = "'" + Constantes.CONS_COD_STIPODOCU_BOLETA + "'";
            arrCodigos[2] = "'" + Constantes.CONS_COD_STIPODOCU_SINDOCU + "'";
            arrCodigos[3] = "'" + Constantes.CONS_COD_STIPODOCU_RECIBOHONOR + "'";
            arrCodigos[4] = "'" + Constantes.CONS_COD_STIPODOCU_BOLETOVIAJE + "'";
            arrCodigos[5] = "'" + Constantes.CONS_COD_STIPODOCU_OTROSPAGOS + "'";
            
            
            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
            lstResult = objDao.obtComprobantesPagoXCodigos(arrCodigos);
                        
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        } 
        return lstResult;
    }  
    
    /** 
     * METODO QUE DEVUELVE EL CATALOGO DE COMPROBANTES DE PAGOS PARA VENTA (FACTURA, BOLETA, NOTA DE VENTA)
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws CustomizerException
     */
    public List<Sic1stipodocu> obtComprobantesPagoVenta() throws Exception {
        
        List<Sic1stipodocu> lstResult = new ArrayList();
        
        try{
            String[] arrCodigos = new String[4];
            arrCodigos[0] = "'" + Constantes.CONS_COD_STIPODOCU_FACTURA + "'";
            arrCodigos[1] = "'" + Constantes.CONS_COD_STIPODOCU_BOLETA + "'";
            arrCodigos[2] = "'" + Constantes.CONS_COD_STIPODOCU_SINDOCU + "'";
            arrCodigos[3] = "'" + Constantes.CONS_COD_STIPODOCU_NOTAVENTA + "'";
            
            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
            lstResult = objDao.obtComprobantesPagoXCodigos(arrCodigos);
                        
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        } 
        return lstResult;
    }  
    
    /** 
     * METODO QUE DEVUELVE EL CATALOGO DE COMPROBANTES DE PAGOS PARA REGISTRAR UNA COMPRA (FACTURA, BOLETA, SIN DOCUMENTO)
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws CustomizerException
     */
    public List<Sic1stipodocu> obtComprobantesPagoCompra() throws Exception {
        
        List<Sic1stipodocu> lstResult = new ArrayList();
        
        try{
            String[] arrCodigos = new String[3];
            arrCodigos[0] = "'" + Constantes.CONS_COD_STIPODOCU_FACTURA + "'";
            arrCodigos[1] = "'" + Constantes.CONS_COD_STIPODOCU_BOLETA + "'";
            arrCodigos[2] = "'" + Constantes.CONS_COD_STIPODOCU_SINDOCU + "'";
            
            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
            lstResult = objDao.obtComprobantesPagoXCodigos(arrCodigos);
                        
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        } 
        return lstResult;
    }  
    
    /** 
     * METODO QUE DEVUELVE EL CATALOGO DE COMPROBANTES DE PAGOS PARA UNA ORDEN DE COMPRA (NOTA DE PEDIDO)
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws CustomizerException
     */
    public List<Sic1stipodocu> obtComprobantesPagoOrdenCompra() throws Exception {
        
        List<Sic1stipodocu> lstResult = new ArrayList();
        
        try{
            String[] arrCodigos = new String[1];
            arrCodigos[0] = "'" + Constantes.CONS_COD_STIPODOCU_NOTAPEDIDO + "'";
            
            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
            lstResult = objDao.obtComprobantesPagoXCodigos(arrCodigos);
                        
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        } 
        return lstResult;
    }  
    
    /** 
     * METODO QUE DEVUELVE TODO EL CATALOGO DE COMPROBANTES DE PAGO (FACTURA, BOLETA, NOTA DE VENTA, ETC)
     * @return "List(Sic1stipodocu)" de Subtipo de documentos
     * @throws CustomizerException
     */
    public List<Sic1stipodocu> obtComprobantesPago() throws Exception {
        
        List<Sic1stipodocu> lstResult = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
            BigDecimal intIdTipoDocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPODOCU, Constantes.CONS_COD_TIPODOCU_COMPROBANTEPAGO);
            
            DaoMaestroCatalogoImpl objDao = new DaoMaestroCatalogoImpl();
            lstResult = objDao.obtSubTipoDocuXidTipoDocu(session, intIdTipoDocu);
                        
        } catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        } finally{
            if( session!= null)
                session.close();
        }
        return lstResult;
    }    
    
    /*CATALOGO DE GASTOS*/
    public List<Sic1sclaseeven> objCatalogoTipoGastos( ) throws Exception{
        
        
        List<Sic1sclaseeven> lstResultado = new ArrayList(); 
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            
            BigDecimal idClaseeven = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_CLASEEVEN
                                                                                , Constantes.CONS_COD_CLASEEVEN_GASTOS);
            
            lstResultado = daoSic1generalImp.getCataSClaseEven(session, idClaseeven);            
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        
        return lstResultado;
        
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
                si.setValue(obj.getIdSclaseeven());
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
    
    /**
     * Permite agregar o actualizar un catalogo
     * @param lstDatos
     * @throws Exception 
     */
    public void insertarTablaMaestro(List<Sic1general> lstDatos) throws Exception {
        daoSic1generalImp.insertarTablaMaestro(lstDatos);
    }
    
    /**
     * METODO QUE OBTIENE EL NOMBRE DE LA IMPRESORA CONFIGURADA PARA IMPRIMIR LOS VOUCHER
     * @return
     * @throws Exception 
     */
    public static String obtenerNombreImpresoraVoucher() throws Exception{
        String resultado = null;
        DaoMaestroCatalogoImpl daoCatalogo = new DaoMaestroCatalogoImpl();
        Sic1general objGeneral = daoCatalogo.listByCod_ValorGeneral("DES_NOMBIMPRESORAVOUCHER");
        
        if(objGeneral!= null && !objGeneral.getDesValor().isEmpty())
            resultado = objGeneral.getDesValor();
        
        return resultado;
    }
    
     
}
