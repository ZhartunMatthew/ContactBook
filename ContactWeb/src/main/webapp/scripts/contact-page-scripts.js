//---------------------------PHONE FUNCTIONS----------------------

function Phone() {
    var phone = {
        id : null,
        countryCode : '',
        operatorCode : '',
        number : '',
        type : 0,
        comment : ''
    };
    return phone;
}

var oldPhones = [];
var newPhones = [];

var contactForm = document.getElementById('contact-form');

var contactPhones = document.getElementById('contact-phones');
var popupWindowPhones = document.getElementById('popup-window-phone');

var addPhoneButton = document.getElementById('add-phone-button');
var deletePhoneButton = document.getElementById('delete-phone-button');
var editPhoneButton = document.getElementById('edit-checked-phone-button');

var submitPhoneButton = document.getElementById('popup-submit-phone-button');
var cancelPhoneButton = document.getElementById('popup-cancel-phone-button');

var submitContactButton = document.getElementById('submit-contact-button');


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

function deleteItem(items, checkValue) {
    for(var i = 0; i < items.length; i++) {
        var itemForDelete = document.getElementById(checkValue + items[i]);
        itemForDelete.parentNode.removeChild(itemForDelete);
    }
}

submitContactButton.onclick = function () {
    preparePhonesForSubmit();
    prepareAttachmentForSubmit();
    preparePhotoForSubmit();

    contactForm.submit();
};


var isNewPhone = false;
var isNewPhoneEdit = false;

var lastCreatedPhoneId = 0;
var editedPhoneId = 0;

addPhoneButton.onclick = function () {
    isNewPhone = true;

    document.getElementById('country-code').value = '';
    document.getElementById('operator-code').value = '';
    document.getElementById('phone-number').value = '';
    document.getElementById('phone-type').value = 2;
    document.getElementById('phone-comment').value = '';

    openModal(popupWindowPhones);
};

editPhoneButton.onclick = function () {

    isNewPhone = false;
    var items = getCheckedItems('phone-check');
    var newItems = getCheckedItems('new-phone-check');

    if(items.length + newItems.length > 1) {
        alert('To much checked phones');
        return;
    }

    var number = items[0];
    if(number !== undefined && number !== 0) {
        var phone = document.getElementById('contact-phone-number-' + number).innerHTML.trim();
        var countryCode = phone.match(/^\+\d+ /)[0];
        document.getElementById('country-code').value = countryCode.substr(1, countryCode.length).trim();
        var operatorCode = phone.match(/\(\d+\)/)[0];
        document.getElementById('operator-code').value = operatorCode.substr(1, operatorCode.length-2);
        var phoneNumber = phone.match(/( )\b\d+\b/)[0];
        document.getElementById('phone-number').value = phoneNumber.trim();

        var typeName = document.getElementById('contact-phone-type-' + number).innerHTML.trim();
        document.getElementById('phone-type').value = typeToVal(typeName);


        document.getElementById('phone-comment').value =
            document.getElementById('contact-phone-comment-' + number).innerHTML.trim();

        isNewPhoneEdit = false;
        editedPhoneId = number;
        number = 0;

        openModal(popupWindowPhones);
    }

    var newNumber = newItems[0];
    if(newNumber !== undefined && newNumber !== 0) {
        var newPhone = document.getElementById('new-contact-phone-number-' + newNumber).innerHTML.trim();
        var newCountryCode = newPhone.match(/^\+\d+ /)[0];
        document.getElementById('country-code').value = newCountryCode.substr(1, newCountryCode.length).trim();
        var newOperatorCode = newPhone.match(/\(\d+\)/)[0];
        document.getElementById('operator-code').value = newOperatorCode.substr(1, newOperatorCode.length-2);
        var newPhoneNumber = newPhone.match(/( )\b\d+\b/)[0];
        document.getElementById('phone-number').value = newPhoneNumber.trim();

        var newTypeName = document.getElementById('new-contact-phone-type-' + newNumber).innerHTML.trim();
        document.getElementById('phone-type').value = typeToVal(newTypeName);


        document.getElementById('phone-comment').value =
            document.getElementById('new-contact-phone-comment-' + newNumber).innerHTML.trim();

        isNewPhoneEdit = true;
        editedPhoneId = newNumber;
        newNumber = 0;

        openModal(popupWindowPhones);
    }


};

