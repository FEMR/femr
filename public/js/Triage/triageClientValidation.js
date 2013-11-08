//constant date characters
var dtCh= "-";
var minYear=1900;
var maxYear=2100;

$('#triageSubmitBtn').click(function () {
    return validate();
});


function validate(){
    var pass = new Boolean(true);

    if (checkRequiredInput() === false) {
        pass = false;
    }
    else if (checkNumbers() === false){
        pass = false;
    }

    else if (validateDate() == false) {
        pass = false;
    }

    return pass;
}

function checkRequiredInput() {
    var pass = new Boolean(true);

    if (!$('#firstName').val()) {
        $('#firstName').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#lastName').val()) {
        $('#lastName').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#age').val()) {
        $('#age').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#city').val()) {
        $('#city').attr("placeholder", "Required Input");
        pass = false;
    }

    return pass;
}

function checkNumbers(){
    var pass = new Boolean(true);

    //Blood Pressure - Systolic
    if ($.trim($('#respirations').val().length) > 0 && $.isNumeric($('#respirations').val()) == false){
        $('#respirations').val("");
        $('#respirations').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Blood Pressure - Diasolic
    if ($.trim($('#bloodPressureSystolic').val().length) > 0 && $.isNumeric($('#bloodPressureSystolic').val()) == false){
        $('#bloodPressureSystolic').val("");
        $('#bloodPressureSystolic').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Respirations
    if ($.trim($('#bloodPressureDiastolic').val().length) > 0 && $.isNumeric($('#bloodPressureDiastolic').val()) == false){
        $('#bloodPressureDiastolic').val("");
        $('#bloodPressureDiastolic').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Heart Rate
    if ($.trim($('#heartRate').val().length) > 0 && $.isNumeric($('#heartRate').val()) == false){
        $('#heartRate').val("");
        $('#heartRate').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Oxygen
    if ($.trim($('#oxygen').val().length) > 0 && $.isNumeric($('#oxygen').val()) == false){
        $('#oxygen').val("");
        $('#oxygen').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Temperature
    if ($.trim($('#temperature').val().length) > 0 && $.isNumeric($('#temperature').val()) == false){
        $('#temperature').val("");
        $('#temperature').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Weight
    if ($.trim($('#weight').val().length) > 0 && $.isNumeric($('#weight').val()) == false){
        $('#weight').val("");
        $('#weight').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Height - Feet
    if ($.trim($('#heightFeet').val().length) > 0 && $.isNumeric($('#heightFeet').val()) == false){
        $('#heightFeet').val("");
        $('#heightFeet').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Height - Inches
    if ($.trim($('#heightInches').val().length) > 0 && $.isNumeric($('#heightInches').val()) == false){
        $('#heightInches').val("");
        $('#heightInches').attr("placeholder","Enter a Number");
        pass = false;
    }
    return pass;
}

// check if age is a date
// adopted from the webs!!!

function isInteger(s){
    var i;
    for (i = 0; i < s.length; i++){
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    return true;
}

function stripCharsInBag(s, bag){
    var i;
    var returnString = "";
    for (i = 0; i < s.length; i++){
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
    for (var i = 1; i <= n; i++) {
        this[i] = 31
        if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
        if (i==2) {this[i] = 29}
    }
    return this
}

function isDate(dtStr){
    var daysInMonth = DaysArray(12);
    var pos1=dtStr.indexOf(dtCh);
    var pos2=dtStr.indexOf(dtCh,pos1+1);
    var strYear=dtStr.substring(0,pos1);  //changed from strMonth
    var strMonth=dtStr.substring(pos1+1,pos2); //changed from strDay
    var strDay=dtStr.substring(pos2+1);    //changed from strYear
    strYr=strYear;
    if (strDay.charAt(0)=="0" && strDay.length>1) {
        strDay=strDay.substring(1);
    }
    if (strMonth.charAt(0)=="0" && strMonth.length>1) {
        strMonth=strMonth.substring(1);
    }
    for (var i = 1; i <= 3; i++) {
        if (strYr.charAt(0)=="0" && strYr.length>1){
            strYr=strYr.substring(1);
        }
    }
    month=parseInt(strMonth);
    day=parseInt(strDay);
    year=parseInt(strYr);
    if (pos1==-1 || pos2==-1){
        //alert("The date format should be : mm-dd-yyyy");
        return false;
    }
    if (strMonth.length<1 || month<1 || month>12){
        //alert("Please enter a valid month");
        return false;
    }
    if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
        //alert("Please enter a valid day");
        return false;
    }
    if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
        //alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
        return false;
    }
    if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
        //alert("Please enter a valid date");
        return false;
    }
    return true;
}

function validateDate(){
    var dt= $('#age').val();
    if (isDate(dt.toString())==false){
        $('#age').val("");
        $('#years').val("");
        $('#age').css('border-color','red');
        $('#age').attr('placeholder','Enter correct date format: yyyy-mm-dd');
        return false;
    }
    return true;
}