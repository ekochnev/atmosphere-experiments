<html>
<%--<%--%>
    <%--HttpSession session1 = request.getSession(true);--%>
    <%--session1.invalidate();--%>
<%--%>--%>
<head>
    <title>Tic Tac Toe Example 1</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/tictactoe.css">
    <link rel="stylesheet" type="text/css" href="resources/block-unblock.css">

    <script type="text/javascript" src="jquery/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="jquery/jquery.form.js"></script>
    <script type="text/javascript" src="jquery/jquery.atmosphere.js"></script>
    <script type="text/javascript" src="jquery/jquery.cookie.js"></script>

    <script type="text/javascript">

        $(document).ready(function() {

            alert(document.cookie);
            alert($.cookie("JSESSIONID"));


            var myLatitude = 0;
            var myLongitude = 0;

            var connectedEndpoint;
            var callbackAdded = false;
            var detectedTransport = null;
            var baseUrl = '/tictactoe/restapi/game'
            //var turnUrl = '/tictactoe/restapi/game/post/turn'
            //var baseUrl = '/tictactoe/'

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(successFunction, errorFunction);
            }

            function successFunction(position) {
                myLatitude = position.coords.latitude;
                myLongitude = position.coords.longitude;

                connect();
            }

            function errorFunction(position) {
                connect();
            }

            function chImg(args) {
                //var test1 = eval('(' + '{"win": "-1", "board": ["10","0","0","0","0","0","0","0","0"],"turn": 1 }' + ')');
                //alert('test1: ' + test1);

                //alert('args: ' + args);

                if (args == null) {
                    return;
                }
                if (args == "") {
                    return;
                }

                var data = eval('(' + args + ')');
                //alert('data: ' + data);

                if (data == null) {
                    //connect();
                    return;
                }

                //alert('1');
                for (i = 0; i < 9; i++) {
                    //alert(i);
                    //alert(data.board[i]);
                    // 0 is blank, 10 is x, 1 is o
                    document.getElementById("img" + i).src = "resources/" + data.board[i] + ".gif";
                    //document.getElementById("img" + i).src = "resources/" + 10 + ".gif";
                }

                //alert('2');
                var statusMsg;
                // -1 is unfinished, 0 is tie, 1 is X win, 2 is Y win
                if (data.win == 0) {
                    statusMsg = "It's a tie!";
                } else if (data.win == 1) {
                    statusMsg = "X wins!";
                } else if (data.win == 2) {
                    statusMsg = "O wins!";
                } else if (data.win == -1 && data.turn == 10) {
                    statusMsg = "It's X's Turn";
                } else if (data.win == -1 && data.turn == 1) {
                    statusMsg = "It's O's Turn";
                } else {
                    statusMsg = "That's odd, it shouldn't get here";
                }

                if (data.win != -1) {
                    statusMsg = statusMsg + '<br><a href="ttt1.jsp">Restart the game</a>';
                }

                // And write the status message out here -
                document.getElementById("gstatus").innerHTML = statusMsg;

            }

            function chState(args) {

                alert('args: ' + args);

                if (args == null) {
                    return;
                }
                if (args == "") {
                    return;
                }

                var data = eval('(' + args + ')');
                //alert('data: ' + data);

                if (data == null) {
                    return;
                }

//                if (data.mark != 'GameState') {
//                    return;
//                }

//                var turn = document.getElementById("turn").value;

                if (data.turn == data.yourSide) {
                    $("#LockPane").get(0).className = 'LockOff';
                } else {
                    $("#LockPane").get(0).className = 'LockOff';
                    //alert("You are blocked. Please wait your turn. Please wait for move the player.");
                }
            }

            function getKeyCode(ev) {
                if (window.event) return window.event.keyCode;
                return ev.keyCode;
            }

            function getElementById() {
                return document.getElementById(arguments[0]);
            }

            function getTransport(t) {
                transport = t.options[t.selectedIndex].value;
                if (transport == 'autodetect') {
                    transport = 'websocket';
                }

                return false;
            }

            function subscribe() {
                // jquery.atmosphere.response
                function callback(response) {
                    // Websocket events.
                    $.atmosphere.log('info', ["response.state: " + response.state]);
                    $.atmosphere.log('info', ["response.transport: " + response.transport]);
                    $.atmosphere.log('info', ["response.status: " + response.status]);
                    $.atmosphere.log('info', ["response.myLatitude: " + myLatitude]);
                    $.atmosphere.log('info', ["response.myLongitude: " + myLongitude]);

                    detectedTransport = response.transport;
                    if (response.transport != 'polling' && response.state != 'connected' && response.state != 'closed') {
                        $.atmosphere.log('info', ["response.responseBody: " + response.responseBody]);
                        if (response.status == 200) {
                            var data = response.responseBody;
                            chState(data);
                            chImg(data);
                        }
                    }
                }

                var location = baseUrl;

                var header = {
                            "myLatitude": myLatitude.toString(),
                            "myLongitude": myLongitude.toString()
                            };

                $.atmosphere.subscribe(location, !callbackAdded ? callback : null,
                        $.atmosphere.request = {
                            attachHeadersAsQueryString : true,
                            transport: 'websocket',
                            headers: header
                        });
                connectedEndpoint = $.atmosphere.response;
                callbackAdded = true;
            }

            function unsubscribe(){
                try {
                    $.atmosphere.unsubscribe();
                } catch (e) {
                    alert(e);
                }
            }

            function connect() {
                //alert("connect");
                unsubscribe();
                //alert("unsubscribe");
                subscribe();
                //alert("subscribe");

//                connectedEndpoint.push(baseUrl,
//                        null,
//                        $.atmosphere.request = {data: 'action=init'});
            }

            getElementById('img0').onclick = function(event) {
                //alert('img0.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }
                var turnUrl = baseUrl + "/post/turn/0"

                //$.get(turnUrl, null, null);
                $.post(turnUrl, null, null);

//                connectedEndpoint.push(turnUrl,
//                        null,
//                        $.atmosphere.request = {data: 'cell=' + 0,
//                        url: turnUrl});

                return false;
            }

            getElementById('img1').onclick = function(event) {
                //alert('img1.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }
                var turnUrl = baseUrl + "/post/turn/1"

//                connectedEndpoint.push(turnUrl,
//                        null,
//                        $.atmosphere.request = {data: 'cell=' + 1,
//                        url: turnUrl});
                $.ajax({
                    async: true,
                    type: 'POST',
                    url: turnUrl,
                    headers: {
                        "Cookie": "JSESSIONID=" + $.cookie("JSESSIONID"),
                        "MY_TEST_HEADER" : "MY_TEST_HEADER"
                    }
                });

                return false;
            }

            getElementById('img2').onclick = function(event) {
                //alert('img2.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/2"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 2,
                        url: turnUrl});

                return false;
            }

            getElementById('img3').onclick = function(event) {
                //alert('img3.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/3"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 3,
                        url: turnUrl});

                return false;
            }

            getElementById('img4').onclick = function(event) {
                //alert('img4.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/4"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 4,
                        url: turnUrl});

                return false;
            }

            getElementById('img5').onclick = function(event) {
                //alert('img5.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/5"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 5,
                        url: turnUrl});

                return false;
            }

            getElementById('img6').onclick = function(event) {
                //alert('img6.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/6"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 6,
                        url: turnUrl});

                return false;
            }

            getElementById('img7').onclick = function(event) {
                //alert('img7.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/7"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 7,
                        url: turnUrl});

                return false;
            }

            getElementById('img8').onclick = function(event) {
                //alert('img8.onclick');
                if (detectedTransport == null) {
                    detectedTransport = 'websocket';
                }

                var turnUrl = baseUrl + "/post/turn/8"
                connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 8,
                        url: turnUrl});

                return false;
            }

        });
    </script>
    <style type='text/css'>
        div {
            border: 0px solid black;
        }

        input#phrase {
            width: 30em;
            background-color: #e0f0f0;
        }

        input#topic {
            width: 14em;
            background-color: #e0f0f0;
        }

        div.hidden {
            display: none;
        }

        span.from {
            font-weight: bold;
        }

        span.alert {
            font-style: italic;
        }
    </style>
