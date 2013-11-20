$(document).ready(function () {
  var $searchButton = $('#searchDivButton');
  var $searchDiv = $('#searchContainer');

  $searchButton.on('click', function () {
    if ($searchDiv.is(':visible') === true) {
      $searchDiv.slideUp();
    } else {
      $searchDiv.slideDown();
    }
  });
});
