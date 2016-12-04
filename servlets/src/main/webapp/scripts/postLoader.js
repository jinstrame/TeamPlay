//noinspection JSUnusedGlobalSymbols
function loadPosts() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/loadposts');
    xhr.onload = function() {
        var div = document.createElement('div');
        div.className = 'row';
        div.innerHTML = this.responseText;
        var rem = document.getElementById('loadPostsButton');
        rem.remove();

        var parent = document.getElementById('content_block');
        parent.appendChild(div)
    };
    xhr.send();
}