var setting = function(){

    toastr.options = {"positionClass": "toast-bottom-left"};

    categoryList = function () {
        $('#categoryList').click(function () {
            $.ajax({
                url: '/admin/product/setting/getCategoriesList'

            }).done(function (data) {
                var titleList = $('#categoriesList');
                titleList.html("");
                var place;
                if (data.length == 0) {
                    place = '<tr class="text-center">' +
                        '<td colspan="4">داده ای موجود نمی‌باشد</td>' +
                        '</tr>';
                    titleList.append(place);
                }else {
                    for (var i=0 ; i<data.length ; i++){
                        var id = data[i].id;

                        var categoriesStr = '';
                        for(var j=0;j<data[i].children.length;j++) {

                            categoriesStr += data[i].children[j].text + "<br/>";

                        }

                        place = '<tr>' +
                            '<td>' + (i+1) + '</td>' +
                            '<td>' + data[i].text + '</td>' +
                            '<td>' + categoriesStr + '</td>' +
                            '<td>' +
                            '<div class="btn-group"><button value="' + id + '" class="btn btn-sm btn-info ziredit">ویرایش زیردسته‌ها</button>' +
                            '<button value="' + id + '" class="btn btn-sm btn-primary ruedit">ویرایش نام دسته</button>' +
                            '<a data-toggle="modal" class="btn btn-sm btn-danger text-light" onclick="deleteCategory(' + id + ')" >حذف</a></td>' +
                            '</div></td>' +
                            '</tr>';
                        titleList.append(place);
                    }
                }

            });
        });
    };

    deleteCategory = function (id) {
        bootbox.confirm({
            message: 'آیا مطمئن هستید؟',
            buttons: {
                confirm: {
                    label: 'بله',
                    className: 'btn-sm btn-success'
                },
                cancel: {
                    label: 'خیر',
                    className: 'btn-sm btn-danger'
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

    reloadCategoryList = function () {
        $('#categoryList').trigger('click');
    };



    tagsList = function () {
        $('#tagList').click(function () {
            $.ajax({
                url: '/admin/product/setting/getTagsList'

            }).done(function (data) {
                var titleList = $('#tagsList');
                titleList.html("");
                var place;
                if (data.length == 0) {
                    place = '<tr class="text-center">' +
                        '<td colspan="3">داده ای موجود نمی‌باشد</td>' +
                        '</tr>';
                    titleList.append(place);
                }else {
                    for (var i=0 ; i<data.length ; i++){
                        var id = data[i].id;

                        place = '<tr>' +
                            '<td>' + (i+1) + '</td>' +
                            '<td>' + data[i].text + '</td>' +
                            '<td>' +
                            '<div class="btn-group"><button value="' + id + '" class="btn btn-sm btn-primary tageditt">ویرایش</button>' +
                            '<a data-toggle="modal" class="btn btn-sm btn-danger text-light" onclick="deleteTag(' + id + ')" >حذف</a></td>' +
                            '</div></td>' +
                            '</tr>';
                        titleList.append(place);
                    }
                }

            });
        });
    };

    deleteTag = function (id) {
        bootbox.confirm({
            message: 'آیا مطمئن هستید؟',
            buttons: {
                confirm: {
                    label: 'بله',
                    className: 'btn-sm btn-success'
                },
                cancel: {
                    label: 'خیر',
                    className: 'btn-sm btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    var data={};
                    data.id = id;

                    $.ajax({
                        url: "/admin/product/setting/deleteTag",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.result) {
                                toastr.success('حذف با موفقیت انجام شد.');
                                reloadTagList();
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

    reloadTagList = function () {
        $('#tagList').trigger('click');
    };



    unitsList = function () {
        $('#unitList').click(function () {
            $.ajax({
                url: '/admin/product/setting/getUnitsList'

            }).done(function (data) {
                var titleList = $('#unitsList');
                titleList.html("");
                var place;
                if (data.length == 0) {
                    place = '<tr class="text-center">' +
                        '<td colspan="6">داده ای موجود نمی‌باشد</td>' +
                        '</tr>';
                    titleList.append(place);
                }else {
                    for (var i=0 ; i<data.length ; i++){
                        var id = data[i].id;

                        var dividable = data[i].dividable ? "می‌باشد" : "نمی‌باشد";

                        place = '<tr>' +
                            '<td>' + (i+1) + '</td>' +
                            '<td>' + data[i].name + '</td>' +
                            '<td>' + data[i].ratio + '</td>' +
                            '<td>' + data[i].tolerance + '</td>' +
                            '<td>' + dividable + '</td>' +
                            '<td>' +
                            '<div class="btn-group"><button value="' + id + '" class="btn btn-sm btn-primary untedit">ویرایش</button>' +
                            '<a data-toggle="modal" class="btn btn-sm btn-danger text-light" onclick="deleteUnit(' + id + ')" >حذف</a>' +
                            '</div></td>' +
                            '</tr>';
                        titleList.append(place);
                    }
                }

            });
        });
    };


    deleteUnit = function (id) {
        bootbox.confirm({
            message: 'آیا مطمئن هستید؟',
            buttons: {
                confirm: {
                    label: 'بله',
                    className: 'btn-sm btn-success'
                },
                cancel: {
                    label: 'خیر',
                    className: 'btn-sm btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    var data={};
                    data.id = id;

                    $.ajax({
                        url: "/admin/product/setting/deleteUnit",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.result) {
                                toastr.success('حذف با موفقیت انجام شد.');
                                reloadUnitList();
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

    reloadUnitList = function () {
        $('#unitList').trigger('click');
    };




    return{
        init :function() {

            categoryList();
            reloadCategoryList();

            tagsList();
            unitsList();

        }
    };
}();