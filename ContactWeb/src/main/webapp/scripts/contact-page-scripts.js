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

submitContactButton.onclick = function () {
    if(checkInputFieldsBeforeSubmit() === false) {
        openModal(popupWindowError);
        return;
    }

    preparePhonesForSubmit();
    prepareAttachmentForSubmit();
    preparePhotoForSubmit();

    contactForm.submit();
};


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
        addErrorMessage('Слишком много телефонов отмечено');
        openModal(popupWindowError);
        return;
    }

    if(items.length + newItems.length < 1) {
        addErrorMessage('Нет отмеченных телефонов');
        openModal(popupWindowError);
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
    var allNewPhonesForDelete = getCheckedItems('new-phone-check');

    if(allPhonesForDelete.length + allNewPhonesForDelete.length < 1) {
        addErrorMessage('Нет отмеченных телефонов');
        openModal(popupWindowError);
        return;
    }

    deleteItem(allPhonesForDelete, 'contact-phone-');
    deleteItem(allNewPhonesForDelete, 'new-contact-phone-');
};

cancelPhoneButton.onclick = function () {
    clearHighlightPhones();
    closeModal(popupWindowPhones);
};

submitPhoneButton.onclick = function () {

    if(!checkPhoneInputBeforeSave()) {
        openModal(popupWindowError);
        return;
    }

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
    columnWithCheckBox.className = 'column column-1';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'new-phone-check';
    input.id = 'new-phone-check-' + phone.id;
    input.value = phone.id;
    columnWithCheckBox.appendChild(input);

    var columnWithPhoneNumber = document.createElement('div');
    columnWithPhoneNumber.className = 'column column-4';
    columnWithPhoneNumber.id = 'new-contact-phone-number-' + phone.id;
    columnWithPhoneNumber.appendChild(document.createTextNode('+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number));

    var columnWithPhoneType = document.createElement('div');
    columnWithPhoneType.className = 'column column-4';
    columnWithPhoneType.id = 'new-contact-phone-type-' + phone.id;
    columnWithPhoneType.appendChild(document.createTextNode(valToType(phone.type)));

    var columnWithPhoneComment = document.createElement('div');
    columnWithPhoneComment.className = 'column column-5';
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
//------------------------PHONE VALIDATION-----------------------------

function checkPhoneInputBeforeSave() {
    var isCorrectInput = true;

    if(!checkOnDigits(countryCode, 1, 9999, true)) {
        isCorrectInput = false;
        addErrorMessage("Некорректный ввод кода страны");
    }
    if(!checkOnDigits(operatorCode, 1, 9999, true)) {
        isCorrectInput = false;
        addErrorMessage("Некорректный ввод кода оператора");
    }
    if(!checkOnDigits(theNumber, 10000, 99999999, true)) {
        isCorrectInput = false;
        addErrorMessage("Некорректный ввод номера телефона");
    }
    if(!checkTextOnLength(phoneComment, 150, false)) {
        isCorrectInput = false;
        addErrorMessage("Некорректный ввод кода страны");
    }

    return isCorrectInput;
}

var countryCode = document.getElementById('country-code');
countryCode.onkeyup = function () {
    checkInputOnDigits(this, 1, 9999, true);
};

var operatorCode = document.getElementById('operator-code');
operatorCode.onkeyup = function () {
    checkInputOnDigits(this, 1, 9999, true);
};

var theNumber = document.getElementById('phone-number');
theNumber.onkeyup = function () {
    checkInputOnDigits(this, 10000, 99999999, true);
};

var phoneComment = document.getElementById('phone-comment');
phoneComment.onkeyup = function () {
    checkInputOnLength(this, 150, false);  
};

function clearHighlightPhones() {
    highlightInput(countryCode, true);
    highlightInput(operatorCode, true);
    highlightInput(theNumber, true);
    highlightInput(phoneComment, true);
}

//------------------------ATTACHMENT FUNCTIONS--------------------------

function Attachment() {
    var attachment = {
        id : null,
        filePath : '',
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
var fileExtension;

addAttachmentButton.onclick = function () {
    isNewAttachment = true;

    document.getElementById('attachment-path').value = '';
    document.getElementById('attachment-comment').value = '';
    document.getElementById('attachment-name').value = '';
    document.getElementById('path-to-file').style.display = 'block';

    openModal(popupWindowAttachments);
};

editAttachmentButton.onclick = function () {
    isNewAttachment = false;

    var items = getCheckedItems('attachment-check');
    var newItems = getCheckedItems('new-attachment-check');

    if(items.length + newItems.length > 1) {
        addErrorMessage('Слишком много отмеченных файлов');
        openModal(popupWindowError);
        return;
    }

    if(items.length + newItems.length < 1) {
        addErrorMessage('Нет отмеченных файлов');
        openModal(popupWindowError);
        return;
    }

    var number = items[0];
    if(number !== undefined && number !== 0) {
        document.getElementById('attachment-comment').value =
            document.getElementById('contact-attachment-comment-' + number).innerHTML.trim();

        document.getElementById('attachment-name').value =
            document.getElementById('contact-attachment-file-name-' + number).innerHTML.trim().split('\.')[0];

        fileExtension =
            document.getElementById('contact-attachment-file-name-' + number).innerHTML.trim().split('\.')[1];

        isNewAttachmentEdit = false;
        editedAttachmentId = number;
        document.getElementById('path-to-file').style.display = 'none';

        openModal(popupWindowAttachments);
    }

    var newNumber = newItems[0];
    if(newNumber != undefined && newNumber != 0) {
        document.getElementById('attachment-comment').value =
            document.getElementById('new-contact-attachment-comment-' + newNumber).innerHTML.trim();

        document.getElementById('attachment-name').value =
            document.getElementById('new-contact-attachment-file-name-' + newNumber).innerHTML.trim().split('\.')[0];

        fileExtension =
            document.getElementById('new-contact-attachment-file-name-' + newNumber).innerHTML.trim().split('\.')[1];

        isNewAttachmentEdit = true;
        editedAttachmentId = newNumber;
        document.getElementById('path-to-file').style.display = 'none';

        openModal(popupWindowAttachments);
    }
};

deleteAttachmentButton.onclick = function () {
    var allAttachmentsForDelete = getCheckedItems('attachment-check');
    var allNewAttachmentsForDelete = getCheckedItems('new-attachment-check');

    if(allAttachmentsForDelete.length + allNewAttachmentsForDelete.length < 1) {
        addErrorMessage('Нет отмеченных файлов');
        openModal(popupWindowError);
        return;
    }

    deleteItem(allAttachmentsForDelete, 'contact-attachment-');
    deleteItem(allNewAttachmentsForDelete, 'new-contact-attachment-');
    deleteItem(allNewAttachmentsForDelete, 'new-attachment-input-');
};

cancelAttachmentButton.onclick = function () {
    fileExtension = '';
    clearHighlightAttachments();
    closeModal(popupWindowAttachments);
};

submitAttachmentButton.onclick = function () {
    if(isNewAttachment) {
        if(!checkAttachmentInputBeforeSave(true)) {
            openModal(popupWindowError);
            return;
        }
        createNewAttachment();
    } else {
        if(!checkAttachmentInputBeforeSave(false)) {
            openModal(popupWindowError);
            return;
        }
        editAttachment(editedAttachmentId, isNewAttachmentEdit);
    }
    closeModal(popupWindowAttachments);

};

function getCurrentDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    month = month < 10 ? '0' + month : month;
    var day = date.getDate();
    day = day < 10 ? '0' + day : day;

    return day + '.' + month + '.' + year;
}

function createNewAttachment() {
    lastCreatedAttachmentId++;
    var attachment = new Attachment();

    attachment.id = lastCreatedAttachmentId;
    attachment.dateUpload = getCurrentDate();
    var filePath = document.getElementById('attachment-path').value;
    filePath = filePath.split('\\');
    filePath = filePath[filePath.length-1].split('\.');
    var localFileExtension;
    if(filePath.length > 1 ) {
        localFileExtension = '.' + filePath[filePath.length - 1];
    } else {
        localFileExtension = '';
    }

    attachment.comment = document.getElementById('attachment-comment').value.trim();
    attachment.fileName = document.getElementById('attachment-name').value.trim() + localFileExtension;

    var oneRow = document.createElement('div');
    oneRow.className = 'one-row new-contact-attachment';
    oneRow.id = 'new-contact-attachment-' + attachment.id;

    var label = document.createElement('label');
    label.htmlFor = 'new-attachment-check-' + attachment.id;

    var columnWithCheckBox = document.createElement('div');
    columnWithCheckBox.className = 'column column-1';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'new-attachment-check';
    input.id = 'new-attachment-check-' + attachment.id;
    input.value = attachment.id;
    columnWithCheckBox.appendChild(input);

    var columnWithFileName = document.createElement('div');
    columnWithFileName.className = 'column column-4';
    columnWithFileName.id = 'new-contact-attachment-file-name-' + attachment.id;
    columnWithFileName.appendChild(document.createTextNode(attachment.fileName));

    var columnWithDateUpload = document.createElement('div');
    columnWithDateUpload.className = 'column column-4';
    columnWithDateUpload.id = 'new-contact-attachment-upload-date-' + attachment.id;
    columnWithDateUpload.appendChild(document.createTextNode(attachment.dateUpload));

    var columnWithAttachmentComment = document.createElement('div');
    columnWithAttachmentComment.className = 'column column-5';
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
    fileInputButton.value = '';
    fileInputButton.type = 'file';
    fileInputButton.id = 'attachment-path';

    document.getElementById('path-to-file').appendChild(fileInputButton);
}

function editAttachment(attachmentID, isNewAttachmentEdit) {
    var attachment = new Attachment();

    attachment.id = attachmentID;
    attachment.fileName = document.getElementById('attachment-name').value.trim() + '.' + fileExtension;
    attachment.comment = document.getElementById('attachment-comment').value.trim();

    if(isNewAttachmentEdit) {
        document.getElementById('new-contact-attachment-comment-' + attachmentID).innerHTML = attachment.comment;
        document.getElementById('new-contact-attachment-file-name-' + attachmentID).innerHTML = attachment.fileName;
    } else {
        document.getElementById('contact-attachment-comment-' + attachmentID).innerHTML = attachment.comment;
        document.getElementById('contact-attachment-file-name-' + attachmentID).innerHTML = attachment.fileName;
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

        tempAttachment.fileName = document.getElementById('contact-attachment-file-name-' + oldId).innerHTML.trim();
        tempAttachment.dateUpload = document.getElementById('contact-attachment-upload-date-' + oldId).innerHTML.trim();
        tempAttachment.comment = document.getElementById('contact-attachment-comment-' + oldId).innerHTML.trim();

        oldAttachments.push(tempAttachment);
    }

    var newAttachmentElements = document.getElementsByClassName('new-contact-attachment');
    for(var newI = 0; newI < newAttachmentElements.length; newI++) {
        var newId = newAttachmentElements[newI].id;
        newId = newId.split('-')[3];

        var newTempAttachment = new Attachment();

        newTempAttachment.fileName = document.getElementById('new-contact-attachment-file-name-' + newId).innerHTML.trim();
        newTempAttachment.dateUpload = document.getElementById('new-contact-attachment-upload-date-' + newId).innerHTML.trim();
        newTempAttachment.comment = document.getElementById('new-contact-attachment-comment-' + newId).innerHTML.trim();

        newAttachments.push(newTempAttachment);
    }

    contactForm.appendChild(addElementsInHiddenInput('old-attachments', JSON.stringify(oldAttachments)));
    contactForm.appendChild(addElementsInHiddenInput('new-attachments', JSON.stringify(newAttachments)));
}

var prevPath = '';
function checkAttachmentInputBeforeSave(isRequired) {
    var isInputCorrect = true;

    var MAX_SIZE = 10;
    var SIZE_MB = 1024 * 1024;
    var attachmentFile = attachmentFileInput.files[0];
    if(isRequired && attachmentFile === null || isRequired && attachmentFile === undefined) {
        isInputCorrect = false;
        addErrorMessage('Файл для присоединения не выбран');
    }
    if(attachmentFile !== null && attachmentFile !== undefined) {
        if(attachmentFileInput.files[0].size > MAX_SIZE * SIZE_MB) {
            isInputCorrect = false;
            addErrorMessage('Файл должен быть меньше ' + MAX_SIZE + ' мб');
        }
    }
    if(isRequired) {
        if (prevPath !== '') {
            if (prevPath === document.getElementById('attachment-path').value
                || document.getElementById('attachment-path').value === '') {
                isInputCorrect = false;
                addErrorMessage('Файл для присоединения не выбран или выбран тот же файл');
            }
        }
    }

    if(!checkAttachmentName(attachmentNameInput, 100, true)) {
        isInputCorrect = false;
        addErrorMessage('Имя файла не указано или указано некорректно');
    }
    if(!checkTextOnLength(attachmentCommentInput, 200, false)) {
        isInputCorrect = false;
        addErrorMessage('Комментарий файла не указан или указан некорректно');
    }
    if(isRequired) {
        if (isInputCorrect) {
            prevPath = JSON.parse(JSON.stringify(document.getElementById('attachment-path').value));
        }
    }

    return isInputCorrect;
}

//----------------------------------ATTACHMENT VALIDATION---------------------

var attachmentFileInput = document.getElementById('attachment-path');

var attachmentNameInput = document.getElementById('attachment-name');
attachmentNameInput.onkeyup = function () {
    checkAttachmentNameInput(this, 100, true);
};

var attachmentCommentInput = document.getElementById('attachment-comment');
attachmentCommentInput.onkeyup = function () {
    checkAttachmentComment(this, 10, false);
};

function checkAttachmentNameInput(inputElement, maxLength, isRequired) {
    if(checkAttachmentName(inputElement, maxLength, isRequired)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkAttachmentName(inputElement, maxLength, isRequired) {
    var incorrectLetters = '~`!@#$%^&*+=:;<>,./\'{}"';
    return checkIfContains(inputElement, maxLength, incorrectLetters, isRequired);
}

function checkAttachmentComment(inputElement, maxLength, isRequired) {
    checkInputOnLength(inputElement, maxLength, isRequired);
}

function clearHighlightAttachments() {
    highlightInput(attachmentNameInput, true);
    highlightInput(attachmentCommentInput, true);
}

//----------------------------------PHOTO FUNCTIONS---------------------------

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
        if(!checkPhotoInputBeforeSave()) {
            openModal(popupWindowError);
            return;
        }
        reader.readAsDataURL(image);
        photoFileInput.id = 'uploaded-contact-photo';
        photoFileInput.name = 'upload-photo';
        photoFileInput.style.display = 'none';
        contactPhoto.appendChild(photoFileInput);
        photoFileInput = createPhotoFileInput();
        document.getElementById('popup-photo-label').appendChild(photoFileInput);
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

//------------------------------PHOTO VALIDATION----------------------------------

function checkPhotoInputBeforeSave() {
    var isInputCorrect = true;

    var photo = photoFileInput.files[0];
    var MAX_SIZE = 2;
    var SIZE_MB = 1024 * 1024;

    if(photo) {
        if(photo.size > MAX_SIZE * SIZE_MB) {
            addErrorMessage('Фото не долно быть больше ' + MAX_SIZE + ' мб');
            isInputCorrect = false;
        }
    }
    return isInputCorrect;
}

//------------------------------ACTIONS--------------------------------------------

var popupWindowError = document.getElementById('popup-window-error');
var popupErrorMessage = document.getElementById('error-message');
var popupErrorButton = document.getElementById('popup-window-error-accept');

popupErrorButton.onclick = function () {
    closeModal(popupWindowError);
    popupErrorMessage.innerHTML = null;
};

function addErrorMessage(message) {
    var newError = document.createElement('p');
    newError.innerHTML = message;
    popupErrorMessage.appendChild(newError);
}

function checkInputFieldsBeforeSubmit() {
    var isInputCorrect = true;

    if(!checkOnText(firstName, 30, true)) {
        isInputCorrect = false;
        addErrorMessage('Имя имеет недопустимую длину или содержит недопустимые символы');
    }
    if(!checkOnText(lastName, 30, true)) {
        isInputCorrect = false;
        addErrorMessage('Фамилия имеет недопустимую длину или содержит недопустимые символы');
    }
    if(!checkOnText(patronymic, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Отчество имеет недопустимую длину или содержит недопустимые символы');
    }
    if(!checkDateInput()) {
        isInputCorrect = false;
        addErrorMessage('Дата имеет неверный формат. Дату необходимо ввести верно, либо не вводить');
    }
    if(!checkWebsite(website)) {
        isInputCorrect = false;
        addErrorMessage('Вебсайт слишком длинный или введен некорректно');
    }
    if(!checkEmail(email)) {
        isInputCorrect = false;
        addErrorMessage('Email введен некорректно');
    }
    if(!checkTextOnLength(job, 45, false)) {
        isInputCorrect = false;
        addErrorMessage('Работа введена некорректно');
    }
    if(!checkTextOnLength(postcode, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Индекс введен некорректно');
    }
    if(!checkOnText(city, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Город введен некорректно');
    }
    if(!checkTextOnLength(street, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Улица введена некорректно');
    }
    if(!checkTextOnLength(house, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Дом введен некорректно');
    }
    if(!checkTextOnLength(flat, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Квартира введена некорректно');
    }
    if(!checkCountOfDays(month.value) && isInputCorrect) {
        isInputCorrect = false;
        addErrorMessage('В этом месяце нет такого дня');
    }

    return isInputCorrect;
}

var firstName = document.getElementById('first-name');
firstName.onkeyup = function () {
    checkInputOnText(this, 30, true);
};

var lastName = document.getElementById('last-name');
lastName.onkeyup = function () {
    checkInputOnText(this, 30, true);
};

var patronymic = document.getElementById('patronymic');
patronymic.onkeyup = function () {
    checkInputOnText(this, 30, false);
};

var day = document.getElementById('day');
var month = document.getElementById('month');
var year = document.getElementById('year');

day.onkeyup = function () {
    checkDateInput();
};
month.onkeyup = function () {
    checkDateInput();
};
year.onkeyup = function () {
    checkDateInput();
};

var website = document.getElementById('website');
website.onkeyup = function () {
    checkWebsiteInput(this);
};

var email = document.getElementById('email');
email.onkeyup = function () {
    checkEmailInput(this);
};

var job = document.getElementById('job');
job.onkeyup = function () {
    checkInputOnLength(this, 45, false);
};

var postcode = document.getElementById('postcode');
postcode.onkeyup = function () {
    checkInputOnLength(this, 8, false);
};

var city = document.getElementById('city');
city.onkeyup = function () {
    checkInputOnText(this, 30, false);
};

var street = document.getElementById('street');
street.onkeyup = function () {
    checkInputOnLength(this, 30, false);
};

var house = document.getElementById('house');
house.onkeyup = function () {
    checkInputOnLength(this, 8, false);
};

var flat = document.getElementById('flat');
flat.onkeyup = function () {
    checkInputOnLength(this, 8, false);
};

//------------------------------VALIDATION-----------------------------------------

var letters_ru = "abcdefghijklmnopqrstuvwxyz";
var letters_en = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
var digits = "0123456789";

function checkInputOnText(inputElement, maxLength, isRequired) {
    if(checkOnText(inputElement, maxLength, isRequired)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkOnText(inputElement, maxLength, isRequired) {
    var length = inputElement.value.trim().length;
    if(isRequired && length < 1) {
        return false;
    }

    if(isOnlyLetters(inputElement.value) && length < maxLength) {
        return true;
    } else {
        return false;
    }
}

function checkIfContains(inputElement, maxLength, letters, isRequired) {
    var length = inputElement.value.trim().length;
    if(isRequired && length < 1) {
        return false;
    }

    if(isNotContainsLetters(inputElement.value, letters) && length < maxLength) {
        return true;
    } else {
        return false;
    }
}

function checkInputOnLength(inputElement, maxLength, isRequired) {
    if(checkTextOnLength(inputElement, maxLength, isRequired)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkTextOnLength(inputElement, maxLength, isRequired) {
    var length = inputElement.value.trim().length;
    if(isRequired && length < 1) {
        return false;
    }

    if(length < maxLength) {
        return true;
    } else {
        return false;
    }
}

function isOnlyLetters(value) {
    var letters = letters_ru + letters_en + '-';
    for (var i = 0; i < value.length; i++) {
        if (letters.indexOf(value.toLowerCase().charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

function isNotContainsLetters(value, letters) {
    for (var i = 0; i < letters.length; i++) {
        if (value.indexOf(letters.toLowerCase().charAt(i)) != -1) {
            return false;
        }
    }
    return true;
}

function checkInputOnDigits(inputElement, min, max, isRequired) {
    if(checkOnDigits(inputElement, min, max, isRequired)) {
        highlightInput(inputElement, true);
        return true;
    } else {
        highlightInput(inputElement, false);
        return false;
    }
}

function checkOnDigits(inputElement, min, max, isRequired) {
    var length = inputElement.value.length;
    if(isRequired && length < 1) {
        return false;
    }

    if(!isRequired && length === 0) {
        return true;
    }

    if(isOnlyDigits(inputElement.value)) {
        if(inputElement.value >= min && inputElement.value <= max) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

function isOnlyDigits(value) {
    for (var i = 0; i < value.length; i++) {
        if (digits.indexOf(value.toLowerCase().charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

function checkDateInput() {
    var dayCorrect, monthCorrect, yearCorrect;
    if(day.value.length > 0 || month.value.length > 0 || year.value.length > 0) {
        dayCorrect = checkInputOnDigits(day, 1, 31, true);
        monthCorrect = checkInputOnDigits(month, 1, 12, true);
        yearCorrect = checkInputOnDigits(year, 1917, new Date().getFullYear(), true);
        if(!dayCorrect || !monthCorrect || !yearCorrect) {
            return false
        } else {
            return true;
        }
    } else {
        dayCorrect = checkInputOnDigits(day, 1, 31, false);
        monthCorrect = checkInputOnDigits(month, 1, 12, false);
        yearCorrect = checkInputOnDigits(year, 1917, new Date().getFullYear(), false);
        return true;
    }
}

function checkCountOfDays(x) {
    if(day.value.length > 0 && month.value.length > 0 && year.value.length > 0) {
        var realDaysCount = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        if(year.value % 4 == 0 && year.value % 100 != 0) {
            realDaysCount[1] = 29;
        }
        if(year.value == 2000) {
            realDaysCount[1] = 28;
        }
        return day.value <= realDaysCount[x - 1];
    } else {
        return true;
    }
}

function checkEmailInput(inputElement) {
    if(checkEmail(inputElement)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkEmail(inputElement) {
    if(inputElement.value.length === 0) {
        return true;
    }
    if(inputElement.value.length > 30) {
        return false;
    }
    var regex = /^[\w]{1}[\w\.]*@[\w]+\.[a-z]{2,4}$/i;
    return regex.test(inputElement.value);
}

function checkWebsiteInput(inputElement) {
    if(checkWebsite(inputElement)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkWebsite(inputElement) {
    if(inputElement.value.length === 0) {
        return true;
    }
    if(inputElement.value.length > 30) {
        return false;
    }
    var regex = /^[\w]*\.[a-z]{2,4}$/i;
    return regex.test(inputElement.value);
}

function highlightInput(inputElement, isCorrect) {
    if(isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}