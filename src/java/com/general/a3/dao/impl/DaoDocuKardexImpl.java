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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author emoreno
 */
public class DaoDocuKardexImpl {
    


    /*METODOS*/    
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
