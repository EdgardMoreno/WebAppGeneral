/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

/**
 *
 * @author emoreno
 */
public class Constantes {
    
    
    
//    public static Integer CONS_GENERO_MASCULINO = 1;
//    public static Integer CONS_GENERO_FEMENINO = 2;
    
    public static Integer CONS_VALUE_TIPOPERS_RUC = 11;
    public static Integer CONS_VALUE_TIPOPERS_DNI = 8;
    public static Integer CONS_VALUE_AUTOCOMPLETE_NUMROWS = 8;
    
    public static double CONS_VALUE_IGV = 0.18;
    
    
    
    /*VISA*/
    //1: Comisión Sobre el Importe de la Venta cuando se paga con Debito.
    public static double CONS_VALUE_COMISION_VISA_DEBITO = 0.0299;
    //2: Comisión Sobre el Importe de la Venta cuando se paga con Crédito.
    public static double CONS_VALUE_COMISION_VISA_CREDITO = 0.0399;
    //3: Del monto obtenido en el punto 1 y 2 se obtiene la comision que va para el BANCO
    public static double CONS_VALUE_COMISION_VISA_BANCO = 0.80;
    //4: Del monto obtenido en el punto 1 y 2 se obtiene la comision que va para VISA
    public static double CONS_VALUE_COMISION_VISA_VISA = 0.20;
    //5: Del monto obtenido en el punto 4 se obtiene se el IGV.
    public static double CONS_VALUE_COMISION_VISA_VISA_IGV = 0.18;
    /**/
    
    
    /*CODIGO DE TIPO DE IDENTIFICADORES*/
    public static String CONS_COD_GENERO            = "VI_SICGENERO";
        /*CODIGOS*/
        public static String CONS_COD_GENERO_MASCULINO   = "M";
        public static String CONS_COD_GENERO_FEMENINO    = "F";
    
    /*CODIGO DE TIPO DE IDENTIFICADORES*/
    public static String CONS_COD_TIPOPERS          = "VI_SICTIPOPERS";
        /*CODIGOS*/
        public static String CONS_COD_TIPOPERS_NATURAL   = "N";
        public static String CONS_COD_TIPOPERS_JURIDICO  = "J";
    
    /*CODIGO DE TIPO DE IDENTIFICADORES*/
    public static String CONS_COD_TIPOIDEN          = "VI_SICTIPOIDEN";
        /*CODIGOS*/
        public static String CONS_COD_TIPOIDEN_DNI           = "DNI";
        public static String CONS_COD_TIPOIDEN_RUC           = "RUC";
        public static String CONS_COD_TIPOIDEN_OTROS_NAT     = "OTNAT";
        
        
    
    /*CODIGO DE TIPO DE RELACIONES*/
    public static String CONS_COD_TIPORELA          = "VI_SICTRELA";
        /*CODIGO DE RELACIONES*/
        public static String CONS_COD_RELADOCUPERS  = "RELDOCUPERS";
    
    /*CODIGO DE TIPO ROL DE PERSONAS*/
    public static String CONS_COD_TIPOROLPERS       = "VI_SICTROLPERS";
        /*CODIGOS*/
        public static String CONS_COD_VENDEDOR      = "VI_SICVENDEDOR";
        public static String CONS_COD_NOAPLICA      = "VI_SICNOAPLICA";
        public static String CONS_COD_TROLPROVEEDOR = "VI_SICTROLPROVEEDOR";
        public static String CONS_COD_TROLCLIENTE   = "VI_SICTROLCLIENTE";
    
    /*CODIGO DE TIPO ROL DE ESTADOS*/
    public static String CONS_COD_TIPOROLESTA       = "VI_SICTROLESTA";    
        /*CODIGOS*/
        public static String CONS_COD_ESTADOCU_COMPROBANTE  = "VI_SICESTADOCUCOMPROBANTE";
        public static String CONS_COD_ESTADOCU_INFORME      = "VI_SICESTADOCUINF";
        
    /*CODIGO DE ESTADOS*/
    public static String CONS_COD_ESTA              = "VI_SICESTA";
        /*CODIGOS*/
        public static String CONS_COD_ESTAFINALIZADO       = "VI_SICESTAFINALIZADO";
        public static String CONS_COD_ESTAPORRECOGER       = "VI_SICESTAPORRECOGER";
        public static String CONS_COD_ESTAANULADO          = "VI_SICESTAANULADO";
        public static String CONS_COD_ESTAELIMINADO        = "VI_SICESTAELIM";
        public static String CONS_COD_ESTACREADO           = "VI_SICESTACREA";
        public static String CONS_COD_ESTACERRADO          = "VI_SICESTACERR";
        public static String CONS_COD_ESTAPENDIENTE        = "VI_SICESTAPEND";
        
    /*CODIGO DE SUB CLASE DE EVENTO*/
    public static String CONS_COD_SCLASEEVEN            = "VI_SICSCLASEEVEN";
        /*CODIGOS*/
        public static String CONS_COD_SCLASEEVEN_VENTA              = "VI_SICSCLASEEVENVENTA";
        public static String CONS_COD_SCLASEEVEN_COMPRA             = "VI_SICSCLASEEVENCOMPRA";
        public static String CONS_COD_SCLASEEVEN_CTRL_INVENTARIO    = "VI_SICSCLASEEVENCTRLINVENT";
        

    /*CODIGO DE SUB TIPO DE DOCUMENTO*/
    public static String CONS_COD_STIPODOCU            = "VI_SICSTIPODOCU";        
        /*CODIGOS*/
        public static String CONS_COD_STIPODOCU_SINDOCU             = "VI_SICSINDOCU";
        public static String CONS_COD_STIPODOCU_FACTURA             = "VI_SICFACTURA";
        public static String CONS_COD_STIPODOCU_BOLETA              = "VI_SICBOLETA";
    
    /*MENSAJES*/
    public static String CONS_SUCCESS_MESSAGE = "Se grabó con éxito";
}
