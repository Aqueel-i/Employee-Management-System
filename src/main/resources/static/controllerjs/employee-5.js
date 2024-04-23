
/** browser onload
 *  Anything inside this arrow function will be triggered once the entire page has loaded.
 *  This is commonly used to ensure that certain actions or scripts are executed only after the entire page,
 *  including all its dependencies, has been fully loaded and rendered in the browser.
 *  It's a way to make sure that the code relying on specific elements or assets on the page won't run until everything is ready.**/

window.addEventListener('load', () => {

    userprivilege = getServiceAjaxRequest("/privilege/bymodule/Employee");
    // console.log('onload');

    // tooltip enable
    $('[data-bs-toggle="tooltip"]').tooltip();

    // call table refresh function
    refreshEmployeeTable();

    // call form refresh function
    refreshEmployeeForm();

})

const refreshEmployeeTable = () => {

    /* employees = [
        
            {
                id: 1,
                fullName: 'Aqueel Ishak',
                nic: '983370942v',
                mobile: '0703972445',
                email: 'aqueel.ik@gmail.com',
                employeeStatus_id: { id: 1, name: 'Working' },
                designation_id: { id: 1, name: 'Manager' },
                hasUserAccount: true
            },
        
            {
                id: 2,
                fullName: 'Nuwan Bandara',
                nic: '952313523V',
                mobile: '0752704119',
                email: 'Nuwan@gmail.com',
                employeeStatus_id: { id: 2, name: 'Resign' },
                designation_id: { id: 2, name: 'Cashier' },
                hasUserAccount: false
            },
        
            {
                id: 3,
                fullName: 'Isuru Viraitha',
                nic: '931313523V',
                mobile: '0712704119',
                email: 'Isuru@gmail.com',
                employeeStatus_id: { id: 3, name: 'Delete' },
                designation_id: { id: 3, name: 'Store-Manager' }, hasUserAccount: true
            },
        
            {
                id: 4,
                fullName: 'Sashini Sithara',
                nic: '975313523V',
                mobile: '0713704119',
                email: 'Sashini@gmail.com',
                employeeStatus_id: { id: 1, name: 'Working' },
                designation_id: { id: 2, name: 'Cashier' },
                hasUserAccount: true
            }
        
        
        ]; */

    employees = [];

    //call jquery ajax function
    // ajax ("URL" , option)

    $.ajax("/employee/alldata", {
        contentType: 'json',
        type: "GET",
        async: false,
        success: function (data) {
            console.log("success" + data);
            employees = data;

        },

        error: function (resData) {
            console.log("Error" + resData);
            employees = [];
        }
    });

    // text-> number , string , date
    // function -> object , array , boolean
    const displayPropertyList = [
        { dataType: 'text', propertyName: 'fullname' },
        { dataType: 'text', propertyName: 'nic' },
        { dataType: 'text', propertyName: 'mobile' },
        { dataType: 'function', propertyName: getHasUserAccount },
        { dataType: 'text', propertyName: 'email' },
        { dataType: 'function', propertyName: getDesignation },
        { dataType: 'function', propertyName: getEmployeeStatus }
    ];

    // call filldatainto Table function
    // (tableid, dataArrayVariableName , displayPropertyList , refillFunction , deleteFunction, printFunction)
    // fillDataIntoTable1(tableEmployee, employees ,displayPropertyList, employeeFormRefill , deleteEmployee , printEmployee, false );

    fillDataIntoTable5(tableEmployee, employees, displayPropertyList, employeeFormRefill, divModifyButton);

    $('#tableEmployee').dataTable();

    divModifyButton.className = 'd-none';
}

// create function get status name
const getEmployeeStatus = (ob) => {
    // return 'ss';
    // return ob.employeeStatus_id.name;
    if (ob.employeestatus_id.name == 'Working') {
        return '<p class="status-working">' + ob.employeestatus_id.name + '</p>'
    }

    if (ob.employeestatus_id.name == 'Resigned') {
        return '<p  class="status-resigned">' + ob.employeestatus_id.name + '</p>'
    }

    if (ob.employeestatus_id.name == 'Deleted') {
        return '<p  class="status-deleted">' + ob.employeestatus_id.name + '</p>'
    } else {
        return '<p  class="status-other">' + ob.employeestatus_id.name + '</p>'
    }


}

