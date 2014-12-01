/*
 *
 *  Stacked Bar Graph
 *
 */

var stackedBarGraphModule = (function(){

    var xAxisTitle = "";
    var measurementUnits = "";
    var graphData = [];
    var primaryValueMap = {};
    var secondaryValueMap = {};

    var publicObject = {};
    publicObject.setGraphData = function(jsonData, xTitle, unitOfMeasurement){

        graphData = jsonData.graphData;
        primaryValueMap = jsonData.primaryValuemap;
        secondaryValueMap = jsonData.secondaryValuemap;
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

        //Easy colors accessible via a 20-step ordinal scale
        var colors = d3.scale.category10();

        // get possible colors from keys in first element
        colors.domain(d3.keys(graphData[0].secondaryData));

        graphData.forEach(function(d) {
            var y0 = 0;
            d.groups = colors.domain().map(function(secondaryName) {
                return {
                    primaryName: d.primaryName, secondaryName: secondaryName, y0: y0, y1: y0 += +d.secondaryData[secondaryName]
                };
            });
            d.total = d.groups[d.groups.length - 1].y1;
        });

        //Set up scales
        var xScale = d3.scale.ordinal()
            .domain(graphData.map(function(d) { return mapGraphData(d.primaryName, primaryValueMap); }))
            .rangeRoundBands([0, graphWidth], 0.15);

        var yScale = d3.scale.linear()
            .domain([0, d3.max(graphData, function(d) { return d.total; })])
            .range([graphHeight, 0]);

        var xAxis = d3.svg.axis()
            .scale(xScale)
            .orient("bottom");

        if( Object.keys(graphData).length > 20 ){

            xAxis.tickValues(xScale.domain().filter(function(d, i) { return !(i % 6); }))
        }

        var yAxis = d3.svg.axis()
            .scale(yScale)
            .orient("left")
            .tickFormat(d3.format("d"));

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .html(function(d) {
                var val = d.y1 - d.y0;
                return "<strong>"+mapGraphData(d.secondaryName, secondaryValueMap)+", "+
                        mapGraphData(d.primaryName, primaryValueMap) +" "+measurementUnits+
                        ":</strong> <span>" + val + " Patients</span>";
            });


        //Create SVG element
        var chart = d3.select("#"+containerIDString)
            .attr("width", containerWidth)
            .attr("height", containerHeight)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        chart.call(tip);

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

        var state = chart.selectAll(".bar")
            .data(graphData)
            .enter().append("g")
            .attr("class", "g")
            .attr("transform", function(d) { return "translate(" + xScale(mapGraphData(d.primaryName, primaryValueMap)) + ",0)"; });

        state.selectAll("rect")
            .data(function(d) { return d.groups; })
            .enter().append("rect")
            .attr("width", xScale.rangeBand())
            .attr("y", function(d) { return yScale(d.y1); })
            .attr("height", function(d) { return yScale(d.y0) - yScale(d.y1); })
            .style("fill", function(d) { return colors(d.secondaryName); })
            .on('mouseover', tip.show)
            .on('mouseout', tip.hide);

        var legend = chart.selectAll(".legend")
            .data(colors.domain().slice().reverse())
            .enter().append("g")
            .attr("class", "legend")
            .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

        legend.append("rect")
            .attr("x", graphWidth - 18)
            .attr("width", 18)
            .attr("height", 18)
            .style("fill", colors);

        legend.append("text")
            .attr("x", graphWidth - 24)
            .attr("y", 9)
            .attr("dy", ".35em")
            .style("text-anchor", "end")
            .text(function(d) { return mapGraphData(d, secondaryValueMap); });

    };

    return publicObject;

})();