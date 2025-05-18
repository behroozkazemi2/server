var productProviderList = function () {

    toastr.options = {"positionClass": "toast-bottom-left"};

    var providerGrid;
    var selectedTabGrid;
    var checkChangeStatus = false;
    var changedStatus = 0;
    var provider = $('input[name="provider"]').val();

    var columns = [
        {"data": "rows"},
        {"data": "name"},
        {"data": "price"},
        {"data": "unit"},
        {"data": "shortDescription"},
        {"data": "action"}
    ];

    var columnDefs = [
        {width: '1%' , targets: 0},
        {width: '15%', targets: 1},
        {width: '10%', targets: 2},
        {width: '10%', targets: 3},
        {width: '22%', targets: 4},
        {width: '42%', targets: 5},
        {
            'searchable': false,
            'orderable': false,
            "targets": [0]
        }
    ];

    var listGrid = function () {
        $(document).on('click', '#gridTabs a', function () {
            selectedTabGrid = $(this).data('id');

            if (providerGrid && checkChangeStatus && changedStatus == selectedTabGrid) {
                $('#productProviderList' + selectedTabGrid).dataTable().fnDestroy();
                checkChangeStatus = false;
            }
            $('input[name="status"]').val(selectedTabGrid);
            productProviderListGrid = $('#productProviderList' + selectedTabGrid).DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "retrieve": true,
                "pageLength": 10,
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/provider/products/" + provider + "/search",
                    "type":"POST",
                    "data":{
                        "pName": $('input[name="pName"]').val(),
                        "pCode": $('input[name="pCode"]').val(),
                        "status": selectedTabGrid
                    },
                    "dataSrc": function (response) {

                        return loadDataTable(response);
                    }

                },
                "columns": columns,
                "columnDefs": columnDefs,
                "order": [
                    [0, "asc"]
                ],
                "drawCallback": function (settings) {
                    $("input:checkbox").uniform();
                },


            });
            providerGrid = productProviderListGrid;
        });

    };

    var searchProvider = function () {
        $(document).on('click', '#search_btn', function () {

            $('#productProviderList' + selectedTabGrid).dataTable().fnDestroy();
            $('#productProviderList' + selectedTabGrid).DataTable({
                "processing": true,
                "searching": false,
                "serverSide": true,
                "pageLength": 10,
                "ajax": {
                    "url": "/admin/provider/products/" + provider + "/search",
                    "type":"POST",
                    "data":{
                        "pName": $('input[name="pName"]').val(),
                        "pCode": $('input[name="pCode"]').val(),
                        "status": selectedTabGrid
                    },
                    "dataSrc": function (response) {

                        return loadDataTable(response);
                    }

                },
                "columns": columns,
                "columnDefs": columnDefs,
                "order": [
                    [0, "asc"]
                ]


            });
        });

    };

    let providerId;

    loadDataTable = function(response){

        var all = [];
        var data = response.data;
        for (var i = 0; i < data.length; i++) {

            var product = data[i];

            var action = '';
            switch (selectedTabGrid) {
                case 1:
                    action = '<a class="btn btn-sm btn-info text-light"  onclick="changeStatus(' + data[i].id + ', 2)" >' +
                        '<i></i> اتمام موجودی </a>';
                    break;
                case 2:
                    action = '<a class="btn btn-sm btn-success text-light"  onclick="changeStatus(' + data[i].id + ', 1)" >' +
                        ' موجود کردن </a>';
                    break;
            }

            // var numbersStr = '';
            // for(var j=0;j<product.phone.length;j++) {
            //
            //     numbersStr += product.phone[j] + "<br/>";
            //
            // }
            //
            // var categoryStr = '';
            //
            // for(var j=0;j<product.categories.length;j++) {
            //
            //     categoryStr += product.categories[j].name + "<br/>";
            //
            // }

            var row = {
                id: product.id,
                rows: response.start + i + 1,
                name: product.name,
                price: product.price,
                unit: product.unit,
                shortDescription: product.shortDescription != null ? product.shortDescription : "-",
                action: '<div class="btn-group"><a href="/admin/provider/products/' + providerId + '/edit?productProvider=' + product.id + '" class="btn btn-sm btn-primary">ویرایش</a>' +
                action + '<a data-toggle="modal" class="btn btn-sm btn-danger text-light"  onclick="deleteProduct(' + product.id + ')" >' +
                ' حذف </a></div>'
            };
            all.push(row);

        }
        return all;

    };

    changeStatus = function (id, status) {
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
                    data.status = status;


                    $.ajax({
                        url: "/admin/provider/products/" + provider + "/changeStatus",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.result) {
                                toastr.success('وضعیت با موفقیت تغییر کرد.');
                                changedStatus = status;
                                reloadList();
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

    deleteProduct = function (id) {
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
                        url: "/admin/provider/products/" + provider + "/delete",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.result) {
                                toastr.success('حذف با موفقیت انجام شد.');
                                reloadList();
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

    reloadList = function () {
        checkChangeStatus = true;
        providerGrid.ajax.reload();
        $('#productProviderList' + selectedTabGrid + '_wrapper').removeClass('hidden');
        $('#productProviderSearchList' + selectedTabGrid + '_wrapper').addClass('hidden');

    };

    return {
        init: function (providerIdd) {
            providerId=providerIdd;
            listGrid();
            searchProvider();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();