// create function get designation name
const getDesignation = (ob) => {
    return '<p class="designation">' + ob.designation_id.name + '</p>';
}

// create function get has user account
const getHasUserAccount = (ob) => {
    if (ob.hasUserAccount) {
        // return ' Has Account';
        return 'Active Account';
    } else {
        //return 'Have not Account';
        return ' No Account Found';
    }

}

//employee print function
const printEmployee = (ob, rowIndex) => {


}

// employee form refill
const employeeFormRefill = (ob, rowIndex) => {
    //open modal
    $('#employeeAddModal').modal('show');

    employee = JSON.parse(JSON.stringify(ob));
    oldemployee = JSON.parse(JSON.stringify(ob));// for checking updates

    // need to get full object

    // data bind ob.property --> input feild
    //elementid.value=ob.relevantpropropertyname(only validstatic element)

    textFullName.value = ob.fullname;
    textCallingName.value = ob.callingname;
    textNIC.value = ob.nic;
    if (ob.gender == "Male") {
        radioMale.checked = true;
    } else {
        radioFemale.checked = true;
    }
    dateDateOfBirth.value = ob.dob;
    textEmail.value = ob.email;
    textMobileNo.value = ob.mobile;
    if (ob.landno != null) textLandNo.value = ob.landno; else textLandNo.value = "";
    textAddress.value = ob.address;
    if (ob.note != null) textNote.value = ob.note; else textNote.value = "";
    selectCivilStatus.value = ob.civilstatus;

    //employeeStatuses = [{ id: 1, name: 'Working' }, { id: 2, name: 'Resign' }, { id: 3, name: 'Delete' }];
    employeeStatuses = getServiceAjaxRequest("/employeestatus/alldata");

    fillDataIntoSelect(selectStatus, 'Please Select', employeeStatuses, 'name', ob.employeestatus_id.name);

    //designations = [{ id: 1, name: 'Manager' }, { id: 2, name: 'Cashier' }, { id: 3, name: 'Store-Manager' }];
    designations = getServiceAjaxRequest("/designation/alldata");

    fillDataIntoSelect(selectDesignation, 'Select Designation', designations, 'name', ob.designation_id.name);

    //Disable Button
    if (userprivilege.updpriv) {
        buttonUpdate.disabled = "";
    buttonUpdate.style.cursor = "pointer";

    } else {
        buttonAdd.disabled = "disabled";
        buttonAdd.style.cursor = "not-allowed";   
    }

    if (userprivilege.delpriv) {
        buttonDelete.disabled = "";
        buttonDelete.style.cursor = "pointer";

    } else {
        buttonDelete.disabled = "disabled";
        buttonDelete.style.cursor = "not-allowed";   
    }
    
    buttonAdd.disabled = "disabled";
    buttonAdd.style.cursor = "not-allowed";


}

//defiine checkUpdate function
const checkUpdates = () => {
    let updates = "";

    if (employee.fullname != oldemployee.fullname) {
        updates = updates + "Employee Full Name Updated Successfully " + oldemployee.fullname + " into " + employee.fullname + "\n";
    }
    if (employee.callingname != oldemployee.callingname) {
        updates = updates + "Employee Caling Name Updated Successfully " + oldemployee.callingname + " into " + employee.callingname + "\n";
    }
    if (employee.nic != oldemployee.nic) {
        updates = updates + "Employee NIC Updated Successfully " + oldemployee.nic + " into " + employee.nic + "\n";
    }
    if (employee.gender != oldemployee.gender) {
        updates = updates + "Employee Gender Updated Successfully " + oldemployee.gender + " into " + employee.gender + "\n";
    }
    if (employee.dob != oldemployee.dob) {
        updates = updates + "Employee Date of Birth Updated Successfully " + oldemployee.dob + " into " + employee.dob + "\n";
    }
    if (employee.email != oldemployee.email) {
        updates = updates + "Employee Email Updated Successfully " + oldemployee.email + " into " + employee.email + "\n";
    }
    if (employee.mobile != oldemployee.mobile) {
        updates = updates + "Employee Mobile Number Updated Successfully " + oldemployee.mobile + " into " + employee.mobile + "\n";
    }
    if (employee.landno != oldemployee.landno) {
        updates = updates + "Employee Land Line Number Updated Successfully " + oldemployee.landno + " into " + employee.landno + "\n";
    }
    if (employee.address != oldemployee.address) {
        updates = updates + "Employee Address Updated Successfully " + oldemployee.address + " into " + employee.address + "\n";
    }
    if (employee.civilstatus != oldemployee.civilstatus) {
        updates = updates + "Employee Civil Status Updated Successfully " + oldemployee.civilstatus + " into " + employee.civilstatus + "\n";
    }
    if (employee.designation_id.name != oldemployee.designation_id.name) {
        updates = updates + "Employee Designation Updated Successfully " + oldemployee.designation_id.name + " into " + employee.designation_id.name + "\n";
    }
    if (employee.employeestatus_id.id != oldemployee.employeestatus_id.id) {
        updates = updates + "Employee Status Updated Successfully " + oldemployee.employeestatus_id.name + " into " + employee.employeestatus_id.name + "\n";
    }

    return updates;
}