</head>
<body>

<%--<input id="turn" name="turn" type="hidden" value="<%=request.getAttribute("turn")%>">--%>
JSESSIONID:&nbsp;<%=request.getSession(true).getId()%>
<h1>Tic Tac Toe</h1>
<%--<div id="game" style="display: block; display: none;">--%>
<div id="game">
<table>
    <tr>
        <td id="cell0"><img id="img0" src="resources/0.gif">
        </td>
        <td id="cell1"><img id="img1" src="resources/0.gif">
        </td>
        <td id="cell2"><img id="img2" src="resources/0.gif">
        </td>
    </tr>
    <tr>
        <td id="cell3"><img id="img3" src="resources/0.gif">
        </td>
        <td id="cell4"><img id="img4" src="resources/0.gif">
        </td>
        <td id="cell5"><img id="img5" src="resources/0.gif">
        </td>
    </tr>
    <tr>
        <td id="cell6"><img id="img6" src="resources/0.gif">
        </td>
        <td id="cell7"><img id="img7" src="resources/0.gif">
        </td>
        <td id="cell8"><img id="img8" src="resources/0.gif">
        </td>
    </tr>
</table>
</div>
<h2 id="gstatus">Starting to watch the game.</h2>
<div id="LockPane" class="LockOff"></div>
</body>
</html>