
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
Now it calls appropriate methods but when I do $.ajax() call -
session doesn't have created broadcaster :(
In debug I have figured out that it use the same session id but different implementation of session.
By subscribing it uses org.atmosphere.container.JettyWebSocketHandler$FakeHttpSession,
after by $.ajax() call it uses org.eclipse.jetty.server.session.HashedSession.

So is it bug, restriction or do I something wrong?
Can we use ajax calls with the Atmosphere framework?

Of course there is workaround - don't use session and add session id (game or broadcaster id) in url (in RESTfull api).
Maybe it is good idea in order to support any other clients and and so on.
But anyway could you please tell can we use additional scope powered by Jersey with Atmosphere framework?