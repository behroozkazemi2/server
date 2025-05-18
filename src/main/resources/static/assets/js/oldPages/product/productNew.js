var productNew = function () {

    var form = $('#newProduct');
    var error = $('.alert-danger', form);
    var success = $('.alert-success', form);
    form.on('keyup keypress', function(e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });

    $('.has-select2').select2({
        placeholder: "انتخاب کنید",
        allowClear: true
    });

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
                'name': {
                    required: true
                },
                'productUnitIds': {
                    required: true
                }

            },
            messages: { // custom messages for radio buttons and checkboxes
                'name': {
                    required:   "نام محصول را وارد نمایید.",
                },
                'productUnitIds': {
                    required:   "واحد فروش محصول را انتخاب نمایید."
                }

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
                var formData = new FormData( $('#newProduct')[0]);


                $.ajax ({
                    url : '/admin/product/save',
                    method :"POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    beforeSend: function() {
                        $('.loading').removeClass('hidden');
                    },
                    success :function(response) {

                        if (response.result) {

                            toastr.success("ثبت با موفقیت انجام شد.");

                            $('#newModal').modal('toggle');
                            reloadList();
                            // Swal({
                            //     title: 'انجام شد',
                            //     type: 'success',
                            //     showCancelButton: false,
                            //     confirmButtonColor: '#3085d6',
                            //     confirmButtonText: 'اکی'
                            // }).then((result) => {
                            //     window.location.replace("/admin/product");
                            // });
                            // lineGrid.ajax.reload();

                        } else {
                            toastr['error'](response.payload);
                        }

                        $('.loading').addClass('hidden');
                    },
                    error: function () {
                        toastr['error']("خطا در ارتباط با سرور.");
                        $('.loading').addClass('hidden');
                    }
                });

                //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
            }
        });

    };

    return {
        init: function () {
            validations();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();