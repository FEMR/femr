/**
 * Created by 212433740 on 11/3/2015.
 */

    function validation()
    {
        var a = document.form.pass1.value;
        if(a=="")
        {
            alert("Please Enter Your Password");
            document.form.pass1.focus();
            return false;
        }
        if ((a.length < 4) || (a.length > 8))
        {
            alert("Your Password must be 4 to 8 Character");
            document.form.pass1.select();
            return false;
        }
    }
