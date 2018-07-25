/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var actionFormCliente;
var clientsTable;
var accountsTable;
var cliente;
var titleModal;
var isValidForm = true;

$(document).ready(function () {
    actionFormCliente = $("#actionFormCliente").val();
    clientsTable = createTable("dataTableClients");
    accountsTable = createTable("dataTableAccounts", defineColumnsAccount());
    
    $("[name^='diaCorte']").on('keydown', function(e){
        -1 !== $.inArray(e.keyCode,[46,8,9,27,13,110,190])
                || (/65|67|86|88/.test(e.keyCode) && (e.ctrlKey === true || e.metaKey === true))
                && (!0 === e.ctrlKey || !0 === e.metaKey)
                || 35 <= e.keyCode&&40 >= e.keyCode
                || (e.shiftKey || 48 > e.keyCode || 57 < e.keyCode)
                && (96 > e.keyCode || 105 < e.keyCode)
                && e.preventDefault();
    });

    
    $('#addClient').on('show.bs.modal', function (e) {
        setTitleModalDataClient();
        $('#claveCliente').prop('readonly', actionFormCliente === "editClient");
        $('#noCuenta').prop('readonly', actionFormCliente === "editClient");
        if(actionFormCliente === "addNewClient"){
            $('#initAccountClient').show();
        }
    });
    
    $('#addClient').on('hidden.bs.modal', function (e) {
        actionFormCliente = "addNewClient";
        clienteForm.reset();
    });
    
    $('#dataAccounts').on('hidden.bs.modal', function (e) {
        actionFormCliente = "addNewClient";
        edicionCuentaForm.reset();
        accountsTable.clear().draw();
        cliente = null;
    });
    
    $("button[name^='editClient']").click(function(){
        actionFormCliente = "editClient";
        $('#initAccountClient').hide();
        getDataClientToEdit($(this).attr('name'));
    });
    
    $("button[name^='adminAccounts']").click(function(){
        actionFormCliente = "editAccounts";
        $("#noCuenta_EA").prop('readonly', false);
        getDataAccountToEdit($(this).attr('name'));
    });
    
    $("#dataTableAccounts tbody").on("click", "button", function(){
        //actionFormCliente = "editAccounts";
        var data = accountsTable.row( $(this).parents('tr') ).data();
        $("#noCuenta_EA").prop('readonly', true);
        setDataFormAccount(data[0]);
    });
    
});

function defineColumnsAccount(){
    var columns = new Array();
    
    columns.push({"data": "noCuenta"});
    columns.push({"data": "descProducto"});
    columns.push({"data": "fechaAperturaCuenta"});
    columns.push({"data": "fechaLimitePago"});
    columns.push({"data": "diaCorte"});
    columns.push({"data": "totalDeudor"});
    columns.push({"data": "pagoMinimo"});
    columns.push({"data": "montoMoroso"});
    columns.push({"targets": 2, "data": "fechaAperturaCuenta", 
        "render": function ( data, type, row, meta ) {
            return '<span>' + moment(row[2]).format("DD/MM/YYYY") + '</span>';
        }
    });
    columns.push({"targets": 3, "data": "fechaLimitePago", 
        "render": function ( data, type, row, meta ) {
            return '<span>' + moment(row[3]).format("DD/MM/YYYY") + '</span>';
        }
    });
    columns.push({"data": null,
        "targets": -1,
        "defaultContent": "<button class='btn btn-primary btn-sm' type='button'> <i class='fa fa-address-book'></i> Editar cuenta </button>"
    });
    
    return columns;
}

