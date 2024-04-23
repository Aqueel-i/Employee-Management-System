
// creat function for fill data into table
const fillDataIntoTable1 = (tableId, dataList, displayPropertyList, refillFunction , deleteFunction , printFunction, buttonVisibility = true)=>{

    const tableBody = tableId.children[1];
    tableBody.innerHTML = '';

    dataList.forEach((element, index) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.innerText = index + 1;
        tr.appendChild(tdIndex);

        displayPropertyList.forEach((ob,ind) => {
            const td = document.createElement('td');
            if(ob.dataType == 'text'){
                td.innerText = element[ob.propertyName];
            }
            if (ob.dataType == 'function') {
              td.innerHTML =  ob.propertyName(element);
            }

            tr.appendChild(td);

        });
     
        const tdButton = document.createElement('td'); // button column

        const editButton = document.createElement('button');
        editButton.className = 'btn  ';
        editButton.innerHTML = '<i class="fa-solid fa-edit "></i> ';

        editButton.onclick = function () {
         //console.log('edit');
         refillFunction(element, index);
        }

        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn ';
        deleteButton.innerHTML = '<i class="fa-solid fa-trash "></i> ';

        deleteButton.onclick = function () {
           // console.log('delete' , element);
            deleteFunction(element, index);
        }

        const printButton = document.createElement('button');
        printButton.className = 'btn ';
        printButton.innerHTML = '<i class="fa-solid fa-print "></i> ';

        printButton.onclick = function () {
           // console.log('print');
            printFunction(element, index);
        }


        tdButton.appendChild(editButton);// append button into table column 
        tdButton.appendChild(deleteButton);// append button into table column 
        tdButton.appendChild(printButton);// append button into table column

        if (buttonVisibility) {
            tr.appendChild(tdButton); // append button column into table row
        }else{
         if(document.getElementById('tdModify') != undefined)
                tdModify.className = 'd-none';
        }

        tableBody.appendChild(tr); // append tr into table body



    });

}


// creat function for fill data into table
const fillDataIntoTable2 = (tableId, dataList, displayPropertyList, refillFunction , deleteFunction , printFunction)=>{

    const tableBody = tableId.children[1];
    tableBody.innerHTML = '';

    dataList.forEach((element, index) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.innerText = index + 1;
        tr.appendChild(tdIndex);

        displayPropertyList.forEach((ob,ind) => {
            const td = document.createElement('td');
            if(ob.dataType == 'text'){
                td.innerText = element[ob.propertyName];
            }
            if (ob.dataType == 'function') {
              td.innerHTML =  ob.propertyName(element);
            }

            tr.appendChild(td);

        });
     
        const tdButton = document.createElement('td'); // button column
        tdButton.className = 'text-center';

        const dropdownDIV = document.createElement('div');
        dropdownDIV.className = 'dropdown';

        const dropdownI = document.createElement('i');
        dropdownI.className = 'fa-solid fa-ellipsis-vertical mt-2';
        dropdownI.setAttribute('data-bs-toggle', 'dropdown');
        dropdownI.setAttribute('aria-expanded', 'false');

        dropdownDIV.appendChild(dropdownI);

        const dropdownUL = document.createElement('ul');
        dropdownUL.className = 'dropdown-menu';

        const dropdownLiEdit = document.createElement('li');
        dropdownLiEdit.className = 'dropdown-item';
       // dropdownLiEdit.innerText = 'edit';
        dropdownUL.appendChild(dropdownLiEdit);

        const dropdownLiDelete = document.createElement('li');
        dropdownLiDelete.className = 'dropdown-item';
       // dropdownLiDelete.innerText = 'Delete';
        dropdownUL.appendChild(dropdownLiDelete);

        const dropdownLiPrint = document.createElement('li');
        dropdownLiPrint.className = 'dropdown-item';
       // dropdownLiPrint.innerText = 'Print';
        dropdownUL.appendChild(dropdownLiPrint);

        dropdownDIV.appendChild(dropdownUL);

        tdButton.appendChild(dropdownDIV);

        const editButton = document.createElement('button');
        editButton.className = 'btn btn-edit w-100 fw-bold';
        editButton.innerHTML = '<i class="fa-solid fa-edit "></i> Edit';

        editButton.onclick = function () {
         //console.log('edit');
         refillFunction(element, index);
        }

        dropdownLiEdit.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn btn-outline-danger fw-bold w-100';
        deleteButton.innerHTML = '<i class="fa-solid fa-trash "></i> Delete';

        deleteButton.onclick = function () {
           // console.log('delete' , element);
            deleteFunction(element, index);
        }

        dropdownLiDelete.appendChild(deleteButton);

        const printButton = document.createElement('button');
        printButton.className = 'btn btn-outline-info fw-bold w-100';
        printButton.innerHTML = '<i class="fa-solid fa-eye fa-beat "></i> View';

        printButton.onclick = function () {
           // console.log('print');
            printFunction(element, index);
        }

        dropdownLiPrint.appendChild(printButton);


        tr.appendChild(tdButton); // append button column into table row

        tableBody.appendChild(tr); // append tr into table body



    });

}


