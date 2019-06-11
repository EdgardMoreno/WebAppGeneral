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
    public static Double CONS_METAMESTOTALVENTAPAPEL = 14000.00;
    public static Double CONS_METAMESTOTALVENTAVINIL = 3000.00;
    
    public static double CONS_VALUE_IGV = 0.18;
    
    
    public static String CONS_COD_COLORTABLA = "#FFC300";
    public static String CONS_COD_COLORTABLATOTAL = "#3b83bd";
    
    public static String CONS_DES_NOMB_COMERCIAL = "DECO TAPIZ";
    public static String CONS_DES_RAZON_SOCIAL = "INVERSIONES EMZA E.I.R.L.";
    public static String CONS_NUM_RUC = "20603693524";
    
    /*REMITENTE*/
    public static String CONS_DES_EMAIL_REMITENTE = "decotapizz.info@gmail.com";
    public static String CONS_DES_EMAIL_DESTINATARIO = "decotapizz.info@gmail.com";
    public static String CONS_DES_EMAIL_ADMINISTRADOR = "edgardmr07@gmail.com";
    public static String CONS_DES_EMAIL_REMITENTE_PWD = "Oracle01";
    

    
    /*DIRECTORIOS FACTURADOR SFS SUNAT QUE CONTIENEN LOS ARCHIVOS GENERADOS EN LA GENERACION DE LA FACTURACION ELECTRONICA*/
    public static String CONS_RUTA_FACTURADOR_DATA = "\\DATA";
    public static String CONS_RUTA_FACTURADOR_ENVIO = "\\ENVIO";
    public static String CONS_RUTA_FACTURADOR_FIRMA = "\\FIRMA";
    public static String CONS_RUTA_FACTURADOR_RPTA = "\\RPTA";
    
    public static String CONS_ESTA_SUNAT_IND_SITU_POR_GENERAL_XML   = "01";
    public static String CONS_ESTA_SUNAT_IND_SITU_XML_GENERADO      = "02";
    public static String CONS_ESTA_SUNAT_IND_SITU_ENVIADO_ACEPTADO  = "03";    
    public static String CONS_ESTA_SUNAT_IND_SITU_CON_ERRORES       = "06";
    
    
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
    
    /*CODIGOS DE SUB TIPO DE PRODUCTOS*/
    public static String CONS_COD_STIPOPROD_VINILCORTE = "VI_SICVINILCORTE";
    public static String CONS_COD_STIPOPROD_STICKER = "VI_SICSTICKER";
    public static String CONS_COD_STIPOPROD_CATALOGO = "VI_SICCATALOGO";
    public static String CONS_COD_STIPOPROD_PEGAMENTO = "VI_SICPEGAMENTO";
    
    
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
        public static String CONS_COD_RELADOCUDOCU  = "RELDOCUDOCU";
    
    /*CODIGO DE TIPO ROL DE PERSONAS*/
    public static String CONS_COD_TIPOROLPERS       = "VI_SICTROLPERS";
        /*CODIGOS*/
        public static String CONS_COD_VENDEDOR      = "VI_SICVENDEDOR";
        public static String CONS_COD_SUPERVISOR    = "VI_SICSUPERVISOR";
        public static String CONS_COD_ADMINISTRADOR = "VI_SICADMIN";
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
        public static String CONS_COD_ESTANOTIFICADO       = "VI_SICESTANOTI";
        
    /*CODIGO DE CLASE DE EVENTO*/
    public static String CONS_COD_CLASEEVEN                = "VI_SICCLASEEVEN";
        /*CODIGOS*/
        public static String COD_CLASEEVEN_GASTOS              = "VI_SICGASTOS";
        public static String CLASEEVEN_OPERACIONESVENTA        = "VI_SICOPERACIONESVENTA";
        
    
    /*CODIGO DE SUB CLASE DE EVENTO*/
    public static String CONS_COD_SCLASEEVEN            = "VI_SICSCLASEEVEN";
        /*CODIGOS*/
        public static String COD_SCLASEEVEN_VENTA              = "VI_SICSCLASEEVENVENTA";
        public static String COD_SCLASEEVEN_COMPRA             = "VI_SICSCLASEEVENCOMPRA";
        public static String COD_SCLASEEVEN_ORDENCOMPRA        = "VI_SICSCLASEEVENORDENCOMPRA";
        public static String COD_SCLASEEVEN_NOTACREDITO        = "VI_SICSCLASEEVENNOTACREDITO";
        public static String COD_SCLASEEVEN_NOTADEBITO         = "VI_SICSCLASEEVENNOTADEBITO";
        public static String COD_SCLASEEVEN_CTRL_INVENTARIO    = "VI_SICSCLASEEVENCTRLINVENT";
            
    /*CODIGO DE TIPO DE DOCUMENTO*/
    public static String CONS_COD_TIPODOCU            = "VI_SICTIPODOCU";        
        /*CODIGOS*/
        public static String CONS_COD_TIPODOCU_COMPROBANTEPAGO     = "COMPROBANTEPAGO";        
        
    /*CODIGO DE SUB TIPO DE DOCUMENTO*/
    public static String CONS_COD_STIPODOCU            = "VI_SICSTIPODOCU";        
        /*CODIGOS*/
        
        public static String CONS_COD_STIPODOCU_FACTURA             = "VI_SICFACTURA";
        public static String CONS_COD_STIPODOCU_BOLETA              = "VI_SICBOLETA";
        public static String CONS_COD_STIPODOCU_SINDOCU             = "VI_SICSINDOCU";
        public static String CONS_COD_STIPODOCU_RECIBOHONOR         = "VI_SICRECIBOHONORARIO";
        public static String CONS_COD_STIPODOCU_BOLETOVIAJE         = "VI_SICBOLETOVIAJE";
        public static String CONS_COD_STIPODOCU_NOTAVENTA           = "VI_SICNOTAVENTA";
        public static String CONS_COD_STIPODOCU_OTROSPAGOS          = "VI_SICOTROPAGO";
        public static String CONS_COD_STIPODOCU_NOTAPEDIDO          = "VI_SICNOTAPEDIDO";        
        
    /*CODIGO DE MODO DE PAGO*/
    public static String COD_MODOPAGO                   = "VI_SICMODAPAGO";
        /*CODIGOS*/
        public static String COD_MODOPAGO_TRANSFER         = "VI_SICTRANSFE";
        public static String COD_MODOPAGO_DEPOSITO         = "VI_SICDEPOSITO";
        public static String COD_MODOPAGO_EFECTIVO         = "VI_SICEFECTIVO";
        public static String COD_MODOPAGO_TARJDEBITO       = "VI_SICTARJDEBITO";
        public static String COD_MODOPAGO_TARJCREDITO      = "VI_SICTARJCREDITO";
        public static String COD_MODOPAGO_NINGUNO          = "VI_SICNINGUNO";
    
    /*CODIGO MOTIVOS DE NOTA DE CREDITO*/
    public static String VI_SICTIPONOTACREDITO          = "VI_SICTIPONOTACREDITO";
        public static String TIPONOTACREDANULOPERACION      = "TIPONOTACREDANULOPERACION";
        public static String TIPONOTACREDANULERRORRUC       = "TIPONOTACREDANULERRORRUC";
        public static String TIPONOTACREDDEVOLTOTAL         = "TIPONOTACREDDEVOLTOTAL";
        public static String TIPONOTACREDDEVOLXITEM         = "TIPONOTACREDDEVOLXITEM";
        public static String TIPONOTACREDAJUSTEIVAP         = "TIPONOTACREDAJUSTEIVAP";        
        
    /*AMBIENTES SISTEMA*/
    public static String CONS_DES_AMBIENTESISTEMA = "PRODUCCIÓN:";
        
    /*MENSAJES*/
    public static String CONS_SUCCESS_MESSAGE    = "Operación terminó con éxito.";
    public static String CONS_SUCCESS_ERROR      = "Se produjo un error.";
    public static String CONS_WARN_SIN_RESULTADO = "No se obtuvieron resultados.";
    
    /*MENSAJES EMAIL*/
    public static String CONS_SUCCESS_EMAIL_MESSAGE = "Correo enviado con éxito.";
    public static String CONS_ERROR_EMAIL_MESSAGE = "No se pudo enviar el correo.";
    
    public static String CONS_RUTA_RPTFOLDER = "filereports/";
    public static String CONS_RUTA_RPTJASPERFOLDER = "/reportesjasper/";
    public static String CONS_RUTA_LOGO_PNG = "/resources/img/logo.png";
    
    /*CODIGOS TIPO OPERACION SUNAT*/
    public static String CONS_COD_TIPO_OPE_SUNAT_COMUNIC_BAJA = "COMUNICACION.BAJA";
    public static String CONS_COD_TIPO_OPE_SUNAT_GENE_COMPROB_ELECT = "COMPROB.ELECTR";
    
   
}
