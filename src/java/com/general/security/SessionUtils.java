package com.general.security;

/**
 *
 * @author emoreno
 */
import com.general.hibernate.entity.Sic1usuario;
import com.general.hibernate.entity.Sic7persrol;
import com.general.util.beans.Constantes;
import java.math.BigDecimal;
import java.util.List;
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
    
    public static String getUserCompleteName() {
        String value;                
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        value = ((Sic1usuario)session.getAttribute("user")).getSic1idenpers().getSic1pers().getDesPers();        
        return value;
    }

    public static BigDecimal getUserId() {
        BigDecimal userId;
        HttpSession session = getSession();
        if (session != null){
            userId = ((Sic1usuario)session.getAttribute("user")).getIdUsuario();
            return userId;
        }
        else
            return null;
    }   
    
    public static Integer getIdSucursal() {
        Integer idSucursal;
        HttpSession session = getSession();
        if (session != null){
            idSucursal = ((Sic1usuario)session.getAttribute("user")).getIdSucursal();
            return idSucursal;
        }
        else
            return null;
    }   
    
    public static String getCodEstaCaja() {
        String val;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        val = ((Sic1usuario)session.getAttribute("user")).getCodEstacaja();
        return val;
    }
    
    public static String getCodTRolPers() {
        String val;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        val = ((Sic1usuario)session.getAttribute("user")).getCodTrolpers();
        return val;
    }
    
    public static List<Sic7persrol> getLstTRolPers() {        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        List<Sic7persrol> lstRoles = ((Sic1usuario)session.getAttribute("user")).getLstSic7persrol();
        return lstRoles;
    }
    
    public static String getCodigosRolPers() {
        String val = "" ;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        List<Sic7persrol> lstRoles = ((Sic1usuario)session.getAttribute("user")).getLstSic7persrol();
        
        for(Sic7persrol obj : lstRoles){
            val = val + "|" + obj.getCodTrolpers();
        }
        
        return val;
    }
    
    public static Boolean flgRolAdmin() {
        Boolean flgResultado = false;
        String codRolesAsignados = SessionUtils.getCodigosRolPers();
        
        if(codRolesAsignados.contains(Constantes.CONS_COD_ADMINISTRADOR))
            flgResultado = true;            
               
        return flgResultado;
    }
    
    public static void setCodEstaCaja(String codEstaCaja) {
    
        /*Actualizando el estado de caja en la Session*/
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Sic1usuario user = (Sic1usuario)session.getAttribute("user");
        user.setCodEstacaja(codEstaCaja);
        session.setAttribute("user", user);
    }
    
    
}
