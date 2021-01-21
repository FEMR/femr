/*
 *
 *  Bar Graph
 *
 */

var tableChartModule = (function(){

    var xAxisTitle = "";
    var measurementUnits = "";
    var graphData = [];
    var valueMap = {};


    var publicObject = {};
    publicObject.setGraphData = function(jsonData, xTitle, unitOfMeasurement){

        graphData = jsonData.graphData;
        valueMap = jsonData.primaryValuemap;
        xAxisTitle = xTitle;
        measurementUnits = unitOfMeasurement;
    };

    publicObject.buildGraph = function(){

        var table = $("#table-container");
        $(table).html('');

        var table_html = '<table class="table table-striped">' +
            '<thead>' +
            '<tr>' +
                '<th>'+xAxisTitle+'</th>' +
                '<th>Number of Patients</th>' +
            '</tr>' +
            '</thead>';

        table_html += '<tbody>';

        $.each(graphData, function (key, obj) {

            table_html += '<tr>' +
                '<td>'+mapGraphData(obj.primaryName, valueMap)+'</td>' +
                '<td>'+obj.primaryValue+'</td>' +
                '</tr>';

        });

        table_html += '</tbody>';

        $(table).append(table_html);

    };

    return publicObject;

})();