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
    if (checkNumbers() === false){
        pass = false;
    }
    if (validateDate() == false) {
        pass = false;
    }

    return pass;
}

function checkRequiredInput() {
    var pass = new Boolean(true);

    if (!$('#firstName').val()) {
        $('#firstName').attr("placeholder", "Required Input");
        $('#firstName').css('border-color','red');
        pass = false;
    }
    if (!$('#lastName').val()) {
        $('#lastName').attr("placeholder", "Required Input");
        $('#lastName').css('border-color','red');
        pass = false;
    }
    if (!$('#age').val()) {
        $('#age').attr("placeholder", "Required Input");
        $('#age').css('border-color','red');
        if($('#month').val() == 0) {
            $('#month').css('border-color','red');
            $('#years').css('border-color','red');
        }
        if($('#day').val() == 0) {
            $('#day').css('border-color','red');
            $('#years').css('border-color','red');
        }
        if($('#year').val() == 0) {
            $('#year').css('border-color','red');
            $('#years').css('border-color','red');
        }
        pass = false;
    }
    if (!$('#city').val()) {
        $('#city').attr("placeholder", "Required Input");
        $('#city').css('border-color','red');
        pass = false;
    }

    return pass;
}

function checkNumbers(){
    var pass = new Boolean(true);


    //Respirations
    if ($.trim($('#respirations').val().length) > 0 && integerCheck($('#respirations').val()) == false){
        $('#respirations').val("");
        $('#respirations').attr("placeholder","Enter a Number");
        $('#respirations').css('border-color','red');
        pass = false;
    }
    //Blood Pressure - Systolic
    if ($.trim($('#bloodPressureSystolic').val().length) > 0 && integerCheck($('#bloodPressureSystolic').val()) == false){
        $('#bloodPressureSystolic').val("");
        $('#bloodPressureSystolic').attr("placeholder","Enter a Number");
        $('#bloodPressureSystolic').css('border-color','red');
        pass = false;
    }
    //Blood Pressure - Diastolic
    if ($.trim($('#bloodPressureDiastolic').val().length) > 0 && integerCheck($('#bloodPressureDiastolic').val()) == false){
        $('#bloodPressureDiastolic').val("");
        $('#bloodPressureDiastolic').attr("placeholder","Enter a Number");
        $('#bloodPressureDiastolic').css('border-color','red');
        pass = false;
    }
    //Heart Rate
    if ($.trim($('#heartRate').val().length) > 0 && integerCheck($('#heartRate').val()) == false){
        $('#heartRate').val("");
        $('#heartRate').attr("placeholder","Enter a Number");
        $('#heartRate').css('border-color','red');
        pass = false;
    }
    //Oxygen
    if ($.trim($('#oxygen').val().length) > 0 && decimalCheck($('#oxygen').val()) == false){
        $('#oxygen').val("");
        $('#oxygen').attr("placeholder","Enter a Number");
        $('#oxygen').css('border-color','red');
        pass = false;
    }
    //Temperature
    if ($.trim($('#temperature').val().length) > 0 && decimalCheck($('#temperature').val()) == false){
        $('#temperature').val("");
        $('#temperature').attr("placeholder","Enter a Number");
        $('#temperature').css('border-color','red');
        pass = false;
    }
    //Weight
    if ($.trim($('#weight').val().length) > 0 && decimalCheck($('#weight').val()) == false){
        $('#weight').val("");
        $('#weight').attr("placeholder","Enter a Number");
        $('#weight').css('border-color','red');
        pass = false;
    }
    //Height - Feet
    if ($.trim($('#heightFeet').val().length) > 0 && integerCheck($('#heightFeet').val()) == false){
        $('#heightFeet').val("");
        $('#heightFeet').attr("placeholder","Enter a Number");
        $('#heightFeet').css('border-color','red');
        pass = false;
    }
    //Height - Inches
    if ($.trim($('#heightInches').val().length) > 0 && integerCheck($('#heightInches').val()) == false){
        $('#heightInches').val("");
        $('#heightInches').attr("placeholder","Enter a Number");
        $('#heightInches').css('border-color','red');
        pass = false;
    }
    //Height - Inches less than 12
    if ($.trim($('#heightInches').val()) >= 12){
        $('#heightInches').val("");
        $('#heightInches').attr("placeholder","Max value: 11");
        $('#heightInches').css('border-color','red');
        pass = false;
    }
    //Glucose
    if ($.trim($('#glucose').val().length) > 0 && integerCheck($('#glucose').val()) == false){
        $('#glucose').val("");
        $('#glucose').attr("placeholder","Enter a Number");
        $('#glucose').css('border-color','red');
        pass = false;
    }
    //Pregnant - Weeks
    if ($.trim($('#weeksPregnant').val().length) > 0 && integerCheck($('#weeksPregnant').val()) == false){
        $('#weeksPregnant').val("");
        $('#weeksPregnant').attr("placeholder","Enter a Number");
        $('#weeksPregnant').css('border-color','red');
        pass = false;
    }
    //Pregnant - Weeks zero or greater
    if ($.trim($('#weeksPregnant').val()) < 0){
        $('#weeksPregnant').val("");
        $('#weeksPregnant').attr("placeholder","Min value: 0");
        $('#weeksPregnant').css('border-color','red');
        pass = false;
    }
    //Age - Years
    if ($.trim($('#years').val().length) > 0 && integerCheck($('#years').val()) == false){
        $('#years').val("");
        $('#years').attr("placeholder","Enter Correct Number of Years");
        $('#years').css('border-color','red');
        pass = false;
    }


    return pass;
}

/*
 * check if age is a date
 * adopted from the webs!!!
 */

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
    var strYear=dtStr.substring(0,pos1); //changed from strMonth
    var strMonth=dtStr.substring(pos1+1,pos2); //changed from strDay
    var strDay=dtStr.substring(pos2+1); //changed from strYear
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
    if (dtStr.indexOf(dtCh,pos2+1)!=-1 || integerCheck(stripCharsInBag(dtStr, dtCh))==false){
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
        $('#years').css('border-color','red');
        $('#years').attr('placeholder','Input valid age or birth date');
        return false;
    }
    return true;
}
