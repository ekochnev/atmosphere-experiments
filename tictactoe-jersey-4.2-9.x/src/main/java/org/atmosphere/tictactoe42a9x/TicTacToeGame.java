package org.atmosphere.tictactoe42a9x;

import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.*;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Produces("text/html;charset=ISO-8859-1")
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

    @GET
    @Path("/start")
    public SuspendResponse<String> startGet(@Context HttpHeaders headers,
                                  @Context UriInfo uriInfo,
                                  @Context SecurityContext securityContext,
                                  @Context ServletConfig servletConfig,
                                  @Context ServletContext servletContext,
                                  @Context HttpServletRequest httpServletRequest,
                                  @Context HttpServletResponse httpServletResponse) {

        int[] initBoard = {0, 0, 0, 0, 1, 0, 0, 0, 0};
        game = new TTTGame(initBoard);

        if (gameBroadcaster == null) {
            gameBroadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "game", true);

            gameBroadcaster.getBroadcasterConfig().addFilter(new PerRequestBroadcastFilter() {

                private final org.slf4j.Logger logger = LoggerFactory.getLogger(TicTacToeGame.this.getClass());

                @Override
                public BroadcastAction filter(AtmosphereResource atmosphereResource, Object originalMessage, Object message) {

                    logger.info("PerRequestBroadcastFilter.filter(AtmosphereResource atmosphereResource, Object originalMessage, Object message)");
                    return new BroadcastAction(message);
                }

                @Override
                public BroadcastAction filter(Object originalMessage, Object message) {
                    logger.info("PerRequestBroadcastFilter.filter(HttpServletRequest request, HttpServletResponse response, Object message)");
                    return new BroadcastAction(message);
                }
            });
        }

        return new SuspendResponse.SuspendResponseBuilder<String>()
                .broadcaster(gameBroadcaster)
                .outputComments(true)
                .addListener(new AtmosphereResourceEventListener() {

                    @Override
                    public void onSuspend(AtmosphereResourceEvent event) {
                        Gson gson = new Gson();
                        String json = gson.toJson(game);
                        gameBroadcaster.broadcast(json);
                    }

                    @Override
                    public void onResume(AtmosphereResourceEvent event) {
                        return;
                    }

                    @Override
                    public void onDisconnect(AtmosphereResourceEvent event) {
                        return;
                    }

                    @Override
                    public void onBroadcast(AtmosphereResourceEvent event) {
                        return;
                    }

                    @Override
                    public void onThrowable(AtmosphereResourceEvent event) {
                        return;
                    }
                })
                .build();
    }

//    @GET
//    @Suspend
//    @Broadcast
//    @Path("/start")
//    public Broadcastable startGet(@Context HttpHeaders headers,
//                                  @Context UriInfo uriInfo,
//                                  @Context SecurityContext securityContext,
//                                  @Context ServletConfig servletConfig,
//                                  @Context ServletContext servletContext,
//                                  @Context HttpServletRequest httpServletRequest,
//                                  @Context HttpServletResponse httpServletResponse
//    ) {
//        if (gameBroadcaster == null) {
//            gameBroadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "game", true);
//
//            gameBroadcaster.getBroadcasterConfig().addFilter(new PerRequestBroadcastFilter() {
//
//                private final org.slf4j.Logger logger = LoggerFactory.getLogger(TicTacToeGame.this.getClass());
//
//                @Override
//                public BroadcastAction filter(HttpServletRequest request, HttpServletResponse response, Object message) {
//                    logger.info("PerRequestBroadcastFilter.filter(HttpServletRequest request, HttpServletResponse response, Object message)");
//
//                    return new BroadcastAction(message);
//                }
//
//                @Override
//                public BroadcastAction filter(Object originalMessage, Object message) {
//                    logger.info("PerRequestBroadcastFilter.filter(Object originalMessage, Object message)");
//
//                    return new BroadcastAction(message);
//                }
//            });
//        }
//
//        int[] initBoard = {0, 0, 0, 0, 1, 0, 0, 0, 0};
//        game = new TTTGame(initBoard);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(game);
//        return new Broadcastable(json, gameBroadcaster);
//    }

    @POST
    @Suspend
    @Broadcast
    @Path("/start")
    public Broadcastable startPost(@Context HttpHeaders headers,
                                   @Context UriInfo uriInfo,
                                   @Context SecurityContext securityContext,
                                   @Context ServletConfig servletConfig,
                                   @Context ServletContext servletContext,
                                   @Context HttpServletRequest httpServletRequest,
                                   @Context HttpServletResponse httpServletResponse
    ) {

        int[] initBoard = {0, 0, 0, 1, 1, 1, 0, 0, 0};
        game = new TTTGame(initBoard);

        Gson gson = new Gson();
        String json = gson.toJson(game);
        return new Broadcastable(json, gameBroadcaster);
    }

    @POST
    @Path("/turn/{cell}")
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

        Gson gson = new Gson();
        String json = gson.toJson(game);
        return new Broadcastable(json, gameBroadcaster);
    }

    @GET
    @Path("/turn/{cell}")
    @Broadcast
    public Broadcastable turnGet(@PathParam("cell") String cellStr,
                                 @Context HttpHeaders headers,
                                 @Context UriInfo uriInfo,
                                 @Context SecurityContext securityContext,
                                 @Context ServletConfig servletConfig,
                                 @Context ServletContext servletContext,
                                 @Context HttpServletRequest httpServletRequest,
                                 @Context HttpServletResponse httpServletResponse) {

        return turnPost(cellStr,
                headers,
                uriInfo,
                securityContext,
                servletConfig,
                servletContext,
                httpServletRequest,
                httpServletResponse);
    }

}