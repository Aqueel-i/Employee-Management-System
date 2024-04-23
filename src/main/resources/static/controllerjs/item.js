//define function for close modal
const buttonModalClose = () => {

    const closeResponse = confirm('Are you sure to close the modal...? \n ');
    if (closeResponse) {
        // close modal
        $('#itemAddModal').modal('hide');
        formItem.reset();
    }
}