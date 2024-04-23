//define browser onload event
window.addEventListener("load", () => {

  //enable tooltip
  $('[data-bs-toggle="tooltip"]').tooltip();

  //call table refresh function
  refreshPrivilegeTable();


})

//define table refresh function
const refreshPrivilegeTable = () => {

  /* privialage = [
     {
       id: 1,
       role_id: { id: 2, name: "manager" },
       module_id: { id: 1, name: "Employee" },
       sel: true,
       inst: true,
       upd: true,
       del: true,
       
     },
     {
       id: 2,
       role_id: { id: 3, name: "Cashier" },
       module_id: { id: 1, name: "Employee" },
       sel: false,
       inst: false,
       upd: false,
       del: false,
     },
     {
       id: 3,
       role_id: { id: 4, name: "Store-manager" },
       module_id: { id: 1, name: "Employee" },
       sel: false,
       inst: false,
       upd: false,
       del: false,
     },
     {
       id: 4,
       role_id: { id: 2, name: "manager" },
       module_id: { id: 2, name: "PRIVIALAGE" },
       sel: true,
       inst: true,
       upd: true,
       del: true,
     },
     {
       id: 5,
       role_id: { id: 3, name: "Cashier" },
       module_id: { id: 2, name: "PRIVIALAGE" },
       sel: true,
       inst: true,
       upd: true,
       del: true,
     },
     {
       id: 6,
       role_id: { id: 4, name: "Store-manager" },
       module_id: { id: 2, name: "PRIVIALAGE" },
       sel: true,
       inst: true,
       upd: true,
       del: true,
     },
   ];*/

  //Create Data Array
  privileges = [];

  privileges = getServiceAjaxRequest("/privilege/alldata");

  // //call jquery ajax function
  // // ajax ("URL" , option)

  // $.ajax("/privilege/alldata", {
  //   contentType: 'json',
  //   type: "GET",
  //   async: false,
  //   success: function (data) {
  //     console.log("success" + data);
  //     Privilege = data;

  //   },

  //   error: function (resData) {
  //     console.log("Error" + resData);
  //     Privilege = [];
  //   }
  // });

  //Create Columns Array
  //Text -- String,Number,Decimal,Boolean,Date
  //Function -- Object,Array,Boolean,Decimal
  const displaypropertylist2 = [

    { dataType: "function", propertyName: getrole_id },
    { dataType: "function", propertyName: getmodule_id },
    { dataType: "function", propertyName: getsel },
    { dataType: "function", propertyName: getinst },
    { dataType: "function", propertyName: getupd },
    { dataType: "function", propertyName: getdel },
  ];

  //Fill Data into Table Function
  fillDataIntoTable1(tablePrivilege, privileges, displaypropertylist2, refillfunction, deletefunction, printfunction);

  // call jquery database function

  $('#tablePrivilege').dataTable();

}


const getrole_id = (ob) => {
  return ob.role_id.name;
};


const getmodule_id = (ob) => {
  return ob.module_id.name;
};


const getsel = (ob) => {
  if (ob.selpriv) {
    return "Granted";
  } else {
    return "Denied";
  }
};


const getinst = (ob) => {
  if (ob.inspriv) {
    return "Granted";
  } else {
    return "Denied";
  }
};


const getupd = (ob) => {
  if (ob.updpriv) {
    return "Granted";
  } else {
    return "Denied";
  }
};


const getdel = (ob) => {
  if (ob.delpriv) {
    return "Granted";
  } else {
    return "Denied";
  }
};

//employee Refill function
const refillfunction = (rowob, rowIndex) => {

}

//employee print function
const printfunction = (rowob, rowIndex) => {


}

//Define Function For Delete Privilege
const deletefunction = (rowob, rowIndex) => {
  console.log("delete");

  console.log(rowob, rowIndex);

  // Getting User Confirmation

  let privilegeConfirm = confirm("Are you Sure to Delete Following Privilege..?"
      + "\n Role:" + rowob.role_id.name);

  //Check User Confirmation

  if (privilegeConfirm) {

      //Call delete Service

      let deleteServiceResponse = getHTTPBodyAjaxRequest("/privilege", "DELETE", rowob);

      //Check delete Service Response

      if (deleteServiceResponse == "OK") {
          alert("Role Deleted Successfully...");
          refreshPrivilegeTable();

      } else {
          alert("Failed to delete Role...\n" + deleteServiceResponse)
      }
  }
}
