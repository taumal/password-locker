$(document).ready(() => {
    $('#btnEdit').on('click', function (event) {
        event.preventDefault();
        let uri = $(this).attr('href');
        $.get(uri, function (credential, s) {
            if (s === 'success') {
                $('#credentialId').val(credential.id);
                $('#name').val(credential.name);
                $('#username').val(credential.username);
                $('#password').val(credential.password);
            } else {
                $('#error-toast').toast('show')
            }
            console.log(s)
        })
        $('#updateModal').modal('show');
    })
});