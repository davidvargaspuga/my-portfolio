// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// checks if text area is empty
function emptyBox(){
    if (document.getElementById('comments-box').value == '') {
        alert('Enter a valid comment.'); 
        return false;  // stop submission until textbox is not ''
    }
    else return true;
}

// External Citation:
// https://www.w3schools.com/howto/howto_js_scroll_to_top.asp
var toTopButton = document.getElementById("to-top"); 
window.onscroll = function() {scrollFunction()};
function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    document.getElementById("to-top").style.display = "block";
  } else {
    document.getElementById("to-top").style.display = "none";
  }
}
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}


// External Citation:
// https://www.quora.com/How-can-I-make-a-comment-box-in-HTML
// This code gets comment data from the /data backend path and updates the #display-comments element
// with comment data.
fetch('/data').then(response => response.json()).then((data) => {
    var displayComments = document.getElementById('display-comments');
    var comment;
    var email;

    //retrieves comment and email and displays
    for (var i = 0; i < data.comments.length; i++) {
        comment = data.comments[i];
        email = data.email[i];
        displayComments.innerHTML += "" + email + ": "+ comment + "<br/>";
    }
});

// If user is logged in, display website
// Otherwise, ask to login.
// isLoggedIn: boolean of false or string of login here message
fetch('/login').then(response => response.json()).then((isLoggedIn) => {
    if(isLoggedIn) document.getElementById("comment-form").style.display = "block";
    else document.getElementById("login-message").innerHTML = isLoggedIn;
});


