/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.dao;

import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;


/**
 *
 * @author emoreno
 */
public class DaoFuncionesUtil {
    
    public static BigDecimal FNC_SICOBTIDGEN(Connection conexion, String X_COD_VALORTIPOGENERAL, String X_COD_VALORGENERAL) throws Exception {
        
        BigDecimal result;
        
        try{
        
            CallableStatement call = conexion.prepareCall("{ ? = call PKG_SICCONSGENERAL.FNC_SICOBTIDGEN(?,?) }");
            call.registerOutParameter( 1, Types.INTEGER ); // or whatever it is
            call.setString(2, X_COD_VALORTIPOGENERAL);
            call.setString(3, X_COD_VALORGENERAL);
            call.execute();

            result =  call.getBigDecimal(1);
        
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        return result;
        
    } 
    
    public static ResultSet FNC_OBTLISTGENERAL(Connection cnConexion, String strQuery) throws SQLException{
        
        ResultSet rsConsulta = null;        
        StoredProcedure Sp = null;        
        Sp = new StoredProcedure("");
        rsConsulta= Sp.ExecuteResultSet(cnConexion, strQuery);
        
        return rsConsulta;
    } 
    
    public static BigDecimal FNC_SICOBTCOMISION( Session session
                                            , double L_NUM_IMPORTE
                                            , Integer L_ID_MODAPAGO
                                            , Integer L_ID_TIPOTARJETA ) throws SQLException, Exception{        
        
        Connection cnConexion = null;
        BigDecimal result = null;
        try{
            cnConexion = ((SessionImpl)session).connection();
            CallableStatement call = cnConexion.prepareCall("{ ? = call FNC_SICOBTCOMISION(?,?,?) }");
            call.registerOutParameter( 1, Types.NUMERIC ); // or whatever it is
            call.setDouble(2, L_NUM_IMPORTE);
            call.setInt(3, L_ID_MODAPAGO);
            call.setInt(4, L_ID_TIPOTARJETA);
            call.execute();
            
            result = call.getBigDecimal(1);
            
        }catch(Exception ex){            
            throw new Exception(ex.getMessage());
        }
        return result; // propagate this back to enclosing class
        
    } 
    
    /*public static Integer FNC_SICOBTIDGEN(Session session, String X_COD_VALORTIPOGENERAL, String X_COD_VALORGENERAL){
        
        int result = session.doReturningWork(new ReturningWork<Integer>() {
        
        @Override
         public Integer execute(Connection cnConexion) throws SQLException {
          CallableStatement call = cnConexion.prepareCall("{ ? = call PKG_SICCONSGENERAL.FNC_SICOBTIDGEN(?,?) }");
          call.registerOutParameter( 1, Types.INTEGER ); // or whatever it is
          call.setString(2, X_COD_VALORTIPOGENERAL);
          call.setString(3, X_COD_VALORGENERAL);
          call.execute();
          return call.getInt(1); // propagate this back to enclosing class
        }
        
        });
        
        return result;
    }  */
    
}
