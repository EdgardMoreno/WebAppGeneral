
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoPersonImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.views.ViSicpers;
import com.general.util.beans.Constantes;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 */
//@Service(value="rfService")

public class PersonServiceImpl implements Serializable{
    
    private final DaoPersonImpl daoPersonImpl;
    private Session session;
    
    /*CONSTRUCTORES*/
    public PersonServiceImpl(){
        this.daoPersonImpl  = new DaoPersonImpl();
    }

    /*METODOS*/
   
    public String insert(Sic1idenpers obj) throws ValidationException, CustomizerException {
        
        String result;
        Transaction tx = null;
        BigDecimal idTipoiden;
        BigDecimal idTipopers;
        BigDecimal idTrolpers = null;
        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            idTipoiden = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPOIDEN, obj.getSic1pers().getCodTipoiden());
            obj.getId().setIdTipoiden(idTipoiden);
            
            idTipopers = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPOPERS, obj.getSic1pers().getCodTipopers());
            obj.getSic1pers().setIdTipopers(idTipopers);
            
            if (obj.getSic1pers().getCodTrolpers() != null){
                idTrolpers = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPOROLPERS, obj.getSic1pers().getCodTrolpers());
                obj.getSic1pers().setIdTrolpers(idTrolpers);
            }
            
            System.out.println("idTipoiden:" + idTipoiden);
            System.out.println("idTipopers:" + idTipopers);
            System.out.println("idTrolpers:" + idTrolpers);
            
            tx = session.beginTransaction();            
            result = daoPersonImpl.insert(session, obj);
            tx.commit();
        
        }catch(ValidationException ex){
            
            if(tx != null)
                tx.rollback();
            
            throw new ValidationException(ex.getMessage());
            
        }catch(Exception ex){
            
            if(tx != null)
                tx.rollback();
            
            throw new CustomizerException(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
        
        return result;
    }
    
    public Sic1idenpers getByCodiden(String codIden) throws CustomizerException{
        
        Sic1idenpers result;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();            
            result = daoPersonImpl.getByCodiden(session, codIden);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
        return result;
    }
    
    public Sic1idenpers getById(BigDecimal id) throws CustomizerException{
        
        Sic1idenpers result;
        try{
            session = HibernateUtil.getSessionFactory().openSession();            
            result = daoPersonImpl.getById(session, id);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
        return result;
    }
    
    public List<Sic1idenpers> getAll(Sic1pers obj) throws CustomizerException{
        
        List<Sic1idenpers> lstResult;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();            
            lstResult = daoPersonImpl.getAll(session, obj);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return lstResult;
    }
    
    public List<ViSicpers> listViSicPers(ViSicpers obj) throws CustomizerException{
        
        List<ViSicpers> result;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = daoPersonImpl.listViSicPers(session, obj);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }
        return result;
    }    
}
