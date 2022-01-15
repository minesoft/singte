$(function () {

    let stDataTable = $("#queryForm").stDataTable("#queryData");

    $(document).on("click", ".saveDataBtn", function () {
        $.ajax({
            url: '/admin/config/submitConfig',
            data: $("#modalDataForm").serialize(),
            type: 'post',
            dataType: 'json',
            success: function (res) {
                console.log(res)
                $(".cancelBtn").trigger("click");
                stDataTable.formQuery(true);
            },
            error(err) {
                console.log(err)
            }
        })
    })

});