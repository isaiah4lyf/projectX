var InititalPosts = [];
var newPost = [];
function GetAllPost() {
    projectXWebApp.projectXLocalService.GetAllPosts(GetAllPostResults);
}
function GetAllPostResults(results) {
    var PostDiv = document.getElementById("Posts");
    InititalPosts = results;
    for (var i = 0; i < 4; i++) {
        PostDiv.innerHTML += "<a href='ViewPost.aspx?dwfsgdrtfyhtd=" + InititalPosts[i] + "'><div>post</div></a><br/>";
    }
    for (var i = 4; i < InititalPosts.length; i++) {
        newPost.push(InititalPosts[i]);
    }
}
function CheckForPostUpdates() {
    projectXWebApp.projectXLocalService.GetAllPosts(CheckForPostUpdatesResults);
}
function CheckForPostUpdatesResults(results) {
    if (results.length > InititalPosts.length) {
        console.log(results.length - InititalPosts.length);
    }
}
function CheckScroll() {
    var limit = document.body.offsetHeight - window.innerHeight;
    var scrollPosition = window.scrollY - 16;
    if (limit < 0) {
        var PostDiv = document.getElementById("Posts");
        if (newPost.length > 0) {
            var id = newPost.shift()
            PostDiv.innerHTML += "<a href='ViewPost.aspx?dwfsgdrtfyhtd=" + id + "'><div>post</div></a><br/>";
        }
    }
    if (limit > 0 && scrollPosition >= limit) {
        var PostDiv = document.getElementById("Posts");
        if (newPost.length > 0) {
            var id = newPost.shift()
            PostDiv.innerHTML += "<a href='ViewPost.aspx?dwfsgdrtfyhtd=" + id + "'><div>post</div></a><br/>";
        }
    }
}
setInterval(CheckScroll, 100);
setInterval(CheckForPostUpdates, 1000);
window.onload = GetAllPost;