deletePhoneButton.onclick = function () {
    var allPhonesForDelete = getCheckedItems('phone-check');
    deleteItem(allPhonesForDelete, 'contact-phone-');
    var allNewPhonesForDelete = getCheckedItems('new-phone-check');
    deleteItem(allNewPhonesForDelete, 'new-contact-phone-');
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
    phone.type = document.getElementById('phone-type').value;
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
    columnWithPhoneType.appendChild(document.createTextNode(valToType(phone.type)));

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

function editPhone(phoneID, isNewPhoneEdit) {
    var phone = new Phone();

    phone.id = phoneID;
    phone.countryCode = document.getElementById('country-code').value.trim();
    phone.operatorCode = document.getElementById('operator-code').value.trim();
    phone.number = document.getElementById('phone-number').value.trim();
    phone.type = document.getElementById('phone-type').value;
    phone.comment = document.getElementById('phone-comment').value.trim();

    if(isNewPhoneEdit == true) {
        document.getElementById('new-contact-phone-number-' + phoneID).innerHTML =
            '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

        document.getElementById('new-contact-phone-type-' + phoneID).innerHTML = valToType(phone.type);
        document.getElementById('new-contact-phone-comment-' + phoneID).innerHTML = phone.comment;
    } else {
        document.getElementById('contact-phone-number-' + phoneID).innerHTML =
            '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

        document.getElementById('contact-phone-type-' + phoneID).innerHTML = valToType(phone.type);
        document.getElementById('contact-phone-comment-' + phoneID).innerHTML = phone.comment;
    }
}

function preparePhonesForSubmit() {
    var oldPhoneElements = document.getElementsByClassName('contact-phone');
    for(var oldI = 0; oldI < oldPhoneElements.length; oldI++) {
        var oldId = oldPhoneElements[oldI].id;
        oldId = oldId.split('-')[2];

        var tempPhone = new Phone();
        tempPhone.id = oldId;

        var oldFullPhone = document.getElementById('contact-phone-number-' + oldId).innerHTML.trim();
        var oldCountryCode = oldFullPhone.match(/^\+\d+ /)[0];
        tempPhone.countryCode = oldCountryCode.substr(1, oldCountryCode.length).trim();
        var oldOperatorCode = oldFullPhone.match(/\(\d+\)/)[0];
        tempPhone.operatorCode = oldOperatorCode.substr(1, oldOperatorCode.length-2);
        var oldPhoneNumber = oldFullPhone.match(/( )\b\d+\b/)[0];
        tempPhone.number = oldPhoneNumber.trim();

        var tempType = document.getElementById('contact-phone-type-' + oldId).innerHTML.trim();
        tempPhone.type = typeToVal(tempType);
        tempPhone.comment = document.getElementById('contact-phone-comment-' + oldId).innerHTML.trim();

        oldPhones.push(tempPhone);
    }

    var newPhoneElements = document.getElementsByClassName('new-contact-phone');
    for(var newI = 0; newI < newPhoneElements.length; newI++) {
        var newId = newPhoneElements[newI].id;
        newId = newId.split('-')[3];

        var newTempPhone = new Phone();
        var newFullPhone = document.getElementById('new-contact-phone-number-' + newId).innerHTML.trim();
        var newCountryCode = newFullPhone.match(/^\+\d+ /)[0];
        newTempPhone.countryCode = newCountryCode.substr(1, newCountryCode.length).trim();
        var newOperatorCode = newFullPhone.match(/\(\d+\)/)[0];
        newTempPhone.operatorCode = newOperatorCode.substr(1, newOperatorCode.length-2);
        var newPhoneNumber = newFullPhone.match(/( )\b\d+\b/)[0];
        newTempPhone.number = newPhoneNumber.trim();

        var newTempType = document.getElementById('new-contact-phone-type-' + newId).innerHTML.trim();
        newTempPhone.type = typeToVal(newTempType);
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

function typeToVal(type) {
    return type == 'Домашний' ? 1 : 2;
}

function valToType(value) {
    return value == 1 ? 'Домашний' : 'Мобильный';
}

//------------------------ATTACHMENT FUNCTIONS--------------------------

function Attachment() {
    var attachment = {
        id : null,
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
var isNewAttachmentEdit = false;

var lastCreatedAttachmentId = 0;
var editedAttachmentId = 0;

addAttachmentButton.onclick = function () {
    isNewAttachment = true;

    document.getElementById('attachment-path').value = '';
    document.getElementById('attachment-comment').value = '';
    document.getElementById('attachment-path').style.display = 'block';

    openModal(popupWindowAttachments);
};

editAttachmentButton.onclick = function () {
    isNewAttachment = false;

    var items = getCheckedItems('attachment-check');
    var newItems = getCheckedItems('new-attachment-check');

    if(items.length + newItems.length > 1) {
        alert('To much checked attachments');
        return;
    }

    var number = items[0];
    if(number !== undefined && number !== 0) {
        document.getElementById('attachment-comment').value =
            document.getElementById('contact-attachment-comment-' + number).innerHTML.trim();

        isNewAttachmentEdit = false;
        editedAttachmentId = number;
        document.getElementById('attachment-path').style.display = 'none';
        openModal(popupWindowAttachments);
    }

    var newNumber = newItems[0];
    if(newNumber != undefined && newNumber != 0) {
        document.getElementById('attachment-comment').value =
            document.getElementById('new-contact-attachment-comment-' + newNumber).innerHTML.trim();

        isNewAttachmentEdit = true;
        editedAttachmentId = newNumber;
        document.getElementById('attachment-path').style.display = 'none';
        openModal(popupWindowAttachments);
    }
};

deleteAttachmentButton.onclick = function () {
    var allAttachmentsForDelete = getCheckedItems('attachment-check');
    deleteItem(allAttachmentsForDelete, 'contact-attachment-');
    var allNewAttachmentsForDelete = getCheckedItems('new-attachment-check');
    deleteItem(allNewAttachmentsForDelete, 'new-contact-attachment-');
    deleteItem(allNewAttachmentsForDelete, 'new-attachment-input-');
};

cancelAttachmentButton.onclick = function () {
    closeModal(popupWindowAttachments);
};

submitAttachmentButton.onclick = function () {
    if(isNewAttachment) {
        createNewAttachment();
    } else {
        editAttachment(editedAttachmentId, isNewAttachmentEdit);
    }
    closeModal(popupWindowAttachments);
};

function getCurrentDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    return  year + '-' + month + '-' + day;
}

function createNewAttachment() {
    lastCreatedAttachmentId++;
    var attachment = new Attachment();

    attachment.id = lastCreatedAttachmentId;
    attachment.dateUpload = getCurrentDate();
    var fileName = document.getElementById('attachment-path').value;
    fileName = fileName.split('\\');
    attachment.fileName = fileName[fileName.length-1];
    attachment.comment = document.getElementById('attachment-comment').value.trim();

    var oneRow = document.createElement('div');
    oneRow.className = 'one-row new-contact-attachment';
    oneRow.id = 'new-contact-attachment-' + attachment.id;

    var label = document.createElement('label');
    label.htmlFor = 'new-attachment-check-' + attachment.id;

    var columnWithCheckBox = document.createElement('div');
    columnWithCheckBox.className = 'column column-2';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'new-attachment-check';
    input.id = 'new-attachment-check-' + attachment.id;
    input.value = attachment.id;
    columnWithCheckBox.appendChild(input);

    var columnWithFileName = document.createElement('div');
    columnWithFileName.className = 'column column-3';
    columnWithFileName.id = 'new-contact-attachment-file-path-' + attachment.id;
    columnWithFileName.appendChild(document.createTextNode(attachment.fileName));

    var columnWithDateUpload = document.createElement('div');
    columnWithDateUpload.className = 'column column-3';
    columnWithDateUpload.id = 'new-contact-attachment-upload-date-' + attachment.id;
    columnWithDateUpload.appendChild(document.createTextNode(attachment.dateUpload));

    var columnWithAttachmentComment = document.createElement('div');
    columnWithAttachmentComment.className = 'column column-x';
    columnWithAttachmentComment.id = 'new-contact-attachment-comment-' + attachment.id;
    columnWithAttachmentComment.appendChild(document.createTextNode(attachment.comment));

    label.appendChild(columnWithCheckBox);
    label.appendChild(columnWithFileName);
    label.appendChild(columnWithDateUpload);
    label.appendChild(columnWithAttachmentComment);

    oneRow.appendChild(label);

    contactAttachments.appendChild(oneRow);

    var attachmentInput = document.getElementById('attachment-path');
    addAttachmentToInputField(attachmentInput);
}

function addAttachmentToInputField(attachmentInput) {
    var newAttachmentFileInput = attachmentInput;

    newAttachmentFileInput.id = 'new-attachment-input-' + lastCreatedAttachmentId;
    newAttachmentFileInput.name = 'new-attachment-input-' + lastCreatedAttachmentId;
    newAttachmentFileInput.style.display = 'none';

    var newAttachmentList = document.getElementById('attachment-input-field');
    newAttachmentList.appendChild(newAttachmentFileInput);

    var fileInputButton = document.createElement('input');
    fileInputButton.type = 'file';
    fileInputButton.id = 'attachment-path';

    document.getElementById('path-to-file').appendChild(fileInputButton);
}

function editAttachment(attachmentID, isNewAttachmentEdit) {
    var attachment = new Attachment();

    attachment.id = attachmentID;
    attachment.comment = document.getElementById('attachment-comment').value.trim();

    if(isNewAttachmentEdit) {
        document.getElementById('new-contact-attachment-comment-' + attachmentID).innerHTML = attachment.comment;
    } else {
        document.getElementById('contact-attachment-comment-' + attachmentID).innerHTML = attachment.comment;
    }
}

function prepareAttachmentForSubmit() {
    var oldAttachments = [];
    var newAttachments = [];

    var oldAttachmentElements = document.getElementsByClassName('contact-attachment');
    for(var oldI = 0; oldI < oldAttachmentElements.length; oldI++) {
        var oldId = oldAttachmentElements[oldI].id;
        oldId = oldId.split('-')[2];

        var tempAttachment = new Attachment();
        tempAttachment.id = oldId;

        tempAttachment.name = document.getElementById('contact-attachment-file-path-' + oldId).innerHTML.trim();
        tempAttachment.dateUpload = document.getElementById('contact-attachment-upload-date-' + oldId).innerHTML.trim();
        tempAttachment.comment = document.getElementById('contact-attachment-comment-' + oldId).innerHTML.trim();

        oldAttachments.push(tempAttachment);
    }

    var newAttachmentElements = document.getElementsByClassName('new-contact-attachment');
    for(var newI = 0; newI < newAttachmentElements.length; newI++) {
        var newId = newAttachmentElements[newI].id;
        newId = newId.split('-')[3];

        var newTempAttachment = new Attachment();

        newTempAttachment.name = document.getElementById('new-contact-attachment-file-path-' + newId).innerHTML.trim();
        newTempAttachment.dateUpload = document.getElementById('new-contact-attachment-upload-date-' + newId).innerHTML.trim();
        newTempAttachment.comment = document.getElementById('new-contact-attachment-comment-' + newId).innerHTML.trim();

        newAttachments.push(newTempAttachment);
    }

    contactForm.appendChild(addElementsInHiddenInput('old-attachments', JSON.stringify(oldAttachments)));
    contactForm.appendChild(addElementsInHiddenInput('new-attachments', JSON.stringify(newAttachments)));
}

//----------------------------------PHOTO FUNCTIONS---------------------------

//TODO: fix bug with updating photo

var photoFileInput = document.getElementById('photo-file-input');
var contactPhoto = document.getElementById('contact-photo');
var photoImage = document.getElementById('contact-photo-image');
var popupWindowPhoto = document.getElementById('popup-window-photo');

var savePhotoButton = document.getElementById('save-photo-button');
var cancelPhotoButton = document.getElementById('cancel-photo-button');
var deletePhotoButton = document.getElementById('delete-photo-button');
var isPhotoDeleted = false;

photoImage.onclick = function () {
    photoFileInput.value = '';
    openModal(popupWindowPhoto);
};

cancelPhotoButton.onclick = function () {
    closeModal(popupWindowPhoto);
};

savePhotoButton.onclick = function() {
    isPhotoDeleted = false;
    var reader = new FileReader;
    var image = photoFileInput.files[0];
    reader.onload = function () {
        photoImage.src = this.result;
    };
    if(image) {
        reader.readAsDataURL(image);
        photoFileInput.id = 'uploaded-contact-photo';
        photoFileInput.name = 'upload-photo';
        photoFileInput.style.display = 'none';
        contactPhoto.appendChild(photoFileInput);
        photoFileInput = createPhotoFileInput();
    }
    closeModal(popupWindowPhoto);
};

function createPhotoFileInput() {
    var newPhotoFileInput = document.createElement('input');
    newPhotoFileInput.type = 'file';
    newPhotoFileInput.id = 'photo-file-input';
    newPhotoFileInput.accept = 'image/jpeg,image/png,image/gif';
    return newPhotoFileInput;
}

deletePhotoButton.onclick = function () {
    isPhotoDeleted = true;
    photoImage.src = '/image/default.png';
    closeModal(popupWindowPhoto);
};

function preparePhotoForSubmit() {
    if(isPhotoDeleted) {
        photoImage.src = null;
        document.getElementById("old-contact-photo").value = null;
    }
}

