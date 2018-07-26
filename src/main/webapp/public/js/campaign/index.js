/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 comentario!!
 * */

var clientes;

$(document).ready(function () {
    $('#fechaAperturaCuenta').datepicker({
        language: "es",
        format: "yyyy-mm-dd",
        orientation: "bottom",
        todayHighlight: true
    });
    $('#fechaLimitePago').datepicker({
        language: "es",
        format: "yyyy-mm-dd",
        orientation: "bottom",
        todayHighlight: true
    });
    $('#fechaLimitePagoCampaign').datepicker({
        language: "es",
        format: "yyyy-mm-dd",
        orientation: "bottom",
        todayHighlight: true
    });
});

$(document).ready(function () {


    $("#saveCampaign").on("click", function () {

        var campaignData = {};
        campaignData["clave"] = $("#clave").val();
        campaignData["producto"] = $("#producto").val();

        var dateString = $("#fechaLimitePagoCampaign").val();

        var mydate = new Date(dateString.substring(0, 4), (dateString.substring(5, 7) - 1), dateString.substring(8, 10));

        campaignData["fechaLimitePagoCampaign"] = mydate;


        console.log(campaignData);

        if (campaignData["clave"] == "" || campaignData["fechaLimitePagoCampaign"] == "" || campaignData["producto"] == "") {
            bootbox.alert({
                message: "<span class='error-alert'>Llene los campos obligatorios.<span>",
                size: 'small'

            });
        } else {

            var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
                    + "rest-api/action/add-campaign";
            $.ajax({
                method: "POST",
                url: url,
                data: JSON.stringify(campaignData),
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
                    .done(function (result) {


                        if (result.code === 0) {

                            bootbox.alert({
                                message: result.msg,
                                size: 'small'

                            });

                            $('#registrarCampaign').modal('hide');
                            myDate1 = new Date(result.campaign.fechaCreacion);
                            myDate2 = new Date(result.campaign.fechaLimitePagoCampaign);
                            table_1.row.add([
                                result.campaign.clave,
                                moment(myDate1).locale('es').format('LL'),
                                moment(myDate2).locale('es').format('LL'),
                                result.campaign.producto
                            ]).draw(true);
                        }

                        $("#clave").val("");
                        $("#fechaLimitePagoCampaign").val("");
                        $("#producto").val("");
                    });
        }
    });

});


function startCampaign(clave) {
    $.blockUI({message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>'});

    var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
            + "rest-api/action/start-campaign";

    var startCampaign = {};
    startCampaign["clave"] = clave;

    $.ajax({
        method: "POST",
        url: url,
        data: JSON.stringify(startCampaign),
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).done(function (result) {
        $.unblockUI();
        bootBoxCustom("", "Se inició la campaña correctamente.", "small")
    }).fail(function (jqXHR, textStatus, errorThrown) {
        $.unblockUI();
        bootBoxCustom("error-alert", "Error en la conexión", "small")
    });
}

function bootBoxCustom(css_class, msg, size) {
    bootbox.alert({
        message: "<span class='" + css_class + "'>" + msg + "<span>",
        size: size
    });
}

function startClientCampaign(tel, fechaLimitePagoCampaign, noCuenta, clave, claveCliente, idCampaignsInitialMessages) {
    $.blockUI({message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>'});

    var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
            + "rest-api/action/start-client-campaign";

    var startCampaign = {};
    startCampaign["tel"] = tel;
    startCampaign["noCuenta"] = noCuenta;
    startCampaign["fechaLimitePagoCampaign"] = fechaLimitePagoCampaign;
    startCampaign["clave"] = clave;
    startCampaign["claveCliente"] = claveCliente;
    startCampaign["idCampaignsInitialMessages"] = idCampaignsInitialMessages;

    $.ajax({
        method: "POST",
        url: url,
        data: JSON.stringify(startCampaign),
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).done(function (result) {
        console.log(result);
        $.unblockUI();
        bootBoxCustom("", "Se inició la campaña correctamente.", "small")

    }).fail(function (jqXHR, textStatus, errorThrown) {
        $.unblockUI();
        alert("Error en la Conexión");
        bootBoxCustom("error-alert", "Error en la conexión", "small")

    });
}

$(function () {

    var url = window.location.protocol + "//" + window.location.host + $("base").attr("href")
            + "rest-api/action/search-client";

    $("#rfc").autocomplete({
        source: function (request, response) {
            var clientRFC = {};
            clientRFC["rfc"] = request.term;
            $.ajax({
                method: "POST",
                url: url,
                dataType: "json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(clientRFC),
                success: function (data) {

                    clientes = data;

                    var arrayAutocomplete = new Array();
                    $.each(data, function (index, value) {
                        arrayAutocomplete.push(value["rfc"]);
                    });
                    response(arrayAutocomplete);
                }
            });
        },
        minLength: 3,
        select: function (event, ui) {
            //log("Selected: " + ui.item.value + " aka " + ui.item.id);
            console.log(ui);
            makeObjFromClientsVar(ui.item.value, clientes)
        }
    });
});
var cliente;
function makeObjFromClientsVar(rfc, cli) {

    $.each(cli, function (index, value) {
        if (rfc == value["rfc"]) {
            cliente = value;

            $("#nombreCliente").val(value.nombreCliente);
            $("#correo").val(value.correo);
            $("#calleNoCliente").val(value.calleNoCliente);
            $("#coloniaCliente").val(value.coloniaCliente);
            $("#ciudadCliente").val(value.ciudadCliente);
            $("#estado").val(value.estado);
            $("#codPostal").val(value.codPostal);
            $("#telSms").val(value.telSms);

            $("#noCuenta").val(value.clienteCuentas[0].noCuenta);
            $("#agenciaActual").val(value.clienteCuentas[0].agenciaActual);
            $("#buc").val(value.clienteCuentas[0].buc);
            //$("#fechaAperturaCuenta").val(value.clienteCuentas[0].fechaAperturaCuenta.substring(0, 10));
            //$("#fechaLimitePago").val(value.clienteCuentas[0].fechaLimitePago.substring(0, 10));
            $("#montoMoroso").val(value.clienteCuentas[0].montoMoroso);

            $("#fechaAperturaCuenta").datepicker("update", value.clienteCuentas[0].fechaAperturaCuenta.substring(0, 10));
            $("#fechaLimitePago").datepicker("update", value.clienteCuentas[0].fechaLimitePago.substring(0, 10));

            $("#existeCliente").val(1);
            return false;
        }
    });


}