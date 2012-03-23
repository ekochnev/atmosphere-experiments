Introduction:

The main goal is to design RESTfull api for TicTacToe game.
I have decided to move my app to Jersey since there is not yet "native" support
for COMET (WebSocket, pooling and etc.) in any Web frameworks (I mean MVC bidirectional support).
So I decided to use Jersey as simple controller on server side instead of using
the AtmosphereHandler directly (since a lot of boilerplate code is needed).

API is very simple and should looks like:

/tictactoe/restapi/game/get/start - starting game (subscribing - create or retrieve a broadcaster, set and publish initial state of game)

/tictactoe/restapi/game/get/turn/{cell} - player move (send chosen cell)
/tictactoe/restapi/game/post/turn/{cell}

Problem:



