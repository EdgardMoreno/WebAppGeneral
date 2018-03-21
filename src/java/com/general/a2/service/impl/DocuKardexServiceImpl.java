/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a3.dao.impl.DaoDocuKardexImpl;
import com.general.a3.dao.impl.DaoDocumentImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docuestaId;
import com.general.hibernate.entity.Sic1docukardex;
import com.general.util.beans.Constantes;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author Edgard
 */
public class DocuKardexServiceImpl {
    
    private final DaoDocuKardexImpl daoDocuKardexImpl;
    private Session session;
    
    public DocuKardexServiceImpl(){        
        this.daoDocuKardexImpl = new DaoDocuKardexImpl();
        
    }
    
    /************************/
    /***** METODOS **********/
    /************************/
    
    /*Graba el inventario realizado*/
    public void saveKardex(Sic1idendocu sic1idendocu, List<Sic1docukardex> list) throws Exception {        
        
        Transaction tx = null;
        String strIdDocuResult;
        try{        
            
            session = HibernateUtil.getSessionFactory().openSession();
           
            /*GUARDAR DOCUMENTO*/
                BigDecimal intIdTRolPers = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLPERS
                                                                                , Constantes.CONS_COD_NOAPLICA);
                
                BigDecimal intIdSClaseEven = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_SCLASEEVEN
                                                                                , sic1idendocu.getSic1docu().getCodSclaseeven());
                
                BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLESTA
                                                                                , Constantes.CONS_COD_ESTADOCU_INFORME);
                
                BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_ESTA
                                                                                , Constantes.CONS_COD_ESTAFINALIZADO );
                
                BigDecimal intIdStipodocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_STIPODOCU
                                                                                , Constantes.CONS_COD_STIPODOCU_SINDOCU );
                
                /*relacionando el ESTADO*/
                Sic3docuestaId idEsta = new Sic3docuestaId();
                idEsta.setIdTrolestadocu(intIdTRolEsta);
                idEsta.setIdEstadocu(intIdEsta);
                Sic3docuesta sic3docuesta = new Sic3docuesta();
                sic3docuesta.setId(idEsta);
                
                sic1idendocu.getSic1docu().setIdStipodocu(intIdStipodocu);
                sic1idendocu.getSic1docu().setIdSclaseeven(intIdSClaseEven);
                sic1idendocu.getSic1docu().setIdTrolpers(intIdTRolPers);
                sic1idendocu.getSic1docu().setSic3docuesta(sic3docuesta);
                
                
                tx = session.beginTransaction();
                DaoDocumentImpl daoDocumentImpl = new DaoDocumentImpl();
                strIdDocuResult = daoDocumentImpl.insert(session, sic1idendocu);
                
                System.out.println("id_Documento:" + strIdDocuResult);
                
                if (strIdDocuResult == null )
                    throw new ValidationException("No se pudo obtener el identificador del documento.");
                if (strIdDocuResult != null && Integer.valueOf(strIdDocuResult) <= 0 )
                    throw new ValidationException("No se pudo obtener el identificador del documento.");

            /*GUARDAR DETALLE DEL INVENTARIO*/                
                daoDocuKardexImpl.insert(session, list, new BigDecimal(strIdDocuResult));

            tx.commit();
        }catch (ValidationException ex){
            if(tx != null)
                tx.rollback();
            throw new ValidationException(ex.getMessage());
        }catch(Exception ex){
            if(tx != null)
                tx.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if (session != null)
                session.close();            
        }
    }
    
    /*Se obtiene el ultimo periodo activo del inventario*/
    public int getKardexLastPeriActi() throws Exception{

        int numPeri = 0;
        try{        
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            numPeri = daoDocuKardexImpl.getKardexLastPeriActi(session);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            session.close();
        }  
            
        return numPeri;        
    }
    
    /*Se obtiene el kardex del periodo en consulta*/
    public List<Sic1docukardex> getKardexByNumPeri(Integer numPeri ) throws CustomizerException {

        List<Sic1docukardex> list;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = daoDocuKardexImpl.getKardexByNumPeri(((SessionImpl)session).connection(), numPeri);
            
        }catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }  
        return list;
    }
}
