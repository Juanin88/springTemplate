/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var inputsBloques;
var inputsBloquesArrayValues = [];
var inputsBloquesArrayValuesUpdate = [];
var listMesajesJson;
var msjUpdate = [];

var numBlock = 0;


$(function () {

    listMesajesJson = JSON.parse($("#listMesajesJson").html());

    $("#sortable").sortable({
        revert: true
    });

    $("#sortable-update").sortable({
        revert: true
    });

    $("ul, li").disableSelection();
});

function addBlock() {

    var optionValue = document.querySelector('input[name="options"]:checked').value;


    var idBlock = "id='block_" + numBlock + "'";
    var eraseBlockhtml = '<i class="fa fa-times" aria-hidden="true" onclick="eraseBlock(\'block_' + numBlock + '\')" ></i>&nbsp;&nbsp;';
    var block = "<li class='list-group-item' " + idBlock + ">" + eraseBlockhtml +
            "<input type='text' name='plantilla[]' value='' onkeyup='makeSentence()' style='width:90%' placeholder='Escribe aquí tu texto...' />" +
            "</li>";

    var blockVar = "<li class='list-group-item ui-sortable-handle' onclose='makeSentence()' " + idBlock + ">" + eraseBlockhtml +
            "<input class='btn btn-info btn-sm' name='plantilla[]' type='button' value='" + optionValue + "'>" +
            "</li>";

    if (optionValue == "[Texto-Libre]") {
        $("#sortable").append(block);
        numBlock++;
    } else {
        $("#sortable").append(blockVar);
        numBlock++;
    }

    makeSentence();

}


function addBlockUpdate() {

    var optionValue = document.querySelector('input[name="options-update"]:checked').value;


    var idBlock = "id='block_update_" + numBlock + "'";
    var eraseBlockhtml = '<i class="fa fa-times" aria-hidden="true" onclick="eraseBlock(\'block_update_' + numBlock + '\')" ></i>&nbsp;&nbsp;';
    var block = "<li class='list-group-item' " + idBlock + ">" + eraseBlockhtml +
            "<input type='text' name='plantilla-update[]' value='' onkeyup='makeSentenceUpdate()' style='width:90%' placeholder='Escribe aquí tu texto...' />" +
            "</li>";

    var blockVar = "<li class='list-group-item ui-sortable-handle' onclose='makeSentenceUpdate()' " + idBlock + ">" + eraseBlockhtml +
            "<input class='btn btn-info btn-sm' name='plantilla-update[]' type='button' value='" + optionValue + "'>" +
            "</li>";


    if (optionValue == "[Texto-Libre]") {
        $("#sortable-update").append(block);
        numBlock++;
    } else {
        $("#sortable-update").append(blockVar);
        numBlock++;
    }


    makeSentenceUpdate();

}

function eraseBlock(block) {
    $("#" + block).remove();
    makeSentence();
    makeSentenceUpdate();
}

function makeSentence() {
    inputsBloquesArrayValues = [];
    inputsBloques = $("input[name='plantilla[]']");
    var sentence = '';
    var orderNum = 0;
    $.each(inputsBloques, function (index, value) {
        var mensajeObj = {
            "type": "",
            "content": value.value,
            "order": orderNum
        };
        orderNum++;
        sentence = sentence + value.value;
        inputsBloquesArrayValues.push(mensajeObj);
    });
    $("#sentenceTest").html(sentence);
}


function makeSentenceUpdate() {
    inputsBloquesArrayValuesUpdate = [];
    inputsBloques = $("input[name='plantilla-update[]']");
    var sentence = '';
    var orderNum = 0;
    $.each(inputsBloques, function (index, value) {
        var mensajeObj = {
            "type": "",
            "content": value.value,
            "order": orderNum
        };
        orderNum++;
        sentence = sentence + value.value;
        inputsBloquesArrayValuesUpdate.push(mensajeObj);
    });
    $("#sentenceTest-update").html(sentence);
}


$("input[name='plantilla[]']").keypress(function () {
    makeSentence();
});

