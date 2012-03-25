package org.atmosphere.tictactoe0;

import com.sun.jersey.spi.container.servlet.PerSession;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.jersey.Broadcastable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Produces("application/json")
@PerSession
@Path("/game")
public class TicTacToeGame {


    private Broadcaster gameBroadcaster;
    private TTTGame game;

    public TicTacToeGame(@Context HttpHeaders headers,
                         @Context UriInfo uriInfo,
                         @Context SecurityContext securityContext,
                         @Context ServletConfig servletConfig,
                         @Context ServletContext servletContext,
                         @Context HttpServletRequest httpServletRequest,
                         @Context HttpServletResponse httpServletResponse) {
        return;
    }

    @POST
    @Suspend
    @Path("/")
    public Broadcastable startPost(@PathParam("cell") String cellStr,
                                   @Context HttpHeaders headers,
                                   @Context UriInfo uriInfo,
                                   @Context SecurityContext securityContext,
                                   @Context ServletConfig servletConfig,
                                   @Context ServletContext servletContext,
                                   @Context HttpServletRequest httpServletRequest,
                                   @Context HttpServletResponse httpServletResponse
    ) throws IOException {

        String message = httpServletRequest.getReader().readLine();

        int[] initBoard = {0, 0, 0, 1, 1, 1, 0, 0, 0};
        game = new TTTGame(initBoard);
        return new Broadcastable(game, game, gameBroadcaster);
    }

    @GET
    @Suspend
    //@Broadcast
    //@Path("/get/start")
    @Path("/")
    public Broadcastable startGet(@Context HttpHeaders headers,
                                  @Context UriInfo uriInfo,
                                  @Context SecurityContext securityContext,
                                  @Context ServletConfig servletConfig,
                                  @Context ServletContext servletContext,
                                  @Context HttpServletRequest httpServletRequest,
                                  @Context HttpServletResponse httpServletResponse
    ) {
        if (gameBroadcaster == null) {

            gameBroadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "game", true);
//            gameBroadcaster.getBroadcasterConfig().addFilter(new PerRequestBroadcastFilter() {
//
//                private final Logger logger = LoggerFactory.getLogger(TicTacToeGame.this.getClass());
//
//                @Override
//                public BroadcastAction filter(HttpServletRequest request, HttpServletResponse response, Object message) {
//                    logger.info("PerRequestBroadcastFilter.filter(HttpServletRequest request, HttpServletResponse response, Object message)");
//
//                    if (message instanceof TTTGame) {
//                        TTTGame game = (TTTGame) message;
//                        game.filter = game.filter + "A1";
//                    }
//
//                    return new BroadcastAction(message);
//                }
//
//                @Override
//                public BroadcastAction filter(Object originalMessage, Object message) {
//                    logger.info("PerRequestBroadcastFilter.filter(Object originalMessage, Object message)");
//
//                    if (message instanceof TTTGame) {
//                        TTTGame game = (TTTGame) message;
//                        game.filter = game.filter + "A2";
//                    }
//
//                    return new BroadcastAction(message);
//                }
//            });
        }

        //gameBroadcaster.addAtmosphereResource();

        //int[] initBoard = {0, 10, 1, 10, 1, 10, 1, 10, 0};
        int[] initBoard = {0, 0, 0, 0, 1, 0, 0, 0, 0};
        game = new TTTGame(initBoard);

//        Gson gson = new Gson();
//        String json = gson.toJson(game);
//        return new Broadcastable(json, json, gameBroadcaster);

        return new Broadcastable(game, gameBroadcaster);
    }

    @POST
    @Path("/post/turn/{cell}")
    @Broadcast
    public Broadcastable turnPost(@PathParam("cell") String cellStr,
                                  @Context HttpHeaders headers,
                                  @Context UriInfo uriInfo,
                                  @Context SecurityContext securityContext,
                                  @Context ServletConfig servletConfig,
                                  @Context ServletContext servletContext,
                                  @Context HttpServletRequest httpServletRequest,
                                  @Context HttpServletResponse httpServletResponse) {

        int cell = -1;

        if (cellStr == null) {
            cellStr = "0";
        }
        try {
            cell = Integer.parseInt(cellStr);
        } catch (NumberFormatException nfe) {
            cell = 0;
        }

        game.turn(cell);

        return new Broadcastable(game, game, gameBroadcaster);
    }

    @GET
    @Path("/get/turn/{cell}")
    @Broadcast
    public Broadcastable turnGet(@PathParam("cell") String cellStr,
                                 @Context HttpHeaders headers,
                                 @Context UriInfo uriInfo,
                                 @Context SecurityContext securityContext,
                                 @Context ServletConfig servletConfig,
                                 @Context ServletContext servletContext,
                                 @Context HttpServletRequest httpServletRequest,
                                 @Context HttpServletResponse httpServletResponse) {

        int cell = -1;

        if (cellStr == null) {
            cellStr = "0";
        }
        try {
            cell = Integer.parseInt(cellStr);
        } catch (NumberFormatException nfe) {
            cell = 0;
        }

        game.turn(cell);

        return new Broadcastable(game, game, gameBroadcaster);
    }

}