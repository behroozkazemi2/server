var customerList = function () {

    toastr.options = {"positionClass": "toast-bottom-left"};

    var customerGrid;
    var selectedTabGrid;
    var checkChangeStatus = false;
    var changedStatus = 0;
    var columns = [
        {"data": "rows"},
        {"data": "name"},
        {"data": "mobile"},
        {"data": "address"},
        {"data": "action"}
    ];
    var columnDefs = [
        {width: '1%' , targets: 0},
        {width: '15%', targets: 1},
        {width: '10%', targets: 2},
        {width: '42%', targets: 3},
        {width: '32%', targets: 4},
        {
            'searchable': false,
            'orderable': false,
            "targets": [0]
        }
    ];
    var listGrid = function () {
        $(document).on('click', '#gridTabs a', function () {
            selectedTabGrid = $(this).data('id');

            if (customerGrid && checkChangeStatus && changedStatus == selectedTabGrid) {
                $('#customerList' + selectedTabGrid).dataTable().fnDestroy();
                checkChangeStatus = false;
            }
            $('input[name="status"]').val(selectedTabGrid);
            var customerListGrid = $('#customerList' + selectedTabGrid).DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "retrieve": true,
                "pageLength": 10,
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/customer/search",
                    "type":"POST",
                    "data":{
                        "firstName": $('input[name="firstName"]').val(),
                        "lastName": $('input[name="lastName"]').val(),
                        "mobile": $('input[name="mobile"]').val(),
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

    var searchProvider = function () {
        $(document).on('click', '#search_btn', function () {

            $('#customerList' + selectedTabGrid).dataTable().fnDestroy();
            $('#customerList' + selectedTabGrid).DataTable({
                "processing": true,
                "searching": false,
                "serverSide": true,
                "pageLength": 10,
                "ajax": {
                    "url": "/admin/customer/search",
                    "type":"POST",
                    "data":{
                        "firstName": $('input[name="firstName"]').val(),
                        "lastName": $('input[name="lastName"]').val(),
                        "mobile": $('input[name="mobile"]').val(),
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

    loadDataTable = function(response){

        var all = [];
        var data = response.data;
        for (var i = 0; i < data.length; i++) {

            var provider = data[i];

            var action = '';
            switch (selectedTabGrid) {
                case 1:
                    action = '<a class="btn btn-sm btn-danger text-light"  onclick="changeStatus(' + data[i].id + ', 2)" >' +
                        '<i></i> تعلیق </a>';
                    break;
                case 2:
                    action = '<a class="btn btn-sm btn-info text-light"  onclick="changeStatus(' + data[i].id + ', 1)" >' +
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
                name: provider.name,
                mobile: provider.mobile,
                address: provider.address,
                action: '<div class="btn-group"><a href="/admin/order/customer/' + provider.id + '" type="button" class="btn btn-sm btn-primary">سفارشات</a>' + action + '</div>'
                // '<a href="/admin/customer/edit?id=' + data[i].id + '" type="button" class="btn btn-sm circle blue button-next">ویرایش</a>' +
                //         action + '<a data-toggle="modal" class="btn btn-sm circle btn-default font-red-intense"  onclick="deleteProduct(' + data[i].id + ')" >' +
                // ' حذف </a>'
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
                        url: "/admin/customer/delete",
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
        });
    };

    reloadList = function () {
        checkChangeStatus = true;
        customerGrid.ajax.reload();
        $('#customerList' + selectedTabGrid + '_wrapper').removeClass('hidden');
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