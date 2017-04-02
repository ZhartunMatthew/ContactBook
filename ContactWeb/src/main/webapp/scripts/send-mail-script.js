var sendMailForm = document.getElementById('send-mail-form');
var sendMailButton = document.getElementById('send-mail-button');
var templateOptionSelect = document.getElementById('template-select');
var emailTextArea = document.getElementById('email-text');
var emailSubjectArea = document.getElementById('email-subject');

function closeModal(modalWindow) {
    modalWindow.style.display = 'none';
}

function openModal(modalWindow) {
    modalWindow.style.display = 'block';
}

sendMailButton.onclick = function () {

    if(!isAnyRecipientExists()) {
        addErrorMessage('Ошибка отправки: ');
        addErrorMessage('У выбранных контаков не был указан email');
        openModal(popupWindowError);
        return;
    }

    if(!checkInputFieldsBeforeSubmit()) {
        openModal(popupWindowError);
        return;
    }

    createHiddenForTemplateIndex();
    sendMailForm.submit();
};

templateOptionSelect.onchange = function () {
    var selectedIndex = templateOptionSelect.selectedIndex;
    var emailText = templateOptionSelect.options[selectedIndex].value;
    emailTextArea.value = emailText;
    emailTextArea.disabled = selectedIndex > 0;
};

function createHiddenForTemplateIndex() {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'selected-template-index';
    input.value = templateOptionSelect.selectedIndex;
    sendMailForm.appendChild(input);
}

//---------------------VALIDATION---------------------------

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

emailTextArea.onkeyup = function () {
    if(templateOptionSelect.selectedIndex == 0) {
        checkInputOnLength(this, 200, true);
    }
};

emailSubjectArea.onkeyup = function () {
    checkInputOnLength(this, 60, true);
};

function checkInputFieldsBeforeSubmit() {
    var isCorrectInput = true;

    if(templateOptionSelect.selectedIndex == 0 && emailTextArea.value.length < 1) {
        isCorrectInput = false;
        addErrorMessage('Если шаблон не выбран, необходимо ввести текст');
    }

    if(emailSubjectArea.value.length < 1) {
        isCorrectInput = false;
        addErrorMessage('Необходимо ввести тему');
    }

    return isCorrectInput;
}

function isAnyRecipientExists() {
    return document.getElementsByClassName('recipient-email').length > 0;

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

function highlightInput(inputElement, isCorrect) {
    if(isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}