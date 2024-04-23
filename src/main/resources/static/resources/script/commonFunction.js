//create function for get service request
const getServiceAjaxRequest = (url) => {

    let getServiceResponse;

    //call jquery ajax function
    // ajax ("URL" , option)

    $.ajax(url, {
        contentType: 'json',
        type: "GET",
        async: false,
        success: function (data) {
            console.log("success" + data);
            getServiceResponse = data;

        },

        error: function (resData) {
            console.log("Error" + resData);
            getServiceResponse = [];
        }
    });

    return getServiceResponse;

}

//Create function for POST,PUT,DELETE Mapping
const getHTTPBodyAjaxRequest = (url, method, ob) => {

    let serviceResponse;

    $.ajax(url, {
        type: method,
        contentType: 'application/json',
        data: JSON.stringify(ob),
        async: false,
        success: function (data) {
            console.log("success" + data);
            serviceResponse = data;

        },

        error: function (resData) {
            console.log("Fail" + resData);
            serviceResponse = resData;
        }
    });

    return serviceResponse;
}

//create function filldatainto select element
const fillDataIntoSelect = (feildId, message, dataList, propertyName, selectedvalue) => {

    feildId.innerHTML = '';

    if (message != '') {
        const optionMsgD = document.createElement('option');
        optionMsgD.innerText = message;
        optionMsgD.selected = 'selected';
        optionMsgD.disabled = 'disabled';
        feildId.appendChild(optionMsgD);

    }

    dataList.forEach(element => {
        const option = document.createElement('option');
        option.value = JSON.stringify(element); // 
        option.innerText = element[propertyName];
        if (selectedvalue == element[propertyName]) {
            option.selected = true;
        }
        feildId.appendChild(option);
    });

}


