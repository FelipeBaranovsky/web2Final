
const headers = `<div class="row">
<div class="col-md-2 celda heade" id="headerDNI" onclick="pruebaheader('dni')">
  <h3 class="tex">DNI</h3>
</div>
<div class="col-md-3 celda heade" id="headerApellido" onclick="pruebaheader('apellido')">
  <h3 class="tex">Apellido</h3>
</div>
<div class="col-md-1 celda heade" id="headerActividad" onclick="pruebaheader('actividad')">
  <h3 class="tex">Actividad</h3>
</div>
<div class="col-md-1 celda" id="headerDias">
  <h3 class="tex">Dias</h3>
</div>
<div class="col-md-1 celda" id="headerCosto">
  <h3 class="tex">Costo</h3>
</div>
<div class="col-md-2 celda" id="headerTotal">
  <h3 class="tex">Total</h3>
</div>
<div class="col-md-2 celda" id="headerOpciones">
  <h3 class="tex">Opciones</h3>
</div>
</div>`;


var flagEditando = false;
var idActual;
var vista = "listarTodos";
recargarTabla();

function pruebaheader(orden){
    if(orden == "dni"){
        vista = "listarDocumentos"
    }else{
        if(orden == "apellido"){
            vista = "listarApellidos"
        }else{
            if(orden == "actividad"){
                vista = "listarActividades"
            }
        }
    }
    recargarTabla();

}

async function getAll(){

    const response = await fetch("http://127.0.0.1:8866/"+vista, {
        //mode: 'no-cors',
        method: 'GET',
        /*headers: {
            'Access-Control-Allow-Origin': '*',
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },*/
    });
    const datos = await response.json();
    console.log(datos);
    return datos;
}

function recargarTabla(){
    let tabla = document.getElementById('divTabla');
    let color = 'background-color: rgb(252, 240, 76)';
    let totalFactura = 0;
    let socios;

    getAll().then(lista => {
        socios = lista;
            console.log(socios.length);
        if(socios.length > 0){
            tabla.innerHTML = headers;
            for(let i = 0; i < socios.length; i++){

                let total = parseFloat(socios[i].dias)*(socios[i].costo);
                let bonificacion = 0;
                let otraAct = false;

                if((1600 > total) && (total >= 1200)){
                    color = 'background-color: rgb(252, 240, 76)';
                    bonificacion = total*0.2;
                    total -= bonificacion;
                    console.log("Se aplica desc 20%");
                }else{
                    if(total >= 1600){
                        color = 'background-color: rgb(252, 240, 76)';
                        bonificacion = total*0.3;
                        total -= bonificacion;
                        console.log("Se aplica desc 30%");
                    }   
                }
                
                for(let j = 0; j < socios.length; j++){
                    if(socios[i].dni == socios[j].dni){
                        if(socios[i].actividad != socios[j].actividad){
                            otraAct = true;
                            break;
                        }
                    }
                }
                if(otraAct == true){
                    bonificacion = total*0.1;
                    total -= bonificacion;
                    console.log("Se aplica desc 10%");
                }else{
                }
                
                
                totalFactura += total;
                
                agregarCelda(socios[i],tabla,total.toFixed(2),color);

            } 

            tabla.innerHTML += `

            <div class="row">
                            <div class="col-md-7 celda" id="header">
                            <h3 class="tex">Total a facturar para el mes</h3>
                            </div>
                            <div class="col-md-2 celda" id="header">
                                <h3 class="tex">`+totalFactura.toFixed(2)+`</h3>
                            </div>
                        </div>
                        `;
            ;
    
        }

    });

}

function masActividad(dni,actividad){

    var esta = false;
    let socios;
    getAll().then(lista => {
        socios = lista;
        for(let i = 0; i < socios.length; i++){
            if(socios[i].dni == dni){
                if(socios[i].actividad != actividad){
                    console.log("El socio realiza otra actividad");
                    esta = true;
                }
            }
        }
    });
    console.log("Esta: "+esta);
    return esta;
}

