/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.security.SessionUtils;
import com.general.a2.service.impl.LoginServiceImpl;
import com.general.hibernate.entity.Sic1usuario;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author emoreno
 */

@ManagedBean
@SessionScoped
public class LoginController implements Serializable{
    
    
    private String userName;
    private String passWord;
    private Sic1usuario user;
    
    public LoginController(){
        //https://www.journaldev.com/7252/jsf-authentication-login-logout-database-example
    }
    
    @PostConstruct
    public void init(){
        user = new Sic1usuario();
    }
    
    /*ATRIBUTOS*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Sic1usuario getUser() {
        return user;
    }

    public void setUser(Sic1usuario user) {
        this.user = user;
    }
   
    
    
    /*METODOS*/
    public String validateUsernamePassword() throws CustomizerException{
        
        System.out.println("Login:" + SessionUtils.getSession());
        System.out.println("Validate Usuario:");
        System.out.println("Usuario: " + this.userName);
        System.out.println("PWD: " + this.passWord);
        
        LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
        this.user = loginServiceImpl.validateUsernamePassword(this.userName, this.passWord);
        
        if (user != null) {       
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", user);
            return "index";
        }else{
            UtilClass.addErrorMessage("Usuario o contraseña incorrecta.");
            return "";
        }
    }
    
}
