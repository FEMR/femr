$(document).ready(function () {

    $(function() {
        $('#paginator').pagination({
            pages: 30,
            cssStyle: 'light-theme',
            onPageClick: function(pageNumber){
                test(pageNumber);
            }
        });
    });

    $('#addExpressionBtn').click(function () {
        addExpression();
        //hide all options
        $('.selectGroup').addClass('hidden');
        //clear options
        $('.selectGroup select').prop('selectedIndex',0);
        $('.selectGroup input').val('');
        //show option to add boolean choice
        $('#expressionBool').parent().removeClass('hidden');
        $('#addBoolBtn').parent().removeClass('hidden');
    });
    $('#addBoolBtn').click(function () {
        addBool();
        $('.selectGroup').addClass('hidden');
        $('.selectGroup select').prop('selectedIndex',0);
        $('.selectGroup input').val('');
        $('#expressionProperty').parent().removeClass('hidden');
        $('#expressionOperator').parent().removeClass('hidden');
        $('#expressionValue').parent().removeClass('hidden');
        $('#addExpressionBtn').parent().removeClass('hidden');

    });
    $('#submitBtn').click(function(){
        $('#submitString').val(generateExpressionString(getCurrentExpressionList()).trim());
        return true;
    })
});

function test(pageNumber)
{

    var page="#page-"+pageNumber;
    $('.selection').hide();
    $(page).show();

}




function addBool() {
    var expressions = getCurrentExpressionList();
    expressions[expressions.length - 1].addBool();
}


/*
 Generate expression string from a list of expression objects
 */
function generateExpressionString(expressionList) {
    var expressionString = "";
    expressionList.forEach(function (expression) {
        expressionString = expressionString + " " +
            expression.Property + " " +
            expression.Operator + " " +
            expression.Val + " " +
            expression.Bool
    });
    return expressionString;
}

/*
 Create a new expression object based on user input
 And add it to the ordered expression list that exists
 in the DOM
 */
function addExpression() {
    var numberOfExpressions = $('.expressions > li').size();
    var args = {};
    args.expressionProperty = $('#expressionProperty').val().trim();
    args.expressionOperator = $('#expressionOperator').val().trim();
    var tempValue = $('#expressionValue').val().trim();
    tempValue = tempValue.replace(/ /g,"_");  //replaces the spaces in the value with underscores
    args.expressionValue = tempValue; //$('#expressionValue').val().trim();
    args.expressionBool = $('#expressionBool').val().trim();
    var newExpression = new Expression(numberOfExpressions + 1, args);
    newExpression.addToDOM();
}

/*
 Get a list of available expression objects currently
 chosen by the user
 */
function getCurrentExpressionList() {
    var expressionStack = [];
    var numberOfExpressions = $('.expressions > li').size();
    for (var expressionNumber = 1; expressionNumber <= numberOfExpressions; expressionNumber++) {
        expressionStack.push(new Expression(expressionNumber, null));
    }
    return expressionStack;
}
/*
 Defines an expression object. Currently contains the
 boolean value, but probably shouldn't.
 */
function Expression(expressionNumber, args) {
    if (args === null || typeof(args) === 'undefined') {
        var property = $('#expression' + expressionNumber).find('.property').text().trim();
        var operator = $('#expression' + expressionNumber).find('.operator').text().trim();
        var val = $('#expression' + expressionNumber).find('.value').text().trim();
        var bool = $('#expression' + expressionNumber).find('.boolean').text().trim();
    } else {
        var property = args.expressionProperty;
        var operator = args.expressionOperator;
        var val = args.expressionValue;
        var bool = args.expressionBool;
    }
    this.Property = property;
    this.Operator = operator;
    this.Val = val;
    this.Bool = bool;
    this.Number = expressionNumber;
}
Expression.prototype = {

    addToDOM: function () {
        var numberOfExpressions = $('.expressions > li').size();
        $('.expressions').append('<li id="expression' + (numberOfExpressions + 1) + '">' +
            '<ol>' +
            '<li class="property">' + this.Property + '</li>' +
            '<li class="operator">' + this.Operator + '</li>' +
            '<li class="value">' + this.Val + '</li>' +
            '<li class="boolean">' + this.Bool + '</li>' +
            '</ol>' +
            '</li> ');
    },
    addBool: function () {
        $('#expression' + this.Number).find('.boolean').text($('#expressionBool').val().trim());
    }
}

