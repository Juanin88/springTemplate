/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('#fechaAperturaCuenta').datepicker({
        language: "es",
        format: "dd/mm/yyyy",
        orientation: "bottom",
        todayHighlight: true
    });
    $('#fechaLimitePago').datepicker({
        language: "es",
        format: "dd/mm/yyyy",
        orientation: "bottom",
        todayHighlight: true
    });
    $('#fechaAperturaCuenta_EA').datepicker({
        language: "es",
        format: "dd/mm/yyyy",
        orientation: "bottom",
        todayHighlight: true
    });
    $('#fechaLimitePago_EA').datepicker({
        language: "es",
        format: "dd/mm/yyyy",
        orientation: "bottom",
        todayHighlight: true
    });
});
