var Login = function () {

    var handleLogin = function() {
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "نام کاربری الزامی است."
                },
                password: {
                    required: "رمز عبور الزامی است."
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function (error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function (form) {
                form.submit();
            }
        });

        $('.login-form input').keypress(function (e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit();
                }
                return false;
            }
        });

    };
    toPersian = function() {

    };
    beforSubmit = function(){
        $('.login-form').submit(function() {
            var  persianVal = $("input[name='username']").val();
            var enNumber = persianJs(persianVal).persianNumber().toString();
            $("input[name='username']").val(enNumber);
            return true;
        });
    };
    var reloadCaptcha = function () {
        $(document).on('click','.btn-reloadCaptcha',function(){
            d = new Date();
            var  src= $(".captcha").attr("src");
            $(".captcha").attr("src", src+"?"+d.getTime());


        });
    };

    return {
        //main function to initiate the module
        init: function () {
            handleLogin();
            reloadCaptcha();
            toPersian();
            beforSubmit();
        }

    };

}();

