/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a3.dao.impl;

import com.general.hibernate.entity.Sic1prod;
import com.general.hibernate1.Sic1docukardex;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;

/**
 *
 * @author emoreno
 */
public class DaoDocuKardexImpl {
    


    /*METODOS*/
    
    /*Graba el inventario realizado*/
    public void insert(Session session, List<Sic1docukardex> list, BigDecimal idDocu) throws Exception {
              
        try {           
            
            session.doWork(new Work() {
                @Override
                public void execute(Connection cnctn) throws SQLException {                   
                    
                    try {                        

                        for(Sic1docukardex obj : list){

                            StoredProcedure sp = new StoredProcedure("PKG_SICMANTDOCU.PRC_SICCREADOCUKARDEX");

                            sp.addParameter(new InParameter("X_ID_DOCU",        Types.INTEGER, idDocu));
                            sp.addParameter(new InParameter("X_NUM_PERI",       Types.INTEGER, obj.getId().getNumPeri()));
                            sp.addParameter(new InParameter("X_ID_ESTAB",       Types.INTEGER, new BigDecimal(1)/*obj.getId().getIdEstab()*/));
                            sp.addParameter(new InParameter("X_ID_PROD",        Types.INTEGER, obj.getId().getIdProd()));
                            
                            //Persona Juridica
                            sp.addParameter(new InParameter("X_FEC_CREACION",   Types.VARCHAR, null));
                            sp.addParameter(new InParameter("X_NUM_CANTINI",    Types.NUMERIC, obj.getNumCantini()));
                            sp.addParameter(new InParameter("X_NUM_CANTINGR",   Types.NUMERIC, obj.getNumCantingr()));
                            sp.addParameter(new InParameter("X_NUM_CANTSALI",   Types.NUMERIC, obj.getNumCantsali()));
                            sp.addParameter(new InParameter("X_NUM_CANTSTOCK",  Types.NUMERIC, obj.getNumCantstock()));
                            sp.addParameter(new InParameter("X_ID_ESTA",        Types.INTEGER, null));

                            sp.addParameter(new OutParameter("X_ID_ERROR",      Types.INTEGER));
                            sp.addParameter(new OutParameter("X_DES_ERROR",     Types.VARCHAR));
                            sp.addParameter(new OutParameter("X_FEC_ERROR",     Types.DATE));

                            sp.ExecuteNonQuery(cnctn);

                            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
                            }  
                        }
                    }catch(Exception ex){
                        throw new HibernateException(ex.getMessage());
                    }
                }
            });
            
        } catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }  
    
    
    /*Se obtiene el ultimo periodo activo del inventario*/
    public int getKardexLastPeriActi(Session session) throws Exception{

        int result = 0;
        try{
        
            CallableStatement call = ((SessionImpl)session).connection().prepareCall("{ ? = call FNC_SICOBTKARDEXPERIACTIVO() }");
            call.registerOutParameter( 1, Types.INTEGER );
            call.execute();

            result =  call.getInt(1);
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
            
        return result;        
    }
    
    /*Se obtiene el kardex del periodo en consulta*/
    public List<Sic1docukardex> getKardexByNumPeri(Connection conexion, Integer numPeri ) throws Exception {

        List<Sic1docukardex> list;
        
        try {
            
            ResultSet rs;                        
            StoredProcedure sp = new StoredProcedure("PRC_SICOBTPLANTKARDEX");

            sp.addParameter(new InParameter("X_NUM_PERI", Types.INTEGER, numPeri));
            sp.addParameter(new OutParameter("X_CURSOR",  OracleTypes.CURSOR));

            rs = sp.ExecuteResultCursor(conexion, 2);        
            
            Sic1docukardex obj;
            list = new ArrayList<>();
            
            while (rs.next()) {
                
                obj = new Sic1docukardex();                
                Sic1prod prod = new Sic1prod();
                
                prod.setIdProd(rs.getBigDecimal("ID_PROD"));
                prod.setCodProd(rs.getString("COD_PROD"));
                prod.setDesProd(rs.getString("DES_PROD"));
                prod.setDesStipoprod(rs.getString("DES_STIPOPROD"));
                
                obj.setSic1prod(prod);
                
                obj.setNumCantini(rs.getBigDecimal("NUM_CANTINI"));
                obj.setNumCantingr(rs.getBigDecimal("NUN_CANTINGR"));
                obj.setNumCantsali(rs.getBigDecimal("NUM_CANTSALI"));
                obj.setNumCantstock(rs.getBigDecimal("NUM_CANTSTOCK"));
                
                list.add(obj);
            }
                   
            
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return list;
    }
    
}
