$(document).ready(() => {
    $('#btnEdit').on('click', function (event) {
        event.preventDefault();
        let uri = $(this).attr('href');
        $.get(uri, function (credential, s) {
            if (s === 'success') {
                $('#credentialId').val(credential.id);
                $('#name').val(credential.name);
                $('#folder').val(credential.folder.id);
                $('#username').val(credential.username);
                $('#password').val(credential.password);
                $('#url').val(credential.url);
                $('#notes').val(credential.notes);
            } else {
                $('#error-toast').toast('show')
            }
            console.log(s)
        })
        $('#typeUpdateModal').modal('show');
    });

    $('#folderEdit').on('click', function (event) {
        event.preventDefault();
        let uri = $(this).attr('href');
        $.get(uri, function (folder, f) {
            console.log(folder.folder)
            console.log(JSON.stringify(folder))
            if (f === 'success') {
                $('#folderId').val(folder.folder.id);
                $('#folderName').val(folder.folder.name);
            } else {
                $('#error-toast').toast('show')
            }
            console.log(f)
        })
        $('#folderUpdateModal').modal('show');
    })
});