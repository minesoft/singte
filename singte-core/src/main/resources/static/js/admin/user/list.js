$(function () {

    let formQuery = $("#queryForm").stDataTable("#queryData");

    $(document).on("click", ".saveDataBtn", function () {
        $.ajax({
            url: '/admin/user/submitUser',
            data: $("#modalDataForm").serialize(),
            type: 'post',
            dataType: 'json',
            success: function (res) {
                console.log(res)
                formQuery();
            },
            error(err) {
                console.log(err)
            }
        })
    })

});