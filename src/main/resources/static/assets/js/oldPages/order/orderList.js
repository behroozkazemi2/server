var orderList = function () {

    toastr.options = {"positionClass": "toast-bottom-left"};

    var customerGrid;
    var selectedTabGrid;
    var checkChangeStatus = false;
    var changedStatus = 0;
    var columns = [
        {"data": "rows"},
        {"data": "name"},
        {"data": "familyname"},
        {"data": "mobile"},
        {"data": "tracking_code"},
        {"data": "sumprice"},
        {"data": "insert_date"},
        {"data": "action"}
    ];
    var columnDefs = [
        {width: '5%' , targets: 0},
        {width: '8%', targets: 1},
        {width: '8%', targets: 2},
        {width: '8%', targets: 2},
        {width: '15%', targets: 3},
        {width: '20%', targets: 4},
        {width: '15%', targets: 6},
        {
            'searchable': false,
            'orderable': false,
            "targets": [0]
        }
    ];
    // Load datatable
    var listGrid = function () {
        $(document).on('click', '#gridTabs a', function () {
            selectedTabGrid = $(this).data('id');
            if (customerGrid && checkChangeStatus && changedStatus == selectedTabGrid) {
                $('#orderList' + selectedTabGrid).dataTable().fnDestroy();
                checkChangeStatus = false;
            }
            $('input[name="status"]').val(selectedTabGrid);
            var customerListGrid = $('#orderList' + selectedTabGrid).DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "retrieve": true,
                "pageLength": 10,
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/order/search",
                    "type":"POST",
                    "data":{
                        "name": $('input[name="name"]').val(),
                        "familyname": $('input[name="familyname"]').val(),
                        "mobile": $('input[name="mobile"]').val(),
                        "tracking_code": $('input[name="tracking_code"]').val(),
                        "from": stringPersianDateToDate($('input[name="from"]').val()),
                        "until": stringPersianDateToDate($('input[name="until"]').val()),
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
            customerGrid = customerListGrid;
        });

    };
    // Load each row of datatable
    loadDataTable = function(response){
        var all = [];
        var data = response.data;
        for (var i = 0; i < data.length; i++) {

            var provider = data[i];

            var action = '';
            switch (selectedTabGrid) {
                case 1:
                    action = '<a class="btn btn-sm circle btn-default font-red-intense"  onclick="changeStatus(' + data[i].id + ', 2)" >' +
                        '<i></i> تعلیق </a>';
                    break;
                case 2:
                    action = '<a class="btn btn-sm circle btn-default font-green-meadow"  onclick="changeStatus(' + data[i].id + ', 1)" >' +
                        ' فعال کردن </a>';
                    break;
            }

            // var numbersStr = '';
            // for(var j=0;j<provider.phone.length;j++) {
            //
            //     numbersStr += provider.phone[j] + "<br/>";
            //
            // }
            //
            // var categoryStr = '';
            //
            // for(var j=0;j<provider.categories.length;j++) {
            //
            //     categoryStr += provider.categories[j].name + "<br/>";
            //
            // }

            var row = {
                id: provider.id,
                rows: response.start + i + 1,
                name: provider.customerFirstName,
                familyname: provider.customerLastName,
                mobile: provider.customerMobile,
                tracking_code: provider.trackingCode,
                sumprice: provider.sumAllPrice,
                insert_date: moment(provider.insertDate).format('jYYYY/jM/jD'),
                action: '<div class="btn-group"><button value="' + provider.id + '" class="btn btn-sm btn-primary facktor">مشاهده فاکتور</button>' +
                // '<a href="/admin/provider/edit?id=' + order.id + '" type="button" class="btn btn-sm circle blue button-next">ویرایش</a>' +
                  '<button value="' + provider.id + '" class="btn btn-sm btn-danger chastat">' +
                ' تغییر وضعیت </button></div>'
            };
            all.push(row);

        }
        return all;

    };
    // Each status badge
    function divcolorbadge(status,statustext) {
        var colorbadge = '';
        switch (status) {
            case 1:
                colorbadge = '<div class="badge badge-success">' + statustext + '</div>';
                break;
            case 2:
                colorbadge = '<div class="badge badge-warning">' + statustext + '</div>';
                break;
            case 3:
                colorbadge = '<div class="badge badge-info">' + statustext + '</div>';
                break;
            case 4:
                colorbadge = '<div class="badge badge-primary">' + statustext + '</div>';
                break;
            case 5:
                colorbadge = '<div class="badge badge-danger">' + statustext + '</div>';
                break;
        }
        return colorbadge;
    };

    var searchProvider = function () {
        $(document).on('click', '#search_btn', function () {

            $('#orderList' + selectedTabGrid).dataTable().fnDestroy();
            $('#orderList' + selectedTabGrid).DataTable({
                "processing": true,
                "searching": false,
                "serverSide": true,
                "pageLength": 10,
                "ajax": {
                    "url": "/admin/order/search",
                    "type":"POST",
                    "data":{
                        "name": $('input[name="name"]').val(),
                        "familyname": $('input[name="familyname"]').val(),
                        "mobile": $('input[name="mobile"]').val(),
                        "tracking_code": $('input[name="tracking_code"]').val(),
                        "from": stringPersianDateToDate($('input[name="from"]').val()),
                        "until": stringPersianDateToDate($('input[name="until"]').val()),
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

    /*changeStatus = function (id, status) {
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
                        url: "/admin/customer/changeStatus",
                        method:"POST",
                        data :JSON.stringify(data),
                        dataType : 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
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
        });
    };*/

    deleteProduct = function (billid, billProductId) {
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
                    $.ajax({
                        url: "/admin/order/deletebill/",
                        method:"POST",
                        data : {
                            "billId": billid,
                            "billProductId": billProductId
                        },
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
        });
    };

    reloadList = function () {
        checkChangeStatus = true;
        customerGrid.ajax.reload();
        $('#orderList' + selectedTabGrid + '_wrapper').removeClass('hidden');
        $('#customerSearchList' + selectedTabGrid + '_wrapper').addClass('hidden');
    };


    return {
        init: function () {
            listGrid();
            searchProvider();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();