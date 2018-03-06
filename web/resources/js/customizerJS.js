/*******************************************************************************************/
/********************** VALIDACIONES GENERICAS *********************************************/

/*Funcion que devuelve el tipo de persona en base al DoCUMENTO DE IDENTIDAD*/
function fnGetPersType(valor){
    /*Persona Juridica*/
    if ( valor.length == 11 && valor.substring(0,2) == '20' ){
        return "PJ";
    } else if ( valor.length == 11 && valor.substring(0,2) == '10' ){
        return "PJ";
    } else if ( valor.length == 8 ){
        return "PN";
    } else {
        return "ERROR";
    }
};

/*Funcion que valida el documento de identidad ingresado*/
function fnValidateDocuIden(val){
    
    var arrMessages = [];

    if (val.length == 0 ){

      arrMessages.push("Debe ingresar el Documento de Identidad.");
      fnShowMessageValidation(arrMessages);
      return false;

    }else {

        var resultado = fnGetPersType(val);

        if ( resultado == "ERROR" ){
            arrMessages.push("El formato del Documento de Identidad es inv&aacutelido.");
            fnShowMessageValidation(arrMessages);
            return false;
        } else {
            fnHideMessageValidation();
            return true;
        }
    }
};

/*Función para que solo permita ingresar números*/
function fnAllowOnlyNumbers(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //alert("tecla:" + tecla);

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8){
        return true;
    }

    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
};

/*Función para que solo permita ingresar números decimales*/
function fnAllowOnlyDecimalNumbers(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //alert("tecla:" + tecla);

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8){
        return true;
    }

    //Tecla de punto(.) para numeros decimales, siempre la permite
    if (tecla==46){
        return true;
    }

    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
};

/*Fúncion que permite formatear a 2 decimales el número ingresado*/
function formatDecimal_2(obj){
    console.log("obj:" + obj);
    if (obj.value.length > 0){
        var nro = obj.value;
        var result = Number(Math.round( nro + 'e2') + 'e-2').toFixed(2);
        document.getElementById(obj.id).value = result;
        return true;
    }

};

function fnFormatDecimal(val){

    if (val.length > 0){
        var nro = val;
        var result = Number(Math.round( nro + 'e2') + 'e-2').toFixed(2);        
        return Number(result);
    }
};

/*Funcion que muestra la cantidad de caracteres
    @val      : Cadena que se contará la cantidad de caracteres.
    @idObject : Id del elemento HTML donde se imprimirá el resultado.
*/
function fnCountLength(val, idObject) {
    $("#"+ idObject).text(val.length);
}


/*Funcion que permite validar el formato de la fecha en formato DD/MM/AAAA
validaFechaDDMMAAAA("14/11/1988"); // true
validaFechaDDMMAAAA("32/11/1988"); // false
*/
function fnValidateDateDDMMAAAA(fecha){
    var dtCh= "/";
    var minYear=1900;
    var maxYear=2100;
    function isInteger(s){
        var i;
        for (i = 0; i < s.length; i++){
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        return true;
    }
    function stripCharsInBag(s, bag){
        var i;
        var returnString = "";
        for (i = 0; i < s.length; i++){
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }
    function daysInFebruary (year){
        return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }
    function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31
            if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
            if (i==2) {this[i] = 29}
        }
        return this
    }
    function isDate(dtStr){
        var daysInMonth = DaysArray(12)
        var pos1=dtStr.indexOf(dtCh)
        var pos2=dtStr.indexOf(dtCh,pos1+1)
        var strDay=dtStr.substring(0,pos1)
        var strMonth=dtStr.substring(pos1+1,pos2)
        var strYear=dtStr.substring(pos2+1)
        strYr=strYear
        if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
        if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
        for (var i = 1; i <= 3; i++) {
            if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
        }
        month=parseInt(strMonth)
        day=parseInt(strDay)
        year=parseInt(strYr)
        if (pos1==-1 || pos2==-1){
            return false
        }
        if (strMonth.length<1 || month<1 || month>12){
            return false
        }
        if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
            return false
        }
        if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
            return false
        }
        if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
            return false
        }
        return true
    }
    if(isDate(fecha)){
        return true;
    }else{
        return false;
    }
}