function validarClienteForm() {
    var formValues = $("#clienteForm").serializeArray();
    var formValuesCuentas = $("#clienteCuentaForm").serializeArray();
    var formAccount = $("#edicionCuentaForm").serializeArray();
    var clienteArr = clienteArray(formValues, formValuesCuentas);
    
    switch (actionFormCliente){
        case "addNewClientToCampaign":
        case "addNewClient":
            validarCamposObligatorios(clienteArr);
            break;
        case "editClient":
            clienteArr = dataClient(formValues);
            validaCamposCliente(clienteArr);
            break;
        case "editAccounts":
            var account = dataAccount(formAccount);
            validaDatosCuenta(account);
            break;
    }
    
    if(isValidForm){
        switch (actionFormCliente){
            case "addNewClientToCampaign":
                var existeCliente = $("#existeCliente").val();
                if (existeCliente === "0") {
                    insertClient(clienteArr);
                }
                insertClienToCampaign(clienteArr);
                break;
            case "addNewClient":
                insertClient(clienteArr);
                break;
            case "editClient":
                editClient(clienteArr);
                break;
            case "editAccounts":
                editAccounts(cliente);
                break;
        }       
    }
}

function getDataAccountToEdit(idEditAccion) {
    var searchData = idEditAccion.split("adminAccounts-")[1];    
    getDataClient(dataFilter(searchData));
}

function getDataClientToEdit(idEditAccion) {
    var searchData = idEditAccion.split("editClient-")[1];    
    getDataClient(dataFilter(searchData));
}

function insertClienToCampaign(clienteArr) {
    ajaxFormClient(clienteArr, "add-client-to-campaign");
}

function insertClient(clienteArr) {
    ajaxFormClient(clienteArr, "add-client");
}

function editClient(clienteArr) {
    ajaxFormClient(clienteArr, "edit-client");
}

function editAccounts(clienteArr) {
    ajaxFormClient(clienteArr, "edit-account");
}

function getDataClient(clienteArr) {
    ajaxFormClient(clienteArr, "search-client");
}

