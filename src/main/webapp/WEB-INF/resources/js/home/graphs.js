
// Shorthand for $( document ).ready()
$(function () {
    console.log("ready!");

    var url = window.location.protocol
            + "//" + window.location.host
            + $("base").attr("href")
            + "rest-api/action/"
            + "report-campaign-results";

    $.blockUI({message: '<div><i class="fa fa-spinner fa-pulse fa-lg"></i>  Espere un momento por favor.</div>'});
    $.ajax({
        method: "POST",
        url: url,
        // data: JSON.stringify(clienteArr),
        handleAs: "json",
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        }
    }).done(function (result) {

        fillGraphs(result);

        $.unblockUI();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        $.unblockUI();
    });


});

function fillGraphs(data) {
    var pie = new d3pie("pieChart", {
        "header": {
            "title": {
                "text": "Resultados de Campaña",
                "fontSize": 22,
                "font": "verdana"
            },
            "subtitle": {
                "text": "Códigos de resultados hasta el momento en la campaña",
                "color": "#999999",
                "fontSize": 10,
                "font": "verdana"
            },
            "titleSubtitlePadding": 12
        },
        "footer": {
            "text": "Actualizado al : ",
            "color": "#999999",
            "fontSize": 11,
            "font": "open sans",
            "location": "bottom-center"
        },
        "size": {
            "canvasHeight": 400,
            "canvasWidth": 590,
            "pieOuterRadius": "88%"
        },
        "data": data,
        "labels": {
            "outer": {
                "pieDistance": 32
            },
            "inner": {
                "format": "value"
            },
            "mainLabel": {
                "font": "verdana"
            },
            "percentage": {
                "color": "#e1e1e1",
                "font": "verdana",
                "decimalPlaces": 0
            },
            "value": {
                "color": "#110040",
                "font": "verdana"
            },
            "lines": {
                "enabled": true,
                "color": "#cccccc"
            },
            "truncation": {
                "enabled": true
            }
        },
        "effects": {
            "pullOutSegmentOnClick": {
                "effect": "linear",
                "speed": 400,
                "size": 8
            }
        }
    });
}