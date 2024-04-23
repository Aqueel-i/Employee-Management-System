//browser onload event
window.addEventListener("load",()=>{

    //privilege object
    userPrivilege = getServiceAjaxRequest("/privilege/bymodule/supplier");

    //call reefresh supplier table function
    refreshSupplierTable();

    //call refresh supplier form function
    refreshSupplierForm();

});

const refreshSupplierTable =() =>{
supplier = new Object();
supplier.supplyItems = new Array();

Items = getServiceAjaxRequest("/item/listbyavailable");
fillDataIntoSelect(selectAllItem,"",items,"itemname");

supplierstatuses = getServiceAjaxRequest("/supplierstatuses/listbyavailable/findall");
fillDataIntoSelect(selectsupplierstatuses,"select status",supplierstatuses,"name");

}

const refreshSupplierForm = () =>{

}

const btnAddOneItem = () => {}
const btnAddAllItem = () => {}
const btnRemoveOneItem = () => {}
const btnRemoveAllItem = () => {}

const checkFormError = () => {}
const submitSupplierForm = () => {}
