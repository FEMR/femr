var allowedFilterValues = (function(){

    var allowedValues = {

        age: {
            graphTypes: ['bar','line','pie','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        gender: {
            graphTypes: ['bar','pie', 'table'],
            secondaryData: []
        },
        pregnancyStatus: {
            graphTypes: ['bar','pie','table'],
            secondaryData: []
        },
        pregnancyTime: {
            graphTypes: ['bar','line','pie','scatter','table'],
            secondaryData: []
        },
        height: {
            graphTypes: ['bar','pie','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        weight: {
            graphTypes: ['bar','line','pie', 'scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        dispensedMeds: {
            graphTypes: ['bar','pie','table'],
            secondaryData: []
        },
        prescribedMeds: {
            graphTypes: ['bar','pie','table'],
            secondaryData: []
        },
        bloodPressureSystolic: {
            graphTypes: ['line','scatter','table'],
            secondaryData: []
        },
        bloodPressureDiastolic: {
            graphTypes: ['line','scatter','table'],
            secondaryData: []
        },
        temperature: {
            graphTypes: ['bar','line','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        oxygenSaturation: {
            graphTypes: ['bar','line','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        heartRate: {
            graphTypes: ['bar','line','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        respiratoryRate: {
            graphTypes: ['bar','line','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        },
        glucose: {
            graphTypes: ['bar','line','scatter','table'],
            secondaryData: ['gender', 'pregnancyStatus']
        }
    };

    var combinableGraphs = ["stacked-bar", "grouped-bar"];

    var publicObject = {};
    publicObject.isPrimaryDataValid = function(dataset){

        if( allowedValues[dataset] != undefined ){

            return true;
        }
        else return false;
    };
    publicObject.isGraphTypeAllowed = function(dataset, graphType){

        if( allowedValues[dataset] != undefined ){
            var found = false;
            allowedValues[dataset].graphTypes.forEach(function(val){
                if( val === graphType ){

                    found = true;
                    return;
                }
            });
            return found;
        }
    };
    publicObject.isSecondaryDataAllowed = function(dataset, secondaryData){

        if( allowedValues[dataset] != undefined ){

            var found = false;
            allowedValues[dataset].secondaryData.forEach(function(val){
                if( val === secondaryData ){

                    found = true;
                    return;
                }
            });
            return found;
        }
    };
    publicObject.isCombinableGraph = function(graphType){

        var found = false;
        combinableGraphs.forEach(function(val){

            if( val == graphType ){

                found = true;
                return;
            }
        });
        return found;
    };
    return publicObject;

})();


var graphLoaderModule = (function(){

    var graphType = 'bar';
    var graphContainerId = "graph";
    var primaryDataType = "";
    var secondaryDataType = "";
    var jsonData;

    var statisticsFields = {
        average: $("#average"),
        range: $("#range"),
        totalPatients: $("#totalPatients"),
        totalEncounters: $("#totalEncounters")
    };

    var showGraphLoadingIcon = function(){

        $(".chart-container").find(".loading").show();
    };

    var hideGraphLoadingIcon = function(){

        $(".chart-container").find(".loading").hide();
    };

    var publicObject = {};
    publicObject.loadGraph = function(newGraphType, postData){

        showGraphLoadingIcon();

        graphType = newGraphType;

        //console.log(postData);

        // remove any previous graph
        d3.selectAll("svg > *").remove();
        $(statisticsFields.range).find(".val").text("");
        $(statisticsFields.average).find(".val").text("");
        $(statisticsFields.totalPatients).find(".val").text("");
        $(statisticsFields.totalEncounters).find(".val").text("");

        // post graph
        $.post("/research", postData, function (rawData) {

            jsonData = $.parseJSON(rawData);

            if( typeof jsonData.graphData == 'undefined' || jsonData.graphData.length == 0 ){

                // show error
                hideGraphLoadingIcon();
                alert("No patients match the chosen filters");

                return;
            }

            primaryDataType = filterMenuModule.getPrimaryDataset();
            secondaryDataType = filterMenuModule.getSecondaryDataset();

            $(".graph-instructions").hide();
            $(".graph-area").show();

            console.log(jsonData);

            var xAxisTitle = "";
            if( "xAxisTitle" in jsonData ) {
                xAxisTitle = jsonData.xAxisTitle;
            }

            var unitOfMeasurement = "";
            if( "unitOfMeasurement" in jsonData ) {

                unitOfMeasurement = jsonData.unitOfMeasurement;
                if( unitOfMeasurement.length > 0 ) {
                    xAxisTitle += " (" + unitOfMeasurement + ")";
                }
            }

            $("#graph").show();
            $("#table-container").hide();

            // remove any previous graph
            d3.selectAll("svg > *").remove();

            switch(graphType){

                case 'line':

                    lineGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    lineGraphModule.buildGraph(graphContainerId);
                    break;

                case 'scatter':
                    scatterGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    scatterGraphModule.buildGraph(graphContainerId);
                    break;

                case 'pie':
                    pieGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    pieGraphModule.buildGraph(graphContainerId);
                    break;

                case 'stacked-bar':
                    stackedBarGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    stackedBarGraphModule.buildGraph(graphContainerId);
                    break;

                case 'grouped-bar':
                    groupedBarGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    groupedBarGraphModule.buildGraph(graphContainerId);
                    break;

                case 'bar':
                    barGraphModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    barGraphModule.buildGraph(graphContainerId);
                    break;

                case 'table':
                default:
                    $("#graph").hide();
                    $("#table-container").show();
                    tableChartModule.setGraphData(jsonData, xAxisTitle, unitOfMeasurement);
                    tableChartModule.buildGraph();
            }


            if( typeof jsonData.primaryValuemap != "undefined" &&
                Object.keys(jsonData.primaryValuemap).length > 0){

                // If there is a value map, statistics are not valid
                $(statisticsFields.average).hide();
                $(statisticsFields.range).hide();

            }
            else {

                $(statisticsFields.average).show();
                $(statisticsFields.range).show();

                // Grab Statistics
                if ("average" in jsonData) {

                    var average = jsonData.average;
                    //console.log(average);
                    if( filterMenuModule.getPrimaryDataset() == "height" ){

                        var average = inchesToFeetInches(average);
                    }
                    else {

                        average = parseFloat(average).toFixed(2);
                    }

                    $(statisticsFields.average).find(".val").text(average + " " + unitOfMeasurement);
                }
                else {
                    $(statisticsFields.average).find(".val").text("n/a");
                }


                if (("rangeLow" in jsonData) && ("rangeHigh" in jsonData)) {

                    var rangeLow = jsonData.rangeLow;
                    //console.log(rangeLow);
                    if( filterMenuModule.getPrimaryDataset() == "height" ){

                        var rangeLow = inchesToFeetInches(rangeLow);
                    }
                    else {

                        rangeLow = parseFloat(rangeLow).toFixed(2);
                    }

                    var rangeHigh = jsonData.rangeHigh;
                    //console.log(rangeHigh);
                    if( filterMenuModule.getPrimaryDataset() == "height" ){

                        var rangeHigh = inchesToFeetInches(rangeHigh);
                    }
                    else {

                        rangeHigh = parseFloat(rangeHigh).toFixed(2);
                    }

                    $(statisticsFields.range).find(".val").text(rangeLow + " - " + rangeHigh + " " + unitOfMeasurement);
                }
                else {
                    $(statisticsFields.range).find(".val").text("n/a");
                }
            }

            if ("totalPatients" in jsonData) {

                var total = jsonData.totalPatients;
                $(statisticsFields.totalPatients).find(".val").text(total);
            }
            else {
                $(statisticsFields.totalPatients).find(".val").text("n/a");
            }

            if ("totalEncounters" in jsonData) {

                var total = jsonData.totalEncounters;
                $(statisticsFields.totalEncounters).find(".val").text(total);
            }
            else {
                $(statisticsFields.totalEncounters).find(".val").text("n/a");
            }

            hideGraphLoadingIcon();
        });

    };

    publicObject.reloadGraph = function(newContainerId){

        var thisContainerId = graphContainerId;
        if( typeof newContainerId !== "undefined" ){

            thisContainerId = newContainerId;
        }

        // remove any previous graph
        d3.selectAll("svg > *").remove();

        if( jsonData != null ) {
            switch (graphType) {

                case 'line':
                    lineGraphModule.buildGraph(thisContainerId);
                    break;

                case 'scatter':
                    scatterGraphModule.buildGraph(thisContainerId);
                    break;

                case 'pie':
                    pieGraphModule.buildGraph(thisContainerId);
                    break;

                case 'stacked-bar':
                    stackedBarGraphModule.buildGraph(thisContainerId);
                    break;

                case 'grouped-bar':
                    groupedBarGraphModule.buildGraph(thisContainerId);
                    break;

                case 'bar':
                    barGraphModule.buildGraph(thisContainerId);
                    break;

                default:
                // do nothing
            }
        }
    };
    publicObject.getGraphType = function (){ return graphType; };
    publicObject.getPrimaryDataType = function (){ return primaryDataType; };
    publicObject.getSecondaryDataType = function (){ return secondaryDataType; };
    publicObject.init = function(){

        // do any initialization that might be needed
    };

    return publicObject;
})();


$(document).ready(function(){

    filterMenuModule.init();
    graphLoaderModule.init();

    $('#MissionTripId').change(function () {
        var value = $('#MissionTripId option:selected').val();

        if (value != -1 && $('#gfilter-menu').is(':visible')) {
            $('#gfilter-menu').hide();
        } else {
            $('#gfilter-menu').show();
        }
    });

    // Detect changes in main container width, redraw chart
    var lastChartWidth = $(".main").width();
    $(window).on("resize", function() {
        var currChartWidth = $(".main").width();
        if( lastChartWidth != currChartWidth ) {

            graphLoaderModule.reloadGraph();
            lastChartWidth = currChartWidth;
        }
    }).trigger("resize");

    mydate3();
    mydate1();

});


Array.prototype.inArray = function(comparer) {
    for(var i=0; i < this.length; i++) {
        if(comparer(this[i])) return true;
    }
    return false;
};

// adds an element to the array if it does not already exist using a comparer
// function
Array.prototype.pushIfNotExist = function(element, comparer) {
    if (!this.inArray(comparer)) {
        this.push(element);
    }
};

function inchesToFeetInches(inches){

    if( inches == 0 ) return '0"';

    var feet = parseInt(inches / 12);
    var inch = parseInt(inches % 12);
    var str = "";

    if( feet > 0 ) {
        str = feet + "'";
    }
    str += inch + '"';

    return str;
}


function mapGraphData(name, datamap){

    var toRet = name;

    // Do nothing if value map does not exist
    if( typeof datamap != "undefined" ) {

        if (Object.keys(datamap).length > 0) {

            if (name in datamap) {
                toRet = datamap[name];
            }
        }
    }

    if( graphLoaderModule.getPrimaryDataType() == "height" && datamap == null ){

        if( toRet.search("-") ){

            var splitVals = toRet.split("-");
            var convVals = [];
            splitVals.forEach(function(val){

                convVals.push(inchesToFeetInches(val));
            });

            toRet = convVals.join(" - ");
        }
        else {
            toRet = inchesToFeetInches(toRet);
        }
    }
    return toRet;
};

function mydate()
{
    //alert("");
    document.getElementById("startDate").hidden=false;
    document.getElementById("ndt").hidden=true;
}
function mydate1()
{
    d=new Date(document.getElementById("startDate").value);
    dt=d.getUTCDate();
    mn=d.getUTCMonth();
    mn++;
    yy=d.getFullYear();
    document.getElementById("ndt").value=dt+"/"+mn+"/"+yy;
    document.getElementById("ndt").hidden=false;
    document.getElementById("startDate").hidden=true;
    document.getElementById("start-up").innerHTML=dt+"/"+mn+"/"+yy;
}
function mydate2()
{
    //alert("");
    document.getElementById("endDate").hidden=false;
    document.getElementById("ndt2").hidden=true;
}
function mydate3()
{
    d=new Date(document.getElementById("endDate").value);
    dt=d.getUTCDate();
    mn=d.getUTCMonth();
    mn++;
    yy=d.getFullYear();
    document.getElementById("ndt2").value=dt+"/"+mn+"/"+yy;
    document.getElementById("ndt2").hidden=false;
    document.getElementById("endDate").hidden=true;
    document.getElementById("start-end").innerHTML=dt+"/"+mn+"/"+yy;
}

