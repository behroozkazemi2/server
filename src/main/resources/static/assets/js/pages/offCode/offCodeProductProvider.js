var productProviderNew = function () {

    var form = $('#newProductProvider');
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
                'order': {
                    required: true
                }

            },
            messages: { // custom messages for radio buttons and checkboxes
                'order': {
                    required:   "ترتیب را وارد نمایید.",
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
                var formData = new FormData( $('#newProductProvider')[0]);


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

                var provider = $('input[name="id"]').val();
                $.ajax ({
                    url : '/admin/offCode/newByProductProvider',
                    method :"POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    enctype:"multipart/form-data",
                    cache: false,
                    beforeSend: function() {
                        $('.loading').removeClass('hidden');
                    },
                    success :function(response) {

                        if (response.result) {

                            toastr.success("ثبت با موفقیت انجام شد.");
                            setTimeout(function(){
                                window.location.href = "/admin/offCode/" ;
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
        console.log("test");

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



    return {
        init: function () {
            validations();
            // checkImageCount();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();