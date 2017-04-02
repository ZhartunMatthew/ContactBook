document.getElementById('start-search').onclick = function () {
    if(!checkInputFieldsBeforeSubmit()) {
        openModal(popupWindowError);
        return;
    }
    var searchForm = document.getElementById('contact-search-form');
    searchForm.submit();
};

document.getElementById('clear-search').onclick = function () {
    firstName.value = '';
    lastName.value = '';
    patronymic.value = '';
    day.value = '';
    month.value = '';
    year.value = '';
    document.getElementById('sex').selectedIndex = 0;
    document.getElementById('nationality').selectedIndex = 0;
    document.getElementById('marital-status').selectedIndex = 0;
    postcode.value = '';
    document.getElementById('country').selectedIndex = 0;
    city.value = '';
    street.value = '';
    house.value = '';
    flat.value = '';
    highlightInput(firstName, true);
    highlightInput(lastName, true);
    highlightInput(patronymic, true);
    highlightInput(day, true);
    highlightInput(month, true);
    highlightInput(year, true);
    highlightInput(postcode, true);
    highlightInput(city, true);
    highlightInput(street, true);
    highlightInput(house, true);
    highlightInput(flat, true);
};

function closeModal(modalWindow) {
    modalWindow.style.display = 'none';
}

function openModal(modalWindow) {
    modalWindow.style.display = 'block';
}

//-----------------------------VALIDATION-------------------------

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

    if(!isAnyFieldEntered()) {
        addErrorMessage('Ни одно поле не введено!');
        return false;
    }

    if(!checkOnText(firstName, 30, false)) {
        isInputCorrect = false;
        addErrorMessage('Имя имеет недопустимую длину или содержит недопустимые символы');
    }
    if(!checkOnText(lastName, 30, false)) {
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

    return isInputCorrect;
}

function isAnyFieldEntered() {
    if(firstName.value.trim().length > 0) {
        return true;
    }
    if(lastName.value.trim().length > 0) {
        return true;
    }
    if(patronymic.value.trim().length > 0) {
        return true;
    }
    if(day.value.trim().length > 0) {
        return true;
    }
    var sex = document.getElementById('sex');
    if(sex.selectedIndex > 0) {
        return true;
    }
    var nationality = document.getElementById('nationality');
    if(nationality.selectedIndex > 0) {
        return true;
    }
    var marital = document.getElementById('marital-status');
    if(marital.selectedIndex > 0) {
        return true;
    }
    if(postcode.value.trim().length  > 0) {
        return true;
    }
    var country = document.getElementById('country');
    if(country.selectedIndex > 0) {
        return true;
    }

    if(city.value.trim().length > 0) {
        return true;
    }
    if(street.value.trim().length > 0) {
        return true;
    }
    if(house.value.trim().length  > 0) {
        return true;
    }
    if(flat.value.trim().length > 0) {
        return true;
    }

    return false;
}

var firstName = document.getElementById('first-name');
firstName.onkeyup = function () {
    checkInputOnText(this, 30, false);
};

var lastName = document.getElementById('last-name');
lastName.onkeyup = function () {
    checkInputOnText(this, 30, false);
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
        yearCorrect = checkInputOnDigits(year, 1930, new Date().getFullYear(), true);
        if(!dayCorrect || !monthCorrect || !yearCorrect) {
            return false
        } else {
            return true;
        }
    } else {
        yearCorrect = checkInputOnDigits(day, 1, 31, false);
        monthCorrect = checkInputOnDigits(month, 1, 12, false);
        yearCorrect = checkInputOnDigits(year, 1930, new Date().getFullYear(), false);
        return true;
    }
}

function highlightInput(inputElement, isCorrect) {
    if(isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}