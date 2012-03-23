Introduction:

The main goal is to design RESTfull api for TicTacToe game.
I have decided to move my app to Jersey since there is not yet "native" support
for COMET (WebSocket, pooling and etc.) in any Web frameworks (I mean MVC bidirectional support).
So I decided to use Jersey as simple controller on server side instead of using
the AtmosphereHandler directly (since a lot of boilerplate code is needed).

API is very simple and should looks like:

/tictactoe/restapi/game/start - starting game (subscribing - create or retrieve a broadcaster, set and publish initial state of game)

/tictactoe/restapi/game/turn/{cell} - player move (send chosen cell)
/tictactoe/restapi/game/turn/{cell}

Problem:

When I push chosen cell by user back it doesn't work properly :(
It calls the TicTacToeGame.startPost() if I do push by POST:

connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 1,
                        method: 'POST',
                        url: turnUrl});

and TicTacToeGame.startGet if I do push by GET:

connectedEndpoint.push(turnUrl,
                        null,
                        $.atmosphere.request = {data: 'cell=' + 0,
                        method: 'GET',
                        url: turnUrl});

So, is it bug or restriction or do I something wrong?

So I decided as workaround do ajax call separately - please see README.md in the tictactoe-jersey-2 example.
