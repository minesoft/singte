$(function () {

    let stDataTable = $("#queryForm").stDataTable("#queryData");

    $(document).on("click", ".stStatusBtn", function () {
        $.ajax({
            url: '/admin/widget/status',
            data: {
                id: $(this).data("itemId"),
                status: $(this).data("targetStatus")
            },
            type: 'post',
            dataType: 'json',
            success: function (res) {
                console.log(res)
                stDataTable.formQuery(true);
            },
            error(err) {
                console.log(err)
            }
        })
    })

});