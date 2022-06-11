
const headers = `<div class="row">
<div class="col-md-2 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">DNI</h3>
</div>
<div class="col-md-3 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">Apellido</h3>
</div>
<div class="col-md-1 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">Actividad</h3>
</div>
<div class="col-md-1 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">Dias</h3>
</div>
<div class="col-md-1 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">Costo</h3>
</div>
<div class="col-md-2 celda heade" id="header" onclick="pruebaheader()">
  <h3 class="tex">Total</h3>
</div>
<div class="col-md-2 celda" id="header">
  <h3 class="tex">Opciones</h3>
</div>
</div>`;


var flagEditando = false;
recargarTabla();


function pruebaheader(){
    console.log("prueba");
    //Lo de ordenar
}

function recargarTabla(){
    let tabla = document.getElementById('divTabla');
    let color = 'background-color: rgb(252, 240, 76)';
    let totalFactura = 0;

    tabla.innerHTML = headers;
    if(localStorage.getItem('socios') != null){
        let socios = JSON.parse(localStorage.getItem('socios'));

        for(let i = 0; i < socios.length; i++){
            console.log("dias: "+socios[i].dias);
            console.log("costo: "+socios[i].costo);
            
            let total = parseFloat(socios[i].dias)*(socios[i].costo);
            let bonificacion = 0;
            console.log("total: "+total);
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
            if(masActividad(socios[i].dni,socios[i].actividad) == true){
                bonificacion = total*0.1;
                total -= bonificacion;
                console.log("Se aplica desc 10%");
            }
            totalFactura += total;
            
            console.log("total: "+total);
            console.log("Color: "+color);
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

}

function masActividad(dni,actividad){
    let socios = JSON.parse(localStorage.getItem('socios'));
    for(let i = 0; i < socios.length; i++){
        if(socios[i].dni == dni){
            console.log("El dni ya existe");
            if(socios[i].actividad != actividad){
                console.log("Realiza otra actividad");
                return true;
            }
        }
    }
    return false;
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
        <button id="deleteBtn" onclick="eliminarFila('`+socio.dni+`','`+socio.actividad+`')">X</button>
    </div>
    <div class="col-md-1 celda" id="header" style="`+color+`">
        <button id="editBtn" onclick="editarFila('`+socio.dni+`','`+socio.actividad+`')">E</button>
    </div>
    </div>
    `;

}


function editarFila(dni,actividad){
    let socios = JSON.parse(localStorage.getItem('socios'));
    for(let i = 0; i < socios.length; i++){
        if(socios[i].dni == dni){
            if(socios[i].actividad == actividad){
           

            let dni = document.getElementById('dni');
            let apellido = document.getElementById('apellido');
            let actividad = document.getElementById('actividadSel');
            let dias = document.getElementById('dias');
            let costo = document.getElementById('costo');
            
            dni.value = socios[i].dni;
            apellido.value = socios[i].apellido;
            actividad.value = socios[i].actividad;
            dias.value = socios[i].dias;
            costo.value = socios[i].costo;
            
            let contenedorMain = document.getElementById('divRegAl');
            contenedorMain.setAttribute("style", "background-color: rgb(192, 192, 48);");
            let titulo = document.getElementById('titulo');
            titulo.innerHTML = "<h1>GIMNASIO ATHOM - Editar Socio</h1>";
            let boton = document.getElementById('registrar');
            boton.setAttribute("value", "Guardar");
            flagEditando = true;
            eliminarFila(dni.value, actividad.value);
            recargarTabla();
        }
        }
    }
    
}


function eliminarFila(dni,actividad){
    let socios = JSON.parse(localStorage.getItem('socios'));
    for(let i = 0; i < socios.length; i++){
        if(socios[i].dni == dni){
            if(socios[i].actividad == actividad){
                socios.splice(i,1);
                localStorage.setItem('socios', JSON.stringify(socios));
                recargarTabla();
            }
        }
    }
}

function validarRegistro(){

    if(verificarVacios() == false){
        if(verificarErroneo() == false){
            registrarSocio();
            recargarTabla();
            if(flagEditando == true){
                flagEditando = false;
                let contenedorMain = document.getElementById('divRegAl');
                contenedorMain.setAttribute("style", "background-color: aqua;");
                let titulo = document.getElementById('titulo');
                titulo.innerHTML = "<h1>GIMNASIO ATHOM - Registro de Socio</h1>";
                let boton = document.getElementById('registrar');
                boton.setAttribute("value", "Registrar");
            }
        }
        //alert("todoBine");
    }

}

function registrarSocio(){
    let apellido = document.getElementById('apellido');
    let dni = document.getElementById('dni');
    let actividad = document.getElementById('actividadSel');
    let dias = document.getElementById('dias');
    let costo = document.getElementById('costo');
    const newSocio = {
        dni: dni.value,
        apellido: apellido.value,
        actividad: actividad.value,
        dias: dias.value,
        costo: costo.value
    }
    if(localStorage.getItem('socios') == null){ //No existen localmente ninguno
        let socios = [];
        socios.push(newSocio);
        localStorage.setItem('socios',JSON.stringify(socios));
        recargarTabla();
    }else{
        if(estaRepetido(newSocio.dni, newSocio.actividad) == false){
            let socios = JSON.parse(localStorage.getItem('socios'));
            socios.push(newSocio);
            localStorage.setItem('socios', JSON.stringify(socios));
            recargarTabla();
        }else{
            alert("Ese socio ya existe con dicha actividad! Verifica el dni o la actividad!");
        }
    }

}
function estaRepetido(dni,actividad){
    let socios = JSON.parse(localStorage.getItem('socios'));
    for(let i = 0; i < socios.length; i++){
        if(socios[i].dni == dni){
            console.log("El dni ya existe");
            if(socios[i].actividad == actividad){
                console.log("La actividad ya existe");
                return true;
            }
        }
    }
    return false;
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

