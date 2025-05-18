var productList = function () {

    toastr.options = {"positionClass": "toast-bottom-left"};

    var productGrid;
    var selectedTabGrid;
    var checkChangeStatus = false;
    var changedStatus = 0;
    var columns = [
        {"data": "rows"},
        {"data": "type"},
        {"data": "percent"},
        {"data": "start"},
        {"data": "end"},
        {"data": "action"}
    ];
//
//     <th>ردیف</th>
//     <th>انتساب</th>
//     <th>درصد</th>
//     <th>حداکثر</th>
//     <th>تاریخ شروع</th>
//     <th>تاریخ پایان</th>
//     <th>کد</th>
//     <th>توضیحات</th>
//     <th>عملیات</th>

    var columnDefs = [
        {width: '10%' , targets: 0},
        {width: '20%', targets: 1},
        {width: '10%', targets: 2},
        {width: '20%' , targets: 3},
        {width: '20%' , targets: 4},
        {width: '20%', targets: 5},

        {
            'searchable': false,
            'orderable': false,
            "targets": [0]
        }
    ];

    var listGrid = function () {
        $(document).on('click', '#gridTabs a', function () {
            selectedTabGrid = $(this).data('id');
            if (productGrid && checkChangeStatus && changedStatus == selectedTabGrid) {
                $('#productList' + selectedTabGrid).dataTable().fnDestroy();
                checkChangeStatus = false;
            }
            $('input[name="status"]').val(selectedTabGrid);
            productListGrid = $('#productList' + selectedTabGrid).DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "retrieve": true,
                "pageLength": 10,
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.19/i18n/Persian.json"
                },
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/discount/list",
                    "type":"POST",
                    "data":{
                        "pName": $('input[name="pName"]').val(),
                        "pCode": $('input[name="pCode"]').val(),
                        "status": selectedTabGrid
                    },
                    "dataSrc": function (response) {
                        if(response.data == null){
                            response.data = [];
                        }
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
            productGrid = productListGrid;  
        });

    };

    var searchProduct = function () {
        $(document).on('click', '#search_btn', function () {

            $('#productList' + selectedTabGrid).dataTable().fnDestroy();
            $('#productList' + selectedTabGrid).DataTable({
                "processing": true,
                "searching": false,
                "serverSide": true,
                "pageLength": 10,
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.19/i18n/Persian.json"
                },
                "ajax": {
                    "url": "/admin/discount/list",
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

    loadDataTable = function(response){

        var all = [];
        var data = response.data;
        for (var i = 0; i < data.length; i++) {

            var product = data[i];

            var row = {

                id: product.id,
                rows: response.start + i + 1,
                type: product.productProviderName,
                percent: product.discountPercent,
                start: product.start,
                end: product.expire,
                action: '<a data-toggle="modal" class="btn btn-sm btn-danger text-light"  onclick="deleteProduct(' + data[i].id + ')" >' +
                    ' حذف </a></div>'
            };
            all.push(row);

        }
        return all;

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
                        url: "/admin/discount/delete",
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
        productGrid.ajax.reload();
        $('#productList' + selectedTabGrid + '_wrapper').removeClass('hidden');
        $('#productSearchList' + selectedTabGrid + '_wrapper').addClass('hidden');

    };


    return {
        init: function () {
            listGrid();
            searchProduct();
            convertPersianNumber($('.checkNumber'));
        }
    }
}();