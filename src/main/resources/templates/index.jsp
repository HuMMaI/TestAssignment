<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script>
        window.onload = () => {
            let canvas = document.getElementById("canvas-el");
            let c = canvas.getContext("2d");

            c.strokeStyle = "black";
            c.lineWidth = 4;

            for (let i = 0; i <= 500; i += 50) {
                c.moveTo(i, 0);
                c.lineTo(i, 500);
                c.stroke();
            }

            for (let j = 0; j <= 500; j += 50) {
                c.moveTo(0, j);
                c.lineTo(500, j);
                c.stroke();
            }
        };
    </script>

</head>
<body>

<canvas id="canvas-el" style="margin: 0; width: 500px; height: 500px">

</canvas>

</body>
</html>
