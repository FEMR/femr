
var mySelect = document.getElementById('languageSelect');
mySelect.onchange = (event) => {
    var languageCode = event.target.value;
    console.log(languageCode);
    localStorage.setItem('languageCode', languageCode);
    document.cookie = 'languageCode=' + encodeURIComponent(languageCode) + '; path=/; max-age=31536000';
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
