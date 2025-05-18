
var MAX_FILE_SIZE = 300000;

encodeImageToBase64 = function (element) {

    var file = element.files[0];

    if(file) {

        var reader = new FileReader();

        return reader.readAsBinaryString(file);
    }else{

        return "";
    }

};

/**
 * Convert PersianDate From DropDown List to TimeStamp ( 1397-01-02 -> )
 * @param yearInput
 * @param mountInput
 * @param dayInput
 * @return {number}
 */
yearMountDayToDate = function(yearInput , mountInput , dayInput){

    var date = JalaliDate.jalaliToGregorian(yearInput.val(), mountInput.val(), dayInput.val());
    var day = date[2];
    var month = date[1];
    var year = date[0];
    var time = year + "-" + month + "-" + day;
    return ((new Date(time).getTime()) - (new Date().getTimezoneOffset() * 60 * 1000) / 1000);
};

stringPersianDateToDate = function(yearInput , mountInput , dayInput){

    var date = JalaliDate.jalaliToGregorian(yearInput, mountInput, dayInput);
    var day = date[2];
    var month = date[1];
    var year = date[0];
    var time = year + "-" + month + "-" + day;
    return ((new Date(time).getTime()) - (new Date().getTimezoneOffset() * 60 * 1000) / 1000);
};

stringPersianDateToDate = function(stringPersianDate){

    if(stringPersianDate=="") return "";
    var persianDate = stringPersianDate.split("/");

    var date = JalaliDate.jalaliToGregorian(persianDate[0], persianDate[1], persianDate[2]);
    var day = date[2];
    var month = date[1];
    var year = date[0];
    var time = year + "-" + month + "-" + day;
    return ((new Date(time).getTime()) - (new Date().getTimezoneOffset() * 60 * 1000) / 1000);
};

/**
 * Convert PersianDate and Time From DropDown List to TimeStamp ( 1397-01-02 13:20 -> )
 * @param yearInput
 * @param mountInput
 * @param dayInput
 * @param hour
 * @param minute
 * @return {number}
 */
yearMountDayHourToDate = function(yearInput , mountInput , dayInput, hour, minute){

    var date = JalaliDate.jalaliToGregorian(yearInput.val(), mountInput.val(), dayInput.val());
    var day = date[2];
    var month = date[1];
    var year = date[0];

    var time = year + "-" + month + "-" + day;

    if (hour) {

        time += " " + hour.val() + ":" + minute.val();

    }

    return ((new Date(time).getTime()) - (new Date().getTimezoneOffset() * 60 * 1000) / 1000);
};

/**
 * Convert TimeStamp to Persian Date ()
 * @param timeStamp
 * @return {string}
 */
timeStampToYearMonthDayHour = function (timeStamp) {
    var year = persianDate.unix((timeStamp/1000)).pDate.year;
    var month = persianDate.unix((timeStamp/1000)).pDate.month;
    var day = persianDate.unix((timeStamp/1000)).pDate.date;

    var date = new Date(timeStamp);
    var hours = date.getHours();
    var minutes = ":" + date.getMinutes();
    var seconds = ":" + date.getSeconds();

    return hours + minutes + seconds + ' ' + year+'/'+month+'/'+day;
};

/**
 * Convert TimeStamp to Persian Date and Time ()
 * @param timeStamp
 * @return {String}
 */
timeStampToYearMonthDay = function (timeStamp) {
    if (timeStamp != null) {
        var year = persianDate.unix((timeStamp/1000)).pDate.year;
        var month = persianDate.unix((timeStamp/1000)).pDate.month;
        var day = persianDate.unix((timeStamp/1000)).pDate.date;

        var yearMonthDay = year+'/'+month+'/'+day ;

        return yearMonthDay;
    }
    return "";

};

/**
 * check image size
 * @param file : File input value
 * @param avatarMaxSize : max size of image
 * @return show error if image size in large
 */
