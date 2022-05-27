var canvasWidth = 620;
var canvasHeight = 500;

// first we need Konva core things: stage and layer
var stage = new Konva.Stage({
    container: 'diagramContainer',
    width: canvasWidth,
    height: canvasHeight,
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

let imgW = 500;
let imgH = 457;
var imageObj = new Image();
imageObj.onload = function () {
    var background = new Konva.Image({
        strokeEnabled: false,
        x: ( stage.getWidth()-((canvasHeight * imgW)/imgH) ) / 2,
        y: 0,
        image: imageObj,
        height: canvasHeight,
        width: (canvasHeight * imgW)/imgH, //numbers here are .img dimensions, to constrain ratio
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
let tagEditModeActive = false;

function editTagActivate(){
    tagEditModeActive = true;
}

function editTagDeactivate(){
    tagEditModeActive = false;
}

stage.on('mousedown touchstart', function (e) {
    if(tagEditModeActive){
        addTagLineAtPosition()
    } else {
        isPaint = true;
        var pos = stage.getPointerPosition();
        lastLine = new Konva.Line({
            stroke: "rgb(26,52,167)",
            strokeWidth: 5,
            globalCompositeOperation:
                mode === 'brush' ? 'source-over' : 'destination-out',
            // round cap for smoother lines
            lineCap: 'round',
            // add point twice, so we have some drawings even on a simple click
            points: [pos.x, pos.y, pos.x, pos.y],
        });

        layer.add(lastLine);
    }
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

function addTagLineAtPosition(){
    let lineShape = tagLocationSet();
    let pos = stage.getPointerPosition();
    const closestPt = closestPoint(lineShape.points(), {x: pos.x, y: pos.y});

    layer.add(new Konva.Line({
        stroke: '#000000',
        strokeWidth: 1.5,
        points: [pos.x, pos.y, closestPt.x, closestPt.y],
    }) );
}

function closestPoint(pathNode, point) {
    let best = {x: 0, y: 0}
    let shortestDist = 800.0;

    for(let i = 0; i < pathNode.length; i+=2){
        let a = point.x - pathNode[i];
        let b = point.y - pathNode[i+1];
        let c = Math.sqrt( a*a + b*b );

        if (c < shortestDist){
            sessionStorage.setItem("closest", JSON.stringify(c))
            best.x = pathNode[i];
            best.y = pathNode[i+1];
            shortestDist = c;
        }
    }
    return {x: best.x, y: best.y};
}

function tagLocationSet(){
    return new Konva.Line({
        stroke: '#000000',
        strokeWidth: 1.5,
        // add point twice, so we have some drawings even on a simple click
        points: [ 102, 458, 102, 456, 101, 454, 101, 451, 99, 449, 99, 446, 98, 445, 98, 443, 97, 442, 96, 440, 96,
            438, 95, 436, 94, 434, 94, 432, 93, 431, 93, 430, 93, 429, 92, 428, 92, 427, 92, 425, 92, 423, 91, 421, 91,
            418, 91, 415, 91, 412, 91, 409, 91, 407, 91, 404, 91, 402, 90, 399, 90, 397, 89, 394, 89, 391, 89, 389, 88,
            386, 88, 382, 88, 379, 88, 376, 88, 374, 88, 371, 87, 369, 87, 366, 87, 363, 87, 361, 86, 360, 86, 358, 86,
            356, 86, 354, 86, 351, 86, 348, 86, 344, 86, 341, 86, 338, 86, 337, 86, 336, 86, 334, 86, 332, 86, 330, 86,
            327, 86, 324, 86, 322, 85, 320, 84, 318, 83, 317, 82, 315, 80, 315, 79, 314, 78, 312, 77, 311, 76, 310, 75,
            309, 73, 306, 72, 303, 70, 299, 69, 295, 67, 293, 66, 291, 65, 289, 64, 287, 63, 284, 62, 280, 61, 277, 61,
            274, 60, 271, 59, 268, 58, 266, 58, 263, 57, 260, 57, 257, 57, 255, 57, 253, 57, 251, 57, 249, 57, 247, 57,
            245, 57, 242, 57, 240, 58, 238, 58, 236, 59, 235, 59, 233, 59, 231, 60, 230, 61, 228, 62, 225, 63, 223, 64,
            221, 65, 219, 66, 216, 68, 214, 69, 212, 70, 210, 71, 208, 72, 207, 73, 205, 74, 203, 75, 201, 76, 199, 77,
            195, 78, 192, 80, 187, 81, 183, 82, 178, 83, 173, 84, 168, 85, 164, 85, 161, 85, 157, 86, 152, 86, 149, 86,
            146, 86, 144, 86, 141, 86, 139, 86, 137, 86, 135, 86, 133, 87, 131, 87, 128, 87, 125, 87, 122, 88, 120, 88,
            118, 88, 115, 88, 112, 89, 107, 90, 104, 91, 101, 91, 99, 92, 97, 92, 95, 93, 93, 94, 90, 94, 89, 95, 87,
            95, 85, 96, 83, 96, 81, 97, 80, 98, 78, 98, 77, 99, 75, 99, 73, 100, 71, 100, 71, 101, 70, 101, 68, 102, 67,
            102, 66, 103, 64, 104, 62, 106, 58, 108, 55, 110, 53, 112, 51, 114, 49, 116, 46, 118, 44, 119, 41, 121, 39,
            122, 38, 123, 37, 124, 35, 126, 34, 128, 33, 130, 31, 132, 30, 134, 29, 136, 28, 137, 27, 139, 26, 141, 25,
            143, 24, 145, 24, 147, 23, 150, 22, 152, 21, 154, 20, 157, 19, 161, 18, 163, 18, 165, 17, 168, 17, 170, 17,
            171, 16, 172, 16, 173, 16, 174, 16, 176, 16, 177, 16, 179, 16, 182, 16, 185, 16, 188, 16, 191, 16, 194, 16,
            197, 16, 198, 16, 200, 16, 201, 16, 203, 17, 205, 17, 207, 18, 210, 19, 212, 19, 214, 20, 216, 20, 217, 21,
            218, 21, 219, 22, 220, 22, 220, 22, 221, 23, 222, 23, 223, 23, 223, 24, 225, 24, 226, 25, 228, 26, 230, 27,
            231, 27, 231, 28, 232, 28, 233, 29, 234, 29, 235, 30, 235, 31, 236, 32, 237, 33, 237, 34, 238, 35, 239, 36,
            240, 38, 241, 39, 242, 40, 243, 41, 244, 43, 246, 44, 247, 45, 248, 47, 249, 47, 250, 48, 250, 50, 251, 53,
            252, 56, 253, 59, 253, 61, 254, 63, 254, 64, 254, 66, 254, 67, 255, 69, 255, 71, 255, 72, 255, 74, 256, 75,
            256, 77, 257, 79, 257, 80, 258, 82, 258, 83, 258, 84, 259, 86, 259, 88, 260, 89, 261, 90, 261, 91, 261, 92,
            261, 92, 261, 93, 262, 94, 262, 95, 262, 96, 263, 97, 263, 98, 263, 99, 264, 100, 264, 101, 264, 102, 265,
            103, 265, 103, 266, 104, 266, 105, 266, 105, 267, 106, 267, 107, 268, 107, 268, 108, 269, 109, 269, 110,
            270, 111, 270, 112, 270, 114, 271, 116, 272, 118, 272, 119, 273, 120, 273, 121, 274, 122, 274, 123, 275,
            124, 275, 126, 276, 129, 277, 132, 278, 134, 278, 135, 279, 136, 279, 137, 279, 138, 280, 139, 280, 140,
            280, 142, 281, 144, 282, 147, 283, 151, 283, 155, 284, 160, 285, 165, 286, 168, 287, 170, 287, 171, 287,
            173, 287, 174, 288, 176, 288, 179, 288, 183, 289, 185, 289, 187, 289, 188, 289, 190, 289, 192, 289, 198,
            288, 205, 288, 209, 288, 212, 288, 213, 288, 214, 288, 215, 288, 215, 288, 216, 288, 217, 289, 218, 290,
            219, 290, 221, 291, 223, 292, 225, 293, 227, 293, 229, 294, 231, 295, 233, 295, 236, 296, 237, 297, 239,
            298, 241, 299, 243, 299, 245, 300, 247, 300, 249, 301, 251, 302, 254, 302, 257, 302, 259, 302, 262, 302,
            265, 302, 267, 302, 270, 302, 274, 302, 277, 302, 279, 302, 282, 302, 285, 302, 288, 302, 290, 301, 292,
            301, 294, 300, 297, 299, 301, 298, 303, 297, 304, 296, 306, 295, 308, 294, 309, 294, 311, 293, 311, 293,
            312, 292, 313, 292, 314, 292, 315, 292, 316, 291, 317, 291, 318, 291, 319, 290, 320, 290, 321, 290, 323,
            290, 324, 289, 326, 289, 327, 289, 328, 289, 329, 289, 330, 288, 332, 288, 333, 287, 334, 287, 336, 287,
            338, 286, 339, 286, 341, 286, 343, 286, 345, 286, 346, 286, 349, 286, 352, 286, 355, 286, 357, 286, 361,
            286, 367, 285, 371, 285, 373, 285, 374, 285, 376, 285, 378, 284, 379, 284, 381, 284, 384, 284, 388, 284,
            394, 284, 398, 284, 402, 284, 404, 284, 406, 284, 408, 284, 410, 284, 411, 284, 413, 284, 415, 284, 417,
            284, 419, 284, 421, 283, 423, 283, 424, 283, 425, 283, 428, 283, 430, 282, 432, 282, 432, 282, 433, 282,
            434, 281, 435, 280, 437, 279, 438, 278, 438, 278, 440, 277, 441, 277, 442, 276, 443, 275, 444, 275, 445,
            274, 446, 274, 447, 274, 447, 273, 448, 273, 448, 272, 449, 271, 450, 270, 451, 270, 452, 269, 452, 268,
            453, 268, 454, 268, 455, 267, 456, 266, 457, 265, 458, 263, 460, 261, 462, 259, 464, 257, 464, 254, 464,
            249, 465, 247, 466, 245, 467, 243, 468, 242, 468, 240, 469, 239, 469, 236, 470, 234, 471, 231, 472, 229,
            473, 227, 473, 225, 473, 224, 473, 221, 473, 220, 473, 218, 473, 216, 473, 214, 473, 212, 473, 210, 473,
            208, 473, 206, 473, 204, 473, 202, 473, 201, 473, 199, 473, 198, 474, 196, 474, 194, 474, 192, 474, 191,
            474, 189, 474, 187, 474, 184, 475, 182, 475, 180, 475, 179, 475, 177, 475, 176, 475, 173, 475, 171, 475,
            167, 475, 161, 475, 158, 475, 154, 475, 151, 476, 150, 476, 149, 476, 147, 475, 144, 475, 141, 475, 139,
            475, 138, 475, 137, 475, 135, 475, 134, 475, 133, 475, 132, 475, 130, 475, 128, 474, 126, 474, 125, 474,
            124, 473, 123, 473, 122, 473, 120, 473, 119, 472, 118, 472, 117, 471, 116, 471, 114, 470, 113, 470, 113,
            470, 112, 470, 111, 470, 110, 470, 109, 469, 109, 469, 108, 468, 107, 468, 106, 467, 105, 466, 105, 466,
            104, 465, 103, 464, 103, 464, 103, 463, 102, 462, 102, 462, 102, 461, 101, 460, 101, 460, 101, 460, 100,
            459, 100, 459, 100, 458, 100, 458, 100, 457, 100, 456, 100]
    }) ;
}