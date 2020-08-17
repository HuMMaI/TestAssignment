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

    // let roomPoints = [];
    //
    // for (let i = 0; i < xValues.length; i++) {
    //     roomPoints[i] = [parseInt(xValues[i]), parseInt(yValues[i])];
    // }

    // console.log(typeof(roomPoints));

    $.ajax({
        url: "/add",
        type: "POST",
        data: {
            x: xValues,
            y: yValues
        },
        traditional: true
    })
        .fail(function() {
            alert("Fail!!");
        })
});

