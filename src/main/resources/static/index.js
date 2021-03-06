window.onload = () => {
    let canvas = document.getElementById("canvas-el");
    let c = canvas.getContext("2d");

    c.strokeStyle = "black";
    c.lineWidth = 4;

    for (let i = 0; i <= 600; i += 60) {
        c.moveTo(i, 0);
        c.lineTo(i, 600);
        c.stroke();
    }

    for (let j = 0; j <= 600; j += 60) {
        c.moveTo(0, j);
        c.lineTo(600, j);
        c.stroke();
    }

    showAllRooms();
};

$("#add-number-of-point").click(function(event) {
    event.preventDefault();

    let num = document.getElementById("number-of-point").value;

    if (num < 4) {
        alert("Number of point cannot be less than 4. Please, try again.");
    } else {
        let formElements = "";

        for (let i = 0; i < num; i++) {
            formElements += "<span>x" + (i + 1) + ": </span>\n" +
                "<input type=\"number\" class=\"x-arg\">\n" +
                "<br>\n" +
                "<span>y" + (i + 1) + ": </span>\n" +
                "<input type=\"number\" class=\"y-arg\">\n" +
                "<br>\n";
        }

        document.getElementById("form-elements").innerHTML = formElements;
        document.getElementById("form").style.display = "block";
    }
});

$("#add-new-room").click(function (event) {
    event.preventDefault();

    let xValuesInputs = $("input[class='x-arg']");
    let xValues = [].map.call(xValuesInputs, function(xValue) {
        return parseInt(xValue.value, 10);
    });

    let yValuesInputs = $("input[class='y-arg']");
    let yValues = [].map.call(yValuesInputs, function(yValue) {
        return parseInt(yValue.value, 10);
    });

    $.ajax({
        url: "/add",
        type: "POST",
        data: {
            x: xValues,
            y: yValues
        },
        traditional: true
    })
        .done(function(data, textStatus, xhr) {
            if (xhr.status === 200) {
                drawRoom(xValues, yValues, "red");

                showOnBoardRooms();

                showAllRooms();
            }
        })
        .fail(function(data) {
            alert(data.responseText);
        })
});

$(document).on("click", ".del-btn", function(event) {
    event.preventDefault();

    let roomId = $(this).attr("room-id");

    $.ajax({
        url: "/rooms/on-board-upd?roomId=" + roomId + "&value=false",
        type: "PUT"
    })
        .done(function (data) {
            drawRoom(data.x, data.y, "black");

            let btnId = "btn-" + roomId;

            $("#btn-" + roomId).prop("disabled", false);

            showOnBoardRooms();
        })
        .fail(function() {
            alert("Fail!!!");
    });
});

let showOnBoardRooms = () => {
    $.get("/rooms/on-board")
        .done(function(rooms) {
            let onBoardData = "";

            jQuery.each(rooms, function (num, room) {
                onBoardData += "<tr>\n" +
                    "<td>" + room.id + "</td>\n" +
                    "<td><button class=\"del-btn\" room-id=\"" + room.id + "\">Del</button></td>\n" +
                    "</tr>\n";
            });

            $("#on-board-data").html(onBoardData);
        });
};

let drawRoom = (xValues, yValues, color) => {
    let canvas = document.getElementById("canvas-el");
    let c = canvas.getContext("2d");

    c.strokeStyle = color;

    c.beginPath();
    c.moveTo(xValues[0] * 60, yValues[0] * 60);
    for (let i = 1; i < xValues.length; i++){
        c.lineTo(xValues[i] * 60, yValues[i] * 60);
    }

    c.closePath();
    c.stroke();
};

$(document).on("click", ".add-btn", function() {
    let roomId = $(this).attr("room-id");

    $.ajax({
        url: "/rooms/on-board-upd?roomId=" + roomId + "&value=true",
        type: "PUT"
    })
        .done(function (data, textStatus, xhr) {
            drawRoom(data.x, data.y, "red");

            $("#btn-" + roomId).prop("disabled", true);

            showOnBoardRooms();
        })
        .fail(function(data) {
            alert(data.responseText);
        });
});

let disableBtns = () => {
    $.get("/rooms/on-board")
        .done(function(data) {
            jQuery.each(data, function(num, room) {
                $("#btn-" + room.id).prop("disabled", true);
            });
        });
};

$(document).on("click", ".room-del-btn", function() {
    let roomId = $(this).attr("room-id");

    $.ajax({
        url: "/rooms/delete/" + roomId,
        type: "DELETE"
    })
        .done(function(data) {
            drawRoom(data.x, data.y, "black");

            showAllRooms();

            showOnBoardRooms();
        })
        .fail(function() {
            alert("Fail!!!");
        });

});

let showAllRooms = () => {
    $.get("/rooms")
        .done(function(data) {
            let tableBody = "";

            jQuery.each(data, function(num, room) {

                tableBody += "<tr>" +
                    "<td>" + room.id + "</td>\n" +
                    "<td>" + room.numberOfPoint + "</td>\n" +
                    "<td>";

                for (let i = 0; i < room.numberOfPoint; i++) {
                    tableBody += "(" + room.points[i][0] + "; " + room.points[i][1] + ")";

                    if (i !== room.numberOfPoint - 1) {
                        tableBody += ", ";
                    }
                }

                tableBody += "</td>\n" +
                    "<td><button class=\"add-btn\" room-id=\"" + room.id + "\" id=\"btn-" + room.id + "\">Add</button></td>" +
                    "<td><button class=\"room-del-btn\" room-id=\"" + room.id + "\" id=\"del-bth" + room.id + "\">Del</button></td>" +
                    "</tr>";

            });

            $("#rooms-table").html(tableBody);

            disableBtns();
        });
};
