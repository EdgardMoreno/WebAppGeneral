/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.interfac.dao;

import com.general.hibernate.entity.Sic1general;
import com.general.hibernate.entity.Sic1stipodocu;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author emoreno
 */
public interface DaoSic1general {
    
     public List<Sic1general> listByCod_ValorTipoGeneral(Connection cnConexion, List<String> list);
     
     public List<Sic1general> listByCod_ValorGeneral(Connection cnConexion, List<String> list);
     
     public List<Sic1stipodocu> listByCod_STipoDocu(Connection cnConexion, List<String> list);

}
