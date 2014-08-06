var textarea = $('#comment');
$('#postBtn').on('click', function () {
    var text = textarea.val();
    $.post('/comment', {comment : text}).then(function() {
        textarea.val('');
    }).fail(function() {
        alert('failed to post a comment');
    });
});
