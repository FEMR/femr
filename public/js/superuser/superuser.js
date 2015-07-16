

$(document).ready(function(){
    $("[name='newTripCity']").change(function(){

        $("[name='newTripCountry']").val($(this).find(':selected').attr('country-name'));
    });

    $('.currentButton').click(function(){

        $.ajax({
            type: "POST",
            url: "/superuser/trips/" + $(this).parent().find('input').val(),
            success: function(){
                window.location.href = 'trips'
            }
        });

    });
});