function fnValidateBiggerDate(fecha){


    var hoy             = new Date();
    var fechaFormulario = new Date("05-03-2018");
    var message         = "";

    fechaFormulario.toLocaleDateString("es-ES")
    console.log("hoy: " + hoy);
    console.log("fechaFormulario: " + fechaFormulario);


    // Comparamos solo las fechas => no las horas!!
    hoy.setHours(0,0,0,0);
    fechaFormulario.setHours(0,0,0,0); // Lo iniciamos a 00:00 horas

    if ( fechaFormulario > hoy ) {
      console.log("Fecha mayor que la actual");
      message = "Ingrese fecha menor o igual a la actual.";
    }
    else {
      console.log("Fecha pasado");
    }

    return message;
}

/******************************************************************************************************/
/**************** MENSAJE DE VALIDACION ***************************************************************/
/*Funcion que muestra el error*/
function fnShowMessageValidation(arrMessage){

    var resultado = '<UL type = "square">';
    for ( var i in  arrMessage ){
        resultado += '<LI>' + arrMessage[i] + '</LI>';
    }
    
    resultado += '</UL>';

    var panel = document.getElementById("divResultadoValidacion");          
    //console.log("obj:" + panel);
    panel.className = "messageValidation";
    panel.style.display = "inline-block";
    panel.innerHTML = resultado;        
   
};

/*Funcion que oculta el error*/
function fnHideMessageValidation(){
    
    var panel = document.getElementById("divResultadoValidacion");
    panel.className = "";
    panel.innerHTML = "";  
    
};

/************************************************************************/
/********************** MESSAGES POPUP****************************************/

/*Funcion para mostrar un popup de tipo INFORMATIVO*/
function fnShowInfoMessage(message){

    $.confirm({
        columnClass: 'col-md-4 col-md-offset-4', //Tamano de la ventana
        title: 'Informaci&oacuten',
        content: ''  + message,
        type: 'blue',
        typeAnimated: true,
        draggable: true, //Animacion para que vibre
        animation: 'scaleX',
        closeAnimation: 'scaleX',
        theme: 'supervan',
        backgroundDismissAnimation: 'glow',
        buttons: {                            
            Cerrar: {
                text: 'Cerrar',
                btnClass: 'btn-primary'
            }
        }
    });
}

/*Funcion para mostrar un popup de tipo ERROR*/
function fnShowErrorMessage(message){

    $.confirm({
        columnClass: 'col-md-4 col-md-offset-4', //Tamano de la ventana
        title: 'Error',
        content: ''  + message,
        type: 'red',
        typeAnimated: true,
        draggable: true, //Animacion para que vibre
        animation: 'scaleX',
        closeAnimation: 'scaleX',
             
        buttons: {                            
            Cerrar: {
                text: 'Cerrar',
                btnClass: 'btn-primary'
            }
        }
    });
}

/* Funcion que permite mostrar POPUP de CONFIRMACION al realizar intentar grabar el formulario
 * idElement: Es el ID del boton que tiene el metodo actionListener (dentro de la pantalla donde se está realizando la invocación
 * a esta función ) el cual se invocará si el usuario hace click en ACEPTAR */

function fnShowDialogConfirm(idElement, message) {

    if (message.length == 0){
        message = "&iquest;Est&aacute seguro de continuar?";
    }
    
    $.confirm({
        columnClass: 'col-md-4 col-md-offset-4', //Tamano de la ventana
        title: 'Confirmaci&oacuten',
        content: '' + message,
        type: 'blue',
        typeAnimated: true,
        draggable: true, //Animacion para que vibre
        animation: 'scaleX',
        closeAnimation: 'scaleX',
        theme: 'supervan',
        backgroundDismissAnimation: 'glow',
        buttons: {
            Aceptar: {
                text: 'Aceptar',
                btnClass: 'btn-primary',
                action: function () {
                    $("#" + idElement).click(); //Se invoca al boton que hace la invocacion del metodo Grabar en el Managed Bean
                }
            },
            Cancelar: {
                text: 'Cancelar',
                btnClass: 'btn-primary'
            }
        }
    });

    return false;
}
;

