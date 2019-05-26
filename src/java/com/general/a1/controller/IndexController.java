/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a1.controller;

import com.general.a2.service.impl.CashRegisterServiceImpl;
import com.general.a2.service.impl.ReportServiceImpl;
import com.general.hibernate.temp.Sic4cuaddiario;
import com.general.hibernate.temp.Sic4cuaddiarioId;
import com.general.security.SessionUtils;
import com.general.util.beans.Constantes;
import com.general.util.beans.Meta;
import com.general.util.beans.UtilClass;
import com.general.util.exceptions.CustomizerException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author emoreno
 */
@ManagedBean
@RequestScoped
public class IndexController {
    
    private int flgCajaAperturada;
    private BigDecimal numMtoApertura;       
        
    private Double numPorcAlcanzadoPapel = 0.00;
    private Double numPorcAlcanzadoVinil = 0.00;
    
    private List<Meta> lstMeta;

    
    public IndexController() throws CustomizerException{
    
        try {
            
            String codEstaCaja = SessionUtils.getCodEstaCaja();
            String codRolesAsignados = SessionUtils.getCodigosRolPers(); //Se obtiene todos los codigos concatenados
            this.flgCajaAperturada = 0;
            
            /*Si es vendedor se valida el estado de la caja*/
            if (codRolesAsignados.contains(Constantes.CONS_COD_VENDEDOR)){
            
                if (codEstaCaja != null && codEstaCaja.equalsIgnoreCase(Constantes.CONS_COD_ESTACERRADO))
                    this.flgCajaAperturada = 1;
                else if (codEstaCaja != null && codEstaCaja.equalsIgnoreCase(Constantes.CONS_COD_ESTACREADO))
                    this.flgCajaAperturada = 1;
                else
                    this.flgCajaAperturada = 0;
            }else
                this.flgCajaAperturada = 1;//Cuando es otro rol el sistema no debe solicitar aperturar caja
            
            /*Comprobar si la caja esta aperturada
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();
            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(SessionUtils.getUserId()); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));
            Sic4cuaddiario obj = service.getById(id);
            
            if (obj != null)
                this.flgCajaAperturada = 1;*/
            ReportServiceImpl objService = new ReportServiceImpl();
            Integer numPeri = UtilClass.getCurrentTime_YYYYMM();
            lstMeta = new ArrayList<>();
            Integer idPers = 0;
            
            if (codRolesAsignados.contains(Constantes.CONS_COD_ADMINISTRADOR)){                
                
                if (codRolesAsignados.contains(Constantes.CONS_COD_VENDEDOR)){
                    idPers = SessionUtils.getUserId().intValue();
                }
                
                lstMeta = objService.obtTotalVentaXLineaProductos(numPeri, idPers);
            
            }else{
                
                if (codRolesAsignados.contains(Constantes.CONS_COD_VENDEDOR)){
                    idPers = SessionUtils.getUserId().intValue();
                }
                
                List<Meta> lstMetaTmp = objService.obtTotalVentaXLineaProductos(numPeri, idPers);
                for(Meta obj : lstMetaTmp){
                    if(obj.getCodStipoprod().equals("VI_SICVINILCORTE"))
                        this.numPorcAlcanzadoVinil = obj.getNumPorclogrado().doubleValue();
                    else if(obj.getCodStipoprod().equals("VI_SICPAPELTAPIZ"))
                        this.numPorcAlcanzadoPapel = obj.getNumPorclogrado().doubleValue();
                }
            }
            

            //this.numPorcAlcanzadoPapel = new BigDecimal((this.numTotalVentasMesPapel/Constantes.CONS_METAMESTOTALVENTAPAPEL) * 100).setScale(2,BigDecimal.ROUND_HALF_UP ).doubleValue();
            
        } catch (Exception ex) {
            throw new CustomizerException(ex.getMessage());
        }        
    }
    
    
    
    /*PROPIEDAS*/
    public int getFlgCajaAperturada() {
        return flgCajaAperturada;
    }

    public void setFlgCajaAperturada(int flgCajaAperturada) {
        this.flgCajaAperturada = flgCajaAperturada;
    }

    public BigDecimal getNumMtoApertura() {
        return numMtoApertura;
    }

    public void setNumMtoApertura(BigDecimal numMtoApertura) {
        this.numMtoApertura = numMtoApertura;
    }

    public List<Meta> getLstMeta() {
        return lstMeta;
    }

    public void setLstMeta(List<Meta> lstMeta) {
        this.lstMeta = lstMeta;
    }

    public Double getNumPorcAlcanzadoPapel() {
        return numPorcAlcanzadoPapel;
    }

    public void setNumPorcAlcanzadoPapel(Double numPorcAlcanzadoPapel) {
        this.numPorcAlcanzadoPapel = numPorcAlcanzadoPapel;
    }

    public Double getNumPorcAlcanzadoVinil() {
        return numPorcAlcanzadoVinil;
    }

    public void setNumPorcAlcanzadoVinil(Double numPorcAlcanzadoVinil) {
        this.numPorcAlcanzadoVinil = numPorcAlcanzadoVinil;
    }
    
    
    
    
    /*METODOS*/
    
    public void openCashRegister() throws CustomizerException{
        
        try{
            System.out.println("Aperturando Caja" );
            CashRegisterServiceImpl service = new CashRegisterServiceImpl();

            Sic4cuaddiarioId id = new Sic4cuaddiarioId();
            id.setIdPers(SessionUtils.getUserId()); //Ira el ID_PERS DEL USUARIO LOGUEADO
            id.setNumPeri(new BigDecimal(UtilClass.getCurrentTime_YYYYMMDD()));

            Sic4cuaddiario obj = new Sic4cuaddiario();
            obj.setId(id);
            obj.setNumEfectApertCaja(this.numMtoApertura);
            obj.setIdSucursal(SessionUtils.getIdSucursal());
            service.open(obj);
            
            
            /*Actualizando el nuevo estado de la caja en la Session*/
            SessionUtils.setCodEstaCaja(Constantes.CONS_COD_ESTACREADO);
            
            this.flgCajaAperturada = 1;

            UtilClass.addInfoMessage("Caja aperturada correctamente.");
        }catch(Exception ex){
            throw new CustomizerException(ex.getMessage());
        }        
    }    
}
