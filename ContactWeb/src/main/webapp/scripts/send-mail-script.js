var sendMailForm = document.getElementById('send-mail-form');
var sendMailButton = document.getElementById('send-mail-button');
var templateOptionSelect = document.getElementById('template-select');
var emailTextArea = document.getElementById('email-text');

sendMailButton.onclick = function () {
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