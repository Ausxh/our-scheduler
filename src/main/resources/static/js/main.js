function getCookieValue(name) 
{
    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'))
    if (match) {
        return match[2]
    }
}

var select = document.getElementById('courses')
var input = document.getElementById('test')
var myTableContainer = document.getElementById('availableSections')
select.onchange = function() {
    const xhttp = new XMLHttpRequest();

    // Define a callback function
    xhttp.onload = function() {
        // var response = JSON.parse(xhttp.responseText);
        myTableContainer.innerHTML = xhttp.responseText
        // for (const course in response) {
        //     console.log(course.section)
        // }
        // input.innerHTML = response[0].section;
    }

    // Send a request
    xhttp.open("POST", "getSections");
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("X-CSRFToken", getCookieValue("csrftoken"));
    xhttp.send(JSON.stringify({
        courseTitle: select.value
    }));
}