$("input[name='plantilla-update[]']").keypress(function () {
    makeSentenceUpdate();
});

$("body").mouseout(function () {
    makeSentence();
    makeSentenceUpdate();
});

function saveBlocks() {

    var campaignsInitialMessages = {
        "nombreMsg": $("#nombreMsg").val(),
        "descripcion": $("#descripcion").val(),
        "mensajes": inputsBloquesArrayValues
    };

//    console.log(inputsBloquesArrayValues)
//    console.log(campaignsInitialMessages);

    var json = JSON.stringify(campaignsInitialMessages);
    console.log(json);
    addInitMessage(json);

}

function updateBlocks() {

    var campaignsInitialMessages = {
        "id": $("#id-update").val(),
        "nombreMsg": $("#nombreMsg-update").val(),
        "descripcion": $("#descripcion-update").val(),
        "mensajes": inputsBloquesArrayValuesUpdate
    };

//    console.log(inputsBloquesArrayValuesUpdate)
//    console.log(campaignsInitialMessages);

    var json = JSON.stringify(campaignsInitialMessages);
    console.log(json);
    updateInitMessage(json);
    
}

function updateInitMessage(json) {
    var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
            + "rest-api/action/update-init-message";

    var valido = true;
    var camposFaltantes;

    if ($("#nombreMsg-update").val() == "") {
        valido = false;
        camposFaltantes = "\n- Nombre del Mensaje";
    }


    if (inputsBloquesArrayValuesUpdate.length < 2) {
        alert("No hay suficientes bloques seleccionados.");
    } else {

        if (valido == true) {
            $.blockUI({message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>'});

            $.ajax({
                method: "POST",
                url: url,
                data: json,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).done(function (result) {
                listMesajesJson = result;
                $.unblockUI();

            });
        } else {
            alert("Faltan Campos Obligatorios." + camposFaltantes);

        }
    }
}

function addInitMessage(json) {
    var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
            + "rest-api/action/add-init-message";

    var valido = true;
    var camposFaltantes;

    if ($("#nombreMsg").val() == "") {
        valido = false;
        camposFaltantes = "\n- Nombre del Mensaje";
    }


    if (inputsBloquesArrayValues.length < 2) {
        alert("No hay suficientes bloques seleccionados.");
    } else {

        if (valido == true) {
            $.blockUI({message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>'});

            $.ajax({
                method: "POST",
                url: url,
                data: json,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).done(function (result) {
                $.unblockUI();

            });
        } else {
            alert("Faltan Campos Obligatorios." + camposFaltantes);

        }
    }
}


function editarMensaje() {

    alert("editar mensaje");
}


function selectMsf(id) {
    $("#sortable-update").empty();

    var obj = listMesajesJson.find(x => x.id === id);

    console.log(obj);

    $("#id-update").val(obj.id);
    $("#descripcion-update").val(obj.descripcion);
    $("#nombreMsg-update").val(obj.nombreMsg);

    $.each(obj.mensajes, function (index, value) {
        
        var idBlock = "id='block_update_" + numBlock + "'";
        var eraseBlockhtml = '<i class="fa fa-times" aria-hidden="true" onclick="eraseBlock(\'block_update_' + numBlock + '\')" ></i>&nbsp;&nbsp;';
        var block = "<li class='list-group-item' " + idBlock + ">" + eraseBlockhtml +
                "<input type='text' name='plantilla-update[]' value='" + value.content + "' onkeyup='makeSentenceUpdate()' style='width:90%' placeholder='Escribe aquí tu texto...' />" +
                "</li>";

        var blockVar = "<li class='list-group-item ui-sortable-handle' onclose='makeSentenceUpdate()' " + idBlock + ">" + eraseBlockhtml +
                "<input class='btn btn-info btn-sm' name='plantilla-update[]' type='button' value='" + value.content + "'>" +
                "</li>";

        if (value.type == "text") {
            $("#sortable-update").append(block);
            numBlock++;
        }

        if (value.type == "var") {
            $("#sortable-update").append(blockVar);
            numBlock++;
        }
        makeSentenceUpdate();
    });
}

