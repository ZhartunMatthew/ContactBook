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
    clearErrorWindow();
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
        fillPhonePopup(number, '');
        isNewPhoneEdit = false;
        openModal(popupWindowPhones);
    }

    var newNumber = newItems[0];
    if(newNumber !== undefined && newNumber !== 0) {
        fillPhonePopup(newNumber, "new-");
        isNewPhoneEdit = true;
        openModal(popupWindowPhones);
    }
};

function fillPhonePopup(id, prefix) {
    var phone = document.getElementById(prefix + 'contact-phone-number-' + id).innerHTML.trim();
    var countryCode = phone.match(/^\+\d+ /)[0];
    document.getElementById('country-code').value = countryCode.substr(1, countryCode.length).trim();
    var operatorCode = phone.match(/\(\d+\)/)[0];
    document.getElementById('operator-code').value = operatorCode.substr(1, operatorCode.length-2);
    var phoneNumber = phone.match(/( )\b\d+\b/)[0];
    document.getElementById('phone-number').value = phoneNumber.trim();

    var typeName = document.getElementById(prefix + 'contact-phone-type-' + id).innerHTML.trim();
    document.getElementById('phone-type').value = typeToVal(typeName);

    document.getElementById('phone-comment').value =
        document.getElementById(prefix + 'contact-phone-comment-' + id).innerHTML.trim();

    editedPhoneId = id;
}

deletePhoneButton.onclick = function () {
    clearErrorWindow();
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
    var commentPre = document.createElement('pre');
    commentPre.id = 'new-contact-phone-comment-' + phone.id;
    commentPre.appendChild(document.createTextNode(phone.comment));
    columnWithPhoneComment.appendChild(commentPre);

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
        applyPhoneChanges(phone, 'new-');
    } else {
        applyPhoneChanges(phone, '');
    }
}

function applyPhoneChanges(phone, prefix) {
    document.getElementById(prefix + 'contact-phone-number-' + phone.id).innerHTML =
        '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

    document.getElementById(prefix + 'contact-phone-type-' + phone.id).innerHTML = valToType(phone.type);
    document.getElementById(prefix + 'contact-phone-comment-' + phone.id).innerHTML = '';
    document.getElementById(prefix + 'contact-phone-comment-' + phone.id).appendChild(document.createTextNode(phone.comment));
}

function preparePhonesForSubmit() {
    contactForm.appendChild(addElementsInHiddenInput('old-phones', JSON.stringify(getPhoneList(''))));
    contactForm.appendChild(addElementsInHiddenInput('new-phones', JSON.stringify(getPhoneList('new-'))));
}

function getPhoneList(prefix) {
    var phones = [];
    var oldPhoneElements = document.getElementsByClassName(prefix + 'contact-phone');
    for(var i = 0; i < oldPhoneElements.length; i++) {
        var id = oldPhoneElements[i].id;
        id = id.split('-');
        id = id[id.length - 1];

        var tempPhone = new Phone();
        if(prefix.length < 1) {
            tempPhone.id = id;
        }

        var fullPhone = document.getElementById(prefix + 'contact-phone-number-' + id).innerHTML.trim();
        var countryCode = fullPhone.match(/^\+\d+ /)[0];
        tempPhone.countryCode = countryCode.substr(1, countryCode.length).trim();
        var operatorCode = fullPhone.match(/\(\d+\)/)[0];
        tempPhone.operatorCode = operatorCode.substr(1, operatorCode.length-2);
        var phoneNumber = fullPhone.match(/( )\b\d+\b/)[0];
        tempPhone.number = phoneNumber.trim();

        var tempType = document.getElementById(prefix + 'contact-phone-type-' + id).innerHTML.trim();
        tempPhone.type = typeToVal(tempType);
        tempPhone.comment = document.getElementById(prefix + 'contact-phone-comment-' + id).innerHTML.trim();

        phones.push(tempPhone);
    }
    return phones;
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
    clearErrorWindow();

    removeAllRedundantSpacesPhone();

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

function removeAllRedundantSpacesPhone() {
    removeRedundantSpaces(phoneComment);
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
    clearErrorWindow();
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
        fillAttachmentPopup(number, '');
        isNewAttachmentEdit = false;
        openModal(popupWindowAttachments);
    }

    var newNumber = newItems[0];
    if(newNumber != undefined && newNumber != 0) {
        fillAttachmentPopup(newNumber, 'new-');
        isNewAttachmentEdit = true;
        openModal(popupWindowAttachments);
    }
};

function fillAttachmentPopup(id, prefix) {
    document.getElementById('attachment-comment').value =
        document.getElementById(prefix + 'contact-attachment-comment-' + id).innerHTML.trim();

    document.getElementById('attachment-name').value =
        document.getElementById(prefix + 'contact-attachment-file-name-' + id).innerHTML.trim().split('\.')[0];

    fileExtension =
        document.getElementById(prefix + 'contact-attachment-file-name-' + id).innerHTML.trim().split('\.')[1];

    editedAttachmentId = id;
    document.getElementById('path-to-file').style.display = 'none';
}

