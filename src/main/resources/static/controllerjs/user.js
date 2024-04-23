//define browser onload event
window.addEventListener('load', () => {

    //enable tooltip
    $('[data-bs-toggle="tooltip"]').tooltip();

    //call table refresh function
    refreshUserTable();

    //call user form function
    refreshUserForm();


})

//define table refresh function
const refreshUserTable = () => {

    //create data array

    /*Users = [

        { id: 1, employee_id: { id: 4, fullName: 'Mohamed Aqueel', callingName: 'Aqueel' }, status: true, usrename: 'Aqueel', email: 'aqueel@email.com', role_id: { id: 2, name: 'manager' } },

        , { id: 2, employee_id: { id: 2, fullName: 'Chamod Dakshina', callingName: 'Chamod' }, status: true, usrename: 'Chamod', email: 'chamod@email.com', role_id: { id: 3, name: 'Cashire' } },

        , { id: 3, employee_id: { id: 3, fullName: 'Naveen Dulanjana', callingName: 'Naveen' }, status: true, usrename: 'naveen', email: 'naveen@email.com', role_id: { id: 3, name: 'Supervisor' } }

    ];*/

    // Users = [];


    // //call jquery ajax function
    // // ajax ("URL" , option)

    // $.ajax("/user/alldata", {
    //     contentType: 'json',
    //     type: "GET",
    //     async: false, // if we use false it waits untilthe response & if we use true it wont wit for the response and execute the next line
    //     success: function (data) {
    //         console.log("success" + data);
    //         Users = data;

    //     },

    //     error: function (resData) {
    //         console.log("Error" + resData);
    //         Users = [];
    //     }
    // });



    //create display column array

    Users = getServiceAjaxRequest("/user/alldata");

    const displayColumnList = [
        { dataType: 'function', propertyName: getEmployeeName },
        { dataType: 'text', propertyName: 'username' },
        { dataType: 'text', propertyName: 'email' },
        { dataType: 'function', propertyName: getRoleName },
        { dataType: 'function', propertyName: getStatus }
    ];

    // call data fill function

    fillDataIntoTable1(tableUser, Users, displayColumnList, refillUserForm, deleteUser, printUser, buttonVisibility = true);

    //fillDataIntoTable4(tableUser, Users, displayColumnList, refillUserForm, deleteUser, printUser, divButton);

    // call jquery database function

    $('#tableUser').dataTable();

}

//define function get employee name
const getEmployeeName = (ob) => {

    return ob.employee_id.fullname;

}

//define function get Role name
const getRoleName = (ob) => {

    //return ob.role_id.name;
    //return"Role";

    let userroles = "";
    ob.roles.forEach((element, index) => {
        if (index == ob.roles.length - 1) {
            userroles = userroles + element.name;

        } else {
            userroles = userroles + element.name + ",";
        }

    });
    return userroles;
}

//define function get Status
const getStatus = (ob) => {

    if (ob.status) {
        return '<p class = "user-active"> Active </p> ';

    } else {

        return 'inactive';

    }

}

//define function for user form refill
const refillUserForm = (rowob, rowIndex) => {

    //user = rowob; --> rowob is an object, value eka save wenne heap eke,ram eke nemei, ram eke save wenne memory address or memory address or reference number
    //olduser  = rowob; --> user and olduser variable has same reference , 

    user = JSON.parse(JSON.stringify(rowob));
    olduser = rowob;

    //elementId.value = object.releventPropertyname
    textUserName.value = user.username;
    textEmail.value = user.email;

    if (user.status) {
        checkStatus.checked = "checked";
        labelUserStatus.innerText = "User Acoount is Active"; // in innertext we can assign only tet

    } else {
        checkStatus.checked = "";
        labelUserStatus.innerHTML = "User Account is Inactive"; // in inner Html we can assign text + tags within tags
    }

    employeeListWithoutUserAccount = getServiceAjaxRequest("/employee/listwithoutuseraccount");
    employeeListWithoutUserAccount.push(user.employee_id);
    fillDataIntoSelect(selectEmployee, "Select Employee", employeeListWithoutUserAccount, "fullname", user.employee_id.fullname);

    selectEmployee.disabled = true;
    textPassword.disabled = true;
    rePassword.disabled = true;

    //get role list
    roleListWithoutAdmin = getServiceAjaxRequest("/role/listwithoutadmin");
    divRoles.innerHTML = "";
    roleListWithoutAdmin.forEach(role => {

        div = document.createElement('div');
        div.className = "form-check form-check-inline";
        inputCHK = document.createElement('input');
        inputCHK.type = "checkbox";
        inputCHK.className = "form-check-input";
        label = document.createElement('label');
        label.className = "form-check-label ms-2";
        label.innerText = role.name;

        inputCHK.onchange = function () {
            if (this.checked) {
                user.roles.push(role);
            } else {
                //user.roles.pop(role);
                let extIndex = user.roles.map(element => element.name).indexOf(role.name);
                if (extIndex != -1) {
                    user.roles.splice(extIndex, 1);
                }
            }
        }

        let extIndex = user.roles.map(item => item.name).indexOf(role.name);
        if (extIndex != -1) {
            inputCHK.checked = "checked";

        }

        div.appendChild(inputCHK);
        div.appendChild(label);

        divRoles.appendChild(div);

    });

}

//define function for get form update
const checkUserFormUpdates = () => {
    let updates = "";

    if (user.username != olduser.username) {
        updates = updates + "Username is changed. \n";

    }

    if (user.email != olduser.email) {
        updates = updates + "email is changed. \n";

    }

    if (user.status != olduser.status) {
        updates = updates + "Status is changed. \n";

    }

    if (user.roles.length != olduser.roles.length) {
        updates = updates + "Roles are Changed.. \n";
    } else {
        let extcount = 0;

        for (let newrole of user.roles) {
            for (let oldrole of olduser.roles) {
                if (newrole.id == oldrole.id) {
                    extcount = extcount + 1;
                }
            }

        }
        if (user.roles.length != extcount) {
            updates = updates + "Roles are Changed. \n";
        }

    }

    return updates;
}

