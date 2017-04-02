var contactListForm = document.getElementById('contact-list-form');
var deleteContactButton = document.getElementById('delete-contact-button');
var showEmailPageButton = document.getElementById('show-email-page-button');

deleteContactButton.onclick = function () {
    contactListForm.action = '/controller?command=delete_contact';
    contactListForm.submit();
};

showEmailPageButton.onclick = function () {
    contactListForm.action = '/controller?command=show_email_page';
    contactListForm.submit();
};

//TODO: checkbox validation