/************************************************************************************************************/
/********************** PANTALLA: REGISTRAR PRODUCTO ********************************************************/
/************************************************************************************************************/

function fnChangeTab(option) {

    if (option == 1) {
        $('[href="#menu1"]').tab('show');
    } else if (option == 2) {
        $('[href="#menu2"]').tab('show');
    }

    return false;
}


function fnValidateProductForm() {
    
    console.log("Product -> fnValidateProductForm" );
    //obteniendo el valor que se puso en campo text del formulario                    
    var CodigoProducto  = document.getElementById("form:codigoProducto").value;
    var NombreProducto  = document.getElementById("form:nombreProducto").value;
    var TipoProducto    = document.getElementById("form:somTipoProducto").selectedIndex;
    var PrecioVenta     = document.getElementById("form:precioVenta").value;
    var FlgError        = false;
    var arrMessages     = [];


    if (CodigoProducto.length == 0) {
        arrMessages.push("Se debe ingresar el C&oacutedigo del Producto.");
        FlgError = true;
    }

     if (CodigoProducto.length < 3) {
        arrMessages.push("El C&oacutedigo del Producto debe tener mas de 2 caracteres.");
        FlgError = true;
    }

    if (NombreProducto.length == 0) {
        arrMessages.push("Se debe ingresar el Nombre del Producto.");        
        FlgError = true;
    }

    if (TipoProducto == 0) {
        arrMessages.push("Seleccione el Tipo de producto.");
        FlgError = true;
    }

    /*if (PrecioVenta.length == 0) {
        arrMessages.push("Se debe ingresar el Precio de Venta.");
        FlgError = true;
    }*/

    //IMPRIMIR RESULTADO
    if (FlgError == true) {
        fnShowMessageValidation(arrMessages);
        return false;
    } else {
        fnHideMessageValidation();
        //fnShowDialogConfirm("form\\:btnGrabar", ""); // -> Se pasa como parametro el ID del boton que tiene el metodo actionListener
        return true;
    }
}

             


/*************************************************************************************************************/
/********************** PANTALLA: COMPRAREGISTRAR.XHTML ******************************************************/
/*************************************************************************************************************/

/*AUTOCOMPLETE ajax - BUSQUEDA DE PRODUCTOS : 
* Calcula la posición donde se mostrará la tabla que contiene los productos obtenidos,
* esta posición es justo debajo de la caja de texto donde se ingresa el codigo del producto.
* */
function fnShowAutocompleteResult(data) {

    switch (data.status) {
        case "success":

            var h = $("#form\\:codigoProducto").css("height");
            var element = document.getElementById("form:codigoProducto");
            var rect = element.getBoundingClientRect(); //Obtiene la posicion absoluta del elemento
            //console.log(rect.top, rect.right, rect.bottom, rect.left);
            var top = Number(rect.top) + Number(h.replace("px", ""));

            $("#idDivAutocomplete").css({"display": "", "position": "absolute"});
            $("#idDivAutocomplete").css("top", top + "px");
            $("#idDivAutocomplete").css("left", rect.left + "px");
            break;
    }
}
;

