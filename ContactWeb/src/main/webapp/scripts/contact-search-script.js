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
    toDay.value = '';
    toMonth.value = '';
    toYear.value = '';
    fromDay.value = '';
    fromMonth.value = '';
    fromYear.value = '';
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
    highlightInput(toDay, true);
    highlightInput(toMonth, true);
    highlightInput(toYear, true);
    highlightInput(fromDay, true);
    highlightInput(fromMonth, true);
    highlightInput(fromYear, true);
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
    popupErrorMessage.innerHTML = null;
    var isInputCorrect = true;

    removeAllRedundantSpaces();

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
    if(!checkDateInput(fromDay, fromMonth, fromYear)) {
        isInputCorrect = false;
        addErrorMessage('Ошибка ввода даты "ОТ"');
    }
    if(!checkDateInput(toDay, toMonth, toYear)) {
        isInputCorrect = false;
        addErrorMessage('Ошибка ввода даты "ДО"');
    }
    if(!checkDateOrder()) {
        isInputCorrect = false;
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

function isAnyFieldEntered() {
    if(!isEmpty(firstName)) {
        return true;
    }
    if(!isEmpty(lastName)) {
        return true;
    }
    if(!isEmpty(patronymic)) {
        return true;
    }
    if(!isEmpty(toDay)) {
        return true;
    }
    if(!isEmpty(fromDay)) {
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
    if(!isEmpty(postcode)) {
        return true;
    }
    var country = document.getElementById('country');
    if(country.selectedIndex > 0) {
        return true;
    }

    if(!isEmpty(city)) {
        return true;
    }
    if(!isEmpty(street)) {
        return true;
    }
    if(!isEmpty(house)) {
        return true;
    }
    if(!isEmpty(flat)) {
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

var fromDay = document.getElementById('from-day');
var fromMonth = document.getElementById('from-month');
var fromYear = document.getElementById('from-year');

fromDay.onkeyup = function () {
    checkDateInput(fromDay, fromMonth, fromYear);
};
fromMonth.onkeyup = function () {
    checkDateInput(fromDay, fromMonth, fromYear);
};
fromYear.onkeyup = function () {
    checkDateInput(fromDay, fromMonth, fromYear);
};

var toDay = document.getElementById('to-day');
var toMonth = document.getElementById('to-month');
var toYear = document.getElementById('to-year');

toDay.onkeyup = function () {
    checkDateInput(toDay, toMonth, toYear);
};
toMonth.onkeyup = function () {
    checkDateInput(toDay, toMonth, toYear);
};
toYear.onkeyup = function () {
    checkDateInput(toDay, toMonth, toYear);
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
    if(isRequired && isEmpty(inputElement)) {
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
    if(isRequired && isEmpty(inputElement)) {
        return false;
    }

    if(isOnlyLettersDigitsSpaces(inputElement.value) && length < maxLength) {
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
    if(isRequired && isEmpty(inputElement)) {
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
    if(isRequired && isEmpty(inputElement)) {
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

function checkDateInput(day, month, year) {
    var dayCorrect, monthCorrect, yearCorrect;
    if(!isEmpty(day) || !isEmpty(month) || !isEmpty(year)) {
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

function checkDateOrder() {
    var fromDate = null, toDate = null;
    if(!isEmpty(fromYear) && !isEmpty(fromMonth) && !isEmpty(fromDay)) {
        fromDate = new Date(fromYear.value, fromMonth.value, fromDay.value);
    }

    if(!isEmpty(toYear) && !isEmpty(toMonth) && !isEmpty(toDay)) {
        toDate = new Date(toYear.value, toMonth.value, toDay.value);
    }

    if(fromDate !== null && toDate !== null && fromDate.valueOf() > toDate.valueOf()) {
        addErrorMessage("Даты не могут идти в таком порядке");
        return false;
    }
    return true;
}

function isEmpty(input) {
    return input.value.trim().length === 0;
}

function isOnlyLettersDigitsSpaces(value) {
    var letters = letters_ru + letters_en + '-' + digits + ' ';
    for (var i = 0; i < value.length; i++) {
        if (letters.indexOf(value.toLowerCase().charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}


function highlightInput(inputElement, isCorrect) {
    if(isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}