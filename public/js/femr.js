$(document).ready(function () {
//NO DUPLICATE CODe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    $('#id').change(function () {
        var idString = $('#id').val();
        var intRegex = /^\d+$/;
        if (intRegex.test(idString)) {
            $('#id').css('border', '');
        }
        else {
            $('#id').val('');
            $('#id').css('border-color', 'red');
            $('#id').attr('placeholder', 'Invalid Id');
        }
    });

    $('.idSearch').click(function (event) {
        var idString = $('#id').val();
        var intRegex = /^\d+$/;
        if (intRegex.test(idString)) {
            $('#id').css('border', '');
        }
        else {
            $('#id').val('');
            $('#id').css('border-color', 'red');
            $('#id').attr('placeholder', 'Invalid Id');
            return false;
        }
    });
});
