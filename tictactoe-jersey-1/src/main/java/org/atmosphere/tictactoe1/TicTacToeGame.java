package org.atmosphere.tictactoe1;

import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.slf4j.Logger;
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

@Produces("text/html;charset=ISO-8859-1")
@PerSession
@Path("/game")
public class TicTacToeGame {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeGame.class);

    private Broadcaster gameBroadcaster;
    private TTTGame game;

    public TicTacToeGame(@Context HttpHeaders headers,
                         @Context UriInfo uriInfo,
                         @Context SecurityContext securityContext,
                         @Context ServletConfig servletConfig,
                         @Context ServletContext servletContext,
                         @Context HttpServletRequest httpServletRequest,
                         @Context HttpServletResponse httpServletResponse) {

        logger.info("Constructor of TicTacToeGame is called.");

        return;
    }

    @GET
    @Suspend
    @Broadcast
    @Path("/start")
    public Broadcastable startGet(@Context HttpHeaders headers,
                                  @Context UriInfo uriInfo,
                                  @Context SecurityContext securityContext,
                                  @Context ServletConfig servletConfig,
                                  @Context ServletContext servletContext,
                                  @Context HttpServletRequest httpServletRequest,
                                  @Context HttpServletResponse httpServletResponse
    ) {
        logger.info("TicTacToeGame.startGet() method is called.");

        if (gameBroadcaster == null) {
            gameBroadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "game", true);
        }

        int[] initBoard = {0, 0, 0, 0, 1, 0, 0, 0, 0};
        game = new TTTGame(initBoard);

        Gson gson = new Gson();
        String json = gson.toJson(game);
        return new Broadcastable(json, json, gameBroadcaster);
    }

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
        logger.info("TicTacToeGame.startPost() method is called.");

        int[] initBoard = {0, 0, 0, 1, 1, 1, 0, 0, 0};
        game = new TTTGame(initBoard);

        Gson gson = new Gson();
        String json = gson.toJson(game);
        return new Broadcastable(json, json, gameBroadcaster);
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

        logger.info("TicTacToeGame.turnPost() method is called.");

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
        return new Broadcastable(json, json, gameBroadcaster);
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

        logger.info("TicTacToeGame.turnGet() method is called.");

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
        return new Broadcastable(json, json, gameBroadcaster);
    }

}