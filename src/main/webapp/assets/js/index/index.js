/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    //jvectormap data
    var visitorsData = {
        //Teruel
        "ES-AR": $('#datosTeruel').val(),
        //Soria
        "ES-CL": $('#datosSoria').val(),
    };
    $('#world-map').empty();
    //World map by jvectormap
    $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: "#fff",
        regionStyle: {
            initial: {
                fill: '#e4e4e4',
                "fill-opacity": 1,
                stroke: 'none',
                "stroke-width": 0,
                "stroke-opacity": 1
            }
        },
        series: {
            regions: [{
                    values: visitorsData,
                    scale: ["#3c8dbc", "#2D79A6"], //['#3E5E6B', '#A6BAC2'],
                    normalizeFunction: 'polynomial'
                }]
        },
        onRegionLabelShow: function(e, el, code) {
            if (typeof visitorsData[code] != "undefined")
                el.html(el.html() + ': ' + visitorsData[code] + ' % empleados');
        }
    });
}); 