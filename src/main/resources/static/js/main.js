//---------------------------Таблица всех юзеров---------------------------
function getAllUsers() {
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(users => {
            let temp = '';
            users.forEach(function (user) {
                temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="firstName${user.id}">${user.firstName}</td> 
                <td id="lastName${user.id}">${user.lastName}</td> 
                <td id="age${user.id}">${user.age}</td>
                <td id="email${user.id}">${user.email}</td>
                <td id="roles${user.id}">${user.roles.map(r => r.role.replace('ROLE_','')).join(', ')}</td>
                <td>
                <button class="btn btn-info btn-md" type="button"
                data-toggle="modal" data-target="#modalEdit" 
                onclick="openModal(${user.id})">Edit</button></td>
                <td>
                <button class="btn btn-danger btn-md" type="button"
                data-toggle="modal" data-target="#modalDelete" 
                onclick="openModal(${user.id})">Delete</button></td>
              </tr>`;
            });
            document.getElementById("allUsersTable").innerHTML = temp;
        });
}

getAllUsers()

//---------------------------Модальное окно---------------------------
function openModal(id) {
    fetch("http://localhost:8080/api/showUser/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            console.log(user);
            document.getElementById('id').value = user.id;
            document.getElementById('editFirstName').value = user.firstName;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editEmail').value = user.email;

            document.getElementById('delId').value = user.id;
            document.getElementById('delFirstName').value = user.firstName;
            document.getElementById('delLastName').value = user.lastName;
            document.getElementById('delAge').value = user.age;
            document.getElementById('delEmail').value = user.email;
        })
    });
}

//---------------------------Добавление нового юзера---------------------------
document.getElementById("NewUserForm")
    .addEventListener("submit", addNewUser);

function addNewUser(e) {
    e.preventDefault();

    let firstname = document.getElementById('newFirstName').value;
    let lastname = document.getElementById('newLastName').value;
    let age = document.getElementById('newAge').value;
    let email = document.getElementById('newEmail').value;
    let password = document.getElementById('newPassword').value;
    let roles = getRoles(Array.from(document.getElementById('newRole').selectedOptions)
        .map(role => role.value));
    fetch("http://localhost:8080/api/newUser", {
        method: "POST",
        headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
        body: JSON.stringify({
                    firstName: firstname,
                    lastName: lastname,
                    age: age,
                    email: email,
                    password: password,
                    roles: roles
                })
    })
        .then(() => {
                    document.getElementById("usersTabLink").click();
                    getAllUsers();
                    document.getElementById("NewUserForm").reset();
                })
}

//---------------------------Инфо юзера---------------------------
function showUserInfo() {
    fetch('http://localhost:8080/api/userInfo')
        .then((res) => res.json())
        .then((user) => {
            let temp = "";
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(r => r.role.replace("ROLE_", "")).join(", ")}</td>
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;
        });
}

showUserInfo();

//---------------------------Редактирование юзера---------------------------
async function editUser() {
    let user = {
        id: document.getElementById('id').value,
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        age: document.getElementById('editAge').value,
        email: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: getRoles(Array.from(document.getElementById('editRole').selectedOptions)
            .map(role => role.value))
    }
    let response = await fetch('http://localhost:8080/api/update', {
        method: "PATCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    if (response.ok) {
        $("#modalEdit .close").click();
        refreshTable();
    }
}

//---------------------------Удаление юзера---------------------------
function deleteUser() {
    fetch("http://localhost:8080/api/delete/" + document.getElementById("delId").value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    $("#modalDelete .close").click();
    refreshTable();
}

//---------------------------Обновление таблицы юзеров---------------------------
function refreshTable() {
    let table = document.getElementById('allUsersTable')
    if (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(getAllUsers, 150);
}

//---------------------------Получение ролей---------------------------
function getRoles(list) {
    let roles = [];

    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    return roles;
}