var contactListForm = document.getElementById('contact-list-form');
var deleteContactButton = document.getElementById('delete-contact-button');
var showEmailPageButton = document.getElementById('show-email-page-button');

var popupWindowError = document.getElementById('popup-window-error');
var popupErrorMessage = document.getElementById('error-message');
var popupErrorButton = document.getElementById('popup-window-error-accept');
var contextPath = document.getElementById('path-context').value;

function closeModal(modalWindow) {
    modalWindow.style.display = 'none';
}

function openModal(modalWindow) {
    modalWindow.style.display = 'block';
}

popupErrorButton.onclick = function () {
    closeModal(popupWindowError);
    popupErrorMessage.innerHTML = null;
};

function addErrorMessage(message) {
    var newError = document.createElement('p');
    newError.innerHTML = message;
    popupErrorMessage.appendChild(newError);
}

deleteContactButton.onclick = function () {
    if(!isAnyContactSelected()) {
        addErrorMessage("Ошибка удаления: ");
        addErrorMessage("Ни один контакт не отмечен");
        openModal(popupWindowError);
        return;
    }
    contactListForm.action = contextPath + '/controller?command=delete_contact';
    contactListForm.submit();
};

showEmailPageButton.onclick = function () {
    if(!isAnyContactSelected()) {
        addErrorMessage("Ошибка отправки email: ");
        addErrorMessage("Ни один контакт не отмечен");
        openModal(popupWindowError);
        return;
    }
    contactListForm.action = contextPath + '/controller?command=show_email_page';
    contactListForm.submit();
};

function isAnyContactSelected() {
    var checkboxes = getCheckedItems('contact-check');
    return checkboxes.length > 0;
}

function getCheckedItems(itemName) {
    var checkBoxItems = document.getElementsByName(itemName);
    var checkedItems = [];
    var item;
    for(var i = 0; i < checkBoxItems.length; i++) {
        item = checkBoxItems[i];
        if(item.checked) {
            checkedItems.push(item.value);
        }
    }
    return checkedItems;
}

var actionWindowModal = document.getElementById('popup-window-message');
var okButton = document.getElementById('popup-window-action-accept');
var actionName = document.getElementById('action-name');
var actionDescription = document.getElementById('action-description');
var actionNameText = document.getElementById('action-name-text');
var actionDescriptionText = document.getElementById('action-description-text');

okButton.onclick = function () {
  closeModal(actionWindowModal);
};

document.addEventListener("DOMContentLoaded", function() {
    if(actionName.value.length > 0) {
        actionNameText.innerHTML = actionName.value;
        actionDescriptionText.innerHTML = actionDescription.value;
        openModal(actionWindowModal);
    }
});