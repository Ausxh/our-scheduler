function getCookieValue(name) 
{
    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'))
    if (match) {
        return match[2]
    }
}

function generateSchedules() 
{
    var scheduleList = document.getElementById('scheduleList')
    const xhttp = new XMLHttpRequest();

    // Define a callback function
    xhttp.onload = function() {
        var response = JSON.parse(xhttp.responseText)

        scheduleList.innerHTML = ""
        for (var i = 0; i < response.length; i++) {
        var div = document.createElement("div")
            for(var j = 0; j < response[i].length; j++) {
                course = response[i][j]
                div.innerHTML += course.subject.title + " " + course.start_time + " " + course.end_time + " " + course.days + "<br>"
            }
            scheduleList.appendChild(div)
        }
    }

    // Send a request
    xhttp.open("GET", "generate");
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("X-CSRFToken", getCookieValue("csrftoken"));
    xhttp.send();

}
