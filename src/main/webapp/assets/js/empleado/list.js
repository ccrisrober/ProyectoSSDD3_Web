/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var oTable;
$(function() {
    oTable = $("#tableempleados").dataTable({'bPaginate': false});
});

$(document).ready(function() {

    $('#updForm').submit(function() {
        console.log("enviando formulario");
        return false;
    });

    /*$('.editar').click(function(event) {
        event.preventDefault();
        $('.modal-content').load("EmpleadoController?action=edit");
        /*var url = $(this).attr('href');

        var row = $(this).parent().parent();

        var name = row.find('td').eq(0).html();
        var surname = row.find('td').eq(1).html();
        var dni = row.find('td').eq(2).html();
        var departamento = row.find('td').eq(3).html();
        var sucursal = row.find('td').eq(4).html();

        alert("Name: ");

        $.ajax({
            url: "EmpleadoController?action=delete",
            data: {dni: dni},
            type: 'get',
            'dataType': 'json',
            error: function(error) {
                alert("Error: " + error);
            },
            success: function(data) {
                if (!data.error) {
                    row.remove();
                    alert(data.data);
                }
                else {
                    alert("No se ha podido borrar :(");
                }
            }
        });*/
    /*})*/
    /*$('.editar').click(function(data) {
        var target_row = $(this).parent().parent();
        var aPos = oTable.fnGetPosition(target_row); 
        oTable.fnDeleteRow(aPos);
        $("#tableempleados").dataTable();
        return false;
    });
    $('.borrar').click(function(data) {
        var target_row = $(this).parent().parent();
        var aPos = oTable.fnGetPosition(target_row); 
        oTable.fnDeleteRow(aPos);
        return false;
    });*/
    $('.editar').click(function(event) {
        event.stopPropagation();
    });
    
    $('.borrar').click(function(event) {
        event.stopPropagation();
        var goDelete = confirm("¿Deseas borrar?");
        if (goDelete) {
            var url = $(this).attr('href');

            var row = $(this).parent().parent();
            var rownum = oTable.fnGetPosition(row.find('td')[0])[0];

            var dni = row.find('td').eq(3).html();    // Extraigo el dni
            
            $.ajax({
                url: "EmpleadoController?action=delete",
                data: {dnifield: dni},
                type: 'get',
                'dataType': 'json',
                error: function(error) {
                    alert("Error: " + error);
                },
                success: function(data) {
                    if (!data.error) {
                        alert("borro");
                        oTable.fnDeleteRow(rownum);
                    }
                    else {
                        alert("No se ha podido borrar :(");
                    }
                }
            });
        }
        //}
        return false;
    });
});