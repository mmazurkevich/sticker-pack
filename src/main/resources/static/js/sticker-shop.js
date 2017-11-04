$(".btn-wish-list").click(function() {
    if($(this).attr("class").includes("btn-wish-list-active")) {
        $(this).removeClass('btn-wish-list-active')
    } else {
        $(this).addClass('btn-wish-list-active');
        $(".alert").alert()
    }
});