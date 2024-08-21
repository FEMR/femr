
var mySelect = document.getElementById('languageSelect');
mySelect.onchange = (event) => {
    var languageCode = event.target.value;
    console.log(languageCode);
    $.ajax({
        type: 'post',
        url: '/settings/update/'+languageCode,
        success: function() {
            location.reload();
        },
        failure: function(){
            console.error('Failed to update user language code');
        }
    });
}