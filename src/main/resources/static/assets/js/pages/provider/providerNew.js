var providerNew = function () {

    var form = $('#newProvider');
    var error = $('.alert-danger', form);
    var success = $('.alert-success', form);
    form.on('keyup keypress', function(e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
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
                'shortDescription': {
                    required: true
                },
                'categories': {
                    required: true
                },
                'address': {
                    required: true
                },
                'phone[0]': {
                    required: true,
                    digits: true
                },
                'phone[1]': {
                    required: true,
                    digits: true
                }
            },
            messages: { // custom messages for radio buttons and checkboxes
                'name': {
                    required:   "نام را وارد نمایید.",
                },
                'shortDescription': {
                    required:   "توضیحات را وارد نمایید."
                },
                'categories': {
                    required:   "دسته‌بندی را انتخاب نمایید."
                },
                'address': {
                    required:   "آدرس را وارد نمایید."
                },
                'phone[0]': {
                    required:   "شماره تماس را وارد نمایید.",
                    digits:   "فقط از اعداد نمایید."
                },
                'phone[1]': {
                    required:   "شماره تماس را وارد نمایید.",
                    digits:   "فقط از اعداد نمایید."
                },
                'logo': {
                    required:   "تصویر را وارد نمایید."
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
                var formData = new FormData( $('#newProvider')[0]);


                var inputFiles = $('input[id^="images-"]');

                $.each( inputFiles , function( index ) {

                    var docIdNumber = $('input[id^="images-"]')[index].id.split('-')[1];

                    var imgId = $('input[name="images[' + docIdNumber + '].id"]').val();

                    formData.set('images[' + docIdNumber + '].id', imgId );

                    if (imgId == 0) {
                        formData.set('images[' + docIdNumber + '].imageFile', $('input[id="images-' + docIdNumber + '"]')[0].files[0] );
                    }


                    formData.set('images[' + docIdNumber + '].imageOrder', $('input[name="images[' + docIdNumber + '].sortOrder"]').val() );

                    // if ($('input[id^="imageType-"]')[index].checked) {
                    //     formData.set('images[' + docIdNumber + '].imageType', 1 );
                    // }


                });


                $.ajax ({
                    url : '/admin/provider/save',
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
                            setTimeout(function(){
                                window.location.href = "/admin/provider";
                            }, 1000);

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

    var imgCount = 0;
    
    addImageRow = function () {

        $('.imageNotExist').css('display', 'none');

        var providerImages = $('#providerImages');

        var imgHtml = 
            '<tr>' +
            '   <td>' +
            '       <a>' +
            '           <img class="img-responsive" alt="">' +
            '       </a>' +
            '   </td>' +
            '   <td>' +
            '       <input type="hidden" value="0" name="images[' + imgCount + '].id">' +
            '       <input type="text" class="form-control checkNumber" name="images[' + imgCount + '].sortOrder" value="1">' +
            '   </td>' +
            '   <td>' +
            '       <input id="images-' + imgCount + '" type="file"/>' +
            '   </td>' +
            '</tr>';

        imgCount++;

        providerImages.append(imgHtml);
    };

    checkImageCount = function () {
        var inputFiles = $('input[id^="images-"]');

        $.each( inputFiles , function( index ) {
            imgCount++;
        });

        inputFiles.change(function () {
           console.log($(this).attr('id').split('-')[1]);
            var docIdNumber = $(this).attr('id').split('-')[1];

            $('input[name="images[' + docIdNumber + '].id"]').val(0);
        });
    };

    return {
        init: function () {
            validations();
            checkImageCount();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();