//define function for employee update button
const buttonEmployeeUpdate = () => {
    console.log("update");

    // check form error

    let errors = checkEmployeeFormErros();
    if (errors == "") {

        //check form updates
        let updates = checkUpdates();
        if (updates == "") {
            alert("No Updates!")

        } else {
            // user confirmation
            let userCofirm = confirm("Following Changes has been done..\n" + updates);
            if (userCofirm) {

                //call put service request
                let putServiceResponse;

                $.ajax("/employee", {
                    type: "PUT",
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(employee),

                    success: function (successResponseOb) {
                        putServiceResponse = successResponseOb;
                    },
                    error: function (failResponseOb) {
                        putServiceResponse = failResponseob;
                    }
                });

                //check put service response
                if (putServiceResponse == "OK") {


                    alert("Updated Successfully..!");

                    $('#employeeAddModal').modal('hide');
                    refreshEmployeeTable();
                    formEmployee.reset();
                    refreshEmployeeForm();

                } else {
                    alert("Fail to Update eployee form \n" + putServiceResponse);

                }

            }

        }

    } else {
        alert("Errors Detected!\n " + errors)
    }

}

// employee delete function
const deleteEmployee = (ob, rowIndex) => {

    let userconfirm = confirm('Are you sure to delete following employee \n'
        + ob.fullname);

    if (userconfirm) {
        let deleteServiceResponse;

        $.ajax("/employee", {
            type: "DELETE",
            contentType: "application/json",
            data: JSON.stringify(ob),
            async: false,
            success: function (data) {
                deleteServiceResponse = data;
            },
            error: function (errordata) {
                deleteServiceResponse = errordata;
            }
        })

        if (deleteServiceResponse == "ok") {
            alert("Successfully Deleted...!");
            $('#employeeAddModal').modal('hide');
            refreshEmployeeTable();
            formEmployee.reset();
            refreshEmployeeForm();
        } else {

            alert("Fail to delete eployee form \n" + deleteServiceResponse);

        }

    }
}

//define function for refresh form
const refreshEmployeeForm = () => {

    //define new object
    employee = new Object(); // for data Binding

    //data Array
    //employeeStatuses = [{ id: 1, name: 'Working' }, { id: 2, name: 'Resign' }, { id: 3, name: 'Delete' }];
    employeeStatuses = getServiceAjaxRequest("/employeestatus/alldata");

    /*  const selectStatusElement = document.querySelector('#selectStatus');
     selectStatusElement.innerHTML = '';
 
     const optionMsg = document.createElement('option');
     optionMsg.innerText = 'Select Status';
     optionMsg.disabled = 'disabled';
     optionMsg.selected = 'selected';
     selectStatusElement.appendChild(optionMsg);
 
     employeeStatuses.forEach(element => {
         const option = document.createElement('option');
         option.innerText = element.name;
         option.value = element;
         selectStatusElement.appendChild(option);
     }); */

    fillDataIntoSelect(selectStatus, 'Please Select', employeeStatuses, 'name')

    //designations = [{ id: 1, name: 'Manager' }, { id: 2, name: 'Cashier' }, { id: 3, name: 'Store-Manager' }];
    designations = getServiceAjaxRequest("/designation/alldata");

    //call function fill data into select element
    fillDataIntoSelect(selectDesignation, 'Select Designation', designations, 'name');


    // form element need to set default color
    selectStatus.style.border = '1px solid #ced4da';
    selectDesignation.style.border = '1px solid #ced4da';

    textFullName.style.border = '1px solid #ced4da'
    textCallingName.style.border = '1px solid #ced4da'
    textNIC.style.border = '1px solid #ced4da'
    dateDateOfBirth.style.border = '1px solid #ced4da'
    textEmail.classList.remove('is-valid');

    labelMale.style.color = 'black';
    labelFemale.style.color = 'black';


    //Disable Button

    buttonUpdate.disabled = "disabled";
    buttonUpdate.style.cursor = "not-allowed";

    if (userprivilege.inspriv) {
        buttonAdd.disabled = "";
        buttonAdd.style.cursor = "pointer";
    } else {
        buttonUpdate.disabled = "disabled";
        buttonUpdate.style.cursor = "not-allowed";
    }
   

}