function agregarCelda(socio,tabla,total,color){
    tabla.innerHTML += `
    <div class="row" id="`+(socio.dni.concat(socio.actividad))+`">
    <div class="col-md-2 celda" id="header" style="`+color+`">
      <h3 class="tex">`+socio.dni+`</h3>
    </div>
    <div class="col-md-3 celda" id="header" style="`+color+`">
      <h3 class="tex">`+socio.apellido+`</h3>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`;display: flex;text-align: center; align-items:center; justify-content: center;">
      <h3 class="tex" style="font-size: 12px">`+socio.actividad+`</h3>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`">
      <h3 class="tex">`+socio.dias+`</h3>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`">
      <h3 class="tex">`+socio.costo+`</h3>
    </div>
    <div class="col-md-2 celda" id="header" style="`+color+`">
      <h3 class="tex">`+total+`</h3>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`">
        <button id="deleteBtn" onclick="eliminarFila('`+socio.dni+`','`+socio.apellido+`','`+socio.actividad+`','`+socio.dias+`','`+socio.costo+`')">X</button>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`">
        <button id="editBtn" onclick="editarFila('`+socio.idCliente+`','`+socio.dni+`','`+socio.apellido+`','`+socio.actividad+`','`+socio.dias+`','`+socio.costo+`')">E</button>
    </div>
    </div>
    `;

}

async function deletear(socio){
    const response = await fetch("http://127.0.0.1:8866/delete", {
        //mode: 'no-cors',
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(socio),
    });
    console.log("Socio eliminado");
}

function eliminarFila(dni,apellido,actividad,dias,costo){
    getAll().then(lista => {

        const newSocio = {
            dni: dni,
            apellido: apellido,
            actividad: actividad,
            dias: dias,
            costo: costo
        }
        deletear(newSocio).then(recargo => {
            recargarTabla();
        });

    });

}

async function editear(socio){
    const response = await fetch("http://127.0.0.1:8866/edit", {
        //mode: 'no-cors',
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(socio),
    });
    console.log("Socio editado");
}

function editarFila(id,dni,apellido,actividad,dias,costo){
    getAll().then(lista => {
        let dniElement = document.getElementById('dni');
        let apellidoElement = document.getElementById('apellido');
        let actividadElement = document.getElementById('actividadSel');
        let diasElement = document.getElementById('dias');
        let costoElement = document.getElementById('costo');

        dniElement.value = dni;
        apellidoElement.value = apellido;
        actividadElement.value = actividad;
        diasElement.value = dias;
        costoElement.value = costo;


        let contenedorMain = document.getElementById('divRegAl');
        contenedorMain.setAttribute("style", "background-color: rgb(192, 192, 48);");
        let titulo = document.getElementById('titulo');
        titulo.innerHTML = "<h1>GIMNASIO ATHOM - Editar Socio</h1>";
        let boton = document.getElementById('registrar');
        boton.setAttribute("value", "Guardar");
        flagEditando = true;
        recargarTabla();
        idActual = id;

    });
    
}

function confirmarEdit(){
    let dniElement = document.getElementById('dni');
    let apellidoElement = document.getElementById('apellido');
    let actividadElement = document.getElementById('actividadSel');
    let diasElement = document.getElementById('dias');
    let costoElement = document.getElementById('costo');

    const newSocio = {
        idCliente: idActual,
        dni: dniElement.value,
        apellido: apellidoElement.value,
        actividad: actividadElement.value,
        dias: diasElement.value,
        costo: costoElement.value
    }
    editear(newSocio).then(recargo => {
        recargarTabla();
    });

}



function validarRegistro(){

    if(verificarVacios() == false){
        if(verificarErroneo() == false){
            if(flagEditando == false){
                registrarSocio();
            }else{
                flagEditando = false;
                let contenedorMain = document.getElementById('divRegAl');
                contenedorMain.setAttribute("style", "background-color: aqua;");
                let titulo = document.getElementById('titulo');
                titulo.innerHTML = "<h1>GIMNASIO ATHOM - Registro de Socio</h1>";
                let boton = document.getElementById('registrar');
                boton.setAttribute("value", "Registrar");
                confirmarEdit();
            }    
            recargarTabla();
            
        }

    }

}

function registrarSocio(){
    let apellido = document.getElementById('apellido');
    let dni = document.getElementById('dni');
    let actividad = document.getElementById('actividadSel');
    let dias = document.getElementById('dias');
    let costo = document.getElementById('costo');
    let socios;
    const newSocio = {
        dni: dni.value,
        apellido: apellido.value,
        actividad: actividad.value,
        dias: dias.value,
        costo: costo.value
    }

    getAll().then(lista => {
        socios = lista;
        if(socios.length != 0){
            for(let i = 0; i < socios.length; i++){
                if((socios[i].dni == newSocio.dni) && (socios[i].actividad == newSocio.actividad)){
                    alert("El socio ya existe con dicha actividad! Verifica el dni o la actividad!");
                    break;
                }else{
                    if(i == socios.length-1){

                        insertar(newSocio).then(recargo => {
                        recargarTabla();
                        });
                    }
                }
            }
        }else{

            insertar(newSocio).then(recargo => {
                recargarTabla();
            });
        }
        

    });
    

}
//Borrar
function estaRepetido(dni,actividad){
    let socios;
    let campo = false
    getAll().then(lista => {
        socios = lista;
        for(let i = 0; i < socios.length; i++){
            if((socios[i].dni == dni) && (socios[i].actividad == actividad)){
                return true;
            }else{
                if(i == socios.length-1){
                    return false;
                }
            }
        }

    });
    return campo;
}

async function insertar(socio){

    const response = await fetch("http://127.0.0.1:8866/insertar", {
        //mode: 'no-cors',
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(socio),
    });
    console.log("Socio cargado");
}



function verificarErroneo(){
    let erroneo = false;
    let inputs = document.getElementsByTagName('input');
    for(let i=0; i<inputs.length;i++){
        if(inputs[i].id != "registrar"){
            //apellido
            if(inputs[i].id == "apellido"){
                if(inputs[i].value.length < 3){
                    let input = document.getElementById(inputs[i].id+"Advert");
                    input.innerHTML = "Debe tener al menos 3 caracteres";
                    erroneo = true;
                }else{
                    let input = document.getElementById(inputs[i].id+"Advert");
                    input.innerHTML = "";
                }
            }else{
                if(inputs[i].id == "dni"){
                    var reg=new RegExp("^[0-9]{1,3}\.[0-9]{3,3}\.[0-9]{3,3}$"); 
                    console.log(reg.test(inputs[i].value));

                    if(reg.test(inputs[i].value)){
                        let input = document.getElementById(inputs[i].id+"Advert");
                        input.innerHTML = "";
                    }else{
                        let input = document.getElementById(inputs[i].id+"Advert");
                        input.innerHTML = "El dni no es correcto";
                        erroneo = true;
                    }
                }else{
                    if(inputs[i].value <= 0){
                        let input = document.getElementById(inputs[i].id+"Advert");
                        input.innerHTML = "Debe ser superior a 0";
                        erroneo = true;
                    }else{
                        let input = document.getElementById(inputs[i].id+"Advert");
                        input.innerHTML = "";
                    }
                }
            }
            
            
        }
    }
    
    return erroneo;
}

function verificarVacios(){
    let vacios = false;
    let inputs = document.getElementsByTagName('input');
    console.log(inputs);
    for(let i = 0; i < inputs.length; i++){
        if(inputs[i].id != "registrar"){
            if(inputs[i].value == ""){
                console.log(inputs[i].id+" esta vacio");
                marcarVacio(inputs[i].id);
                vacios = true;
            }else{
                let input = document.getElementById(inputs[i].id+"Advert");
                input.innerHTML = "";
            }   
        }
    }
    return vacios;
}
function marcarVacio(id){
    let input = document.getElementById(id+"Advert");
    input.innerHTML = "Este campo es obligatorio";
}
