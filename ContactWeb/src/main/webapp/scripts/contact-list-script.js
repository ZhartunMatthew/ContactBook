var contactListForm = document.getElementById('contact-list-form');
var deleteContactButton = document.getElementById('delete-contact-button');

deleteContactButton.onclick = function () {
    contactListForm.action = '/controller?command=delete_contact';
    contactListForm.submit();
};