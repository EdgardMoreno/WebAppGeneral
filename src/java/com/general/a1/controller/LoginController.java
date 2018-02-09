/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.LoginServiceImpl;
import com.general.a2.service.impl.UserServiceImpl;
import com.general.hibernate1.Sic1usuario;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author emoreno
 */

@ManagedBean
@SessionScoped
public class LoginController implements Serializable{
    
    private Sic1usuario usuario;
    
    public LoginController(){
        
    }
    
    @PostConstruct
    public void init(){
        usuario = new Sic1usuario();
    }
    
    /*ATRIBUTOS*/
    public Sic1usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Sic1usuario usuario) {
        this.usuario = usuario;
    }    
    
    
    /*METODOS*/            
    public String validateUsernamePassword() throws CustomizerException{        
        
        System.out.println("Validate Usuario:");
        System.out.println("Usuario: " + this.usuario.getCodUsuario());
        System.out.println("PWD: " + this.usuario.getCodPwd());
        
        LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
        this.usuario = loginServiceImpl.validateUsernamePassword(this.usuario);
        
        return "";
    }
    
}
