//---------------------------Таблица всех юзеров---------------------------

function getAllUsers() {
    fetch('http://localhost:8080/admin/users')
        .then(res => res.json())
        .then(users => {
            let temp = '';
            users.forEach(user => {
                temp += '<tr>'
                temp += '<td>' + user.id + '</td>'
                temp += '<td>' + user.firstName + '</td>'
                temp += '<td>' + user.lastName + '</td>'
                temp += '<td>' + user.age + '</td>'
                temp += '<td>' + user.email + '</td>'
                temp += '<td>' + user.roles.map(r => r.role.replace("ROLE_", "")).join(", ") + '</td>'
                temp += '<td>' +
                    '<button class="btn btn-info" type="button" data-toggle="modal" data-target="#modalEdit" ' +
                    'onclick="openModal(${user.id})">Edit</button></td>'
                temp += '<td>' +
                    '<button class="btn btn-danger" type="button" data-toggle="modal" data-target="#modalEdit" ' +
                    'onclick="openModal(${user.id})">Delete</button></td>'
                temp += '</tr>'
            });
            document.getElementById("allUsersTable").innerHTML = temp;
        });
}
getAllUsers();

//---------------------------Модальное окно---------------------------
function openModal(id){
    fetch('http://localhost:8080/admin/' + id)
        .then(res => res.json())
        .then(user => {
            document.getElementById('id').value = user.id;
            document.getElementById('editFirstName').value = user.firstName;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editEmail').value = user.email;

            document.getElementById('delId').value = user.id;
            document.getElementById('delFirstName').value = user.firstName;
            document.getElementById('delLastname').value = user.lastName;
            document.getElementById('delAge').value = user.age;
            document.getElementById('delEmail').value = user.email;
        });
}

//---------------------------Добавление нового юзера---------------------------
async function addNewUser() {
    await document.getElementById('NewUser')
        .addEventListener('submit', e => {
            e.preventDefault()

            let firstname = document.getElementById('newFirstName').value;
            let lastname = document.getElementById('newLastName').value;
            let age = document.getElementById('newAge').value;
            let email = document.getElementById('newEmail').value;
            let password = document.getElementById('newPassword').value;
            let roles_list = document.getElementById('newRole').value;
            let roles = getRoles(roles_list);

            fetch('/admin/users', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({
                    firstname,
                    lastname,
                    age,
                    email,
                    password,
                    'roles': roles
                })
            })
                .then(() => {
                    document.getElementById("usersTabLink").click();
                    getAllUsers();
                    document.getElementById("newUser").reset();
                })
        })
}


//---------------------------Инфо юзера---------------------------
function showUserInfo() {
    fetch('/admin/users')
        .then((res) => res.join())
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
            document.getElementById("allUsersTable").innerHTML = temp;
        });
}
showUserInfo();
//---------------------------Редактирование юзера---------------------------
async function edituser() {
    let user = {
        id: document.getElementById('id').value,
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        age: document.getElementById('editAge').value,
        email: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: getRoles(document.getElementById('editRole').value)

    }
    let response = await fetch('/admin/users', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    $("#modalEdit").modal();
}

//---------------------------Удаление юзера---------------------------
function deleteUser() {

}

//---------------------------Получение ролей---------------------------
function getRoles(list) {
    let roles = [];
    if (list === "ADMIN") {
        let role = {
            'id': 1,
            'name': 'ADMIN'
        }
        roles.push(role)
    }
    if (list === "USER") {
        let role = {
            'id': 2,
            'name': 'USER'
        }
        roles.push(role)
    }
    return roles;
}