/*CREACION NUEVO PRODUCTO: */
/*Se llama a la función de FancyBOX para que muestre la URL especificada.*/
function fnShowPopupCreateProduct(){
    
    var codProd = document.getElementById("form:codigoProducto").value;
    var nombreProducto = document.getElementById("form:descProducto").value;
    
    console.log("codProd: "+ codProd.trim().length);
    console.log("nombreProducto: "+ nombreProducto);
    console.log("nombreProducto: "+ nombreProducto.trim().length);
    
    if (codProd.trim().length < 3){
        fnShowErrorMessage("C&oacutedigo del Producto debe contener al menos 3 caracteres.");
    }
    else if (nombreProducto.trim().length > 0){
        fnShowErrorMessage("Producto ingresado ya existe.");
    }
    else if (nombreProducto.trim().length == 0){
        console.log("Mostrar Popup Persona");
        $("#linkDialog").attr("href", "faces/popups/popupProductoRegistrar.xhtml?paramCodProd=" + codProd + "&paramExternalPage=1");
        $("#linkDialog").click(); //Origina que llame a la función de FancyBOX y que muestre la URL especificada.                        
    }
    
    
        
    
    return false;
};                

/*Funcion que permite validar en ingreso del nro de serie y correlativo*/
function fnValiteVoucherHeader() {

    var codSerie = document.getElementById("form:codSerie").value;
    var numDocumento = document.getElementById("form:numDocumento").value;
    var somTipoDocu = document.getElementById("form:somTipoDocu").selectedIndex;

    console.log("codSerie:" + codSerie);
    //console.log("numDocumento:" + numDocumento + " sdf:" + somTipoDocu.selectedIndex);

    var FlgError = false;
    var arrMessages = [];

    if (somTipoDocu == 0) {
        arrMessages.push("Seleccione el Tipo de Documento de la Orden.");
        FlgError = true;
    }

    if (codSerie.trim().length == 0) {
        arrMessages.push("Debe ingresar el Nro. de Serie.");
        FlgError = true;
    }

    if (codSerie.trim().length > 4) {
        arrMessages.push("Nro. de Serie no puede ser mayor a 4 digitos.");
        FlgError = true;
    }

    if (numDocumento.length == 0) {
        arrMessages.push("Debe ingresar el Correlativo.");
        FlgError = true;
    }                   

    var divResu = $("#idDivValidation");

    if (FlgError) {

        var resultado = '<UL type = "square">';
        for (var i in arrMessages) {
            resultado += '<LI>' + arrMessages[i] + '</LI>';
        }
        resultado += '</UL>';

        console.log("resultado:" + resultado);

        divResu.html(resultado);
        divResu.css("display", "inline-block");

        return false;

    } else {
        divResu.html("");
        divResu.css("display", "none");
        return true;
    }
};

/*Función que se ejecuta durante el ciclo de vida de la llamada AJAX.
 * Evalua si el DNI/RUC ingresado existe, en caso no, muestra la pagina de REGISTRAR PERSONA*/
function fnAjaxListenPersonSearch(data) {

    switch (data.status) {
        case "begin":
            break;
        case "complete":
            break;
        case "success":
            var value = document.getElementById("form:razonSocial").value;
            if (value.length == 0) {
                var codTRolpers = document.getElementById("idCodTRolpers").value;
                var numDocuIden = document.getElementById("form:numDocuIden").value;
                console.log("codTRolpers: " + codTRolpers);
                //Se sobreescribe la propiedad HREF
                $("#linkDialog").attr("href", "faces/popups/popupPersonaRegistrar.xhtml?paramExternalPage=1&paramCodIden=" + numDocuIden + "&paramCodTRolpers=" + codTRolpers + "&paramNuevoRegistro=false");
                $("#linkDialog").click(); //Origina que llame a la función de FancyBOX y que muestre la URL especificada.
                return false;
            }
            break;
    }
};

function fnCloseIFrame_fromRegisterPerson() {
    $("#form\\:btnSearchPerson").click();
};

function fnCloseIFrame_fromRegisterProduct() {
    $("#form\\:btnSearchProduct").click();
};


