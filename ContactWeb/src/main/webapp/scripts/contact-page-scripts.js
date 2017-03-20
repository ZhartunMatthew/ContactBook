var contactPhones = document.getElementById('contact-phones');

var popupWindowPhones = document.getElementById('popup-window-phone');
var addPhoneButton = document.getElementById('add-phone-button');
var submitPhoneButton = document.getElementById('popup-submit-phone-button');
var cancelPhoneButton = document.getElementById('popup-cancel-phone-button');
var addedPhoneCount = 0;

addPhoneButton.onclick = function () {
    popupWindowPhones.style.display = 'block';
};

submitPhoneButton.onclick = function () {
    addedPhoneCount++;
    var phone = {
        id : addedPhoneCount,
        countryCode : document.getElementById('country-code').value,
        operatorCode : document.getElementById('operator-code').value,
        number : document.getElementById('phone-number').value,
        type : document.getElementById('phone-type').value,
        comment : document.getElementById('phone-comment').value
    };
    var oneRow = document.createElement('div');
    oneRow.className = 'one-row';

    var label = document.createElement('label');
    label.htmlFor = 'added-phone-check-' + phone.id;

    var columnWithCheckBox = document.createElement('div');
    columnWithCheckBox.className = 'column column-2';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'phone-check';
    input.id = 'added-phone-check-' + phone.id;
    input.value = phone.id;
    columnWithCheckBox.appendChild(input);

    var columnWithPhoneNumber = document.createElement('div');
    columnWithPhoneNumber.className = 'column column-3';
    columnWithPhoneNumber.appendChild(document.createTextNode('+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number));

    var columnWithPhoneType = document.createElement('div');
    columnWithPhoneType.className = 'column column-3';
    columnWithPhoneType.appendChild(document.createTextNode(phone.type));

    var columnWithPhoneComment = document.createElement('div');
    columnWithPhoneComment.className = 'column column-x';
    columnWithPhoneComment.appendChild(document.createTextNode(phone.comment));

    label.appendChild(columnWithCheckBox);
    label.appendChild(columnWithPhoneNumber);
    label.appendChild(columnWithPhoneType);
    label.appendChild(columnWithPhoneComment);

    oneRow.appendChild(label);

    contactPhones.appendChild(oneRow);

    popupWindowPhones.style.display = 'none';
};

cancelPhoneButton.onclick = function () {
    popupWindowPhones.style.display = 'none';
};