// creat function for fill data into table
const fillDataIntoTable3 = (tableId, dataList, displayPropertyList, divButton)=>{

    const tableBody = tableId.children[1];
    tableBody.innerHTML = '';

    dataList.forEach((element, index) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.innerText = index + 1;
        tr.appendChild(tdIndex);

        displayPropertyList.forEach((ob,ind) => {
            const td = document.createElement('td');
            if(ob.dataType == 'text'){
                td.innerText = element[ob.propertyName];
            }
            if (ob.dataType == 'function') {
              td.innerHTML =  ob.propertyName(element);
            }

            tr.appendChild(td);

        });
     
        const tdButton = document.createElement('td'); // button column
        tdButton.className = 'text-center';

        const inputRadio = document.createElement('input');
        inputRadio.type = 'radio';
        inputRadio.className = 'form-check-input mt-2';
        inputRadio.name = 'modify';
        inputRadio.onclick = () => {
            window['editOb'] = element;
            window['editRow'] = index;
            divButton.className = '';
        }

        tdButton.appendChild(inputRadio);
    
        tr.appendChild(tdButton); // append button column into table row

        tableBody.appendChild(tr); // append tr into table body



    });

}


// creat function for fill data into table
const fillDataIntoTable4 = (tableId, dataList, displayPropertyList, divButton)=>{

    const tableBody = tableId.children[1];
    tableBody.innerHTML = '';

    dataList.forEach((element, index) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.innerText = index + 1;
        tr.appendChild(tdIndex);

        displayPropertyList.forEach((ob,ind) => {
            const td = document.createElement('td');
            if(ob.dataType == 'text'){
                td.innerText = element[ob.propertyName];
            }
            if (ob.dataType == 'function') {
              td.innerHTML =  ob.propertyName(element);
            }

            tr.appendChild(td);

        });
     
    
        tr.onclick = () => {
            window['editOb'] = element;
            window['editRow'] = index;
            divButton.className = '';
        }

        tableBody.appendChild(tr); // append tr into table body

    });

}


// creat function for fill data into table
const fillDataIntoTable5 = (tableId, dataList, displayPropertyList,refillFunction, divButton)=>{

    const tableBody = tableId.children[1];
    tableBody.innerHTML = '';

    dataList.forEach((element, index) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.innerText = index + 1;
        tr.appendChild(tdIndex);

        displayPropertyList.forEach((ob,ind) => {
            const td = document.createElement('td');
            if(ob.dataType == 'text'){
                td.innerText = element[ob.propertyName];
            }
            if (ob.dataType == 'function') {
              td.innerHTML =  ob.propertyName(element);
            }

            tr.appendChild(td);

        });
     
    
        tr.onclick = () => {
            refillFunction(element,index);
            window['editOb'] = element;
            window['editRow'] = index;
            divButton.className = '';
        }

        tableBody.appendChild(tr); // append tr into table body

    });

}