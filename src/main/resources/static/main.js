let activUser = null;

window.sessionStorage;

let login = "<div class='card w-50'><h5 class='card-header'>Logg inn</h5><div class='card-body'>Skriv inn brukernavn: " +
    "<input placeholder='Brukernavn' class='form-control' id=\"username\"><br>\n" +
    "Skriv inn passord: <input placeholder='Passord' type='password' class='form-control' id=\"password\">\n" +
    "<br><button class='btn btn-outline-primary' onclick=\"loginUser()\">Logg inn</button>" +
    " <button class='btn btn-outline-secondary' onclick='showRegister()'>Registrer</button>" +
    "<br>Forbli pålogget? <input type='checkbox' id='innlogget'></div></div>"

let register = "<div class='card w-50'><h5 class='card-header'>Registrer ny bruker</h5><div class='card-body'>Skriv inn brukernavn: " +
    "<input placeholder='Brukernavn' class='form-control' id=usernameReg'><br>\n" +
    "Skriv inn passord: <input placeholder='Passord' type='password' class='form-control' id='passwordReg'>\n" +
    "<br><button class='btn btn-outline-primary' onclick=\"registrerUser()\">Registrer</button>"

let makeTodoDiv = "<div class='card w-50'><h5 class='card-header'>Lag ny todo</h5>" +
    "<div class='card-body'> <input placeholder='Todo' class='form-control' id='todoTodo'><br>" +
    "<input type='date' id='todoDate'><br><br><button onclick='makeTodo()' class='btn btn-outline-primary'>Registrer</button>" +
    " <button onclick='getTodos()' class='btn btn-outline-secondary'>Vis todos</button> </div></div>"

let navbarLogedInn = "<div type='button' class=\"navbar-brand\" onclick=\"showMakeTodo()\">Lag todo</div>" +
    "<div type='button' class='navbar-brand' onclick='formatTodos(todoListNye)'>Vis todos</div> " +
    "<div type='button' class='nav-link' onclick='logOut()'>Logg ut</div>" +
    "</div> </div>"

let navbarNot = "<div type='button' class='navbar-brand' onclick='showLogin()'>Logg inn</div>" +
  "<div type='button' class='navbar-brand' onclick='showRegister()'>Registrer</div>"

let todoList = [];
let todoListNye = [];

function test() {

}

function showLogin() {
    $("#info").html(login);
}

function showRegister() {
    $("#info").html(register);
}

function showMakeTodo() {
    $("#info").html(makeTodoDiv);
}

function loginUser() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    let user = {
        "username" : username,
        "password" : password
    }

    $("#navIn").html(navbarLogedInn)

    $.post("/api/user/login", user, function (data, status) {
        activUser = data
        console.log(activUser);

        if ($("#innlogget").is(":checked") === true) {
            sessionStorage.setItem("username", activUser);
        } else {
            console.log($("innlogget").val())
        }

        getTodos();
    })
    .fail(function (data, status) {
        alert("Feil brukernavn eller passord");
    });
}

function registrerUser() {
    const username = document.getElementById("usernameReg").value;
    const password = document.getElementById("passwordReg").value;

    let user = {
        "username" : username,
        "password" : password
    }
    /*
    $.ajax({
        url: '/api/user/registrer',
        type: 'post',
        data: user,
        success: function (data) {
            activUser = data;
            console.log(activUser);
            getTodos();
            $("#info").html(login);
        },
        fail: function (data, status, xhr) {
            alert(data + " " + status + " " + xhr);
        }

    });
     */

    $.post("/api/user/registrer", user, function (data, status) {
        activUser = data
        console.log(activUser);
        //$("#login").html("");
        getTodos();
        $("#info").html(login);
    })
    .fail(function (data, status) {
        alert("Registrering feilet\nPrøv på nytt");
    });

}

function getTodos() {
    userMap = {
        "username":activUser
    }
    $.post("/api/todo/todos", userMap, function (todos) {
        todoList = todos;
        for (let i = 0; i <todoList.length; i++) {
            if (todoList[i].done === false) {
                todoListNye.push(todoList[i]);
            }
        }
        console.log(todos)
        showTodos()
        //formatTodos(todoList)
    })
}

function makeTodo() {
    let todo = {
        "username":activUser,
        "todo": $("#todoTodo").val(),
        "date": $("#todoDate").val()
    };

    alert("username " + activUser + "\n" + "todo "+ $("#todoTodo").val() + "\n" + "date " + $("#todoDate").val())

    $.post("api/todo/postTodo", todo, function (data) {
        alert(data)
    })
    .fail (function(data, status) {
        alert(data +" " + status)
    });
}

function showTodos() {
    let ut =
        "<div class='card w-75'>" +
            "<div class='card-header'>Dine Todos " +
                "<div class='btn-group'>" +
                    "<button class='btn btn-outline-warning dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
                        "Sorter" +
                    "</button>" +
                    "<div class='dropdown-menu'>" +
                        "<div type='button' class='dropdown-item' onclick='formatTodos(todoList)'>Vis alle</div>" +
                        "<div type='button' class='dropdown-item' onclick='formatTodos(todoListNye)'>Vis ikke fullført</div>" +
                    "</div>" +
                "</div>" +
            "</div>" +
            "<div class='card-body' id='todos'></div>" +
        "</div>"
    $("#info").html(ut);
    formatTodos(todoListNye);

}

function formatTodos(todos) {
    let ut =
        "<table class=\"table table-borderless table-dark\"> </div><tr>" +
        "<th scope=\"col\">Todo</th>" +
        "<th scope=\"col\">Done</th>" +
        "<th scope=\"col\">Date</th>" +
        "<th scope='col'>Delete</th></tr>"
    for (let i = 0; i <todos.length; i++) {
        ut += "<tr><td>"+todos[i].todo+"</td><td><div class='custom-control custom-switch'>"
        let id = todos[i].id;
        let done = !todos[i].done
        if (todos[i].done === true) {
            ut += "<input onchange='updateTodo("+ id + ", " + done + ")' type='checkbox' checked='checked'>"
        } else {
            ut += "<input onchange='updateTodo("+ id + ", " + done + ")' type=\"checkbox\">"
        }
        ut += "</div></td><td>"+todos[i].frist+"</td><td><button onclick='deleteTodo(" + id + ")' class='btn btn-outline-danger'>Slett</button></td>" +
            "</tr></div>"
    }
    $("#todos").html(ut)
}

function updateTodo(id, done) {
    updateMap = {
        "id":id,
        "done":done
    }
    $.post("api/todo/updateTodo", updateMap, function (data) {
        console.log(data)
    })
    .fail( function (data){
        console.log(data)
    })
}

function deleteTodo(id) {
    data = {
        "id":id
    }

    $.ajax({
        url: "api/todo/deleteTodo",
        type: 'DELETE',
        data: data,
        success: function (data) {
            alert(data)
            getTodos()
        },
        fail: function (data, status, xhr) {
            alert(xhr);
        }
    })

    /*
    $.delete("api/todo/deleteTodo", id, function (data) {
        alert(data)
        getTodos()
    }) .fail (function(data, status, xhr){
        alert(xhr)
    })

     */
}

function logOut() {
    activUser = null;
    sessionStorage.clear();
    showLogin();
    $("#navIn").html(navbarNot);
}

function onLoad() {
    showLogin();
    activUser = sessionStorage.getItem("username");
    console.log(activUser);
    if (activUser !== null) {
        console.log("in")
        $("#navIn").html(navbarLogedInn);
        getTodos();
    }

}
