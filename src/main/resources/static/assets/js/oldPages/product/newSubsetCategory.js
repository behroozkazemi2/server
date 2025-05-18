var newSubsetCategory = function(){

    var form = $('#add_subset_submit_form');
    var error = $('.alert-danger', form);
    var success = $('.alert-success', form);
    toastr.options = {"positionClass": "toast-bottom-left"};
    var category;


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

                var formData = new FormData( $('#add_subset_submit_form')[0]);

                $.ajax ({
                    url : '/admin/product/setting/subset/save',
                    method :"POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success :function(response) {
                        if (response.result) {
                            $('input[name="name"]').val("");
                            toastr.success("ثبت با موفقیت انجام شد.");
                            reloadCategoryList();
                            getSubsetList();
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

    getSubsetList = function(){

        var data={};
        data.id = category;

        $.ajax({
            url: "/admin/product/setting/getSubsetList",
            method:"POST",
            data :JSON.stringify(data),
            dataType : 'json',
            contentType: 'application/json',
            success: function (response) {
                if (response.result) {

                    var data = JSON.parse(response.payload);

                    var tableList = $('#subsetsList');
                    
                    var html = '';
                    tableList.html(html);

                    if (data.length == 0){
                        html =
                            '<tr class="text-center">' +
                            '   <td colspan="3">اطلاعاتی یافت نشد</td>' +
                            '</tr>'
                    } else {

                        for (var i=0; i< data.length; i++ ){
                            var rowData = data[i];
                            html +=
                                '<tr>' +
                                '   <td>' + (i+1) + '</td>' +
                                '   <td>' + rowData.text + '</td>' +
                                '   <td>' +
                                '       <a data-toggle="modal" class=" deleteOne btn btn-sm btn-danger text-light font-red-intense" onclick="deleteSubsetCategory(' + rowData.id + ')">حذف</a></td>' +
                                '   </td>' +
                                '</tr>'
                        }
                    }

                    tableList.html(html);

                } else {
                    toastr.error(response.payload);

                }
            }
        });

    };

    deleteSubsetCategory = function (id) {
        bootbox.confirm({
            message: 'آیا مطمئن هستید؟',
            buttons: {
                confirm: {
                    label: 'بله',
                    className: 'btn-success'
                },
                cancel: {
                    label: 'خیر',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    var data={};
                    data.id = id;

                    $.ajax({
                        url: "/admin/product/setting/deleteCategory",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.result) {
                                toastr.success('حذف با موفقیت انجام شد.');
                                reloadCategoryList();
                                getSubsetList();
                                bootbox.hideAll();
                            } else {
                                toastr.error(data.payload);

                            }
                        }
                    });


                }
            }
        }).find('.modal-content').css({
            'margin-top': function (){
                let w = $( window ).height();
                let b = $(".modal-dialog").height();
                let h = (w-b)/2;
                h-=100;
                return h+"px";
            }
        });
    };

    setCategory = function (id) {
        category = id;
        getSubsetList();
    };


    return{
        init :function() {
            validations();
            convertPersianNumber($('.checkNumber'));

        }
    };
}();