deleteAttachmentButton.onclick = function () {
    clearErrorWindow();
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
    var commentPre = document.createElement('pre');
    commentPre.id = 'new-contact-attachment-comment-' + attachment.id;
    commentPre.appendChild(document.createTextNode(attachment.comment));
    columnWithAttachmentComment.appendChild(commentPre);

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
        applyAttachmentChanges(attachment, 'new-');
    } else {
        applyAttachmentChanges(attachment, '');
    }
}

function applyAttachmentChanges(attachment, prefix) {
    document.getElementById(prefix + 'contact-attachment-file-name-' + attachment.id).innerHTML = attachment.fileName;
    document.getElementById(prefix + 'contact-attachment-comment-' + attachment.id).innerHTML = '';
    document.getElementById(prefix + 'contact-attachment-comment-' + attachment.id).appendChild(document.createTextNode(attachment.comment));
}

function prepareAttachmentForSubmit() {
    contactForm.appendChild(addElementsInHiddenInput('old-attachments', JSON.stringify(getAttachmentList(''))));
    contactForm.appendChild(addElementsInHiddenInput('new-attachments', JSON.stringify(getAttachmentList('new-'))));
}

function getAttachmentList(prefix) {
    var attachments = [];
    var attachmentElements = document.getElementsByClassName(prefix + 'contact-attachment');
    for(var i = 0; i < attachmentElements.length; i++) {
        var id = attachmentElements[i].id;
        id = id.split('-');
        id = id[id.length - 1];

        var tempAttachment = new Attachment();
        if(prefix.length < 1) {
            tempAttachment.id = id;
        }

        tempAttachment.fileName = document.getElementById(prefix + 'contact-attachment-file-name-' + id).innerHTML.trim();
        if(prefix.length > 1) {
            tempAttachment.dateUpload = null;
        } else {
            tempAttachment.dateUpload =
                changeDateFormat(document.getElementById(prefix + 'contact-attachment-upload-date-' + id).innerHTML.trim());
        }
        tempAttachment.comment = document.getElementById(prefix + 'contact-attachment-comment-' + id).innerHTML.trim();
        attachments.push(tempAttachment);
    }
    return attachments;
}

function changeDateFormat(dateStr) {
    dateStr = dateStr.split('.');
    return dateStr[2] + '-' + dateStr[1] + '-' + dateStr[0];
}

