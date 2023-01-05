function getCookieValue(name) 
{
    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'))
    if (match) {
        return match[2]
    }
}

var select = document.getElementById('courses')
var myTableContainer = document.getElementById('availableSections')
select.onchange = function() {
    const xhttp = new XMLHttpRequest();

    // Define a callback function
    xhttp.onload = function() {
        myTableContainer.innerHTML = xhttp.responseText
    }

    // Send a request
    xhttp.open("POST", "getSections");
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("X-CSRFToken", getCookieValue("csrftoken"));
    xhttp.send(JSON.stringify({
        courseTitle: select.value
    }));
}
