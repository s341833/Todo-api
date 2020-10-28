let activUser = null;

function registrerUser() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    let user = {
        "username" : username,
        "password" : password
    }

    $.post("/api/user/login", user, function (data, status) {
        activUser = data
        console.log(activUser);
    })
    .fail(function (data, status) {
        alert(data + " " + status);
    });

}

function getTodos() {

}
