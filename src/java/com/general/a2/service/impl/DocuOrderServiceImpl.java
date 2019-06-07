
package com.general.a2.service.impl;


import com.general.a3.dao.impl.DaoDocumentImpl;
import com.general.a3.dao.impl.DaoMaestroCatalogoImpl;
import com.general.a3.dao.impl.DaoProductImpl;
import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1docu;
import com.general.hibernate.entity.Sic1idendocu;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.relaentity.Sic3docudocu;
import com.general.hibernate.relaentity.Sic3docuesta;
import com.general.hibernate.relaentity.Sic3docuestaId;
import com.general.hibernate.views.ViSicdocu;
import com.general.hibernate.relaentity.Sic3docuprod;
import com.general.interfac.service.DocumentService;
import com.general.util.beans.Constantes;
import com.general.util.beans.UtilClass;
import com.general.util.dao.ConexionBD;
import java.io.Serializable;
import java.util.List;
import com.general.util.dao.DaoFuncionesUtil;
import com.general.util.exceptions.CustomizerException;
import com.general.util.exceptions.ValidationException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import org.hibernate.HibernateException;
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
        boolean flgNuevaOrden = false;
        boolean flgComprobanteAutogenerado = false;
        
        try{
                       
            session = HibernateUtil.getSessionFactory().openSession();
            Sic1docu sic1docu = sic1idendocu.getSic1docu();   
            
            Sic1stipodocu objTipoComp = sic1idendocu.getSic1docu().getSic1stipodocu();
            
            /*VALIDAR SI ESTA HACIENDO UNA FACTURA PARA UNA PERSONA NATURAL CON DNI*/            
            String codTipoidenpersLocal = DaoFuncionesUtil.FNC_SICOBTCODGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPOIDEN
                                                                                , sic1docu.getSic1persexterno().getIdTipoiden());

            if (codTipoidenpersLocal.equals(Constantes.CONS_COD_TIPOIDEN_DNI) &&
                    objTipoComp.getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_FACTURA))
                throw new ValidationException("No se puede otorgar FACTURA a una persona Natural con DNI.");
                
            
            /*SABER SI SE ESTA EDITANDO O REGISTRANDO UN DOCUMENTO*/
            if (sic1docu.getIdDocu() == null)
                flgNuevaOrden = true;
            
            /*Se obtiene el tipo de operacion, Compra, Venta, Gasto, etc*/
            BigDecimal intIdSClaseEven = null;
            if (sic1docu.getSic1sclaseeven().getCodSclaseeven() != null || sic1docu.getIdSclaseeven() == null){
                
                intIdSClaseEven = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_SCLASEEVEN
                                                                                , sic1docu.getSic1sclaseeven().getCodSclaseeven());
                
                sic1idendocu.getSic1docu().setIdSclaseeven(intIdSClaseEven);
            }else
                intIdSClaseEven = sic1docu.getIdSclaseeven();
            
            
            /*Se valida si el tipo de documento seleccionado está configurado para que se autogenere un correlativo*/
            DaoMaestroCatalogoImpl objDaoMae = new DaoMaestroCatalogoImpl();
            Integer flgAutogen = objDaoMae.obtConfigComprobante(sic1docu.getIdSclaseeven().intValue(), sic1docu.getIdStipodocu().intValue());                        
            
            if( flgAutogen == 1){
                
                flgComprobanteAutogenerado = true;
//                sic1idendocu.getSic1docu().setCodSerie(null);
//                sic1idendocu.getSic1docu().setNumDocu(null);
            }           
            
            
            /*VALIDAR si se ha ingresado SERIE o NUMERO DE COMPROBANTE para un tipo de comprobante está configurado para 
            que se autogenere un correlativo*/
            if(flgComprobanteAutogenerado && flgNuevaOrden){                

                String msg = "No se debe especificar SERIE o NUMERO DE COMPROBANTE para un(a) " + sic1docu.getSic1stipodocu().getDesStipodocu() + " ya que se autogenera.";

                if(sic1idendocu.getSic1docu().getCodSerie() != null && sic1idendocu.getSic1docu().getCodSerie().trim().length() > 0)
                    throw new ValidationException(msg);
                if(sic1idendocu.getSic1docu().getNumDocu()!= null && sic1idendocu.getSic1docu().getNumDocu().intValue() > 0)
                    throw new ValidationException(msg);
            }
            
            //System.out.println("ID_STIPODOCU_SINDOCU:" + idStipodocuSinDocu);
            //System.out.println("ID_STIPODOCU:" + sic1docu.getIdStipodocu());

            /*En caso en la operacion NO EXISTA COMPROBANTE la generación del COD_IDEN es diferente*/
            if (flgComprobanteAutogenerado){
                /*Solo se crea el nuevo COD_IDEN cuando es un nuevo registro, en el caso que se este editante el COD_IDEN ya viene 
                en el objeto "sic1idendocu" que se pasa como parametro*/
                if (flgNuevaOrden){
                    /*Se Genera el COD_IDEN*/
                    String strCodigo =  intIdSClaseEven + "."  +
                                        sic1docu.getIdStipodocu() + "." +
                                        UtilClass.getCurrentTime_YYYYMMDDHHMISS();
                    sic1idendocu.setCodIden(strCodigo);
                }
                
            }else{
                
                /*Se valida si el tipo de comprobante seleccionado requiere que se ingrese LA SERIE Y COMPROBANTE o solo UN NUMERO DE OPERACION*/
                
                if(objTipoComp.getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_FACTURA) || 
                        objTipoComp.getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_BOLETA) ||
                            objTipoComp.getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAPEDIDO) || 
                                    objTipoComp.getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_NOTAVENTA)){

                    if ( sic1docu.getCodSerie() == null || sic1docu.getCodSerie().isEmpty() )
                        throw new ValidationException("Serie inválida.");

                    if ( sic1docu.getNumDocu() == null || sic1docu.getNumDocu().intValue() <= 0 )
                        throw new ValidationException("Número de comprobante inválido.");
                    
                }else{
                    if ( sic1docu.getNumDocu() == null || sic1docu.getNumDocu().intValue() <= 0 )
                        throw new ValidationException("Número de comprobante inválido.");
                }
                
                /*Se genera un nuevo COD_IDEN cuando es un nuevo registro, en el caso que se este editante el COD_IDEN ya viene 
                  en el objeto "sic1idendocu" que se pasa como parametro*/
                if (flgNuevaOrden){
                    String strCodigo =  sic1docu.getIdPersexterno().toString() + "." +
                                        intIdSClaseEven + "."  + 
                                        sic1docu.getIdStipodocu() + "." + 
                                        sic1docu.getCodSerie().trim() + "-" + sic1docu.getNumDocu();
                    sic1idendocu.setCodIden(strCodigo);
                }
            }

            /*VALIDAR SI YA EXISTE EL DOCUMENTO QUE SE PRETENDE REGISTRAR*/            
                if(flgNuevaOrden){
                    /*Para los comprobantes SIN DOCUMENTO no se realiza la validacion
                        ya que esto siempre es un correlativo*/
                    if(!flgComprobanteAutogenerado){
                        boolean exist = daoDocumentImpl.verifyByCodiden(session, sic1idendocu.getCodIden());
                        if (exist){
                            throw new ValidationException("El Número del comprobante ingresado ya existe.");
                        }
                    }
                }

            /*CALCULAR COMISION POS*/
                if (sic1docu.getIdTipotarjeta() != null && sic1docu.getIdTipotarjeta().intValue() > 0 ){
                    
                    numMtoTotal = sic1docu.getNumMtotarjeta().doubleValue() + sic1docu.getNumMtocomitarjeta().doubleValue();
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
                
                sic1idendocu.getSic1docu().setIdTrolpers(intIdTRolPers);
                sic1idendocu.getSic1docu().setSic3docuesta(sic3docuesta);
                
                tx = session.beginTransaction();
                
                strIdDocuResult = daoDocumentImpl.insert(session, sic1idendocu);
                System.out.println("id_Documento:" + strIdDocuResult);
                
            /*EN CASO DE ORDEN DE COMPRA SE ELIMINA LOS PRODUCTOS PARA VOLVER A CREARLOS, ESTO PARA QUE FUNCIONE LA EDICION DE UN ORDEN DE COMPRA DONDE SE REDUCE LA CANTIDAD DE ITEMS
              QUE HABIA EN UN INICIO*/
                if(sic1docu.getSic1sclaseeven().getCodSclaseeven() != null && sic1docu.getSic1sclaseeven().getCodSclaseeven().equals(Constantes.COD_SCLASEEVEN_ORDENCOMPRA))
                    daoDocumentImpl.eliminarProductosXidDocu(session, new BigDecimal(strIdDocuResult));                

            /*GUARDAR DETALLE DE PRODUCTOS*/         

                daoDocumentImpl.relateDocuProd(session, new BigDecimal(strIdDocuResult), sic1docu.getLstSic3docuprod());
                
            /*GUARDAR DOCUMENTO RELACIONADO. EJEMPLO: NOTA DE VENTA*/
                if(sic1docu.getSic3docudocu() != null){
                    
                    BigDecimal intIdTRelaDocu = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
                                                                                , Constantes.CONS_COD_TIPORELA
                                                                                , Constantes.CONS_COD_RELADOCUDOCU);
                    
                    sic1docu.getSic3docudocu().getId().setIdDocu(new BigDecimal(strIdDocuResult));
                    sic1docu.getSic3docudocu().getId().setIdTreladocu(intIdTRelaDocu);
                    daoDocumentImpl.relateDocuDocu(session, sic1docu.getSic3docudocu());

                    /*Cambiando de estado "FINALIZADO" al documento relacionado*/
                    relateDocuEsta( ((SessionImpl)session).connection()
                                   ,sic1docu.getSic3docudocu().getId().getIdDocurel()
                                   ,Constantes.CONS_COD_ESTADOCU_COMPROBANTE
                                   ,Constantes.CONS_COD_ESTAFINALIZADO
                                   ,null);
                    
                }
            
            /*GUARDAR RELACION DEL DOCUMENTO CON LA PERSONA(Cliente/Proveedor)*/
