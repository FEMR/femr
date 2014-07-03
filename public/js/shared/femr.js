$(document).ready(function () {
  var $id = $('#id');
  $id.change(function () {
    var idString = $id.val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
      $('#id').css('border', '');
    } else {
      $id.val('');
      $id.css('border-color', 'red');
      $id.attr('placeholder', 'Invalid Id');
    }
  });

  $('.idSearch').click(function () {
    var idString = $id.val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
      $('#id').css('border', '');
    } else {
      $id.val('');
      $id.css('border-color', 'red');
      $id.attr('placeholder', 'Invalid Id');
      return false;
    }
  });
});

/*
 * must be in one of the following formats,  excludes 1.
 * 111.11
 * 1
 * 0.1
 * 1.1
 */
function decimalCheck(wonkyDeci) {
  var regexDecimal = /^\d+(\.\d{1,2})?$/;
  return regexDecimal.test(wonkyDeci);
}
/*
 * positive integer numbers
 * excludes 1.
 */
function integerCheck(wonkyInt) {
  var regexInt = /^\d+$/;
  return regexInt.test(wonkyInt);
}

/*
 * Checks if value of input is equal to NaN
 */
function randomString(strVal) {
  return isNaN(strVal);
}