imageValidator = function (file, avatarMaxSize) {

    var fileSize = file[0].files[0].size;
    if (fileSize > avatarMaxSize){
        $('.imageSizeError').removeClass("hidden");
        file.val("");
        $('.button-next').attr("disabled", "disabled");
    }else {
        $('.imageSizeError').addClass("hidden");
        $('.button-next').removeAttrs("disabled");
    }

};


checkFileSize = function(file, maxFileSize){

    var fileSize = file[0].files[0].size;
    return fileSize < maxFileSize;

};


/**
 * Change Persian Number to En Number
 * @param input : input Without .val()
 * @return En Number
 */
convertPersianNumber = function (input) {

    input.on('keyup', function() {
        if ($(this).val() != null && $(this).val() != ''){
            var enNumber = persianJs($(this).val()).persianNumber().toString();
            $(this).val(enNumber);
        }
    });

};

var generateDropDownInModal = function (dropDownId, url) {
    $("#" + dropDownId).select2({
        dropdownParent: $('#newModal'),
        placeholder: "انتخاب کنید",
        allowClear: true,
        ajax: {
            url: '/admin/constant/' + url,
            dataType: 'json',
            processResults: function (data) {
                return {
                    results: data
                };
            }
        }
    });
};


var generateDropDown = function (dropDownId, url) {
    $("#" + dropDownId).select2({
        placeholder: "انتخاب کنید",
        allowClear: true,
        ajax: {
            url: '/admin/constant/' + url,
            dataType: 'json',
            processResults: function (data) {
                return {
                    results: data
                };
            }
        }
    });
};

var imageUpload = function (docId, step) {

    var fileName = "";
    var file = "";

    switch (step) {
        case 1:
            fileName = 'uploadFile[' + docId + ']';
            file = $('input[id="' + fileName + '"]');
            break;
        case 2:
            file = $('input[id="' + docId + '"]');
            break;
        case 3:

            break;
    }


    var data = file[0].files[0];
    var formData = new FormData();
    formData.append('importImage', data);


    if (file.val() != "") {

        if ( checkFileSize(file, MAX_FILE_SIZE) ) {

            $.ajax({
                url: "/image/upload",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (result) {

                    if (result.result) {

                        var input = 'input[id="docToken-' + docId + '"]';
                        $(input).val(result.payload);

                        switch (step) {
                            case 1:
                                var input = 'input[id="stepOneDocToken-' + docId + '"]';
                                $(input).val(result.payload);
                                $('.' + docId + '-error').addClass('hidden');
                                $('.' + docId + '-result').html("تصویر با موفقیت بارگذاری شد.");
                                break;
                            case 2:

                                $('.' + docId + '-result').html("تصویر با موفقیت بارگذاری شد.");
                                break;
                            case 3:

                                break;
                        }

                        // toastr.success("تصویر با موفقیت بارگذاری شد.");

                    }else {

                        toastr.error(result.payload);

                    }

                },
                error: function () {

                    toastr.error("خطا در ارتباط با سرور");

                }
            })

        }else {

            $('.' + docId + '-result').html("");
            toastr.error("حجم فایل بیش از حد مجاز می‌باشد.");

        }

    }else {

        toastr.error("ابتدا فایل را انتخاب نمایید.");

    }


};


var setFakeImage = function (token) {

    var inputFiles = $('input[id^="docToken-"]');
    $.each( inputFiles , function( index ) {
        var docIdNumber = $('input[id^="docToken-"]')[index].id.split('-')[1];

        var input = 'input[name="' + docIdNumber + '.number"]';
        $(input).val(1);

        var docToken = 'input[id="docToken-' + docIdNumber + '"]';
        $(docToken).val(token);
    });

}

var scrollToElement = function (id) {
    div_height = $("#"+id).height();
    div_offset = $("#"+id).offset().top;
    window_height = $(window).height();
    $('html,body').animate({
        scrollTop: div_offset-window_height+div_height
    },'slow');
};