//create function for validate full name name generate callingname data list
const textFullNameValidator = (feildId) => {
    const fullNameValue = feildId.value;
    const regPettern = new RegExp('^([A-Z][a-z]{2,20}[\\s])+([A-Z][a-z]{2,20})$');

    if (fullNameValue != '') {
        if (regPettern.test(fullNameValue)) {
            //valid value 
            feildId.style.border = '2px solid green';
            employee.fullname = fullNameValue;

            // generate callingname list
            dlFullNameParts.innerHTML = '';
            callingnameList = fullNameValue.split(' ');
            callingnameList.forEach(element => {
                const option = document.createElement('option');
                option.value = element;
                dlFullNameParts.appendChild(option);
            });

        } else {
            //invalid
            dlFullNameParts.innerHTML = '';
            employee.fullname = null;
            employee.callingname = null;
            feildId.style.border = '2px solid red';
        }
    } else {
        dlFullNameParts.innerHTML = '';
        employee.fullname = null;
        employee.callingname = null;

        feildId.style.border = '2px solid red';
    }
}

//define function for close modal
const buttonModalClose = () => {

    const closeResponse = confirm('Are you sure to close the modal...? \n ');
    if (closeResponse) {
        // close modal
        $('#employeeAddModal').modal('hide');
        formEmployee.reset();
    }
}

//define function for check required feild error
const checkEmployeeFormErros = () => {
    let errors = '';

    if (employee.fullname == null) {
        errors = errors + 'Full Name cannot be null.. ! \n';
        textFullName.style.border = '2px solid red';
    }

    if (employee.callingname == null) {
        errors = errors + 'Please enter Calling name.. ! \n'
        textCallingName.classList.add('is-invalid')
    }

    if (employee.designation_id == null) {
        errors = errors + 'designation cannot be null.. ! \n'
    }

    if (employee.employeestatus_id == null) {
        errors = errors + 'Please select employee status... ! \n'
    }


    return errors;
}

//define function callingname validator 
const textCallingNameValidator = (feild) => {

    const feildValue = feild.value;

    const index = callingnameList.map(element => element).indexOf(feildValue);

    if (index != -1) {
        // valid
        feild.style.border = '2px solid green'; // set valid color for element
        employee.callingname = feildValue; // value binding
    } else {
        // invalid
        feild.style.border = '2px solid red';
        employee.callingname = null;

    }
}

//define function employee submit
const buttonEmployeeSubmit = () => {
    //console.log('on submit');
    console.log(employee);
    // need to check erros -- required feild value valid or not    // can check optional feild
    const errors = checkEmployeeFormErros();
    if (errors == '') {
        //not ext need to get user confirmation

        const userSubmitResponse = confirm('Are you sure to submit...?\n');

        if (userSubmitResponse) {
            // call post service 
            // check post serice responce

            $.ajax("/employee", {
                contentType: 'application/json',
                type: "POST",
                data: JSON.stringify(employee),
                async: false,
                success: function (data) {
                    console.log("success" + data);
                    postServiceResponse = data;

                },

                error: function (resData) {
                    console.log("Fail" + resData);
                    postServiceResponse = resData;
                }
            });

            if (postServiceResponse == "OK") {

                alert("saved Successfully..!");

                $('#employeeAddModal').modal('hide');
                refreshEmployeeTable();
                formEmployee.reset();
                refreshEmployeeForm();

            } else {

                alert("Fail to submit eployee form \n" + postServiceResponse);

            }

        }

    } else {
        // if error ext then set alert
        // alert('Form has following error...\n' + errors);
    }




}
