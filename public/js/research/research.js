


$(document).ready(function(){
    console.log(generateExpressionString(getExpressionList()));
    $('#addExpressionBtn').click(function(){
        addExpression();
    });
});

/*
Create a new expression object based on user input
And add it to the ordered expression list that exists
in the DOM
 */
function addExpression(){
    var numberOfExpressions = $('.expressions > li').size();
}



/*
Generate expression string from a list of expression objects
 */
function generateExpressionString(expressionList){
    var expressionString = "";
    expressionList.forEach(function(expression){
        expressionString = expressionString + " " +
            expression.Property + " " +
            expression.Operator + " " +
            expression.Val + " " +
            expression.Bool
    });
    return expressionString;
}

/*
Get a list of available expression objects currently
chosen by the user
 */
function getExpressionList(){
    var expressionStack = [];
    var numberOfExpressions = $('.expressions > li').size();
    for(var expressionNumber = 1; expressionNumber <= numberOfExpressions; expressionNumber++){
        expressionStack.push(new expression(expressionNumber));
    }
    return expressionStack;
}

/*
 Defines an expression object. Currently contains the
 boolean value, but probably shouldn't.
*/
function expression(expressionNumber){
    this.Property = $('#expression' + expressionNumber).find('.property').text().trim();
    this.Operator = $('#expression' + expressionNumber).find('.operator').text().trim();
    this.Val = $('#expression' + expressionNumber).find('.value').text().trim();
    this.Bool = $('#expression' + expressionNumber).find('.boolean').text().trim();
}
