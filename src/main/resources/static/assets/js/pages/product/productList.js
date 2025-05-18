var productList = function () {

    toastr.options = {"positionClass": "toast-bottom-left"};

    var productGrid;
    var selectedTabGrid;
    var checkChangeStatus = false;
    var changedStatus = 0;
    var columns = [
        {"data": "rows"},
        {"data": "name"},
        {"data": "categories"},
        {"data": "tags"},
        {"data": "units"},
        {"data": "action"}
    ];

    var columnDefs = [
        {width: '1%' , targets: 0},
        {width: '15%', targets: 1},
        {width: '15%', targets: 2},
        {width: '30%' , targets: 3},
        {width: '20%', targets: 4},
        {width: '19%', targets: 5},
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
                "initComplete": function (settings, json) {
                    $("input:checkbox").uniform();
                },
                "ajax": {
                    "url": "/admin/product/search",
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
                "ajax": {
                    "url": "/admin/product/search",
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

            var action = '';
            switch (selectedTabGrid) {
                case 1:
                    action = '<a class="btn circle btn-default font-red-intense"  onclick="changeStatus(' + data[i].id + ', 2)" >' +
                        '<i></i> تعلیق </a>';
                    break;
                case 2:
                    action = '<a class="btn circle btn-default font-green-meadow"  onclick="changeStatus(' + data[i].id + ', 1)" >' +
                        ' فعال کردن </a>';
                    break;
            }


            var tagsStr = '';
            for(var j=0;j<product.tags.length;j++) {

                tagsStr += product.tags[j].name + "<br/>";

            }

            var unitsStr = '';
            for(var j=0;j<product.units.length;j++) {

                unitsStr += product.units[j].name + "<br/>";

            }

            var row = {

                id: product.id,
                rows: response.start + i + 1,
                name: product.name,
                categories: product.category.name,
                tags: tagsStr,
                units: unitsStr,
                action: '<div class="btn-group"><button value="' + data[i].id + '" class="btn btn-sm btn-success proedit">ویرایش</button>' +
                '<a data-toggle="modal" class="btn btn-sm btn-danger text-light"  onclick="deleteProduct(' + data[i].id + ')" >' +
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
                        url: "/admin/product/changeStatus",
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
                        url: "/admin/product/delete",
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