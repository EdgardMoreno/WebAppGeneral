
package com.general.a2.service.impl;


import com.general.a3.dao.impl.DaoProductImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.hibernate.relaentity.Sic3proddocu;
import com.general.hibernate.views.ViSicprod;
import com.general.util.beans.Constantes;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;


/**
 *
 * @author emoreno
 */
//@Service(value="rfService")

public class ProductServiceImpl implements Serializable{
    
    private final DaoProductImpl daoProductImpl;    
    private Session session;
    
    /*CONSTRUCTORES*/
    public ProductServiceImpl(){
        //this.session = HibernateUtil.getSessionFactory().openSession();
        this.daoProductImpl = new DaoProductImpl();
    }
    
    public ProductServiceImpl(Session session){        
        this.session = session;
        this.daoProductImpl = new DaoProductImpl();        
    }   
    
    /*METODOS*/        
    public String insert(Sic1prod sic1prod) throws ValidationException, CustomizerException {
        
        String result = null;
        try{
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.session.beginTransaction();
            result = daoProductImpl.insert(this.session, sic1prod);            
            this.session.getTransaction().commit();
        }catch(ValidationException ex){
            session.getTransaction().rollback();
            throw new ValidationException(ex.getMessage());
        }catch(Exception ex){
            session.getTransaction().rollback();
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        
        return result;
    }
    
    public String update(Sic1prod sic1prod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public String delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String relateProdDocu(List<Sic3proddocu> list) throws Exception {
        return daoProductImpl.relateProdDocu(this.session, list);
    }
    
    public List<Sic1prod> getAll(Sic1prod obj) throws CustomizerException{
        session = HibernateUtil.getSessionFactory().openSession();
        List<Sic1prod> list;  
        try{
            
            list = daoProductImpl.getAll(session, obj);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return list;         
    }
    
    public List<Sic1prod> getAutocompleteByCodProd(String codProd) throws CustomizerException{
        session = HibernateUtil.getSessionFactory().openSession();
        List<Sic1prod> list;  
        try{            
            list = daoProductImpl.getAutocompleteByCodProd(session, codProd);            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return list;         
    }
    
    public Sic1prod getByCod(String cod) throws CustomizerException {
        
        session = HibernateUtil.getSessionFactory().openSession();
        Sic1prod obj;
        try{
            obj = daoProductImpl.getByCod(session, cod);
        
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return obj;  
        
    }
    
    public List<ViSicprod> listViSicProd(ViSicprod obj) throws CustomizerException{
        
        List<ViSicprod> result;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = daoProductImpl.listViSicProd(session, obj);
        }catch(HibernateException ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return result;
    }
    
    /**
     * PROCEDIMIENTO PARA, SEGUN LA OPERACION, REVERTIR EL STOCK DEL PRODUCTO
     * @param cnConexion
     * @param objDocu
     * @param lstProductos
     * @throws Exception 
     */
    public void actualizarStockPorAnulacion(Connection cnConexion, Sic1docu objDocu, List<Sic3docuprod> lstProductos) throws Exception{
        
        try{
            
            DaoProductImpl objDaoProd = new DaoProductImpl();
            BigDecimal numNuevaCantidad = new BigDecimal(0);
            
            for(Sic3docuprod objDocuProd : lstProductos){
                /* - SI ES UNA VENTA, SE SUMA
                   - SI ES UNA COMPRA, SE RESTA*/
                if( objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_VENTA) &&
                        !objDocu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){
                    numNuevaCantidad = objDocuProd.getNumCantidad();
                }else if( objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_COMPRA)){
                    numNuevaCantidad = objDocuProd.getNumCantidad().multiply(new BigDecimal(-1));
                }

                objDaoProd.updateStock(cnConexion,objDocuProd.getId().getIdProd(), numNuevaCantidad);
            }
            
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    } 
    
    
    /**
     * PROCEDIMIENTO PARA ACTUALIZAR EL STOCK DEL PRODUCTO
     * @param cnConexion
     * @param objDocu
     * @param lstProductos
     * @throws Exception 
     */
    public void actualizarStockPorRegistro(Connection cnConexion, Sic1docu objDocu, List<Sic3docuprod> lstProductos) throws Exception{
        
        try{
            
            DaoProductImpl objDaoProd = new DaoProductImpl();
            BigDecimal numNuevaCantidad = new BigDecimal(0);
            
            for(Sic3docuprod objDocuProd : lstProductos){
                
                if(objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_VENTA) &&
                        !objDocu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){
                    numNuevaCantidad = objDocuProd.getNumCantidad().multiply(new BigDecimal(-1));
                }else if( objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_COMPRA) ||
                          objDocu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_NOTACREDITO )){
                    numNuevaCantidad = objDocuProd.getNumCantidad();
                }

                objDaoProd.updateStock(cnConexion,objDocuProd.getId().getIdProd(), numNuevaCantidad);
            }
            
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    } 
}
