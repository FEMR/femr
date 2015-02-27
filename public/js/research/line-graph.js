
/*
 *
 *  Line Graph
 *
 */

var lineGraphModule = (function(){

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
        var aspectRatio = 5/3;
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

        var line = d3.svg.line()
            .x(function(d) { return xScale(d.primaryName); })
            .y(function(d) { return (d) ? yScale(d.primaryValue) : 0; });

        var chart = d3.select("#"+containerIDString)
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

        chart.append("path")
            .datum(graphData)
            .attr("class", "line")
            .attr("d", line);

        var focus = chart.append("g")
            .attr("class", "focus")
            .style("display", "none");

        focus.append("circle")
            .attr("r", 4.5);

        focus.append("text")
            .attr("x", 9)
            .attr("dy", ".35em");

        chart.append("rect")
            .attr("class", "overlay")
            .attr("width", graphWidth)
            .attr("height", graphHeight)
            .on("mouseover", function() { focus.style("display", null); })
            .on("mouseout", function() { focus.style("display", "none"); })
            .on("mousemove", mousemove);

        var bisectValue = d3.bisector(function(d){ return d.primaryName; }).left;

        function mousemove() {

            var x0 = xScale.invert(d3.mouse(this)[0]),
                i = bisectValue(graphData, x0, 1);

            if( i < graphData.length ) {

                var d0 = graphData[i - 1],
                    d1 = graphData[i],
                    d = x0 - d0.primaryValue > d1.primaryValue - x0 ? d1 : d0;

                var xTranslate = xScale(d.primaryName);
                var yTranslate = yScale(d.primaryValue);

                focus.attr("transform", "translate(" + xTranslate + "," + yTranslate + ")");

                //console.log(d3.mouse(this));
                //console.log(graphWidth);
                if( d3.mouse(this)[0] > (graphWidth/2) ) {
                    console.log("right");
                    focus.select("text")
                        .attr('text-anchor', 'end')
                        .attr("dx", "-1em")
                        .text(mapGraphData(d.primaryName, valueMap) + " " + measurementUnits + ": " + d.primaryValue + " patients");

                }
                else{
                    focus.select("text")
                        .attr('text-anchor', 'start')
                        .attr("dx", "0")
                        .text(mapGraphData(d.primaryName, valueMap) + " " + measurementUnits + ": " + d.primaryValue + " patients");
                }
            }
        }
    };

    return publicObject;

})();


