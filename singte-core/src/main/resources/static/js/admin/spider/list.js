$(function () {

    let formQuery = $("#queryForm").stDataTable("#queryData");

    $(document).on("click", ".saveDataBtn", function () {
        $.ajax({
            url: '/admin/spider/submitSpider',
            data: $("#modalDataForm").serialize(),
            type: 'post',
            dataType: 'json',
            success: function (res) {
                console.log(res)
                $(".cancelBtn").trigger("click");
                formQuery();
            },
            error(err) {
                console.log(err)
            }
        })
    })

});