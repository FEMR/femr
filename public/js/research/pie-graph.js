/*
 *
 *
  *  Pie Graph
 *
 */

var pieGraphModule = (function(){

    var xAxisTitle = "";
    var measurementUnits = "";
    var graphData = [];
    var valueMap = {};
    var label_ids = {};
    var arc_ids = {};

    var publicObject = {};
    publicObject.setGraphData = function(jsonData, xTitle, unitOfMeasurement){

        graphData = jsonData.graphData;
        valueMap = jsonData.primaryValuemap;
        xAxisTitle = xTitle;
        measurementUnits = unitOfMeasurement;
    };


    publicObject.buildGraph = function(containerIDString){

        var margin = {top: 20, right: 30, bottom: 50, left: 60};

        // keep 3/2 width/height ratio on container
        var aspectRatio = 5/2.5;
        var containerWidth = $("#"+containerIDString).width();
        var containerHeight = containerWidth / aspectRatio;

        // Calculate height/width taking margin into account
        var graphWidth = containerWidth - margin.right - margin.left;
        var graphHeight = containerHeight - margin.top - margin.bottom;

        var pieWidth = graphHeight-20;
        //var pieHeight = graphHeight-20;
        var outerRadius = pieWidth / 2;
        var innerRadius = 0;
        //var textOffset = 14;


        var arc = d3.svg.arc()
            .innerRadius(innerRadius)
            .outerRadius(outerRadius);

        var arcOver = d3.svg.arc()
            .innerRadius(innerRadius)
            .outerRadius(outerRadius + 10);

        var pie = d3.layout.pie()
            .value(function(d){ return d.primaryValue; } );
        var color = d3.scale.category20();

        var pied_data = pie(graphData);

        var pieLeftTranslate = (graphWidth/2)+margin.left;
        var pieTopTranslate = (graphHeight/2)+margin.top;
        var chart = d3.select("#"+containerIDString)
            .attr("width", containerWidth)
            .attr("height", containerHeight)
            .append("g")
            .attr("transform", "translate(" + pieLeftTranslate + "," + pieTopTranslate + ")");

        //Set up Arcs
        var arcs = chart.selectAll("g.arc")
            .data(pied_data)
            .enter()
            .append("g")
            .attr("class", "arc")
            .each(function(d, i){

                var labelId = "label-"+i;
                var arcId = "arc-"+i;
                arc_ids[d.data.primaryName] = arcId;
                label_ids[d.data.primaryName] = labelId;

            })
            .attr('id', function(d){ return arc_ids[d.data.primaryName]; })
            .on("mouseover", function(d) {
                d3.select(this).select("path").transition()
                    .duration(200)
                    .attr("d", arcOver);

                var labelId = label_ids[d.data.primaryName];
                $("#"+labelId).show();

            })
            .on("mouseout", function(d) {
                d3.select(this).select("path").transition()
                    .duration(100)
                    .attr("d", arc);

                var labelId = label_ids[d.data.primaryName];
                $("#"+labelId).hide();

            });

        arcs.append("path")
            .attr("fill", function(d, i) { return color(i); })
            .attr("data-legend", function(d){ return mapGraphData(d.data.primaryName, valueMap); })
            .attr("d", arc);


        var legend = d3.select("#"+containerIDString)
            .append("g")
            .attr("class", "legend")
            .attr("width", outerRadius)
            //.attr("height", outerRadius * 2)
            .attr("transform", "translate(20,"+margin.top+")")
            .selectAll("g.item")
            .data(graphData)
            .enter()
            .append("g")
            // don't show 0 values in the legend
            .filter(function(d){ return d.primaryValue > 0; })
            .attr("class", "item")
            .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; })
            .on("mouseover", function(d) {

                var arcID = arc_ids[d.primaryName];
                d3.select("#"+arcID).select("path").transition()
                    .duration(200)
                    .attr("d", arcOver);

                var labelId = label_ids[d.primaryName];
                $("#"+labelId).show();
            })
            .on("mouseout", function(d) {

                var arcID = arc_ids[d.primaryName];
                d3.select("#"+arcID).select("path").transition()
                    .duration(100)
                    .attr("d", arc);

                var labelId = label_ids[d.primaryName];
                $("#"+labelId).hide();

            });

        legend.append("rect")
            .attr("width", 18)
            .attr("height", 18)
            .style("fill", function(d, i) { return color(i); });


        legend.append("text")
            .attr("x", 24)
            .attr("y", 9)
            .attr("dy", ".35em")
            .text(function(d) { return mapGraphData(d.primaryName, valueMap)+" "+measurementUnits; });


        //GROUP FOR LABELS
        var label_group = d3.select("#"+containerIDString).append("g")
            .attr("class", "label_group")
            .attr("transform", "translate(" + pieLeftTranslate + "," + pieTopTranslate + ")");


        //DRAW LABELS WITH PERCENTAGE VALUES
        //*
        var enteringLabels = label_group.selectAll("g.label")
            .data(pied_data)
            .enter();
        var labelGroups = enteringLabels.append("g")
            .attr("class", "label")
            .attr("id", function(d){

                var labelId = label_ids[d.data.primaryName];
                return labelId;
            });

        labelGroups.append("circle").attr({
            x: 0,
            y: 0,
            r: 2,
            fill: "#333",
            transform: function (d) {
                centroid = arc.centroid(d);
                return "translate(" + arc.centroid(d) + ")";
            },
            'class': "label-circle"
        });

        cDim = {
            height: graphWidth,
            width: graphWidth,
            labelRadius: graphWidth/4
        };

        //var textLines = labelGroups.append("line").attr({
        //    x1: function (d, i) {
        //        return arc.centroid(d)[0];
        //    },
        //    y1: function (d, i) {
        //        return arc.centroid(d)[1];
        //    },
        //    x2: function (d, i) {
        //        centroid = arc.centroid(d);
        //        midAngle = Math.atan2(centroid[1], centroid[0]);
        //        x = Math.cos(midAngle) * cDim.labelRadius;
        //        return x;
        //    },
        //    y2: function (d, i) {
        //        centroid = arc.centroid(d);
        //        midAngle = Math.atan2(centroid[1], centroid[0]);
        //        y = Math.sin(midAngle) * cDim.labelRadius;
        //        return y;
        //    },
        //    'class': "label-line"
        //});

        //var textLabels = labelGroups.append("text").attr({
        //    x: function (d, i) {
        //        centroid = arc.centroid(d);
        //        midAngle = Math.atan2(centroid[1], centroid[0]);
        //        x = Math.cos(midAngle) * cDim.labelRadius;
        //        sign = (x > 0) ? 1 : -1;
        //        labelX = x + (5 * sign);
        //        return labelX;
        //    },
        //    y: function (d, i) {
        //        centroid = arc.centroid(d);
        //        midAngle = Math.atan2(centroid[1], centroid[0]);
        //        y = Math.sin(midAngle) * cDim.labelRadius;
        //        return y;
        //    },
        //    'data-labelbg': "true",
        //    'text-anchor': function (d, i) {
        //        centroid = arc.centroid(d);
        //        midAngle = Math.atan2(centroid[1], centroid[0]);
        //        x = Math.cos(midAngle) * cDim.labelRadius;
        //        return (x > 0) ? "start" : "end";
        //    },
        //    'class': 'label-text',
        //    'with-space-preserve': true,
        //    'xml:space': 'preserve',
        //    "dominant-baseline": "central"
        //})
        //.text(function (d) {
        //
        //    var label = "  "+mapGraphData(d.data.primaryName, valueMap)+" "+measurementUnits+": "+d.data.primaryValue+" patients  ";
        //    return label;
        //});
        //.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", "preserve");


        /*
        var filter = '<filter x="0" y="0" width="1" height="1" id="highlight">' +
                        '<feFlood flood-color="#aaf"/>' +
                        '<feComposite in="SourceGraphic"/>' +
                    '</filter>';
        */
        var filter = d3.select("#"+containerIDString)
            .append("filter")
            .attr("x", "0")
            .attr("y", "0")
            .attr("width", "1")
            .attr("height", "1")
            .attr("id", "bg-color")

        filter.append("feFlood")
            .attr("flood-color", "#333333");

        filter.append("feComposite")
            .attr("in", "SourceGraphic");

    };

    return publicObject;

})();