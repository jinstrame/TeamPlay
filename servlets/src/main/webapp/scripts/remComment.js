//noinspection JSUnusedGlobalSymbols
function removeComment(page, post, commentator, id) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/remcomment?page='+page+'&post='+post+'&commentator='+commentator+'&id='+id);
    xhr.send();
    xhr.onload = function() {
        alert('delete');
        var rem = document.getElementById(page+post+commentator+id);
        rem.remove();
    }
}