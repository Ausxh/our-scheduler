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
            var schedButton = document.createElement("button")
            schedButton.innerHTML = "View"
            schedButton.setAttribute("onclick", "viewSchedule(this)");
            for(var j = 0; j < response[i].length; j++) {
                course = response[i][j]
                div.innerHTML += course.subject.title + " " + course.start_time + " " + course.end_time + " " + course.days + "<br>"
                schedButton.setAttribute("data-value-" + j, course.id);
            }
            scheduleList.appendChild(div)
            div.append(schedButton)
        }
    }

    // Send a request
    xhttp.open("GET", "generate");
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("X-CSRFToken", getCookieValue("csrftoken"));
    xhttp.send();

}

function viewSchedule(button) {
    console.log(JSON.stringify(button.dataset))
    var idList = [];
    for (var course in button.dataset) {
        idList.push(button.dataset[course])
    }

    var url = "/viewSchedule?classes=" + encodeURIComponent(idList.join(","));
    window.location.href = url;
}
