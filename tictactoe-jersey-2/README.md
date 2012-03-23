
Since I have an issue with tictactoe-jersey-1 (please see README.md in the tictactoe-jersey-1)
so I decided as workaround do ajax call separately:

$.ajax({
    async: true,
    type: 'POST',
    url: turnUrl,
});

and publish (update) game state by broadcaster.

Please note that I use @PerSession.
Unfortunately it doesn't work for me too :(
And yes the problem with session.