/*Funcion que valida los campos antes de agregar un producto*/
function fnValiteAddProduct() {
    //obteniendo el valor que se puso en campo text del formulario
    var CodigoProducto = document.getElementById("form:codigoProducto").value;
    var CostoUnitario = document.getElementById("form:costoUnitario").value;
    var Cantidad = document.getElementById("form:cantidad").value;
    var FlgError = false;
    var arrMessages = [];

    if (CodigoProducto.length == 0) {
        arrMessages.push("Debe ingresar el Código del Producto");
        FlgError = true;
    }

    if (CostoUnitario.length == 0) {
        arrMessages.push("Debe ingresar el Precio del Producto.");
        FlgError = true;

    }else if (Number(Cantidad) <= 0) {
        arrMessages.push("Debe ingresar una cantidad mayor a 0.");
        FlgError = true;
    }

    //IMPRIMIR RESULTADO
    if (FlgError == true) {
        fnShowMessageValidation(arrMessages);
        return false;
    } else {
        fnHideMessageValidation();
        return true;
    }
}
;



/*Funcion pque valida en ingreso del monto de descuento*/

/*La logica se realiza en el Controlador
function fnValidarDescuento(obj){
                    
    var mtoDescuento = fnFormatDecimal(obj.value); 
    var message;
    
    if (mtoDescuento != undefined){
        
        var tableDetOrder = document.getElementById("form:idTableDetOrder");
        var mtoTotal = 0;
        console.log("tableDetOrder:" + tableDetOrder.rows.length);
        
        //Empieza en 1 por la cabecera
        for (var i = 1; i <= tableDetOrder.rows.length - 1; i++){
            console.log("Celda:" + tableDetOrder.rows[i].cells[4].innerHTML);
            mtoTotal += Number(tableDetOrder.rows[i].cells[4].innerHTML) * Number(tableDetOrder.rows[i].cells[3].innerHTML);
        }
        console.log("mtoTotal:" + mtoTotal);
        
        var mtoTotal     = $("#form\\:numMtoTotal").text();
        if (mtoDescuento >= Number(mtoTotal)){
            message = "El descuento no puede ser mayor al importe Total.";
            fnShowErrorMessage(message);
            obj.value = "";
            return false;
        }else{
            obj.value = mtoDescuento;
        }
    }else{
        return false;
    }
}*/

/************************************************************************************************************/
/********************** PANTALLA: REGISTRAR PERSONA *********************************************************/
/************************************************************************************************************/

function fnShowPopupCreateEditPerson(idPers) {    

    console.log("idPers:" + idPers);
    var codTRolpers = document.getElementById("idCodTRolpers").value;    
    console.log("codTRolpers:" + codTRolpers);
    var numDocuIden = document.getElementById("form:numDocuIden").value;
    console.log("numDocuIden: " + numDocuIden);

    if (idPers == 0){
        if (codTRolpers.length > 0) {
            
            //Se sobreescribe la propiedad HREF
            $("#linkDialog").attr("href", "faces/popups/popupPersonaRegistrar.xhtml?paramExternalPage=1&paramCodIden=" + numDocuIden + "&paramCodTRolpers=" + codTRolpers +"&paramNuevoRegistro=true");
            $("#linkDialog").click(); //Origina que llame a la función de FancyBOX y que muestre la URL especificada.            
           
        }
    }else if (idPers > 0){

        //Se sobreescribe la propiedad HREF
        $("#linkDialog").attr("href", "faces/popups/popupPersonaRegistrar.xhtml?paramExternalPage=1&paramCodIden=" + numDocuIden + "&paramCodTRolpers=" + codTRolpers +"&paramNuevoRegistro=false&paramIdPers=" + idPers);
        $("#linkDialog").click(); //Origina que llame a la función de FancyBOX y que muestre la URL especificada.

    }


    return false;
            
    
};

/************************************************************************************************************/
/********************** PANTALLA: LISTAR DOCUMENTOS *********************************************************/
/************************************************************************************************************/

/*(NO SE LLEGO A USAR) Funcion que muestra en un PopUP el detalle de la orden */
function fnShowOrderDetail(idDocu, titulo) {

    console.log("idDocu:" + idDocu);
    console.log("titulo:" + titulo);
    

    if (idDocu > 0){

        //Se sobreescribe la propiedad HREF
        $("#linkDialog").attr("href", "faces/ordenDetalle.xhtml?paramIdDocu=" + idDocu + "&paramTituloPagina=" + titulo );
        $("#linkDialog").click(); //Origina que llame a la función de FancyBOX y que muestre la URL especificada.

    }

    return false;            
    
};


