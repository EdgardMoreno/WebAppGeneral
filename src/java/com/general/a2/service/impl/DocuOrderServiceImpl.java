
package com.general.a2.service.impl;


import com.general.a3.dao.impl.DaoDocumentImpl;
import com.general.a3.dao.impl.DaoProductImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docuestaId;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.relaentity.Sic3docupersId;
import com.general.hibernate.relaentity.Sic3proddocu;
import com.general.hibernate.views.ViSicdocu;
import com.general.interfac.service.DocumentService;
import com.general.util.beans.Constantes;
import java.io.Serializable;
import java.util.List;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author emoreno
 */
//@Service(value="rfService")

public class DocuOrderServiceImpl implements Serializable, DocumentService{
    
    private final DaoDocumentImpl daoDocumentImpl;
    private Session session;
    
    public DocuOrderServiceImpl(){        
        this.daoDocumentImpl = new DaoDocumentImpl();
        
    }

    /*
        Metodo que se utiliza para guardar un pedido de: 
                Compra, Venta, Proforma, etc
    */
    @Override
    public String insert(Sic1idendocu sic1idendocu) throws ValidationException, CustomizerException {        
                
        double numMtoTotal      = 0.0;        
        String strIdDocuResult        = null;
        Transaction tx          = null;
        BigDecimal numMtoComision;
        
        try{
            Sic1docu sic1docu = sic1idendocu.getSic1docu();
            
            if ( sic1docu.getCodSerie() == null || sic1docu.getCodSerie().isEmpty()) 
                throw new ValidationException("Número de serie inválido");
            
            if ( sic1docu.getNumDocu() == null || sic1docu.getNumDocu().intValue() <= 0) 
                throw new ValidationException("Número del Documento inválido");
                
            session = HibernateUtil.getSessionFactory().openSession();

            /*VALIDAR SI EXISTE DOCUMENTO: Cuando se crea un nuevo documento el ID_DOCU es nulo. Se verifica que no exista*/
                if(sic1docu.getIdDocu() == null ){
                    Sic1idendocu obj = daoDocumentImpl.get(session, sic1idendocu);
                    if (obj != null){
                        throw new ValidationException("El Número del Documento ingresado ya existe");
                    }
                }

            /*CALCULAR COMISION POS*/
                if (sic1docu.getIdTipotarjeta() != null && sic1docu.getIdTipotarjeta().intValue() > 0 ){
                    numMtoTotal = sic1docu.getNumSubtotal().doubleValue() + sic1docu.getNumIgv().doubleValue();
                    numMtoComision = DaoFuncionesUtil.FNC_SICOBTCOMISION(session
                                                                        ,numMtoTotal
                                                                        ,sic1docu.getIdModapago().intValue()
                                                                        ,sic1docu.getIdTipotarjeta().intValue());
                    sic1idendocu.getSic1docu().setNumMtocomi(numMtoComision);
                }
        
            /*GUARDAR PEDIDO*/
                BigDecimal intIdTRolPers = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLPERS
                                                                                , Constantes.CONS_COD_NOAPLICA);
                
                BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLESTA
                                                                                , Constantes.CONS_COD_ESTADOCUCOMPROBANTE);
                /*relacionando el ESTADO*/
                Sic3docuestaId idEsta = new Sic3docuestaId();
                idEsta.setIdTrolestadocu(intIdTRolEsta);
                Sic3docuesta sic3docuesta = new Sic3docuesta();
                sic3docuesta.setId(idEsta);
                
                sic1idendocu.getSic1docu().setIdTrolpers(intIdTRolPers);
                sic1idendocu.getSic1docu().setSic3docuesta(sic3docuesta);
                
                tx = session.beginTransaction();
                strIdDocuResult = daoDocumentImpl.insert(session, sic1idendocu);
                System.out.println("id_Documento:" + strIdDocuResult);

            /*GUARDAR DETALLE DE PRODUCTOS*/            
                List<Sic3proddocu> lstSic3proddocu = sic1docu.getLstSic3proddocu();
                int index = 0;
                
                while(index < lstSic3proddocu.size()){
                    lstSic3proddocu.get(index).getId().setIdDocu(new BigDecimal(strIdDocuResult));
                    index++;
                }
                DaoProductImpl daoProductImpl = new DaoProductImpl();
                daoProductImpl.relateProdDocu(session, lstSic3proddocu);
            
            /*GUARDAR RELACION DEL DOCUMENTO CON LA PERSONA(Vendedor)*/
//                BigDecimal idTipoRela = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPORELA, Constantes.CONS_COD_RELADOCUPERS);
//                BigDecimal idTipoRol = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPOROLPERS, Constantes.CONS_COD_VENDEDOR);
//                Sic3docupers sic3docupers = new Sic3docupers();
//                Sic3docupersId id = new Sic3docupersId();
//                id.setIdDocu(new BigDecimal(strIdDocuResult));
//                id.setIdPers(new BigDecimal(1)); /*Cambiar por el ID de vendedor que inicio sesion en el sistema*/
//                id.setIdTreladocu(idTipoRela);
//                id.setIdTrolpers(idTipoRol);
//                sic3docupers.setId(id);
//                List<Sic3docupers> lstSic3docupers = new ArrayList();
//                lstSic3docupers.add(sic3docupers);
//                daoDocumentImpl.relateDocuPers(session, lstSic3docupers);
            
            
            tx.commit();
        }catch (ValidationException ex){
            throw new ValidationException(ex.getMessage());
        }catch(Exception ex){
            if(tx != null)
                tx.rollback();            
            throw new CustomizerException(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
        
        return strIdDocuResult;
    }
    
    @Override
    public String update(Sic1idendocu obj) throws ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String relateDocuDocu(Sic3docudocu sic3docudocu) throws ValidationException, CustomizerException {
        String result = null;
        try{
            result = daoDocumentImpl.relateDocuDocu(null, sic3docudocu);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        return result;
    }

    @Override
    public String relateDocuEsta(Sic3docuesta sic3docuesta) throws ValidationException, CustomizerException {
         String result = null;
        try{
            result = daoDocumentImpl.relateDocuEsta(null, sic3docuesta);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        return result;        
    }    
    
    @Override
    public String relateDocuPers(List<Sic3docupers> list) throws ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws ValidationException, CustomizerException {
        
        List<ViSicdocu> lstResult;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lstResult = daoDocumentImpl.listViSicdocu(session, obj);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }        
        return lstResult;
        
    }

    @Override
    public Sic1idendocu getById(BigDecimal id) throws ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sic1idendocu getByCodIden(String cod) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