//define function for user update
const buttonUpdateUser = () => {
    console.log("update");

    //check form errors
    let errors = checkUserFormErrors();

    if (errors == "") {
        //check for updates
        let updates = checkUserFormUpdates();
        if (updates == "") {
            alert("No Updates...");

        } else {
            let userCofirm = confirm("Are You Sure to Update Followings..? \n" + updates);
            if (userCofirm) {
                // need to call put request
                let putResponse = getHTTPBodyAjaxRequest("/user", "PUT", user);
                if (putResponse == "OK") {
                    alert("Successfully Updated..!");
                    refreshUserTable();
                    userForm.reset();
                    refreshUserForm();
                } else {
                    alert("Couldn't Update..! \n Following Errors Occured \n" + putResponse);

                }

            }
        }
    } else {
        alert("Form has Following Errors..! \n" + errors);
    }
}

//define function for user delete
const deleteUser = (rowob, rowIndex) => {

    console.log("user delete");

    console.log(rowob, rowIndex);

    // Getting User Confirmation

    let userConfirm = confirm("Are you Sure to Delete Following User..?"
        + "\n User Name:" + rowob.username
        + "\n User Email:" + rowob.email);

    //Check User Confirmation

    if (userConfirm) {

        //Call delete Service

        let deleteServiceResponse = getHTTPBodyAjaxRequest("/user", "DELETE", rowob);

        //Check delete Service Response

        if (deleteServiceResponse == "OK") {
            alert("User Deleted Successfully...");
            refreshUserTable();

        } else {
            alert("Failed to delete User...\n" + deleteServiceResponse)
        }
    }
}

//define function for user row print
const printUser = (rowob, rowIndex) => {

}

//Define Function for User form refresh
const refreshUserForm = () => {

    user = new Object(); //create user object
    olduser = null;

    user.roles = new Array(); // create new array

    selectEmployee.disabled = false;
    textPassword.disabled = false;
    textrepassword.disabled = false;

    //get employee list doesnt have user account
    employeeListWithoutUserAccount = getServiceAjaxRequest("/employee/listwithoutuseraccount");
    fillDataIntoSelect(selectEmployee, "select Employee", employeeListWithoutUserAccount, "fullname")

    //get role list
    roleListWithoutAdmin = getServiceAjaxRequest("/role/listwithoutadmin");
    divRoles.innerHTML = "";
    roleListWithoutAdmin.forEach(role => {

        div = document.createElement('div');
        div.className = "form-check form-check-inline";
        inputCHK = document.createElement('input');
        inputCHK.type = "checkbox";
        inputCHK.className = "form-check-input";
        label = document.createElement('label');
        label.className = "form-check-label ms-2";
        label.innerText = role.name;

        inputCHK.onchange = function () {
            if (this.checked) {
                user.roles.push(role);
            } else {
                //user.roles.pop(role);
                let extIndex = user.roles.map(element => element.name).indexOf(role.name);
                if (extIndex != -1) {
                    user.roles.splice(extIndex, 1);
                }
            }
        }

        div.appendChild(inputCHK);
        div.appendChild(label);

        divRoles.appendChild(div);

    });

    user.status = false;
    labelUserStatus.innerText = "User Account Not Active";


    //Setting Form Elements to default colors

    selectEmployee.style.border = '1px solid #ced4da';
    textUserName.style.border = '1px solid #ced4da';
    textPassword.style.border = '1px solid #ced4da';
    rePassword.style.border = '1px solid #ced4da';
    textEmail.style.border = '1px solid #ced4da';

}

//Define function form Check Error
const checkUserFormErrors = () => {
    let errors = "";

    if (user.employee_id == null) {
        errors = errors + "Select Employee..!\n";
    }

    if (user.username == null) {
        errors = errors + "Enter Username..!\n";
    }

    if (olduser == null) {

        if (user.password == null) {
            errors = errors + "Should Provide a Password..!\n";
        }

        if (rePassword.value == "") {
            errors = errors + "Should Provide the Password again..!\n";
        }

    }


    if (user.email == null) {
        errors = errors + "Eenter Email..!\n";
    }

    if (user.roles.length == 0) {
        errors = errors + "Select Role/s..!\n";
    }

    return errors;
}

//Define function for user form submit
const buttonUserFormSubmit = () => {
    console.log("Submit");
    console.log(user);

    //check form error
    let errors = checkUserFormErrors();
    if (errors == "") {

        //get user confirmation
        let userConfirm = confirm("Are You Sure to Submit Following User..?\n" + "User Name : " + user.username + "\n User Email : " + user.email + "\n Employee : " + user.employee_id.fullname);

        if (userConfirm) {

            //call post service
            let postServiceResponse = getHTTPBodyAjaxRequest("/user", "POST", user);

            //check post service response
            if (postServiceResponse == "OK") {
                alert("Saved Successfully..!");
                refreshUserTable(); // Refresh Table
                userForm.reset();// refresh Static element
                refreshUserForm();

            } else {
                alert("Failed to Save..\n" + postServiceResponse);
            }

        }
    } else {
        alert("form has following errors..\n" + errors);

    }

    //get user confirmation

    //call post service

    //check post service response
}

//Define function for password re type validator
const passwordRetypeValidator = () => {

    if (textPassword.value == rePassword.value) {

        user.password = textPassword.value;
        rePassword.style.border = "2px solid green";

    } else {

        user.password = null;
        rePassword.style.border = "2px solid red";

    }
}