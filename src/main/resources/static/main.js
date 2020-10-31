let activUser = null;

let login = "<div class='card w-50'><h5 class='card-header'>Logg inn</h5><div class='card-body'>Skriv inn brukernavn: " +
    "<input placeholder='Brukernavn' class='form-control' id=\"username\"><br>\n" +
    "Skriv inn passord: <input placeholder='Passord' type='password' class='form-control' id=\"password\">\n" +
    "<br><button class='btn btn-outline-primary' onclick=\"loginUser()\">Logg inn</button>" +
    " <button class='btn btn-outline-secondary' onclick='showRegister()'>Registrer</button> </div></div>"

let register = "<div class='card w-50'><h5 class='card-header'>Registrer ny bruker</h5><div class='card-body'>Skriv inn brukernavn: " +
    "<input placeholder='Brukernavn' class='form-control' id=\"username\"><br>\n" +
    "Skriv inn passord: <input placeholder='Passord' type='password' class='form-control' id=\"password\">\n" +
    "<br><button class='btn btn-outline-primary' onclick=\"registrerUser()\">Registrer</button>"

let makeTodoDiv = "<div class='card w-50'><h5 class='card-header'>Lag ny todo</h5>" +
    "<div class='card-body'> <input placeholder='Todo' class='form-control' id='todoTodo'><br>" +
    "<input type='date' id='todoDate'><br><br><button onclick='makeTodo()' class='btn btn-outline-primary'>Registrer</button>" +
    " <button onclick='getTodos()' class='btn btn-outline-secondary'>Vis todos</button> </div></div>"

let todoList = [];

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

    $("#navbar").html("<div class=\"navbar-brand\" onclick=\"showMakeTodo()\">Lag todo</div>")

    $.post("/api/user/login", user, function (data, status) {
        activUser = data
        console.log(activUser);
        //$("#login").html("");
        getTodos();
    })
    .fail(function (data, status) {
        alert("Feil brukernavn eller passord");
    });
}

function registrerUser() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    let user = {
        "username" : username,
        "password" : password
    }

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
        console.log(todos)
        formatTodos(todoList)
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

function formatTodos(todos) {
    let ut = "<div class='card w-75'><div class='card-header'>Dine Todos</div><div class='card-body'><table class=\"table table-borderless table-dark\"> </div>" +
        "<tr>" +
        "<th scope=\"col\">Todo</th>" +
        "<th scope=\"col\">Done</th>" +
        "<th scope=\"col\">Date</th></tr>"
    for (let i = 0; i <todos.length; i++) {
        ut += "<tr><td>"+todos[i].todo+"</td><td><div class='custom-control custom-switch'>"
        let id = todos[i].id;
        let done = !todos[i].done
        if (todos[i].done === true) {
            ut += "<input onchange='updateTodo("+ id + ", " + done + ")' type='checkbox' checked='checked'>"
        } else {
            ut += "<input onchange='updateTodo("+ id + ", " + done + ")' type=\"checkbox\" class='custom-control-input'>"
        }
        ut += "</div></td><td>"+todos[i].frist+"</td></tr></div>"
    }
    $("#info").html(ut)
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
