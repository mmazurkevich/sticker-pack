//Enable tooltips
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    iziToast.settings({
        zindex: 2000,
        position: 'topRight',
        progressBar: false
    });
});

$(".btn-wish-list").click(function() {
    if($(this).attr("class").includes("btn-wish-list-active")) {
        $(this).removeClass('btn-wish-list-active');
        iziToast.error({
            icon: 'fa fa-ban',
            color: '#fdebeb',
            titleColor: '#f5514f',
            iconColor: '#f5514f',
            messageColor: '#f5514f',
            title: 'Product',
            message: 'removed from your wishlist!'
        });
    } else {
        $(this).addClass('btn-wish-list-active');
        iziToast.info({
            icon: 'fa fa-bell-o',
            color: '#e8f7fd',
            titleColor: '#60c3e9',
            iconColor: '#60c3e9',
            messageColor: '#60c3e9',
            title: 'Product',
            message: 'added to your wishlist!'
        });
    }
});