var prevPath = '';
function checkAttachmentInputBeforeSave(isRequired) {
    var isInputCorrect = true;
    clearErrorWindow();

    removeAllRedundantSpacesAttachment();

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

    if(!checkAttachmentName(attachmentNameInput, 80, true)) {
        isInputCorrect = false;
        addErrorMessage('Имя файла не указано или указано некорректно');
    }
    if(!checkTextOnLength(attachmentCommentInput, 150, false)) {
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

function removeAllRedundantSpacesAttachment() {
    removeRedundantSpaces(attachmentNameInput);
    removeRedundantSpaces(attachmentCommentInput);
}

//----------------------------------ATTACHMENT VALIDATION---------------------

var attachmentFileInput = document.getElementById('attachment-path');

var attachmentNameInput = document.getElementById('attachment-name');
attachmentNameInput.onkeyup = function () {
    checkAttachmentNameInput(this, 80, true);
};

var attachmentCommentInput = document.getElementById('attachment-comment');
attachmentCommentInput.onkeyup = function () {
    checkAttachmentComment(this, 150, false);
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
        if(image.type !== 'image/jpeg' && image.type !== 'image/png' && image.type !== 'image/gif') {
            addErrorMessage("Фыбранный файл не является картинкой");
            openModal(popupWindowError);
            return;
        }
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

function clearErrorWindow() {
    popupErrorMessage.innerHTML = null;
}

function checkInputFieldsBeforeSubmit() {
    popupErrorMessage.innerHTML = null;
    var isInputCorrect = true;

    removeAllRedundantSpaces();

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
    }
    if(!checkWebsite(website)) {
        isInputCorrect = false;
        addErrorMessage('Вебсайт слишком длинный или введен некорректно');
    }
    if(!checkEmail(email)) {
        isInputCorrect = false;
        addErrorMessage('Email введен некорректно');
    }
    if(!checkJob(job)) {
        isInputCorrect = false;
        addErrorMessage('Работа введена некорректно');
    }
    if(!checkOnTextWithDigits(postcode, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Индекс введен некорректно');
    }
    if(!checkOnTextWithDigits(city, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Город введен некорректно');
    }
    if(!checkOnTextWithDigits(street, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Улица введена некорректно');
    }
    if(!checkOnTextWithDigits(house, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Дом введен некорректно');
    }
    if(!checkOnTextWithDigits(flat, 8, false)) {
        isInputCorrect = false;
        addErrorMessage('Квартира введена некорректно');
    }

    return isInputCorrect;
}

function removeAllRedundantSpaces() {
    removeRedundantSpaces(firstName);
    removeRedundantSpaces(lastName);
    removeRedundantSpaces(patronymic);
    removeRedundantSpaces(job);
    removeRedundantSpaces(postcode);
    removeRedundantSpaces(city);
    removeRedundantSpaces(street);
    removeRedundantSpaces(house);
    removeRedundantSpaces(flat);
}

function removeRedundantSpaces(input) {
    input.value = input.value.replace(/ +/g, " ");
    input.value = input.value.trim();
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
    checkDateInput(false);
};
month.onkeyup = function () {
    checkDateInput(false);
};
year.onkeyup = function () {
    checkDateInput(false);
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
    checkJobInput(this);
};

var postcode = document.getElementById('postcode');
postcode.onkeyup = function () {
    checkInputOnTextWithDigits(this, 8, false);
};

var city = document.getElementById('city');
city.onkeyup = function () {
    checkInputOnTextWithDigits(this, 30, false);
};

var street = document.getElementById('street');
street.onkeyup = function () {
    checkInputOnTextWithDigits(this, 30, false);
};

var house = document.getElementById('house');
house.onkeyup = function () {
    checkInputOnTextWithDigits(this, 8, false);
};

var flat = document.getElementById('flat');
flat.onkeyup = function () {
    checkInputOnTextWithDigits(this, 8, false);
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

function checkInputOnTextWithDigits(inputElement, maxLength, isRequired) {
    if(checkOnTextWithDigits(inputElement, maxLength, isRequired)) {
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

function checkOnTextWithDigits(inputElement, maxLength, isRequired) {
    var length = inputElement.value.trim().length;
    if(isRequired && length < 1) {
        return false;
    }

    if(isOnlyLettersDigitsSpaces(inputElement.value) && length < maxLength) {
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
    var letters = letters_ru + letters_en + '-' + ' ';
    for (var i = 0; i < value.length; i++) {
        if (letters.indexOf(value.toLowerCase().charAt(i)) === -1) {
            return false;
        }
    }
    return true;
}

function isOnlyLettersAndChars(value, chars) {
    var letters = letters_ru + letters_en + digits + '-' + ' ' + chars;
    for (var i = 0; i < value.length; i++) {
        if (letters.indexOf(value.toLowerCase().charAt(i)) === -1) {
            return false;
        }
    }
    return true;
}

function isOnlyLettersDigitsSpaces(value) {
    var letters = letters_ru + letters_en + '-' + digits + ' ';
    for (var i = 0; i < value.length; i++) {
        if (letters.indexOf(value.toLowerCase().charAt(i)) === -1) {
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
        if (digits.indexOf(value.toLowerCase().charAt(i)) === -1) {
            return false;
        }
    }
    return true;
}

function checkDateInput(isFinalCheck) {
    if(arguments.length > 0) {
        if(!isFinalCheck) {
            clearErrorWindow();
        }
    }
    var dayCorrect, monthCorrect, yearCorrect;
    if(day.value.length > 0 || month.value.length > 0 || year.value.length > 0) {
        var currentDate = new Date();
        dayCorrect = checkInputOnDigits(day, 1, 31, true);
        monthCorrect = checkInputOnDigits(month, 1, 12, true);
        yearCorrect = checkInputOnDigits(year, 1917, currentDate.getFullYear(), true);
        if(!dayCorrect || !monthCorrect || !yearCorrect) {
            addErrorMessage("Неверный формат ввода даты");
            return false
        }

        var realDate = new Date(year.value, month.value - 1, day.value);
        var realYear = realDate.getFullYear();
        var realMonth = realDate.getMonth();
        var realDay = realDate.getDate();

        if(realDate.valueOf() > currentDate.valueOf()) {
            addErrorMessage("Такая дата еще не наступила");
            return false;
        }

        if(day.value == realDay && month.value - 1 == realMonth && year.value == realYear) {
            return true;
        } else {
            addErrorMessage("Введенная дата не существует");
            return false;
        }
    } else {
        highlightInput(day, true);
        highlightInput(month, true);
        highlightInput(year, true);
        return true;
    }
}

function checkJobInput(inputElement) {
    if(checkJob(inputElement)) {
        highlightInput(inputElement, true);
    } else {
        highlightInput(inputElement, false);
    }
}

function checkJob(inputElement) {
    if (inputElement.value.length === 0) {
        return true;
    }
    if (inputElement.value.length > 45) {
        return false;
    }
    if (isOnlyLettersAndChars(inputElement.value, '(),.:;')) {
        return true;
    } else {
        return false;
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
    var regex = /^[\w]{1}[\w\.-]*@[\w]+\.[a-z]{2,4}$/i;
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
    var regex = /^[\w-]*\.[a-z]{2,4}$/i;
    return regex.test(inputElement.value);
}

function highlightInput(inputElement, isCorrect) {
    if(isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}