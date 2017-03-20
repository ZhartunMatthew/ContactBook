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

function PhoneAction() {
    var phoneAction = {
        oldPhone : new Phone(),
        newPhone : new Phone(),
        action : 0              //1 - adding, 2 - deleting, 3 - editing
    };
    return phoneAction;
}

var contactPhones = document.getElementById('contact-phones');
var popupWindowPhones = document.getElementById('popup-window-phone');

var addPhoneButton = document.getElementById('add-phone-button');
var deletePhoneButton = document.getElementById('delete-phone-button');
var editPhoneButton = document.getElementById('edit-checked-phone-button');
var submitPhoneButton = document.getElementById('popup-submit-phone-button');
var cancelPhoneButton = document.getElementById('popup-cancel-phone-button');

var isNewPhone = false;
var phoneCount = getLastPhoneId();

var editedPhoneId = 0;

function closeModal(modalWindow) {
    modalWindow.style.display = 'none';
}

function openModal(modalWindow) {
    modalWindow.style.display = 'block';
}

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
    if(number === undefined) {
        alert('Nothing to edit');
        return;
    }
    editedPhoneId = number;

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

    openModal(popupWindowPhones);
};

deletePhoneButton.onclick = function () {
    var allPhonesForDelete = getCheckedItems('phone-check');
    deletePhones(allPhonesForDelete);
}

cancelPhoneButton.onclick = function () {
    closeModal(popupWindowPhones);
};

submitPhoneButton.onclick = function () {
    if(isNewPhone) {
        createNewPhone();
    } else {
        editPhone(editedPhoneId);
    }
    closeModal(popupWindowPhones);
};

function createNewPhone() {
    phoneCount++;
    var phone = new Phone();

    phone.id = phoneCount;
    phone.countryCode = document.getElementById('country-code').value.trim();
    phone.operatorCode = document.getElementById('operator-code').value.trim();
    phone.number = document.getElementById('phone-number').value.trim();
    phone.type = document.getElementById('phone-type').value.trim();
    phone.comment = document.getElementById('phone-comment').value.trim();

    var oneRow = document.createElement('div');
    oneRow.className = 'one-row';
    oneRow.id = 'contact-phone-' + phone.id;

    var label = document.createElement('label');
    label.htmlFor = 'phone-check-' + phone.id;

    var columnWithCheckBox = document.createElement('div');
    columnWithCheckBox.className = 'column column-2';
    var input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'phone-check';
    input.id = 'phone-check-' + phone.id;
    input.value = phone.id;
    columnWithCheckBox.appendChild(input);

    var columnWithPhoneNumber = document.createElement('div');
    columnWithPhoneNumber.className = 'column column-3';
    columnWithPhoneNumber.id = 'contact-phone-number-' + phone.id;
    columnWithPhoneNumber.appendChild(document.createTextNode('+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number));

    var columnWithPhoneType = document.createElement('div');
    columnWithPhoneType.className = 'column column-3';
    columnWithPhoneType.id = 'contact-phone-type-' + phone.id;
    columnWithPhoneType.appendChild(document.createTextNode(phone.type));

    var columnWithPhoneComment = document.createElement('div');
    columnWithPhoneComment.className = 'column column-x';
    columnWithPhoneComment.id = 'contact-phone-comment-' + phone.id;
    columnWithPhoneComment.appendChild(document.createTextNode(phone.comment));

    label.appendChild(columnWithCheckBox);
    label.appendChild(columnWithPhoneNumber);
    label.appendChild(columnWithPhoneType);
    label.appendChild(columnWithPhoneComment);

    oneRow.appendChild(label);

    contactPhones.appendChild(oneRow);
}

function deletePhones(phoneNumbers) {

    for(var i = 0; i < phoneNumbers.length; i++) {
        var phone = new Phone();
        phone.id = phoneNumbers[i];
        alert(phone.id);
        phone.countryCode = document.getElementById('country-code').value.trim();
        phone.operatorCode = document.getElementById('operator-code').value.trim();
        phone.number = document.getElementById('phone-number').value.trim();
        phone.type = document.getElementById('phone-type').value.trim();
        phone.comment = document.getElementById('phone-comment').value.trim();

        var phoneForDelete = document.getElementById('contact-phone-' + phoneNumbers[i]);
        phoneForDelete.parentNode.removeChild(phoneForDelete);
    }

}

function editPhone(phoneID) {
    var phone = new Phone();

    phone.id = editedPhoneId;
    phone.countryCode = document.getElementById('country-code').value.trim();
    phone.operatorCode = document.getElementById('operator-code').value.trim();
    phone.number = document.getElementById('phone-number').value.trim();
    phone.type = document.getElementById('phone-type').value.trim();
    phone.comment = document.getElementById('phone-comment').value.trim();

    document.getElementById('contact-phone-number-' + phoneID).innerHTML =
        '+' + phone.countryCode + ' (' + phone.operatorCode + ') ' + phone.number;

    document.getElementById('contact-phone-type-' + phoneID).innerHTML = phone.type;
    document.getElementById('contact-phone-comment-' + phoneID).innerHTML = phone.comment;
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

function getLastPhoneId() {
    var items = document.getElementsByName('phone-check');
    if(items.length <= 0) {
        return 1;
    }
    return items[items.length-1].value;
}

//------------------------ATTACHMENT FUNCTIONS--------------------------