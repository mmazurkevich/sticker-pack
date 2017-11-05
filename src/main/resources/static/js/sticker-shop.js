//Enable tooltips
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

$(".btn-wish-list").click(function() {
    if($(this).attr("class").includes("btn-wish-list-active")) {
        $(this).removeClass('btn-wish-list-active');
        iziToast.error({
            title: 'Error',
            message: 'Illegal operation'
        });
    } else {
        $(this).addClass('btn-wish-list-active');
        iziToast.success({
            title: 'OK',
            message: 'Successfully inserted record!'
        });
    }
});