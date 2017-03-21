function closeModal(modalWindow) {
    modalWindow.style.display = 'none';
}

function openModal(modalWindow) {
    modalWindow.style.display = 'block';
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

//---------------------------PHONE FUNCTIONS----------------------

function Phone() {
    var phone = {
        id : 0,
        countryCode : '',
        operatorCode : '',
        number : '',
        type : '',
        comment : ''
    };
    return phone;
}

var contactForm = document.getElementById('contact-form');

var contactPhones = document.getElementById('contact-phones');
var popupWindowPhones = document.getElementById('popup-window-phone');

var addPhoneButton = document.getElementById('add-phone-button');
var deletePhoneButton = document.getElementById('delete-phone-button');
var editPhoneButton = document.getElementById('edit-checked-phone-button');
var submitPhoneButton = document.getElementById('popup-submit-phone-button');
var cancelPhoneButton = document.getElementById('popup-cancel-phone-button');

var submitContactButton = document.getElementById('submit-contact-button');

var isNewPhone = false;
var isNewPhoneEdit = false;
var lastCreatedPhoneId = 0;

var editedPhoneId = 0;

addPhoneButton.onclick = function () {
    isNewPhone = true;

    document.getElementById('country-code').value = '';
    document.getElementById('operator-code').value = '';
    document.getElementById('phone-number').value = '';
    document.getElementById('phone-type').value = '';
    document.getElementById('phone-comment').value = '';

    openModal(popupWindowPhones);
};

editPhoneButton.onclick = function () {
    isNewPhone = false;
    var number = getCheckedItems('phone-check')[0];
    if(number !== undefined) {
        var phone = document.getElementById('contact-phone-number-' + number).innerHTML.trim();
        alert(phone);
        var countryCode = phone.match(/^\+\d+ /)[0];
        document.getElementById('country-code').value = countryCode.substr(1, countryCode.length).trim();
        var operatorCode = phone.match(/\(\d+\)/)[0];
        document.getElementById('operator-code').value = operatorCode.substr(1, operatorCode.length-2);
        var phoneNumber = phone.match(/( )\b\d+\b/)[0];
        document.getElementById('phone-number').value = phoneNumber.trim();

        document.getElementById('phone-type').value =
            document.getElementById('contact-phone-type-' + number).innerHTML.trim();

        document.getElementById('phone-comment').value =
            document.getElementById('contact-phone-comment-' + number).innerHTML.trim();

        isNewPhoneEdit = false;
        editedPhoneId = number;
    }

    var newNumber = getCheckedItems('new-phone-check')[0];
    if(newNumber !== undefined) {
        var phone = document.getElementById('new-contact-phone-number-' + newNumber).innerHTML.trim();
        alert(phone);
        var countryCode = phone.match(/^\+\d+ /)[0];
        document.getElementById('country-code').value = countryCode.substr(1, countryCode.length).trim();
        var operatorCode = phone.match(/\(\d+\)/)[0];
        document.getElementById('operator-code').value = operatorCode.substr(1, operatorCode.length-2);
        var phoneNumber = phone.match(/( )\b\d+\b/)[0];
        document.getElementById('phone-number').value = phoneNumber.trim();

        document.getElementById('phone-type').value =
            document.getElementById('new-contact-phone-type-' + newNumber).innerHTML.trim();

        document.getElementById('phone-comment').value =
            document.getElementById('new-contact-phone-comment-' + newNumber).innerHTML.trim();

        isNewPhoneEdit = true;
        editedPhoneId = newNumber;
    }


    openModal(popupWindowPhones);
};

deletePhoneButton.onclick = function () {
    var allPhonesForDelete = getCheckedItems('phone-check');
    deletePhones(allPhonesForDelete, 'contact-phone-');
    var allNewPhonesForDelete = getCheckedItems('new-phone-check');
    deletePhones(allNewPhonesForDelete, 'new-contact-phone-');
};

cancelPhoneButton.onclick = function () {
    closeModal(popupWindowPhones);
};

submitPhoneButton.onclick = function () {
    if(isNewPhone) {
        createNewPhone();
    } else {
        editPhone(editedPhoneId, isNewPhoneEdit);
    }
    closeModal(popupWindowPhones);
};

function createNewPhone() {
    lastCreatedPhoneId++;
    var phone = new Phone();

    phone.id = lastCreatedPhoneId;
    phone.countryCode = document.getElementById('country-code').value.trim();
    phone.operatorCode = document.getElementById('operator-code').value.trim();
    phone.number = document.getElementById('phone-number').value.trim();
    phone.type = document.getElementById('phone-type').value.trim();
    phone.comment = document.getElementById('phone-comment').value.trim();

    var oneRow = document.createElement('div');
    oneRow.className = 'one-row new-contact-phone';
    oneRow.id = 'new-contact-phone-' + phone.id;

    var label = document.createElement('label');
    label.htmlFor = 'new-phone-check-' + phone.id;

    var columnWithCheckBox = document.createElement('div');
    columnWithCheckBox.className = 'column column-2';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'new-phone-check';
    input.id = 'new-phone-check-' + phone.id;
    input.value = phone.id;
    columnWithCheckBox.appendChild(input);

    var columnWithPhoneNumber = document.createElement('div');
    columnWithPhoneNumber.className = 'column column-3';
    columnWithPhoneNumber.id = 'new-contact-phone-number-' + phone.id;
    columnWithPhoneNumber.appendChild(document.createTextNode('+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number));

    var columnWithPhoneType = document.createElement('div');
    columnWithPhoneType.className = 'column column-3';
    columnWithPhoneType.id = 'new-contact-phone-type-' + phone.id;
    columnWithPhoneType.appendChild(document.createTextNode(phone.type));

    var columnWithPhoneComment = document.createElement('div');
    columnWithPhoneComment.className = 'column column-x';
    columnWithPhoneComment.id = 'new-contact-phone-comment-' + phone.id;
    columnWithPhoneComment.appendChild(document.createTextNode(phone.comment));

    label.appendChild(columnWithCheckBox);
    label.appendChild(columnWithPhoneNumber);
    label.appendChild(columnWithPhoneType);
    label.appendChild(columnWithPhoneComment);

    oneRow.appendChild(label);

    contactPhones.appendChild(oneRow);
}

function deletePhones(phoneNumbers, checkValue) {
    for(var i = 0; i < phoneNumbers.length; i++) {
        var phoneForDelete = document.getElementById(checkValue + phoneNumbers[i]);
        phoneForDelete.parentNode.removeChild(phoneForDelete);
    }

}

function editPhone(phoneID, isNewPhoneEdit) {
    var phone = new Phone();

    phone.id = phoneID;
    phone.countryCode = document.getElementById('country-code').value.trim();
    phone.operatorCode = document.getElementById('operator-code').value.trim();
    phone.number = document.getElementById('phone-number').value.trim();
    phone.type = document.getElementById('phone-type').value.trim();
    phone.comment = document.getElementById('phone-comment').value.trim();

    alert(phoneID);

    if(isNewPhoneEdit == true) {
        document.getElementById('new-contact-phone-number-' + phoneID).innerHTML =
            '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

        document.getElementById('new-contact-phone-type-' + phoneID).innerHTML = phone.type;
        document.getElementById('new-contact-phone-comment-' + phoneID).innerHTML = phone.comment;
    } else {
        document.getElementById('contact-phone-number-' + phoneID).innerHTML =
            '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

        document.getElementById('contact-phone-type-' + phoneID).innerHTML = phone.type;
        document.getElementById('contact-phone-comment-' + phoneID).innerHTML = phone.comment;
    }
}

submitContactButton.onclick = function () {
    preparePhonesForSubmit();
    contactForm.submit();
};

function preparePhonesForSubmit() {
    var oldPhones = [];
    var newPhones = [];

    var oldPhoneElements = document.getElementsByClassName('contact-phone');
    for(var oldI = 0; oldI < oldPhoneElements.length; oldI++) {
        var oldId = oldPhoneElements[oldI].id;
        oldId = oldId.substring(oldId.length-1, oldId.length);

        var tempPhone = new Phone();
        tempPhone.id = oldId;

        var oldFullPhone = document.getElementById('contact-phone-number-' + oldId).innerHTML.trim();
        var oldCountryCode = oldFullPhone.match(/^\+\d+ /)[0];
        tempPhone.countryCode = oldCountryCode.substr(1, oldCountryCode.length).trim();
        var oldOperatorCode = oldFullPhone.match(/\(\d+\)/)[0];
        tempPhone.operatorCode = oldOperatorCode.substr(1, oldOperatorCode.length-2);
        var oldPhoneNumber = oldFullPhone.match(/( )\b\d+\b/)[0];
        tempPhone.number = oldPhoneNumber.trim();

        tempPhone.type = document.getElementById('contact-phone-type-' + oldId).innerHTML.trim();
        tempPhone.comment = document.getElementById('contact-phone-comment-' + oldId).innerHTML.trim();

        oldPhones.push(tempPhone);
    }

    var newPhoneElements = document.getElementsByClassName('new-contact-phone');
    for(var newI = 0; newI < newPhoneElements.length; newI++) {
        var newId = newPhoneElements[newI].id;
        newId = newId.substring(newId.length-1, newId.length);

        var newTempPhone = new Phone();
        newTempPhone.id = newId;

        var newFullPhone = document.getElementById('new-contact-phone-number-' + newId).innerHTML.trim();
        var newCountryCode = newFullPhone.match(/^\+\d+ /)[0];
        newTempPhone.countryCode = newCountryCode.substr(1, newCountryCode.length).trim();
        var newOperatorCode = newFullPhone.match(/\(\d+\)/)[0];
        newTempPhone.operatorCode = newOperatorCode.substr(1, newOperatorCode.length-2);
        var newPhoneNumber = newFullPhone.match(/( )\b\d+\b/)[0];
        newTempPhone.number = newPhoneNumber.trim();

        newTempPhone.type = document.getElementById('new-contact-phone-type-' + newId).innerHTML.trim();
        newTempPhone.comment = document.getElementById('new-contact-phone-comment-' + newId).innerHTML.trim();

        newPhones.push(newTempPhone);
    }
    contactForm.appendChild(addElementsInHiddenInput('old-phones', JSON.stringify(oldPhones)));
    contactForm.appendChild(addElementsInHiddenInput('new-phones', JSON.stringify(newPhones)));
}

function addElementsInHiddenInput(name, items) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = items;
    return input;
}

//------------------------ATTACHMENT FUNCTIONS--------------------------

function Attachment() {
    var attachment = {
        id : 0,
        fileName : '',
        dateUpload : '',
        comment: ''
    };
    return attachment;
}

var contactAttachments = document.getElementById('contact-attachments');
var popupWindowAttachments = document.getElementById('popup-window-attachment');

var addAttachmentButton = document.getElementById('add-attachment-button');
var deleteAttachmentButton = document.getElementById('delete-attachment-button');
var editAttachmentButton = document.getElementById('edit-checked-attachment-button');
var submitAttachmentButton = document.getElementById('popup-submit-attachment-button');
var cancelAttachmentButton = document.getElementById('popup-cancel-attachment-button');

var isNewAttachment = false;
var lastCreatedAttachment = 0;

addAttachmentButton.onclick = function () {
    isNewAttachment = false;

    document.getElementById('attachment-path').value = '';
    document.getElementById('attachment-comment').value = '';

    openModal(popupWindowAttachments);
};

cancelAttachmentButton.onclick = function () {
    closeModal(popupWindowAttachments);
};

submitAttachmentButton.onclick = function () {
    closeModal(popupWindowAttachments);
};