function ajaxFormClient(clienteArr, option) {
    var url = window.location.protocol 
            + "//" + window.location.host 
            + $("base").attr("href")
            + "rest-api/action/"
            + option;
    $.blockUI({ message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>' });
    $.ajax({
        method: "POST",
        url: url,
        data: JSON.stringify(clienteArr),
        handleAs: "json",
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        }
    }).done(function (result) {
        
        switch (option) {
            case "search-client":
                //cliente = JSON.stringify(result[0]);
                cliente = result[0];
                if(cliente != null){
                    fillDataClient(clienteForm, cliente);
                    if(actionFormCliente === "editAccounts"){
                        fillDataTableAccounts(cliente.clienteCuentas);
                        $('#dataAccounts').modal('show');
                    }
                    if(actionFormCliente === "editClient"){
                        $('#addClient').modal('show');
                    }
                    modifyOptions(option);
                }
                break;
            case "add-client":
            case "edit-client":
                addClientToTable(result);
                $('#addClient').modal('hide');
                modifyOptions(option);
                break;
            case "edit-account":
                edicionCuentaForm.reset();
                $("#noCuenta_EA").prop('readonly', false);
                reDrawTableAccounts();
                break;
            default:
                addClientToTable(result);
                $('#addClient').modal('hide');
                break;
        }
              
        $.unblockUI();
        getMessage(option);        
    }).fail(function(jqXHR, textStatus, errorThrown){
        $.unblockUI();
    });
}

function reDrawTableAccounts(){
    accountsTable.clear().draw();
    fillDataTableAccounts(cliente.clienteCuentas);
}

function fillDataTableAccounts(data){
    $.each(data, function(i, item){
        accountsTable.row.add([
            item.noCuenta
            ,item.descProducto
            ,item.fechaAperturaCuenta
            ,item.fechaLimitePago
            ,item.diaCorte
            ,item.totalDeudor
            ,item.pagoMinimo
            ,item.montoMoroso
        ]);
    });
    accountsTable.draw();
}

function modifyOptions(option){
    switch (option) {
        case "search-client":
            $('#claveCliente').prop('readonly', true);
            $('#initAccountClient').hide();
            break;
        case "add-client":
        case "edit-client":
        case "edit-account":
            actionFormCliente = "addNewClient";
            clienteForm.reset();
            break;
        default:
            actionFormCliente = "addNewClient";
            clienteForm.reset();
            break;
    }
    setTitleModalDataClient();
}

function setDataFormAccount(idAccount){
    fillDataFormAccount(edicionCuentaForm, findAccountClient(idAccount, cliente.clienteCuentas));
}

function findAccountClient(idAccount, dataArray){
    var data = dataArray.filter(account => account.noCuenta === idAccount);
    
    return data.length > 0 ? data[0] :  null;
}

function fillDataFormAccount(formAccount, dataToFill){
    $.each(dataToFill, function(key, value){
        var selectorField = "[name=" + key + "_EA]";        
        var field = $(selectorField, formAccount);
        switch (key + "_EA") {
            case "fechaAperturaCuenta_EA":
            case "fechaLimitePago_EA":
                field.datepicker("update", moment(value).toDate());
                break;
            default:
                field.val(value);
                break;
        }
    });
}

function cleanDataFormAccount(){
    edicionCuentaForm.reset();
    $("#noCuenta_EA").prop('readonly', false);
}

function fillDataClient(formClient, dataToFill){
    $.each(dataToFill, function(key, value){
        var field = $("[name='" + key + "']", formClient);
        field.val(value);
    });
}

function getMessage(option){
    var message = "";
    switch (option) {
        case "add-client":
            message = "Se ha agregado el CLiente.";
            bootbox.alert({
                message: message,
                size: 'small'
            });
            break;
        case "search-client":
            message = "Datos obtenidos";
            break;
        case "add-client-to-campaign":
            message = "Se ha agregado el CLiente a la campaña.";
            bootbox.alert({
                message: message,
                size: 'small'
            });
            break;
        default:
            message = "Se ha agregado el CLiente.";
            break;
    }
}

function addClientToTable(result) {
    if (actionFormCliente === "addNewClient") {
        clientsTable.row.add([
            result.claveCliente,
            result.nombreCliente,
            result.ciudadCliente,
            result.telSms,
            result.clienteCuentas[0].noCuenta + " " + result.clienteCuentas[0].agenciaActual
        ]).draw(true);
    }
    if (actionFormCliente === "addNewClientToCampaign") {
        table_1.row.add([
            "<a href='/CognitivaCobranza/clients/edit.htm?clavecliente=" + result.clavecliente + "'>" + result.clavecliente + "</a>",
            result.nombreCliente,
            result.ciudadCliente,
            result.telSms,
            result.clienteCuentas[0].noCuenta + " " + result.clienteCuentas[0].agenciaActual
        ]).draw(true);
    }
}

function clienteArray(formClient, formValuesCuentas) {

    var clientData = {};
    var cuentaData = {};

    $.each(formClient, function (key, value) {
        clientData[value["name"]] = value["value"];
    });

    $.each(formValuesCuentas, function (i, item) {
        switch (item["name"]) {
            case "fechaAperturaCuenta":
            case "fechaLimitePago":
            cuentaData[item["name"]] = moment($('#'+item["name"]).datepicker("getDate")).format('YYYY-MM-DDTHH:mm:ss-SSSS');
            break;
        default:
            cuentaData[item["name"]] = item["value"];
            break;
        }        
    });

    var cuentas = new Array(cuentaData);
    clientData["clienteCuentas"] = cuentas;

    clientData["clave"] = $("#clave").val();

    return clientData;

};

function dataClient(formClient) {
    var clientData = {};
    $.each(formClient, function (key, value) {
        clientData[value["name"]] = value["value"];
    });
    return clientData;
}

function dataAccount(formAccount){
    var cuentaData = {};
    $.each(formAccount, function (i, item) {
        switch (item["name"]) {
            case "fechaAperturaCuenta_EA":
            case "fechaLimitePago_EA":
            cuentaData[item["name"].split("_")[0]] = moment($('#'+item["name"]).datepicker("getDate")).format('YYYY-MM-DDTHH:mm:ss-SSSS');
            break;
        default:
            cuentaData[item["name"].split("_")[0]] = item["value"];
            break;
        }
    });
    return cuentaData;
}

function dataFilter(value) {
    var searchClientRq = {
        clave: value
    };
    return searchClientRq;
}

function setWhiteBg(element){
    if(!element.readOnly)
        $(element).css('background-color', '#ffffff');
}

function setTitleModalDataClient(){
    var title = "";
    
    switch (actionFormCliente){
        case "addNewClient":
            title = "Agregar";
            break;
        case "editClient":
            title = "Editar";
            break;
        default:
            title = "Agregar";
            break;
    }
    
    titleModal = title;
    $('#title_modal').html(title);
}

function validaCamposCliente(clienteArr){
    isValidForm = true;
    $.each(clienteArr, function (key, value) {
        if (key === "claveCliente" && value === "") {
            inputAlert(key);
        }
        if (key === "nombreCliente" && value === "") {
            inputAlert(key);
        }
        if (key === "telSms") {
           isNumber(key , value);
        }
    });
}

function validaDatosCuenta(cuenta){
    isValidForm = true;
    $.each(cuenta, function (key, value) {
        if (key === "noCuenta" && value === "") {
            inputAlert(key);
        }
        if (key === "descProducto" && value === "") {
            inputAlert(key);
        }
        if (key === "fechaAperturaCuenta" && value === "") {
            inputAlert(key);
        }
        if (key === "fechaLimitePago" && value === "") {
            inputAlert(key);
        }
        if (key === "montoMoroso") {
            isNumber(key , value);              
        }
        if (key === "totalDeudor") {
            isNumber(key , value);              
        }
        if (key === "pagoMinimo" ) {
            isNumber(key , value);
        }
    });
    if(isValidForm){
        var accountModify = cuenta;
        var existeCuenta = cliente.clienteCuentas.filter(account => account.noCuenta === accountModify.noCuenta);
        if(existeCuenta.length === 0){
            cliente.clienteCuentas.push(accountModify);
        }else{
            $.each(cliente.clienteCuentas, function(i, item){
                if(item.noCuenta === accountModify.noCuenta){
                    setDataToUpdateAccount(accountModify, item);
                }
            });
        }
    }
}

function setDataToUpdateAccount(accountEdit, accountToEdit){
    $.each(accountEdit, function (key, value) {
        accountToEdit[key] = value;
    });
}

function validarCamposObligatorios(clienteArr) {
    isValidForm = true;
    $.each(clienteArr, function (key, value) {
        if (key === "claveCliente" && value === "") {
            inputAlert(key);
        }
        if (key === "nombreCliente" && value === "") {
            inputAlert(key);
        }
        if (key === "telSms") {
           isNumber(key , value);
        }
    });
    if(clienteArr.clienteCuentas.length > 0){
        $.each(clienteArr.clienteCuentas, function (key, value) {
            $.each(value, function (key, value) {
                if (key === "noCuenta" && value === "") {
                    inputAlert(key);
                }
                if (key === "descProducto" && value === "") {
                    inputAlert(key);
                }
                if (key === "fechaAperturaCuenta" && value === "") {
                    inputAlert(key);
                }
                if (key === "fechaLimitePago" && value === "") {
                    inputAlert(key);
                }
                if (key === "montoMoroso") {
                    isNumber(key , value);              
                }
                if (key === "totalDeudor") {
                    isNumber(key , value);              
                }
                if (key === "pagoMinimo" ) {
                    isNumber(key , value);
                }
            });
        });    
    }
}

function isNumber(key , value){
    if (!$.isNumeric(value)) 
    {
        inputAlert(key);
    }
}

function inputAlert(elementId) {
    $("#" + elementId).css('background-color', '#F98282');
    isValidForm = false;
}

