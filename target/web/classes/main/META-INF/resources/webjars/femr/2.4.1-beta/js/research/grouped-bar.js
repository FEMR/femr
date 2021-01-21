/*
 *
 *  Grouped Bar Graph
 *
 */

var groupedBarGraphModule = (function(){

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

        var secondaryKeys = d3.keys(graphData[0].secondaryData);

        graphData.forEach(function(d) {
            d.groups = secondaryKeys.map(function(secondaryName) {
                return {
                    primaryName: d.primaryName, secondaryName: secondaryName, value: +d.secondaryData[secondaryName]
                };

            });
        });

        var x0 = d3.scale.ordinal()
            .rangeRoundBands([0, graphWidth], .1)
            .domain(graphData.map(function(d) { return mapGraphData(d.primaryName, primaryValueMap); }));

        var x1 = d3.scale.ordinal()
            .domain(secondaryKeys).rangeRoundBands([0, x0.rangeBand()]);

        var y = d3.scale.linear()
            .range([graphHeight, 0])
            .domain([0, d3.max(graphData, function(d) {
                return d3.max(d.groups, function(d) {
                    return d.value;
                });
            })]);


        var xAxis = d3.svg.axis()
            .scale(x0)
            .orient("bottom");

        if( Object.keys(graphData).length > 20 ){

            xAxis.tickValues(x0.domain().filter(function(d, i) { return !(i % 6); }))
        }

        var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left")
            .tickFormat(d3.format("d"));

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .html(function(d) {
                return "<strong>"+mapGraphData(d.secondaryName, secondaryValueMap)+", "+
                    mapGraphData(d.primaryName, primaryValueMap) +" "+measurementUnits+
                    ":</strong> <span>" + d.value + " Patients</span>";
            });

        var svg = d3.select("#"+containerIDString)
            .attr("width", containerWidth)
            .attr("height", containerHeight)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        svg.call(tip);

        svg.append("g")
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

        svg.append("g")
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

        var bar = svg.selectAll(".bar")
            .data(graphData)
            .enter().append("g")
            .attr("class", "g")
            .attr("transform", function(d) { return "translate(" + x0(mapGraphData(d.primaryName, primaryValueMap)) + ",0)"; });

        bar.selectAll("rect")
            .data(function(d) { return d.groups; })
            .enter().append("rect")
            .attr("width", x1.rangeBand())
            .attr("x", function(d) { return x1(d.secondaryName); })
            .attr("y", function(d) { return y(d.value); })
            .attr("height", function(d) { return graphHeight - y(d.value); })
            .style("fill", function(d) { return colors(d.secondaryName); })
            .on('mouseover', tip.show)
            .on('mouseout', tip.hide);

        var legend = svg.selectAll(".legend")
            .data(secondaryKeys.slice().reverse())
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

