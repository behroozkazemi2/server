var newUnit = function(){
    var form = $('#add_category_submit_form');
    console.log(form);
    var error = $('.alert-danger', form);
    var success = $('.alert-success', form);
    toastr.options = {"positionClass": "toast-bottom-left"};

    validations = function() {
        $.validator.addMethod("regex", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "");

        form.validate({
            doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                'name': {required: true}


            },
            messages: { // custom messages for radio buttons and checkboxes
                'name': {required:    "نام را وارد کنید."}

            },

            errorPlacement: function (error, element) { // render error placement for each input type
                element.parent().find('.help-block').remove();
                error.insertAfter(element); // for other inputs, just perform default behavior
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success.hide();
                error.show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass('has-success').addClass('has-error').find('.form-control').addClass('edited'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .addClass('valid') // mark the current input as valid and display OK icon
                    .closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                label.remove();
            },

            submitHandler: function (form) {
                success.show();
                error.hide();

                var formData = new FormData( $('#add_category_submit_form')[0]);

                $.ajax ({
                    url : '/admin/product/setting/unit/save',
                    method :"POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success :function(response) {
                        if (response.result) {
                            /*toastr.success("ثبت با موفقیت انجام شد.");
                            $('#newModal').modal('toggle');
                            reloadUnitList();*/
                            Swal({
                                title: 'انجام شد',
                                type: 'success',
                                showCancelButton: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: 'اکی'
                            }).then((result) => {
                                window.location.replace("/admin/product/setting");
                            });
                        } else {
                            toastr['error'](response.payload);
                        }
                    },
                    error: function () {
                        toastr.error('عملیات با خطا مواجه شد.');
                    }
                });
            }
        });

    };


    return{
        init :function() {
            validations();
            convertPersianNumber($('.checkNumber'));

        }
    };
}();
