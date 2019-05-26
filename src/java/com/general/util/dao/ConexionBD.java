/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.dao;

import com.general.a2.service.impl.FacturadorSunatServiceImpl;
import com.general.a3.dao.impl.DaoFacturadorSunatImpl;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD implements Serializable{
        
    
    private Connection cnConexion;
    private String _strNombreHost;
    private String _strNombreBD;
    private String _strUsuario;
    private String _strContrasenia;
    private int _intFilaError;
    private String _strError;
    
    public Connection getCnConexion() {
        return cnConexion;
    }

    public void setCnConexion(Connection cnConexion) {
        this.cnConexion = cnConexion;
    }
    
    public int getFilaError() {
        return _intFilaError;
    }

    public void setFilaError(int intFilaError) {
        _intFilaError = intFilaError;
    }   
    
    public String getNombreHost() {
        return _strNombreHost;
    }

    public void setNombreHost(String strNombreHost) {
        _strNombreHost = strNombreHost;
    }

    public String getNombreBD() {
        return _strNombreBD;
    }

    public void setNombreBD(String strNombreBD) {
        _strNombreBD = strNombreBD;
    }

    public String getUsuario() {
        return _strUsuario;
    }

    public void setUsuario(String strUsuario) {
        _strUsuario = strUsuario;
    }

    public String getContrasenia() {
        return _strContrasenia;
    }

    public void setContrasenia(String strContrasenia) {
        _strContrasenia = strContrasenia;
    }
    
    
    public String getError() {
        return _strError;
    }

    public void setError(String pstrOrigen, int pintFilaError, String pstrIden, String pstrErrorDesc) {
        _strError = pstrOrigen + "-FILAERROR:" + pintFilaError + "-IDENT: " + pstrIden + " ERRDESC:" + pstrErrorDesc;
    }
    
    public static Connection obtConexion() throws SQLException, Exception {
        
        Connection cnConexion = null;
        try {
            
            System.out.println("====== obtenerConexion =========");
            
            String strNombreHost = "localhost";
            String strNombreBD = "XE";
            String strUsuario = "SICDBA";
            String strContrasenia = "Oracle01";

            /*DESARROLLO*/
            String strCadenaCon = "jdbc:oracle:thin:@" + strNombreHost + ":1521:" + strNombreBD;            
                       
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            cnConexion =  DriverManager.getConnection(strCadenaCon, strUsuario, strContrasenia);
            cnConexion.setAutoCommit(false);

            if(cnConexion == null){
                throw new Exception("Conexión nula");
            }

        } catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }

        return cnConexion;
    }
    
    public static Connection obtConexionBDFacturadorSunat() throws SQLException, Exception {
        
        Connection cnConexion = null;
        try {
            
            System.out.println("====== obtConexionBDFacturadorSunat =========");
            
            //DaoFacturadorSunatImpl objDao = new DaoFacturadorSunatImpl();
            String strUrl = FacturadorSunatServiceImpl.obtRutaBDFacturador();
            System.out.println("strUrl:" + strUrl);
            
            Class.forName("org.sqlite.JDBC");
            cnConexion = DriverManager.getConnection("jdbc:sqlite:" + strUrl);
            cnConexion.setAutoCommit(false);
            
            if(cnConexion == null){
                throw new Exception("Conexión nula");
            }

        }
        catch(SQLException e){
            throw new Exception(e.getMessage());
        }

        return cnConexion;
    }
   
}
