package com.general.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emoreno
 */
import com.general.hibernate.entity.Sic1usuario;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static HttpSession getSession() {
            return (HttpSession) FacesContext.getCurrentInstance()
                            .getExternalContext().getSession(false);//True: Si no existe la sesion, la crea.
                                                                    //False: Obtiene la session existente, si no hay devuelve null.
    }

    public static HttpServletRequest getRequest() {
            return (HttpServletRequest) FacesContext.getCurrentInstance()
                            .getExternalContext().getRequest();
    }

    public static String getUserName() {
        String userName;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        userName = ((Sic1usuario)session.getAttribute("user")).getCodUsuario();
        return userName;
    }

    public static String getUserId() {
        String userId;
        HttpSession session = getSession();
        if (session != null){
            userId = ((Sic1usuario)session.getAttribute("user")).getIdUsuario().toString();
            return userId;
        }
        else
            return null;
    }
}
