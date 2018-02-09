
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

/***********************************************************************/
/****************MENSAJE DE VALIDACION**********************************/
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

/***********************************************************************/
/********************** PERSONA ****************************************/
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

/************************************************************************/
/********************** MESSAGES ****************************************/
/*Funcion que devuelve el tipo de persona en base al DoCUMENTO DE IDENTIDAD*/

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


/*******************************************************************************************************************/
/********************** VALIDACIONES PANTALLA: COMPRAREGISTRAR.XHTML ****************************************/
/*Funcion pque valida en ingreso del monto de descuento*/

function fnValidarDescuento(obj){
                    
    var mtoDescuento = fnFormatDecimal(obj.value); 
    var message;
    
    if (mtoDescuento != undefined){
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
}