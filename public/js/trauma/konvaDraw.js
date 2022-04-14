var width = 500;
var height = 460;

// first we need Konva core things: stage and layer
var stage = new Konva.Stage({
    container: 'container',
    width: width,
    height: height,
});

// Background Image
var backgroundLayer = new Konva.Layer();
stage.add(backgroundLayer);

var maleImg = '/assets/img/male_bodychart.png';
var femaleImg = '/assets/img/female_bodychart.png';
var pregnantImg = '/assets/img/female_pregnant_bodychart.png';

function changeBackground(selection){
    let final = maleImg
    if (selection === "maleBtn") {
        final = maleImg
    } else if (selection === "weeksPregnant") {
        final = pregnantImg
    } else
        final =  femaleImg
    imageObj.src = final
}

var imageObj = new Image();
imageObj.onload = function () {
    var background = new Konva.Image({
        strokeEnabled: false,
        x: 0,
        y: 0,
        image: imageObj,
        width: 500,
        height: 460,
    });

    // add the shape to the layer
    backgroundLayer.add(background);
    backgroundLayer.draw();
};
changeBackground()

// Drawing functionality

var layer = new Konva.Layer();
stage.add(layer);

var isPaint = false;
var mode = 'brush';
var lastLine;

stage.on('mousedown touchstart', function (e) {
    isPaint = true;
    var pos = stage.getPointerPosition();
    lastLine = new Konva.Line({
        stroke: document.getElementById("brushColor").value,
        strokeWidth: 5,
        globalCompositeOperation:
            mode === 'brush' ? 'source-over' : 'destination-out',
        // round cap for smoother lines
        lineCap: 'round',
        // add point twice, so we have some drawings even on a simple click
        points: [pos.x, pos.y, pos.x, pos.y],
    });
    layer.add(lastLine);
});

stage.on('mouseup touchend', function () {
    isPaint = false;
});

// and core function - drawing
stage.on('mousemove touchmove', function (e) {
    if (!isPaint) {
        return;
    }

    // prevent scrolling on touch devices
    e.evt.preventDefault();

    const pos = stage.getPointerPosition();
    var newPoints = lastLine.points().concat([pos.x, pos.y]);
    lastLine.points(newPoints);
});

document.querySelectorAll('input[name=drawCheckbox]').forEach(item => {
    item.addEventListener('click', event => {
        if (item.checked === true) mode = item.value
    })
})