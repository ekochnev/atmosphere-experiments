package org.atmosphere.tictactoe4;

import org.atmosphere.cpr.PerRequestBroadcastFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonalPerRequestBroadcastFilter implements PerRequestBroadcastFilter {

    private static final Logger logger = LoggerFactory.getLogger(PersonalPerRequestBroadcastFilter.class);

    public BroadcastAction filter(HttpServletRequest request, HttpServletResponse response, Object message) {

        logger.info("PersonalPerRequestBroadcastFilter.filter(HttpServletRequest request, HttpServletResponse response, Object message)");

        return new BroadcastAction(message);
    }

    public BroadcastAction filter(Object originalMessage, Object message) {
        logger.info("PersonalPerRequestBroadcastFilter.filter(Object originalMessage, Object message)");

        return new BroadcastAction(message);
    }
}