//                BigDecimal idTipoRela = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPORELA, Constantes.CONS_COD_RELADOCUPERS);
//                BigDecimal idTipoRol = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl)session).connection(), Constantes.CONS_COD_TIPOROLPERS, sic1docu.getCodTrolpersexterno());
//                Sic3docupers sic3docupers = new Sic3docupers();
//                Sic3docupersId id = new Sic3docupersId();
//                id.setIdDocu(new BigDecimal(strIdDocuResult));
//                id.setIdPers(sic1docu.getIdPersexterno()); /*ID DEL CLIENTE O PROVEEDOR*/
//                id.setIdTreladocu(idTipoRela);
//                id.setIdTrolpers(idTipoRol);
//                sic3docupers.setId(id);
//                List<Sic3docupers> lstSic3docupers = new ArrayList();
//                lstSic3docupers.add(sic3docupers);
//                daoDocumentImpl.relateDocuPers(session, lstSic3docupers);


            /*Registrar documento en tabla de pendientes de envío a Sunat*/
            sic1docu.setIdDocu(new BigDecimal(strIdDocuResult));
            FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
            objFacturador.registrarDocuPendienteEnvioSunat(((SessionImpl)session).connection(), sic1docu, Constantes.CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT);
            
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
    
    public void anularDocumento( Sic1docu objDocu
                                ,String codTrolestadocu
                                ,String codEstadocu
                                ,String desMotivo) throws ValidationException, Exception {        
        
        Connection cnConexion = null;
        
        try{            
            
            BigDecimal idDocu = objDocu.getIdDocu();
            
            cnConexion = ConexionBD.obtConexion();
            
            /*Se verifica si el documento en consulta es documento secundario de algun documento principal*/
            List<Sic3docudocu> list = daoDocumentImpl.obtDocusRelXidDocuRel(cnConexion, idDocu);
            for (Sic3docudocu obj :list ){
                String codEstaDocu = daoDocumentImpl.getLastCodEstaDocu(cnConexion, obj.getId().getIdDocu());
                if(!codEstaDocu.equals(Constantes.CONS_COD_ESTAANULADO)){
                    
                    ViSicdocu objDatosDocu  = daoDocumentImpl.obtViSicdocu(obj.getId().getIdDocu());
                    
                    throw new ValidationException("No se puede anular, primero anule el documento principal: " + objDatosDocu.getDesDocu() );
                }
            }           
                        
            /*Se anula documento en consulta*/            
            this.relateDocuEsta(cnConexion, idDocu, codTrolestadocu, codEstadocu, desMotivo );
            
            /*Se libera los documentos secundarios relacionados al documento en consulta*/
            list = daoDocumentImpl.obtDocusRelXidDocu(cnConexion, idDocu);
            for (Sic3docudocu obj :list ){                
                this.relateDocuEsta(cnConexion, obj.getId().getIdDocurel(), codTrolestadocu, Constantes.CONS_COD_ESTAPORRECOGER, desMotivo );
            }            
            
            /*Registrar documento en tabla de pendientes de envío a Sunat*/
            if(objDocu.getSic1stipodocu().getCodStipodocu().equals(Constantes.CONS_COD_STIPODOCU_FACTURA)){
                FacturadorSunatServiceImpl objFacturador = new FacturadorSunatServiceImpl();
                objFacturador.registrarDocuPendienteEnvioSunat(cnConexion, objDocu, Constantes.CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA);
            }
            
            cnConexion.commit();
        
        }catch(ValidationException ex){
            if(cnConexion != null)
                cnConexion.rollback();
            throw new ValidationException(ex.getMessage());
        }catch(Exception ex){
            if(cnConexion != null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if (cnConexion != null)
                cnConexion.close();
        }
    }
    
//    public void relateDocuEsta( BigDecimal idDocu
//                                ,String codTrolestadocu
//                                ,String codEstadocu ) throws Exception {
//        
//        Transaction tx = null;
//        try{
//            
//            session = HibernateUtil.getSessionFactory().openSession();
//            
//            BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
//                                                                        , Constantes.CONS_COD_TIPOROLESTA
//                                                                        , codTrolestadocu);
//
//            BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(((SessionImpl) session).connection()
//                                                                        , Constantes.CONS_COD_ESTA
//                                                                        , codEstadocu);
//            
//            System.out.println("intIdTRolEsta: " + intIdTRolEsta);
//            System.out.println("intIdEsta: " + intIdEsta);
//            
//            Sic3docuestaId id = new Sic3docuestaId();
//            id.setIdTrolestadocu(intIdTRolEsta);
//            id.setIdEstadocu(intIdEsta);
//            id.setIdDocu(idDocu);
//            Sic3docuesta sic3docuesta = new Sic3docuesta();
//            sic3docuesta.setId(id);
//            
//            tx = session.beginTransaction();
//            daoDocumentImpl.relateDocuEsta(session, sic3docuesta);
//            
//            DaoProductImpl objDaoProd = new DaoProductImpl();
//            objDaoProd.updateStock(session, idDocu.intValue());
//            
//            tx.commit();
//            
//        }catch(Exception ex){
//            if(tx != null)
//                tx.rollback();
//            throw new Exception(ex.getMessage());
//        }finally{
//            if (session != null)
//                session.close();
//        }
//    }
    
    /**
     * METODO PARA CAMBIAR Y VERSIONAR DE ESTADO AL DOCUMENTO     
     * @param idDocu
     * @param codTrolestadocu
     * @param codEstadocu
     * @param desMotivo
     * @throws Exception 
     */
    public void relateDocuEsta(  BigDecimal idDocu
                                ,String codTrolestadocu
                                ,String codEstadocu
                                ,String desMotivo) throws Exception {
        
        Connection cnConexion = null;
        try{
            
            cnConexion = ConexionBD.obtConexion();
            
            BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion
                                                                        , Constantes.CONS_COD_TIPOROLESTA
                                                                        , codTrolestadocu);

            BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion
                                                                        , Constantes.CONS_COD_ESTA
                                                                        , codEstadocu);            
            
            Sic3docuestaId id = new Sic3docuestaId();
            id.setIdTrolestadocu(intIdTRolEsta);
            id.setIdEstadocu(intIdEsta);
            id.setIdDocu(idDocu);
            Sic3docuesta sic3docuesta = new Sic3docuesta();
            sic3docuesta.setId(id);
            sic3docuesta.setDesNotas(desMotivo);
                        
            daoDocumentImpl.relateDocuEsta(cnConexion, sic3docuesta);
            
            DaoProductImpl objDaoProd = new DaoProductImpl();
            objDaoProd.updateStock(cnConexion, idDocu.intValue());
            
            cnConexion.commit();
                       
        }catch(Exception ex){
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion!= null)
                cnConexion.close();
        }   
    }
    
    /**
     * METODO PARA CAMBIAR Y VERSIONAR DE ESTADO AL DOCUMENTO
     * @param cnConexion
     * @param idDocu
     * @param codTrolestadocu
     * @param codEstadocu
     * @param desMotivo
     * @throws Exception 
     */
    public void relateDocuEsta(  Connection cnConexion
                                ,BigDecimal idDocu
                                ,String codTrolestadocu
                                ,String codEstadocu
                                ,String desMotivo) throws Exception {
        
        
        try{
            
            BigDecimal intIdTRolEsta  = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion
                                                                        , Constantes.CONS_COD_TIPOROLESTA
                                                                        , codTrolestadocu);

            BigDecimal intIdEsta      = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion
                                                                        , Constantes.CONS_COD_ESTA
                                                                        , codEstadocu);            
            
            Sic3docuestaId id = new Sic3docuestaId();
            id.setIdTrolestadocu(intIdTRolEsta);
            id.setIdEstadocu(intIdEsta);
            id.setIdDocu(idDocu);
            Sic3docuesta sic3docuesta = new Sic3docuesta();
            sic3docuesta.setId(id);
            sic3docuesta.setDesNotas(desMotivo);
                        
            daoDocumentImpl.relateDocuEsta(cnConexion, sic3docuesta);
            
            DaoProductImpl objDaoProd = new DaoProductImpl();
            objDaoProd.updateStock(cnConexion, idDocu.intValue());
                       
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    @Override
    public List<ViSicdocu> listViSicdocu(ViSicdocu obj) throws CustomizerException {
        
        List<ViSicdocu> lstResult;
        try{
            
            lstResult = daoDocumentImpl.listViSicdocu(obj);
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }
        
        return lstResult;
        
    }    

    
    /**
     * METODO QUE DEVUELDE EL DETALLE DE LA ORDEN
     * @param idDocu
     * @return
     * @throws CustomizerException 
     */    
    public Sic1idendocu getOrderByIdDocu(BigDecimal idDocu) throws Exception {
        
        Sic1idendocu sic1idendocu;
        List<Sic3docuprod> lstProducts = new ArrayList<>();
        List<Sic3docuprod> lstProductsRel;
        Connection cnConexion = null;
        
        try{            
            
            cnConexion = ConexionBD.obtConexion();    
            
            /*Se obtiene los datos de documento*/
            //sic1idendocu = daoDocumentImpl.getById(session, idDocu);
            sic1idendocu = daoDocumentImpl.obtDocuXidDocu(idDocu);                       
            
            /*Se obtiene la lista de los DOCUMENTOS RELACIONADOS*/
            List<Sic3docudocu> lstDocusRel = daoDocumentImpl.obtDocusRelXidDocu(cnConexion, idDocu);
            
            /*SE OBTIENE LOS PRODUCTOS DEL DOCUMENTO RELACIONADO*/
            for(Sic3docudocu objDocuRel : lstDocusRel){
                lstProductsRel = daoDocumentImpl.obtProductosXidDocu(objDocuRel.getId().getIdDocurel());
                lstProducts.addAll(lstProductsRel);
            }
            
            /*SE OBTIENE LA LISTA DE PRODUCTOS DEL DOCUMENTO PRINCIPAL*/
            lstProducts = daoDocumentImpl.obtProductosXidDocu(idDocu);
            
            /*Seteando valor false para indicar que esos productos ya son parte de la orden*/
            for(int i=0; i<lstProducts.size(); i++){
                lstProducts.get(i).setFlgNuevo(false);
            }
            
            sic1idendocu.getSic1docu().setLstSic3docuprod(lstProducts);
            
            /*Se obtiene la lista de PERSONAS RELACIONAS AL DOCUMENTO*/
//            List<Sic3docupers> lstPersonas = daoDocumentImpl.obtPersRelXidDocu(idDocu);
//            sic1idendocu.getSic1docu().setLstPersRela(lstPersonas);            
            
            /*Se obtiene el codigo del ultimo estado del documento*/
            String codEstaDocu = daoDocumentImpl.getLastCodEstaDocu(cnConexion, idDocu);
            sic1idendocu.getSic1docu().setCodEstadocu(codEstaDocu);            
        
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(cnConexion != null)
                cnConexion.close();
        }        
        return sic1idendocu;        
    }
    
    /**
     * SE OBTIENE LOS PRODUCTOS RELACIONADOS A UNA ORDEN
     * @param idDocu
     * @return
     * @throws CustomizerException 
     */
    public List<Sic3docuprod> obtProductosXidDocu(BigDecimal idDocu) throws Exception{
        
        List<Sic3docuprod> lstProducts = new ArrayList<>();
        try{            
            lstProducts = daoDocumentImpl.obtProductosXidDocu(idDocu);

        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        
        return lstProducts;
    }
    
    
    /**
     * SE OBTIENE EL ULTIMO ESTADO DEL DOCUMENTO
     * @param idDocu
     * @return
     * @throws CustomizerException 
     */
    public String getLastCodEstaDocu( BigDecimal idDocu) throws CustomizerException {

        String result = "";
        try{            
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            result= daoDocumentImpl.getLastCodEstaDocu(session, idDocu);                        
                   
        }catch(HibernateException ex){
            throw new CustomizerException(ex.getMessage());
        }finally{
            if (session != null)
                session.close();
        }
        
        return result;
    }
    
}