/************************************************************************************************************/
/********************** PANTALLA: CUADRE CAJA ***************************************************************/
/************************************************************************************************************/
/*Función que se ejecuta durante el ciclo de vida de la llamada AJAX.
 *Se pinta de color rojo si hay faltantes en el cuadre de EFECTIVO Y/O TARJETA */
function fnAjaxListenCalcularCuadreCaja(data) {

    switch (data.status) {        
        case "success":
            var efectivo = document.getElementById("idSobraFaltaEfectivo");
            console.log("Celda: " + efectivo);
            console.log("efectivo: " + efectivo.innerHTML);
            if (Number(efectivo.innerHTML) < 0) {
                console.log("holass");
                    efectivo.style.color = "red";
            }
            var tarjeta = document.getElementById("idSobraFaltaTarjeta");
            console.log("tarjeta: " + tarjeta.innerHTML);
            if (Number(tarjeta.innerHTML) < 0) {
                    tarjeta.style.color = "red";
            }
            
            $("#btnConfirm").attr("disabled",false);
            break;
    }
};

/*Funcion que permite deshabilitar el boton de CERRAR CAJA*/
function fnHideSaveButton(){
    //document.getElementById("btnConfirm").disabled = "false";
    console.log("deshabilitar boton");
    $("#btnConfirm").attr("disabled",true);
};


/************************************************************************************************************/
/********************** PANTALLA: INDEX *********************************************************************/
/************************************************************************************************************/
/*Funcion que permite validar en ingreso del Mto de Apertura para la Caja Diaria*/
function fnValidateIndexForm(){

    var mtoApertura = document.getElementById("form:idMtoApertura").value;
    console.log("mtoApertura: " + mtoApertura);                    

    var FlgError = false;
    var arrMessages = [];

    if (mtoApertura.trim().length == 0) {
        arrMessages.push("Debe ingresar un monto para la apertura.");
        FlgError = true;
    }else if (Number(mtoApertura) < 50){
        arrMessages.push("El monto m&iacutenimo para aperturar caja es de S/100.00");
        FlgError = true;
    }

    var divResu = $("#idDivValidation");

    if (FlgError) {

        var resultado = '<UL type = "square">';
        for (var i in arrMessages) {
            resultado += '<LI>' + arrMessages[i] + '</LI>';
        }
        resultado += '</UL>';

        console.log("resultado:" + resultado);

        divResu.html(resultado);
        divResu.css("display", "inline-block");

        return false;

    } else {
        console.log("sin errores"); 
        
        divResu.html("");
        divResu.css("display", "none");
        return true;
    }
};


/**********************************************************************************************************/
/********************** ACCESOS MENU *********************************************************************/
/*********************************************************************************************************/
/*Función que permite ejecutar la opcion que llama a la PANTALLA: REPORTE DE COMPRAS*/
function fnRedirectPurchaseReport(){
    $("#form\\:idMenuPurchaseReport").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectRegisterPurchase(){
    $("#form\\:idMenuRegisterPurchase").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectSaleRegister(){
    console.log("fnRedirectSaleRegister");
    $("#form\\:idMenuSaleRegist").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectSaleReport(){
    $("#form\\:idMenuSaleReport").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectProviderReport(){
    $("#form\\:idMenuProviderReport").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectCustomerReport(){
    $("#form\\:idMenuCustomerReport").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectRegisterProduct(){
    $("#form\\:idMenuRegisterProduct").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectCustomerReport(){
    $("#form\\:idMenuCustomerReport").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectCloseBox(){
    $("#form\\:idMenuCloseBox").click();
};

/*Función que permite ejecutar la opcion que llama a la PANTALLA: REGISTRAR COMPRAS*/
function fnRedirectBoxReport(){
    $("#form\\:idMenuBoxReport").click();
};