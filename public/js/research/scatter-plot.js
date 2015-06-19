/*
 *
 *  Scatterplot Graph
 *
 */

var scatterGraphModule = (function(){

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

    publicObject.buildGraph = function(containerIDString){

        var margin = {top: 20, right: 30, bottom: 50, left: 60};

        // keep 3/2 width/height ratio
        var aspectRatio = 5/2.5;
        var containerWidth = $("#"+containerIDString).width();
        var containerHeight = containerWidth / aspectRatio;

        // Calculate height/width taking margin into account
        var graphWidth = containerWidth - margin.right - margin.left;
        var graphHeight = containerHeight - margin.top - margin.bottom;

        var xScale = d3.scale.linear()
            .domain([d3.min(graphData, function(d) { return parseInt(d.primaryName); })-1,
                d3.max(graphData, function(d) { return parseInt(d.primaryName); })])
            .range([0, graphWidth]);

        var yScale = d3.scale.linear()
            .domain([0,
                d3.max(graphData, function(d) { return d.primaryValue; })])
            .range([graphHeight, 0]);

        var xAxis = d3.svg.axis()
            .scale(xScale)
            .orient("bottom");

        var yAxis = d3.svg.axis()
            .scale(yScale)
            .orient("left")
            .tickFormat(d3.format("d"));

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .html(function(d) {
                return '<span class="name">' + d.primaryName + ' '+measurementUnits+'</span> <span class="val"><strong>Patients: </strong>' + d.primaryValue + '</span>';
            });

        var chart = d3.select(".chart")
            .attr("width", containerWidth)
            .attr("height", containerHeight)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        chart.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + graphHeight + ")")
            .call(xAxis)
            .append("text")
            .attr("class", "title")
            .attr("x", graphWidth / 2 )
            .attr("y",  0 + margin.bottom)
            .style("text-anchor", "middle")
            .attr("dy", "-5px")
            .text(xAxisTitle);

        chart.call(tip);

        chart.append("g")
            .attr("class", "y axis")
            .call(yAxis)
            .append("text")
            .attr("class", "title")
            .attr("transform", "rotate(-90)")
            .attr("y", 0 - margin.left)
            .attr("x", 0 - (graphHeight / 2))
            .attr("dy", "1.25em")
            .style("text-anchor", "middle")
            .text("Number of Patients");

        chart.selectAll(".dot")
            .data(graphData)
            .enter().append("circle")
            .attr("class", "dot")
            .attr("r", 4.5)
            .attr("cx", function(d){ return xScale(d.primaryName)})
            .attr("cy", function(d){ return yScale(d.primaryValue)})
            .on('mouseover', tip.show)
            .on('mouseout', tip.hide);

    };

    return publicObject;

})();
