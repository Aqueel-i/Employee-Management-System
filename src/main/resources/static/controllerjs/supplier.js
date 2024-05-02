//browser onload event
window.addEventListener("load", ()=>{

    //privilege object
    userPrivilege = getServiceAjaxRequest("/privilege/bymodule/Supplier");

    //call refresh supplier table function
    refreshSupplierTable();

    //call refresh supplier form function
    refreshSupplierForm();
});

const refreshSupplierTable = () => {

}

const refreshSupplierForm = ()=>{
    supplier = {};

    supplier.supplyItems = [];

    items = getServiceAjaxRequest("/item/alldata");
    fillDataIntoSelect(selectAllItem , "" , items , "itemname");

    supplierstatuses = getServiceAjaxRequest("/supplierstatus/alldata");
    fillDataIntoSelect(selectSupplierStatus, "Select Status" , supplierstatuses, "name");


}

const btnAddOneItem = () => {
    // get selected item object from allitem side into selecteditem variable
    let selectedItem = JSON.parse(selectAllItem.value);

    supplier.supplyItems.push(selectedItem); // Add selectedItem into supplier.supplyItems list
    fillDataIntoSelect(selectSelectedItem,"",supplier.supplyItems,"itemname");//refill selected item side

    //get selected item index from all item list into extind variable
    let extind = items.map(item=> item.id).indexOf(selectedItem.id);
    if (extind != -1) { //check if index exist
        items.splice(extind,1); //remove selecteditem from allitemlist
    }
    //refill allitem side
    fillDataIntoSelect(selectAllItem,"",items,"itemname");
}
const btnAddAllItem = () => {

    //add one by one item into selecteditemlist from alllist
    for (const item of items) {
        supplier.supplyItems.push(item);
    }
    //refill selecteditem side
    fillDataIntoSelect(selectSelectedItem,"",supplier.supplyItems,"itemname");

    //set empty for allitems
    items =[];
    //refill allitem side
    fillDataIntoSelect(selectAllItem,"",items,"itemname");
}
const btnRemoveOneItem = () => {

    // get selected item object from selecteditem side into selecteditem variable
    let selectedItem = JSON.parse(selectSelectedItem.value);
    items.push(selectedItem); // Add selectedItem into allitem list
    //refill allitem side
    fillDataIntoSelect(selectAllItem,"",items,"itemname");

    //get selected item index from selecteditem list into extind variable
    let extind = supplier.supplyItems.map(item=> item.id).indexOf(selectedItem.id);
    if (extind != -1) { //check if index exist
        supplier.supplyItems.splice(extind,1); //remove selecteditem from allitemlist
    }
    //refill selecteditem side
    fillDataIntoSelect(selectSelectedItem,"",supplier.supplyItems,"itemname");

}
const btnRemoveAllItem = () => {

    //add one by one item into allitemlist from selecteditemist
    for (const item of supplier.supplyItems) {
        items.push(item);
    }
    //refill allitem side to item side
    fillDataIntoSelect(selectAllItem,"",items,"itemname");

    //set empty for selecteditem
    supplier.supplyItems=[];
    //refill selected item side
    fillDataIntoSelect(selectSelectedItem,"",supplier.supplyItems,"itemname");
}

const checkFormError = () => {}
const submitSupplierForm = () => {}
