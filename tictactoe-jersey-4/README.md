
Please see this example.

The issue that Atmosphere calls filter(Object originalMessage, Object message) but doesn't call
filter(HttpServletRequest request, HttpServletResponse response, Object message) for both assigned filters (via web.xml and programmatically)
Please see log below.

Log from my console:

15:58:37.793 [qtp984103443-23] DEBUG o.a.container.JettyWebSocketUtil - WebSocket-checkOrigin request /tictactoe/restapi/game/start with origin http://localhost:8080
15:58:37.794 [qtp984103443-23] DEBUG o.a.container.JettyWebSocketUtil - WebSocket-connect request /tictactoe/restapi/game/start with protocol null
15:58:37.813 [qtp984103443-23] DEBUG o.a.websocket.WebSocketProcessor - Atmosphere detected WebSocket: org.atmosphere.container.version.Jetty8WebSocket
15:58:47.974 [qtp984103443-23] INFO  o.a.t.PersonalPerRequestBroadcastFilter - PersonalPerRequestBroadcastFilter.filter(Object originalMessage, Object message)
15:58:47.977 [qtp984103443-23] INFO  o.a.tictactoe4.TicTacToeGame - PerRequestBroadcastFilter.filter(Object originalMessage, Object message)
15:58:49.637 [Atmosphere-BroadcasterConfig-0] DEBUG o.atmosphere.cpr.DefaultBroadcaster - Broadcaster game doesn't have any associated resource
15:58:49.640 [qtp984103443-23] DEBUG o.atmosphere.jersey.AtmosphereFilter - Linked HttpServletRequest org.atmosphere.cpr.AtmosphereRequest@7d6c848f with ContainerResponse com.sun.jersey.spi.container.ContainerResponse@5f73089d
15:58:49.648 [qtp984103443-23] DEBUG o.a.container.JettyWebSocketUtil - Suspending response: org.atmosphere.cpr.AtmosphereResponse@3bdd6a67
###15:59:03.551 [qtp984103443-21] INFO  o.a.t.PersonalPerRequestBroadcastFilter - PersonalPerRequestBroadcastFilter.filter(Object originalMessage, Object message)
###15:59:03.553 [qtp984103443-21] INFO  o.a.tictactoe4.TicTacToeGame - PerRequestBroadcastFilter.filter(Object originalMessage, Object message)
15:59:04.959 [Atmosphere-BroadcasterConfig-0] DEBUG o.atmosphere.cpr.DefaultBroadcaster - Broadcaster game doesn't have any associated resource
15:59:04.960 [qtp984103443-21] DEBUG o.atmosphere.jersey.AtmosphereFilter - Linked HttpServletRequest org.atmosphere.cpr.AtmosphereRequest@7393e1fc with ContainerResponse com.sun.jersey.spi.container.ContainerResponse@71d9a2ab
15:59:04.962 [qtp984103443-21] DEBUG o.a.container.JettyWebSocketUtil - Suspending response: org.atmosphere.cpr.AtmosphereResponse@79095fd7
###16:01:56.021 [qtp984103443-22] INFO  o.a.t.PersonalPerRequestBroadcastFilter - PersonalPerRequestBroadcastFilter.filter(Object originalMessage, Object message)
###16:01:56.022 [qtp984103443-22] INFO  o.a.tictactoe4.TicTacToeGame - PerRequestBroadcastFilter.filter(Object originalMessage, Object message)
16:01:57.585 [Atmosphere-BroadcasterConfig-0] DEBUG o.atmosphere.cpr.DefaultBroadcaster - Broadcaster game doesn't have any associated resource
16:01:57.586 [qtp984103443-22] DEBUG o.atmosphere.jersey.AtmosphereFilter - Linked HttpServletRequest org.atmosphere.cpr.AtmosphereRequest@23b35955 with ContainerResponse com.sun.jersey.spi.container.ContainerResponse@53adedc2
16:01:57.588 [qtp984103443-22] DEBUG o.a.container.JettyWebSocketUtil - Suspending response: org.atmosphere.cpr.AtmosphereResponse@16394576
