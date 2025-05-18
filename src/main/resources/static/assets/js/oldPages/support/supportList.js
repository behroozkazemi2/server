var supportList = function () {

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
        {"data": "insert_date"},
        {"data": "status"},
        {"data": "action"}
    ];
    var columnDefs = [
        {width: '5%' , targets: 0},
        {width: '10%', targets: 1},
        {width: '15%', targets: 2},
        {width: '10%', targets: 2},
        {width: '20%', targets: 3},
        {width: '10%', targets: 4},
        {width: '15%', targets: 5},
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
                $('#supportList1' + selectedTabGrid).dataTable().fnDestroy();
                checkChangeStatus = false;
            }
            $('input[name="status"]').val(selectedTabGrid);
            var supportListGrid = $('#supportList' + selectedTabGrid).DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "retrieve": true,
                "pageLength": 10,
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/support/search",
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
            customerGrid = supportListGrid;
        });

    };

    // Load each row of datatable
    loadDataTable = function(response){
        var all = [];

        var data = response.data;
        for (var i = 0; i < data.length; i++) {

            var support = data[i];

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
            // for(var j=0;j<support.phone.length;j++) {
            //
            //     numbersStr += support.phone[j] + "<br/>";
            //
            // }
            //
            // var categoryStr = '';
            //
            // for(var j=0;j<support.categories.length;j++) {
            //
            //     categoryStr += support.categories[j].name + "<br/>";
            //
            // }

            var row = {
                id: support.id,
                rows: response.start + i + 1,
                name: support.customerFirstName,
                familyname: support.customerLastName,
                mobile: support.customerMobile,
                tracking_code: support.trackingCode,
                insert_date: moment(support.insertDate).format('jYYYY/jM/jD'),
                status: divcolorbadge(selectedTabGrid,support.statusName),
                action: '<button value="' + support.id + '" type="button" class="btn btn-sm btn-primary supdet">مشاهده</button>'
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
        }
        return colorbadge;
    };

    var supportProvider = function () {
        $(document).on('click', '#search_btn', function () {

            $('#supportList' + selectedTabGrid).dataTable().fnDestroy();
            $('#supportList' + selectedTabGrid).DataTable({
                "processing": true,
                "searching": false,
                "serverSide": true,
                "pageLength": 10,
                "ajax": {
                    "url": "/admin/support/search",
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
        $('#supportList' + selectedTabGrid + '_wrapper').removeClass('hidden');
        $('#supportSearchList' + selectedTabGrid + '_wrapper').addClass('hidden');
    };

    return {
        init: function () {
            listGrid();
            supportProvider();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();