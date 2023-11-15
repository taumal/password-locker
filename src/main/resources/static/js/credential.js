$(document).ready(() => {
    console.log('hello aliens')
});

function shieldEdit(dom) {
    let domId =$(dom).attr('id');
    let uri = `/shields/json/by-id/${domId}`;

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
}

function folderEdit(dom) {
    let domId =$(dom).attr('id');
    let uri = `/folders/json/by-id/${domId}`

    $.get(uri, function (folder, f) {
        if (f === 'success') {
            $('#folderId').val(folder.id);
            $('#folderName').val(folder.name);
        } else {
            $('#error-toast').toast('show')
        }
        console.log(f)
    })
    $('#folderUpdateModal').modal('show');
}