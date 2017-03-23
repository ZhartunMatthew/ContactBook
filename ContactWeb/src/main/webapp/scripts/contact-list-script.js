var deleteContactButton = document.getElementById('delete-contact-button');

deleteContactButton.onclick = function () {
    var checkedContacts = getCheckedItems('contact-check');
    alert(checkedContacts.length);
};

function getCheckedItems(checkName) {
    var checkBoxes = document.getElementsByName(checkName);
    var checked = [];
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            checked.push(checkBoxes[i].value);
        }
    }
    return checked;
}