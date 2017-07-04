window.fbAsyncInit = function() {
    FB.init({
        appId      : '1377756422278567',
        cookie     : true,
        xfbml      : true,
        version    : 'v2.8'
    });
    FB.AppEvents.logPageView();
};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

$("#facebookSingIn").click(function () {
    FB.login(function (response) {
        if (response.status === 'connected') {
            FB.api('/me', {fields: "id, email, name, first_name, middle_name, last_name"}, function (response) {
                    var authWrapper = JSON.stringify({
                        id: response.id,
                        email: response.email,
                        name: response.name,
                        firstName: response.first_name,
                        middleName: response.middle_name,
                        lastName: response.last_name
                    });
                    $.ajax({
                        type: 'POST',
                        url: '/facebook-signin',
                        data: authWrapper,
                        contentType: 'application/json',
                        success: function (data) {
                            document.location = data;
                        }
                    })
                }
            );
        }
    }, {scope: 'public_profile,email'});
});

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}

function renderButton() {
    gapi.load('auth2', function(){
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
            client_id: '89944683825-852048csojanv1vacf88te428nuuhj82.apps.googleusercontent.com',
            scope: 'profile'
        });
        auth2.attachClickHandler(document.getElementById('googleSingIn'), {},
            function(googleUser) {
                $.post( "/google-signin", { token_id: googleUser.getAuthResponse().id_token });
            });
    });

}
