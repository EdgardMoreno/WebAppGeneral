/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.dao;

import com.general.util.beans.Comision;
import conexionbd.InParameter;
import conexionbd.OutParameter;
import conexionbd.StoredProcedure;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Edgard
 */
public class DaoUtilClass {
 
    public Comision calculateCommission(Connection cnConexion, Comision comision) throws Exception {
                
        try {
            
            StoredProcedure sp = null;
            sp = new StoredProcedure("PRC_SICOBTCOMISIONES");
            
            sp.addParameter(new InParameter("X_NUM_IMPORTE",           Types.NUMERIC, comision.getNum_Importe()));
            sp.addParameter(new InParameter("X_ID_MODAPAGO",           Types.INTEGER, comision.getId_Modapago()));
            sp.addParameter(new InParameter("X_ID_TIPOTARJETA",        Types.INTEGER, comision.getId_TipoTarjeta()));
            
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_VISA_DEBITO",    Types.NUMERIC));
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_VISA_CREDITO",   Types.NUMERIC));
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_VISA_BANCO",     Types.NUMERIC));
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_VISA_VISA",      Types.NUMERIC));
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_VISA_VISA_IGV",  Types.NUMERIC));
            sp.addParameter(new OutParameter("X_TOTAL_COMISION_FINAL",          Types.NUMERIC));                      
            
            sp.addParameter(new OutParameter("X_ID_ERROR",  Types.INTEGER));
            sp.addParameter(new OutParameter("X_DES_ERROR", Types.VARCHAR));
            sp.addParameter(new OutParameter("X_FEC_ERROR", Types.DATE));
            
            sp.ExecuteNonQuery(cnConexion);
            
            comision.setNum_TotalComision_Final(new BigDecimal(sp.getParameter("X_TOTAL_COMISION_FINAL").toString())); 

            if (Integer.valueOf(sp.getParameter("X_ID_ERROR").toString()) != 0 ){
                throw new SQLException((String)sp.getParameter("X_DES_ERROR"));
            }
            
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
        return comision;
    }
    
}
