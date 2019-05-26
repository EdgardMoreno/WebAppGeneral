/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.util.dao.DaoFuncionesUtil;
import com.general.hibernate.entity.Sic1idenpers;
import com.general.hibernate.entity.Sic1pers;
import com.general.hibernate.views.ViSicpers;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.ValidationException;
import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author Edgard
 */
//@Lazy
//@Component
public class DaoPersonImpl implements Serializable{
    
    private final static Logger log = LoggerFactory.getLogger(DaoPersonImpl.class);

    /*CONSTRUCTORES*/
    public DaoPersonImpl(){
        
    }
    
    /*METODOS*/    
    
    public String insert(Session session, Sic1idenpers sic1idenpers) throws Exception {
        
        try {
            
            String result = session.doReturningWork(new ReturningWork<String>() {
                     @Override
                     public String execute(Connection cnConexion) throws SQLException {
                         
                        BigDecimal intIdTipoLugar;
                        BigDecimal intIdTipoPersPN;
                        BigDecimal intIdTipoPersPJ;
                        BigDecimal intIdEstaCivil = null;
                        BigDecimal intIdTipoEmpresa = null;
                        BigDecimal intIdTipoOrga = null;
                        BigDecimal intIdGenero = null;                        
                        String dtFecNaci = null;
                        String strFecDesde = null;
                        String strFecHasta = null;                        
                        
                        try{

                            Sic1pers sic1pers = sic1idenpers.getSic1pers();
                            
                            
                            BigDecimal idTipoIden = sic1idenpers.getId().getIdTipoiden();
                            String codIden        = sic1idenpers.getId().getCodIden().trim();
                            
                            /* Validar si la persona que se quiere registrar ya existe
                            *  Si el ID_PERS es null quiere decir que es nuevo
                            */
                            if( sic1pers.getIdPers()== null){
                                BigDecimal id = DaoFuncionesUtil.FNC_SICOBTIDIDEN(cnConexion, "PERS", idTipoIden.intValue(), codIden);
                                if (id != null && id.intValue() > 0)
                                    return "-99";
                            }
                            
                            
                            intIdTipoLugar =  DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTIPOLUGAR", "VI_SICDIRECCION");
                            intIdTipoPersPN = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTIPOPERS", "N");
                            intIdTipoPersPJ = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTIPOPERS", "J");
                            
                            
                            if(sic1pers.getSic1persindi().getFecNaci() != null){
                                dtFecNaci = UtilClass.convertDateToString(sic1pers.getSic1persindi().getFecNaci());
                            }

                            if( sic1pers.getIdTipopers().intValue() == intIdTipoPersPN.intValue()){

                                //intIdEstaCivil = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICESTA", "VI_SICESTASOLT");
                                //intIdGenero = sic1pers.getSic1persindi().getIdGenero();

//                                if(sic1pers.getSic1persindi().getIdGenero().intValue() == Constantes.CONS_GENERO_MASCULINO){
//                                    sic1pers.getSic1persindi().setIdGenero(DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICGENERO", "M"));
//                                }else if(sic1pers.getSic1persindi().getIdGenero().intValue() == Constantes.CONS_GENERO_FEMENINO){
//                                    sic1pers.getSic1persindi().setIdGenero(DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICGENERO", "F"));
//                                }

                            }else if(sic1pers.getIdTipopers().intValue() == intIdTipoPersPJ.intValue()){

                                //intIdTipoEmpresa = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTIPOEMPR", "EIRLtda");
                                intIdTipoOrga = DaoFuncionesUtil.FNC_SICOBTIDGEN(cnConexion, "VI_SICTIPOORGA", "TOTROS");
                            }else{
                                throw new HibernateException("Tipo de Persona No Válida");
                            }
                            
                            
                            if (sic1idenpers.getFecDesde()!= null){
                                strFecDesde = UtilClass.convertDateToString(sic1idenpers.getFecDesde());
                            }
                            if (sic1idenpers.getFecHasta()!= null){
                                strFecHasta = UtilClass.convertDateToString(sic1idenpers.getFecHasta());
                            }
                            
                            //Sic1idenpers sic1idenpers = (Sic1idenpers)sic1pers.getSic1idenpers().iterator().next();

                            StoredProcedure sp = null;
                            sp = new StoredProcedure("PKG_SICMANTPERS.PRC_SICCREAPERS");                

                            sp.addParameter(new InParameter("X_ID_TIPOIDEN", Types.INTEGER, sic1idenpers.getId().getIdTipoiden().intValue()));
                            sp.addParameter(new InParameter("X_COD_IDEN", Types.VARCHAR, sic1idenpers.getId().getCodIden().trim()));
                            sp.addParameter(new InParameter("X_ID_TIPOPERS", Types.INTEGER, sic1pers.getIdTipopers().intValue()));
                            sp.addParameter(new InParameter("X_ID_TROLPERS", Types.INTEGER, sic1pers.getIdTrolpers()));
                            //Persona Juridica
                            sp.addParameter(new InParameter("X_DES_PERSORGA", Types.VARCHAR, sic1pers.getSic1persorga().getDesPersorga()));
                            sp.addParameter(new InParameter("X_DES_NOMBCOME", Types.VARCHAR, sic1pers.getSic1persorga().getDesPersorga()));
                            sp.addParameter(new InParameter("X_DES_GRUPEMPR", Types.VARCHAR, sic1pers.getSic1persorga().getDesGrupempr()));
                            sp.addParameter(new InParameter("X_ID_TIPOEMPR", Types.INTEGER, intIdTipoEmpresa));
                            sp.addParameter(new InParameter("X_ID_TIPOORGA", Types.INTEGER, intIdTipoOrga));
                            //Persona Natural
                            sp.addParameter(new InParameter("X_DES_PRIMNOMB", Types.VARCHAR, sic1pers.getSic1persindi().getDesPrimnomb()));
                            sp.addParameter(new InParameter("X_DES_SEGUNOMB", Types.VARCHAR, sic1pers.getSic1persindi().getDesSegunomb()));
                            sp.addParameter(new InParameter("X_DES_APELPATE", Types.VARCHAR, sic1pers.getSic1persindi().getDesApelpate()));
                            sp.addParameter(new InParameter("X_DES_APELMATE", Types.VARCHAR, sic1pers.getSic1persindi().getDesApelmate()));
                            sp.addParameter(new InParameter("X_ID_GENERO", Types.INTEGER, sic1pers.getSic1persindi().getIdGenero()));
                            sp.addParameter(new InParameter("X_FEC_NACI", Types.VARCHAR, dtFecNaci));
                            sp.addParameter(new InParameter("X_ID_ESTACIVIL", Types.INTEGER, intIdEstaCivil));
                            
                            sp.addParameter(new InParameter("COD_EMAIL", Types.VARCHAR, sic1pers.getCodEmail()));
                            sp.addParameter(new InParameter("DES_DIRECCION", Types.VARCHAR, sic1pers.getDesDireccion()));

                            sp.addParameter(new InParameter("X_DES_TELFFIJO", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_TELFCELU", Types.VARCHAR, sic1pers.getCodNumtele()));
                            sp.addParameter(new InParameter("X_DES_TELFFAX", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_CORREO", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_UBIGEO", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_ID_VIA", Types.INTEGER, null));
                            sp.addParameter(new InParameter("X_DES_VIA", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_ID_ZONA", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_ZONA", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_UBICAEXT", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_DES_UBICAINTE", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_COD_NUMDIRE", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_COD_NUMINTERIOR", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_ID_TIPODOMI", Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_FEC_DESDE", Types.VARCHAR, strFecDesde));
                            sp.addParameter(new InParameter("X_FEC_HASTA", Types.VARCHAR, strFecHasta));

                            sp.addParameter(new OutParameter("X_ID_PERS", Types.INTEGER));
                            sp.addParameter(new OutParameter("X_ID_ERROR", Types.INTEGER));
                            sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
                            sp.addParameter(new OutParameter("X_FEC_ERROR", Types.DATE));

                            sp.ExecuteNonQuery(cnConexion);

                            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                            }
                            
                            return sp.getParameter("X_ID_PERS").toString();
                            
                         }catch(Exception ex){
                            throw new HibernateException(ex.getMessage());
                        }
                     }
            });
            
            if (result.equals("-99"))
                throw new ValidationException("El documento de identidad ingresado ya existe");
            
            return result;
            
        } catch (HibernateException ex) {
            throw new SQLException(ex.getMessage());
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public String update(Sic1pers sic1pers) {
        String result = null;
//        tx = session.getTransaction();
//        
//        try {
//            session.clear();
//            session.update(sic1pers);
//            tx.commit();
//            
//        } catch (Exception e) {
//            result = e.getMessage();
//            tx.rollback();
//        }
        
        return result;        
    }

    
    public String delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public List<Sic1idenpers> getAll(Session session, Sic1pers obj) throws Exception{
        
        String consulta = "select idenpers from Sic1idenpers as idenpers, Sic1pers sic1pers where idenpers.sic1pers.idPers = sic1pers.idPers";
        //String consulta = "select idenpers from Sic1idenpers as idenpers, Sic1pers sic1pers left join fetch sic1pers.sic1persindi left join fetch sic1pers.sic1persorga where idenpers.sic1pers.idPers = sic1pers.idPers";
        
        if(obj.getIdPers() != null && obj.getIdPers().intValue() > 0)
            consulta += " and idenpers.sic1pers.idPers = " + obj.getIdPers().intValue();
        
        Query q = session.createQuery(consulta);
        
        List<Sic1idenpers> lstResult = (List<Sic1idenpers>)q.list();
        for(Sic1idenpers o : lstResult){
            Hibernate.initialize(o.getSic1pers());
        }
        
        System.out.println("Registros:"  + lstResult.size());
        
        return lstResult;
    }
    
    public Sic1idenpers getByCodiden(Session session, String codIden) throws Exception{
        
        Sic1idenpers sic1idenpers = null;
        try{        
            Criteria criteria = session.createCriteria(Sic1idenpers.class).add(Restrictions.eq("id.codIden", codIden));                     
        if(criteria != null){                    
            sic1idenpers = (Sic1idenpers)criteria.uniqueResult();
            if (sic1idenpers != null)
                Hibernate.initialize(sic1idenpers.getSic1pers());
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        
        return sic1idenpers;
    }
    
    public Sic1idenpers getById(Session session, BigDecimal id) throws Exception{       
        
        Sic1idenpers sic1idenpers = (Sic1idenpers)session.createCriteria(Sic1idenpers.class).add(Restrictions.eq("sic1pers.idPers", id)).uniqueResult();
        if (sic1idenpers != null)
            Hibernate.initialize(sic1idenpers.getSic1pers());
        return sic1idenpers;
    }
    
    
    public List<ViSicpers> listViSicPers(Session session, ViSicpers obj) {
               
        Criteria criteria = session.createCriteria(ViSicpers.class);
        
        if(obj.getCodTrolpers()!= null && obj.getCodTrolpers().trim().length() > 0)
            criteria.add(Restrictions.like("codTrolpers", '%' + obj.getCodTrolpers() + '%').ignoreCase());
        if(obj.getIdPers()!= null && obj.getIdPers().intValue() > 0)
            criteria.add(Restrictions.eq("idPers",obj.getIdPers()));
        if(obj.getCodIden() != null && obj.getCodIden().trim().length() > 0 )
            criteria.add(Restrictions.eq("codIden",obj.getCodIden()));
        if(obj.getIdTipoiden()!= null && obj.getIdTipoiden().intValue() > 0 )
            criteria.add(Restrictions.eq("idTipoiden",obj.getIdTipoiden()));
        if(obj.getIdTipopers()!= null && obj.getIdTipopers().intValue() > 0 )
            criteria.add(Restrictions.eq("idTipopers",obj.getIdTipopers()));
        if(obj.getDesPers()!= null && !obj.getDesPers().trim().isEmpty() )
            criteria.add( Restrictions.like("desPers",'%' + obj.getDesPers() + '%').ignoreCase());
        
        List<ViSicpers> lst = criteria.list();        

        return lst;
    }
    
}
