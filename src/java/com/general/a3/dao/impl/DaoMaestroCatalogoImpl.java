/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.HibernateUtil;
import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1stipodocu;
import com.general.hibernate.views.ViSicestageneral;
import com.general.hibernate.entity.Sic1sclaseeven;
import com.general.hibernate.temp.Sic4publcanal;
import com.general.util.dao.ConexionBD;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author emoreno
 * @Descripcion: Clase que maneja los catalogos
 */
public class DaoMaestroCatalogoImpl implements Serializable{
    
    private Session session;
    
    public DaoMaestroCatalogoImpl(){
        
    }
    
    public List<Sic4publcanal> listCanalesPublicidad(Integer numPeri) throws Exception {
        
        List<Sic4publcanal> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic4publcanal.class).add(Restrictions.eq("id.numPeri", numPeri)).list();
        }catch(HibernateException ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }    
    
    public void insertCanalesPublicidad(Session session, Sic4publcanal obj) throws Exception {        
        try {            
            session.merge(obj);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }                
    }
    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" DE TODO EL CATALOGO
     * @param list Indicar las listas de "Nombres de Vista" que se desea obtener
     * @return Se obtiene un objeto LIST<SIC1GENERAL>
     * @throws Exception 
     */
    public List<Sic1general> listByCod_ValorTipoGeneral(List<String> list) throws Exception {
        List<Sic1general> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();        
            lst = session.createCriteria(Sic1general.class).add(Restrictions.in("codValortipogeneral", list)).list();
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    /**
     * SE OBTIENE UN ELEMENTO ESPECIFICO DEL CATALOGO
     * @param codValorgeneral indicar el codigo del elemento a obtener
     * @return Se obtiene un objeto SIC1GENERAL
     * @throws Exception 
     */
    public Sic1general listByCod_ValorGeneral(String codValorgeneral) throws Exception {
        Sic1general obj;
        try{
            session = HibernateUtil.getSessionFactory().openSession();   
            obj = (Sic1general)session.createCriteria(Sic1general.class).add(Restrictions.eq("codValorgeneral", codValorgeneral)).uniqueResult();
        }catch(HibernateException ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return obj;
    }
    
    /**
     * SE OBTIENE UNA "LISTA DE ELEMENTOS" DE TODO EL CATALOGO
     * @param list indicar la lista de los codigos de los elementos a obtener
     * @return Se obtiene un objeto LIST<SIC1GENERAL>
     * @throws Exception 
     */
    public List<Sic1general> listByCod_ValorGeneral(List<String> list) throws Exception {
        List<Sic1general> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();   
            lst = session.createCriteria(Sic1general.class).add(Restrictions.in("codValorgeneral", list)).list();
        }catch(HibernateException ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1stipodocu> listByCod_STipoDocu(List<String> list) throws Exception {
        List<Sic1stipodocu> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1stipodocu.class).add(Restrictions.in("codStipodocu", list)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }    
    
    public List<Sic1general> listById_GeneralRelSec(BigDecimal id) throws Exception {
        List<Sic1general> lst;        
        try{    
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1general.class).add(Restrictions.eq("idGeneralrelsec", id)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    public List<Sic1general> listById_GeneralRel(BigDecimal id) throws Exception {
        List<Sic1general> lst;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1general.class).add(Restrictions.eq("idGeneralrel", id)).list();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
        return lst;
    }
    
    /****************************************************************************************/
    /*METODOS PARA CATALOGO DE SUB TIPO DE DOCUMENTO   **************************************/
    /****************************************************************************************/
    /**
     * METODO PARA OBTENER LOS SUBTIPOS DE DOCUMENTOS(EJEM: FACTURA, BOLETA, NOTA VENTA, ETC) MEDIANTE EL IDENTIFICADO DEL TIPO DE DOCUMENTO (EJEM: COMPRONTE DE PAGO)
     * @param session Indica la conexion
     * @param idTipoDocu Indica el numero de evento
     * @return ArrayList de Subtipos de documentos
     * @throws Exception
     */
    public List<Sic1stipodocu> obtSubTipoDocuXidTipoDocu(Session session, BigDecimal idTipoDocu) throws Exception {
        List<Sic1stipodocu> lstResult = null;
        try{            
            lstResult = session.createCriteria(Sic1stipodocu.class).add(Restrictions.eq("idTipodocu", idTipoDocu)).list();
        }catch(HibernateException ex){
            throw new Exception(ex.getMessage());
        }
            
        return lstResult;
    }
    
    
    /**
     * LISTA LOS COMPROBANTES DE PAGO SEGUN LOS CODIGOS ENVIADOS
     * @param arrCodigos INDICA LA LISTA DE CODIGOS QUE SE DEBE CONSULTAR
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<Sic1stipodocu> obtComprobantesPagoXCodigos( String[] arrCodigos ) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        List<Sic1stipodocu> lista   = new ArrayList<>(); 
        
        try {
            
                cnConexion = ConexionBD.obtConexion();
            
                String sql = "SELECT * FROM SIC1STIPODOCU WHERE 1 = 1 ";
                
                if (arrCodigos != null ){                    
                    sql += " AND COD_STIPODOCU IN (" + String.join("," , arrCodigos) + ")";                    
                }
                
                sql += " ORDER BY DES_STIPODOCU ";
                
                System.out.println("sql: " + sql);

                statement = cnConexion.prepareCall(sql,
                                                   ResultSet.TYPE_SCROLL_SENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY,
                                                   ResultSet.CLOSE_CURSORS_AT_COMMIT);

                rsConsulta = statement.executeQuery();
                         
                while(rsConsulta.next()){

                    Sic1stipodocu obj = new Sic1stipodocu();
                    obj.setIdStipodocu(rsConsulta.getBigDecimal("ID_STIPODOCU"));
                    obj.setDesStipodocu(rsConsulta.getString("DES_STIPODOCU"));
                    obj.setCodStipodocu(rsConsulta.getString("COD_STIPODOCU"));
                    obj.setIdTipodocu(rsConsulta.getBigDecimal("ID_TIPODOCU"));
                    obj.setFlgVigencia(rsConsulta.getBigDecimal("FLG_VIGENCIA"));
                    obj.setIdTrolesta(rsConsulta.getBigDecimal("ID_TROLESTA"));
                    obj.setCodSunat(rsConsulta.getString("COD_SUNAT"));
                    obj.setCodPrefcpe(rsConsulta.getString("COD_PREFCPE"));

                    lista.add(obj);
                }
        
        } catch (SQLException e){
            throw new SQLException("obtComprobantesPagoXCodigos()-ERROR:" + e.getMessage());
        } catch (Exception e){
            throw new Exception("obtComprobantesPagoXCodigos()-ERROR:" + e.getMessage());
        }finally{
            if(statement != null){
                statement.close();
            }
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return lista;
    }
    
    /**
     * INDICA SI EL COMPROBANTE ESTA CONFIGURADO PARA QUE SU NUMERO SE AUTOGENERE O LA NUMERACION SEA MANUAL
     * @param arrCodigos INDICA LA LISTA DE CODIGOS QUE SE DEBE CONSULTAR
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Integer obtConfigComprobante(Integer idSclaseeven, Integer idStipodocu) throws SQLException, Exception{
        
        CallableStatement statement = null;
        ResultSet rsConsulta        = null;
        Connection cnConexion       = null;
        Integer flgAutoGen          = 0;
        
        try {
            
                cnConexion = ConexionBD.obtConexion();
            
                String sql = " SELECT FLG_AUTOGEN " +                            
                            "        FROM SIC3SCLASEEVENSTIPODOCU " +
                            "        WHERE ID_SCLASEEVEN = " + idSclaseeven +
                            "              AND ID_STIPODOCU = " + idStipodocu;                

                statement = cnConexion.prepareCall(sql,
                                                   ResultSet.TYPE_SCROLL_SENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY,
                                                   ResultSet.CLOSE_CURSORS_AT_COMMIT);

                rsConsulta = statement.executeQuery();
                         
                while(rsConsulta.next()){
                    flgAutoGen = rsConsulta.getInt("FLG_AUTOGEN");
                }
        
        } catch (SQLException e){
            throw new SQLException("obtConfigComprobante()-ERROR:" + e.getMessage());
        } catch (Exception e){
            throw new Exception("obtConfigComprobante()-ERROR:" + e.getMessage());
        }finally{
             if(statement != null){
                statement.close();
            }
            
            if(rsConsulta != null)
                rsConsulta.close();
            
            if(cnConexion != null)
                cnConexion.close();
        }
        
        return flgAutoGen;
    }
    
    
    public List<ViSicestageneral> getCataEstaRol(ViSicestageneral obj) throws Exception{
        
        List<ViSicestageneral> lst;
        try{
            session = HibernateUtil.getSessionFactory().openSession();        
            Criteria criteria = session.createCriteria(ViSicestageneral.class);

            if(obj.getCodTrolesta()!= null && obj.getCodTrolesta().trim().length() > 0)
                criteria.add(Restrictions.eq("codTrolesta",obj.getCodTrolesta()));

            lst = criteria.list();
        
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }finally{
            if(session != null)
                session.close();
        }

        return lst;        
    } 
    
    public List<Sic1sclaseeven> getCataSClaseEven(Session session, BigDecimal idClaseeven) throws SQLException, Exception {
        List<Sic1sclaseeven> lst = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            lst = session.createCriteria(Sic1sclaseeven.class).add(Restrictions.eq("idClaseeven", idClaseeven)).list();
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
            
        return lst;
    }
    
    
    public void insertarTablaMaestro(List<Sic1general> lstDatos) throws SQLException, Exception {

       StoredProcedure sp = null;
       Connection cnConexion = null;

       try {

           System.out.println("======================== insertarTablaMaestro ========================");           
          
           
           
           cnConexion = ConexionBD.obtConexion();
           
           for(Sic1general obj : lstDatos){
                
                sp = new StoredProcedure("SICDBA.PKG_SICMANTGENERAL.PRC_SICCREAGENERAL");
                sp.addParameter(new InParameter("X_DES_GENERAL"          ,Types.VARCHAR, obj.getDesGeneral()));
                sp.addParameter(new InParameter("X_COD_VALORTIPOGENERAL" ,Types.VARCHAR, obj.getCodValortipogeneral()));
                sp.addParameter(new InParameter("X_COD_VALORGENERAL"     ,Types.VARCHAR, obj.getCodValorgeneral()));
                sp.addParameter(new InParameter("X_ID_GENERALREL"        ,Types.VARCHAR, obj.getIdGeneralrel()));
                sp.addParameter(new InParameter("X_COD_VALORDEFECTO"     ,Types.VARCHAR, obj.getCodValordefecto()));
                sp.addParameter(new InParameter("X_COD_VALORTIPOENTIDAD" ,Types.VARCHAR, obj.getCodValortipoentidad()));
                sp.addParameter(new InParameter("X_NUM_VALOR"            ,Types.NUMERIC, obj.getNumValor()));
                sp.addParameter(new InParameter("X_DES_VALOR"            ,Types.VARCHAR, obj.getDesValor()));
                sp.addParameter(new InParameter("X_FEC_DESDE"            ,Types.VARCHAR, null));
                sp.addParameter(new InParameter("X_FEC_HASTA"            ,Types.VARCHAR, null));           

                sp.addParameter(new OutParameter("X_ID_ERROR"    ,Types.NUMERIC));
                sp.addParameter(new OutParameter("X_DES_ERROR"   ,Types.VARCHAR));
                sp.addParameter(new OutParameter("X_FEC_ERROR"   ,Types.DATE));

                sp.ExecuteNonQuery(cnConexion);

                if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                    throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                }
           }           
           
           cnConexion.commit();

        } catch (SQLException e){
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new SQLException(e.getMessage());
        } catch (Exception e){
            if(cnConexion!= null)
                cnConexion.rollback();
            throw new SQLException(e.getMessage());
       } finally{
           if(cnConexion!= null)
               cnConexion.close();        
       }
   }  
    
}
