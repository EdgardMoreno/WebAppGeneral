
package com.general.a2.service.impl;


import com.general.a3.dao.impl.DaoDocumentImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docuestaId;
import com.general.hibernate.relaentity.Sic3docupers;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.interfac.service.DocumentService;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import java.io.Serializable;
import java.util.List;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.math.BigDecimal;
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
        String strIdDocuResult  = null;
        Transaction tx          = null;
        BigDecimal numMtoComision;
        boolean flgNuevoRegistro = false;
        boolean flgSinComprobante = false;
        
        try{
                       
            session = HibernateUtil.getSessionFactory().openSession();
            Sic1docu sic1docu = sic1idendocu.getSic1docu();            
            
            /*OBTENER SI SE ESTA EDITANDO O REGISTRANDO UN NUEVO DOCUMENTO*/
            if (sic1docu.getIdDocu() == null)
                flgNuevoRegistro = true;
            
            BigDecimal idStipodocuSinDocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_STIPODOCU
                                                                                , Constantes.CONS_COD_STIPODOCU_SINDOCU);
            
            /*Se valida si el SUBTIPO DE DOCUMENTO es SIN DOCUMENTO, es decir la operacion NO TIENE COMPROBANTE*/
            if (sic1docu.getIdStipodocu().compareTo(idStipodocuSinDocu) == 0){
                flgSinComprobante = true;
                sic1idendocu.getSic1docu().setCodSerie(null);
                sic1idendocu.getSic1docu().setNumDocu(null);
            }
            
            BigDecimal intIdSClaseEven = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_SCLASEEVEN
                                                                                , sic1docu.getCodSclaseeven());

            System.out.println("ID_STIPODOCU_SINDOCU:" + idStipodocuSinDocu);
            System.out.println("ID_STIPODOCU:" + sic1docu.getIdStipodocu());

            /*En caso en la operacion NO EXISTA COMPROBANTE la generación del COD_IDEN es diferente*/
            if (flgSinComprobante){                
                /*Solo se crea el nuevo COD_IDEN cuando es un nuevo registro, en el caso que se este editante el COD_IDEN ya viene 
                en el objeto "sic1idendocu" que se pasa como parametro*/
                if (flgNuevoRegistro){
                    /*Se Genera el COD_IDEN*/
                    String strCodigo =  intIdSClaseEven + "."  +
                                        sic1docu.getIdStipodocu() + "." +
                                        UtilClass.getCurrentTime_YYYYMMDDHHMISS();
                    sic1idendocu.setCodIden(strCodigo);
                }
                
            }else{
                
                if ( sic1docu.getCodSerie() == null || sic1docu.getCodSerie().isEmpty() )
                    throw new ValidationException("Número de Serie inválido");

                if ( sic1docu.getNumDocu() == null || sic1docu.getNumDocu().intValue() <= 0 )
                    throw new ValidationException("Número de Correlativo inválido");
                
                /*Se genera un nuevo COD_IDEN cuando es un nuevo registro, en el caso que se este editante el COD_IDEN ya viene 
                  en el objeto "sic1idendocu" que se pasa como parametro*/
                if (flgNuevoRegistro){
                    String strCodigo =  sic1docu.getIdPersexterno().toString() + "." +
                                        intIdSClaseEven + "."  + 
                                        sic1docu.getIdStipodocu() + "." + 
                                        sic1docu.getCodSerie().trim() + "-" + sic1docu.getNumDocu();
                    sic1idendocu.setCodIden(strCodigo);
                }
            }

            /*VALIDAR SI YA EXISTE EL DOCUMENTO QUE SE PRETENDE REGISTRAR*/            
                if(flgNuevoRegistro){
                    /*Para los comprobantes SIN DOCUMENTO no se realiza la validacion
                        ya que esto siempre es un correlativo*/
                    if(!flgSinComprobante){
                        boolean exist = daoDocumentImpl.verifyByCodiden(session, sic1idendocu.getCodIden());
                        if (exist){
                            throw new ValidationException("El Número del comprobante ingresado ya existe.");
                        }
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
                
                System.out.println("CodigoEstaDocu:" + sic1docu.getCodEstadocu());
        
            /*GUARDAR PEDIDO*/
                BigDecimal intIdTRolPers = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLPERS
                                                                                , Constantes.CONS_COD_NOAPLICA);
                
                
                
                BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOROLESTA
                                                                                , Constantes.CONS_COD_ESTADOCU_COMPROBANTE);
                
                BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_ESTA
                                                                                , sic1docu.getCodEstadocu());
                
                /*relacionando el ESTADO*/
                Sic3docuestaId idEsta = new Sic3docuestaId();
                idEsta.setIdTrolestadocu(intIdTRolEsta);
                idEsta.setIdEstadocu(intIdEsta);
                Sic3docuesta sic3docuesta = new Sic3docuesta();
                sic3docuesta.setId(idEsta);
                
                sic1idendocu.getSic1docu().setIdSclaseeven(intIdSClaseEven);
                sic1idendocu.getSic1docu().setIdTrolpers(intIdTRolPers);
                sic1idendocu.getSic1docu().setSic3docuesta(sic3docuesta);
                
                tx = session.beginTransaction();
                strIdDocuResult = daoDocumentImpl.insert(session, sic1idendocu);
                System.out.println("id_Documento:" + strIdDocuResult);

            /*GUARDAR DETALLE DE PRODUCTOS*/
                List<Sic3docuprod> lstSic3docuprod = sic1docu.getLstSic3docuprod();
                int index = 0;                
                
                while(index < lstSic3docuprod.size()){
                    lstSic3docuprod.get(index).getId().setIdDocu(new BigDecimal(strIdDocuResult));
                    index++;
                }
                
                daoDocumentImpl.relateDocuProd(session, lstSic3docuprod);
            
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
    
    public void relateDocuEsta( BigDecimal idDocu
                                ,String codTrolestadocu
                                ,String codEstadocu ) throws Exception {
        
        Transaction tx = null;
        try{
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                        , Constantes.CONS_COD_TIPOROLESTA
                                                                        , codTrolestadocu);

            BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                        , Constantes.CONS_COD_ESTA
                                                                        , codEstadocu);
            
            System.out.println("intIdTRolEsta: " + intIdTRolEsta);
            System.out.println("intIdEsta: " + intIdEsta);
            
            Sic3docuestaId id = new Sic3docuestaId();
            id.setIdTrolestadocu(intIdTRolEsta);
            id.setIdEstadocu(intIdEsta);
            id.setIdDocu(idDocu);
            Sic3docuesta sic3docuesta = new Sic3docuesta();
            sic3docuesta.setId(id);
            
            tx = session.beginTransaction();            
            daoDocumentImpl.relateDocuEsta(session, sic3docuesta);
            tx.commit();
            
        }catch(Exception ex){
            if(tx != null)
                tx.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
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
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws CustomizerException {
        
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
    public Sic1idendocu getById(BigDecimal id) throws CustomizerException {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sic1idendocu getByCodIden(String cod) throws ValidationException, CustomizerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
    /**     
     * METODO QUE DEVUELDE EL DETALLE DE LA ORDEN
     */
    
    public Sic1idendocu getOrderById(BigDecimal id) throws CustomizerException {
        
        Sic1idendocu sic1idendocu;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            sic1idendocu = daoDocumentImpl.getById(session, id);
            
            /*Se obtiene la LISTA DE PRODUCTOS*/
            List<Sic3docuprod> list = daoDocumentImpl.getRelaDocuProdByIdDocu(session, id);
            sic1idendocu.getSic1docu().setLstSic3docuprod(list);
            
            /*Se obtiene el codigo del estado vigente*/
            String codEstaDocu = daoDocumentImpl.getLastCodEstaDocu(session, id);
            sic1idendocu.getSic1docu().setCodEstadocu(codEstaDocu);
            
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            session.close();
        }        
        return sic1idendocu;        
    }
}
